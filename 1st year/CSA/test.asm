bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a1 db '256'
    a2 db 256, 256h
    ;a3 db $ + a2 -> The result cannot be located
    a4 equ -256 / 4
    ;a5 db 256 shr 1, 256 shl 1 -> shr and shl are instructions, not operators
    ;a6 dw -1 lt 256 -> lt is not written as string
    a7 dd a2
    a8 dd 256h, 256256h
    a9 dd $ - a9
    a10 db 256, -256
    a dd 12345678h

segment code use32 class=code
start:
    mov eax, a
    mov bl, 0
    ; push dword [ebx + 6]
    ; pop ebx
    div bl

    push dword 0
    call [exit]