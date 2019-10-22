bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a dw 0111011101010111b
    b dw 1001101110111110b
    c dw 0

segment code use32 class=code
start:
    mov  bx, 0 ; in registrul bx vom calcula rezultatul

    mov  ax, [b] ; izolam bitii 10-12 ai lui b
    and  ax, 0001110000000000b
    mov  cl, 10
    ror  ax, cl ; rotim 10 pozitii spre dreapta
    or   bx, ax ; punem bitii in rezultat

    or   bx, 0000000001111000b ; facem biti 3-6 din rezultat sa aiba valoarea 1

    mov  ax, [a] ; izolam biti 1-4 ai lui a
    and  ax, 0000000000011110b
    mov  cl, 6
    rol  ax, cl ; rotim 6 pozitii spre stanga
    or   bx, ax ; punem bitii in rezultat

    and  bx, 1110011111111111b ; facem biti 11-12 din rezultat sa aiba valoarea 0

    mov  ax, [b]
    not  ax ; inversam valoarea lui b
    and  ax, 0000111000000000b ; izolam biti 9-11 ai lui b
    mov  cl, 4
    rol  ax, cl ; deplasam biti 4 pozitii spre stanga
    or   bx, ax ; punem biti in rezultat

    mov  [c], bx ; punem valoarea din registru in variabila rezultat
    
    push dword 0
    call [exit]