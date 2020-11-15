bits 32

global start

extern exit, fopen, fread, fprintf, fclose, fscanf, printf
import fopen msvcrt.dll
import fread msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll
import fscanf msvcrt.dll
import printf msvcrt.dll
import exit msvcrt.dll

segment data use32 class=data
    file_name db "grades.txt", 0
    access_mode db "a+", 0
    read_format db "%d", 0
    print_format db 10, "%d", 0
    descriptor dd -1
    buffer dd 0
    sum dd 0
    count dw 0
    
segment code use32 class=code
start:
    push access_mode
    push file_name
    call [fopen]
    add esp, 4 * 2

    cmp eax, 0
    je .return

    mov [descriptor], eax

.read:
    push buffer
    push read_format
    push dword [descriptor]
    call [fscanf]
    add esp, 4 * 3

    test eax, eax
    js .stop_read

    mov eax, [buffer]
    mov cx, [count]
    mov ebx, [sum]
    inc cx
    add ebx, eax
    mov [count], cx
    mov [sum], ebx

    jmp .read

.stop_read:
    mov ax, [sum]
    mov dx, 0

    div word [count]
    cwde

    push dword eax
    push dword print_format
    push dword [descriptor]
    call [fprintf]
    add esp, 4 * 3

    push dword [descriptor]
    call [fclose]
    add esp, 4

.return:
    push dword 0
    call [exit]