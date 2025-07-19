package lab5.players;

import lab5.game.Board;
import lab5.game.Position;
import lab5.game.Row;
import lab5.game.Col;
import java.util.Random;

public class Randy extends Player {

    Random rand = new Random();

    public Randy(String name) {
        super(name);
    }

    public Position pickNextMove(Board currentBoard) {
        Row[] rows = Row.values();
        Col[] cols = Col.values();

        while (true) {
            Row randRow = rows[rand.nextInt(rows.length)];
            Col randCol = cols[rand.nextInt(cols.length)];

            Position move = new Position(randRow, randCol);

            if (currentBoard.isEmptyAt(move)) {
                return move;
            }
        }
    }
}
