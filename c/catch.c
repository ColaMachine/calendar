#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <netinet/if_ether.h>
#include <string.h>
int main(int argc, char **argv) {
   int sock, n;
unsigned char *iphead;
	int saddr_len,daddr_len;
	saddr_len=14;
	daddr_len=14;
        char *source,*dest;

   char buffer[2048];
   struct ethhdr *eth;
   struct iphdr *iph;

   if (0>(sock=socket(PF_PACKET, SOCK_RAW, htons(ETH_P_IP)))) {
     perror("socket");
     exit(1);
   }

   while (1) {
     printf("=====================================\n");
     //注意：在这之前我没有调用bind函数，原因是什么呢？
     n = recvfrom(sock,buffer,2048,0,NULL,NULL);
     printf("%d bytes read\n",n);

     //接收到的数据帧头6字节是目的MAC地址，紧接着6字节是源MAC地址。
     eth=(struct ethhdr*)buffer;
     printf("Dest MAC addr:%02x:%02x:%02x:%02x:%02x:%02x\n",eth->h_dest[0],eth->h_dest[1],eth->h_dest[2],eth->h_dest[3],eth->h_dest[4],eth->h_dest[5]);
     printf("Source MAC addr:%02x:%02x:%02x:%02x:%02x:%02x\n",eth->h_source[0],eth->h_source[1],eth->h_source[2],eth->h_source[3],eth->h_source[4],eth->h_source[5]);
     iphead = buffer+14; /* Skip Ethernet  header */
     iph=(struct iphdr*)(buffer+sizeof(struct ethhdr));
     //我们只对IPV4且没有选项字段的IPv4报文感兴趣
  if (*iphead==0x45) { /* Double check for IPv4 
                          * and no options present */
      printf("Source host %d.%d.%d.%d\n",
             iphead[12],iphead[13],
             iphead[14],iphead[15]);
      printf("Dest host %d.%d.%d.%d\n",
             iphead[16],iphead[17],
             iphead[18],iphead[19]);
      printf("Source,Dest ports %d,%d\n",
             (iphead[20]<<8)+iphead[21],
             (iphead[22]<<8)+iphead[23]);
      printf("Layer-4 protocol %d\n",iphead[9]);
    }     
/*
if(iph->version ==4 && iph->ihl == 5){
	//saddr_len = strlen((const char *)inet_ntoa(iph->saddr));  
	//daddr_len = strlen((const char *)inet_ntoa(iph->daddr)); 
	
	//memcpy(source,inet_ntoa(iph->saddr),saddr_len);  
    	//memcpy(dest,inet_ntoa(iph->daddr),daddr_len);  
    	//source[saddr_len] = '/0';  
    	//dest[daddr_len] = '/0';  
        //printf("Source host:%s \n",source);
        //printf("Dest host:%s \n",dest);
     }*/
   }
}
