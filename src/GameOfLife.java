// Created by Harshdeep Kahlon
// 11/01/2019
// GameOfLife.java

public class GameOfLife<T> {
  boolean[][] coordinates;
  int length;
  int width;
  int currentRow;

  // Default
  public GameOfLife() {
    coordinates = new boolean[32][32];
    length = 32;
    width = 32;
    currentRow = 2;
  }

  // Parameter
  public GameOfLife(int length, int width) {
    this.coordinates = new boolean[length][width];
    this.length = length;
    this.width = width;
    this.currentRow = 2;
  }

  public void input(String setOfData) {
    for (int i = 0; i < setOfData.length(); i++) {
      int currentNumber = Character.getNumericValue(setOfData.charAt(i));
      if (currentNumber != 0) {
        convertInput(currentNumber, (i * 4));
      }
    }
    currentRow++;
  }

  public void convertInput(int currentNumber, int currentNode) {
    int currentInput = currentNumber;
    if (currentInput / 8 > 0) {
      coordinates[currentRow][currentNode] = true;
      currentInput = currentInput - 8;
    }
    if (currentInput / 4 > 0) {
      coordinates[currentRow][currentNode + 1] = true;
      currentInput = currentInput - 4;
    }
    if (currentInput / 2 > 0) {
      coordinates[currentRow][currentNode + 2] = true;
      currentInput = currentInput - 2;
    }
    if (currentInput > 0) {
      coordinates[currentRow][currentNode + 3] = true;
    }
  }

  public void dumpCompact() {
    for (int y = 0; y < (length); y++) {
      int counter = 0;
      int tempWillBecomeHex = 0;
      for (int x = 0; x < (width); x++) {
        if (coordinates[y][x]) {
          if (counter == 0) {
            tempWillBecomeHex = tempWillBecomeHex + 8;
          }
          if (counter == 1) {
            tempWillBecomeHex = tempWillBecomeHex + 4;
          }
          if (counter == 2) {
            tempWillBecomeHex = tempWillBecomeHex + 2;
          }
          if (counter == 3) {
            tempWillBecomeHex = tempWillBecomeHex + 1;
          }
        }
        counter++;
        if (counter == 4) {
          System.out.print(Integer.toHexString(tempWillBecomeHex));
          tempWillBecomeHex = 0;
          counter = 0;
        }
      }
      System.out.println();
    }
    System.out.println();
  }

  public void dumpUserFriendly() {
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < width; j++) {
        if (coordinates[i][j]) {
          System.out.print("*");
        } else {
          System.out.print(".");
        }
      }
      System.out.println();
    }
    System.out.println();
  }

  public boolean checkNeighbors(boolean currentNode, int y, int x) {
    int liveNeighbors = 0;

    if (coordinates[y + 1][x]) liveNeighbors++;

    if (coordinates[y + 1][x + 1]) liveNeighbors++;

    if (coordinates[y + 1][x - 1]) liveNeighbors++;

    if (coordinates[y][x + 1]) liveNeighbors++;

    if (coordinates[y][x - 1]) liveNeighbors++;

    if (coordinates[y - 1][x + 1]) liveNeighbors++;

    if (coordinates[y - 1][x]) liveNeighbors++;

    if (coordinates[y - 1][x - 1]) liveNeighbors++;

    return (currentNode && liveNeighbors > 1 && liveNeighbors < 4) || liveNeighbors == 3;

  }

  public void nextIteration() {
    boolean[][] temp = new boolean[length][width];
    for (int y = 1; y < (length - 1); y++) {
      for (int x = 1; x < (width - 1); x++) {
        temp[y][x] = checkNeighbors(coordinates[y][x], y, x);
      }
    }
    coordinates = temp;
  }

  public static void main(String[] args) {
    var conways = new GameOfLife<Integer>(32, 32);
    conways.input("00030000");
    conways.input("00030000");
    conways.input("0000C000");
    conways.input("0000C000");
    conways.dumpUserFriendly();
    conways.dumpCompact();

    conways.nextIteration();

    conways.dumpUserFriendly();
    conways.dumpCompact();

    conways.nextIteration();

    conways.dumpUserFriendly();
    conways.dumpCompact();
  }

}
