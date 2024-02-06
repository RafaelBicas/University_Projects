 
 

/**
 * @author jarakaki
 * @version 0.2 20190523
 */
class No {
    private Object conteudo;  // dados do no
    private No proximo;       // proximo na lista
    private No anterior;      // anterior na lista
    
    /**
     * constructor
     * 
     * @param conteudo dados do No
     */
    public No(Object conteudo) {   // 
        setConteudo(conteudo);
        setProximo(null);
        setAnterior(null);
    }

    /**
     * @return conteudo do No
     */
    public Object getConteudo() {
        return conteudo;
    }

    /**
     * @param conteudo dados do No
     */
    public void setConteudo(Object conteudo) {
        this.conteudo = conteudo;
    }

    /**
     * @return endereco do proximo No
     */
    public No getProximo() {
        return proximo;
    }

    /**
     * @param proximo endereco do proximo No
     */
    public void setProximo(No proximo) {
        this.proximo = proximo;
    }

    /**
     * @return endereco do No anterior
     */
    public No getAnterior() {
        return anterior;
    }

    /**
     * @param anterior endereco do No anterior
     */
    public void setAnterior(No anterior) {
        this.anterior = anterior;
    }

    /**
     * Retorna o conteudo do No como String
     */
    public String toString() { 
        return getConteudo().toString(); 
    }
} 
