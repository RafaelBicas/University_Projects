#include <stdbool.h>
#include "model.h"

int obterNumeroDeJogadores(int totalDePecasNoJogo);
void obterNomeDoJogador(int indexDoJogador, char* nome);
TiposDeJogador obterTipoDoJogador(char nome[]);
void imprimirPeca(Peca peca);
void imprimirMonte(MonteDePecas monte);
int menu();
void pausar();
void imprimirTodasAsPecas(MonteDePecas monte);
bool perguntarJogarNovamente();
int menuTurnoPessoa(Jogador jogador, int numeroDePecasParaCompra, MonteDePecas campoDeJogo);
bool mensagemCompraPeca(Peca pecaComprada);
int menuJogarPeca(MonteDePecas mao, bool mostrarPecas, int indiceDaPecaAPedir);
Lado obterLadoAJogarPeca();
void mensagemFimDeJogo(char nomeJogadorVencedor[], MonteDePecas campoDeJogo);