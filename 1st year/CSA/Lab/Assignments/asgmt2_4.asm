bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 2
    b db 2
    c db 3
    d dw 50

segment code use32 class=code
start:
    mov al, 10 ; al = 10
    mul byte [a] ; ax = 10 * a
    mov bx, 100 ; bx = 100
    sub bx, ax ; bx = 100 - 10 * a
    mov al, [b] ; al = b
    add al, [c] ; al = b + c
    mov cl, 4 ; cl = 4
    mul byte cl ; ax = 4 * (b + c)
    add ax, bx ; ax = 100 - 10 * a + 4 * (b + c)
    sub ax, [d] ; ax = [100 - 10 * a + 4 * (b + c)] - d

    push dword 0
    call [exit]