bits 32

global start

extern exit, fopen, fread, fclose
import exit msvcrt.dll
import fopen msvcrt.dll
import fread msvcrt.dll
import fclose msvcrt.dll

segment data use32 class=data
    file_name db "file.txt", 0
    access_mode db "r", 0
    descriptor dd -1
    len equ 100
    text times len db 0

segment code use32 class=code
start:
    push dword access_mode     
    push dword file_name
    call [fopen]
    add esp, 4 * 2

    mov [descriptor], eax
    
    cmp eax, 0
    je final
    
    push dword [descriptor]
    push dword len
    push dword 1
    push dword text        
    call [fread]
    add esp, 4 * 4
    
    push dword [descriptor]
    call [fclose]
    add esp, 4
    
final:
    push dword 0      
    call [exit]       