#include <unistd.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <linux/if_ether.h>
#include <linux/in.h>
#include <stdio.h>
#include <errno.h>
#define BUFFER_MAX 2048
 
int main(int argc, char *argv[]){
    int  SOCKET_SRC;
    char buf[BUFFER_MAX];
    int n_rd;

    if( (SOCKET_SRC = socket(PF_PACKET, SOCK_RAW, htons(ETH_P_IP))) < 0 ){
        fprintf(stderr, "create socket error.\n");
        exit(0);
    }
    while(1){
        n_rd = recvfrom(SOCKET_SRC, buf, BUFFER_MAX, 0, NULL, NULL);
      if (n_rd<46) {
          perror("recvfrom():");
           printf("Incomplete packet (errno is %d)\n",  errno);
           close(SOCKET_SRC);
           exit(0);
      }
      /* An Ethernet frame was written to buf, frame analysis can be processed here */
      /* Termination control */
    }
    close(SOCKET_SRC);
    return 0;
}
