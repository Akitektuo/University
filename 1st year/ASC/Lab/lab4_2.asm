bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 10110011b
    b db 01110001b
    c db 0
segment code use32 class=code
start:
    ; c[0, 2] = a[5, 7]
    ; c[3, 5] = 0
    ; c[6] = 1
    ; c[7] = !b[3]
    mov al, 00000111b
    and al, [a]
    mov cl, 5
    shl al, cl
    or al, 00000010b
    mov bl, [b]
    not bl
    and bl, 00010000b
    or al, bl

    push dword 0
    call [exit]