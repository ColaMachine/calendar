#include <sys/types.h>
#include <sys/socket.h>
main()
{
   int s;
   int optval;
   int optlen = sizeof(int);
   if((s = socket(AF_INET, SOCK_STREAM, 0)) < 0)
   perror("socket");
   getsockopt(s, SOL_SOCKET, SO_TYPE, &optval, &optlen);
   printf("optval = %d\n", optval);
   close(s);
}
