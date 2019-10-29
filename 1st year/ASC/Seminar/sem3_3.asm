bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    s db 'a', 'b', 'c' ; defines a string with 3 bytes
    len equ $ - s ; equ - len is a constant
    d times len db 0

segment code use32 class=code
start:
    mov esi, s
    mov edi, d
    mov ecx, len

    repeat:
        mov al, [esi]
        sub al, 32
        mov [edi], al
        inc esi
        inc edi
        dec ecx

        cmp ecx, 0
        ja repeat

    push dword 0
    call [exit]