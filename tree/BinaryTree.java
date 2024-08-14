package tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree {
  private Node root;

  public BinaryTree() {
    root = null;
  }

  // Métodos de busca recursiva
  // -----------------------------------------------------------------------------------------------------------

  public Node search(int key) {
    return search(key, root);
  }

  private Node search(int key, Node root) {
    if (root == null || root.key == key)
      return root;

    if (key < root.key)
      return search(key, root.left);
    else
      return search(key, root.right);
  }

  // Método para inserir recursivamente
  // -----------------------------------------------------------------------------------------------------------

  public void insert(int valor) {
    root = insert(valor, root);
  }

  private Node insert(int key, Node root) {
    if (root == null)
      return new Node(key);

    if (key < root.key)
      root.left = insert(key, root.left);
    else if (key > root.key)
      root.right = insert(key, root.right);

    return root;
  }

  // Métodos recursivos para percorrer a árvore
  // -----------------------------------------------------------------------------------------------------------

  public void ordemRec() { // método público para exibir em ordem
    System.out.print("Percurso em Ordem Recusivo: ");
    this.ordemRec(root);
    System.out.println('\n');
  }

  private void ordemRec(Node root) { // método privado para exibir em ordem
    if (root != null) {
      this.ordemRec(root.left);
      System.out.print(root.key + " ");
      this.ordemRec(root.right);
    }
  }

  public void preOrdemRec() {
    System.out.print("Percurso em Pré-Ordem Recusivo: ");
    preOrdemRec(root);
    System.out.println('\n');
  }

  private void preOrdemRec(Node root) {
    if (root != null) {
      System.err.print(root.key + " ");
      preOrdemRec(root.left);
      preOrdemRec(root.right);
    }
  }

  public void posOrdemRec() {
    System.out.print("Percurso em Pós-Ordem Recusivo: ");
    posOrdemRec(root);
    System.out.println('\n');
  }

  private void posOrdemRec(Node root) {
    if (root != null) {
      posOrdemRec(root.left);
      posOrdemRec(root.right);
      System.err.print(root.key + " ");
    }
  }

  // Métodos iterativos para percorrer a árvore
  // -----------------------------------------------------------------------------------------------------------

  public void ordemIte() { // método público para exibir em ordem
    System.out.print("Percurso em Ordem Iterativo: ");
    this.ordemIte(root);
    System.out.println('\n');
  }

  private void ordemIte(Node root) { // método privado para exibir em ordem
    if (root == null)
      return;

    Stack<Node> stack = new Stack<>();
    Node current = root;

    while (current != null || !stack.isEmpty()) {
      while (current != null) {
        stack.push(current);
        current = current.left;
      }

      current = stack.pop();
      System.out.print(current.key + " ");
      current = current.right;
    }
  }

  public void preOrdemIte() {
    System.out.print("Percurso em Pré-Ordem Iterativo: ");
    preOrdemIte(root);
    System.out.println('\n');
  }

  private void preOrdemIte(Node root) {
    if (root == null)
      return;

    Stack<Node> stack = new Stack<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      Node current = stack.pop();
      System.out.print(current.key + " ");

      if (current.right != null)
        stack.push(current.right);
      if (current.left != null)
        stack.push(current.left);
    }
  }

  public void posOrdemIte() {
    System.out.print("Percurso em Pós-Ordem Iterativo: ");
    posOrdemIte(root);
    System.out.println('\n');
  }

  private void posOrdemIte(Node root) {
    if (root == null)
      return;

    Stack<Node> stack = new Stack<>();
    Stack<Node> stackOut = new Stack<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      Node current = stack.pop();
      stackOut.push(current);

      if (current.left != null)
        stack.push(current.left);
      if (current.right != null)
        stack.push(current.right);
    }

    while (!stackOut.isEmpty()) {
      Node current = stackOut.pop();
      System.out.print(current.key + " ");
    }
  }

  // métodos para remover recursivamente
  // -----------------------------------------------------------------------------------------------------------

  public void remove(int key) {
    remove(key, root);
  }

  private Node remove(int key, Node root) {
    if (root == null)
      return root;

    if (key < root.key)
      root.left = remove(key, root.left);
    else if (key > root.key)
      root.right = remove(key, root.right);
    else {
      if (root.right == null && root.left == null)
        root = null;
      else if (root.left == null) {
        root = root.right;
      } else if (root.right == null) {
        root = root.left;
      } else {
        Node aux = minorKeyRoot(root.right);
        root.key = aux.key;
        root.right = remove(aux.key, root.right);
      }
    }

    return root;
  }

  // Método recursivo para buscar a menor chave da sub-árvore direita
  // -----------------------------------------------------------------------------------------------------------

  private Node minorKeyRoot(Node root) {
    if (root == null) // nunca vai entrar aqui, mas... Por segurança....
      return null;

    Node aux = root;

    while (aux.left != null)
      aux = aux.left;

    return aux;
  }

  // Método recursivo para calcular a altura
  // -----------------------------------------------------------------------------------------------------------

  public int height() {
    return height(root);
  }

  private int height(Node root) {
    int a, b;

    if (root == null)
      return -1;

    a = height(root.left);
    b = height(root.right);

    if (a > b)
      return a + 1;
    else
      return b + 1;
  }

  // Métodos da questão 11 da primeira lista
  // A menor chave da árvore binária de busca.
  // -----------------------------------------------------------------------------------------------------------

  public int menorChave() {
    return menorChave(root);
  }

  private int menorChave(Node root) {
    if (root == null)
      return -1;

    Node aux = root;
    while (aux.left != null)
      aux = aux.left;

    return aux.key;
  }

  // maior chave da árvore binária de busca
  // -----------------------------------------------------------------------------------------------------------

  public int maiorChave() {
    return maiorChave(root);
  }

  private int maiorChave(Node root) {
    if (root == null)
      return -1;

    Node aux = root;
    while (aux.right != null)
      aux = aux.right;

    return aux.key;
  }

  // A menor chave da subárvore direta de um nó da árvore binária de busca
  // -----------------------------------------------------------------------------------------------------------

  public int menorChaveAD() {
    return menorChaveAD(root.right);
  }

  private int menorChaveAD(Node root) {
    if (root == null)
      return -1;

    Node aux = root;

    while (aux.left != null) {
      aux = aux.left;
    }

    return aux.key;
  }

  // A maior chave da subárvore esquerda de um nó da árvore binária de busca
  // -----------------------------------------------------------------------------------------------------------

  public int maiorChaveAE() {
    return maiorChaveAE(root.left);
  }

  private int maiorChaveAE(Node root) {
    if (root == null)
      return -1;

    Node aux = root;

    while (aux.right != null) {
      aux = aux.right;
    }

    return aux.key;
  }

  // A quantidade de nós recursivo
  // -----------------------------------------------------------------------------------------------------------

  public int quantNodesRec() {
    return quantNodesRec(root);
  }

  private int quantNodesRec(Node root) {
    if (root == null)
      return 0;
    else
      return 1 + quantNodesRec(root.left) + quantNodesIte(root.right);
  }

  // A quantidade de nós iterativo
  // -----------------------------------------------------------------------------------------------------------

  public int quantNodesIte() {
    return quantNodesIte(root);
  }

  private int quantNodesIte(Node root) {
    if (root == null)
      return 0;

    Stack<Node> stack = new Stack<>();
    stack.push(root);
    int cont = 0;

    while (!stack.isEmpty()) {
      Node current = stack.pop();
      cont++;

      if (current.right != null)
        stack.push(current.right);
      if (current.left != null)
        stack.push(current.left);
    }
    return cont;
  }

  // Quantidade de folhas
  // -----------------------------------------------------------------------------------------------------------

  public int quantFolhasRec() {
    return quantFolhasRec(root);
  }

  private int quantFolhasRec(Node root) {
    if (root == null)
      return 0;

    if (root.left == null && root.right == null)
      return 1;
    else
      return quantFolhasRec(root.left) + quantFolhasRec(root.right);
  }

  public int quantFolhasIte() {
    return quantFolhasIte(root);
  }

  // iterativo

  private int quantFolhasIte(Node root) {
    if (root == null)
      return 0;

    Stack<Node> stack = new Stack<>();
    stack.push(root);
    int cont = 0;

    while (!stack.isEmpty()) {
      Node current = stack.pop();
      if (current.left == null && current.right == null)
        cont++;

      if (current.right != null)
        stack.push(current.right);
      if (current.left != null)
        stack.push(current.left);
    }
    return cont;
  }

  // Quantidade de nós internos
  // -----------------------------------------------------------------------------------------------------------

  public int quantIternos() {
    return quantInternos(root);
  }

  private int quantInternos(Node root) {
    if (root == null)
      return 0;

    Stack<Node> stack = new Stack<>();
    stack.push(root);
    int cont = 0;

    while (!stack.isEmpty()) {
      Node current = stack.pop();
      if (current.left != null || current.right != null)
        cont++;

      if (current.right != null)
        stack.push(current.right);
      if (current.left != null)
        stack.push(current.left);
    }
    return cont;
  }

  public int contarNosInternosRec() {
    return contarNosInternosRec(root);
  }

  private int contarNosInternosRec(Node root) {
    if (root == null)
      return 0;
    if (root.left != null || root.right != null)
      return contarNosInternosRec(root.left) + contarNosInternosRec(root.right) + 1;
    else
      return 0;
  }

  // Percorrer a árvore por largura (ou por nível)
  // -----------------------------------------------------------------------------------------------------------

  public void percorrerPorNivel() {
    System.out.print("Percurso em por nível: ");
    percorrerPorNivel(root);
    System.out.println('\n');
  }

  private void percorrerPorNivel(Node root) {
    if (root == null)
      return;

    Queue<Node> queue = new LinkedList<>();
    queue.add(root);

    while (!queue.isEmpty()) {
      Node current = queue.poll();
      System.out.print(current.key + " ");
      if (current.left != null)
        queue.add(current.left);
      if (current.right != null)
        queue.add(current.right);
    }
  }

  // Questão 12 da lista
  // -----------------------------------------------------------------------------------------------------------

  public void codigos() {
    codigos(root, "");
  }

  private void codigos(Node root, String codigoAtual) {
    if (root == null)
      return;

    root.codigo = codigoAtual;
    System.out.println("Codigo do nó: " + root.key + " - é: " + root.codigo);

    if (root.left != null)
      codigos(root.left, codigoAtual + "0");
    if (root.right != null)
      codigos(root.right, codigoAtual + "1");
  }

  // Questão 13 da lista
  // -----------------------------------------------------------------------------------------------------------

  public int profundidadeIte(int codigo) {
    Node aux = search(codigo);
    if (aux == null)
      return -1;

    return profundidadeIte(root, codigo);
  }

  private int profundidadeIte(Node root, int codigo) {
    if (root == null || root.key == codigo)
      return 0;

    int cont = 0;
    Node aux = root;
    while (codigo != aux.key) {
      if (aux.left != null && codigo < aux.key) {
        aux = aux.left;
        cont++;
      } else if (aux.right != null && codigo > aux.key) {
        aux = aux.right;
        cont++;
      }
    }
    return cont;
  }

  public int profundidadeRec(int codigo) {
    Node aux = search(codigo);
    if (aux == null)
      return -1;

    return profundidadeRec(root, codigo);
  }

  // pronfunidade recursiva
  private int profundidadeRec(Node root, int codigo) {
    if (root == null)
      return 0;

    if (codigo < root.key)
      return profundidadeRec(root.left, codigo) + 1;
    else if (codigo > root.key)
      return profundidadeRec(root.right, codigo) + 1;
    else
      return 0;
  }
}