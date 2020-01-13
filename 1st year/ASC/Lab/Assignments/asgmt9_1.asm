bits 32

global start

extern exit, scanf, printf
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll
%include  "asgmt9_2.asm"

segment data use32 class=data
    table db "OPQRSTUVWXYZABCDEFGHIJKLMN", 0
    initial resb 100
    final resb 100
    format_string db "%s", 0

segment code use32 class=code
start:
    push dword initial
    push dword format_string
    call [scanf]
    add esp, 4 * 2
    
    push dword final
    push dword initial
    push dword table
    call map_string
    add esp, 4 * 3

    push dword final
    push dword format_string
    call [printf]
    add esp, 4 * 2
    
    push dword 0
    call [exit]