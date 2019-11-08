bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    s db "abc"
    len equ $ - s
    d times len db 0

segment code use32 class=code
start:
    mov esi, s
    mov edi, d
    mov ecx, len
    cld

    repeat:
        lodsb
        sub al, 'a' - 'A'
        stosb
        loop repeat
    
    push dword 0
    call [exit]