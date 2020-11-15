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
    mov esi, 0
    
    repeat:
        mov al, [s + esi] ; al = s[esi]
        sub al, 32 ; al = s[esi] - 32
        mov [d + esi], al ; d[esi] = s[esi] - 32
        inc esi ; esi = esi + 1

        ; if esi < len, jump to repeat
        cmp esi, len
        jb repeat

    push dword 0
    call [exit]