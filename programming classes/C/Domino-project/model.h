typedef enum TiposDeJogador { Pessoa, Computador } TiposDeJogador;
typedef enum PossibilidadesDeJogo { Nenhuma, Esquerda, Direita, Ambas } PossibilidadesDeJogo;
typedef enum Lado { Esquerdo, Direito } Lado;

typedef struct Peca
{
	int valorEsquerda;
	int valorDireita;
} Peca;

typedef struct MonteDePecas
{
	Peca* pecas;
	int numeroDePecas;
} MonteDePecas;

typedef struct Jogador
{
	MonteDePecas mao;
	TiposDeJogador tipo;
	char nome[21];
} Jogador;

#define VALORMAXIMODOLADO 6

void embaralharMonte(MonteDePecas* monte);
void ajustarOrdemDosJogadores(Jogador** jogadores, int numeroDeJogadores);
Peca encontrarMaiorPecaComLadosIguais(MonteDePecas mao);
int encontrarIndiceDaMaiorPecaComLadosIguais(MonteDePecas mao);
Peca comprarPeca(MonteDePecas* monte, Jogador* jogador);
void removerPecaDoMonte(MonteDePecas* monte, int indexDaPeca);
int obterTotalDePecasNoJogo();
void alocarMonteDeCompra(MonteDePecas* monte);
void distribuirPecas(Jogador* jogadores, int numeroDeJogadores, MonteDePecas* monteDeCompra);
PossibilidadesDeJogo checarSePecaPodeSerJogada(Peca peca, MonteDePecas campoDeJogo);
void ajustarPecaAJogar(Peca* peca, Lado ladoAJogar, MonteDePecas campoDeJogo);
bool checarSeAMaiorPecaSeraJogada(MonteDePecas mao, Peca pecaAJogar);