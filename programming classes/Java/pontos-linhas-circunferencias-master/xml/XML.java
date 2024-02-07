/**
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 */

package xml;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException; 
import javax.xml.transform.TransformerException; 
import javax.xml.transform.TransformerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.util.LinkedList;
import java.util.Iterator;
import primitivos.Circunferencia;
import primitivos.Figura;
import primitivos.Linha;
import primitivos.Poligono;
import primitivos.Retangulo;
import primitivos.PontoGrafico;
import primitivos.PontoMatematico;
import javafx.scene.paint.Color;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.lang.ClassCastException;

public class XML
{
    public static void gravar(String arquivo, LinkedList<Figura> listaFiguras, int xMAX, int yMAX) {
        try {
           //Cria um novo documento para armazenar as figuras que foram desenhadas.
           DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
           Document documentoFormasGeometricas = documentBuilder.newDocument(); 
           
           Element figuraXML = documentoFormasGeometricas.createElement("Figura");
           documentoFormasGeometricas.appendChild(figuraXML);
           
           Iterator iterador = listaFiguras.iterator();
           while(iterador.hasNext()) {
               Figura figura = (Figura)iterador.next();
               
               if(figura instanceof PontoGrafico) { // caso o elemento seja um PONTO, com seus atributos, no caso: x, y e cor
                   Element ponto = documentoFormasGeometricas.createElement("Ponto"); //
                   Element x = documentoFormasGeometricas.createElement("x");
                   Element y = documentoFormasGeometricas.createElement("y");
                   PontoGrafico pontoXML = (PontoGrafico)figura;//O cast é usado para explicar ao compilador que o item é um pontoGráfico, pois ele acusa que a figura pode não ser está forma.
                   
                   x.appendChild(documentoFormasGeometricas.createTextNode(Double.toString((double)pontoXML.getX()/xMAX)));
                   y.appendChild(documentoFormasGeometricas.createTextNode(Double.toString((double)pontoXML.getY()/yMAX))); //Todo valor passado deve ser do tipo String.
                   
                   Element cor = documentoFormasGeometricas.createElement("Cor");
                   Element r = documentoFormasGeometricas.createElement("R");
                   Element g = documentoFormasGeometricas.createElement("G");
                   Element b = documentoFormasGeometricas.createElement("B");
                                      
                   r.appendChild(documentoFormasGeometricas.createTextNode(Integer.toString((int)(pontoXML.getCor().getRed() * 255.0))));
                   g.appendChild(documentoFormasGeometricas.createTextNode(Integer.toString((int)(pontoXML.getCor().getGreen() * 255.0))));
                   b.appendChild(documentoFormasGeometricas.createTextNode(Integer.toString((int)(pontoXML.getCor().getBlue() * 255.0))));
                   cor.appendChild(r);
                   cor.appendChild(g);
                   cor.appendChild(b);
                   
                   ponto.appendChild(x); //para indicar que o atributo x é de PONTO.
                   ponto.appendChild(y); //para indicar que o atributo y é de PONTO.
                   ponto.appendChild(cor);
                   figuraXML.appendChild(ponto); //Indicar que ponto é um filho de figuraXML 
                   
               } else if(figura instanceof Linha) { // caso o elemento seja uma Linha, com seus atributos: Ponto1(x, y), Ponto2(x, y) e cor
                   Element linha = documentoFormasGeometricas.createElement("Reta"); 
                   Element ponto1 = documentoFormasGeometricas.createElement("Ponto"); // Cada linha deve ter dois pontos
                   Element x1 = documentoFormasGeometricas.createElement("x");
                   Element y1 = documentoFormasGeometricas.createElement("y");
                   Element ponto2 = documentoFormasGeometricas.createElement("Ponto"); // Cada linha deve ter dois pontos
                   Element x2 = documentoFormasGeometricas.createElement("x");
                   Element y2 = documentoFormasGeometricas.createElement("y");
                   
                   Linha linhaXML = (Linha)figura;//O cast é usado para explicar ao compilador que o item é uma linha, pois ele acusa que a figura pode não ser está forma.
                   
                   x1.appendChild(documentoFormasGeometricas.createTextNode(Double.toString(linhaXML.getP().getX()/xMAX))); //Todo valor passado deve ser do tipo String.
                   y1.appendChild(documentoFormasGeometricas.createTextNode(Double.toString(linhaXML.getP().getY()/yMAX))); //Todo valor passado deve ser do tipo String.
                   ponto1.appendChild(x1); //para indicar que o atributo x é de LINHA.
                   ponto1.appendChild(y1); //para indicar que o atributo y é de LINHA.
                   
                   x2.appendChild(documentoFormasGeometricas.createTextNode(Double.toString(linhaXML.getQ().getX()/xMAX))); //Todo valor passado deve ser do tipo String.
                   y2.appendChild(documentoFormasGeometricas.createTextNode(Double.toString(linhaXML.getQ().getY()/yMAX))); //Todo valor passado deve ser do tipo String.
                   ponto2.appendChild(x2); //para indicar que o atributo x é de LINHA.
                   ponto2.appendChild(y2); //para indicar que o atributo y é de LINHA.
                   
                   Element cor = documentoFormasGeometricas.createElement("Cor");
                   Element r = documentoFormasGeometricas.createElement("R");
                   Element g = documentoFormasGeometricas.createElement("G");
                   Element b = documentoFormasGeometricas.createElement("B");
                   
                   r.appendChild(documentoFormasGeometricas.createTextNode(Integer.toString((int)(linhaXML.getCor().getRed() * 255.0))));
                   g.appendChild(documentoFormasGeometricas.createTextNode(Integer.toString((int)(linhaXML.getCor().getGreen() * 255.0))));
                   b.appendChild(documentoFormasGeometricas.createTextNode(Integer.toString((int)(linhaXML.getCor().getBlue() * 255.0))));
                   cor.appendChild(r);
                   cor.appendChild(g);
                   cor.appendChild(b);
                   
                   linha.appendChild(ponto1);
                   linha.appendChild(ponto2);
                   linha.appendChild(cor);
                   
                   figuraXML.appendChild(linha); //Indicar que linha é um filho de figuraXML
                   
               } else if(figura instanceof Circunferencia) { // caso o elemento seja uma Circunferencia, com seus atributos: Ponto1(x, y), raio e cor
                   Element circulo = documentoFormasGeometricas.createElement("Circulo");
                   
                   Element ponto = documentoFormasGeometricas.createElement("Ponto");
                   Element x = documentoFormasGeometricas.createElement("x");
                   Element y = documentoFormasGeometricas.createElement("y");
                   Element raio = documentoFormasGeometricas.createElement("Raio");
                   
                   Circunferencia circuloXML = (Circunferencia)figura; //O cast é usado para explicar ao compilador que o item é um círculo, pois ele acusa que a figura pode não ser está forma.
                   
                   x.appendChild(documentoFormasGeometricas.createTextNode(Double.toString(circuloXML.getCentro().getX()/xMAX))); //Todo valor passado deve ser do tipo String.
                   y.appendChild(documentoFormasGeometricas.createTextNode(Double.toString(circuloXML.getCentro().getY()/yMAX))); //Todo valor passado deve ser do tipo String.               
                   ponto.appendChild(x); //para indicar que o atributo x é de CIRCULO.
                   ponto.appendChild(y); //para indicar que o atributo y é de CIRCULO.
                   
                   raio.appendChild(documentoFormasGeometricas.createTextNode(Double.toString((circuloXML.getRaio())/xMAX)));
                   
                   Element cor = documentoFormasGeometricas.createElement("Cor");
                   Element r = documentoFormasGeometricas.createElement("R");
                   Element g = documentoFormasGeometricas.createElement("G");
                   Element b = documentoFormasGeometricas.createElement("B");
                   
                   r.appendChild(documentoFormasGeometricas.createTextNode(Integer.toString((int)(circuloXML.getCor().getRed() * 255.0))));
                   g.appendChild(documentoFormasGeometricas.createTextNode(Integer.toString((int)(circuloXML.getCor().getGreen() * 255.0))));
                   b.appendChild(documentoFormasGeometricas.createTextNode(Integer.toString((int)(circuloXML.getCor().getBlue() * 255.0))));
                   cor.appendChild(r);
                   cor.appendChild(g);
                   cor.appendChild(b);
                                                
                   circulo.appendChild(ponto);
                   circulo.appendChild(raio);
                   circulo.appendChild(cor);
                   
                   figuraXML.appendChild(circulo);
               
               } else if(figura instanceof Retangulo) { // caso o elemento seja um Retângulo, com seus atributos: Ponto1(x, y), Ponto2(x, y) e cor
                   Element retangulo = documentoFormasGeometricas.createElement("Retangulo"); 
                   Element ponto1 = documentoFormasGeometricas.createElement("Ponto"); // Cada linha deve ter dois pontos
                   Element x1 = documentoFormasGeometricas.createElement("x");
                   Element y1 = documentoFormasGeometricas.createElement("y");
                   Element ponto2 = documentoFormasGeometricas.createElement("Ponto"); // Cada linha deve ter dois pontos
                   Element x2 = documentoFormasGeometricas.createElement("x");
                   Element y2 = documentoFormasGeometricas.createElement("y");
                   
                   Retangulo retanguloXML = (Retangulo)figura;//O cast é usado para explicar ao compilador que o item é uma linha, pois ele acusa que a figura pode não ser está forma.
                   
                   x1.appendChild(documentoFormasGeometricas.createTextNode(Double.toString(retanguloXML.getP().getX()/xMAX))); //Todo valor passado deve ser do tipo String.
                   y1.appendChild(documentoFormasGeometricas.createTextNode(Double.toString(retanguloXML.getP().getY()/yMAX))); //Todo valor passado deve ser do tipo String.
                   ponto1.appendChild(x1); //para indicar que o atributo x é de CIRCULO.
                   ponto1.appendChild(y1); //para indicar que o atributo y é de CIRCULO.
                   
                   x2.appendChild(documentoFormasGeometricas.createTextNode(Double.toString(retanguloXML.getQ().getX()/xMAX))); //Todo valor passado deve ser do tipo String.
                   y2.appendChild(documentoFormasGeometricas.createTextNode(Double.toString(retanguloXML.getQ().getY()/yMAX))); //Todo valor passado deve ser do tipo String.
                   ponto2.appendChild(x2); //para indicar que o atributo x é de CIRCULO.
                   ponto2.appendChild(y2); //para indicar que o atributo y é de CIRCULO.
                   
                   Element cor = documentoFormasGeometricas.createElement("Cor");
                   Element r = documentoFormasGeometricas.createElement("R");
                   Element g = documentoFormasGeometricas.createElement("G");
                   Element b = documentoFormasGeometricas.createElement("B");
                   
                   r.appendChild(documentoFormasGeometricas.createTextNode(Integer.toString((int)(retanguloXML.getCor().getRed() * 255.0))));
                   g.appendChild(documentoFormasGeometricas.createTextNode(Integer.toString((int)(retanguloXML.getCor().getGreen() * 255.0))));
                   b.appendChild(documentoFormasGeometricas.createTextNode(Integer.toString((int)(retanguloXML.getCor().getBlue() * 255.0))));
                   cor.appendChild(r);
                   cor.appendChild(g);
                   cor.appendChild(b);
                                 
                   retangulo.appendChild(ponto1);
                   retangulo.appendChild(ponto2);               
                   retangulo.appendChild(cor);
                   
                   figuraXML.appendChild(retangulo); //Indicar que circulo é um filho de figuraXML
                   
               } else if(figura instanceof Poligono) {
                   Element poligono = documentoFormasGeometricas.createElement("Poligono");
                   
                   Poligono poligonoXML = (Poligono)figura;
                   int i = poligonoXML.getQtdPontos();
                   for(int k = 0; k < i; k++) {
                       Element ponto = documentoFormasGeometricas.createElement("Ponto");
                       Element x = documentoFormasGeometricas.createElement("x");
                       Element y = documentoFormasGeometricas.createElement("y");
                       x.appendChild(documentoFormasGeometricas.createTextNode(Double.toString(poligonoXML.getPonto(k).getX()/xMAX))); //Todo valor passado deve ser do tipo String.
                       y.appendChild(documentoFormasGeometricas.createTextNode(Double.toString(poligonoXML.getPonto(k).getY()/yMAX))); //Todo valor passado deve ser do tipo String.
                       ponto.appendChild(x); //para indicar que o atributo x é de POLIGONO.
                       ponto.appendChild(y); //para indicar que o atributo y é de POLIGONO.
                       
                       poligono.appendChild(ponto);
                       figuraXML.appendChild(poligono); //Indicar que circulo é um filho de figuraXML
                   }
                   Element cor = documentoFormasGeometricas.createElement("Cor");
                   Element r = documentoFormasGeometricas.createElement("R");
                   Element g = documentoFormasGeometricas.createElement("G");
                   Element b = documentoFormasGeometricas.createElement("B");
                   
                   r.appendChild(documentoFormasGeometricas.createTextNode(Integer.toString((int)(poligonoXML.getCor().getRed() * 255.0))));
                   g.appendChild(documentoFormasGeometricas.createTextNode(Integer.toString((int)(poligonoXML.getCor().getGreen() * 255.0))));
                   b.appendChild(documentoFormasGeometricas.createTextNode(Integer.toString((int)(poligonoXML.getCor().getBlue() * 255.0))));
                   cor.appendChild(r);
                   cor.appendChild(g);
                   cor.appendChild(b);
                    
                   poligono.appendChild(cor);
               }
           }
           
           TransformerFactory transformerFactory = TransformerFactory.newInstance();
           Transformer transformer = transformerFactory.newTransformer();
           DOMSource documentoFonte = new DOMSource(documentoFormasGeometricas);
           StreamResult documentoFinal = new StreamResult(new File(arquivo));
           transformer.transform(documentoFonte, documentoFinal);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
        } catch(TransformerConfigurationException ex) {
            Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
        } catch(TransformerException ex) {
            Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static LinkedList<Figura> ler(String arquivo, int espessura, int xMAX, int yMAX)
    {
        LinkedList<Figura> listaFiguras = new LinkedList<Figura>(); //lista que será retornada com as figuras carregadas do arquivo
        try {
            DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            Document doc = builder.parse(arquivo);
            
            Element elementoFigura = (Element)doc.getElementsByTagName("Figura").item(0); //o arquivo deve conter exatamente um elemento do tipo figura, que contém todos os outros
            NodeList listaPrimitivos = elementoFigura.getChildNodes();
            double raio = 0;
            
            int tamanhoLista = listaPrimitivos.getLength();
            for (int i = 0; i < tamanhoLista; i++)
            {
                Node no = listaPrimitivos.item(i);
                Element elemento = (Element)no; //o cast é necessário para obter o TagName (tipo de elemento)
                switch(elemento.getTagName())
                {
                    case "Ponto":
                        PontoGrafico ponto = lerPontoGrafico(elemento, espessura, xMAX, yMAX);
                        listaFiguras.addLast(ponto);
                        break;
                    
                    case "Reta":
                        Linha linha = lerLinha(elemento, espessura, xMAX, yMAX);
                        listaFiguras.addLast(linha);
                        break;
                    
                    case "Circulo":
                        Circunferencia circunferencia = lerCircunferencia(elemento, espessura, xMAX, yMAX);
                        listaFiguras.addLast(circunferencia);
                        break;
                    
                    case "Retangulo":
                        Retangulo retangulo = lerRetangulo(elemento, espessura, xMAX, yMAX);
                        listaFiguras.addLast(retangulo);
                        break;
                    
                    case "Poligono":
                        Poligono poligono = lerPoligono(elemento, espessura, xMAX, yMAX);
                        listaFiguras.addLast(poligono);
                        break;
                }
            }
        } catch (ParserConfigurationException ex){
            Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
        } catch(SAXException ex) {
            Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex) {
            Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
        } catch(ClassCastException ex) {
            Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaFiguras;
    }
    
    static PontoMatematico lerPontoMatematico(Element elementoPonto, int xMAX, int yMAX)
    {
        //inicializar todas as variáveis com um valor padrão é importante para evitar erros no programa caso o arquivo esteja incompleto
        double x = 0, y = 0;
        
        NodeList listaFilhos = elementoPonto.getChildNodes();
        int tamanhoLista = listaFilhos.getLength();
        for(int j = 0; j < tamanhoLista; j++)
        {
            Node noFilho = listaFilhos.item(j);
            Element elementoFilho = (Element)noFilho; //o cast é necessário para obter o TagName (tipo de elemento)
            switch(elementoFilho.getTagName())
            {
                case "x":
                    x = Double.parseDouble(elementoFilho.getTextContent()) * xMAX;
                    break;
                
                case "y":
                    y = Double.parseDouble(elementoFilho.getTextContent()) * yMAX;                                
                    break;
            }
        }
        return new PontoMatematico(x, y);
    }
    
    static Color lerCor(Element elementoCor)
    {
        //inicializar todas as variáveis com um valor padrão é importante para evitar erros no programa caso o arquivo esteja incompleto
        int r = 0, g = 0, b = 0;
        
        NodeList listaFilhos = elementoCor.getChildNodes();
        int tamanhoLista = listaFilhos.getLength();
        for(int j = 0; j < tamanhoLista; j++)
        {
            Node noFilho = listaFilhos.item(j);
            Element elementoFilho = (Element)noFilho; //o cast é necessário para obter o TagName (tipo de elemento)
            
            switch(elementoFilho.getTagName())
            {
                case "R":
                    r = Integer.parseInt(elementoFilho.getTextContent());
                    break;
                
                case "G":
                    g = Integer.parseInt(elementoFilho.getTextContent());                               
                    break;
                
                case "B":
                    b = Integer.parseInt(elementoFilho.getTextContent());                               
                    break;
            }
        }
        
        return Color.rgb(r, g, b);
    }
    
    static PontoGrafico lerPontoGrafico(Element elementoPonto, int espessura, int xMAX, int yMAX)
    {
        //inicializar todas as variáveis com um valor padrão é importante para evitar erros no programa caso o arquivo esteja incompleto
        double x = 0, y = 0;
        Color cor = Color.BLACK;
        
        NodeList listaFilhos = elementoPonto.getChildNodes();
        int tamanhoLista = listaFilhos.getLength();
        for(int j = 0; j < tamanhoLista; j++)
        {
            Node noFilho = listaFilhos.item(j);
            Element elementoFilho = (Element)noFilho; //o cast é necessário para obter o TagName (tipo de elemento)
            switch(elementoFilho.getTagName())
            {
                case "x":
                    x = Double.parseDouble(elementoFilho.getTextContent()) * xMAX;
                    break;
                
                case "y":
                    y = Double.parseDouble(elementoFilho.getTextContent()) * yMAX;                                
                    break;
                
                case "Cor":
                    cor = lerCor(elementoFilho);
                    break;
            }
        }
        return new PontoGrafico((int)x, (int)y, espessura, cor);
    }
    
    static Linha lerLinha(Element elementoLinha, int espessura, int xMAX, int yMAX)
    {
        //inicializar todas as variáveis com um valor padrão é importante para evitar erros no programa caso o arquivo esteja incompleto
        PontoMatematico[] pontos = new PontoMatematico[2];
        pontos[0] = new PontoMatematico(0, 0);
        pontos[1] = new PontoMatematico(0, 0);
        int indice = 0;
        Color cor = Color.BLACK;
        
        NodeList listaFilhos = elementoLinha.getChildNodes();
        int tamanhoLista = listaFilhos.getLength();
        for(int j = 0; j < tamanhoLista; j++)
        {
            Node noFilho = listaFilhos.item(j);
            Element elementoFilho = (Element)noFilho; //o cast é necessário para obter o TagName (tipo de elemento)
            switch(elementoFilho.getTagName())
            {
                case "Ponto":
                    pontos[indice++] = lerPontoMatematico(elementoFilho, xMAX, yMAX);
                    break;
                
                case "Cor":
                    cor = lerCor(elementoFilho);
                    break;
            }
        }
        return new Linha(pontos[0], pontos[1], espessura, cor);
    }
    
    static Circunferencia lerCircunferencia(Element elementoCircunferencia, int espessura, int xMAX, int yMAX)
    {
        //inicializar todas as variáveis com um valor padrão é importante para evitar erros no programa caso o arquivo esteja incompleto
        PontoMatematico centro = new PontoMatematico(0, 0);
        double raio = 0.0;
        Color cor = Color.BLACK;
        
        NodeList listaFilhos = elementoCircunferencia.getChildNodes();
        int tamanhoLista = listaFilhos.getLength();
        for(int j = 0; j < tamanhoLista; j++)
        {
            Node noFilho = listaFilhos.item(j);
            Element elementoFilho = (Element)noFilho; //o cast é necessário para obter o TagName (tipo de elemento)
            switch(elementoFilho.getTagName())
            {
                case "Ponto":
                    centro = lerPontoMatematico(elementoFilho, xMAX, yMAX);
                    break;
                
                case "Raio":
                    raio = Double.parseDouble(elementoFilho.getTextContent()) * xMAX;
                    break;
                
                case "Cor":
                    cor = lerCor(elementoFilho);
                    break;
            }
        }
        return new Circunferencia(centro, raio, espessura, cor);
    }
    
    static Retangulo lerRetangulo(Element elementoRetangulo, int espessura, int xMAX, int yMAX)
    {
        //inicializar todas as variáveis com um valor padrão é importante para evitar erros no programa caso o arquivo esteja incompleto
        PontoMatematico[] pontos = new PontoMatematico[2];
        pontos[0] = new PontoMatematico(0, 0);
        pontos[1] = new PontoMatematico(0, 0);
        int indice = 0;
        Color cor = Color.BLACK;
        
        NodeList listaFilhos = elementoRetangulo.getChildNodes();
        int tamanhoLista = listaFilhos.getLength();
        for(int j = 0; j < tamanhoLista; j++)
        {
            Node noFilho = listaFilhos.item(j);
            Element elementoFilho = (Element)noFilho; //o cast é necessário para obter o TagName (tipo de elemento)
            switch(elementoFilho.getTagName())
            {
                case "Ponto":
                    pontos[indice++] = lerPontoMatematico(elementoFilho, xMAX, yMAX);
                    break;
                
                case "Cor":
                    cor = lerCor(elementoFilho);
                    break;
            }
        }
        return new Retangulo(pontos[0], pontos[1], espessura, cor);
    }
    
    static Poligono lerPoligono(Element elementoPoligono, int espessura, int xMAX, int yMAX)
    {
        //inicializar todas as variáveis com um valor padrão é importante para evitar erros no programa caso o arquivo esteja incompleto
        LinkedList<PontoMatematico> listaPontos = new LinkedList<PontoMatematico>(); //a lista estando vazia, por padrão o polígono não terá pontos
        Color cor = Color.BLACK;
        
        NodeList listaFilhos = elementoPoligono.getChildNodes();
        int tamanhoLista = listaFilhos.getLength();
        for(int j = 0; j < tamanhoLista; j++)
        {
            Node noFilho = listaFilhos.item(j);
            Element elementoFilho = (Element)noFilho; //o cast é necessário para obter o TagName (tipo de elemento)
            switch(elementoFilho.getTagName())
            {
                case "Ponto":
                    PontoMatematico ponto = lerPontoMatematico(elementoFilho, xMAX, yMAX);
                    listaPontos.addLast(ponto);
                    break;
                
                case "Cor":
                    cor = lerCor(elementoFilho);
                    break;
            }
        }
        
        Poligono poligono = new Poligono(espessura, cor);
        tamanhoLista = listaPontos.size();
        for(int i = 0; i < tamanhoLista; i++) { poligono.addPonto(listaPontos.get(i)); }
        return poligono;
    }
}