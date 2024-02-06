#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "view.h"

int obterNumeroDeJogadores(int totalDePecasNoJogo)
{
	int escolha = 0, maximoDeJogadores = totalDePecasNoJogo / 7;
	do
	{
		printf("Digite o número de jogadores (2 - %d): ", maximoDeJogadores);
		scanf("%d", &escolha);
		setbuf(stdin, NULL);
		
		if (escolha < 2 || escolha > maximoDeJogadores) { printf("Opção inválida!\n\n"); }
	}
	while (escolha < 2 || escolha > maximoDeJogadores);
	
	printf("\n");
	return escolha;
}

void obterNomeDoJogador(int indexDoJogador, char* nome)
{
	printf("Digite o nome do %dº jogador: ", indexDoJogador + 1);
	scanf("%s", nome);
	setbuf(stdin, NULL);
}

TiposDeJogador obterTipoDoJogador(char nome[])
{
	TiposDeJogador tipo;
	int escolha = 0;
	do
	{
		printf("Digite 1 para o jogador %s ser uma pessoa, ou 2 para ser controlado pelo computador: ", nome);
		scanf("%d", &escolha);
		setbuf(stdin, NULL);

		if (escolha < 1 || escolha > 2) { printf("Opção inválida!\n\n"); }
	} while (escolha < 1 || escolha > 2);

	if (escolha == 1) { tipo = Pessoa; } else { tipo = Computador; }

	printf("\n");
	return tipo;
}

void imprimirPeca(Peca peca)
{
	printf("%d%d", peca.valorEsquerda, peca.valorDireita);
}

void imprimirMonte(MonteDePecas monte)
{
	if (monte.numeroDePecas > 0)
	{
		imprimirPeca(monte.pecas[0]);
		for (int i = 1; i < monte.numeroDePecas; i++)
		{
			printf("-");
			imprimirPeca(monte.pecas[i]);
		}
		printf("\n\n");
	}
	else
	{
		printf("Vazio.\n\n");
	}
}

int menu()
{
	system("clear||cls");
	printf("============================================\n");
	printf("===================Dominó===================\n");
	printf("============================================\n\n");
	printf("1 - Iniciar jogo\n");
	printf("2 - Visualizar todas as peças do jogo\n");
	printf("3 - Configurações\n");
	printf("4 - Sair\n\n");
	
	int escolha = 0;
	do
	{
		printf("Digite a opção escolhida: ");
		scanf("%d", &escolha);
		setbuf(stdin, NULL);
		
		if (escolha < 1 || escolha > 4) { printf("Opção inválida!\n\n"); }
	}
	while (escolha < 1 || escolha > 4);
	
	printf("\n");
	return escolha;
}

void pausar()
{
	printf("Pressione enter para continuar...");
	getchar();
	setbuf(stdin, NULL);
}

void imprimirTodasAsPecas(MonteDePecas monte)
{
	printf("Peças existentes no jogo, de acordo com as configurações atuais:\n");
	imprimirMonte(monte);
	pausar();
}

bool perguntarJogarNovamente()
{
	bool jogarNovamente = false;
	
	int escolha = 0;
	do
	{
		printf("Digite 1 para jogar novamente com os mesmos jogadores, ou 2 para voltar ao menu: ");
		scanf("%d", &escolha);
		setbuf(stdin, NULL);

		if (escolha < 1 || escolha > 2) { printf("Opção inválida!\n\n"); }
	}
	while (escolha < 1 || escolha > 2);

	if (escolha == 1) { jogarNovamente = true; }

	printf("\n");
	return jogarNovamente;
}

int menuTurnoPessoa(Jogador jogador, int numeroDePecasParaCompra, MonteDePecas campoDeJogo)
{
	system("clear||cls");
	printf("Turno de %s\n\n", jogador.nome);
	printf("Campo de jogo:\n");
	imprimirMonte(campoDeJogo);
	printf("Peças no monte de compra: %d\n\n", numeroDePecasParaCompra);
	printf("Sua mão:\n");
	imprimirMonte(jogador.mao);
	////////////////imprimir a quantidade de peças na mão de cada jogador

	printf("1 - Jogar peça\n");
	printf("2 - Comprar peça\n");
	printf("3 - Salvar jogo\n\n");

	int escolha = 0;
	do
	{
		printf("Digite a opção escolhida: ");
		scanf("%d", &escolha);
		setbuf(stdin, NULL);

		if (escolha < 1 || escolha > 3) { printf("Opção inválida!\n\n"); }
	}
	while (escolha < 1 || escolha > 3);

	printf("\n");
	return escolha;
}

bool mensagemCompraPeca(Peca pecaComprada)
{
	bool passarVez = false;
	if (pecaComprada.valorDireita == -1)
	{
		printf("Não há mais peças disponíveis para compra.\n");
		int escolha = 0;
		do
		{
			printf("Digite 1 para passar a vez, ou 2 para continuar jogando: ");
			scanf("%d", &escolha);
			setbuf(stdin, NULL);

			if (escolha < 1 || escolha > 2) { printf("Opção inválida!\n\n"); }
		}
		while (escolha < 1 || escolha > 2);

		if (escolha == 1) { passarVez = true; }
	}
	else
	{
		printf("Você comprou a peça ");
		imprimirPeca(pecaComprada);
		printf(".\n");
		pausar();
	}
	
	return passarVez;
}

int menuJogarPeca(MonteDePecas mao, bool pedirNovaPeca, int indiceDaPecaAPedir)
{
	if (pedirNovaPeca)
	{
		if (indiceDaPecaAPedir == -1)
		{
			printf("Essa peça não pode ser jogada, escolha uma outra peça ou compre se não tiver nenhuma que possa ser jogada.\n");
		}
		else
		{
			printf("Você precisa começar jogando a peça ");
			imprimirPeca(mao.pecas[indiceDaPecaAPedir]);
			printf(".\n");
		}
	}
	else
	{
		printf("Suas peças:\n");
		for (int i = 0; i < mao.numeroDePecas; i++)
		{
			printf("%d. ", i + 1);
			imprimirPeca(mao.pecas[i]);
			printf("\n");
		}
	}

	int escolha = 0;
	do
	{
		printf("Digite o número da peça a jogar, ou 0 para cancelar: ");
		scanf("%d", &escolha);
		setbuf(stdin, NULL);

		if (escolha < 0 || escolha > mao.numeroDePecas) { printf("Opção inválida!\n\n"); }
	}
	while (escolha < 0 || escolha > mao.numeroDePecas);

	printf("\n");
	return escolha - 1; //retorna -1 se escolheu cancelar (0 - 1 = -1), ou índice da peça na lista caso contrário
}

Lado obterLadoAJogarPeca()
{
	printf("Esta peça pode ser jogada em qualquer um dos lados. ");
	int escolha = 0;
	do
	{
		printf("Escolha em qual lado você quer jogá-la (1 - Esquerda, 2 - Direita): ");
		scanf("%d", &escolha);
		setbuf(stdin, NULL);

		if (escolha < 1 || escolha > 2) { printf("Opção inválida!\n\n"); }
	}
	while (escolha < 1 || escolha > 2);

	if (escolha == 1) { return Esquerdo; } else { return Direito; }
}

void mensagemFimDeJogo(char nomeJogadorVencedor[], MonteDePecas campoDeJogo)
{
	system("clear||cls");
	printf("Campo de jogo:\n");
	imprimirMonte(campoDeJogo);
	printf("Fim de jogo, %s venceu!\n\n", nomeJogadorVencedor);
}