bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    s dw 12345, 20778, 4596
    len resb $ - s
    l db 0
    r times 14 db 0 
    
segment code use32 class=code
start:
    mov esi, s + len - 1 ; i = len - 1
    mov edi, r ; j = 0
    mov ecx, len ; len - number of s elements

    loop_numbers: ; iterates each element in s
        std ; set direction: <-<-<-
        lodsw ; ax = s[i--]
        cld ; clear direction: ->->->
        loop_numbers: ; loop until current number has no digits
            cmp ax, 0 ; ax ? 0
            jz end_loop_numbers ; if (ax == 0)
            mov dx, 0 ; dx = 0 (reset dx for div)
            div word 10 ; ax = dx:ax / 10 ; dx = dx:ax % 10
            mov [edi], dl ; r[j] = dl
            inc edi ; j++
            inc byte [l] ; l++
            jmp loop_numbers ; while (ax > 0)
        end_loop_numbers: ; is called when the current number has no digits left
            loop loop_numbers ; while (len-- > 0)
    
    push dword 0
    call [exit]