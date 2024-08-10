package tree;

public class BinaryTree<E extends Comparable<E>> {
  public class Node {
    E data;
    Node left;
    Node right;

    public Node(E data) {
      this.data = data;
      this.left = null;
      this.right = null;
    }

    public E getData(){
      return this.data;
    }

    public Node getLeft(){
      return this.left;
    }

    public Node getRight(){
      return this.right;
    }
  }

  private Node root;

  public Node getRoot(){
    return this.root;
  }

  public BinaryTree() {
    this.root = null;
  }

  public Node insert(E data) {
    if (root == null) {
      root = new Node(data);
    }

    return insertRec(root, data);
  }

  private Node insertRec(Node current, E data) {
    if (current == null) {
      current = new Node(data);
    }

    if (data.compareTo(current.data) < 0) {
      current.left = insertRec(current.left, data);
    } else if (data.compareTo(current.data) > 0) {
      current.right = insertRec(current.right, data);
    }

    return current;
  }

  public Node search(E data) {
    Node current = root;

    while (current != null) {
      if (data.compareTo(current.data) == 0) {
        return current; // Encontrou o nó
      } else if (data.compareTo(current.data) < 0) {
        current = current.left; // Vá para a subárvore esquerda
      } else {
        current = current.right; // Vá para a subárvore direita
      }
    }
    
    return null; // Não encontrou o nó
  }

  private Node searchRecursive(Node current, E data) {
    if (current == null || data.compareTo(current.data) == 0) {
      return current;
    }

    if (data.compareTo(current.data) < 0) {
      return searchRecursive(current.left, data);
    } else {
      return searchRecursive(current.right, data);
    }
  }

  // Para iniciar a busca a partir da raiz:
  public Node searchRecursive(E data) {
    return searchRecursive(root, data);
  }

  public Node deleteRecursive(E data) {
    if (root == null) {
      return null;
    } else if (data.compareTo(root.data) == 0) {
      return root;
    }

    return deleteRecursive(root, data);
  }

  private Node deleteRecursive(Node arv, E valor) {
    if (arv == null) {
      return null;
    }

    if (valor.compareTo(arv.data) < 0) {
      arv.left = deleteRecursive(arv.left, valor);
    } else if (valor.compareTo(arv.data) > 0) {
      arv.right = deleteRecursive(arv.right, valor);
    } else {
      // Caso 1: Nó sem filhos ou com um filho (tratado como casos 2 e 3)
      if (arv.left == null) {
        return arv.right;
      } else if (arv.right == null) {
        return arv.left;
      } else {
        // Caso 3: Nó com dois filhos
        // Encontrar o sucessor na subárvore direita
        Node temp = arv.right;
        while (temp.left != null) {
          temp = temp.left;
        }
        // Substituir o valor do nó atual pelo valor do sucessor
        arv.data = temp.data;
        // Remover o sucessor da subárvore direita
        arv.right = deleteRecursive(arv.right, temp.data);
      }
    }

    return arv; // Retornar o nó atual modificado após a remoção
  }

  public void displayOrder() {
    displayOrder(root);
    System.out.println("\n-------------------------------------------");
  }

  private void displayOrder(Node node) {
    if (node != null) {
      displayOrder(node.left); // Visitando subárvore esquerda
      System.out.print(node.data + " "); // Visita o próprio nó
      displayOrder(node.right); // Visitando subárvore direita
    }
  }

    /**
     * @param root the root to set
     */
    public void setRoot(Node root) {
        this.root = root;
    }

}