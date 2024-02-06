#include <stdbool.h>
#include "view.h"

void executarPrograma();
bool processarOpcao(int opcao);
void jogo();
bool turnos(Jogador* jogadores, int numeroDeJogadores, MonteDePecas* monteDeCompra, MonteDePecas* campoDeJogo);
void inicializarJogadores(Jogador** jogadores, int* numeroDeJogadores, int totalDePecasNoJogo);
void reiniciarMesa(Jogador* jogadores, int numeroDeJogadores, MonteDePecas* monteDeCompra, MonteDePecas* campoDeJogo);
bool executarTurno(Jogador* jogador, MonteDePecas* monteDeCompra, MonteDePecas* campoDeJogo, bool primeiroTurno);
bool turnoPessoa(Jogador* jogador, MonteDePecas* monteDeCompra, MonteDePecas* campoDeJogo, bool primeiroTurno);
bool processarOpcaoTurnoPessoa(int opcao, Jogador* jogador, MonteDePecas* monteDeCompra, MonteDePecas* campoDeJogo, bool primeiroTurno);
bool turnoComputador(Jogador* jogador, MonteDePecas* monteDeCompra, MonteDePecas* campoDeJogo, bool primeiroTurno);