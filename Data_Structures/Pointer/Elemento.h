#ifndef _Elemento_h
	#define _Elemento_h
	
typedef struct {
	char * nome;
	char * RA;
}TipoElemento;

TipoElemento criarElemento(char *, char *);
TipoElemento criarNovoElemento();
TipoElemento criarFantasma();
void set_Nome(TipoElemento *, char *);
void set_RA(TipoElemento *, char *);
char * get_nome(TipoElemento);
char get_RA(TipoElemento);
void mostrarElemento(TipoElemento reg);

#endif
