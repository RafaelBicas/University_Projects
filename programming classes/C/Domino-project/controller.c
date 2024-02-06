#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include "controller.h"

void executarPrograma()
{
	bool sair;
	do
	{
		int opcao = menu();
		sair = processarOpcao(opcao);
	}
	while(!sair);
}

bool processarOpcao(int opcao)
{
	bool sair = false;
	switch (opcao)
	{
		case 1: //iniciar jogo
			jogo();
			break;

		case 2: //mostrar todas as peças
		{
			MonteDePecas monte = { .pecas = NULL, .numeroDePecas = 0 };
			alocarMonteDeCompra(&monte);
			imprimirTodasAsPecas(monte);
			break;
		}

		case 3: //configurações
			break;

		case 4: //sair
			sair = true;
			break;
	}
	return sair;
}

void jogo()
{
	MonteDePecas monteDeCompra = { .pecas = NULL, .numeroDePecas = 0 };
	MonteDePecas campoDeJogo = { .pecas = NULL, .numeroDePecas = 0 };
	Jogador* jogadores = NULL;
	int numeroDeJogadores = 0;

	inicializarJogadores(&jogadores, &numeroDeJogadores, obterTotalDePecasNoJogo());

	bool jogarNovamente;
	do
	{
		reiniciarMesa(jogadores, numeroDeJogadores, &monteDeCompra, &campoDeJogo);
		embaralharMonte(&monteDeCompra);
		distribuirPecas(jogadores, numeroDeJogadores, &monteDeCompra);
		ajustarOrdemDosJogadores(&jogadores, numeroDeJogadores);
		jogarNovamente = turnos(jogadores, numeroDeJogadores, &monteDeCompra, &campoDeJogo);
	}
	while (jogarNovamente);

	for (int i = 0; i < numeroDeJogadores; i++)
	{
		free(jogadores[i].mao.pecas);
	}
	free(jogadores);
	free(monteDeCompra.pecas);
	free(campoDeJogo.pecas);
}

bool turnos(Jogador* jogadores, int numeroDeJogadores, MonteDePecas* monteDeCompra, MonteDePecas* campoDeJogo)
{
	Jogador jogadorVencedor;
	int turnosPassados = 0;
	
	bool fimDeJogo = false;
	while (!fimDeJogo)
	{
		for (int i = 0; i < numeroDeJogadores && !fimDeJogo; i++)
		{
			fimDeJogo = executarTurno(&jogadores[i], monteDeCompra, campoDeJogo, turnosPassados == 0);
			if (fimDeJogo) { jogadorVencedor = jogadores[i]; }
			turnosPassados++;
		}
	}

	mensagemFimDeJogo(jogadorVencedor.nome, *campoDeJogo);

	return perguntarJogarNovamente();
}

void inicializarJogadores(Jogador** jogadores, int* numeroDeJogadores, int totalDePecasNoJogo)
{
	*numeroDeJogadores = obterNumeroDeJogadores(totalDePecasNoJogo);

	*jogadores = malloc(sizeof(Jogador) * *numeroDeJogadores);
	for (int i = 0; i < *numeroDeJogadores; i++)
	{
		(*jogadores)[i].mao.pecas = malloc(sizeof(Peca) * totalDePecasNoJogo);
		(*jogadores)[i].mao.numeroDePecas = 0;
		obterNomeDoJogador(i, (*jogadores)[i].nome);
		(*jogadores)[i].tipo = obterTipoDoJogador((*jogadores)[i].nome);
	}
}

void reiniciarMesa(Jogador* jogadores, int numeroDeJogadores, MonteDePecas* monteDeCompra, MonteDePecas* campoDeJogo)
{
	for (int i = 0; i < numeroDeJogadores; i++)
	{
		jogadores[i].mao.numeroDePecas = 0;
	}

	alocarMonteDeCompra(monteDeCompra);

	campoDeJogo->numeroDePecas = 0;
	free(campoDeJogo->pecas);
	campoDeJogo->pecas = malloc(sizeof(Peca) * monteDeCompra->numeroDePecas); //neste momento o monte de compra contém todas as peças, então é possível utilizar sua variável de número de peças como quantidade total de peças no jogo
}

bool executarTurno(Jogador* jogador, MonteDePecas* monteDeCompra, MonteDePecas* campoDeJogo, bool primeiroTurno)
{
	if (jogador->tipo == Pessoa) { return turnoPessoa(jogador, monteDeCompra, campoDeJogo, primeiroTurno); } else { return turnoComputador(jogador, monteDeCompra, campoDeJogo, primeiroTurno); }
}

bool turnoPessoa(Jogador* jogador, MonteDePecas* monteDeCompra, MonteDePecas* campoDeJogo, bool primeiroTurno)
{
	bool fimDoTurno;
	do
	{
		int opcao = menuTurnoPessoa(*jogador, monteDeCompra->numeroDePecas, *campoDeJogo);
		fimDoTurno = processarOpcaoTurnoPessoa(opcao, jogador, monteDeCompra, campoDeJogo, primeiroTurno);
	}
	while (!fimDoTurno);

	return jogador->mao.numeroDePecas == 0; //se não há peças na mão do jogador, ele ganhou a partida, e portanto retornamos positivo para encerrá-la; caso contrário, retornamos falso, e a partida continua
}

bool processarOpcaoTurnoPessoa(int opcao, Jogador* jogador, MonteDePecas* monteDeCompra, MonteDePecas* campoDeJogo, bool primeiroTurno)
{
	bool fimDoTurno = false;
	switch (opcao)
	{
		case 1: //jogar peça
		{
			PossibilidadesDeJogo possibilidade;
			int pecaEscolhida, indiceDaPecaAPedir = -1;
			bool pedirNovaPeca = false;
			do
			{
				pecaEscolhida = menuJogarPeca(jogador->mao, pedirNovaPeca, indiceDaPecaAPedir);
				indiceDaPecaAPedir = -1;
				if (pecaEscolhida == -1)
				{
					pedirNovaPeca = false;
				}
				else
				{
					if (primeiroTurno)
					{
						pedirNovaPeca = !checarSeAMaiorPecaSeraJogada(jogador->mao, jogador->mao.pecas[pecaEscolhida]);
						if (pedirNovaPeca) { indiceDaPecaAPedir = encontrarIndiceDaMaiorPecaComLadosIguais(jogador->mao); }
					}
					else
					{
						possibilidade = checarSePecaPodeSerJogada(jogador->mao.pecas[pecaEscolhida], *campoDeJogo);
						pedirNovaPeca = possibilidade == Nenhuma;
					}
				}
			}
			while (pedirNovaPeca == true);
			
			if (pecaEscolhida > -1)
			{
				Lado ladoAJogar;
				if (campoDeJogo->numeroDePecas > 0) //não faz sentido ajustar a peça se ainda não houver peças no campo
				{
					if (possibilidade == Esquerda) { ladoAJogar = Esquerdo; }
					else if (possibilidade == Direita) { ladoAJogar = Direito; }
					else { ladoAJogar = obterLadoAJogarPeca(); } //já que a peça com certeza pode ser jogada, se a possibilidade não é esquerda nem direita, é ambas, e portanto o jogador pode escolher onde quer jogar
					ajustarPecaAJogar(&jogador->mao.pecas[pecaEscolhida], ladoAJogar, *campoDeJogo);
				}
				else
				{
					ladoAJogar = Direito; //se não há peças no campo, não há realmente um lado para jogar, então qualquer valor dá o mesmo resultado. utilizamos então o lado direito, pois nesse caso o esforço computacional para adicionar a peça será menor
				}

				if (ladoAJogar == Esquerdo)
				{
					//adicionar peça ao lado esquerdo do campo
					for (int i = campoDeJogo->numeroDePecas; i > 0; i--)
					{
						campoDeJogo->pecas[i] = campoDeJogo->pecas[i - 1];
					}
					campoDeJogo->pecas[0] = jogador->mao.pecas[pecaEscolhida];
				}
				else //se não é esquerdo, é direito
				{
					//adicionar peça ao lado direito do campo
					campoDeJogo->pecas[campoDeJogo->numeroDePecas] = jogador->mao.pecas[pecaEscolhida];
				}
				campoDeJogo->numeroDePecas++;

				removerPecaDoMonte(&jogador->mao, pecaEscolhida);
				fimDoTurno = true;
			}
			break;
		}
		case 2: //comprar peça
			fimDoTurno = mensagemCompraPeca(comprarPeca(monteDeCompra, jogador));
			break;
		case 3: //salvar jogo

			break;
	}

	return fimDoTurno;
}

bool turnoComputador(Jogador* jogador, MonteDePecas* monteDeCompra, MonteDePecas* campoDeJogo, bool primeiroTurno)
{
	//debug temporário, não ficará interface aqui
	printf("Turno do computador. Acabou? ");
	int i = 0;
	scanf("%d", &i);
	if (i == 1) { return true; }
	return false;
}