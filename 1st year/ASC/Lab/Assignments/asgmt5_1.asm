bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 2, 1, -3, 3, 3, -4, 2, 6
    b db 4, 5, 7, 6, 2, 1
    bLen equ $ - b
    aLen equ $ - a
    r resb len
    
segment code use32 class=code
start:
    mov esi, b ; i = 0 
    mov ecx, bLen ; len = number of b elements
    push_b: ; push all b values to the stack
        mov al, [esi] ; al = b[i]
        push al ; stack.push(al)
        inc esi ; i++
    loop push_b ; while (len > 0)

    mov edi, r ; j = 0
    mov ecx, bLen ; len = number of b elements
    pop_b: ; pop all b values from the stack to form the reverse array
        pop al ; al = stack.pop()
        mov [edi], al ; r[j] = al
        inc edi ; j++
    loop pop_b ; while (len > 0)
    
    mov esi, a ; i = 0
    mov ecx, aLen ; len = number of a elements
    filter_a: ; copy all negative numbers from a
        cmp byte [esi], 0 ; check if a[i] < 0
        jge end_of_loop ; if (a[i] < 0) -> end_of_loop
            mov al, [esi] ; al = a[i]
            mov [edi], al ; r[j] = al
            inc edi ; j++
        end_of_loop: ; the last part of the loop
            inc esi ; i++
    loop filter_a: ; while (len > 0)

    push dword 0
    call [exit]