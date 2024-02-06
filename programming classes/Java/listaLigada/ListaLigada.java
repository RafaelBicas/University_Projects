
/**
 * Escreva a descrição da classe ListaLigada aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class ListaLigada //implements IListaLigada
{
    public Celula inicio;
    public int tamanho;
    Celula fim;
    /**
     * Construtor para objetos da classe ListaLigada
     */
    public ListaLigada()
    {
        inicio = null;
        tamanho = 0;
        fim = null;
        // inicializa variáveis de instância
    }

    public boolean verificarListaVazia() {
        boolean vazia = false;

        if(tamanho == 0) {
            vazia = true;
        }

        return vazia;
    }

    public void inserirInicio(Object item) {
        Celula novo = new Celula(item);  
        novo.prox = inicio;
        inicio = novo;
        if(tamanho == 0) {
            fim = novo;
        }
        tamanho++;
    }

    public void inserirFim(Object item) {
        Celula novo = new Celula(item);
        if ( tamanho == 0 ) {
            inserirInicio( item );
        } else {
            fim.prox = novo;
            novo.prox = null;
            tamanho++;
        }

    }

    public Celula removerInicio() {
        Celula aux = inicio;
        if(inicio == fim) {
            inicio = null;
            fim = null;
        } else {  
            inicio = aux.prox;
            aux.prox = null;
        }
        tamanho--;
        return aux;
    }

    public Celula removerFim() {
        Celula aux = fim;
        Celula ant = inicio;
        if(inicio == fim) {
            inicio = null;
            fim = null;
        } else {
            while (ant.prox != fim) {
                ant = ant.prox;
            }
            ant.prox = null;
            aux = fim;
            fim = ant;
        }
        tamanho--;
        return aux;
    }

    public void mostrar() {
        Celula p;

        if(tamanho == 0) {
            System.out.println("Lista Vazia!!");
        } else {
            p = inicio;
            while(p != null) {
                System.out.println(" " + p.conteudo);
                p = p.prox;
            }
        }

    }
}
