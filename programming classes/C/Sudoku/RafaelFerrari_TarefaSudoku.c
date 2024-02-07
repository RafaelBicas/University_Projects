/* SUDOKU  - técnica single */
/*
  Arquivo: RafaelFerrari_TarefaSudoku.c
  Autor: Fernando Westphal Fonseca, Rafael Pedro Bicas Ferrari
  Date: 21/04/19
  Descrição: Leitura de um arquivo texto e armazenamento em uma matriz de caracteres
  aplicação da técnica single no Sudoku
*/

#include <stdio.h>
#include <string.h>
#include <stdio.h>

// INTERFACE

void LerDoArquivo(unsigned char[9][9]);
void mostrar(unsigned char[9][9]);
void mostrarPossiveis(unsigned char [9][9], unsigned char P[3][3], int, int);
// esta função mostra na tela a lista de valores possíveis para uma determinada cela (i,j)
// pertencente à matriz Sudoku

int main(){
       unsigned char matrizSudoku[9][9], P[3][3];
       int lin = 3, col = 3;
	   LerDoArquivo(matrizSudoku);
       mostrar(matrizSudoku);
       printf("\n\n\n");
       printf("\n");
       mostrarPossiveis(matrizSudoku, P, lin, col);
       return 0;
}

// IMPLEMENTAÇÕES
void LerDoArquivo(unsigned char matrizSudoku[9][9]){
    unsigned char linha[9]; // o array linha é usado como variável de entrada na função fscanf
    FILE *entrada;          // entrada é a referência para o arquivo de entrada
    int i,lin,nlinhas,k;
	entrada = fopen("SUDOKU.txt","r");  // o arquivo SUDOKU.txt pode conter até 9 linhas

	if (entrada == NULL) {
                printf("\n arquivo de dados inexistente \n\n");
    }
    else {
         printf("arquivo SUDOKU.txt encontrado... \n\n");
         lin = 0; nlinhas = 0;
         while(fscanf(entrada, "%9s", linha)==1){       // lê até 9 caracteres e guarda na string linha
                   nlinhas = nlinhas +1;                // conta o número de linhas lidas no arquivo
                   lin = lin + 1;                       //
                   for(i=0;i<=8;i++){                   //
                       matrizSudoku[lin-1][i]=linha[i];
                   }
         }
         fclose(entrada);
         k=1;
    }
}

void mostrar(unsigned char matriz[9][9]){
         int lin,col,k;
         int l = 196;
         int c = 179;
         for(lin=0; lin <= 8; lin++){       // matriz guarda a tabela Sudoku da linha 0 até a linha 8
              if(lin%3 == 0){               // matriz guarda a tabela Sudoku da coluna 0 até a linha 8
                   printf("%c",l);
                   for(k=1; k<=10; k++) printf ("%c%c%c",l,l,l);
                   printf("\n");
              }
              for(col=0; col <= 8; col++){
                   //if(col%3 == 0) printf ('179');
                   if(col%3 == 0) printf ("%c",c);

                   printf(" %c ",matriz[lin][col]);
              }
              printf("%c\n",c);
         }
         for(k=1; k<=10; k++) printf ("%c%c%c",l,l,l);
         printf("%c",l);
}

void mostrarPossiveis(unsigned char matrizSudoku[9][9], unsigned char P[3][3], int lin, int col) {
	int i, j, k, r, l = 0, c = 0, m = 0; 
	int validos[10], maxCol, minCol, maxLin, minLin;
	int matriz[9][9];
	
	printf("Linha = %d	Coluna = %d\n\n", lin, col);
	lin = lin - 1;
	col = col - 1;
	
	r = lin % 3; // calculo para descobrir quais linhas deve percorrer, a partir do resto
	if ( r == 0 ) {
		minLin = lin;
		maxLin = lin + 2;
	} else if ( r == 1 ) {
		minLin = lin - 1;
		maxLin = lin + 1;
	} else {
		minLin = lin - 2;
		maxLin = lin;
	}
	r = col % 3;
	if ( r == 0 ) {
		minCol = col;
		maxCol = col + 2;
	} else if ( r == 1 ) {
		minCol = col - 1;
		maxCol = col + 1;
	} else {
		minCol = col - 2;
		maxCol = col;
	}

	for (i = 0; i < 9; i++ ) {//para fazer comparações entre os numeros, é preciso passar eles da tabela ascii para numeros, por isso deve subtrair 48 de cada numero.
		for (j = 0; j < 9; j++ ) {
			matriz[i][j] = matrizSudoku[i][j] - 48;
		}
	}
	for (k = 1; k <= 9; k++ ) {
		i = 0;
		j = 0;
		while ( matriz[lin][j] != k &&  j < 8  ) {
			//busca o valor na horizontal
			j++;
		}
		while ( ( matriz[i][col] != k ) && ( i < 8 ) ) {
			i++;//busca o valor na vertical
		}
		m++;

		l = minLin;
		c = minCol;
		while ( matriz[l][c] != k && l < maxLin) {
			while ( matriz[l][c] != k && c < maxCol) {
				c++;	
			}
			if(matriz[l][c] != k && l < maxLin) {
				l++;
				c = minCol;
			}
			
		}
		while ( matriz[l][c] != k && c < maxCol) {
			c++;	
		}
		if ( matriz[lin][j] == k || matriz[i][col] == k || matriz[l][c] == k) {
			validos[m] = 0;
		} else {
			validos[m] = k;
		}
	}
	k = 1;
	for (i = 0; i < 3; i++) {
		for (j = 0; j < 3; j++) {
			P[i][j] = validos[k];
			k++;
			printf("%d   ", P[i][j]);
		}
		printf("\n");
	}
}
