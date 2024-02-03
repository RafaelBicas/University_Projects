#include <stdio.h>
#include "Booleano.h"
#define Maximo 6
#define Fantasma 0


typedef struct {
	int tamanho;
	int vetor[Maximo];
}ListaInt;

void criarLista(ListaInt *);
void mostrarLista(ListaInt*);
void construirLista(int, int, ListaInt*);
int obterTamanho(ListaInt *);
bool verificarListaVazia(ListaInt *);
void obterElemento(ListaInt *, int, int);
int buscarElemento(ListaInt *, int);
void inserir(ListaInt *a, int novo);

void main() {
	ListaInt *a;
	int n = 3, item = 2, p = 1, obterItem, buscarItem = 2, posicao = 0, novo = 7, tamanhoFibonacci = 5, x = 100, y = 300, i;
	bool teste;
	
	criarLista(&a);
	mostrarLista(&a);
	construirLista(n, item, &a);
	mostrarLista(&a);
	//printf("\n\ntamanho = %d", n);
	n = obterTamanho(&a);
	/*printf("\n\ntamanho = %d", n);
	teste = verificarListaVazia(&a);
	printf("\nteste(0/1): %d\n", teste);
	obterElemento(&a, p, obterItem);
	printf("\nItem Obtido: %d\n", obterItem);*/
	posicao = buscarElemento(&a, buscarItem);
	printf("\nposicao do item buscado: %d\n", posicao);
	
	inserir( &a, novo);
	printf("\nNova Lista:\n");
	mostrarLista(&a);
	
}

void criarLista(ListaInt *a) {
	//ListaInt *a; 
	a->tamanho = 0;
	
}

void construirLista(int n, int item, ListaInt *a) {
	//ListaInt *a;
	int i;
	if(n > 0 && n < Maximo) {
		a->tamanho = n;
	} else {
		a->tamanho = Maximo - 1;
	}
	for(i = 1; i <= a->tamanho; i++) {
		a->vetor[i] = 0;
		a->vetor[i] = item;
	}
	//return *a;
}

void mostrarLista(ListaInt *a) {
	int n = 0,i;
	
	if (a->tamanho == 0) {
		printf("\nlista vazia!!\n");
	} else {
		n = a->tamanho;
		printf("\nn = %d\n\n", n);
		for (i = 1; i <= n; i++) {
			printf("%d", a->vetor[i]);
			printf("\n");
		}
	}
}

int obterTamanho(ListaInt *a) {
	int n;
	
	n = a->tamanho;
	return n;
}

bool verificarListaVazia(ListaInt *a) {
	bool ok;
	
	if(a->tamanho = 0) {
		ok = TRUE;
	} else {
		ok = FALSE;
	}
	return ok;
}

void obterElemento(ListaInt *a, int p, int item) {
	//;
	
	if((p >= 1) && (p <= a->tamanho)) {
		item = a->vetor[p];
	} else {
		item = Fantasma;
	}
	
	//return item;
}

int buscarElemento(ListaInt *a, int item) {
	int p, k = 0;
	
	p = 0;
	
	if(a->tamanho != 0) {
		k = 1;
		while((a->vetor[k] != item) && (k < a->tamanho)) {
			k = k + 1;
		}
		
		if(a->vetor[k] == item) {
			p = k;
		}
	}
	return p;
}

void inserir(ListaInt *a, int novo) {
	int p = a->tamanho + 1;
	
	a->vetor[p] = novo;
	a->tamanho = p;
	
	//return *a;
}
