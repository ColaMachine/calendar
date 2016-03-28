//TCP示例客户端 tcpclt.c
#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <netdb.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <arpa/inet.h>
int main(int argc, char *argv[])
{
    int skfd;
    char buf[1024] = {0};
    struct sockaddr_in server_addr;
    struct hostent *host;
    int portnumber,nbytes;
    if(3 != argc || 0>(portnumber=atoi(argv[2])))
    {
         printf("Usage:%s hostname portnumber \n");
         exit(1);
    }
    if(NULL == (host=gethostbyname(argv[1])))
    {
         perror("Gethostname error:");
         exit(1);
    }

    /* 创建socket描述符 */
    if(-1 == (skfd=socket(AF_INET,SOCK_STREAM,0)))
    {
         perror("Socket Error:");
         exit(1);
    }

    /* 客户端填充需要连接的服务器的地址信息结构体 */
    bzero(&server_addr,sizeof(server_addr));
    server_addr.sin_family=AF_INET;
    server_addr.sin_port=htons(portnumber);
    server_addr.sin_addr=*((struct in_addr *)host->h_addr);

    /* 客户端调用connect主动发起连接请求 */
    if(-1 == connect(skfd,(struct sockaddr *)(&server_addr),sizeof(struct sockaddr)))
    {
         perror("Connect Error:");
         exit(1);
    }

    /*客户端只接收服务器发来的数据，然后就退出*/
    if(-1 == read(skfd,buf,1024)){
         perror("Recv Error:");
    }
    printf("Date arrived:%s",buf);

    /* 拆除TCP连接 */
    close(skfd);
    exit(0);
}
