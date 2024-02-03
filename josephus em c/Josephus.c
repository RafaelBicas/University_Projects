#include <stdio.h>
#include <stdlib.h>
#include "Booleano.h"

typedef struct Celula {
	int item;
	struct Celula* seguinte;
	struct Celula* anterior;
} Celula;

typedef Celula* Lista;

// Interface
Lista criarListaVazia();
bool verificarVazia(Lista);
void mostrarLista(Lista);
Lista inserir(Lista, int);
Lista inserirFimLista(Lista, int);
Lista remover(Lista, Celula*);
Lista esvaziar(Lista);
////////////////


void main() {
	
	Lista L;
	int it;
	Celula* S;
	
	printf("ta vazia\n");	
	L = criarListaVazia();
	if(verificarVazia(L)) {
		printf("ta vazia");		
	}
	L = inserir(L, 3);
	
	//it = L->item;
	//printf("\n\nL[1] = %d", it);
	L = inserir(L, 4);
	
	//printf("\n\nL[2] = %d", it);
	//it = L->item;
	//mostrarLista(L);
	
	//L = inserir(L, 3);
	//L = inserir(L, 4);
	L = inserir(L, 5);
	S = L;
	L = inserir(L, 6);
	L = inserir(L, 7);
	L = remover(L, S); 
	//inserir(L, 3);
	mostrarLista(L);
}

Lista criarListaVazia() {
	Lista L;
	
	L = NULL;
	
	return L;
}

bool verificarVazia(Lista L) {
	bool vazia = FALSE;
	
	if(L == NULL) {
		vazia = TRUE;
	}
	
	return vazia;
}

void mostrarLista(Lista L) {
	Lista S;
	int item;
	
	if(L == NULL) {
		printf("\nLista Vazia\n");
	} else  {
		S = L;
		do {
			item = S->item;
			printf("\n%d ", item);
			S = S->seguinte;
		} while(S != L);
	}
}

Lista inserir(Lista L, int k) { // insere no inicio
	// Criar uma celula
	Lista aux, aux1;
	Celula* S = (Celula*) malloc (sizeof(Celula));
	S->item = k;
	
	//inserir Celula na lista
	if(L == NULL) { // caso seja o primeiro
		S->anterior = S;
		S->seguinte = S;
		L = S;
	} else {
		//aux = L;
		aux = S;
		aux1 = L->anterior;
		aux->seguinte = L;
		aux->anterior = L->anterior;
		L->anterior = aux;
		aux1->seguinte = S;
		L = S;
	}
	return L;
        
}
Lista remover(Lista L, Celula *S) {
	Lista temp;
	Lista aux, aux2;
	
	
	if(L != NULL) {
		aux = L;
		temp = L;
		while(temp != S) {
			temp = temp->seguinte;
		}
		if(temp == L) aux = temp->seguinte;
		if(temp == L->anterior) aux->anterior = temp->anterior;
		aux2 = temp->anterior;
		aux2->seguinte = temp->seguinte;
		aux2 = temp->seguinte;
		aux2->anterior = temp->anterior;
		L = aux;
	}
	return L;
}

