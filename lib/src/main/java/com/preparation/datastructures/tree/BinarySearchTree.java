package com.preparation.datastructures.tree;

import com.preparation.datastructures.queue.Queue;
import java.util.NoSuchElementException;

/**
 * Created by chaitanya.ak on 11/06/16.
 */
public class BinarySearchTree<E> {

  public Tree mTree;

  public class Tree {
    public E value;
    public Tree left;
    public Tree right;
  }

  public void insert(E value) {

    if (null == mTree) {
      Tree temp = new Tree();
      temp.value = value;
      mTree = temp;
    } else {
      mTree = insertNode(mTree, value);
    }
  }

  public Tree insertNode(Tree tree, E value) {
    if (isValueLessThanTreeValue(tree.value, value)) {
      if (null == tree.left) {
        Tree temp = new Tree();
        temp.value = value;
        tree.left = temp;
      } else {
        insertNode(tree.left, value);
      }
    } else {
      if (null == tree.right) {
        Tree temp = new Tree();
        temp.value = value;
        tree.right = temp;
      } else {
        insertNode(tree.right, value);
      }
    }

    return tree;
  }

  public boolean contains(E value) {
    return contains(mTree, value);
  }

  private boolean contains(Tree tree, E value) {
    if (tree == null) {
      return false;
    } else if (equals(value, tree.value)) {
      return true;
    } else {
      if (isValueLessThanTreeValue(tree.value, value)) {
        return contains(tree.left, value);
      } else {
        return contains(tree.right, value);
      }
    }
  }

  public void preOrder() {
    preOrder(mTree);
  }

  private void preOrder(Tree root) {
    if (root == null) return;

    System.out.println(root.value);
    preOrder(root.left);
    preOrder(root.right);
  }

  public void inOrder() {
    inOrder(mTree);
  }

  private void inOrder(Tree root) {
    if (root == null) return;

    inOrder(root.left);
    System.out.println(root.value);
    inOrder(root.right);
  }

  public void postOrder() {
    postOrder(mTree);
  }

  private void postOrder(Tree root) {
    if (root == null) return;

    postOrder(root.left);
    postOrder(root.right);
    System.out.println(root.value);
  }

  /*
  case 1: Node to be deleted is a leaf(i.e, node has not children)
  case 2: Node to be deleted has one child and it is a left sub child
  case 3: Node to be deleted has one child and it is a right sub child
  case 4: Node to be deleted has two children
  1. Find node to delete - 7
  2. Find max(node) of left sub tree from node to delete / min(node) of right sub tree from node to delete - 6 / 10
  3. Find parent node of the node to be deleted - 5
  4. f( max(node) of left sub tree in temp - 6 / 10
  5. Replace node to be deleted with max(node) of left sub tree - 6 / 10
   */
  public Tree delete(Tree root, E value) {
    Tree nodeToDelete = findNode(root, value);
    if (nodeToDelete == null) return root;

    Tree parent = findParentNode(root, nodeToDelete);
    if (parent == null && nodeToDelete.left == null && nodeToDelete.right == null) {
      root = null;
      return root;
    }

    if (nodeToDelete.left == null && nodeToDelete.right == null) {//case 1
      if (isValueLessThanTreeValue(parent.value, nodeToDelete.value)) {
        parent.left = null;
      } else {
        parent.right = null;
      }
    } else if (nodeToDelete.right == null) {//case 2
      if (isValueLessThanTreeValue(parent.value, nodeToDelete.value)) {
        parent.left = nodeToDelete.left;
      } else {
        parent.right = nodeToDelete.left;
      }
    } else if (nodeToDelete.left == null) {//case 3
      if (isValueLessThanTreeValue(parent.value, nodeToDelete.value)) {
        parent.left = nodeToDelete.right;
      } else {
        parent.right = nodeToDelete.right;
      }
    } else {//case 4
      //Any one logic can be used either maxValueOfLeftSubTree or minValueOfRightSubTree

      //maxValueOfLeftSubTree
      Tree maxValue = maxValueOfLeftSubTree(nodeToDelete);
      nodeToDelete.value = maxValue.value;
      nodeToDelete.left = delete(nodeToDelete.left, maxValue.value);

      //minValueOfRightSubTree
      //Tree minValue = minValueOfRightSubTree(nodeToDelete);
      //nodeToDelete.value = minValue.value;
      //nodeToDelete.right = delete(nodeToDelete.right, minValue.value);
    }

    return root;
  }

  public void delete(E value) {
    delete(mTree, value);
  }

  protected Tree findNode(Tree root, E value) {
    if (root == null) {
      return null;
    } else if (equals(value, root.value)) {
      return root;
    } else {
      if (isValueLessThanTreeValue(root.value, value)) {
        return findNode(root.left, value);
      } else {
        return findNode(root.right, value);
      }
    }
  }

  protected Tree findParentNode(Tree root, Tree value) {//23,1 (7,1)
    if (root == null) {
      return null;
    } else if (equals(value.value, root.value)) {
      return null;
    } else if (isValueLessThanTreeValue(root.value, value.value)) {
      if (root.left.value == value.value) {
        return root;
      } else {
        return findParentNode(root.left, value);
      }
    } else {
      if (root.right.value == value.value) {
        return root;
      } else {
        return findParentNode(root.right, value);
      }
    }
  }

  protected Tree maxValueOfLeftSubTree(Tree value) {//23,1 (7,1)
    value = value.left;
    while (value.right != null) {
      value = value.right;
    }

    return value;
  }

  protected Tree minValueOfRightSubTree(Tree value) {//23,1 (7,1)
    value = value.right;
    while (value.left != null) {
      value = value.left;
    }

    return value;
  }

  public void breadthFirstTraversal() throws InterruptedException {

    Queue queue = new Queue();
    if (mTree == null) {
      return;
    }

    while (mTree != null) {
      System.out.println(mTree.value);
      if (mTree.left != null) {
        queue.enQueue(mTree.left);
      }
      if (mTree.right != null) {
        queue.enQueue(mTree.right);
      }
      if (queue.notEmpty()) {
        mTree = (Tree) queue.deQueue();
      } else {
        mTree = null;
      }
    }
  }

  protected boolean isValueLessThanTreeValue(E treeValue, E valueToInsert) {
    if (treeValue instanceof Integer) {
      return (Integer) valueToInsert < (Integer) treeValue;
    }

    throw new NoSuchElementException();
  }

  protected boolean equals(E valueOne, E valueTwo) {
    if (valueOne instanceof Integer) {
      return (Integer) valueOne == (Integer) valueTwo;
    }

    throw new NoSuchElementException();
  }

}
