package gwentstone.cards.minioncards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import gwentstone.Board;
import gwentstone.GwentStone;
import gwentstone.cards.Card;
import gwentstone.cards.herocards.HeroCard;

import java.util.ArrayList;

@JsonPropertyOrder({"mana", "attackDamage", "health", "description", "colors", "name"})
public abstract class StandardMinionCard extends Card {

    private int health;

    private int attackDamage;

    @JsonIgnore
    private boolean frozen = false;
    @JsonIgnore
    private int rowIdx;
    @JsonIgnore

    private boolean available = true;

    protected StandardMinionCard() { }

    protected StandardMinionCard(final int mana, final String description,
                                 final ArrayList<String> colors, final String name,
                                 final int health, final int attackDamage) {
        super(mana, description, colors, name);

        this.health = health;
        this.attackDamage = attackDamage;
    }

    protected StandardMinionCard(final StandardMinionCard standardMinionCard) {
        super(standardMinionCard);

        this.health = standardMinionCard.health;
        this.attackDamage = standardMinionCard.attackDamage;
    }

    /**
     * for checking if is Environment Card
     * @return
     */
    @Override
    public final boolean isEnvironmentCard() {
        return false;
    }

    /**
     * for checking if the Minion Card is in the front line
     * @return
     */
    @JsonIgnore
    public  boolean isFrontlineMinion() {
        return false;
    }

    @JsonIgnore
    public boolean isTank() {
        return false;
    }

    /**
     *
     * @param attackedMinionCard the attacked minion card
     */
    public final void attackCard(final StandardMinionCard attackedMinionCard) {
        attackedMinionCard.takeDamage(attackDamage);
        available = false;
    }

    /**
     * for attack the hero
     */
    public final void attackHero() {
        HeroCard enemyHero = GwentStone.getInstance()
                .getPlayers()[GwentStone.getInstance()
                .getCurrentPlayerIdx() % 2].getHeroCard();

        enemyHero.takeDamage(attackDamage);
        available = false;
    }

    /**
     * for freezing a card
     */
    public final void freeze() {

        frozen = true;
    }

    /**
     * for unfreezing a card
     */
    public final void unfreeze() {

        frozen = false;
    }

    /**
     * for update health after damage
     * @param damage
     */
    public final void takeDamage(final int damage) {

        health -= damage;
        if (health <= 0) {
            die();
        }
    }

    public final void die() {
        Board board = GwentStone.getInstance().getBoard();
        board.getRow(rowIdx).remove(this);
    }
    public final boolean isAvailable() {
        return available;
    }

    public final void setAvailable(final boolean available) {
        this.available = available;
    }

    public final int getAttackDamage() {
        return attackDamage;
    }

    public final void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public final void setRowIdx(final int rowIdx) {
        this.rowIdx = rowIdx;
    }

    public final int getRowIdx() {
        return rowIdx;
    }

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }

    public final boolean isFrozen() {
        return frozen;
    }

    public final void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }
}
