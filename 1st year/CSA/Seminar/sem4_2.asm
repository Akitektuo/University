bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    s db 1, 2, 3, 4
    len equ $ - s
    d times len db 0
    
segment code use32 class=code
start:
    mov esi, s
    mov edi, d + len - 1
    mov ecx, len
    repeat:
        cld
        lods
        std
        stosb
        loop repeat
    
    push dword 0
    call [exit]