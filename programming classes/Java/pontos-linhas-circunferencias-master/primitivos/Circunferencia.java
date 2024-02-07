/**
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 */

package primitivos;

import java.lang.Math;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Circunferencia implements Figura
{
    Color cor;
    PontoMatematico centro;
    PontoGrafico centroGrafico;
    double raio;
    int espessura;
    
    public Circunferencia(double xCentro, double yCentro, double raio, int espessura, Color cor)
    {
        centro = new PontoMatematico(xCentro, yCentro);
        centroGrafico = new PontoGrafico(centro, espessura, cor);
        this.raio = raio;
        this.espessura = espessura;
        this.cor = cor;
    }
    
    public Circunferencia(PontoMatematico centro, double raio, int espessura, Color cor)
    {
        this.centro = centro;
        centroGrafico = new PontoGrafico(centro, espessura, cor);
        this.raio = raio;
        this.espessura = espessura;
        this.cor = cor;
    }
    
    public Circunferencia(PontoGrafico centroGrafico, double raio, Color cor)
    {
        centro = new PontoMatematico(centroGrafico.getX(), centroGrafico.getY());
        this.centroGrafico = centroGrafico;
        this.raio = raio;
        this.espessura = espessura;
        this.cor = cor;
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
            
            (new PontoGrafico(centroGrafico.getX() + deslocamentoX, centroGrafico.getY() + deslocamentoY, espessura, cor)).desenhar(graphicsContext); //0 a 45
            (new PontoGrafico(centroGrafico.getX() + deslocamentoY, centroGrafico.getY() + deslocamentoX, espessura, cor)).desenhar(graphicsContext); //45 a 90
            (new PontoGrafico(centroGrafico.getX() - deslocamentoY, centroGrafico.getY() + deslocamentoX, espessura, cor)).desenhar(graphicsContext); //90 a 135
            (new PontoGrafico(centroGrafico.getX() - deslocamentoX, centroGrafico.getY() + deslocamentoY, espessura, cor)).desenhar(graphicsContext); //135 a 180
            (new PontoGrafico(centroGrafico.getX() - deslocamentoX, centroGrafico.getY() - deslocamentoY, espessura, cor)).desenhar(graphicsContext); //180 a 225
            (new PontoGrafico(centroGrafico.getX() - deslocamentoY, centroGrafico.getY() - deslocamentoX, espessura, cor)).desenhar(graphicsContext); //225 a 270
            (new PontoGrafico(centroGrafico.getX() + deslocamentoY, centroGrafico.getY() - deslocamentoX, espessura, cor)).desenhar(graphicsContext); //270 a 315
            (new PontoGrafico(centroGrafico.getX() + deslocamentoX, centroGrafico.getY() - deslocamentoY, espessura, cor)).desenhar(graphicsContext); //315 a 360
        }
        
        graphicsContext.setFill(fill); //restaurar fill, deixando-o igual ao que era antes da chamada deste método
    }
    
    public Circunferencia clonar()
    {
        return new Circunferencia(centro.clonar(), raio, espessura, cor);
    }
    
    public CircunferenciaComClipping fazerClipping(RetanguloDeClipping retanguloClipping)
    {
        return new CircunferenciaComClipping(centro, raio, espessura, cor, retanguloClipping);
    }
}
