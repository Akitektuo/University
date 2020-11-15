bits 32

global start

extern exit
import exit msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data
    v resd 128
    fmt db "%d", 0

segment code use32 class=code
start:
    mov ebx, v
    mov ecx, 128

    .bucla:
        push ecx
        push ebx
        push fmt
        call [scanf]
        add esp, 2 * 4
        pop ecx
        add ebx, 4
        cmp eax, 0
    loopnz .bucla

    push dword 0
    call [exit]