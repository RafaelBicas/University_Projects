#include <stdio.h>
#include <stdlib.h>
#include "Booleano.h"
#define MAX_VET 3

typedef struct Celula {
	int item;
	struct Celula* next;
} Celula;

typedef Celula* Lista;

///////INTERFACE/////////
Lista criarLista();
bool verificarListaVazia(Lista);
void inserir(Lista*, int);
void mostrar(Lista);
void removerPrimeiro(Lista *);
Celula* buscarElemento(Lista, int);
void inserirSeguinte(Celula*, int);
void buscaRemove(Lista, int);
void buscaRemoveTodos(Lista, int);
bool verificarCrescente(Lista);
void trocarCelulas(Lista, Celula*, Celula*);
//Celula* obterMinimo(Lista);
Lista copiarLista(Lista);
bool verificarIgual(Lista, Lista);
Lista concatenarListas(Lista, Lista);
Lista copiarVetorLista(int[]);
void copiarListaVetor( Lista, int[]);
int obterAlturaCelula(Lista, Celula*);
int obterProfundidadeCelula(Lista, Celula*);
Lista inverterLista(Lista);
Lista listaCircular(Lista L);
/////////////////////////

////////FUNCAO PRINCIPAL///////
void main() {
	Lista L, P, C, D;
	Celula *S, *A;
	int novo = 3, vet[MAX_VET], tam;
	bool ok;
	vet[0] = 1;
	vet[1] = 2;
	vet[2] = 3;
	L = criarLista();
	//P = criarLista();
	//C = criarLista();
	//D = criarLista();
	if(verificarListaVazia(L)) {
		mostrar(L);
	}
	inserir(&L, 6);
	inserir(&L, 5);
	inserir(&L, 4);
	inserir(&L, 3);
	inserir(&L, 2);
	inserir(&L, 1);
	//Celula *V = obterMinimo(L);
	//printf("\n\nMinimo = %d\n\n", V->item);
	mostrar(L);
	printf("\n");
	//D = inverterLista(L); 
	D = listaCircular(L);
	mostrar(D);
/*
	inserir(&P, 12);
	inserir(&P, 11);
	inserir(&P, 10);
	inserir(&P, 9);
	inserir(&P, 8);
	inserir(&P, 7);
	//C = concatenarListas(L, P);
	//inserir(&P, 7);
	//ok = verificarIgual(L, P);
	//printf("\nok?  %d\n", ok);
	//mostrar(P);
	//D = NULL;
	//D = copiarVetorLista(vet);
	S = buscarElemento(L, 2);
	tam = obterAlturaCelula(L, S);
	printf("\n\nTam = %d\n\n", tam);
	printf("\n");
	inserir(&D, 3);
	inserir(&D, 2);
	inserir(&D, 1);
	copiarListaVetor(D, vet);
	*/
}
///////////////////////////////

/////ESTRUTURA DE DADOS//////
Lista criarLista() {
	Lista L;
	L = NULL;
	return L;
}

bool verificarListaVazia(Lista L) {
	return L == NULL;
}

void inserir(Lista* L, int novo) {
	Celula* inicio;
	inicio = (Celula*) malloc (sizeof(Celula));
	inicio->item = novo;
	inicio->next = *L;
	*L = inicio;
}

void mostrar(Lista L) {
	Lista S = L;
	if(L != NULL) {
		while(S != NULL) {
			printf("%d\n", S->item);
			S = S->next;			
		}
	} else {
		printf("Lista Vazia\n");
	}
}

void removerPrimeiro(Lista *L) {
	Celula *S; 
	if(L != NULL) {
		S = (*L)->next;
		*L = S;
	}
}


Celula* buscarElemento(Lista L, int valor) {
	bool achou;
	Celula* S = L;
	if(S != NULL) {
		achou = FALSE;
		do {
			if(S->item == valor) {
				achou = TRUE;	
			} else {
				S = S->next;
			}
		} while(S != NULL && !achou);
	}
	return S;
}

void inserirSeguinte(Celula* L, int valor) {
	Celula *A;
	A = (Celula *) malloc (sizeof (Celula));
	A->item = valor;
	A->next = L->next;
	L->next = A;
}

void buscaRemove(Lista p, int y) {
	Lista aux, ant;
	
	if(p == NULL) {
		printf("\nA lista já está vazia\n");
	} else {
		aux = p;
		while(aux->item != y && aux != NULL) {
			ant = aux;
			aux = aux->next;
		}
			if( aux == p) {
				p = aux->next;
			} else {
				ant->next = aux->next;
			}
		free(aux);
	}
}

void buscaRemoveTodos(Lista p, int y) {
	Lista aux, ant;
	bool ok = FALSE;
	if(p == NULL) {
		printf("A lista esta vazia");
	} else {
		aux = p;
		while(!ok && aux != NULL) {
			if(aux->item != y) {
				ant = aux;
				aux = aux->next	;
			} else {
				aux = aux->next;
				if(aux->item != y) {
					ant->next = aux;
					ok = TRUE;
				}	
			}
		}
	}
}

bool verificarCrescente(Lista L) {
	bool crescendo = TRUE;
	Celula* aux, *aux2;
	if(L == NULL) {
		crescendo = FALSE;
   	} else {
		aux = L;
		while(aux != NULL && crescendo == TRUE) {
			if(aux->next != NULL) {
				aux2 = aux->next;
				if(aux->item <= aux2->item) {
					crescendo = TRUE;
				} else {
					crescendo = FALSE;
				}
			}
			aux = aux->next;
		}
	}
	return crescendo;
}


void trocarCelulas(Lista L, Celula* S, Celula* A) {
	Celula *aux, *antS = NULL, *antA = NULL;
	
	if(L != NULL) {
		aux = L;
		while(aux != NULL && (antS == NULL || antA == NULL)) {
			if(aux->next == S) {
				antS = aux;
			} else if(aux->next == A) {
				antA = aux;
			}
			aux = aux->next;
		}
		
		if(antS != NULL && antA != NULL ) {
			aux = A->next;
			antS->next = A;
			A->next = S->next;
			antA->next = S;
			S->next = aux;
		}
	}
}

	
/*Celula* obterMinimo(Lista L) {
	Celula *minimo, *aux1, *aux2;
	
	if(L != NULL) {
		aux1 = L;
		while(aux1 != NULL) {
			aux2 = aux1->next;
			while( aux2 != NULL) {
				if(aux1->item < aux2->item) {
					minimo = aux1;
				}
				aux2 = aux2->next;
			}
			aux1 = aux1->next;	
		}
	}
	return minimo;	
}*/

Lista copiarLista(Lista L) {
	Lista copia;
	
	copia = L;
	
	return copia;
}

bool verificarIgual(Lista L, Lista P) {
	Lista auxL = NULL, auxP = NULL;
	bool igual = FALSE;
	int tamP = 0, tamL = 0;
	if(L != NULL && P != NULL) {
		auxL = L;
		auxP = P;
		while( auxL != NULL) {
			if(auxL != NULL) {
				tamL++;
				auxL = auxL->next;	
			} 
		}
		while(auxP != NULL) {
			if(auxP != NULL) {
				tamP++;
				auxP = auxP->next;
			}
		}
		if(tamL == tamP) {
			auxL = L;
			while(auxL != NULL && igual == FALSE) {
				auxP = P;
				igual = FALSE; 
				while(auxP != NULL && igual == FALSE) {
					if(auxL->item == auxP->item) {
						igual = TRUE;
					}
					auxP = auxP->next;
				}
				auxL = auxL->next;
				if(auxL != NULL) igual = FALSE;
			}	
		}
	}
	return igual;
}

Lista concatenarListas(Lista A, Lista B) {
	Lista aux, C;
	if(A != NULL && B != NULL) {
		aux = A;
		C = aux;
		while(aux->next != NULL) {
			aux = aux->next;
		}
		aux->next = B;
	}
	return C;
}

Lista copiarVetorLista(int V[]) {
	Lista L, aux;
	int k = MAX_VET;
	int i;
	L = aux;
	for(i = 0; i < k; i++) {
		aux->item = V[i];
		if (i != k-1) aux = aux->next;
		if(i+1 == k) {
			aux->next = NULL;
		}
	}
	return L;
}

void copiarListaVetor( Lista L, int V[]) {
	int k = MAX_VET, i;
	Lista aux = L;
	
	if(L != NULL) {
		for(i = k; i > 0; i--) {
			V[i] = aux->item;
			aux = aux->next;
		}	
	}
}

int obterAlturaCelula(Lista L, Celula* C) {
	int altura;
	bool achou = FALSE;
	Lista aux = L;
	
	if(L != NULL) {
		while(aux != NULL) {
			if(aux == C) {
				achou = TRUE;
				altura = 0;
			} else if(achou) {
				altura++;
			}
			aux = aux->next;
		}	
	}
	return altura;
}

int obterProfundidadeCelula(Lista L, Celula* C) {
	int profundidade;
	bool chegou = FALSE;
	Lista aux;
	
	if( L != NULL) {
		aux = L;
		while(aux != NULL && !chegou) {
			if(aux == C) chegou = TRUE;
			else if(!chegou) {
				profundidade++;
			}
			aux = aux->next;
		}
	}
	return profundidade;
}

Lista inverterLista(Lista L) {
	Lista aux; //proximo
	Lista atual;
	Lista temp; // LISTA TEMPORARIA, QUE SERVE PARA PRESERVAR AS LIGAÇOES QUE AINDA NAO FORAM PREENCHIDAS
	
	if(L != NULL) {
		atual = L;
		aux = L;
		temp = L;
		while(aux != NULL ) {
			temp = temp->next;
			aux->next = atual;
			atual = aux;
			aux = temp;	
		}
		L->next = NULL;
		L = atual;
		
	}
	return L;
}

Lista listaCircular(Lista L) {
	Lista aux, ant;
	
	if(L != NULL) {
		aux = L;
		while(aux != NULL) {
			ant = aux;
			aux = aux->next;
		}
		if(aux == NULL) {
			ant->next = L;
		}
	}
}
////////////////////////////
