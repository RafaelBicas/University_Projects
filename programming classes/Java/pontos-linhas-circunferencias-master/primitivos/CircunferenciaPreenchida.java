/**
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 */ 

package primitivos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class CircunferenciaPreenchida extends Circunferencia implements Figura
{
    public CircunferenciaPreenchida(double xCentro, double yCentro, double raio, int espessura, Color cor)
    {
        super(xCentro, yCentro, raio, espessura, cor);
    }
    
    public CircunferenciaPreenchida(PontoMatematico centro, double raio, int espessura, Color cor)
    {
        super(centro, raio, espessura, cor);
    }
    
    public CircunferenciaPreenchida(PontoGrafico centroGrafico, double raio, Color cor)
    {
        super(centroGrafico, raio, cor);
    }
    
    @Override
    public void desenhar(GraphicsContext graphicsContext)
    {
        Paint fill = graphicsContext.getFill(); //salvar fill atual para restaurar depois de desenhar
        graphicsContext.setFill(cor);
        
        int deslocamentoX, deslocamentoY;
        int novoRaio = (int)Math.round(raio); //variável utilizada para controlar o preenchimento
        while (novoRaio >= 0)
        {
            for (double angulo = 0.0; angulo <= 45.0; angulo = angulo + 0.1)
            {
                deslocamentoX = (int)Math.round(novoRaio * Math.cos(Math.toRadians(angulo)));
                deslocamentoY = (int)Math.round(novoRaio * Math.sin(Math.toRadians(angulo)));
                
                (new PontoGrafico(centroGrafico.getX() + deslocamentoX, centroGrafico.getY() + deslocamentoY, espessura, cor)).desenhar(graphicsContext); //0 a 45
                (new PontoGrafico(centroGrafico.getX() + deslocamentoY, centroGrafico.getY() + deslocamentoX, espessura, cor)).desenhar(graphicsContext); //45 a 90
                (new PontoGrafico(centroGrafico.getX() - deslocamentoY, centroGrafico.getY() + deslocamentoX, espessura, cor)).desenhar(graphicsContext); //90 a 135
                (new PontoGrafico(centroGrafico.getX() - deslocamentoX, centroGrafico.getY() + deslocamentoY, espessura, cor)).desenhar(graphicsContext); //135 a 180
                (new PontoGrafico(centroGrafico.getX() - deslocamentoX, centroGrafico.getY() - deslocamentoY, espessura, cor)).desenhar(graphicsContext); //180 a 225
                (new PontoGrafico(centroGrafico.getX() - deslocamentoY, centroGrafico.getY() - deslocamentoX, espessura, cor)).desenhar(graphicsContext); //225 a 270
                (new PontoGrafico(centroGrafico.getX() + deslocamentoY, centroGrafico.getY() - deslocamentoX, espessura, cor)).desenhar(graphicsContext); //270 a 315
                (new PontoGrafico(centroGrafico.getX() + deslocamentoX, centroGrafico.getY() - deslocamentoY, espessura, cor)).desenhar(graphicsContext); //315 a 360
            }
            novoRaio--;
        }
        
        graphicsContext.setFill(fill); //restaurar fill, deixando-o igual ao que era antes da chamada deste método
    }
    
    public CircunferenciaPreenchida clonar()
    {
        return new CircunferenciaPreenchida(centro.clonar(), raio, espessura, cor);
    }
}
