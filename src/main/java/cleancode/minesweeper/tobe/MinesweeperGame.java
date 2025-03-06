package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.Beginner;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;

public class MinesweeperGame {


    public static void main(String[] args) {

        GameLevel gameLevel = new Beginner();

        Minesweeper minesweeper = new Minesweeper(gameLevel);
        minesweeper.run();
    }



}
