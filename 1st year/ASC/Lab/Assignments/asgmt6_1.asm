bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    s dw 12345, 20778, 4596
    r times 15 db 0 
    
segment code use32 class=code
start:
    
    
    push dword 0
    call [exit]