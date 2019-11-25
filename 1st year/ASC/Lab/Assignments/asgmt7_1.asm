bits 32

global start

extern exit, printf, scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data
    a dd 0
    b dd 0
    k dd 2
    result db "n=", 0
    format_d db "%d", 0
    
segment code use32 class=code
start:

    ; citire a
    push dword a
    push dword format_d
    call [scanf]
    add esp, 4 * 2

    ; citire b
    push dword b
    push dword format_d
    call [scanf]
    add esp, 4 * 2

    mov edx, 0 ; edx = 0
    mov eax, [a] ; edx:eax = a
    div dword [b] ; eax = edx:eax / b = a / b
    mul dword [k] ; edx:eax = a / b * k

    mov edi, result

    build_edx:
    cmp edx, 0
    jz build_eax

    shl edx, 1
    jc add_edx

    mov al, "0"
    stosb
    jmp build_edx

    add_edx:
    mov al, "1"
    stosb
    jmp build_edx

    build_eax:
    cmp eax, 0
    jz end

    shl eax, 1
    jc add_eax

    mov al, "0"
    stosb
    jmp build_eax

    add_eax:
    mov al, "1"
    stosb
    jmp build_eax

    mov al, 0
    stosb

    end:
    push dword result
    call [printf]
    add esp, 4 * 1
    
    push dword 0
    call [exit]