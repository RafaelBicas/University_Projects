/**
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 */

package primitivos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class PontoGrafico implements Figura
{
    int x, y, diametro;
    Color cor;
    
    public PontoGrafico(int x, int y, int diametro, Color cor)
    {
        this.x = x;
        this.y = y;
        this.diametro = diametro;
        this.cor = cor;
    }
    
    public PontoGrafico(PontoMatematico ponto, int diametro, Color cor)
    {
        this.x = (int)Math.round(ponto.getX());
        this.y = (int)Math.round(ponto.getY());
        this.diametro = diametro;
        this.cor = cor;
    }
    
    public int getX()
    {
        return x;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public int getDiametro()
    {
        return diametro;
    }
    
    public void setDiametro(int diametro)
    {
        this.diametro = diametro;
    }
    
    public Color getCor()
    {
        return cor;
    }
    
    public void setCor(Color cor)
    {
        this.cor = cor;
    }
    
    public void desenhar(GraphicsContext graphicsContext)
    {
        Paint fill = graphicsContext.getFill(); //salvar fill atual para restaurar depois de desenhar
        graphicsContext.setFill(cor);
        graphicsContext.fillOval(x - diametro/2, y - diametro/2, diametro, diametro);
        graphicsContext.setFill(fill); //restaurar fill, deixando-o igual ao que era antes da chamada deste método
    }
    
    public PontoGrafico clonar()
    {
        return new PontoGrafico(x, y, diametro, cor);
    }
    
    public PontoGrafico fazerClipping(RetanguloDeClipping retanguloClipping)
    {
        if (x < retanguloClipping.getP().getX()
         || x > retanguloClipping.getQ().getX()
         || y < retanguloClipping.getP().getY()
         || y > retanguloClipping.getQ().getY()) { return null; }
        return this;
    }
}
