bits 32

global start

extern exit, fopen, fprintf, scnaf
import exit msvcrt.dll1
import fopen msvcrt.dll1
import fprintf msvcrt.dll1
import scanf msvcrt.dll1

segment data use32 class=data
    fmt db "Ana %s are %d mere", 0
    f db "ana.txt", 0
    m db "w", 0
    n db "Pop", 0
    d_f dd -1
    mere dd 3
    format_d db "%d", 0
    format_s db "%s", 0

segment code use32 class=code
start:
    push dword n
    push dword format_s
    call [scanf]
    add esp, 4 * 2 

    push dword mere
    push dword format_d
    call [scanf]
    add esp, 4 * 2

    push dword n
    push dword format
    call [scanf]
    add esp, 4 * 2 

    push m
    push f
    call [fopen]
    cmp eax, 0
    jz end
    mov [d_f], eax
    add esp, 4 * 2

    push [mere]
    push n
    push fmt
    push dword [d_f]
    call [fprintf]
    add esp, 4 * 4

    end

    push dword 0
    call [exit]