/**
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 */

package primitivos;

import javafx.scene.canvas.GraphicsContext;

public interface Figura
{
    public void desenhar(GraphicsContext graphicsContext);
    public Figura clonar();
    public Figura fazerClipping(RetanguloDeClipping retanguloClipping);
}
