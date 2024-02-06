 /**
 * Implementação do problema de Josephus.
 * 
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 * @version 0.2 20190611
 */

public class Josephus
{
    private ListaDuplamenteLigadaCircular ldl;
    Josephus() {
        ldl = new ListaDuplamenteLigadaCircular();
    }

    private void inserirSoldados(int tamanho) {
        for(int i = 0; i < tamanho; i++) {
            ldl.inserirFim(i + 1);
        }
    }
    
    private void mostrarSoldadosVivos() {
       System.out.printf("Soldados Vivos:%n%s%n%n", ldl);
    }
    
    public int[] problemaJosephus(int k, int tamanho) {
        int posicao = -1, i = 0;
        int[] permutacao = new int[tamanho];
        
        inserirSoldados(tamanho);
        System.out.printf("Intervalo: %d%n", k);
        System.out.printf("Situação inicial:%n");
        mostrarSoldadosVivos();
        System.out.printf("Passos após o início:%n");
        do {
            posicao = (posicao + k) % tamanho; //calcular posição do próximo a morrer
            No removido = ldl.removerNaPosicao(posicao); //matar soldado (remover da lista)
            permutacao[i++] = (int)removido.getConteudo() - 1; //adicionar o soldado morto na lista de permutação. obter o índice do elemento sobrevivente, que é valor - 1 (o índice 0 contém o valor 1)
            posicao--; //como um soldado foi removido, atualizar a posição
            tamanho--; //atualizar o tamanho
            if (tamanho > 0) { mostrarSoldadosVivos(); } //não imprimir na última execução, onde o sobrevivente é removido da lista
        } while(tamanho > 0); //o último a ser adicionado na lista é na verdade o sobrevivente, e não um soldado morto
        
        return permutacao;
    }
    
}
