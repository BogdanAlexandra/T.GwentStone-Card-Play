package gwentstone;

import fileio.CardInput;
import gwentstone.cards.Card;
import gwentstone.cards.environmentcards.Firestorm;
import gwentstone.cards.environmentcards.HeartHound;
import gwentstone.cards.environmentcards.Winterfell;
import gwentstone.cards.minioncards.Sentinel;
import gwentstone.cards.minioncards.Berserker;
import gwentstone.cards.minioncards.TheCursedOne;
import gwentstone.cards.minioncards.TheRipper;
import gwentstone.cards.minioncards.Miraj;
import gwentstone.cards.minioncards.Goliath;
import gwentstone.cards.minioncards.Disciple;
import gwentstone.cards.minioncards.Warden;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private List<Card> cards = new ArrayList<>();

    public Deck() { }

    public Deck(final ArrayList<CardInput> cardInputArr) {
        for (CardInput cardInput : cardInputArr) {
            switch (cardInput.getName()) {
                case "Sentinel" -> cards.add(new Sentinel(
                        cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName(),
                        cardInput.getHealth(), cardInput.getAttackDamage()));
                case "Berserker" -> cards.add(new Berserker(
                        cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName(),
                        cardInput.getHealth(), cardInput.getAttackDamage()));
                case "Goliath" -> cards.add(
                        new Goliath(cardInput.getMana(),
                        cardInput.getDescription(), cardInput.getColors(),
                        cardInput.getName(), cardInput.getHealth(),
                        cardInput.getAttackDamage()));
                case "Warden" -> cards.add(new Warden(
                        cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName(), cardInput.getHealth(),
                        cardInput.getAttackDamage()));
                case "The Ripper" -> cards.add(new TheRipper(
                        cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName(), cardInput.getHealth(),
                        cardInput.getAttackDamage()));
                case "Miraj" -> cards.add(new Miraj(
                        cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName(), cardInput.getHealth(),
                        cardInput.getAttackDamage()));
                case "The Cursed One" -> cards.add(new TheCursedOne(
                        cardInput.getMana(),
                        cardInput.getDescription(), cardInput.getColors(), cardInput.getName(),
                        cardInput.getHealth(), cardInput.getAttackDamage()));
                case "Disciple" -> cards.add(new Disciple(
                        cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName(), cardInput.getHealth(),
                        cardInput.getAttackDamage()));
                case "Firestorm" -> cards.add(new Firestorm(
                        cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName()));
                case "Winterfell" -> cards.add(new Winterfell(
                        cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName()));
                case "Heart Hound" -> cards.add(new HeartHound(
                        cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName()));
                default -> throw new IllegalArgumentException("Invalid Card Input: " + cardInput);
            }
        }
    }

    public final Deck deepCopy() {
        Deck newDeck = new Deck();

        for (Card card : this.cards) {
            newDeck.cards.add(card.deepCopy());
        }

        return newDeck;
    }

    public final void shuffle() {
        Collections.shuffle(cards, new Random(GwentStone.getInstance().getShuffleSeed()));
    }

    public final void dealCard(final Hand hand) {
        if (cards.isEmpty()) {
            return;
        }

        hand.addCard(cards.get(0));
        cards.remove(0);
    }
    public final List<Card> getCards() {
        return cards;
    }

    public final void setCards(final List<Card> cards) {
        this.cards = cards;
    }
}
