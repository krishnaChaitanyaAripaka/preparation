package com.preparation.datastructures;

/**
 * Created by chaitanya.ak on 14/06/16.
 */
public class AVL<E> extends BinarySearchTree<E> {

  @Override public Tree insertNode(Tree tree, E value) {
    if (isLessThan(value, tree.value)) {
      if (null == tree.left) {
        Tree temp = new Tree();
        temp.value = value;
        tree.left = temp;
      } else {
        tree.left = insertNode(tree.left, value);
        tree = checkTreeBalance(tree);
      }
    } else {
      if (null == tree.right) {
        Tree temp = new Tree();
        temp.value = value;
        tree.right = temp;
      } else {
        tree.right = insertNode(tree.right, value);
        tree = checkTreeBalance(tree);
      }
    }

    return tree;
  }

  public Tree rotationWithLeftChild(Tree tree) {
    Tree current = tree.left;
    tree.left = current.right;
    current.right = tree;

    return current;
  }

  public Tree rotationWithRightChild(Tree tree) {
    Tree current = tree.right;
    tree.right = current.left;
    current.left = tree;
    return current;
  }

  public Tree doubleRotationWithRightChild(Tree tree) {
    tree.right = rotationWithLeftChild(tree.right);
    return rotationWithRightChild(tree);
  }

  public Tree doubleRotationWithLeftChild(Tree tree) {
    tree.left = rotationWithRightChild(tree.left);
    return rotationWithLeftChild(tree);
  }

  public int findHeight(Tree tree) {
    if (tree == null) return -1;

    return Math.max(findHeight(tree.left), findHeight(tree.right)) + 1;
  }

  public Tree checkTreeBalance(Tree tree) {

    if (findHeight(tree.left) - findHeight(tree.right) > 1) { //left is greater
      if (findHeight(tree.left.left) - findHeight(tree.left.right) > 0) {//left greater
        return rotationWithLeftChild(tree); //ll rotation
      } else {//right is greater
        return doubleRotationWithLeftChild(tree);
      }
    } else if (findHeight(tree.left) - findHeight(tree.right) < -1) { //right greater
      if (findHeight(tree.right.left) - findHeight(tree.right.right) < 0) {//right greater
        return rotationWithRightChild(tree);
      } else {//left greater
        return doubleRotationWithRightChild(tree);
      }
    }

    return tree;
  }
}
