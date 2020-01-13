bits 32

extern _table

global _mapString

segment data public data use32

segment code public code use32

_mapString:
    push ebp
    mov ebp, esp

    mov ebx, _table
    mov esi, [ebp + 4 * 2]
    mov edi, [ebp + 4 * 3]

    map:
    lodsb
    sub al, 'a'
    js return
    xlat
    stosb
    jmp map

    return:
    mov esp, ebp
    pop ebp
    ret