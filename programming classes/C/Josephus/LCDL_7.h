#include <stdio.h>
#include <stdlib.h>
#include "Booleano.h"

typedef struct Celula
{
	int item;
	struct Celula *seguinte;
	struct Celula *anterior;
} Celula;

typedef Celula * Lista;

Lista criarListaVazia();
bool verificarVazia(Lista);
void mostrarLista(Lista);
Lista inserir(Lista, int);
Lista inserirFimLista(Lista, int);
Lista remover(Lista, Celula*);
Lista esvaziar(Lista);
Lista construirLista(Lista, int);
void permutacaoJosephus(Lista, int, int);

Lista criarListaVazia()
{
	Lista L = NULL;
	return L;
}

bool verificarVazia(Lista L)
{
	return L == NULL;
}

void mostrarLista(Lista L)
{
	if (L == NULL) { printf("Lista vazia.\n"); }
	else
	{
		Celula *C = L;
		do
		{
			printf("%d ", C->item);
			C = C->seguinte;
		}
		while (C != L);
		printf("\n");
	}
}

Lista inserir(Lista L, int n)
{
    Celula *C = malloc(sizeof(Celula));
    C->item = n;
    
    if(L == NULL)
	{
    	C->seguinte = C;
    	C->anterior = C;
    }
	else
	{
        C->seguinte = L;
        C->anterior = L->anterior;
        L->anterior->seguinte = C;
        L->anterior = C;
    }
    L = C;
    
    return L;
}

Lista inserirFimLista(Lista L, int n)
{
	Celula *C = malloc(sizeof(Celula));
    C->item = n;
    
    if(L == NULL)
	{
    	C->seguinte = C;
    	C->anterior = C;
    	L = C;
    }
	else
	{
		C->anterior = L->anterior;
		C->seguinte = L;
        L->anterior->seguinte = C;
        L->anterior = C;
    }
    
	return L;
}

/*Lista remover(Lista L, Celula* C)
{	
	if (L != NULL)
	{
		C->anterior->seguinte = C->seguinte;
		C->seguinte->anterior = C->anterior;
		if (C == L) { L = L->seguinte; } //se a celula for a primeira, atualizar o inicio da lista
		free(C);
	}
	
	return L;
}
*/
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

Lista esvaziar(Lista L)
{
	Celula *fim = L->anterior;
	while (L != fim)
	{
		L = L->seguinte;
		free (L->anterior);
	}
	free(fim);
	L = NULL;
	
	return L;
}

Lista construirLista(Lista L , int n){
	
	int i ;
	L = criarListaVazia();
	
	for(i = 0 ; i< n ;i++){
		
		L = inserirFimLista(L,i+1);
		
	}
	
	return L;
}

void permutacaoJosephus(Lista L , int n , int m){
	//n = número de pessoas
	//m = intervalo de contagem para remover uma célula na permutação de Josephus
	int cont = -1, i = 1, tam = n;
	Celula* p;
	
	L = construirLista(L, n);
	p = L;
	mostrarLista(L);
	//printf("\n");
	do {
		cont = cont + m;
		
		while(i <= cont) {
			p = p->seguinte;
			i++;
		}
		L = remover(L, p);
		mostrarLista(L);
		tam--;
	} while(tam > 1);
	
	
	
}
