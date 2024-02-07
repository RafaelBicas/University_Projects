/**
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 */

package primitivos;

import java.lang.Math;

public class PontoMatematico
{
    double x;
    double y;
    
    public PontoMatematico(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public double getX()
    {
        return x;
    }
    
    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }

    public double calcularDistancia(PontoMatematico p)
    {
        return calcularDistancia(p.getX(), p.getY());
    }
    
    public double calcularDistancia(double x, double y)
    {
        double deltaX = Math.abs(this.x - x);
        double deltaY = Math.abs(this.y - y);
        return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
    }
    
    public PontoMatematico clonar()
    {
        return new PontoMatematico(x, y);
    }
}
