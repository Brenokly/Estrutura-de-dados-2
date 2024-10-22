package cache_eviction_final.structure.listPstructure;

/*
 * Aqui, vamos implementar a estrutura de dados MinHeap.
 * MinHeap é uma árvore binária completa onde o valor do nó pai é menor que o valor dos nós filhos.
 * A raiz é o menor elemento da árvore.
 */

import java.util.ArrayList;
import java.util.List;
import cache_eviction_final.compression.Node;;

// Estrutura de dados MinHeap personalizada
public class MinHeap {
  private List<Node> heap; // Lista para armazenar a heap

  // Construtor para inicializar a heap
  public MinHeap() {
    this.heap = new ArrayList<>(); // Inicializa a lista que armazenará os nós
  }

  // Retorna o tamanho da heap
  public int size() {
    return heap.size(); // Retorna o número de elementos na heap
  }

  // Verifica se a heap está vazia
  public boolean isEmpty() {
    return heap.isEmpty(); // Retorna verdadeiro se a heap não contiver elementos
  }

  // Inserir um nó na heap
  public void insert(Node node) {
    heap.add(node); // Adiciona o nó no final da lista
    heapifyUp(heap.size() - 1); // Corrige a posição do nó para manter a propriedade do MinHeap
  }

  // Remover e retornar o menor elemento da heap (raiz)
  public Node extractMin() {
    if (isEmpty()) // Verifica se a heap está vazia
      return null; // Retorna nulo se não houver elementos

    Node minNode = heap.get(0); // Menor elemento é a raiz
    Node lastNode = heap.remove(heap.size() - 1); // Remove o último nó

    if (!isEmpty()) { // Se ainda houver elementos na heap
      heap.set(0, lastNode); // Coloca o último nó na raiz
      heapifyDown(0); // Corrige a posição do nó para manter a propriedade do MinHeap
    }

    return minNode; // Retorna o menor nó
  }

  // Método para corrigir a heap de baixo para cima (inserção)
  private void heapifyUp(int index) {
    int parentIndex = (index - 1) / 2; // Índice do pai

    // Se o nó atual é menor que seu pai, faz a troca
    if (index > 0 && heap.get(index).getFrequency() < heap.get(parentIndex).getFrequency()) {
      swap(index, parentIndex); // Troca o nó com seu pai
      heapifyUp(parentIndex); // Continua subindo
    }
  }

  // Método para corrigir a heap de cima para baixo (remoção)
  private void heapifyDown(int index) {
    int leftChild = 2 * index + 1; // Índice do filho esquerdo
    int rightChild = 2 * index + 2; // Índice do filho direito
    int smallest = index; // Assume que o menor é o nó atual

    // Verifica se o filho esquerdo existe e é menor que o nó atual
    if (leftChild < heap.size() && heap.get(leftChild).getFrequency() < heap.get(smallest).getFrequency()) {
      smallest = leftChild; // Atualiza o menor
    }
    // Verifica se o filho direito existe e é menor que o menor atual
    if (rightChild < heap.size() && heap.get(rightChild).getFrequency() < heap.get(smallest).getFrequency()) {
      smallest = rightChild; // Atualiza o menor
    }
    // Se o menor não for o nó atual, faz a troca
    if (smallest != index) {
      swap(index, smallest); // Troca o nó atual com o menor filho
      heapifyDown(smallest); // Continua descendo
    }
  }

  // Método para trocar dois elementos na lista
  private void swap(int i, int j) {
    Node temp = heap.get(i); // Armazena o nó em i
    heap.set(i, heap.get(j)); // Coloca o nó em j na posição de i
    heap.set(j, temp); // Coloca o nó em i na posição de j
  }
}