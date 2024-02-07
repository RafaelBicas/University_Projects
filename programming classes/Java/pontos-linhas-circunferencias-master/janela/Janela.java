/**
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 */

package janela;

import java.lang.Math;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import primitivos.Circunferencia;
import primitivos.CircunferenciaComClipping;
import primitivos.CircunferenciaPreenchida;
import primitivos.Figura;
import primitivos.Linha;
import primitivos.Poligono;
import primitivos.Retangulo;
import primitivos.RetanguloComClipping;
import primitivos.RetanguloDeClipping;
import primitivos.PontoGrafico;
import primitivos.PontoMatematico;
import java.io.File;
import xml.XML;

public class Janela
{
    enum Ferramenta { PONTO, LINHA, CIRCUNFERENCIA, RETANGULO, POLIGONO }
    
    final int larguraDaImagem = 800;
    final int alturaDaImagem = 600;
    final float escalaMapa = 1/4f;
    final int larguraDoMapa = Math.round(larguraDaImagem * escalaMapa);
    final int alturaDoMapa = Math.round(alturaDaImagem * escalaMapa);
    Ferramenta ferramentaSelecionada = Ferramenta.PONTO;
    boolean primeiroCliqueDadoFerramenta = false; //controla se o primeiro clique já foi dado, e portanto é necessário apenas dar o segundo para criar a figura
    PontoMatematico pontoPrimeiroCliqueFerramenta; //ponto definido no primeiro clique
    int espessuraAtual = 5;
    
    boolean clippingAtivado = false;
    boolean primeiroCliqueDadoClipping = false; //controla se o primeiro clique já foi dado ao definir o retângulo de clipping
    PontoMatematico pontoPrimeiroCliqueClipping; //ponto definido no primeiro clique do retângulo de clipping
    RetanguloDeClipping retanguloClipping = null;
    Poligono poligonoTemporario = null; //utilizada para o desenho de polígono (o polígono pode utilizar mais de dois pontos, então a variável de ponto do primeiro clique não serve)
    
    Color corDesenhoComum = Color.BLACK, corVogais = Color.BLUE;
    
    GraphicsContext graphicsContextDesenhoComum, graphicsContextMapa;
    
    LinkedList<Figura> listaFiguras = new LinkedList<Figura>();
    Figura figuraTemporaria; //utilizada para os desenhos de preview (visualizar como ficará linha, circunferencia etc. antes do clique final)
    
    //constantes usadas no desenho das vogais
    final int tamanhoMaximo = 6;
    final int raioPreenchimento = 12;
    final int diametroPreenchimento = raioPreenchimento * 2;
    final int espacamento = 8;
    final boolean[][] A = {{true, true, true},
                                  {true, false, true},
                                  {true, true, true},
                                  {true, false, true},
                                  {true, false, true}};
    final boolean[][] E = {{true, true, true},
                                  {true, false, false},
                                  {true, true, true},
                                  {true, false, false},
                                  {true, true, true}};
    final boolean[][] I = {{true, true, true},
                                  {false, true, false},
                                  {false, true, false},
                                  {false, true, false},
                                  {true, true, true}};
    final boolean[][] O = {{true, true, true},
                                  {true, false, true},
                                  {true, false, true},
                                  {true, false, true},
                                  {true, true, true}};
    final boolean[][] U = {{true, false, true},
                                  {true, false, true},
                                  {true, false, true},
                                  {true, false, true},
                                  {true, true, true}};
    
    public Janela(Stage palco)
    {
        VBox painelEdicaoDesenhoComum = new VBox();
        inicializarPainelEdicaoDesenhoComum(painelEdicaoDesenhoComum, palco);
        
        VBox painelMapaDesenhoComum = new VBox();
        inicializarPainelMapaDesenhoComum(painelMapaDesenhoComum, palco);
        
        HBox painelGeralDesenhoComum = new HBox();
        painelGeralDesenhoComum.getChildren().add(painelEdicaoDesenhoComum);
        painelGeralDesenhoComum.getChildren().add(painelMapaDesenhoComum);
        Tab abaDesenhoComum = new Tab("Desenho comum", painelGeralDesenhoComum);
        
        VBox painelVogais = new VBox();
        inicializarPainelVogais(painelVogais);
        Tab abaVogais = new Tab("Vogais", painelVogais);
        
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE); //impedir que as abas sejam fechadas
        tabPane.getTabs().add(abaDesenhoComum);
        tabPane.getTabs().add(abaVogais);
        
        //painel geral usado como conteúdo da cena
        BorderPane painel = new BorderPane();
        painel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null))); //definir cor de fundo
        painel.setCenter(tabPane);
        
        Scene cena = new Scene(painel);
        palco.setScene(cena);
        
        /* no javafx, é interessante não definir tamanho dos componentes explicitamente quando não é necessário.
           isso faz com que cada componente se redimensione para que seus componentes filhos caibam nele,
           sem sobrar ou faltar espaço (inclusive a janela).
           neste caso, definimos o tamanho apenas do canvas (como parâmetros no seu construtor),
           já que este precisamos que tenha um tamanho definido para termos uma base de tamanho da imagem gerada */
        
        palco.show();
    }
    
    void inicializarPainelEdicaoDesenhoComum(VBox painelEdicaoDesenhoComum, Stage palco)
    {
        Canvas canvasDesenhoComum = new Canvas(larguraDaImagem, alturaDaImagem);
        graphicsContextDesenhoComum = canvasDesenhoComum.getGraphicsContext2D();
        
        ToggleButton botaoPonto = new ToggleButton("Ponto");
        botaoPonto.setSelected(true);
        palco.setTitle("Clique para desenhar um ponto"); //definir título de ponto inicialmente, já que o botão de ponto começa selecionado
        ToggleButton botaoLinha = new ToggleButton("Linha");
        ToggleButton botaoCirculo = new ToggleButton("Círculo");
        ToggleButton botaoRetangulo = new ToggleButton("Retângulo");
        ToggleButton botaoPoligono = new ToggleButton("Polígono");
        
        //o togglegroup impede que mais de um botão esteja selecionado ao mesmo tempo
        ToggleGroup grupoBotoes = new ToggleGroup();
        botaoPonto.setToggleGroup(grupoBotoes);
        botaoLinha.setToggleGroup(grupoBotoes);
        botaoCirculo.setToggleGroup(grupoBotoes);
        botaoRetangulo.setToggleGroup(grupoBotoes);
        botaoPoligono.setToggleGroup(grupoBotoes);
        
        botaoPonto.setOnAction(e ->
        {
            if (grupoBotoes.getSelectedToggle() == null) //se não há botão selecionado, significa que este botão estava selecionado e foi desselecionado
            {
                botaoPonto.setSelected(true); //precisamos manter sempre um botão selecionado, então vamos selecioná-lo novamente
                //efetivamente, estamos impedindo que clicar em um botão já selecionado o faça desselecionar,
                //já que a desseleção só é permitida quando o clique é feito em outro botão
            }
            ferramentaSelecionada = Ferramenta.PONTO;
            primeiroCliqueDadoFerramenta = false; //resetar variável para caso o usuário troque de ferramenta enquanto já havia definido um ponto em outra ferramenta
            figuraTemporaria = null; //resetar figura temporária, visto que quando ocorre troca de ferramenta não há mais pontos definidos para preview
            redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa); //redesenhar para caso uma figura temporária estar sendo desenhada quando trocar de ferramenta
            palco.setTitle("Clique para desenhar um ponto");
        });
        botaoLinha.setOnAction(e ->
        {
            if (grupoBotoes.getSelectedToggle() == null) //se não há botão selecionado, significa que este botão estava selecionado e foi desselecionado
            {
                botaoLinha.setSelected(true); //precisamos manter sempre um botão selecionado, então vamos selecioná-lo novamente
                //efetivamente, estamos impedindo que clicar em um botão já selecionado o faça desselecionar,
                //já que a desseleção só é permitida quando o clique é feito em outro botão
            }
            ferramentaSelecionada = Ferramenta.LINHA;
            primeiroCliqueDadoFerramenta = false; //resetar variável para caso o usuário troque de ferramenta enquanto já havia definido um ponto em outra ferramenta
            figuraTemporaria = null; //resetar figura temporária, visto que quando ocorre troca de ferramenta não há mais pontos definidos para preview
            redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa); //redesenhar para caso uma figura temporária estar sendo desenhada quando trocar de ferramenta
            palco.setTitle("Clique para definir o ponto de início da linha");
        });
        botaoCirculo.setOnAction(e ->
        {
            if (grupoBotoes.getSelectedToggle() == null) //se não há botão selecionado, significa que este botão estava selecionado e foi desselecionado
            {
                botaoCirculo.setSelected(true); //precisamos manter sempre um botão selecionado, então vamos selecioná-lo novamente
                //efetivamente, estamos impedindo que clicar em um botão já selecionado o faça desselecionar,
                //já que a desseleção só é permitida quando o clique é feito em outro botão
            }
            ferramentaSelecionada = Ferramenta.CIRCUNFERENCIA;
            primeiroCliqueDadoFerramenta = false; //resetar variável para caso o usuário troque de ferramenta enquanto já havia definido um ponto em outra ferramenta
            figuraTemporaria = null; //resetar figura temporária, visto que quando ocorre troca de ferramenta não há mais pontos definidos para preview
            redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa); //redesenhar para caso uma figura temporária estar sendo desenhada quando trocar de ferramenta
            palco.setTitle("Clique para definir o ponto de centro da circunferência");
        });
        botaoRetangulo.setOnAction(e ->
        {
            if (grupoBotoes.getSelectedToggle() == null) //se não há botão selecionado, significa que este botão estava selecionado e foi desselecionado
            {
                botaoRetangulo.setSelected(true); //precisamos manter sempre um botão selecionado, então vamos selecioná-lo novamente
                //efetivamente, estamos impedindo que clicar em um botão já selecionado o faça desselecionar,
                //já que a desseleção só é permitida quando o clique é feito em outro botão
            }
            ferramentaSelecionada = Ferramenta.RETANGULO;
            primeiroCliqueDadoFerramenta = false; //resetar variável para caso o usuário troque de ferramenta enquanto já havia definido um ponto em outra ferramenta
            figuraTemporaria = null; //resetar figura temporária, visto que quando ocorre troca de ferramenta não há mais pontos definidos para preview
            redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa); //redesenhar para caso uma figura temporária estar sendo desenhada quando trocar de ferramenta
            palco.setTitle("Clique para definir o ponto de início do retângulo");
        });
        botaoPoligono.setOnAction(e ->
        {
           if(grupoBotoes.getSelectedToggle() == null) {
               botaoPoligono.setSelected(true); //precisamos manter sempre um botão selecionado, então vamos selecioná-lo novamente
                //efetivamente, estamos impedindo que clicar em um botão já selecionado o faça desselecionar,
                //já que a desseleção só é permitida quando o clique é feito em outro botão
           }
           ferramentaSelecionada = Ferramenta.POLIGONO;
           primeiroCliqueDadoFerramenta = false; //resetar variável para caso o usuário troque de ferramenta enquanto já havia definido um ponto em outra ferramenta
           figuraTemporaria = null; //resetar figura temporária, visto que quando ocorre troca de ferramenta não há mais pontos definidos para preview
           redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa); //redesenhar para caso uma figura temporária estar sendo desenhada quando trocar de ferramenta
           palco.setTitle("Clique para definir o primeiro ponto do polígono");
        });
        
        ColorPicker colorPickerDesenhoComum = new ColorPicker();
        colorPickerDesenhoComum.setValue(corDesenhoComum);
        colorPickerDesenhoComum.setOnAction(e -> {
                corDesenhoComum = colorPickerDesenhoComum.getValue();
        });
        
        Spinner spinnerDesenhoComum = new Spinner(1, 100, 5, 1); //vai de 1 a 100, valor padrão 5, com incremento 1
        spinnerDesenhoComum.setPrefWidth(55);
        spinnerDesenhoComum.valueProperty().addListener((observable, valorAntigo, valorNovo) -> espessuraAtual = (int)valorNovo);
        
        CheckBox checkBoxClipping = new CheckBox("Clipping");
        checkBoxClipping.setOnAction(e ->
        {
            if (checkBoxClipping.isSelected())
            {
                clippingAtivado = true;
            }
            else
            {
                clippingAtivado = false;
            }
            redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa);
        });
        
        Button botaoDesfazer = new Button("Desfazer");
        botaoDesfazer.setOnAction(e ->
        {
            if (!listaFiguras.isEmpty())
            {
                listaFiguras.removeLast();
                redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa);
            }
        });
        
        Button botaoLimpar = new Button("Limpar");
        botaoLimpar.setOnAction(e ->
        {
            listaFiguras.clear();
            primeiroCliqueDadoFerramenta = false; //resetar variável para caso o usuário troque de ferramenta enquanto já havia definido um ponto em outra ferramenta
            figuraTemporaria = null; //resetar figura temporária, visto que quando a imagem é limpa não há mais pontos definidos para preview
            redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa);
        });
        
        ToolBar toolBarDesenhoComum = new ToolBar();
        toolBarDesenhoComum.getItems().add(botaoPonto);
        toolBarDesenhoComum.getItems().add(botaoLinha);
        toolBarDesenhoComum.getItems().add(botaoCirculo);
        toolBarDesenhoComum.getItems().add(botaoRetangulo);
        toolBarDesenhoComum.getItems().add(botaoPoligono);
        toolBarDesenhoComum.getItems().add(colorPickerDesenhoComum);
        toolBarDesenhoComum.getItems().add(spinnerDesenhoComum);
        toolBarDesenhoComum.getItems().add(checkBoxClipping);
        toolBarDesenhoComum.getItems().add(botaoDesfazer);
        toolBarDesenhoComum.getItems().add(botaoLimpar);
        
        canvasDesenhoComum.setOnMouseMoved(e ->
        {
            boolean redesenhar = false;
            if (figuraTemporaria != null)
            {
                switch (ferramentaSelecionada)
                {
                    case LINHA:
                        Linha linhaTemporaria = new Linha(pontoPrimeiroCliqueFerramenta, new PontoMatematico(e.getX(), e.getY()), espessuraAtual, corDesenhoComum);
                        figuraTemporaria = linhaTemporaria;
                        break;
                    case CIRCUNFERENCIA:
                        double raio = pontoPrimeiroCliqueFerramenta.calcularDistancia(e.getX(), e.getY());
                        Circunferencia circunferenciaTemporaria = new Circunferencia(pontoPrimeiroCliqueFerramenta, raio, espessuraAtual, corDesenhoComum);
                        figuraTemporaria = circunferenciaTemporaria;
                        break;
                    case RETANGULO:
                        Retangulo retanguloTemporario = new Retangulo(pontoPrimeiroCliqueFerramenta, new PontoMatematico(e.getX(), e.getY()), espessuraAtual, corDesenhoComum);
                        figuraTemporaria = retanguloTemporario;
                        break;
                    case POLIGONO:
                        Poligono p = new Poligono(espessuraAtual, corDesenhoComum);
                        int i = poligonoTemporario.getQtdPontos();
                        for(int k = 0; k < i; k++) {
                            p.addPonto(poligonoTemporario.getPonto(k)); 
                        }
                        p.addPonto(new PontoMatematico(e.getX(), e.getY()));
                        figuraTemporaria = p;
                        break;
                }
                redesenhar = true;
            }
            if (clippingAtivado && primeiroCliqueDadoClipping)
            {
                retanguloClipping = new RetanguloDeClipping(pontoPrimeiroCliqueClipping, new PontoMatematico(e.getX(), e.getY()), 1, corDesenhoComum);
                redesenhar = true;
            }
            
            if (redesenhar) { redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa); }
        });
        
        canvasDesenhoComum.setOnMousePressed(e ->
        {
            if (clippingAtivado && e.getButton() == MouseButton.SECONDARY)
            {
                if (primeiroCliqueDadoClipping)
                {
                    retanguloClipping = new RetanguloDeClipping(pontoPrimeiroCliqueClipping, new PontoMatematico(e.getX(), e.getY()), 1, corDesenhoComum);                    
                    primeiroCliqueDadoClipping = false;
                }
                else
                {
                    pontoPrimeiroCliqueClipping = new PontoMatematico(e.getX(), e.getY());
                    retanguloClipping = new RetanguloDeClipping(pontoPrimeiroCliqueClipping, pontoPrimeiroCliqueClipping, 1, corDesenhoComum);
                    primeiroCliqueDadoClipping = true;
                }
                                
                redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa);
            }
            else if (e.getButton() == MouseButton.PRIMARY) switch (ferramentaSelecionada)
            {
                case PONTO:
                    PontoGrafico ponto = new PontoGrafico((int)e.getX(), (int)e.getY(), espessuraAtual, corDesenhoComum);
                    listaFiguras.addLast(ponto);
                    redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa);
                    break;
                
                case LINHA:
                    if (primeiroCliqueDadoFerramenta)
                    {
                        Linha linha = new Linha(pontoPrimeiroCliqueFerramenta, new PontoMatematico(e.getX(), e.getY()), espessuraAtual, corDesenhoComum);
                        listaFiguras.addLast(linha);
                        primeiroCliqueDadoFerramenta = false;
                        figuraTemporaria = null;
                        redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa);
                        palco.setTitle("Clique para definir o ponto de início da linha");
                    }
                    else
                    {
                        pontoPrimeiroCliqueFerramenta = new PontoMatematico(e.getX(), e.getY());
                        primeiroCliqueDadoFerramenta = true;
                        figuraTemporaria = new Linha(pontoPrimeiroCliqueFerramenta, pontoPrimeiroCliqueFerramenta, espessuraAtual, corDesenhoComum);
                        palco.setTitle("Clique para definir o ponto de fim da linha");
                    }
                    break;
                
                case CIRCUNFERENCIA:
                    if (primeiroCliqueDadoFerramenta)
                    {
                        double raio = pontoPrimeiroCliqueFerramenta.calcularDistancia(e.getX(), e.getY());
                        Circunferencia circunferencia = new Circunferencia(pontoPrimeiroCliqueFerramenta, raio, espessuraAtual, corDesenhoComum);
                        listaFiguras.addLast(circunferencia);
                        primeiroCliqueDadoFerramenta = false;
                        figuraTemporaria = null;
                        redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa);
                        palco.setTitle("Clique para definir o ponto de centro da circunferência");
                    }
                    else
                    {
                        pontoPrimeiroCliqueFerramenta = new PontoMatematico(e.getX(), e.getY());
                        primeiroCliqueDadoFerramenta = true;
                        double raio = pontoPrimeiroCliqueFerramenta.calcularDistancia(e.getX(), e.getY());
                        figuraTemporaria = new Circunferencia(pontoPrimeiroCliqueFerramenta, raio, espessuraAtual, corDesenhoComum);
                        palco.setTitle("Clique para definir o raio da circunferência");
                    }
                    break;
                
                case RETANGULO:
                    if (primeiroCliqueDadoFerramenta)
                    {
                        Retangulo retangulo = new Retangulo(pontoPrimeiroCliqueFerramenta, new PontoMatematico(e.getX(), e.getY()), espessuraAtual, corDesenhoComum);
                        listaFiguras.addLast(retangulo);
                        primeiroCliqueDadoFerramenta = false;
                        figuraTemporaria = null;
                        redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa);
                        palco.setTitle("Clique para definir o ponto de início do retângulo");
                    }
                    else
                    {
                        pontoPrimeiroCliqueFerramenta = new PontoMatematico(e.getX(), e.getY());
                        primeiroCliqueDadoFerramenta = true;
                        figuraTemporaria = new Retangulo(pontoPrimeiroCliqueFerramenta, pontoPrimeiroCliqueFerramenta, espessuraAtual, corDesenhoComum);
                        palco.setTitle("Clique para definir o ponto de fim do retângulo");
                    }
                    break;
                    
                case POLIGONO:
                    if (primeiroCliqueDadoFerramenta)
                    {
                        if(e.getClickCount() == 2) {
                            primeiroCliqueDadoFerramenta = false;
                            listaFiguras.addLast(poligonoTemporario);
                            poligonoTemporario = null;
                            figuraTemporaria = null;
                            palco.setTitle("Clique para definir o primeiro ponto do polígono");
                        }
                        else
                        {
                            poligonoTemporario.addPonto(new PontoMatematico(e.getX(), e.getY()));
                        }
                        redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa);
                    }
                    else
                    {
                        poligonoTemporario = new Poligono(espessuraAtual, corDesenhoComum); 
                        primeiroCliqueDadoFerramenta = true;
                        poligonoTemporario.addPonto(new PontoMatematico(e.getX(), e.getY()));
                        figuraTemporaria = poligonoTemporario;
                        palco.setTitle("Clique uma vez para definir o próximo ponto do polígono, ou dê um clique duplo para inserir o último ponto e terminar o polígono");
                    }
                    break;
            }
        });
        
        painelEdicaoDesenhoComum.getChildren().add(toolBarDesenhoComum);
        painelEdicaoDesenhoComum.getChildren().add(canvasDesenhoComum);
    }
    
    void inicializarPainelMapaDesenhoComum(VBox painelEdicaoDesenhoComum, Stage palco)
    {
        int larguraCanvas = larguraDoMapa, alturaCanvas = alturaDoMapa;
        Canvas canvasMapa = new Canvas(larguraCanvas, alturaCanvas);
        graphicsContextMapa = canvasMapa.getGraphicsContext2D();
        graphicsContextMapa.setFill(Color.WHITE);
        graphicsContextMapa.fillRect(0, 0, larguraCanvas, alturaCanvas);
        
        Button botaoSalvar = new Button("Salvar");
        botaoSalvar.setOnAction(e ->
        {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Arquivos XML (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(filter);
 
            File arquivo = fileChooser.showSaveDialog(palco);
 
            if (arquivo != null) {
                XML.gravar(arquivo.getAbsolutePath(), listaFiguras, larguraDaImagem, alturaDaImagem);
            }
        });
        
        Button botaoAbrir = new Button("Abrir");
        botaoAbrir.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Arquivos XML (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(filter);
 
            File arquivo = fileChooser.showOpenDialog(palco);
 
            if (arquivo != null) {
                listaFiguras = XML.ler(arquivo.getAbsolutePath(), espessuraAtual, larguraDaImagem, alturaDaImagem);
                redesenharDesenhoComum(graphicsContextDesenhoComum, graphicsContextMapa);
            }
        });
        
        //configurar painel de edição para suportar o painel de mapa
        painelEdicaoDesenhoComum.setBackground(new Background(new BackgroundFill(Color.rgb(221, 221, 221), null, null)));
        painelEdicaoDesenhoComum.getChildren().add(canvasMapa);
        painelEdicaoDesenhoComum.getChildren().add(botaoSalvar);
        painelEdicaoDesenhoComum.getChildren().add(botaoAbrir);
    }
    
    void inicializarPainelVogais(VBox painelVogais)
    {
        Canvas canvasVogais = new Canvas(larguraDaImagem, alturaDaImagem);
        GraphicsContext graphicsContextVogais = canvasVogais.getGraphicsContext2D();
        
        Label labelVogais = new Label("Vogais:");
        
        TextField campoDeTextoVogais = new TextField();
        campoDeTextoVogais.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String textoAntigo, String textoNovo)
            {
                textoNovo = textoNovo.replaceAll("[^AEIOUaeiou]", ""); //filtrar o que não for vogal
                if (textoNovo.length() > tamanhoMaximo) { textoNovo = textoNovo.substring(0, tamanhoMaximo); }
                campoDeTextoVogais.setText(textoNovo);
                redesenharVogais(graphicsContextVogais, textoNovo);
            }
        });
        
        ColorPicker colorPickerVogais = new ColorPicker();
        colorPickerVogais.setValue(corVogais);
        colorPickerVogais.setOnAction(e -> {
                corVogais = colorPickerVogais.getValue();
                redesenharVogais(graphicsContextVogais, campoDeTextoVogais.getText());
        });
        
        ToolBar toolBarVogais = new ToolBar();
        toolBarVogais.getItems().add(labelVogais);
        toolBarVogais.getItems().add(campoDeTextoVogais);
        toolBarVogais.getItems().add(colorPickerVogais);
        
        painelVogais.getChildren().add(toolBarVogais);
        painelVogais.getChildren().add(canvasVogais);
    }
    
    //faz o redesenho da aba de desenho comum, incluindo o canvas principal e o mapa
    void redesenharDesenhoComum(GraphicsContext graphicsContextFiguraComum, GraphicsContext graphicsContextMapa)
    {
        graphicsContextFiguraComum.clearRect(0, 0, larguraDaImagem, alturaDaImagem); //limpar o canvas
        Iterator iterador = listaFiguras.iterator();
        while(iterador.hasNext())
        {
            Figura figura = (Figura)iterador.next();
            figura.desenhar(graphicsContextFiguraComum);
        }
        if (figuraTemporaria != null) { figuraTemporaria.desenhar(graphicsContextFiguraComum); }
        if (clippingAtivado && retanguloClipping != null) { retanguloClipping.desenhar(graphicsContextFiguraComum); }
        redesenharMapa(graphicsContextMapa);
    }
    
    LinkedList<Figura> fazerClipping()
    {
        //clonar a lista de figuras, para obter uma lista separada para fazer clipping
        LinkedList<Figura> listaClipping = new LinkedList<Figura>();
        Iterator iterador = listaFiguras.iterator();
        while(iterador.hasNext())
        {
            Figura figura = (Figura)iterador.next();
            listaClipping.addLast(figura.clonar());
        }
        
        //checar quais figuras precisam de clipping, iterando do fim pro começo
        int indice = listaClipping.size() - 1;
        while(indice >= 0)
        {
            Figura figura = listaClipping.get(indice);
            figura = figura.fazerClipping(retanguloClipping);
            if (figura == null)
            {
                listaClipping.remove(indice); //remover figuras que estejam fora da janela de clipping (o método fazerClipping retorna null caso este seja o caso)
            }
            else
            {
                listaClipping.set(indice, figura); //trocar a figura antiga pela figura em que o clipping foi aplicado
            }
            
            indice--;
        }
        
        return listaClipping;
    }
    
    void redesenharMapa(GraphicsContext graphicsContextMapa)
    {
        graphicsContextMapa.setFill(Color.WHITE);
        graphicsContextMapa.fillRect(0, 0, larguraDoMapa, alturaDoMapa); //limpar o canvas
        Iterator iterador;
        if (clippingAtivado && retanguloClipping != null) { iterador = fazerClipping().iterator(); }
        else { iterador = listaFiguras.iterator(); }
        
        while(iterador.hasNext())
        {
            //esta forma de mapeamento por escala é equivalente à forma que usa fórmula geral, para este caso específico
            Figura figura = (Figura)iterador.next();
            if (figura instanceof PontoGrafico)
            {
                PontoGrafico ponto = (PontoGrafico)figura;
                int x = Math.round(ponto.getX() * escalaMapa);
                int y = Math.round(ponto.getY() * escalaMapa);
                int diametro = Math.round(ponto.getDiametro() * escalaMapa);
                figura = new PontoGrafico(x, y, diametro, ponto.getCor());
            }
            else if (figura instanceof Linha)
            {
                Linha linha = (Linha)figura;
                PontoMatematico p = linha.getP();
                double x = p.getX() * escalaMapa;
                double y = p.getY() * escalaMapa;
                p = new PontoMatematico(x, y);
                PontoMatematico q = linha.getQ();
                x = q.getX() * escalaMapa;
                y = q.getY() * escalaMapa;
                q = new PontoMatematico(x, y);
                int espessura = Math.round(linha.getEspessura() * escalaMapa);
                figura = new Linha(p, q, espessura, linha.getCor());
            }
            else if (figura instanceof Circunferencia)
            {
                Circunferencia circunferencia = (Circunferencia)figura;
                PontoMatematico centro = circunferencia.getCentro();
                double x = centro.getX() * escalaMapa;
                double y = centro.getY() * escalaMapa;
                centro = new PontoMatematico(x, y);
                double raio = circunferencia.getRaio() * escalaMapa;
                int espessura = Math.round(circunferencia.getEspessura() * escalaMapa);
                figura = new Circunferencia(centro, raio, espessura, circunferencia.getCor());
            }
            else if (figura instanceof CircunferenciaComClipping)
            {
                //para escalar uma circunferência com clipping, é necessário também escalar o retângulo de clipping que ela guarda
                RetanguloDeClipping janelaClipping = retanguloClipping.clonar();
                PontoMatematico p = janelaClipping.getP();
                double x = p.getX() * escalaMapa;
                double y = p.getY() * escalaMapa;
                p = new PontoMatematico(x, y);
                PontoMatematico q = janelaClipping.getQ();
                x = q.getX() * escalaMapa;
                y = q.getY() * escalaMapa;
                q = new PontoMatematico(x, y);
                int espessura = Math.round(janelaClipping.getEspessura() * escalaMapa);
                janelaClipping = new RetanguloDeClipping(p, q, espessura, janelaClipping.getCor());
                
                //escala da circunferência
                CircunferenciaComClipping circunferencia = (CircunferenciaComClipping)figura;
                PontoMatematico centro = circunferencia.getCentro();
                x = centro.getX() * escalaMapa;
                y = centro.getY() * escalaMapa;
                centro = new PontoMatematico(x, y);
                double raio = circunferencia.getRaio() * escalaMapa;
                espessura = Math.round(circunferencia.getEspessura() * escalaMapa);
                figura = new CircunferenciaComClipping(centro, raio, espessura, circunferencia.getCor(), janelaClipping);
            }
            else if (figura instanceof Retangulo)
            {
                Retangulo retangulo = (Retangulo)figura;
                PontoMatematico p = retangulo.getP();
                double x = p.getX() * escalaMapa;
                double y = p.getY() * escalaMapa;
                p = new PontoMatematico(x, y);
                PontoMatematico q = retangulo.getQ();
                x = q.getX() * escalaMapa;
                y = q.getY() * escalaMapa;
                q = new PontoMatematico(x, y);
                int espessura = Math.round(retangulo.getEspessura() * escalaMapa);
                figura = new Retangulo(p, q, espessura, retangulo.getCor());
            }
            else if (figura instanceof RetanguloComClipping)
            {
                RetanguloComClipping retangulo = (RetanguloComClipping)figura;
                Linha linhaEsquerda = retangulo.getLinhaEsquerda();
                if (linhaEsquerda != null)
                {
                    PontoMatematico p = linhaEsquerda.getP();
                    double x = p.getX() * escalaMapa;
                    double y = p.getY() * escalaMapa;
                    p = new PontoMatematico(x, y);
                    PontoMatematico q = linhaEsquerda.getQ();
                    x = q.getX() * escalaMapa;
                    y = q.getY() * escalaMapa;
                    q = new PontoMatematico(x, y);
                    int espessura = Math.round(linhaEsquerda.getEspessura() * escalaMapa);
                    linhaEsquerda = new Linha(p, q, espessura, linhaEsquerda.getCor());
                }
                Linha linhaSuperior = retangulo.getLinhaSuperior();
                if (linhaSuperior != null)
                {
                    PontoMatematico p = linhaSuperior.getP();
                    double x = p.getX() * escalaMapa;
                    double y = p.getY() * escalaMapa;
                    p = new PontoMatematico(x, y);
                    PontoMatematico q = linhaSuperior.getQ();
                    x = q.getX() * escalaMapa;
                    y = q.getY() * escalaMapa;
                    q = new PontoMatematico(x, y);
                    int espessura = Math.round(linhaSuperior.getEspessura() * escalaMapa);
                    linhaSuperior = new Linha(p, q, espessura, linhaSuperior.getCor());
                }
                Linha linhaDireita = retangulo.getLinhaDireita();
                if (linhaDireita != null)
                {
                    PontoMatematico p = linhaDireita.getP();
                    double x = p.getX() * escalaMapa;
                    double y = p.getY() * escalaMapa;
                    p = new PontoMatematico(x, y);
                    PontoMatematico q = linhaDireita.getQ();
                    x = q.getX() * escalaMapa;
                    y = q.getY() * escalaMapa;
                    q = new PontoMatematico(x, y);
                    int espessura = Math.round(linhaDireita.getEspessura() * escalaMapa);
                    linhaDireita = new Linha(p, q, espessura, linhaDireita.getCor());
                }
                Linha linhaInferior = retangulo.getLinhaInferior();
                if (linhaInferior != null)
                {
                    PontoMatematico p = linhaInferior.getP();
                    double x = p.getX() * escalaMapa;
                    double y = p.getY() * escalaMapa;
                    p = new PontoMatematico(x, y);
                    PontoMatematico q = linhaInferior.getQ();
                    x = q.getX() * escalaMapa;
                    y = q.getY() * escalaMapa;
                    q = new PontoMatematico(x, y);
                    int espessura = Math.round(linhaInferior.getEspessura() * escalaMapa);
                    linhaInferior = new Linha(p, q, espessura, linhaInferior.getCor());
                }
                figura = new RetanguloComClipping(linhaEsquerda, linhaSuperior, linhaDireita, linhaInferior);
            }
            else if (figura instanceof Poligono)
            {
                Poligono poligono = (Poligono)figura;
                int espessura = Math.round(poligono.getEspessura() * escalaMapa);
                Poligono novoPoligono = new Poligono(espessura, poligono.getCor());
                int i = poligono.getQtdPontos();
                for(int k = 0; k < i; k++) {
                    PontoMatematico p = poligono.getPonto(k); 
                    double x = p.getX() * escalaMapa;
                    double y = p.getY() * escalaMapa;
                    p = new PontoMatematico(x, y);
                    novoPoligono.addPonto(p);
                }
                figura = novoPoligono;
            }
            figura.desenhar(graphicsContextMapa);
        }
    }
    
    void redesenharVogais(GraphicsContext graphicsContext, String texto)
    {
        graphicsContext.clearRect(0, 0, larguraDaImagem, alturaDaImagem); //limpar o canvas
        for(int i = 0; i < texto.length(); i++)
        {
            boolean[][] vogal = null;
            switch (texto.toUpperCase().charAt(i))
            {
                case 'A':
                    vogal = A;
                    break;
                
                case 'E':
                    vogal = E;
                    break;
                
                case 'I':
                    vogal = I;
                    break;
                
                case 'O':
                    vogal = O;
                    break;
                
                case 'U':
                    vogal = U;
                    break;
            }
            if (vogal != null)
            {
                desenharVogal(graphicsContext, new PontoMatematico(i * (espacamento*2 + diametroPreenchimento)*3, 0), vogal, corVogais);
            }
        }
    }
        
    void desenharVogal(GraphicsContext graphicsContext, PontoMatematico origem, boolean[][] letra, Color cor)
    {
        //a circunferência é desenhada a partir de sua coordenada de centro
        //temos aqui a origem da vogal, ou seja, o canto superior esquerdo da sua circunferência que está no canto
        //superior esquerdo (circunferência base). precisamos do centro dessa circunferência para desenhá-la no local
        //correto, bem como todas as outras circunferências, que serão desenhadas baseadas a partir da base
        PontoMatematico centroBase = new PontoMatematico(origem.getX() + raioPreenchimento, origem.getY() + raioPreenchimento);
        
        if (letra[0][0])
        {
            CircunferenciaPreenchida c = new CircunferenciaPreenchida(centroBase, raioPreenchimento, 1, cor);
            c.desenhar(graphicsContext);
        }
        if (letra[0][1])
        {
            CircunferenciaPreenchida c = new CircunferenciaPreenchida(new PontoMatematico(centroBase.getX() + espacamento + diametroPreenchimento, centroBase.getY()), raioPreenchimento, 1, cor);
            c.desenhar(graphicsContext);
        }
        if (letra[0][2])
        {
            CircunferenciaPreenchida c = new CircunferenciaPreenchida(new PontoMatematico(centroBase.getX() + (espacamento + diametroPreenchimento)*2, centroBase.getY()), raioPreenchimento, 1, cor);
            c.desenhar(graphicsContext);
        }
        if (letra[1][0])
        {
            CircunferenciaPreenchida c = new CircunferenciaPreenchida(new PontoMatematico(centroBase.getX(), centroBase.getY() + espacamento + diametroPreenchimento), raioPreenchimento, 1, cor);
            c.desenhar(graphicsContext);
        }
        if (letra[1][1])
        {
            CircunferenciaPreenchida c = new CircunferenciaPreenchida(new PontoMatematico(centroBase.getX() + espacamento + diametroPreenchimento, centroBase.getY() + espacamento + diametroPreenchimento), raioPreenchimento, 1, cor);
            c.desenhar(graphicsContext);
        }
        if (letra[1][2])
        {
            CircunferenciaPreenchida c = new CircunferenciaPreenchida(new PontoMatematico(centroBase.getX() + (espacamento + diametroPreenchimento)*2, centroBase.getY() + espacamento + diametroPreenchimento), raioPreenchimento, 1, cor);
            c.desenhar(graphicsContext);
        }
        if (letra[2][0])
        {
            CircunferenciaPreenchida c = new CircunferenciaPreenchida(new PontoMatematico(centroBase.getX(), centroBase.getY() + (espacamento + diametroPreenchimento)*2), raioPreenchimento, 1, cor);
            c.desenhar(graphicsContext);
        }
        if (letra[2][1])
        {
            CircunferenciaPreenchida c = new CircunferenciaPreenchida(new PontoMatematico(centroBase.getX() + espacamento + diametroPreenchimento, centroBase.getY() + (espacamento + diametroPreenchimento)*2), raioPreenchimento, 1, cor);
            c.desenhar(graphicsContext);
        }
        if (letra[2][2])
        {
            CircunferenciaPreenchida c = new CircunferenciaPreenchida(new PontoMatematico(centroBase.getX() + (espacamento + diametroPreenchimento)*2, centroBase.getY() + (espacamento + diametroPreenchimento)*2), raioPreenchimento, 1, cor);
            c.desenhar(graphicsContext);
        }
        if (letra[3][0])
        {
            CircunferenciaPreenchida c = new CircunferenciaPreenchida(new PontoMatematico(centroBase.getX(), centroBase.getY() + (espacamento + diametroPreenchimento)*3), raioPreenchimento, 1, cor);
            c.desenhar(graphicsContext);
        }
        if (letra[3][1])
        {
            CircunferenciaPreenchida c = new CircunferenciaPreenchida(new PontoMatematico(centroBase.getX() + espacamento + diametroPreenchimento, centroBase.getY() + (espacamento + diametroPreenchimento)*3), raioPreenchimento, 1, cor);
            c.desenhar(graphicsContext);
        }
        if (letra[3][2])
        {
            CircunferenciaPreenchida c = new CircunferenciaPreenchida(new PontoMatematico(centroBase.getX() + (espacamento + diametroPreenchimento)*2, centroBase.getY() + (espacamento + diametroPreenchimento)*3), raioPreenchimento, 1, cor);
            c.desenhar(graphicsContext);
        }
        if (letra[4][0])
        {
            CircunferenciaPreenchida c = new CircunferenciaPreenchida(new PontoMatematico(centroBase.getX(), centroBase.getY() + (espacamento + diametroPreenchimento)*4), raioPreenchimento, 1, cor);
            c.desenhar(graphicsContext);
        }
        if (letra[4][1])
        {
            CircunferenciaPreenchida c = new CircunferenciaPreenchida(new PontoMatematico(centroBase.getX() + espacamento + diametroPreenchimento, centroBase.getY() + (espacamento + diametroPreenchimento)*4), raioPreenchimento, 1, cor);
            c.desenhar(graphicsContext);
        }
        if (letra[4][2])
        {
            CircunferenciaPreenchida c = new CircunferenciaPreenchida(new PontoMatematico(centroBase.getX() + (espacamento + diametroPreenchimento)*2, centroBase.getY() + (espacamento + diametroPreenchimento)*4), raioPreenchimento, 1, cor);
            c.desenhar(graphicsContext);
        }
    }
}
