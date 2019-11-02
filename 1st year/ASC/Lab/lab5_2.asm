bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 1, 3, -4, -6
    len equ $ - a
    r resb len
    
segment code use32 class=code
start:
    mov esi, a
    mov edi, r
    mov ecx, len

    fillResult:
        cmp byte [esi], 0
        jge end
        test byte [esi], 1
        jnz end
        mov al, [esi]
        mov [edi], al
        inc edi
        end:
            inc esi
    loop fillResult

    
    push dword 0
    call [exit]