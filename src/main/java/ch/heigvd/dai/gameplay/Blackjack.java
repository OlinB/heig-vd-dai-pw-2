package ch.heigvd.dai.gameplay;

import java.util.Vector;
import java.util.Collections;

import ch.heigvd.dai.gameplay.Card;

public class Blackjack {
    private int nbFamilies = 4; //Multiple de 4, pour simuler 1..n jeux de cartes utilisés.

    private Vector<Card> deck = new Vector<>(13*nbFamilies);

    private int tokens; //Argent du joueur  //TODO si on l'implémente ou non.
    private int bet; //Argents misé par le joueur   //TODO en lien avec les tokens.
    private int points = 0; //TODO Je ne l'utilise pas, du coup, à remove ? ou bien pas ?.
    private Vector<Card> dealerHand;
    private Vector<Vector<Card>> playersHand; //Si plusieurs mains sont utilisés par le système.

    public Blackjack(int nbPlayers){
        resetDeck();
        dealerHand = new Vector<>();
        playersHand = new Vector<>(nbPlayers);
        for (int i = 0; i < nbPlayers; ++i){
            playersHand.add(new Vector<>());
        }
    } //TODO à voir si le nombre de joueur, on l'augmente ou pas.

    //Méthode à appeler pour commencer un round.
    public void startRound(){
        resetDeck();
        dealerHand.removeAllElements();
        draw(dealerHand);
        draw(dealerHand);
        for (int i = 0; i < playersHand.size(); i++) {
            Vector<Card> hand = new Vector<>(); // T is the type of elements in the vectors
            draw(hand);
            draw(hand);
            playersHand.set(i, hand);
        }
    }

    //Méthode à appeler pour afficher la main du dealer.
    public String getDealerHand(boolean reveal){
        String result = "";
        for (int i = 0; i < dealerHand.size(); ++i){
            if(i == 0 && !reveal){ // first card is hidden, until the end of the round.
                result += "X,";
                continue;
            }

            result += dealerHand.get(i).toString();
            if(i != dealerHand.size() - 1){
                result += ",";
            }
        }
        return result;
    }

    //Méthode à appeler pour afficher la main d'un joueur.
    public String getPlayerHand(int player){
        String result = "";
        Vector<Card> hand = playersHand.get(player);
        for (int i = 0; i < hand.size(); ++i){
            result += hand.get(i).toString();
            if(i != hand.size() - 1){
                result += ",";
            }
        }
        return result;
    }

    //Méthode à appeler quand le joueur souhaite HIT et permet de tirer une carte.
    public void draw(Vector<Card> hand){
        hand.add(deck.get(deck.size() - 1));
        deck.removeElementAt(deck.size() - 1);
    }

    //Méthode à appeler quand le joueur souhaite HIT et permet de tirer une carte.
    public void hit(int player){
        draw(playersHand.get(player));
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

    //Méthode récupérant les points pour la main du joueur fourni.
    public int getPlayerPoints(int player){
        return getPoints(playersHand.get(player));
    }

    //Méthode récupérant les points pour la main du dealer.
    public int getDealerPoints(){
        return getPoints(dealerHand);
    }

    //Méthode à appeler une fois que le joueur a fini de jouer.
    public String gameResult(int player){
        Vector<Card> playerHand = playersHand.get(player);
        if (getPoints(playerHand) > 21){
            return "You lose !";
        }
        else if(getPoints(playerHand) == 21 && playerHand.size() == 2){
            return "Blackjack !";
        }
        else if(getPoints(dealerHand) > 21 && getPoints(playerHand) <= 21){
            return "You win !";
        }
        else if(getPoints(playerHand) > getPoints(dealerHand)){
            return "You win !";
        }
        else{
            return "You lose !";
        }
    }

    //Méthode de draw des cartes du dealers
    public void revealDealerHand(){
        while(getPoints(dealerHand) < 16){
            draw(dealerHand);
        }
    }
}
