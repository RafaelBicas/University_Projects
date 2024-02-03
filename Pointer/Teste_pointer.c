#include "Elemento.h"

void main() {
	
}

//implementações
TipoElemento criarElemento(char *s, char *r) {
	TipoElemento reg;
	reg.nome = s;
	reg.RA = r;
	return reg;
}

TipoElemento criarNovoElemento() {
	TipoElemento novo;
	novo.nome = "MARIA";
	novo.RA = "00000001";
	return novo;
}

TipoElemento criarFantasma() {
	TipoElemento f;
	f.nome = "fantasma";
	f.RA = "00000000";
	return f;
}

void set_Nome(TipoElemento *reg, char *s) {
	reg->nome = s;
}

void set_RA(TipoElemento *reg, char *m) {
	reg->RA = m;
}

char * get_nome(TipoElemento reg) {
	char *s;
	s = reg.nome;
	return s;
}

char get_RA(TipoElemento reg) {
	char *m;
	m = reg.RA;
	return m;
}

void mostrarElemento(TipoElemento reg) {
	printf("nome = %s", reg.nome);
	printf(" RA = %s", reg.RA);
}

