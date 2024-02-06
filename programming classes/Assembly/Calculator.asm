
section .data
    msgOp1: db "Digite o primeiro operando: "
    tamanhoMsgOp1: equ $-msgOp1
    msgOp2: db "Digite o segundo operando: "
    tamanhoMsgOp2: equ $-msgOp2
    msgOperador: db "Digite o operador: "
    tamanhoMsgOperador: equ $-msgOperador

section .bss
    valorLido: resb 4
    operando1: resb 2
    operando2: resb 2
    operador: resb 1
    valorResultado: resb 4

section .text
    global main

main:
    ;mensagem operando 1
    mov eax, 4 ;sys_write
    mov ebx, 1 ;stdout
    mov ecx, msgOp1
    mov edx, tamanhoMsgOp1
    int 80h
    
    ;leitura operando 1
    mov eax, 3 ;sys_read
    mov ebx, 0 ;stdin
    mov ecx, valorLido
    mov edx, 4
    int 80h
    
    ;armazenar operando 1
    mov [operando1], word 0
    ;somar primeiro dígito
    mov al, [valorLido] ;copia o primeiro dígito
    movzx ax, al
    sub ax, '0' ;converte de ascii para decimal
    mov bx, 10
    mul bx ;obter n*10 em ax
    mul bx ;obter n*100 em ax
    mul bx ;ober n*1000 em ax
    mov bx, [operando1] ;copiar o valor atual do operando 1 em bx
    add ax, bx ;somar o valor atual do operando 1 com o valor do novo dígito processado
    mov [operando1], ax ;atualizar valor do operando 1 com o dígito processado
    ;somar segundo dígito
    mov al, [valorLido + 1] ;copia o segundo dígito
    movzx ax, al
    sub ax, '0' ;converte de ascii para decimal
    mov bx, 10
    mul bx ;obter n*10 em ax
    mul bx ;obter n*100 em ax
    mov bx, [operando1] ;copiar o valor atual do operando 1 em bx
    add ax, bx ;somar o valor atual do operando 1 com o valor do novo dígito processado
    mov [operando1], ax ;atualizar valor do operando 1 com o dígito processado
    ;somar terceiro dígito
    mov al, [valorLido + 2] ;copia o terceiro dígito
    movzx ax, al
    sub ax, '0' ;converte de ascii para decimal
    mov bx, 10
    mul bx ;obter n*10 em ax
    mov bx, [operando1] ;copiar o valor atual do operando 1 em bx
    add ax, bx ;somar o valor atual do operando 1 com o valor do novo dígito processado
    mov [operando1], ax ;atualizar valor do operando 1 com o dígito processado
    ;somar quarto dígito
    mov al, [valorLido + 3] ;copia o quarto dígito
    movzx ax, al
    sub ax, '0' ;converte de ascii para decimal
    add ax, [operando1] ;somar o valor atual do operando 1 com o valor do novo dígito processado
    mov [operando1], ax ;atualizar valor do operando 1 com o dígito processado
    
    ;mensagem operando 2
    mov eax, 4 ;sys_write
    mov ebx, 1 ;stdout
    mov ecx, msgOp2
    mov edx, tamanhoMsgOp2
    int 80h
    
    ;consumir quebra de linha    
    mov eax, 3 ;sys_read
    mov ebx, 0 ;stdin
    mov ecx, operando2
    mov edx, 1
    int 80h
    
    ;leitura operando 2
    mov eax, 3 ;sys_read
    mov ebx, 0 ;stdin
    mov ecx, valorLido
    mov edx, 4
    int 80h
    
    ;armazenar operando 2
    mov [operando2], word 0
    ;somar primeiro dígito
    mov al, [valorLido] ;copia o primeiro dígito
    movzx ax, al
    sub ax, '0' ;converte de ascii para decimal
    mov bx, 10
    mul bx ;obter n*10 em ax
    mul bx ;obter n*100 em ax
    mul bx ;ober n*1000 em ax
    mov bx, [operando2] ;copiar o valor atual do operando 2 em bx
    add ax, bx ;somar o valor atual do operando 2 com o valor do novo dígito processado
    mov [operando2], ax ;atualizar valor do operando 2 com o dígito processado
    ;somar segundo dígito
    mov al, [valorLido + 1] ;copia o segundo dígito
    movzx ax, al
    sub ax, '0' ;converte de ascii para decimal
    mov bx, 10
    mul bx ;obter n*10 em ax
    mul bx ;obter n*100 em ax
    mov bx, [operando2] ;copiar o valor atual do operando 2 em bx
    add ax, bx ;somar o valor atual do operando 2 com o valor do novo dígito processado
    mov [operando2], ax ;atualizar valor do operando 2 com o dígito processado
    ;somar terceiro dígito
    mov al, [valorLido + 2] ;copia o terceiro dígito
    movzx ax, al
    sub ax, '0' ;converte de ascii para decimal
    mov bx, 10
    mul bx ;obter n*10 em ax
    mov bx, [operando2] ;copiar o valor atual do operando 2 em bx
    add ax, bx ;somar o valor atual do operando 2 com o valor do novo dígito processado
    mov [operando2], ax ;atualizar valor do operando 2 com o dígito processado
    ;somar quarto dígito
    mov al, [valorLido + 3] ;copia o quarto dígito
    movzx ax, al
    sub ax, '0' ;converte de ascii para decimal
    add ax, [operando2] ;somar o valor atual do operando 2 com o valor do novo dígito processado
    mov [operando2], ax ;atualizar valor do operando 2 com o dígito processado
    
    ;mensagem operador
    mov eax, 4 ;sys_write
    mov ebx, 1 ;stdout
    mov ecx, msgOperador
    mov edx, tamanhoMsgOperador
    int 80h
    
    ;consumir quebra de linha    
    mov eax, 3 ;sys_read
    mov ebx, 0 ;stdin
    mov ecx, operador
    mov edx, 1
    int 80h
    
    ;leitura operador
    mov eax, 3 ;sys_read
    mov ebx, 0 ;stdin
    mov ecx, operador
    mov edx, 1
    int 80h
    
    cmp [operador], byte '-'
    je subtracao
    
    cmp [operador], byte 'x'
    je multiplicacao
    
    cmp [operador], byte '/'
    je divisao
    
soma:
    mov eax, [operando1]
    add eax, [operando2]
    mov [valorResultado], eax
    jmp resultado

subtracao:
    mov eax, [operando1]
    sub eax, [operando2]
    mov [valorResultado], eax
    jmp resultado

multiplicacao:
    mov eax, [operando1]
    mov ebx, [operando2]
    mul ebx
    mov [valorResultado], eax
    jmp resultado

divisao:
    mov edx, 0
    mov eax, [operando1]
    mov bx, [operando2]
    div bx
    mov [valorResultado], eax
    jmp resultado
    
resultado:
    ;converter resultado para ascii e imprimir

sair:
    mov eax, 1 ;sys_exit
    mov ebx, 0 ;código do erro
    int 80h