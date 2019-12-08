bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db '0', '1', '2', '3', '4', '5'

segment code use32 class=code
start:
    mov ax, [a + 2]
    
    push dword 0
    call [exit]