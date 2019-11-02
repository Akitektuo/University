bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    b dd 10011011101111101001101110111110b
    c dd 0
segment code use32 class=code
start:
    mov ebx, 0 ; bx = 0 - reazultat

    ; ebx[0, 3] = b[5-8]
    mov eax, [b]
    and eax, 00000000000000000000000111100000b
    mov cl, 5
    ror eax, cl
    or ebx, eax

    ; ebx[4, 10] = !b[0, 6]
    mov eax, [b]
    not eax
    and eax, 00000000000000000000000001111111b
    mov cl, 4
    rol eax, cl
    or ebx, eax

    or ebx, 00000000000001111111100000000000b ; ebx[11, 18] = 1

    ; ebx[19, 31] = b[3, 15]
    mov eax, [b]
    and eax, 00000000000000001111111111111000b
    mov cl, 16
    rol eax, cl
    or ebx, eax

    mov [c], ebx

    push dword 0
    call [exit]