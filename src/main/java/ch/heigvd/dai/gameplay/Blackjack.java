package ch.heigvd.dai.gameplay;

import java.util.Arrays;
import java.util.Vector;
import java.util.Collections;
import java.util.Random;

import ch.heigvd.dai.gameplay.Card;

public class Blackjack {
    private int nbFamilies = 4;

    public Blackjack(int nbPlayers){
        resetDeck();
    }

    private Vector<Card> deck = new Vector<>(52);

    private int tokens; //Argent du joueur
    private int points = 0;
    private Vector<String> DealerHand;
    private Vector<String> PlayerHand;

    public void draw(Vector<Card> hand){
        hand.add(deck.get(deck.size() - 1));
        deck.removeElementAt(deck.size() - 1);
    }

    public void resetDeck(){
        for (int i = 0; i < nbFamilies; ++i){
            for(int j = 0; j < Card.cardValue.values().length; ++j){
                deck.add(new Card(Card.cardValue.values()[j]));
            }
        }
        shuffleDeck();
    }

    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    public int getPoints(Vector<Card> hand)
    {
        int result = 0;
        int ace = 0;
        for (int i = 0; i < hand.size(); ++i){
            if (hand.get(i).isAce()) ++ace;
            else{ result += hand.get(i).pointValue(); }
        }
        //ajout de la valeur des as
        for (int i = 0; i < ace; ++i){
            if (result + 11 > 21) {
                result += 1;
            }
            else {
                result += 11;
            }
        }
        return result;
    }
}
