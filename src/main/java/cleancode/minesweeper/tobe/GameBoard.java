package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.CellSnapshot;
import cleancode.minesweeper.tobe.cell.Cells;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.CellPositions;
import cleancode.minesweeper.tobe.position.RelativePosition;
import java.util.List;

public class GameBoard {

  private final Cell[][] board;
  private final int landMineCount;
  private GameStatus gameStatus;

  public GameBoard(GameLevel gameLevel) {
    int rowSize = gameLevel.getRowSize();
    int colSize = gameLevel.getColSize();
    board = new Cell[rowSize][colSize];

    landMineCount = gameLevel.getLandMineCount();
    initializeGameStatus();
  }

  // 상태변경
  public void initializeGame() {
    initializeGameStatus();
    CellPositions cellPositions = CellPositions.from(board);

    initializeEmptyCells(cellPositions);

    List<CellPosition> landMinePositions = cellPositions.extractRandomPositions(landMineCount);
    initializeLandMineCells(landMinePositions);

    List<CellPosition> numberPositionCandidates = cellPositions.subtract(landMinePositions);
    initializeNumberCells(numberPositionCandidates);
  }

  public void flagAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    cell.flag();

    checkIfGameIsOver();
  }

  private void openOneAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    cell.open();
  }

  public void openAt(CellPosition cellPosition) {
    if (isLandMineCellAt(cellPosition)) {
      openOneAt(cellPosition);
      changeGameStatusToLose();
      return;
    }

    openSurroundedCells(cellPosition);
    checkIfGameIsOver();
  }

  // 판별
  public boolean isInProgress() {
    return gameStatus == GameStatus.IN_PROGRESS;
  }

  public boolean isInvalidCellPosition(CellPosition cellPosition) {
    int rowSize = getRowSize();
    int colSize = getColSize();

    return cellPosition.isRowIndexMoreThanOrEqual(rowSize)
        || cellPosition.isColIndexMoreThanOrEqual(colSize);
  }

  public boolean isLandMineCellAt(CellPosition cellPosition) {
    return findCell(cellPosition).isLandMine();
  }

  public boolean isWinStatus() {
    return gameStatus == GameStatus.WIN;
  }

  public boolean isLoseStatus() {
    return gameStatus == GameStatus.LOSE;
  }


  // 조회
  public int getRowSize() {
    return board.length;
  }

  public int getColSize() {
    return board[0].length;
  }

  public CellSnapshot getSnapshot(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.getSnapshot();
  }



  // private
  private void initializeGameStatus() {
    gameStatus = GameStatus.IN_PROGRESS;
  }

  private void initializeEmptyCells(CellPositions cellPositions) {
    List<CellPosition> allPositions = cellPositions.getPositions();
    for(CellPosition position : allPositions) {
      updateCellAt(position, new EmptyCell());
    }
  }

  private void initializeLandMineCells(List<CellPosition> landMinePositions) {
    for(CellPosition position : landMinePositions) {
      updateCellAt(position, new LandMineCell());
    }
  }

  private void initializeNumberCells(List<CellPosition> numberPositionCandidates) {
    for(CellPosition candidate : numberPositionCandidates) {
      int count = countNearByLandMines(candidate);
      if(count != 0) {
        updateCellAt(candidate, new NumberCell(count));
      }
    }
  }

  private void updateCellAt(CellPosition position, Cell cell) {
    board[position.getRowIndex()][position.getColIndex()] = cell;
  }

  private void checkIfGameIsOver() {
    if (isAllCellChecked()) {
      changeGameStatusToWin();
    }
  }

  private void changeGameStatusToWin() {
    gameStatus = GameStatus.WIN;
  }

  private void changeGameStatusToLose() {
    gameStatus = GameStatus.LOSE;
  }


  private boolean isAllCellChecked() {
    Cells cells = Cells.from(board);
    return cells.isAllChecked();
  }

  private boolean isOpenedCell(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.isOpened();
  }

  private boolean doesCellHasLandMineCount(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.hasLandMineCount();
  }

  private int countNearByLandMines(CellPosition cellPosition) {

    int rowSize = getRowSize();
    int colSize = getColSize();

    long count = calculateSurroundedPositions(cellPosition, rowSize, colSize).stream()
        .filter(this::isLandMineCellAt)
        .count();

    return (int) count;
  }

  private static List<CellPosition> calculateSurroundedPositions(
      CellPosition cellPosition, int rowSize, int colSize) {
    return RelativePosition.SURROUNDED_POSITIONS.stream()
        .filter(cellPosition::canCalculatePositionBy)
        .map(cellPosition::calculatePositionBy)
        .filter(position -> position.isRowIndexLessThan(rowSize))
        .filter(position -> position.isColIndexLessThan(colSize))
        .toList();
  }

  private void openSurroundedCells(CellPosition cellPosition) {
    if (isOpenedCell(cellPosition)) {
      return;
    }
    if (isLandMineCellAt(cellPosition)) {
      return;
    }

    openOneAt(cellPosition);

    if (doesCellHasLandMineCount(cellPosition)) {
      return;
    }

    List<CellPosition> cellPositions = calculateSurroundedPositions(cellPosition, getRowSize(), getColSize());
    cellPositions.forEach(this::openSurroundedCells);

  }

  private Cell findCell(CellPosition cellPosition) {
    return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
  }
}
