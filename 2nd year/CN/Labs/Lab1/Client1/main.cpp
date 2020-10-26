#include <stdio.h>

#ifndef WIN32
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>

#include <unistd.h>
#include <errno.h>

#define closesocket close
typedef int SOCKET;
#else
// on Windows include and link these things
#include<WinSock2.h>
// for uint16_t an so
#include<cstdint>

// this is how we can link a library directly from the source code with the VC++ compiler – otherwise got o project settings and link to it explicitly
#pragma comment(lib,"Ws2_32.lib")
#endif

int main() {
    int c;
    struct sockaddr_in server;
    uint16_t a, b, suma;

    c = socket(AF_INET, SOCK_STREAM, 0);
    if (c < 0) {
        printf("Eroare la crearea socketului client\n");
        return 1;
    }

    memset(&server, 0, sizeof(server));
    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = inet_addr("127.0.0.1");

    if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0) {
        printf("Eroare la conectarea la server\n");
        return 1;
    }
    printf("a = ");
    scanf("%hu", &a);
    printf("b = ");
    scanf("%hu", &b);
    a = htons(a);
    b = htons(b);
    send(c, reinterpret_cast<const char *>(&a), sizeof(a), 0);
    send(c, reinterpret_cast<const char *>(&b), sizeof(b), 0);
    recv(c, reinterpret_cast<char *>(&suma), sizeof(suma), 0);
    suma = ntohs(suma);
    printf("Suma este %hu\n", suma);
    closesocket(c);
}