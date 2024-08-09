package cache_eviction;

import java.util.ArrayList;
import java.util.List;

import cache_eviction.exceptions.InvalidOperationException;
import cache_eviction.exceptions.NodeAlreadyExistsException;
import cache_eviction.exceptions.NodeNotFoundException;

public class TreeAVL { // base de dados AVL
  class Node { // classe Node
    int height;
    Node left, right;
    OrderService data;

    Node(OrderService data) {
      this.data = data;
    }
  }

  private Node root;
  private boolean rotated; // Atributo para indicar se houve rotação
  private RotationType rotationType; // Atributo para indicar o tipo de rotação

  TreeAVL() {
    this.root = null;
    this.rotated = false;
    this.rotationType = RotationType.NONE;
  }

  public int getHeight() {
    return height(root);
  }

  public void insert(OrderService data) throws NodeAlreadyExistsException { // método público de busca
    root = insert(root, data);
  }

  private Node insert(Node root, OrderService data) throws NodeAlreadyExistsException { // método recursivo privado de
                                                                                        // busca
    if (root == null)
      return new Node(data);

    if (data.getCodigo() < root.data.getCodigo())
      root.left = insert(root.left, data);
    else if (data.getCodigo() > root.data.getCodigo())
      root.right = insert(root.right, data);
    else
      // Node com mesmo código já existe
      throw new NodeAlreadyExistsException("O nó com o código " + data.getCodigo() + " já existe na árvore.");

    root.height = newHeight(root);

    return balance(root); // retorna o nó já balanceado tudo
  }

  public void remove(int codigo) throws InvalidOperationException { // método público de busca
    root = remove(root, codigo);
  }

  private Node remove(Node root, int codigo) throws InvalidOperationException { // método recursivo privado de busca
    if (root == null) {
      throw new InvalidOperationException("Árvore vazia ou nó não encontrado.");
    }

    if (codigo < root.data.getCodigo())
      root.left = remove(root.left, codigo); // procurando o node a ser removido
    else if (codigo > root.data.getCodigo())
      root.right = remove(root.right, codigo); // procurando o node a ser removido
    else {
      if (root.left == null && root.right == null) { // Se o node a ser removido não tem filhos
        root = null;
      } else if (root.left == null) { // Se o node a ser removido tem filho direito
        Node aux = root;
        root = aux.right;
        aux = null;
      } else if (root.right == null) { // Se o node a ser removido tem filho esquerdo
        Node aux = root;
        root = aux.left;
        aux = null;
      } else {
        Node aux = minorKey(root.right); // escolhi o lado direito como o professor.
        root.data = aux.data;
        root.right = remove(root.right, aux.data.getCodigo());
      }
    }

    if (root == null) {
      return root;
    }

    root.height = newHeight(root);

    return balance(root); // retorna o nó já balanceado tudo
  }

  public OrderService search(int codigo) throws NodeNotFoundException { // método público de busca
    return search(root, codigo);
  }

  private OrderService search(Node root, int codigo) throws NodeNotFoundException { // método recursivo privado de busca
    if (root == null) {
      throw new NodeNotFoundException("O nó com o código " + codigo + " não existe na árvore.");
    }

    if (codigo < root.data.getCodigo()) {
      return search(root.left, codigo);
    } else if (codigo > root.data.getCodigo()) {
      return search(root.right, codigo);
    } else {
      return root.data;
    }
  }

  public void ordem() { // método público para exibir em ordem
    this.ordem(root);
  }

  private void ordem(Node root) { // método privado para exibir em ordem
    if (root != null) {
      this.ordem(root.left);
      System.out.print(root.data.getCodigo() + " ");
      this.ordem(root.right);
    }
  }

  public List<OrderService> list() { // retorna uma lista de todos os elementos (OrderService) armazenados na árvore
    List<OrderService> list = new ArrayList<>();
    list(root, list);
    return list;
  }

  private void list(Node node, List<OrderService> list) {
    if (node != null) {
      list(node.left, list);
      list.add(node.data);
      list(node.right, list);
    }
  }

  public int getQuantityRecords() { // retorna a quantidade total de nós (elementos) presentes na árvore AVL.
    return getQuantityRecords(root);
  }

  private int getQuantityRecords(Node node) {
    if (node == null) {
      return 0;
    }
    return 1 + getQuantityRecords(node.left) + getQuantityRecords(node.right);
  }

  public void alter(OrderService ordemServico) throws NodeNotFoundException { // Método para alterar um nó na árvore
    // Primeiro, buscamos o nó correspondente ao código
    Node node = searchNode(root, ordemServico.getCodigo());

    // Se o nó for encontrado, atualizamos o valor
    if (node != null) {
      node.data = ordemServico; // Atualiza o valor do nó
      // Não precisam fazer balanceamento porque a atualização não altera a
      // estrutura da árvore
    } else {
      // Se o nó não existe, poderia inserir o novo nó, mas como a função não permite
      // inserir um novo nó, dispara uma exceção

      throw new NodeNotFoundException("O nó com o código " + ordemServico.getCodigo() + " não existe na árvore.");
    }
  }

  private Node searchNode(Node root, int codigo) { // Métodos exclusivo para buscar um nó na árvore e usar em alterar
    if (root == null) {
      return null;
    }
    if (codigo < root.data.getCodigo()) {
      return searchNode(root.left, codigo);
    } else if (codigo > root.data.getCodigo()) {
      return searchNode(root.right, codigo);
    } else {
      return root;
    }
  }

  private Node balance(Node root) {
    int fbRoot = getFB(root); // Fator de balanceamento do nó raiz
    int fbRootLeft = getFB(root.left); // fator de balanceamento do nó esquerdo
    int fbRootRight = getFB(root.right); // fator de balanceamento do nó direito

    if (fbRoot > 1) { // Se entrar aqui, então é uma rotação a direita
      if (fbRootLeft >= 0) { // Rotação simples a direita
        this.rotationType = RotationType.RIGHT;
        return simpleRightRotation(root);
      } else if (fbRootLeft < 0) { // Rotação dupla a direita
        this.rotationType = RotationType.LEFT_RIGHT;
        root.left = simpleLeftRotation(root.left);
        return simpleRightRotation(root);
      }
    } else if (fbRoot < -1) { // Se entrar aqui, então é uma rotação a esquerda
      if (fbRootRight <= 0) { // Rotação simples a esquerda
        this.rotationType = RotationType.LEFT;
        return simpleLeftRotation(root);
      } else if (fbRootRight > 0) { // Rotação dupla a esquerda
        this.rotationType = RotationType.RIGHT_LEFT;
        root.right = simpleRightRotation(root.right);
        return simpleLeftRotation(root);
      }
    }

    return root;
  }

  private Node simpleRightRotation(Node oldRoot) { // Rotação simples a direita
    Node newRoot = oldRoot.left;
    Node rightSub = newRoot.right;

    newRoot.right = oldRoot;
    oldRoot.left = rightSub;

    oldRoot.height = newHeight(oldRoot);
    newRoot.height = newHeight(newRoot);

    this.rotated = true; // Indica que houve rotação

    return newRoot;
  }

  private Node simpleLeftRotation(Node oldRoot) { // Rotação simples a esquerda
    Node newRoot = oldRoot.right;
    Node leftSub = newRoot.left;

    newRoot.left = oldRoot;
    oldRoot.right = leftSub;

    oldRoot.height = newHeight(oldRoot);
    newRoot.height = newHeight(newRoot);

    this.rotated = true; // Indica que houve rotação

    return newRoot;
  }

  private int newHeight(Node node) {
    if (node == null) {
      return 0;
    }
    // A altura é 1 mais o máximo entre a altura dos filhos
    return 1 + Math.max(height(node.left), height(node.right));
  }

  private Node minorKey(Node root) {
    if (root == null)
      return null;

    Node aux = root;

    while (aux.left != null)
      aux = aux.left;

    return aux;
  }

  private int height(Node root) {
    if (root == null)
      return -1;

    return root.height;
  }

  private int getFB(Node root) {
    if (root == null)
      return 0;

    return height(root.left) - height(root.right);
  }

  // Método para verificar se houve rotação e resetar o valor de rotated
  public boolean wasRotated() {
    boolean wasRotated = this.rotated;
    this.rotated = false; // Resetar o valor
    return wasRotated;
  }

  public String getRotationType() {
    RotationType rotation = this.rotationType;
    this.rotationType = RotationType.NONE; // Resetar o valor
    return rotation.toString();
  }
}