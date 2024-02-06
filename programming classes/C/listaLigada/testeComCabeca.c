#include "lLigadaSemCabeca.h"

void main() {
	Lista L;
	
	L = criarLista();
	mostrarLista(L);
	inserirInicio(&L, 3);
	mostrarLista(L);
}


