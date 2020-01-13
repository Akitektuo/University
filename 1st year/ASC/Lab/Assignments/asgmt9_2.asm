%ifndef  _MAP_STRING_
%define  _MAP_STRING_

map_string:
    mov ebx, [esp + 4]
    mov esi, [esp + 4 * 2]
    mov edi, [esp + 4 * 3]

    map:
    lodsb
    sub al, 'a'
    js return
    xlat
    stosb
    jmp map

    return:
    ret

%endif