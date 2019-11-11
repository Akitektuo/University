bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    a db 2, 1, -3, 3, 3, -4, 2, 6
    b db 4, 5, 7, 6, 2, 1
    bLen equ ($ - b) / 2
    aLen equ ($ - a) / 2
    r resb aLen + bLen
    
segment code use32 class=code
start:

    mov esi, b ; i = 0
    mov edi, r + bLen - 1 ; j = bLen - 1
    mov ecx, bLen ; len = number of b elements
    fill_b: ; obtain the reverse array of b
        cld ; clear direction: ->->->
        lods ; al = b[i++]
        std ; set direction: <-<-<-
        stosb ; r[j++] = al
        loop fill_b ; whiile (len > 0)
    
    cld ; clear direction: ->->->
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