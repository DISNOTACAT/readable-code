package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

public class Minesweeper implements GameInitializable, GameRunnable {

  private final GameBoard gameBoard;
  private final BoardIndexConverter indexConverter = new BoardIndexConverter();
  private final InputHandler consoleInputHandler;
  private final OutputHandler consoleOutputHandler;
  private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

  public Minesweeper(GameLevel gameLevel, InputHandler consoleInputHandler, OutputHandler consoleOutputHandler) {
    gameBoard = new GameBoard(gameLevel);
    this.consoleInputHandler = consoleInputHandler;
    this.consoleOutputHandler = consoleOutputHandler;
  }

  @Override
  public void initialize() {
    gameBoard.initializeGame();
  }

  @Override
  public void run() {

    consoleOutputHandler.showGameStartComments();

    while (true) {
      try {
        consoleOutputHandler.showBoard(gameBoard);

        if (doesUserWinTheGame()) {
          consoleOutputHandler.showGameWinningComment();
          break;
        }
        if (doesUserLoseTheGame()) {
          consoleOutputHandler.showGameLosingComment();
          break;
        }

        String cellInput = getCellInputFromUser();
        String userActionInput = getUserActionInput();
        actOnCell(cellInput, userActionInput);

      } catch (GameException e) {
        consoleOutputHandler.showExceptionMessage(e);

      } catch (Exception e) {
        consoleOutputHandler.showSimpleMessage("프로그램에 문자가 생겼습니다.");
      }
    }
  }

  private void actOnCell(String cellInput, String userActionInput) {
    int selectedColIndex = indexConverter.getSelectedColIndex(cellInput, gameBoard.getColSize());
    int selectedRowIndex = indexConverter.getSelectedRowIndex(cellInput, gameBoard.getRowSize());

    if (doesUserChooseToPlantFlag(userActionInput)) {
      gameBoard.flag(selectedRowIndex, selectedColIndex);
      checkIfGameIsOver();
      return;
    }

    if (doesUserChooseToOpenCell(userActionInput)) {
      if (gameBoard.isLandMineCell(selectedRowIndex, selectedColIndex)) {
        gameBoard.open(selectedRowIndex, selectedColIndex);
        changeGameStatusToLose();
        return;
      }

      gameBoard.openSurroundedCells(selectedRowIndex, selectedColIndex);
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



  private String getUserActionInput() {
    consoleOutputHandler.showCommentForUserAction();
    return consoleInputHandler.getUserInput();
  }

  private String getCellInputFromUser() {
    consoleOutputHandler.showCommentForSelectCell();
    return consoleInputHandler.getUserInput();
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
