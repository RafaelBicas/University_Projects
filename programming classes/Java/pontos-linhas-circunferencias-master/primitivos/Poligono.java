 /**
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 */

package primitivos;

import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Poligono implements Figura
{
    private LinkedList<PontoMatematico> listaPontos = new LinkedList<PontoMatematico>();
    private Color cor;
    private int espessura;
    
    public Poligono(int espessura, Color cor) {
        this.cor = cor;
        this.espessura = espessura;
    }
    
    public void addPonto(PontoMatematico p) {
        listaPontos.addLast(p);
    }
    
    public int getEspessura() {
        return espessura;
    }
    
    public Color getCor() {
        return cor;
    }
    
    public PontoMatematico getPonto(int i) { 
        if(listaPontos != null && i < listaPontos.size()) {
            return listaPontos.get(i);
        } else return null;
    }
    
    public int getQtdPontos() {
        return listaPontos.size();
    }
    
    public void desenhar(GraphicsContext graphicsContext) {
        if(listaPontos != null) {
            int i = listaPontos.size();
            for(int k = 0; k < i-1; k++) {
                (new Linha(listaPontos.get(k), listaPontos.get(k+1), espessura, cor)).desenhar(graphicsContext);
            }
            (new Linha(listaPontos.getFirst(), listaPontos.getLast(), espessura, cor)).desenhar(graphicsContext);
        }
    }
    
    public Poligono clonar()
    {
        Poligono poligono = new Poligono(espessura, cor);
        int i = listaPontos.size();
        for(int k = 0; k < i; k++) {
            PontoMatematico p = getPonto(k);
            poligono.addPonto(p.clonar());
        }
        return poligono;
    }
    
    public Poligono fazerClipping(RetanguloDeClipping retanguloClipping)
    {
        //não implementado
        return this;
    }
}
