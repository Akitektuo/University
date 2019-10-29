bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a dw 2
    b dw 5
    c dw 10
    d dw 40
    x dd 0

segment code use32 class=code
start:
    ; x = a * b + c * d, all numbers are unsigned represented on 1 word (size - word)
    mov ax, [a] ; ax = a
    mul word [b] ; dx:ax = a * b

    ; save dx:ax to x
    mov word [x], ax
    mov word [x + 2], dx

    mov ax, [c] ; ax = c
    mul word [d] ; dx:ax = c * d

    ; x = a * b + c * d
    add word [x], ax
    adc word [x + 2], dx

    push dword 0
    call [exit]