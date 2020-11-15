bits 32

global start

extern exit, printf, scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data
    a dd 0
    b dd 0
    k dd 2
    format_d db "%d", 0
    format_s db "%s", 0
    result db "-"
    
segment code use32 class=code
start:

    ; citire a
    push dword a
    push dword format_d
    call [scanf]
    add esp, 4 * 2

    ; citire b
    push dword b
    push dword format_d
    call [scanf]
    add esp, 4 * 2

    mov edx, 0 ; edx = 0
    mov eax, [a] ; edx:eax = a
    div dword [b] ; eax = edx:eax / b = a / b
    mul dword [k] ; edx:eax = a / b * k

    mov edi, result ; result[]
    mov cl, 32 ; cl = 32
    mov edx, eax ; edx = a / b * k

    build:
    cmp cl, 0 ; cl ? 0
    jz end ; cl == 0 -> 'end'
    dec cl ; cl--

    shl edx, 1 ; edx << 1
    jc add_bit ; CF == 1 -> 'add_bit'

    mov al, "0" ; al = "0"
    stosb ; result[i++] = "0"
    jmp build ; -> 'build'

    add_bit:
    mov al, "1" ; al = "1"
    stosb ; result[i++] = "1"
    jmp build ; -> 'build'

    ; mark end of array
    mov al, 0 ; al = 0
    stosb ; result[i++] = 0

    end: ; afisare rezultat
    push dword result
    push dword format_s
    call [printf]
    add esp, 4 * 2
    
    push dword 0
    call [exit]