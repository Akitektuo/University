; Sa se citeasca de la tastatura mai multe numere intregi pana la citirea valorii 0. Sa se
; scrie intr-un fisier, separate de spatii, doar acele numere negative care au numar impar de
; biti (cu valoarea 1).

bits 32

global start

extern exit, fopen, scanf, fprintf, fclose
import exit msvcrt.dll
import fopen msvcrt.dll
import scanf msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll

segment data use32 class=data
    numbers resd 100
    num dd 0
    len db 0
    read_format db "%d", 0 

    file_name db "test_results.txt", 0
    access_mode db "w", 0
    print_format db "%d ", 0
    descriptor dd -1
    
segment code use32 class=code
start:
    push access_mode
    push file_name
    call [fopen]
    add esp, 4 * 2

    cmp eax, 0
    je .return

    mov [descriptor], eax

    mov edi, numbers

.read:
    push num
    push read_format
    call [scanf]
    add esp, 4 * 2

    mov eax, [num]
    cmp eax, 0
    jz .end_read

    stosd
    inc byte [len] 
    jmp .read

.end_read:
    mov esi, numbers
    mov bl, [len]

.filter:
    cmp bl, 0
    je .end_filter

    lodsd
    dec bl
    cmp eax, 0
    jge .filter
    jp .filter

    push dword eax
    push dword print_format
    push dword [descriptor]
    call [fprintf]
    add esp, 4 * 3

    jmp .filter

.end_filter:
    push dword [descriptor]
    call [fclose]
    add esp, 4

.return:
    push dword 0
    call [exit]