bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a dd 10
    b db 8
    c dw 4
    d db 1
    e dq 5

segment code use32 class=code
start:
    ; a + b / c - d * 2 - e

    ; without sign
    mov al, [b]
    mov ah, 0
    mov dx, 0
    div word [c] ; ax = b / c
    mov bx, ax
    mov al, 2
    mul byte [d] ; ax = d * 2
    sub bx, ax
    mov ax, 0 ; ax:bx = a + b / c - d * 2
    add bx, word [a]
    adc ax, word [a + 2]
    mov dx, ax
    mov ax, bx
    push dx
    push ax
    pop eax
    mov edx, 0
    sub eax, dword [e]
    sbb edx, dword [e + 4] ; 4 bytes pana la partea superioara din qw
    
    ;with sign
    mov al, [b]
    cbw
    cwd
    idiv word [c]
    mov bx, ax
    mov al, 2
    imul byte [d] ; ax = d * 2
    sub bx, ax
    mov ax, bx
    cwd
    add ax, word [a]
    adc dx, word [a + 2]
    push dx
    push ax
    pop eax
    cdq
    sub eax, dword [e]
    sbb edx, dword [e + 4] ; 4 bytes pana la partea superioara din qw
    
    push dword 0
    call [exit]