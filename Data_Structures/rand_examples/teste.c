/*#include <stdlib.h>
#include <stdio.h>

void main() {
	
	int a = 0, b = 0, c = 0, d = 0;
	int k = 0;
	
	for(k = 1; k <= 5; k++) {
		a = rand();
		
		printf("a = %d\n", a);
	}
	for(k = 1; k <= 5; k++) {
		b = rand();
		
		printf("b = %d\n", b);
	}
	for(k = 1; k <= 5; k++) {
		c = rand();
		
		printf("c = %d\n", c);
	}
	for(k = 1; k <= 5; k++) {
		d = rand();
		
		printf("d = %d\n", d);
	}
}
/////////////////////////////////////////////////////////////

void main() {
	
	int a = 0;
	int b = 0;
	int y = 0;
	
	for(y = 1; y <= 5; y++) {
		a = funcaoA(y);
		b = funcaoA(y);
		printf("%d", a);
		printf("\n%d", b);
		
	}	
}

int funcaoA (int y) {
	
	int e = 0;
	
	srand(y);
	e = rand();

	return e;
}

// testeRand.c
void main(){
	
	int i,e;
	
	srand(time(NULL));
	for(i = 1; i <= 5; i++) { 
		e = rand() / 100;
		printf(" %d ",e); 
	}
	
	printf("\n\n");
	system("PAUSE");
}*/

#include <stdio.h>
#include <stdlib.h>
//#include "Booleano.h"
#define Maximo 6
#define Fantasma 0


typedef struct {
	int tamanho;
	int vetor[Maximo];
}ListaInt;


ListaInt construirListaRam(int ,int ,int );

void main() {
	ListaInt a;
	int n = 3, item = 2, p = 2, obterItem, buscarItem = 3, posicao, novo = 7, tamanhoFibonacci = 5, x = 100, y = 300, i;
	//bool teste;
	
	/*a = criarLista();
	mostrarLista(a);
	n = obterTamanho(a);
	
	teste = verificarListaVazia(a);
	printf("\nteste(0/1): %d\n", teste);
	a = construirLista(n, item);
	mostrarLista(a);
	
	obterItem = obterElemento(a, p);
	printf("\nItem Obtido: %d\n", obterItem);
	
	posicao = buscarElemento(a, buscarItem);
	printf("\nposicao do item buscado: %d\n", posicao);
	
	a = inserir( a, novo);
	printf("\nNova Lista:\n");
	mostrarLista(a);
	
	a = construirListaFibonacci(a, tamanhoFibonacci);
	mostrarLista(a);*/
	
	a = construirListaRam(x,y,n);
	
	//for(i = 0; i < n; i++) {
	//	printf("\n\nrand = %d", a.vetor[i]);
	//}
	
}

ListaInt construirListaRam(int x,int y,int n) {
	int i;
	ListaInt a;
	int valor;
	float v1;
	
	srand((unsigned)time(NULL));
	for(i = 0; i < n; i++) {
		valor = rand();
		v1 = (valor/(32768));
		printf("RAND_MAX = %d", RAND_MAX);
		printf("v1 = %d ", v1);
		//valor = (y-x+1) * ) + x;
		printf("loop = %d\n", valor);
		//a.vetor[i] =  a.vetor[i];
		//a.vetor[i] = a.vetor[i] + x;
	}
	
	return a;
}



