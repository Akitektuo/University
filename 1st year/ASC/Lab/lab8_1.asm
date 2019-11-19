bits 32

global start

extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    f db "Ana %s are %d mere", 0
    n db "Pop", 0
    
segment code use32 class=code
start:
    push 7
    push n
    push f
    call [printf]
    add esp, 4 * 3

    push dword 0
    call [exit]