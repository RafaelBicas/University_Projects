/**
 * Escreva a descrição da classe teste aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class teste
{
    public static void main() {
        int tamanho = 0;
        Celula inicio = null;
        int item = 3;
        ListaLigada funcao = new ListaLigada();
        //Pilha funcao = new Pilha();
        /**/funcao.inserirInicio(item);
        funcao.inserirInicio(5);
        funcao.inserirFim(7);
        funcao.removerFim();
        funcao.mostrar();
        System.out.printf("tam = %d", funcao.tamanho);
        
        
        /*
        funcao.pushPilha(item);
        //funcao.popPilha();
        funcao.pushPilha(5);
        tamanho = funcao.tamanhoPilha();
        funcao.mostrarPilha();
        System.out.printf("tam = %d", tamanho);*/
    }
}
