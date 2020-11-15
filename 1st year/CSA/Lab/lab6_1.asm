bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    s1 dd 1, 2, 3
    len1 equ ($ - s1) / 4
    s2 dd 2, 3, 4, 5, 6
    len2 equ ($ - s2) / 4
    r times len1 + len2 db 0
segment code use32 class=code
start:
    mov edi, r
    mov esi, s1
    mov ecx, len1
    loop1:
        mov [edi], 0
        sub_loop1:
            test dword [esi], 1
            jz end1
            add [edi], 1
            end1
            shr [esi], 1
            cmp dword [esi], 0
            jnz sub_loop1
        inc esi
        inc edi
        loop loop1

    mov esi, s2 + len2 - 1
    mov ecx, len2 - len1
    loop2:
        std
        lodsd
        cld
        stosd
        loop loop2

    push dword 0
    call [exit]