#include <stdio.h>
#include <stdlib.h>

typedef struct Celula {
	int item;
	struct Celula * next;
} Celula;

typedef struct {
	int tam;
	Celula* inicio;
}Lista;

Lista criarLista();
void mostrarLista(Lista);
void inserirInicio(Lista*, int);

Lista criarLista() {
	Lista L;
	L.tam = 0;
	L.inicio = NULL;
	return L;
}

void inserirInicio(Lista *p, int y) {
	Celula* aux;
	aux = (Celula*) malloc (sizeof (Celula));
	aux->item = y;
	aux->next = p->inicio;
	p->inicio = aux;
	p->tam = p->tam + 1;
}



void mostrarLista(Lista L) {
	
	 Celula *p;
     p = L.inicio;
     int i;
     for( i = 0; i<= L.tam; i++ ) {
     	printf( "%d: %d \n",i, p->item );
		p = p->next;
	 }
	 if ( L.tam == 0 ) { 
	 	printf("vazio baby");
	 }
}
//	Celula* S;
//	
//	if(L.inicio == NULL ) {
//		printf("Lista Vazia\n");
//	} else {
//		S = L.inicio;
//		while(S != NULL) {
//			printf("%d",S->item);
//			S = S->next;
//		}
//	}
	

