bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 2
    b db 3
    c db 4
    d db 5
segment code use32 class=code
start:
    mov al, [c] ; al = c
    add al, [d] ; al = c + d
    add al, [d] ; al = c + d + d

    mov bl, [a] ; bl = a
    add bl, [a] ; bl = a + a
    add bl, [b] ; bl = a + a + b

    sub al, bl ; al = al - bl = (c + d + d) - (a + a + b)

    push dword 0
    call [exit]