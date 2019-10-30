bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 2
    b dw 4
    e dd 10
    x dq 5
segment code use32 class=code
start:

    ; UNSIGNED

    mov al, [a] ; al = a
    mul al ; ax = a * a
    mov dx, 0 ; dx:ax = a * a
    div word [b] ; ax = a * a / b
    mov bx, ax ; bx = a * a / b
    mov ax, [b] ; ax = b
    mul word [b] ; dx:ax = b * b
    mov cx, 0 ; cx:bx = a * a / b

    ; cx:bx = a * a / b + b * b
    add bx, ax
    adc cx, dx

    ; eax = a * a / b + b * b
    push cx
    push bx
    pop eax

    mov edx, 0 ; edx:eax = a * a / b + b * b
    mov bx, 2 ; bx = 2

    ; cx:bx = 2 + b
    mov cx, 0 
    add bx, [b]
    adc cx, 0 

    ; ebx = 2 + b
    push cx
    push bx
    pop ebx

    div ebx ; eax = (a * a / b + b * b) / (2 + b)
    mov ebx, 0 ; ebx:eax = (a * a / b + b * b) / (2 + b)

    ; ebx:eax = (a * a / b + b * b) / (2 + b) + e
    add eax, [e]
    adc ebx, 0

    ; ebx:eax = (a * a / b + b * b) / (2 + b) + e - x
    sub eax, dword [x]
    sbb ebx, dword [x + 4]

    ; SIGNED

    mov al, [a] ; al = a
    imul al ; ax = a * a
    cwd ; dx:ax = a * a
    idiv word [b] ; ax = a * a / b
    cwd ; dx:ax = a * a / b

    ; cx:bx = a * a / b
    mov bx, ax
    mov cx, bx

    mov ax, [b] ; ax = b
    imul word [b] ; dx:ax = b * b

    ; cx:bx = a * a / b + b * b
    add bx, ax
    adc cx, dx

    ; eax = a * a / b + b * b
    push dx
    push ax
    pop eax

    mov ebx, 2 ; ebx = 2
    add ebx, [b] ; ebx = 2 + b
    idiv ebx ; eax = (a * a / b + b * b) / (2 + b)
    cdq ; edx:eax = (a * a / b + b * b) / (2 + b)

    ; ebx:eax = (a * a / b + b * b) / (2 + b) + e
    add eax, [e]
    adc ebx, 0 

    ; ebx:eax = (a * a / b + b * b) / (2 + b) + e - x
    sub eax, dword [x]
    sbb ebx, dword [x + 4]

    push dword 0
    call [exit]