package teste;

import tree.BinaryTree;

public class Teste {
  public static void main(String[] args) {
    BinaryTree tree = new BinaryTree();
    tree.insert(41); // 1°
    tree.insert(30);
    tree.insert(42);
    tree.insert(31);
    tree.insert(43);

    // tree.ordemRec();

    // tree.remove(11);

    // tree.ordemRec();

    // System.out.println("-------------------------------------------------------\n");

    tree.ordemRec();
    tree.ordemIte();

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

    System.out.println("-------------------------------------------------------\n");

    System.out.println("Altura da árvore é: " + tree.height());
  }
}