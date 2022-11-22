package gwentstone.cards.environmentcards;

import gwentstone.Board;
import gwentstone.GwentStone;
import gwentstone.cards.minioncards.StandardMinionCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HeartHound extends EnvironmentCard {
    static final int THREE = 3;
    public HeartHound() { }

    public HeartHound(final HeartHound heartHound) {
        super(heartHound);
    }

    public HeartHound(final int mana, final String description,
                      final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    @Override
    public final void useEnvironmentAbility(final int rowIdx) {
        Board board = GwentStone.getInstance().getBoard();
        int oppositeRowIdx = THREE - rowIdx;

        StandardMinionCard stolen = Collections.max(board.getRow(rowIdx),
                Comparator.comparingInt(
                        StandardMinionCard::getHealth));
        board.getRow(oppositeRowIdx).add(stolen);
        board.getRow(rowIdx).remove(stolen);

        removeFromHand();
    }

    @Override
    public final HeartHound deepCopy() {
        return new HeartHound(this);
    }
}
