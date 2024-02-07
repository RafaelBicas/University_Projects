/**
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 */

package primitivos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Retangulo implements Figura
{
    PontoMatematico p;
    PontoMatematico q;
    Color cor;
    int espessura;
    
    public Retangulo(double x1, double y1, double x2, double y2, int espessura, Color cor)
    {
        p = new PontoMatematico(x1, y1);
        q = new PontoMatematico(x2, y2);
        this.cor = cor;
        this.espessura = espessura;
    }
    
    public Retangulo(PontoMatematico p, PontoMatematico q, int espessura, Color cor)
    {
        this.p = p;
        this.q = q;
        this.cor = cor;
        this.espessura = espessura;
    }
    
    public Retangulo(PontoGrafico p, PontoGrafico q, int espessura, Color cor)
    {
        this.p = new PontoMatematico(p.getX(), p.getY());
        this.q = new PontoMatematico(q.getX(), q.getY());
        this.cor = cor;
        this.espessura = espessura;
    }
    
    public PontoMatematico getP()
    {
        return p;
    }
    
    public PontoMatematico getQ()
    {
        return q;
    }
    
    public int getEspessura() {
        return espessura;
    }
    
    public void setEspessura(int espessura) {
        this.espessura = espessura;
    }
    
    public Color getCor() {
        return cor;
    }
    
    public void setCor(Color cor) {
        this.cor = cor;
    }
    
    public void desenhar(GraphicsContext graphicsContext)
    {
        (new Linha(p.getX(), p.getY(), p.getX(), q.getY(), espessura, cor)).desenhar(graphicsContext); //linha esquerda
        (new Linha(p.getX(), q.getY(), q.getX(), q.getY(), espessura, cor)).desenhar(graphicsContext); //linha superior
        (new Linha(q.getX(), q.getY(), q.getX(), p.getY(), espessura, cor)).desenhar(graphicsContext); //linha direita
        (new Linha(p.getX(), p.getY(), q.getX(), p.getY(), espessura, cor)).desenhar(graphicsContext); //linha inferior
    }
    
    public Retangulo clonar()
    {
        return new Retangulo(p.clonar(), q.clonar(), espessura, cor);
    }
    
    public RetanguloComClipping fazerClipping(RetanguloDeClipping retanguloClipping)
    {
        Linha linhaEsquerda = new Linha(p.getX(), p.getY(), p.getX(), q.getY(), espessura, cor).fazerClipping(retanguloClipping);
        Linha linhaSuperior = new Linha(p.getX(), q.getY(), q.getX(), q.getY(), espessura, cor).fazerClipping(retanguloClipping);
        Linha linhaDireita = new Linha(q.getX(), q.getY(), q.getX(), p.getY(), espessura, cor).fazerClipping(retanguloClipping);
        Linha linhaInferior = new Linha(p.getX(), p.getY(), q.getX(), p.getY(), espessura, cor).fazerClipping(retanguloClipping);
        if (linhaEsquerda == null && linhaSuperior == null && linhaDireita == null && linhaInferior == null) return null; //se nenhuma linha é visível, o retângulo todo não o é
        return new RetanguloComClipping(linhaEsquerda, linhaSuperior, linhaDireita, linhaInferior);
    }
}
