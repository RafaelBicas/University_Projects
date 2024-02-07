/**
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 */

package primitivos;

import java.lang.Math;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class CircunferenciaComClipping implements Figura
{
    Color cor;
    PontoMatematico centro;
    PontoGrafico centroGrafico;
    double raio;
    int espessura;
    RetanguloDeClipping janelaDeClipping;
    
    public CircunferenciaComClipping(double xCentro, double yCentro, double raio, int espessura, Color cor, RetanguloDeClipping janelaDeClipping)
    {
        centro = new PontoMatematico(xCentro, yCentro);
        centroGrafico = new PontoGrafico(centro, espessura, cor);
        this.raio = raio;
        this.espessura = espessura;
        this.cor = cor;
        this.janelaDeClipping = janelaDeClipping;
    }
    
    public CircunferenciaComClipping(PontoMatematico centro, double raio, int espessura, Color cor, RetanguloDeClipping janelaDeClipping)
    {
        this.centro = centro;
        centroGrafico = new PontoGrafico(centro, espessura, cor);
        this.raio = raio;
        this.espessura = espessura;
        this.cor = cor;
        this.janelaDeClipping = janelaDeClipping;
    }
    
    public CircunferenciaComClipping(PontoGrafico centroGrafico, double raio, Color cor, RetanguloDeClipping janelaDeClipping)
    {
        centro = new PontoMatematico(centroGrafico.getX(), centroGrafico.getY());
        this.centroGrafico = centroGrafico;
        this.raio = raio;
        this.espessura = espessura;
        this.cor = cor;
        this.janelaDeClipping = janelaDeClipping;
    }
    
    public PontoMatematico getCentro()
    {
        return centro;
    }
    
    public double getRaio()
    {
        return raio;
    }
    
    public int getEspessura()
    {
        return espessura;
    }
    
    public Color getCor()
    {
        return cor;
    }
    
    public void desenhar(GraphicsContext graphicsContext)
    {
        Paint fill = graphicsContext.getFill(); //salvar fill atual para restaurar depois de desenhar
        graphicsContext.setFill(cor);
        
        int deslocamentoX, deslocamentoY;
        for (double angulo = 0.0; angulo <= 45.0; angulo = angulo + 0.1)
        {
            deslocamentoX = (int)Math.round(raio * Math.cos(Math.toRadians(angulo)));
            deslocamentoY = (int)Math.round(raio * Math.sin(Math.toRadians(angulo)));
            
            //0 a 45
            PontoGrafico ponto = new PontoGrafico(centroGrafico.getX() + deslocamentoX, centroGrafico.getY() + deslocamentoY, espessura, cor);
            ponto = ponto.fazerClipping(janelaDeClipping);
            if (ponto != null) { ponto.desenhar(graphicsContext); }
            
            //45 a 90
            ponto = new PontoGrafico(centroGrafico.getX() + deslocamentoY, centroGrafico.getY() + deslocamentoX, espessura, cor);
            ponto = ponto.fazerClipping(janelaDeClipping);
            if (ponto != null) { ponto.desenhar(graphicsContext); }
            
            //90 a 135
            ponto = new PontoGrafico(centroGrafico.getX() - deslocamentoY, centroGrafico.getY() + deslocamentoX, espessura, cor);
            ponto = ponto.fazerClipping(janelaDeClipping);
            if (ponto != null) { ponto.desenhar(graphicsContext); }
            
            //135 a 180
            ponto = new PontoGrafico(centroGrafico.getX() - deslocamentoX, centroGrafico.getY() + deslocamentoY, espessura, cor);
            ponto = ponto.fazerClipping(janelaDeClipping);
            if (ponto != null) { ponto.desenhar(graphicsContext); }
            
            //180 a 225
            ponto = new PontoGrafico(centroGrafico.getX() - deslocamentoX, centroGrafico.getY() - deslocamentoY, espessura, cor);
            ponto = ponto.fazerClipping(janelaDeClipping);
            if (ponto != null) { ponto.desenhar(graphicsContext); }
            
            //225 a 270
            ponto = new PontoGrafico(centroGrafico.getX() - deslocamentoY, centroGrafico.getY() - deslocamentoX, espessura, cor);
            ponto = ponto.fazerClipping(janelaDeClipping);
            if (ponto != null) { ponto.desenhar(graphicsContext); }
            
            //270 a 315
            ponto = new PontoGrafico(centroGrafico.getX() + deslocamentoY, centroGrafico.getY() - deslocamentoX, espessura, cor);
            ponto = ponto.fazerClipping(janelaDeClipping);
            if (ponto != null) { ponto.desenhar(graphicsContext); }
            
            //315 a 360
            ponto = new PontoGrafico(centroGrafico.getX() + deslocamentoX, centroGrafico.getY() - deslocamentoY, espessura, cor);
            ponto = ponto.fazerClipping(janelaDeClipping);
            if (ponto != null) { ponto.desenhar(graphicsContext); }
        }
        
        graphicsContext.setFill(fill); //restaurar fill, deixando-o igual ao que era antes da chamada deste método
    }
    
    public CircunferenciaComClipping clonar()
    {
        return new CircunferenciaComClipping(centro.clonar(), raio, espessura, cor, janelaDeClipping);
    }
    
    public CircunferenciaComClipping fazerClipping(RetanguloDeClipping retanguloClipping)
    {
        return this; //não faz sentido fazer clipping em um primitivo em que o clipping já foi feito, então vamos apenas retornar o próprio objeto, como se estivesse completamente dentro da janela de clipping
    }
}
