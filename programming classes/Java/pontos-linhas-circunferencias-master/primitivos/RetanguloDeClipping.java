/**
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 */

package primitivos;

import java.lang.Math;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class RetanguloDeClipping extends Retangulo implements Figura
{    
    public RetanguloDeClipping(double x1, double y1, double x2, double y2, int espessura, Color cor)
    {
        super(Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2), espessura, cor);
    }
    
    public RetanguloDeClipping(PontoMatematico p, PontoMatematico q, int espessura, Color cor)
    {
        super(new PontoMatematico(Math.min(p.getX(), q.getX()), Math.min(p.getY(), q.getY())), new PontoMatematico(Math.max(p.getX(), q.getX()), Math.max(p.getY(), q.getY())), espessura, cor);
    }
    
    public RetanguloDeClipping(PontoGrafico p, PontoGrafico q, int espessura, Color cor)
    {
        super(new PontoGrafico(Math.min(p.getX(), q.getX()), Math.min(p.getY(), q.getY()), espessura, cor), new PontoGrafico(Math.max(p.getX(), q.getX()), Math.max(p.getY(), q.getY()), espessura, cor), espessura, cor);
    }
    
    @Override
    public void desenhar(GraphicsContext graphicsContext)
    {
        (new Linha(p.getX(), p.getY(), p.getX(), q.getY(), espessura, cor)).desenharPontilhado(graphicsContext); //linha esquerda
        (new Linha(p.getX(), q.getY(), q.getX(), q.getY(), espessura, cor)).desenharPontilhado(graphicsContext); //linha superior
        (new Linha(q.getX(), q.getY(), q.getX(), p.getY(), espessura, cor)).desenharPontilhado(graphicsContext); //linha direita
        (new Linha(p.getX(), p.getY(), q.getX(), p.getY(), espessura, cor)).desenharPontilhado(graphicsContext); //linha inferior
    }
    
    public RetanguloDeClipping clonar()
    {
        return new RetanguloDeClipping(p.clonar(), q.clonar(), espessura, cor);
    }
}
