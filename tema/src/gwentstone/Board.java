package gwentstone;

import gwentstone.cards.minioncards.StandardMinionCard;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public final List<List<StandardMinionCard>> getMatrix() {
        return matrix;
    }
    static final int FIVE = 5;

    public final void setMatrix(final List<List<StandardMinionCard>> matrix) {
        this.matrix = matrix;
    }

    private List<List<StandardMinionCard>> matrix;
    private int rows;
    private int columns;

    public Board(final int rows, final int columns) {
        matrix = new ArrayList<List<StandardMinionCard>>(rows);

        for (int i = 0; i < FIVE; i++) {
            matrix.add(new ArrayList<>(columns));
        }

        this.rows = rows;
        this.columns = columns;
    }

    public final List<StandardMinionCard> getRow(final int rowIdx) {
        return matrix.get(rowIdx);
    }

    /**
     * for checking if the row is full
     * @param rowIdx
     * @return
     */
    public final boolean rowIsFull(final int rowIdx) {
        return (getRow(rowIdx).size() == FIVE);
    }

    /**
     * for reset the card from the matrix
     */
    public final void reset() {
        for (List<StandardMinionCard> row : matrix) {
            row.clear();
        }
    }
}
