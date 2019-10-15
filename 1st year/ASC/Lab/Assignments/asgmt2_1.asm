bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 64
    b db 4
    
segment code use32 class=code
start:
    mov AL, [a] ; al = a
    mul BYTE [b] ; ax = a * b
    
    push dword 0
    call [exit]