  

/**
 * Contem assinaturas do metodos da TAD Lista Duplamente Ligada Circular
 * 
 * @author Julio Arakaki, Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 * @version 20190527
 */
public interface IListaDuplamenteLigadaCircular {
    public boolean estaVazia(); 
    
    public void inserirInicio(Object novo); 

    public void inserirFim(Object novo);
    
    public boolean inserirApos(int chave, Object novo);

    public Object removerInicio();

    public Object removerFim();
    
    public Object removerPelaChave(int chave);
}
