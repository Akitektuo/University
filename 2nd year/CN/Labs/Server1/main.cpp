// ServerSuma-Iterativ.cpp : Defines the entry point for the console application.

// exists on all platforms
#include <stdio.h>
#include <iostream>

// this section will only be compiled on NON Windows platforms
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
    SOCKET s;
    struct sockaddr_in server, client;
    int c, l, err;

// initialize the Windows Sockets LIbrary only when compiled on Windows
#ifdef WIN32
    WSADATA wsaData;
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) < 0) {
        printf("Error initializing the Windows Sockets LIbrary");
        return -1;
    }
#endif
    s = socket(AF_INET, SOCK_STREAM, 0);
    if (s < 0) {
        printf("Eroare la crearea socketului server\n");
        return 1;
    }

    memset(&server, 0, sizeof(server));
    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;

    if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
        perror("Bind error:");
        return 1;
    }

    listen(s, 5);

    l = sizeof(client);
    memset(&client, 0, sizeof(client));

    while (1) {
        printf("Listening for incomming connections\n");
        c = accept(s, (struct sockaddr *) &client, &l);
        err = errno;
#ifdef WIN32
        err = WSAGetLastError();
#endif
        if (c < 0) {
            printf("accept error: %d", err);
            continue;
        }
        printf("Incomming connected client from: %s:%d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));
        // serving the connected client
        char a;
        std::string message;
        while (recv(c, &a, sizeof(a), 0)) {
            message += a;
        }
        std::cout << '\'' << message << "'\n";

        //decode the value to the local representation
        //res = send(c, (char *) &suma, sizeof(suma), 0);
        //if (res != sizeof(suma)) printf("Error sending result\n");
        //on Linux closesocket does not exist but was defined above as a define to close
        closesocket(c);
    }

    // never reached
    // Release the Windows Sockets Library
#ifdef WIN32
    WSACleanup();
#endif
}