package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.position.CellPosition;

public class Minesweeper implements GameInitializable, GameRunnable {

  private final GameBoard gameBoard;
  private final BoardIndexConverter indexConverter = new BoardIndexConverter();
  private final InputHandler inputHandler;
  private final OutputHandler outputHandler;
  private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

  public Minesweeper(GameLevel gameLevel, InputHandler consoleInputHandler, OutputHandler consoleOutputHandler) {
    gameBoard = new GameBoard(gameLevel);
    this.inputHandler = consoleInputHandler;
    this.outputHandler = consoleOutputHandler;
  }

  @Override
  public void initialize() {
    gameBoard.initializeGame();
  }

  @Override
  public void run() {

    outputHandler.showGameStartComments();

    while (true) {
      try {
        outputHandler.showBoard(gameBoard);

        if (doesUserWinTheGame()) {
          outputHandler.showGameWinningComment();
          break;
        }
        if (doesUserLoseTheGame()) {
          outputHandler.showGameLosingComment();
          break;
        }

        CellPosition cellPosition = getCellInputFromUser();
        String userActionInput = getUserActionInputFromUser();
        actOnCell(cellPosition, userActionInput);

      } catch (GameException e) {
        outputHandler.showExceptionMessage(e);

      } catch (Exception e) {
        outputHandler.showSimpleMessage("프로그램에 문자가 생겼습니다.");
      }
    }
  }

  private void actOnCell(CellPosition cellPosition, String userActionInput) {
    if (doesUserChooseToPlantFlag(userActionInput)) {
      gameBoard.flagAt(cellPosition);
      checkIfGameIsOver();
      return;
    }

    if (doesUserChooseToOpenCell(userActionInput)) {

      if (gameBoard.isLandMineCellAt(cellPosition)) {
        gameBoard.openAt(cellPosition);
        changeGameStatusToLose();
        return;
      }

      gameBoard.openSurroundedCells(cellPosition);
      checkIfGameIsOver();
      return;
    }
    System.out.println("잘못된 번호를 선택하셨습니다.");

  }

  private void changeGameStatusToLose() {
    gameStatus = -1;
  }

  private boolean doesUserChooseToOpenCell(String userActionInput) {
    return userActionInput.equals("1");
  }

  private boolean doesUserChooseToPlantFlag(String userActionInput) {
    return userActionInput.equals("2");
  }



  private String getUserActionInputFromUser() {
    outputHandler.showCommentForUserAction();
    return inputHandler.getUserInput();
  }

  private CellPosition getCellInputFromUser() {
    outputHandler.showCommentForSelectCell();
    CellPosition cellPosition = inputHandler.getCellPositionFromUser();
    if (gameBoard.isInvalidCellPosition(cellPosition)) {
      throw new GameException("잘못된 좌표를 선택하셨습니다.");
    }
    return cellPosition;
  }

  private boolean doesUserLoseTheGame() {
    return gameStatus == -1;
  }

  private boolean doesUserWinTheGame() {
    return gameStatus == 1;
  }

  private void checkIfGameIsOver() {
    if (gameBoard.isAllCellChecked()) {
      changeGameStatusToWin();
    }
  }

  private void changeGameStatusToWin() {
    gameStatus = 1;
  }


}
