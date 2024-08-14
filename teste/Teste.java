package teste;

import tree.BinaryTree;

public class Teste {
  public static void main(String[] args) {
    BinaryTree tree = new BinaryTree();
    tree.insert(11); // 1°
    tree.insert(6); // 2°
    tree.insert(13); // 3°
    tree.insert(17); // 4°
    tree.insert(4); // 5°
    tree.insert(9); // 6°
    tree.insert(7); // 7°
    tree.insert(10); // 8°
    tree.insert(16); // 9°

    // tree.ordemRec();

    // tree.remove(11);

    // tree.ordemRec();

    // System.out.println("-------------------------------------------------------\n");

    // tree.ordemRec();
    // tree.ordemIte();

    // System.out.println("-------------------------------------------------------\n");

    // tree.preOrdemRec();
    // tree.preOrdemIte();

    // System.out.println("-------------------------------------------------------\n");

    // tree.posOrdemRec();
    // tree.posOrdemIte();

    // System.out.println("-------------------------------------------------------\n");

    // ------------------------------------------------------------------------------------------------

    System.out.println("Menor chave: " + tree.menorChave());

    System.out.println("-------------------------------------------------------\n");

    System.out.println("Maior chave: " + tree.maiorChave());

    System.out.println("-------------------------------------------------------\n");

    System.out.println("Menor chave da sub-árvore direita: " + tree.menorChaveAD());

    System.out.println("-------------------------------------------------------\n");

    System.out.println("Maior chave da sub-árvore esquerda: " + tree.maiorChaveAE());

    System.out.println("-------------------------------------------------------\n");

    System.out.println("Quantidade de nós recursivo: " + tree.quantNodesRec());

    System.out.println("-------------------------------------------------------\n");

    System.out.println("Quantidade de nós iterativo: " + tree.quantNodesIte());

    System.out.println("-------------------------------------------------------\n");

    System.out.println("Quantidade de folhas recursivo: " + tree.quantFolhasRec());

    System.out.println("-------------------------------------------------------\n");

    System.out.println("Quantidade de folhas iterativo: " + tree.quantFolhasIte());

    System.out.println("-------------------------------------------------------\n");

    System.out.println("Quantidade de nós internos iterativo: " + tree.quantIternos());

    System.out.println("-------------------------------------------------------\n");

    System.out.println("Quantidade de nós internos recursivo: " + tree.contarNosInternosRec());

    System.out.println("-------------------------------------------------------\n");

    tree.percorrerPorNivel();

    System.out.println("-------------------------------------------------------\n");

    tree.codigos();

    System.out.println("-------------------------------------------------------\n");

    System.out.println("Profundidade do nó " + 16 + " é: " + tree.profundidadeRec(16));
  }
}