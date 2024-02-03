/* TAD Pilha = pilha de inteiros */
/*
  Arquivo: Pilha.h
  Autor: Lisbete Madsen Barbosa
  Date: 11/10/09 10:58
  Descrição: Define e implementa o tipo de dados Pilha
  que pode guardar até MaxPilha-1 números inteiros positivos. 
  
*/

#include "Booleano.h"
#define MaxPilha 21
#define sinal -1
#define fantasma 0

typedef struct {
      int topo; 			// topo mostra onde está o último item colocado
      int tabela[MaxPilha];
} Pilha;

/* construtores */
void criarPilhaVazia(Pilha *);
/* acesso */
int acessarTopo(Pilha *);
bool verificarPilhaVazia(Pilha *);
bool verificarPilhaCheia(Pilha *);
/* manipulação */
void pushPilha(Pilha *, int);
void popPilha(Pilha *);
void esvaziarPilha(Pilha *);

void criarPilhaVazia(Pilha *ap){
     ap->topo = sinal;
}

int acessarTopo(Pilha *ap){
    int k,x;
    k = ap->topo;
    if(k != sinal) x = ap->tabela[k];
    else x = fantasma;
    return x;
}     

bool verificarPilhaVazia(Pilha *ap){
     bool vazia;
     if (ap->topo == sinal) vazia = TRUE; else vazia = FALSE;
     return vazia;
}

bool verificarPilhaCheia(Pilha *ap){
     bool cheia;
     if (ap->topo == MaxPilha-1) cheia = TRUE; else cheia = FALSE;
     return cheia;
}

void pushPilha(Pilha *a, int x){
     int k;
     k = a->topo;
     if (k < MaxPilha-1) {
     	a->tabela[k+1] = x;
     	a->topo = k+1;
	 }  
}

void popPilha(Pilha *a){
     int k;
     k = a->topo;
     if (a->topo != sinal) a->topo = k-1;
}

void esvaziarPilha(Pilha *a){
	a->topo = sinal;
}

