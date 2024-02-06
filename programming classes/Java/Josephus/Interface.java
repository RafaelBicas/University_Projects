 /**
 * Implementação do problema de Josephus.
 * 
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 * @version 0.2 20190611
 */

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface extends javax.swing.JFrame implements ActionListener
{
    JPanel painelSoldados;
    JLabel labelQuantidade;
    JLabel labelIntervalo;
    JLabel labelTempo;
    JTextField textFieldQuantidade;
    JTextField textFieldIntervalo;
    JTextField textFieldTempo;
    JButton botaoIniciar;
    JButton botaoParar;
    JButton botaoSair;
    
    JLabel[] soldados;
    Color corVivo = new Color(100, 150, 255);
    Color corMorto = new Color(255, 20, 20);
    Color corSobrevivente = new Color(0, 220, 40);
    ImageIcon iconeVivo = new ImageIcon("Vivo.png");
    ImageIcon iconeMorto = new ImageIcon("Morto.png");
    ImageIcon iconeSobrevivente = new ImageIcon("Sobrevivente.png");
    
    Josephus josephus;
    Thread threadAnimacao;
    
    public Interface()
    {
        josephus = new Josephus();
        inicializar();
    }
    
    /**
     * Executado para inicializar o frame
     */
    public void exibirJanela()
    {
        this.setLocationRelativeTo(null); //centralizar janela, precisa ser executado antes do setVisible
        this.setVisible(true);
    }
    
    /**
     * Insere os controles na janela
     */
    void inicializar()
    {
        //configuração da janela
        this.setMinimumSize(new Dimension(616, 319)); //definir tamanho mínimo (e, juntamente disso, o tamanho inicial, já que este não está sendo explicitamente definido)
        Container container = getContentPane();
        
        //painel de entrada
        JPanel painelValores = new JPanel();
        painelValores.setPreferredSize(new Dimension(596, 32));
        container.add(painelValores, BorderLayout.PAGE_START);
        
        //painel de soldados
        painelSoldados = new JPanel();
        painelSoldados.setBackground(new Color(204, 204, 204));
        container.add(painelSoldados, BorderLayout.CENTER);
        
        //margens do painel de soldados
        container.add(Box.createRigidArea(new Dimension(5,0)), BorderLayout.LINE_START);
        container.add(Box.createRigidArea(new Dimension(5,0)), BorderLayout.LINE_END);
        
        //painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setPreferredSize(new Dimension(596, 32));
        container.add(painelBotoes, BorderLayout.PAGE_END);
        
        labelQuantidade = new JLabel("Quantidade de soldados:");
        painelValores.add(labelQuantidade);
        
        textFieldQuantidade = new JTextField("41");
        textFieldQuantidade.setPreferredSize(new Dimension(34, 20));
        painelValores.add(textFieldQuantidade);
        
        labelIntervalo = new JLabel("Intervalo de morte:");
        painelValores.add(labelIntervalo);
        
        textFieldIntervalo = new JTextField("2");
        textFieldIntervalo.setPreferredSize(new Dimension(34, 20));
        painelValores.add(textFieldIntervalo);
        
        labelTempo = new JLabel("Tempo entre mortes (ms):");
        painelValores.add(labelTempo);
        
        textFieldTempo = new JTextField("500");
        textFieldTempo.setPreferredSize(new Dimension(34, 20));
        painelValores.add(textFieldTempo);
        
        botaoIniciar = new JButton("Iniciar");
        botaoIniciar.setPreferredSize(new Dimension(70, 20));
        botaoIniciar.addActionListener(this);
        painelBotoes.add(botaoIniciar);
        
        botaoParar = new JButton("Parar");
        botaoParar.setPreferredSize(new Dimension(70, 20));
        botaoParar.setEnabled(false);
        botaoParar.addActionListener(this);
        painelBotoes.add(botaoParar);
        
        botaoSair = new JButton("Sair");
        botaoSair.setPreferredSize(new Dimension(70, 20));
        botaoSair.addActionListener(this);
        painelBotoes.add(botaoSair);
        
        //inicializar painel de soldados com os valores padrão
        resetarSoldados(Integer.parseInt(textFieldQuantidade.getText()));
    }
    
    /**
     * Controlador de eventos de botão
     */
    public void actionPerformed(ActionEvent evento)
    {
        if (evento.getSource() == botaoIniciar)
        {
            //executar a animação
            int quantidade = Integer.parseInt(textFieldQuantidade.getText());
            int intervalo = Integer.parseInt(textFieldIntervalo.getText());
            int tempo = Integer.parseInt(textFieldTempo.getText());
            resetarSoldados(quantidade);
            int[] permutacoes = josephus.problemaJosephus(intervalo, quantidade);
            threadAnimacao = new Thread(new AnimacaoMortes(permutacoes, tempo));
            threadAnimacao.start();
            
            //desabilitar configuração
            textFieldQuantidade.setEnabled(false);
            textFieldIntervalo.setEnabled(false);
            textFieldTempo.setEnabled(false);
            botaoParar.setEnabled(true);
            botaoIniciar.setEnabled(false);
        }
        else if (evento.getSource() == botaoParar)
        {
            //parar animação
            if (threadAnimacao != null) { threadAnimacao.stop(); }
            
            //habilitar configuração
            textFieldQuantidade.setEnabled(true);
            textFieldIntervalo.setEnabled(true);
            textFieldTempo.setEnabled(true);
            botaoIniciar.setEnabled(true);
            botaoParar.setEnabled(false);
        }
        else if (evento.getSource() == botaoSair)
        {
            System.exit(0);
        }
    }
    
    void resetarSoldados(int quantidade)
    {
        soldados = new JLabel[quantidade]; 
        painelSoldados.removeAll();
        
        for (int i = 0; i < quantidade; i++)
        {
            soldados[i] = new JLabel(iconeVivo);
            soldados[i].setText(Integer.toString(i + 1));
            soldados[i].setPreferredSize(new Dimension(34, 48));
            soldados[i].setBorder(new EmptyBorder(2,0,0,0)); //acertar bordas
            soldados[i].setIconTextGap(0); //espaçamento entre ícone e texto
            soldados[i].setHorizontalTextPosition(JLabel.CENTER); //alinhar texto no centro
            soldados[i].setVerticalTextPosition(JLabel.BOTTOM); //alinhar texto abaixo do ícone
            soldados[i].setBackground(corVivo);
            soldados[i].setOpaque(true);
            painelSoldados.add(soldados[i]);
        }
        
        painelSoldados.revalidate(); //atualizar painel com os novos componentes
        painelSoldados.repaint(); //atualizar desenho do painel
    }
    
    public void matarSoldado(int indice)
    {
        if (soldados.length > indice)
        {
            soldados[indice].setIcon(iconeMorto);
            soldados[indice].setBackground(corMorto);
        }
    }
    
    public void tornarSoldadoSobrevivente(int indice)
    {
        if (soldados.length > indice)
        {
            soldados[indice].setIcon(iconeSobrevivente);
            soldados[indice].setBackground(corSobrevivente);
        }
    }
    
    class AnimacaoMortes extends Thread
    {
        int[] permutacoes;
        int tempo;
        
        public AnimacaoMortes(int[] permutacoes, int tempo)
        {
            this.permutacoes = permutacoes;
            this.tempo = tempo;
        }
        
        public void run()
        {
            for (int i = 0; i < permutacoes.length - 1; i++) {
                matarSoldado(permutacoes[i]);
                try { Thread.sleep(tempo); } catch(Exception e) { }
            }
            tornarSoldadoSobrevivente(permutacoes[permutacoes.length - 1]);
        }
    }
}
