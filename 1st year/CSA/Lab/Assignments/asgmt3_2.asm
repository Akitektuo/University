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
    ; ecx:ebx = d
    mov ebx, dword [d]
    mov ecx, dword [d + 4]

    ; ecx:ebx = d - a
    sub ebx, [a]
    sbb ecx, 0

    mov ax, [b] ; ax = b
    add ax, [a] ; ax = b + a
    cwde ; eax = b + a

    ; edx:eax = b + a - c
    mov edx, 0
    sub eax, [c]
    sbb edx, 0

    ; ecx: ebx = d - a + (b + a - c)
    add ebx, eax
    adc ecx, edx

    ; ebx: eax = d - a + (b + a - c)
    mov eax, ebx
    mov ebx, ecx

    push dword 0
    call [exit]