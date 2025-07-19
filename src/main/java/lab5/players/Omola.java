package lab5.players;

import lab5.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Omola extends Player {

    private final Random rand = new Random();

    public Omola(String name) {
        super(name);
    }

    @Override
    public Position pickNextMove(Board currentBoard) {
        PlayerToken myToken = currentBoard.getNextTurnToken();
        PlayerToken opponentToken = myToken.opponent();

        List<Position> availableMoves = currentBoard.getEmptyCells();

        for (Position pos : availableMoves) {
            Board testBoard = new Board(currentBoard);
            testBoard.place(pos, myToken);
            if (testBoard.getWinner() == myToken) {
                return pos;
            }
        }

        for (Position pos : availableMoves) {
            Board testBoard = new Board(currentBoard);
            testBoard.place(pos, opponentToken);
            if (testBoard.getWinner() == opponentToken) {
                return pos;
            }
        }

        return availableMoves.get(rand.nextInt(availableMoves.size()));
    }
}
