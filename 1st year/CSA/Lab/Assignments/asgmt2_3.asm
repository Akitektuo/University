bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a dw 1
    b dw 2
    c dw 3
    d dw 4
    
segment code use32 class=code
start:
    mov ax, [a] ; ax = a
    add ax, [b] ; ax = a + b
    sub ax, [c] ; ax = a + b - c
    sub ax, [d] ; ax = (a + b - c) - d

    push dword 0
    call [exit]