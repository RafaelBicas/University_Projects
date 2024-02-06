#include <stdio.h>
#include "LCDL_7.h"

int main()
{
	Lista S;
	Lista L = criarListaVazia();
	L = inserirFimLista(L, 5);
	L = inserirFimLista(L, 6);
	L = inserirFimLista(L, 7);
	L = remover(L, L->seguinte);
	L = esvaziar(L);
	mostrarLista(L);
	//mostrarLista(S);
	
	permutacaoJosephus(S, 40, 12);
	return 0;
}
