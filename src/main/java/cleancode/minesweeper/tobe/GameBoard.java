package cleancode.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {

  private final int LAND_MINE_COUNT = 10;
  private final Cell[][] board;


  public GameBoard(int row, int col) {
    board = new Cell[row][col];
  }

  public int getRowSize() {
    return board.length;
  }

  public int getColSize() {
    return board[0].length;
  }

  public void initializeGame() {

    int rowSize = getRowSize();
    int colSize = getColSize();

    for (int row = 0; row < rowSize; row++) {
      for (int col = 0; col < colSize; col++) {
        board[row][col] = Cell.create();
      }
    }
    for (int i = 0; i < LAND_MINE_COUNT; i++) {
      int landMineCol = new Random().nextInt(colSize);
      int landMineRow = new Random().nextInt(rowSize);
      Cell landMineCell = findCell(landMineRow, landMineCol);
      landMineCell.turnOnLandMine();
    }
    for (int row = 0; row < rowSize; row++) { //  반복문 내에도 i 와 j 의 역할을 명시했다.
      for (int col = 0; col < colSize; col++) {

        if (isLandMineCell(row, col)) {
          continue;
        }

        int count = countNearByLandMines(row, col);
        Cell cell = findCell(row, col);
        cell.updateNearbyLandMineCount(count);
      }
    }
  }

  public String getSign(int rowIndex, int colIndex) {
    Cell cell = findCell(rowIndex, colIndex);
    return cell.getSign();
  }

  private Cell findCell(int rowIndex, int colIndex) {
    return board[rowIndex][colIndex];
  }

  public void flag(int rowIndex, int colIndex) {
    Cell cell = findCell(rowIndex, colIndex);
    cell.flag();
  }

  public boolean isLandMineCell(int selectedRowIndex,
      int selectedColIndex) {
    return findCell(selectedRowIndex, selectedColIndex).isLandMine();
  }

  public void open(int rowIndex, int colIndex) {
    Cell cell = findCell(rowIndex, colIndex);
    cell.open();
  }

  public void openSurroundedCells(int row, int col) {
    if (row < 0 || row >= getRowSize() || col < 0 || col >= getColSize()) {
      return;
    }
    if (isOpenedCell(row, col)) {
      return;
    }
    if (isLandMineCell(row, col)) {
      return;
    }

    open(row, col);

    if (doesCellHasLandMineCount(row, col)) {
      return;
    }

    openSurroundedCells(row - 1, col - 1);
    openSurroundedCells(row - 1, col);
    openSurroundedCells(row - 1, col + 1);
    openSurroundedCells(row, col - 1);
    openSurroundedCells(row, col + 1);
    openSurroundedCells(row + 1, col - 1);
    openSurroundedCells(row + 1, col);
    openSurroundedCells(row + 1, col + 1);
  }

  public boolean isAllCellChecked() {
    return Arrays.stream(board) // Stream<String[]>
        .flatMap(Arrays::stream)
        .allMatch(Cell::isChecked);
  }

  private boolean doesCellHasLandMineCount(int row, int col) {
    return findCell(row, col).hasLandMineCount();
  }

  private boolean isOpenedCell(int row, int col) {
    return findCell(row, col).isOpened();
  }

  private int countNearByLandMines(int row, int col) {

    int rowSize = getRowSize();
    int colSize = getColSize();
    int count = 0;

    if (row - 1 >= 0 && col - 1 >= 0 && doesCellHasLandMineCount(row - 1, col - 1)) {
      count++;
    }
    if (row - 1 >= 0 && doesCellHasLandMineCount(row - 1, col)) {
      count++;
    }
    if (row - 1 >= 0 && col + 1 < colSize && isLandMineCell(row - 1,
        col + 1)) {
      count++;
    }
    if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
      count++;
    }
    if (col + 1 < colSize && isLandMineCell(row, col + 1)) {
      count++;
    }
    if (row + 1 < rowSize && col - 1 >= 0 && isLandMineCell(row + 1,
        col - 1)) {
      count++;
    }
    if (row + 1 < rowSize && isLandMineCell(row + 1, col)) {
      count++;
    }
    if (row + 1 < rowSize && col + 1 < colSize && isLandMineCell(
        row + 1,
        col + 1)) {
      count++;
    }
    return count;
  }
}
