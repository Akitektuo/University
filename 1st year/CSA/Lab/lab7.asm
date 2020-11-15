bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    
segment code use32 class=code
start:
    mov ax, 65535
    mov bl, 10
    idiv bl
    
    push dword 0
    call [exit]