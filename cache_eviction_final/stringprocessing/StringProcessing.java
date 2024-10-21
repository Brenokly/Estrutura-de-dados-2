package cache_eviction_final.stringprocessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StringProcessing {
    // Algoritmo KMP para buscar a substring em cada linha do texto
    public static List<String> searchKMP(String pattern, String[] lines, int rowsReturned) {
        List<String> occurrences = new ArrayList<>();
        int[] prefixTable = buildPrefixTable(pattern); // Constrói a tabela de prefixo

        for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
            String line = lines[lineNumber];
            int i = 0, j = 0;

            while (i < line.length()) {
                if (pattern.charAt(j) == line.charAt(i)) {
                    i++;
                    j++;
                }

                if (j == pattern.length()) {
                    // Encontrou uma ocorrência, adiciona o número da linha
                    StringBuilder sb = new StringBuilder();

                    sb.append("Padrão encontrado na linha " + (lineNumber + 1) + " do log:\n");
                    sb.append("-----------------------------------------------\n");
                    // Adiciona a quantidade de linhas retornadas no resultado
                    for (int k = lineNumber; k < (lineNumber + rowsReturned); k++) {
                        if (lines.length > k) {
                            sb.append(lines[k] + "\n");
                        }
                    }
                    sb.append("-----------------------------------------------\n");

                    // Adiciona a lista de ocorrências
                    occurrences.add(sb.toString());

                    // Reinicia a busca
                    j = prefixTable[j - 1];
                } else if (i < line.length() && pattern.charAt(j) != line.charAt(i)) {
                    if (j != 0) {
                        j = prefixTable[j - 1];
                    } else {
                        i++;
                    }
                }
            }
        }

        return occurrences;
    }

    // Constrói a tabela de prefixo para o padrão
    private static int[] buildPrefixTable(String pattern) {
        int[] prefixTable = new int[pattern.length()];
        int length = 0;
        int i = 1;

        prefixTable[0] = 0; // A primeira posição é sempre 0

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                prefixTable[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = prefixTable[length - 1];
                } else {
                    prefixTable[i] = 0;
                    i++;
                }
            }
        }

        return prefixTable;
    }

    // Lê o log de um arquivo e retorna como um array de strings
    // Fiz dessa forma para retornar a linha exata onde a substring foi encontrada
    public static String[] readLogFile(String filePath) throws IOException {
        List<String> logLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                logLines.add(line);
            }
        }
        return logLines.toArray(new String[0]);
    }
}