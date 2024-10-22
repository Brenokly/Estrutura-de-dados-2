package cache_eviction_final.simulations;

import cache_eviction_final.stringprocessing.StringProcessing;
import java.util.List;

/*
 * Criei essa simulação para mostrar o algoritmo de processamento de strings
 * em ação!
 */

public class Simulação3 {
  public static void main(String[] args) {
    StringProcessing sp = new StringProcessing();

    try {
      // Deve ler o arquivo de log do servidor
      String[] lines = sp.readLogFile("cache_eviction_final/ServerLog.txt");

      // Buscando pelas ocorrências de inserção (Cadastros) no log do servidor
      String pattern = "Operacao realizada: Insercao"; // Indicando o padrão que estou buscando

      // Buscando as ocorrências
      List<String> occurrences = sp.searchKMP(pattern, lines, 5); // Buscando as ocorrências

      // Imprimindo as ocorrências
      for (String occurrence : occurrences) {
        System.out.println("=============================================================================");
        System.out.println(occurrence);
      }

      // ----------------------------------------------------------------------------------------------------------

      // Buscando pelas ocorrências de Remoções no log do servidor
      pattern = "Operacao realizada: Remocao"; // Indicando o padrão que estou buscando

      // Buscando as ocorrências
      occurrences = sp.searchKMP(pattern, lines, 5); // Buscando as ocorrências

      // Imprimindo as ocorrências
      for (String occurrence : occurrences) {
        System.out.println("=============================================================================");
        System.out.println(occurrence);
      }
    } catch (Exception e) {
      System.out.println("Erro ao ler o arquivo de log do servidor");
    }
  }
}
