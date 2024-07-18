package teste;

import tree.BinaryTree;

public class Teste {
  public static void main(String[] args) {
    BinaryTree<Integer> tree = new BinaryTree<>();

    tree.insert(1);
    tree.insert(2);
    tree.insert(3);
    tree.insert(4);
    tree.insert(5);
    tree.insert(6);
    tree.insert(7);

    tree.displayOrder();

    tree.insert(8);

    tree.displayOrder();

    tree.deleteRecursive(4);

    tree.displayOrder();

    System.out.println("Dado encontrado: " + tree.search(3).getData());
  }
}
