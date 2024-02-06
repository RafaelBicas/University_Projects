
/**
 * Escreva a descrição da classe Pilha aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */ 
public class Pilha
{
    ListaLigada lista;
    Object topo;
    Pilha() {
        lista = new ListaLigada();
    }
    
    public void pushPilha(Object item) {
        lista.inserirFim(item);
    }
    
    public void popPilha() {
        if(!lista.verificarListaVazia()) {
            lista.removerFim();
        }
    }
    
    public int tamanhoPilha() {
        return lista.tamanho;
    }
    
    public void mostrarPilha() {
        lista.mostrar();
    }
    
        
}
