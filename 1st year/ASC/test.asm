bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    sum dd 7

segment code use32 class=code
start:
    mov ax, 14
    mov dx, 0
    div word [sum]
    
    push dword 0
    call [exit]