bits 32

global start

extern exit, scanf, printf
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll

%include "factorial.asm"
segment data use32 class=data
    f db "%u", 0
    n resd 1
    f2 db "f=%u", 0
    
segment code use32 class=code
start:
    push n
    push f
    call [scanf]
    add esp, 4 * 2

    call factorial
    add esp, 4

    push eax
    push f2
    call [printf]
    add esp, 4 * 2
    
    push dword 0
    call [exit]