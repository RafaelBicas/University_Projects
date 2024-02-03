/* TAD Fila */
/*  Arquivo: FilaDinamica.h
    Autor: Lisbete Madsen Barbosa           
    Date:17/10/2012
    Descrição: implementação de uma fila circular de inteiros positivos.
 */



#include <stdlib.h>
#include "Booleano.h"

#define SINAL -1
#define MAXFILA 20
#define FANTASMA 0

typedef struct {
    int inicio;
    int fim;
    int item[MAXFILA];
}c_Fila;

typedef c_Fila* Fila;

/*	A fila pode guardar ate MAXFILA elementos 
O campo inicio indica o local em que se encontra o primeiro da fila
O campo fim indica o local onde se encontra o ultimo da fila 
*/

//-------------------------------Construção------------------------------------------------------------

Fila criarFilaVazia();

//---------------------------------Acesso----------------------------------------------------------

int acessarInicio(Fila);
bool verificarFilaVazia(Fila);
bool verificarFilaCheia(Fila);

//-------------------------------Manipulação----------------------------------------------------------

void pushFila(Fila, int);
void popFila(Fila);
void esvaziarFila(Fila);

//---------------------------------------------------------------------------------------



//------------------------------Construção---------------------------------------------------------
Fila criarFilaVazia() {
    Fila ap;
    ap = (c_Fila *) malloc(sizeof (c_Fila));
    ap->inicio = SINAL;
    ap->fim = SINAL;
    return ap;
}

//------------------------------Acesso---------------------------------------------------------

int acessarInicio(Fila ap) {
    int primeiro, posInicio;
    posInicio = ap->inicio;
    if (posInicio == SINAL) primeiro = FANTASMA;
	else primeiro = ap->item[posInicio];
    return primeiro;
}

bool verificarFilaVazia(Fila ap) {
    int inicio;
    bool vazia;
    inicio = ap->inicio;   vazia = FALSE;
    if (inicio == SINAL)  vazia = TRUE;
    return vazia;
}

bool verificarFilaCheia(Fila ap) {
    int inicio, fim, seguinte;
    bool cheia;
    inicio = ap->inicio;	fim = ap->fim;    cheia = FALSE;
    seguinte = ((fim + 1) % MAXFILA);
    if (seguinte == inicio) cheia = TRUE;
	return cheia;
}

//---------------------------------Manipulação------------------------------------------------------

void pushFila(Fila ap, int numero) {
    int inicio, fim, seguinte;
    inicio = ap->inicio;    fim = ap->fim;    seguinte = ((fim + 1) % MAXFILA);
    if (seguinte != inicio) {
        ap->fim = seguinte;
        ap->item[seguinte] = numero;
        if (inicio == SINAL) ap->inicio = ap->fim;
    }
}

void popFila(Fila ap) {
    int inicio, fim;
    inicio = ap->inicio;
    if (inicio != SINAL) {
        fim = ap->fim;
        if (inicio == fim) {
            ap->inicio = SINAL;
            ap->fim = SINAL;
        } 
		else ap->inicio = (inicio + 1) % MAXFILA;
    }
}

void esvaziarFila(Fila ap){
    ap->inicio=SINAL;
    ap->fim=SINAL;
}



