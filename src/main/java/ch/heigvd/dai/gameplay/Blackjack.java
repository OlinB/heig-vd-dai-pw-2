package ch.heigvd.dai.gameplay;

import java.util.Vector;
import java.util.Collections;

import ch.heigvd.dai.gameplay.Card;

public class Blackjack {
    private int nbFamilies = 4; //Multiple de 4, pour simuler 1..n jeux de cartes utilisés.

    public Blackjack(int nbPlayers){
        resetDeck();
        PlayersHand = new Vector<>(nbPlayers);
    } //TODO à voir si le nombre de joueur, on l'augmente ou pas.

    private Vector<Card> deck = new Vector<>(13*nbFamilies);

    private int tokens; //Argent du joueur  //TODO si on l'implémente ou non.
    private int bet; //Argents misé par le joueur   //TODO en lien avec les tokens.
    private int points = 0; //TODO Je ne l'utilise pas, du coup, à remove ? ou bien pas ?.
    private Vector<String> DealerHand;
    private Vector<Vector<String>> PlayersHand; //Si plusieurs mains sont utilisés par le système.

    //Méthode à appeler quand le joueur souhaite HIT et permet de tirer une carte.
    public void draw(Vector<Card> hand){
        hand.add(deck.get(deck.size() - 1));
        deck.removeElementAt(deck.size() - 1);
    }

    //Méthode créant/réinitialisant le deck.
    public void resetDeck(){
        for (int i = 0; i < nbFamilies; ++i){
            for(int j = 0; j < Card.cardValue.values().length; ++j){
                deck.add(new Card(Card.cardValue.values()[j]));
            }
        }
        shuffleDeck();
    }

    //Méthode mélangeant le deck utilisé.
    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    //Méthode récupérant les points pour la main fournie.
    public int getPoints(Vector<Card> hand)
    {
        int result = 0;
        for (int i = 0; i < hand.size(); ++i){
            if (hand.get(i).isAce()){
                hand.add(hand.get(i));
                hand.removeElementAt(i);
                --i;
            }
            else{ result += hand.get(i).pointValue(result); }
        }
        return result;
    }

    //Méthode à appeler une fois que le joueur a fini de jouer.
    public boolean gameResult(Vector<Card> dealerHand, Vector<Card> playerHand){
        if (getPoints(playerHand) > 21){
            System.out.println("You lose !");
            //tokens -= bet;
            return false;
        }
        else if(getPoints(playerHand) == 21 && playerHand.size() == 2){
            System.out.println("Blackhjack !");
            //tokens += (2*bet);
            return true;
        }
        else if(getPoints(dealerHand) > 21 && getPoints(playerHand) <= 21){
            System.out.println("You win !");
            //tokens += bet;
            return true;
        }
        else if(getPoints(playerHand) > getPoints(dealerHand)){
            System.out.println("You win !");
            //tokens += bet;
            return true;
        }
        else{
            System.out.println("You lose !");
            //tokens -= bet;
            return false;
        }
    }

    //Méthode de draw des cartes du dealers
    public void revealDealerHand(Vector<Card> dealerHand){
        while(getPoints(dealerHand) < 16){
            draw(dealerHand);
        }
    }
}
