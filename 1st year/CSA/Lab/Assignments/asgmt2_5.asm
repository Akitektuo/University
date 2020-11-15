bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 2
    b db 3
    c db 1
    d db 4
    e dw 5
    f dw 7
    g dw 8
    h dw 6
segment code use32 class=code
start:
    mov al, [a] ; al = a
    add al, [b] ; al = a + b
    mov [c], al ; h = a + b
    mov ax, [e] ; ax = e
    add ax, [f] ; ax = e + f
    add ax, [g] ; ax = e + f + g
    div byte [c] ; al = (e + f + g) / (a + b)
    
    push dword 0
    call [exit]