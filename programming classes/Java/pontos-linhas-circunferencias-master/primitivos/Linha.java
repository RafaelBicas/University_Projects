/**
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 */

package primitivos;

import java.lang.Math;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Linha implements Figura
{
    PontoMatematico p;
    PontoMatematico q;
    Color cor;
    int espessura;
    
    public Linha(double x1, double y1, double x2, double y2, int espessura, Color cor)
    {
        p = new PontoMatematico(x1, y1);
        q = new PontoMatematico(x2, y2);
        this.cor = cor;
        this.espessura = espessura;
    }
    
    public Linha(PontoMatematico p, PontoMatematico q, int espessura, Color cor)
    {
        this.p = p;
        this.q = q;
        this.cor = cor;
        this.espessura = espessura;
    }
    
    public Linha(PontoGrafico p, PontoGrafico q, int espessura, Color cor)
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
        Paint fill = graphicsContext.getFill(); //salvar fill atual para restaurar depois de desenhar
        graphicsContext.setFill(cor);
        
        //p = (x0, y0)
        //q = (x, y)
        
        //y = m * (x - x0) + y0
        //x = (y - y0) / m + x0
        //m = (y - y0) / (x - x0)
        
        double deltaX = q.getX() - p.getX();
        double deltaY = q.getY() - p.getY();
        double m = (deltaY) / (deltaX);
        PontoGrafico pGrafico = new PontoGrafico(p, espessura, cor);
        PontoGrafico qGrafico = new PontoGrafico(q, espessura, cor);
        
        if (Math.abs(deltaX) > Math.abs(deltaY))
        {
            //obter y a partir do x
            if (deltaX > 0)
            {
                for (int x = pGrafico.getX(); x <= qGrafico.getX(); x++)
                {
                    double yCalculado = m * Math.abs(x - p.getX()) + p.getY();
                    int y = (int)Math.round(yCalculado);
                    (new PontoGrafico(x, y, espessura, cor)).desenhar(graphicsContext);
                }
            }
            else
            {
                for (int x = qGrafico.getX(); x <= pGrafico.getX(); x++)
                {
                    double yCalculado = m * Math.abs(x - q.getX()) + q.getY();
                    int y = (int)Math.round(yCalculado);
                    (new PontoGrafico(x, y, espessura, cor)).desenhar(graphicsContext);
                }
            }
        }
        else
        {
            if (deltaX == 0)
            {
                if (deltaY > 0)
                {
                    for (int y = pGrafico.getY(); y <= qGrafico.getY(); y++)
                    {
                        int x = (int)Math.round(p.getX());
                        (new PontoGrafico(x, y, espessura, cor)).desenhar(graphicsContext);
                    }
                }
                else
                {
                    for (int y = qGrafico.getY(); y <= pGrafico.getY(); y++)
                    {
                        int x = (int)Math.round(q.getX());
                        (new PontoGrafico(x, y, espessura, cor)).desenhar(graphicsContext);
                    }
                }
            }
            //obter x a partir do y
            else if (deltaY > 0)
            {
                for (int y = pGrafico.getY(); y <= qGrafico.getY(); y++)
                {
                    double xCalculado = Math.abs(y - p.getY()) / m + p.getX();
                    int x = (int)Math.round(xCalculado);
                    (new PontoGrafico(x, y, espessura, cor)).desenhar(graphicsContext);
                }
            }
            else
            {
                for (int y = qGrafico.getY(); y <= pGrafico.getY(); y++)
                {
                    double xCalculado = Math.abs(y - q.getY()) / m + q.getX();
                    int x = (int)Math.round(xCalculado);
                    (new PontoGrafico(x, y, espessura, cor)).desenhar(graphicsContext);
                }
            }
        }
        
        graphicsContext.setFill(fill); //restaurar fill, deixando-o igual ao que era antes da chamada deste método
    }
    
    public void desenharPontilhado(GraphicsContext graphicsContext)
    {
        Paint fill = graphicsContext.getFill(); //salvar fill atual para restaurar depois de desenhar
        graphicsContext.setFill(cor);
        
        double deltaX = q.getX() - p.getX();
        double deltaY = q.getY() - p.getY();
        double m = (deltaY) / (deltaX);
        PontoGrafico pGrafico = new PontoGrafico(p, espessura, cor);
        PontoGrafico qGrafico = new PontoGrafico(q, espessura, cor);
        int modulo = Math.max(1, 5 * espessura); //se espessura for 0 (acontece ao desenhar o mapa), a multiplicação tem resultado 0
                                                 //isso acarretará em divisão por zero na checagem feita para desenhar o ponto, e portanto precisa ser evitado
                                                 //1 como valor mínimo é suficiente, qualquer valor serve, já que com espessura 0 o desenho não será feito
        int limite = espessura;
        
        if (Math.abs(deltaX) > Math.abs(deltaY))
        {
            //obter y a partir do x
            if (deltaX > 0)
            {
                for (int x = pGrafico.getX(); x <= qGrafico.getX(); x++)
                {
                    double yCalculado = m * Math.abs(x - p.getX()) + p.getY();
                    int y = (int)Math.round(yCalculado);
                    if (x % modulo > limite) (new PontoGrafico(x, y, espessura, cor)).desenhar(graphicsContext);
                }
            }
            else
            {
                for (int x = qGrafico.getX(); x <= pGrafico.getX(); x++)
                {
                    double yCalculado = m * Math.abs(x - q.getX()) + q.getY();
                    int y = (int)Math.round(yCalculado);
                    if (x % modulo > limite) (new PontoGrafico(x, y, espessura, cor)).desenhar(graphicsContext);
                }
            }
        }
        else
        {
            if (deltaX == 0)
            {
                if (deltaY > 0)
                {
                    for (int y = pGrafico.getY(); y <= qGrafico.getY(); y++)
                    {
                        int x = (int)Math.round(p.getX());
                        if (y % modulo > limite) (new PontoGrafico(x, y, espessura, cor)).desenhar(graphicsContext);
                    }
                }
                else
                {
                    for (int y = qGrafico.getY(); y <= pGrafico.getY(); y++)
                    {
                        int x = (int)Math.round(q.getX());
                        if (y % modulo > limite) (new PontoGrafico(x, y, espessura, cor)).desenhar(graphicsContext);
                    }
                }
            }
            //obter x a partir do y
            else if (deltaY > 0)
            {
                for (int y = pGrafico.getY(); y <= qGrafico.getY(); y++)
                {
                    double xCalculado = Math.abs(y - p.getY()) / m + p.getX();
                    int x = (int)Math.round(xCalculado);
                    if (y % modulo > limite) (new PontoGrafico(x, y, espessura, cor)).desenhar(graphicsContext);
                }
            }
            else
            {
                for (int y = qGrafico.getY(); y <= pGrafico.getY(); y++)
                {
                    double xCalculado = Math.abs(y - q.getY()) / m + q.getX();
                    int x = (int)Math.round(xCalculado);
                    if (y % modulo > limite) (new PontoGrafico(x, y, espessura, cor)).desenhar(graphicsContext);
                }
            }
        }
        
        graphicsContext.setFill(fill); //restaurar fill, deixando-o igual ao que era antes da chamada deste método
    }
    
    public Linha clonar()
    {
        return new Linha(p.clonar(), q.clonar(), espessura, cor);
    }
    
    public Linha fazerClipping(RetanguloDeClipping retanguloClipping)
    {
        boolean pCima = false, pBaixo = false, pEsquerda = false, pDireita = false, qCima = false, qBaixo = false, qEsquerda = false, qDireita = false, remover = false;
        
        Linha linha = clonar();
        
        if (linha.getP().getY() < retanguloClipping.getP().getY()) { pCima = true; }
        if (linha.getQ().getY() < retanguloClipping.getP().getY()) { qCima = true; }
        
        if (linha.getP().getY() > retanguloClipping.getQ().getY()) { pBaixo = true; }
        if (linha.getQ().getY() > retanguloClipping.getQ().getY()) { qBaixo = true; }
        
        if (linha.getP().getX() < retanguloClipping.getP().getX()) { pEsquerda = true; }
        if (linha.getQ().getX() < retanguloClipping.getP().getX()) { qEsquerda = true; }
        
        if (linha.getP().getX() > retanguloClipping.getQ().getX()) { pDireita = true; }
        if (linha.getQ().getX() > retanguloClipping.getQ().getX()) { qDireita = true; }
        
        //se a linha não estiver completamente dentro da janela
        if (!(pCima == false && pBaixo == false && pEsquerda == false && pDireita == false && qCima == false && qBaixo == false && qEsquerda == false && qDireita == false))
        {
            //já que a linha não está completamente dentro da janela, ela é completamente invisível se não houver intersecção com alguma borda da janela de clipping
            if ((pCima && qCima) || (pBaixo && qBaixo) || (pEsquerda && qEsquerda) || (pDireita && qDireita))
            {
                remover  = true;
            }
            else //se há intersecção, devemos calcular em quais pontos ocorre
            {
                double deltaX = linha.getQ().getX() - linha.getP().getX();
                double deltaY = linha.getQ().getY() - linha.getP().getY();
                double m = (deltaY) / (deltaX);
                
                if (deltaX == 0) //se for uma linha vertical
                {
                    if (pCima) { linha.getP().setY(retanguloClipping.getP().getY()); pCima = false; }
                    if (pBaixo) { linha.getP().setY(retanguloClipping.getQ().getY()); pBaixo = false; }
                    if (qCima) { linha.getQ().setY(retanguloClipping.getP().getY()); qCima = false; }
                    if (qBaixo) { linha.getQ().setY(retanguloClipping.getQ().getY()); qBaixo = false; }
                }
                else if (deltaY == 0) //se for uma linha horizontal
                {
                    if (pEsquerda) { linha.getP().setX(retanguloClipping.getP().getX()); pEsquerda = false; }
                    if (pDireita) { linha.getP().setX(retanguloClipping.getQ().getX()); pDireita = false; }
                    if (qEsquerda) { linha.getQ().setX(retanguloClipping.getP().getX()); qEsquerda = false; }
                    if (qDireita) { linha.getQ().setX(retanguloClipping.getQ().getX()); qDireita = false; }
                }
                else
                {
                    if (pEsquerda)
                    {
                        double xNovo = retanguloClipping.getP().getX();
                        double yNovo = linha.getP().getY() + m*(xNovo - linha.getP().getX());
                        if (yNovo >= retanguloClipping.getP().getY() && yNovo <= retanguloClipping.getQ().getY())
                        {
                            linha.getP().setX(xNovo);
                            linha.getP().setY(yNovo);
                        }
                    }
                    else if (pDireita)
                    {
                        double xNovo = retanguloClipping.getQ().getX();
                        double yNovo = linha.getP().getY() + m*(xNovo - linha.getP().getX());
                        if (yNovo >= retanguloClipping.getP().getY() && yNovo <= retanguloClipping.getQ().getY())
                        {
                            linha.getP().setX(xNovo);
                            linha.getP().setY(yNovo);
                        }
                    }
                    
                    if (pCima)
                    {
                        double yNovo = retanguloClipping.getP().getY();
                        double xNovo = linha.getP().getX() + (yNovo - linha.getP().getY())/m;
                        if (xNovo >= retanguloClipping.getP().getX() && xNovo <= retanguloClipping.getQ().getX())
                        {
                            linha.getP().setX(xNovo);
                            linha.getP().setY(yNovo);
                        }
                    }
                    else if (pBaixo)
                    {
                        double yNovo = retanguloClipping.getQ().getY();
                        double xNovo = linha.getP().getX() + (yNovo - linha.getP().getY())/m;
                        if (xNovo >= retanguloClipping.getP().getX() && xNovo <= retanguloClipping.getQ().getX())
                        {
                            linha.getP().setX(xNovo);
                            linha.getP().setY(yNovo);
                        }
                    }
                    
                    if (qEsquerda)
                    {
                        double xNovo = retanguloClipping.getP().getX();
                        double yNovo = linha.getQ().getY() + m*(xNovo - linha.getQ().getX());
                        if (yNovo >= retanguloClipping.getP().getY() && yNovo <= retanguloClipping.getQ().getY())
                        {
                            linha.getQ().setX(xNovo);
                            linha.getQ().setY(yNovo);
                        }
                    }
                    else if (qDireita)
                    {
                        double xNovo = retanguloClipping.getQ().getX();
                        double yNovo = linha.getQ().getY() + m*(xNovo - linha.getQ().getX());
                        if (yNovo >= retanguloClipping.getP().getY() && yNovo <= retanguloClipping.getQ().getY())
                        {
                            linha.getQ().setX(xNovo);
                            linha.getQ().setY(yNovo);
                        }
                    }
                    
                    if (qCima)
                    {
                        double yNovo = retanguloClipping.getP().getY();
                        double xNovo = linha.getQ().getX() + (yNovo - linha.getQ().getY())/m;
                        if (xNovo >= retanguloClipping.getP().getX() && xNovo <= retanguloClipping.getQ().getX())
                        {
                            linha.getQ().setX(xNovo);
                            linha.getQ().setY(yNovo);
                        }
                    }
                    else if (qBaixo)
                    {
                        double yNovo = retanguloClipping.getQ().getY();
                        double xNovo = linha.getQ().getX() + (yNovo - linha.getQ().getY())/m;
                        if (xNovo >= retanguloClipping.getP().getX() && xNovo <= retanguloClipping.getQ().getX())
                        {
                            linha.getQ().setX(xNovo);
                            linha.getQ().setY(yNovo);
                        }
                    }
                }
            }
            
            //remover linhas que estão completamente fora, mas que têm um ponto em cima/baixo e outro em esquerda/direita
            if ((linha.getP().getY() < retanguloClipping.getP().getY())
             || (linha.getQ().getY() < retanguloClipping.getP().getY())
             || (linha.getP().getY() > retanguloClipping.getQ().getY())
             || (linha.getQ().getY() > retanguloClipping.getQ().getY())
             || (linha.getP().getX() < retanguloClipping.getP().getX())
             || (linha.getQ().getX() < retanguloClipping.getP().getX())
             || (linha.getP().getX() > retanguloClipping.getQ().getX())
             || (linha.getQ().getX() > retanguloClipping.getQ().getX())) { remover = true; }
        }
        
        if (remover) { return null; }
        else { return linha; }
    }
}
