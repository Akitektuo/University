bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 5
    b dw 10
    c dd 15
    d dq 20
segment code use32 class=code
start:
    mov eax, [c] ; eax = c
    mov ebx, [a] ; ebx = a
    sub eax, ebx ; eax = c - a

    mov ebx, 0 ; ebx = 0
    mov ecx, [b] ; ecx = b

    ; ebx:ecx = b - d
    sub ecx, dword [d]
    sbb ebx, dword [d + 4]

    ; ebx:eax = (c - a) + (b - d) 
    add eax, ecx
    adc ebx, 0 

    ; ebx:eax = (c - a) + (b - d) + d
    add eax, dword [d]
    adc ebx, dword [d + 4]
    
    push dword 0
    call [exit]