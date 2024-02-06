#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
#include "model.h"

void embaralharMonte(MonteDePecas* monte)
{
	MonteDePecas temp = { NULL, monte->numeroDePecas };
	temp.pecas = malloc(sizeof(Peca) * monte->numeroDePecas);
	for (int i = 0; i < monte->numeroDePecas; i++)
	{
		temp.pecas[i] = monte->pecas[i];
	}

	srand(time(NULL));
	int index = 0;
	while (temp.numeroDePecas > 0)
	{
		//adicionar uma peça aleatória da lista temporária para a original
		int posicao = rand() % temp.numeroDePecas;
		monte->pecas[index++] = temp.pecas[posicao];

		//remover a peça adicionada da lista temporária
		removerPecaDoMonte(&temp, posicao);
	}

	free(temp.pecas);
}

void ajustarOrdemDosJogadores(Jogador** jogadores, int numeroDeJogadores)
{
	Jogador* temp;
	temp = malloc(sizeof(Jogador) * numeroDeJogadores);
	for (int i = 0; i < numeroDeJogadores; i++)
	{
		temp[i] = (*jogadores)[i];
	}

	//randomizar ordem dos jogadores
	srand(time(NULL));
	int index = 0, i = numeroDeJogadores;
	while (i > 0)
	{
		//adicionar um jogador aleatório da lista temporária para a original
		int posicao = rand() % i;
		(*jogadores)[index++] = temp[posicao];

		//remover o jogador adicionado da lista temporária
		for (int j = posicao; j < i - 1; j++)
		{
			temp[j] = temp[j + 1];
		}

		i--;
	}

	//colocar jogador com a maior peça de lados com valor igual em primeiro (66, 55, 44, ...)

	//encontrar jogador com a maior peça de lados com valor igual
	Peca maiorPeca = { .valorEsquerda = -1, .valorDireita = -1 };
	int indiceDoJogadorComAPeca = -1;
	for (int i = 0; i < numeroDeJogadores && (maiorPeca.valorEsquerda != 6 || maiorPeca.valorDireita != 6); i++)
	{
		Peca pecaEncontrada = encontrarMaiorPecaComLadosIguais((*jogadores)[i].mao);
		if (pecaEncontrada.valorEsquerda > maiorPeca.valorEsquerda && pecaEncontrada.valorDireita > maiorPeca.valorDireita)
		{
			maiorPeca = pecaEncontrada;
			indiceDoJogadorComAPeca = i;
		}
	}
	
	//colocar jogador com a maior peça no topo, mas não fazer nada se nenhuma das peças de valor igual tiver sido encontrada, ou o jogador com a peça encontrada já for o primeiro
	if (maiorPeca.valorDireita != -1 && indiceDoJogadorComAPeca > 0)
	{
		Jogador aux = (*jogadores)[indiceDoJogadorComAPeca];
		//deslocar os jogadores da primeira posição até a posição do jogador encontrado, removendo-o desta posição e liberando a primeira
		for (int k = 0; k < indiceDoJogadorComAPeca; k++)
		{
			(*jogadores)[k + 1] = (*jogadores)[k];
		}
		//colocar o jogador encontrado na primeira posição, que já foi liberada
		(*jogadores)[0] = aux;
	}
	
	free(temp);
}

Peca encontrarMaiorPecaComLadosIguais(MonteDePecas mao)
{
	Peca pecaEncontrada = { .valorEsquerda = -1, .valorDireita = -1 };
	
	for (int i = 0; i < mao.numeroDePecas; i++)
	{
		if (mao.pecas[i].valorEsquerda == mao.pecas[i].valorDireita && mao.pecas[i].valorEsquerda > pecaEncontrada.valorEsquerda)
		{
			pecaEncontrada = mao.pecas[i];
		}
	}

	return pecaEncontrada;
}

int encontrarIndiceDaMaiorPecaComLadosIguais(MonteDePecas mao)
{
	Peca pecaEncontrada = { .valorEsquerda = -1, .valorDireita = -1 };
	int index = -1;
	
	for (int i = 0; i < mao.numeroDePecas; i++)
	{
		if (mao.pecas[i].valorEsquerda == mao.pecas[i].valorDireita && mao.pecas[i].valorEsquerda > pecaEncontrada.valorEsquerda)
		{
			pecaEncontrada = mao.pecas[i];
			index = i;
		}
	}

	return index;
}


Peca comprarPeca(MonteDePecas* monte, Jogador* jogador)
{
	Peca pecaComprada = { .valorDireita = -1 };
	if (monte->numeroDePecas > 0)
	{
		pecaComprada = monte->pecas[0]; //0 porque a peça que será comprada do monte é a primeira
		removerPecaDoMonte(monte, 0); //0 porque é a primeira peça que será removida do monte, já que ela foi comprada

		jogador->mao.pecas[jogador->mao.numeroDePecas] = pecaComprada;
		jogador->mao.numeroDePecas++;
	}

	return pecaComprada;
}

void removerPecaDoMonte(MonteDePecas* monte, int indexDaPeca)
{
	for (int i = indexDaPeca; i < monte->numeroDePecas - 1; i++)
	{
		monte->pecas[i] = monte->pecas[i + 1];
	}

	monte->numeroDePecas--;
}

void inverterPeca(Peca* peca)
{
	int aux = peca->valorEsquerda;
	peca->valorEsquerda = peca->valorDireita;
	peca->valorDireita = aux;
}

int obterTotalDePecasNoJogo()
{
	int numeroDePecas = 0;
	for (int i = 0; i <= VALORMAXIMODOLADO; i++)
	{
		numeroDePecas += i + 1;
	}

	return numeroDePecas;
}

void alocarMonteDeCompra(MonteDePecas* monteDeCompra)
{
	//calcular número de peças, de acordo com o valor máximo da peça nas configurações
	monteDeCompra->numeroDePecas = obterTotalDePecasNoJogo();

	free(monteDeCompra->pecas);
	monteDeCompra->pecas = malloc(sizeof(Peca) * monteDeCompra->numeroDePecas);

	//inserir todas as peças, de acordo com o valor máximo da peça nas configurações
	int index = 0;
	for (int i = 0; i <= VALORMAXIMODOLADO; i++)
	{
		for (int j = i; j <= VALORMAXIMODOLADO; j++)
		{
			monteDeCompra->pecas[index].valorEsquerda = i;
			monteDeCompra->pecas[index++].valorDireita = j;
		}
	}
}

void distribuirPecas(Jogador* jogadores, int numeroDeJogadores, MonteDePecas* monteDeCompra)
{
	for (int i = 0; i < numeroDeJogadores; i++)
	{
		for (int j = 0; j < 7; j++) //7 peças são compradas ao início do jogo
		{
			comprarPeca(monteDeCompra, &jogadores[i]);
		}
	}
}

PossibilidadesDeJogo checarSePecaPodeSerJogada(Peca peca, MonteDePecas campoDeJogo)
{
	if (campoDeJogo.numeroDePecas == 0) { return Ambas; }

	int valorEsquerdaDoCampo = campoDeJogo.pecas[0].valorEsquerda; //o valor no canto esquerdo do campo é o valor à esquerda da primeira peça
	int valorDireitaDoCampo = campoDeJogo.pecas[campoDeJogo.numeroDePecas - 1].valorDireita; //o valor no canto direito do campo é o valor à direita da última peça
	bool podeEsquerda = false, podeDireita = false;
	PossibilidadesDeJogo retorno;
	
	if (peca.valorEsquerda == valorEsquerdaDoCampo || peca.valorDireita == valorEsquerdaDoCampo)
	{
		podeEsquerda = true;
	}

	if (peca.valorEsquerda == valorDireitaDoCampo || peca.valorDireita == valorDireitaDoCampo)
	{
		podeDireita = true;
	}

	if (podeEsquerda && podeDireita) { retorno = Ambas; }
	else if (podeEsquerda) { retorno = Esquerda; }
	else if (podeDireita) { retorno = Direita; }
	else { retorno = Nenhuma; }

	return retorno;
}

void ajustarPecaAJogar(Peca* peca, Lado ladoAJogar, MonteDePecas campoDeJogo)
{
	int valorEsquerdaDoCampo = campoDeJogo.pecas[0].valorEsquerda; //o valor no canto esquerdo do campo é o valor à esquerda da primeira peça
	int valorDireitaDoCampo = campoDeJogo.pecas[campoDeJogo.numeroDePecas - 1].valorDireita; //o valor no canto direito do campo é o valor à direita da última peça

	//se o valor da peça que for jogada não for compatível com a ponta em que ela for jogada, é o outro valor que é compatível, então precisamos invertê-la
	if ((ladoAJogar == Esquerdo && peca->valorDireita != valorEsquerdaDoCampo) || (ladoAJogar == Direito && peca->valorEsquerda != valorDireitaDoCampo))
	{
		inverterPeca(peca);
	}
}

bool checarSeAMaiorPecaSeraJogada(MonteDePecas mao, Peca pecaAJogar)
{
	Peca maiorPeca = encontrarMaiorPecaComLadosIguais(mao);
	if (maiorPeca.valorDireita > -1 && (pecaAJogar.valorEsquerda != maiorPeca.valorEsquerda || pecaAJogar.valorDireita != maiorPeca.valorDireita))
	{
		return false;
	}
	else
	{
		return true;
	}
}