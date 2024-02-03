#include <stdio.h>
#include "FilaDinamica.h"
#include "Pilha.h"

void inverterFila(Fila);
void inverterPilha(Pilha *);
void mostrarPilha(Pilha);
void mostrarFila(Fila);
void retirarK(Fila, int);

void main() {
	Fila F = criarFilaVazia();
	Pilha S;
	criarPilhaVazia(&S);
	
	pushFila(F, 3);
	pushFila(F, 5);
	pushFila(F, 7);
	mostrarFila(F);
	printf("\n");
	inverterFila(F);
	retirarK(F, 2);
	mostrarFila(F);
	
} 


void inverterFila(Fila F) {
	Pilha S;
	criarPilhaVazia(&S);
	int numero;
	while(!verificarFilaVazia(F)) {
		numero = acessarInicio(F);
		popFila(F);	
		pushPilha(&S, numero);
		//printf("%d\n", numero);
	}
	
	//printf("\n\n------INVERTIDA------\n")
	while(!verificarPilhaVazia(&S)) {
		numero = acessarTopo(&S);
		popPilha(&S);
		pushFila(F, numero);
		//printf("%d\n", numero);
	}
}

void inverterPilha(Pilha *S) {
	Fila F = criarFilaVazia();
	int numero;
	
	while(!verificarPilhaVazia(S)) {
		numero = acessarTopo(S);
		popPilha(S);
		pushFila(F, numero);
	}
	while(!verificarFilaVazia(F)) {
		numero = acessarInicio(F);
		popFila(F);	
		pushPilha(S, numero);
	}
}

void retirarK(Fila F, int num) {
	Fila aux = criarFilaVazia();
	int numero, pos = 0;
	
	while(!verificarFilaVazia(F)) {
			numero = acessarInicio(F);
			pushFila(aux, numero);
			popFila(F);
	}
	while(!verificarFilaVazia(aux)) {
		num = acessarInicio(aux);
		if(pos != num) {
			pushFila(F, num);
			printf("%d ", num);	
		}
		popFila(aux);
	}
}

void mostrarPilha(Pilha S) {
	Pilha aux;
	int num;
	// construtor
	criarPilhaVazia(&aux);
	
	//codigo
	if(verificarPilhaVazia(&S)) {
		printf("Pilha Vazia!!");
	} else {
		while(!verificarPilhaVazia(&S)) {
			num = acessarTopo(&S);
			pushPilha(&aux, num);
			popPilha(&S);
		}
		while(!verificarPilhaVazia(&aux)) {
			num = acessarTopo(&aux);
			pushPilha(&S, num);
			popPilha(&aux);
			num = acessarTopo(&S);
			printf("%d\n", num);
		}
	}
}

void mostrarFila(Fila F) {
	Fila aux = criarFilaVazia();
	int num;
	
	//codigo
	if(verificarFilaVazia(F)) {
		printf("Pilha Vazia!!");
	} else {
		while(!verificarFilaVazia(F)) {
			num = acessarInicio(F);
			pushFila(aux, num);
			printf("%d ", num);
			popFila(F);
		}
		while(!verificarFilaVazia(aux)) {
			num = acessarInicio(aux);
			pushFila(F, num);
			popFila(aux);
		}
	}
}
