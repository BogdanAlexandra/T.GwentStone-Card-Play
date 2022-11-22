package gwentstone;

import fileio.CardInput;
import fileio.DecksInput;
import gwentstone.cards.herocards.EmpressThorina;
import gwentstone.cards.herocards.GeneralKocioraw;
import gwentstone.cards.herocards.HeroCard;
import gwentstone.cards.herocards.KingMudface;
import gwentstone.cards.herocards.LordRoyce;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private int mana = 0;

    private HeroCard heroCard;
    private List<Deck> decks = new ArrayList<>();

    private Deck currentDeck;

    private Hand hand = new Hand();

    public final int getMana() {
        return mana;
    }

    private boolean finishedTurn = false;

    private int wonGamesCount = 0;

    public Player() { }

    /**
     * for setting the Hero for a player
     */

    public final void setHeroCard(final CardInput cardInput) {
        switch (cardInput.getName()) {
            case "Lord Royce":
                heroCard = new LordRoyce(cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName());
                break;
            case "Empress Thorina":
                heroCard = new EmpressThorina(cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName());
                break;
            case "King Mudface":
                heroCard = new KingMudface(cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName());
                break;
            case "General Kocioraw":
                heroCard = new GeneralKocioraw(cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName());
                break;
            default:
                throw new IllegalArgumentException("Invalid Card Input" + cardInput);
        }
    }

    /**
     * for setting the deck
     * @param decksInput
     */
    public final void setDecks(final DecksInput decksInput) {
        for (ArrayList<CardInput> cardInputArr : decksInput.getDecks()) {
            decks.add(new Deck(cardInputArr));
        }
    }



    public final void setCurrentDeck(final int deckIdx) {
        currentDeck = decks.get(deckIdx).deepCopy();
    }

    /**
     * for adding mana
     * @param manaAmount
     */
    public final void addMana(final int manaAmount) {
        mana += manaAmount;
    }

    /**
     * for reset the mana
     */
    public final  void resetMana() {
        mana = 0;
    }

    public final void subtractMana(final int manaAmount) {
        mana -= manaAmount;
    }

    /**
     * for FinisherTurn
     * @return
     */
    public final  boolean getFinishedTurn() {
        return finishedTurn;
    }
    public final HeroCard getHeroCard() {
        return heroCard;
    }

    public final Deck getCurrentDeck() {
        return currentDeck;
    }

    public final Hand getHand() {
        return hand;
    }

    public final void setHand(final Hand hand) {
        this.hand = hand;
    }

    public final void setFinishedTurn(final boolean finishedTurn) {
        this.finishedTurn = finishedTurn;
    }

    public final int getWonGamesCount() {
        return wonGamesCount;
    }

    public final void setWonGamesCount(final int wonGamesCount) {
        this.wonGamesCount = wonGamesCount;
    }
}
