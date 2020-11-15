%ifndef _FACTORIAL_ASM_
%define _FACTORIAL_ASM_

factorial:
    mov eax, 1
    mov ebx, [esp + 4]
    mov ecx, [ebx]

    l:
    mul ecx
    loop l

    ret

%endif