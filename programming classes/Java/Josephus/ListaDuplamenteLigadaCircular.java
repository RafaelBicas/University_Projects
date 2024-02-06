 

/**
 * classe: ListaDuplamenteLigadaCircular
 *  Implementa a TAD Lista Duplamente Ligada Circular
 * 
 * @author Julio Arakaki, Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 * @version 20190527
 */

class ListaDuplamenteLigadaCircular implements IListaDuplamenteLigadaCircular {
    private No inicio; // ref para primeiro elemento
    private No fim;    // ref para ultimo elemento
    // Setters e Getters
    /**
     * @return endereco do primeiro elemento da lista
     */
    public No getInicio() {
        return inicio;
    }
    
    /**
     * @param inicio endereco do primeiro elemento da lista
     */
    public void setInicio(No inicio) {
        this.inicio = inicio;
    }
    
    /**
     * @return endereco do ultimo elemento da lista
     */
    public No getFim() {
        return fim;
    }
    
    /**
     * @param fim endereco do ultimo elemento da lista
     */
    public void setFim(No fim) {
        this.fim = fim;
    }

    /**
     * Construtor da Lista
     */
    public ListaDuplamenteLigadaCircular() {   
        setInicio(null);
        setFim(null);
    }
    
    /**
     * Verifica se a Lista esta vazia
     * 
     * @return true se estiver vazia e false caso contrario
     * 
     */
    public boolean estaVazia() {
        return (getInicio() == null && getFim() == null); 
    }
 
    /**
     * Insere um novo No no inicio da Lista
     *  
     */
    public void inserirInicio(Object elem) {

        // Cria novo no
        No novoNo = new No(elem); 
        
        // Insere novo no na Lista (atualizando ponteiros)
        if( estaVazia() ) {   // se a lista estiver vazia
            setFim(novoNo);
        } else {
            getInicio().setAnterior(novoNo);
        }
        novoNo.setProximo(getInicio());
        novoNo.setAnterior(getFim());
        setInicio(novoNo);
        getFim().setProximo(novoNo);
    }

    /**
     * Insere um novo No no final da Lista
     *  
     */
    public void inserirFim(Object elem) {
        // Cria novo no
        No novoNo = new No(elem);
        // Insere novo no na Lista (atualizando ponteiros)
        if( estaVazia() ) { // se a lista estiver vazia
            setInicio(novoNo);
        } else {
            getFim().setProximo(novoNo); 
            novoNo.setAnterior(getFim());
        }  
        novoNo.setProximo(getInicio());
        getInicio().setAnterior(novoNo);
        setFim(novoNo);
    }

    /**
     * Insere um novo No apos um No encontrado (pela chave)
     * 
     * @param chave codigo do elemento a ser encontrado
     * 
     * @param elem elemento a ser inserido
     * 
     * @return true se o o no foi inserido e false caso contrario
     * 
     */
    public boolean inserirApos(int chave, Object elem) {
        No noAtual = getInicio(); 

        // percorre a lista em busca do No
        while((Integer)noAtual.getConteudo() != chave) {
            noAtual = noAtual.getProximo(); 
            if(noAtual == null) { // nao encontrou posicao
                return false;  
            }
        }
        
        // Cria novo no
        No novoNo = new No(elem);

        // Insere novo no na Lista (atualizando ponteiros)
        novoNo.setProximo(noAtual.getProximo());
        novoNo.setAnterior(noAtual);
        noAtual.getProximo().setAnterior(novoNo);
        noAtual.setProximo(novoNo);
        
        return true; 
    }

    /**
     * Remove o primeiro No da Lista
     * 
     * @return No removido ou null se a Lista estiver vazia
     * 
     */
    public No removerInicio() {
        No temp = null;
        if(getInicio() != null) {
            // Guarda o no
            temp = getInicio();
            // Acerta todas as referencias (ponteiros)
            if (getInicio() == getFim()) { //se for o primeiro
                setInicio(null);
                setFim(null);
            }
            else {
                getInicio().getProximo().setAnterior(getFim()); 
                getInicio().getAnterior().setProximo(getInicio().getProximo());
                setInicio(getInicio().getProximo());
            }
        }

        // retorna o no removido
        return temp;
    }

    /**
     * Remove o ultimo No da Lista
     * 
     * @return No removido ou null se a Lista estiver vazia
     * 
     */
    public No removerFim() {
        No temp = null;
        
        if(getFim() != null) {

            // Guarda o no
            temp = getFim();

            // Acerta todas as referencias (ponteiros)
            if (getFim() == getInicio()) { //se for o primeiro
                setFim(null);
                setInicio(null);
            }
            else {
                getFim().getAnterior().setProximo(inicio);
                getInicio().setAnterior(getFim().getAnterior());
                setFim(getFim().getAnterior());
            }
        }
        
        // retorna o no removido
        return temp;
    }


    /**
     * Remove um No de acordo com uma chave (inteiro)
     * 
     * @param chave numero inteiro para buscar o no a ser removido
     * 
     * @return No removido ou null caso nao encontre
     * 
     */
    public No removerPelaChave(int chave) {
        No temp = null; // Ponteiro para percorrer a lista
        
        if(getInicio() != null) {
            
            temp = getInicio(); // comeca do nicio

            // Percorre ate encontrar o No, ou retorn null caso nao encontre
            while((Integer)temp.getConteudo() != chave) {
                temp = temp.getProximo(); 
                if(temp == null) {
                    return null;                
                }
            }
            
            // Acerta todas as referencias (ponteiros)
            if (temp == getInicio()) { setInicio(temp.getProximo()); }
            if (temp == getFim()) { setFim(temp.getAnterior()); }
            temp.getAnterior().setProximo(temp.getProximo());
            temp.getProximo().setAnterior(temp.getAnterior());
        }
                
        // retorna o no removido        
        return temp;
    }

    /**
     * Retorna o conteudo da Lista como String (do inicio ate o fim)
     */
    public String toString() {
        String s = "[ ";
        No noAtual = getInicio();  // inicia do inicio
        if (noAtual != null) {
            do {
                s = s + noAtual.toString() + " ";  // monta os dados como string
                noAtual = noAtual.getProximo();   // vai para o proximo
            } while(noAtual != getInicio());    // enquanto nao voltar para o inicio
        }
        s = s + "]";

        return s;
    }

    /**
     * Retorna o conteudo da Lista como String (do fim ate o inicio)
     */
    public String toStringDoFim() {
        String s = "[ ";
        No noAtual = getFim();  // inicia no fim
        if (noAtual != null) {
            do {
                s = s + noAtual.toString() + " "; // monta os dados como string
                noAtual = noAtual.getAnterior(); // vai para o anterior
            } while(noAtual != getFim()); // enquanto nao voltar para o inicio
        }
        s = s + "]";
        
        return s;
    }
    
    
    /**
     * Remove o No que esta na posicao k
     * 
     * @param k posicao do no a remover
     * 
     * @return No removido ou null caso nao encontre
     * 
     */
    public No removerNaPosicao(int k) {
        No temp = getInicio(); // Ponteiro para percorrer a lista
        
        if(temp != null) {
            
            int contador = 0;
            // Percorre ate encontrar o No, ou retorn null caso nao encontre
            while (contador < k) {
                temp = temp.getProximo();
                contador++;
                if(temp == getInicio()) {
                    return null;                
                }
            }

            // Acerta todas as referencias (ponteiros)
            if (getInicio() == getFim()) { //se for o primeiro
                setInicio(null);
                setFim(null);
            }
            else {
                if (temp == getInicio()) { setInicio(temp.getProximo()); }
                if (temp == getFim()) { setFim(temp.getAnterior()); }
                temp.getAnterior().setProximo(temp.getProximo());
                temp.getProximo().setAnterior(temp.getAnterior());
            }
        }
                
        // retorna o no removido        
        return temp;
    }
    
    public No obterPrimeiro() {
        return getInicio();
    }
}  
