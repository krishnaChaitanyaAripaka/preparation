package com.preparation.datastructures;

import java.util.ArrayList;

/**
 * Created by chaitanya.ak on 07/06/16.
 */
public class HeapDemo {

  //static int ab;
  private static ArrayList<Integer> mList = new ArrayList<>();

  public static void main(String a[]) {

    //System.out.println(ab);
    addElementsForMinHeap();
    //addElementsForMaxHeap();
    //delete(1);
    //contains(8);
    containsWithHeapifyLogic(8);
    //print();
  }

  /**
   * 13 9 12 7 5 3 8 6 10 1
   *
   * 13 9
   * 9 13 - swap
   * 9 13 12
   * 9 13 12 7
   * 9 7 12 13 - swap
   * 7 9 12 13 - swap
   * 7 9 12 13 5
   * 7 5 12 13 9 -swap
   * 5 7 12 13 9
   * 5 7 12 13 9 3
   * 5 7 3 13 9 12 -swap
   * 3 7 5 13 9 12 -swap
   * 3 7 5 13 9 12 8
   * 3 7 5 13 9 12 8 6
   * 3 7 5 6 9 12 8 13 -swap
   * 3 6 5 7 9 12 8 13 -swap
   * 3 6 5 7 9 12 8 13 10
   * 3 6 5 7 9 12 8 13 10 1
   * 3 6 5 7 1 12 8 13 10 9 -swap
   * 3 1 5 7 6 12 8 13 10 9 -swap
   * 1 3 5 7 6 12 8 13 10 9 -swap
   */
  private static void addElementsForMinHeap() {
    addValueWithMinHeapify(13);
    addValueWithMinHeapify(9);
    addValueWithMinHeapify(12);
    addValueWithMinHeapify(7);
    //addValueWithMinHeapify(5);
    addValueWithMinHeapify(3);
    addValueWithMinHeapify(8);
    addValueWithMinHeapify(6);
    addValueWithMinHeapify(10);
    addValueWithMinHeapify(1);
  }

  /**
   *
   */
  private static void addElementsForMaxHeap() {
    addValueWithMaxHeapify(13);
    addValueWithMaxHeapify(9);
    addValueWithMaxHeapify(12);
    addValueWithMaxHeapify(7);
    addValueWithMaxHeapify(5);
    addValueWithMaxHeapify(3);
    addValueWithMaxHeapify(8);
    addValueWithMaxHeapify(6);
    addValueWithMaxHeapify(10);
    addValueWithMaxHeapify(1);
  }

  /**
   *
   * @param value
   */
  private static void addValueWithMinHeapify(int value) {
    mList.add(value);

    if (mList.size() == 1) return;

    minHeapify();
  }

  /**
   *
   * @param value
   */
  private static void addValueWithMaxHeapify(int value) {
    mList.add(value);

    if (mList.size() == 1) return;

    maxHeapify();
  }

  /**
   * 1 3 5 7 6 12 8 13 10 9
   *
   * 9 3 5 7 6 12 8 13 10  -swap
   * 3 9 5 7 6 12 8 13 10  -swap
   * 3 6 5 7 9 12 8 13 10  -swap
   */
  private static void delete(int value) {
    //step 1: find the index of the value to delete
    int indexOfValue = mList.indexOf(value);
    if (indexOfValue < 0) {
      return;
    }
    //step 2: put the last value in the heap at the index location of the item to delete
    mList.set(indexOfValue, mList.remove(mList.size() - 1));

    //step 3: verify heap ordering for each subtree which used to include the value
    minHeapifyForDelete(indexOfValue);
  }

  /**
   *
   */
  private static void minHeapifyForDelete(int i) {
    int childIndex;
    int size = mList.size();
    int leftChild;
    int rightChild;
    while (i < size - 1
        && (leftChild = 2 * i + 1) < size - 1
        && (rightChild = 2 * i + 2) < size - 1) {
      if (mList.get(i) > mList.get(leftChild) || mList.get(i) > mList.get(rightChild)) {
        childIndex = mList.get(leftChild) < mList.get(rightChild) ? leftChild : rightChild;
        swap(i, childIndex);
        i = childIndex;
        System.out.println(i);
      } else {
        break;
      }
    }
  }

  /**
   *
   */
  private static void minHeapify() {
    for (int i = mList.size() - 1; i > 0; ) {
      int parent = (i - 1) / 2;
      if (mList.get(i) < mList.get(parent)) {
        swap(i, parent);
        i = parent;
      } else {
        break;
      }
    }
  }

  /**
   *
   */
  private static void maxHeapify() {
    for (int i = mList.size() - 1; i > 0; ) {
      int parent = (i - 1) / 2;
      if (mList.get(i) > mList.get(parent)) {
        swap(i, parent);
        i = parent;
      } else {
        break;
      }
    }
  }

  /**
   *
   * @param value
   * @return
   */
  public static boolean contains(int value) {
    boolean isPresent;
    System.out.println(isPresent = mList.contains(value));
    return isPresent;
  }

  public static boolean containsWithHeapifyLogic(int value) {
    boolean isPresent = false;

    int start = 0;
    int nodes = 1;
    while (start < mList.size()) {
      start = nodes - 1;
      int end = nodes + start;
      int count = 0;

      while (start < mList.size() && start < end) {
        if (value == mList.get(start)) {
          isPresent = true;
          System.out.println(isPresent);
          return isPresent;
        } else if (value > mList.get((start - 1) / 2) && value < mList.get(start)) {
          count++;
          start++;
        } else {
          start++;
        }
      }

      if (count == nodes) {
        isPresent = false;
        System.out.println(isPresent);
        return isPresent;
      } else {
        nodes *= 2;
      }
    }

    System.out.println(isPresent);
    return isPresent;
  }

  /**
   *
   * @param i
   * @param childIndex
   */
  private static void swap(int i, int childIndex) {
    int temp = mList.get(i);
    mList.set(i, mList.get(childIndex));
    mList.set(childIndex, temp);
  }

  /**
   *
   */
  private static void traverse() {
    for (int value : mList) {
      System.out.println(value);
    }
  }
}