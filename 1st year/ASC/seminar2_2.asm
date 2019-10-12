bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a DB -30
    b DW 2
    c DB -5
    d DB 10
    x DW 0
    
segment code use32 class=code
start:
    ; x = (a * b) / d - c
    
    mov AL, [a] ; AL = a
    cbw ; AX = a
    imul WORD [b] ; DX:AX = AX * b
    mov BX, AX ; DX:BX = a * b
    mov AL, [d] ; AL = d
    cbw ; AX = d
    mov [x], AX ; x = AX = d
    mov AX, BX ; DX:AX = a * b
    idiv WORD [x] ; AX = DX:AX / x
    mov [x], AX ; x = AX
    mov AL, [c] ; AL = c
    cbw ; AX = c
    sub [x], AX ; x = x - AX
    
    push dword 0
    call [exit]