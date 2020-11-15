bits 32

global start

extern exit, fopen, fprintf, fclose
import exit msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll

segment data use32 class=data
    file_name db "no_digits.txt", 0
    acces_mode db "w", 0    
    text db "Ana are 5 mere si 6 cai frumosi.", 0
    len equ $ - text
    result resb len     
    descriptor dd -1
    
segment code use32 class=code
start:
    mov esi, text
    mov edi, result
    mov ecx, len

    replace:
        lodsb
        cmp al, "0"
        jb nothing_to_replace

        cmp al, "9"
        ja nothing_to_replace

        mov al, "C"

        nothing_to_replace:
        stosb
        loop replace

    push dword acces_mode     
    push dword file_name
    call [fopen]
    add esp, 4 * 2

    mov [descriptor], eax

    cmp eax, 0
    je final
    
    push dword result
    push dword [descriptor]
    call [fprintf]
    add esp, 4 * 2
    
    push dword [descriptor]
    call [fclose]
    add esp, 4
    
    final:
    push dword 0
    call [exit]