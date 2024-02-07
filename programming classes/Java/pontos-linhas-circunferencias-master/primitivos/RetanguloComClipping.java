/**
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 */

package primitivos;

import javafx.scene.canvas.GraphicsContext;

public class RetanguloComClipping implements Figura
{
    Linha linhaEsquerda, linhaSuperior, linhaDireita, linhaInferior;
    
    public RetanguloComClipping(Linha linhaEsquerda, Linha linhaSuperior, Linha linhaDireita, Linha linhaInferior)
    {
        this.linhaEsquerda = linhaEsquerda;
        this.linhaSuperior = linhaSuperior;
        this.linhaDireita = linhaDireita;
        this.linhaInferior = linhaInferior;
    }
    
    public void desenhar(GraphicsContext graphicsContext)
    {
        //como foi feito clipping nas linhas, elas podem ser null, e portanto devemos checar isso para não tentar mexer com ponteiro nulo
        if (linhaEsquerda != null) linhaEsquerda.desenhar(graphicsContext);
        if (linhaSuperior != null) linhaSuperior.desenhar(graphicsContext);
        if (linhaDireita != null) linhaDireita.desenhar(graphicsContext);
        if (linhaInferior != null) linhaInferior.desenhar(graphicsContext);
    }
    
    public Linha getLinhaEsquerda()
    {
        return linhaEsquerda;
    }
    
    public Linha getLinhaSuperior()
    {
        return linhaSuperior;
    }
    
    public Linha getLinhaDireita()
    {
        return linhaDireita;
    }
    
    public Linha getLinhaInferior()
    {
        return linhaInferior;
    }
    
    public RetanguloComClipping clonar()
    {
        return new RetanguloComClipping(linhaEsquerda.clonar(), linhaSuperior.clonar(), linhaDireita.clonar(), linhaInferior.clonar());
    }
    
    public RetanguloComClipping fazerClipping(RetanguloDeClipping retangulo)
    {
        return this; //não faz sentido fazer clipping em um primitivo em que o clipping já foi feito, então vamos apenas retornar o próprio objeto, como se estivesse completamente dentro da janela de clipping
    }
}
