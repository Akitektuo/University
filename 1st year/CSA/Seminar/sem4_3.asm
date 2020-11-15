bits 32

global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    s dw 2345h, 0a5h, 368h, 3990h
    len1 equ ($ - s) / 2
    t dw 4h, 2655h, 10
    len2 equ ($ - t) / 2
    
segment code use32 class=code
start:
    mov esi, s
    mov edi, r
    mov ecx, len1
    cld
    repeat:
        lodsw
        stosb
        loop repeat

    mov esi, t
    mov ecx, len2
    repeat1:
        lodsw
        mov al, ah
        stosb
        loop repeat1

    mov dx, 1
    repeat2:
        cmp dx, 0
        je the_end
        mov dx, 0
        mov esi, r
        mov ecx, len1 + len2
        repeat3:
            mov al, [esi]
            cmp al, [esi + 1]
            jle next
            mov ah, [esi + 1]
            mov [esi], ah
            mov [esi + 1], al
            mov dx, 1

        next:
            inc esi
            loop repeat3
            jmp repeat2

    the_end:
        push dword 0
        call [exit]