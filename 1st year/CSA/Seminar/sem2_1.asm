bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a DB 3
    b DB 4
    c DB 5
    d DB 10
    e DB 30
    x DB 0
    
segment code use32 class=code
start:
    ; x = (a + b) * c / d
    
    ; a + b
    mov AL, [a]
    add AL, [b]
    
    ; (a + b) * c
    mul BYTE [c]
    
    ; (a + b) * c / d
    div BYTE [d]
    
    ; x = (a + b) * c / d
    mov [x], AL
    
    ; x = e - b * c / d
    
    ; b * c
    mov AL, [b]
    mul BYTE [c]
    
    ; convert from BYTE to WORD
    mov BL, [e]
    mov BH, 0
    
    ; e - b * c
    sub BX, AX
    
    ; e - b * c / d
    mov AX, BX
    div BYTE [d]
    
    ; x = e - b * c / d
    mov [x], AL
    
    push dword 0
    call [exit]