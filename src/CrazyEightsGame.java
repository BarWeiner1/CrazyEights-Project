import java.util.ArrayList;
import java.util.List;

public class CrazyEightsGame {
	private Hand myHand= new Hand();
	private Hand computerHand= new Hand();
	private Deck gameDeck= new Deck();
	
	
	
	public void playRound() {
		gameDeck.shuffle();
		for(int i=0; i<8; i++) {
			myHand.addCard(gameDeck.dealCard());
		}
		for(int i=0; i<8; i++) {
			computerHand.addCard(gameDeck.dealCard());
		}
		
		Card topCard= gameDeck.dealCard();
		System.out.println("The starting card for this round is " + topCard);
		
		System.out.println("Your cards for this round are " + myHand.toString());
		String sortChoice=askToSort();
		if(sortChoice.equals("value")){
			myHand.sortByValue();
		}
		if(sortChoice.equals("suit")) {
			myHand.sortBySuit();
		}
		while(computerHand.getCardCount()!=0 && myHand.getCardCount()!=0) {
			System.out.println("Your cards are " + myHand.toString());
			
			
			
			int playerCardCounter=0;
			while(!compareValues(myHand, topCard) && !compareSuits(myHand, topCard)&& playerCardCounter<3) {
				System.out.println("You have no cards that can be played this round");
				System.out.println("You must now draw a card");
				Card forcedDraw= gameDeck.dealCard();
				System.out.println("You drew a "+forcedDraw.toString());
				myHand.addCard(forcedDraw);
				if(forcedDraw.getValue()==8) {
					break;
				}
				playerCardCounter++;
			}
			
			if(playerCardCounter>=1) {
				System.out.println("Your hand is now " + myHand.toString());
			}
			if(playerCardCounter<3) {
				
				if(sortChoice.equals("value")){
					myHand.sortByValue();
				}
				if(sortChoice.equals("suit")) {
					myHand.sortBySuit();
				}
				if(playerCardCounter>=1) {
					System.out.println("Your sorted hand is now " + myHand.toString());
				}
			boolean cardChosen= false;
			while(!cardChosen) {
				System.out.println("Would you like to play multiple cards, yes or no; anyting else will be treated as no");
				String choiceMultipleCard= TextIO.getlnString();
				choiceMultipleCard.toLowerCase();
				if(choiceMultipleCard.equals("yes")) {
					System.out.println("How many cards would you like to play");
					int numberCardsToPlay= TextIO.getlnInt();
					int [] multipleCardsPlayer= new int[numberCardsToPlay];
					for(int i=0; i<multipleCardsPlayer.length; i++) {
						System.out.println("What is the index of your card " + (i+1));
						int indexCard=TextIO.getlnInt();
						multipleCardsPlayer[i]=indexCard-1;
					}
					
					if (checkMultiple(multipleCardsPlayer, myHand, topCard)) {
						topCard = playMultiple(myHand, topCard, multipleCardsPlayer);
						cardChosen = true;
					} else {
						System.out.println("Your cards were not all playable.");
						cardChosen = false;
					}
				} else {
				System.out.println("Which index card would you like to play(for first card type 1, second card type 2....)");
				int choiceCard= TextIO.getlnInt();
				if(choiceCard<=myHand.getCardCount()&&(myHand.getCard(choiceCard-1).getValue())== 8) {
					topCard=chooseSuit(topCard, choiceCard-1);//prompt to change suit
					myHand.removeCard(choiceCard-1);
					break;
				
				}
				else if(choiceCard<=myHand.getCardCount() && compareCards(myHand.getCard(choiceCard-1), topCard)){
						topCard= myHand.getCard(choiceCard-1);
						myHand.removeCard(choiceCard-1);
						cardChosen=true;
					}
				else {
					System.out.println("Please enter a valid response");
					cardChosen=false;
					}
				}
			}
			
			System.out.println("The top card is now " + topCard);
			}
			if(playerCardCounter==3) {
				System.out.println("You drew three cards, so it is now the computers turn");
			}
			if(myHand.getCardCount()==0) {
				break;
			}
			System.out.println("It is now the computer's turn");
			Card computerChoice= AIPlayRound(computerHand,topCard);
			topCard= (computerChoice);
			computerHand.removeCard(computerChoice);
			System.out.println("The top card is now " + topCard);
			
		}
		if(myHand.getCardCount()==0) {
			System.out.println("Congratulations you beat the Computer in Crazy Eights!");
		}
		else {
			System.out.println(" The Computer got rid of all of its cards first, you lost at Crazy Eights");
		}
		
	}
	public boolean checkMultiple(int[] indexes, Hand myHand, Card topCard) {
		boolean firstCard=false;
		for(int i=0; i<indexes.length; i++) {
			if(indexes[i]> myHand.getCardCount()) {
				return false;
			}
		}
		for(int i=0; i<indexes.length; i++) {
			if((indexes[i]< myHand.getCardCount()&&((myHand.getCard(indexes[i]).getValue()==topCard.getValue()) || myHand.getCard(indexes[i]).getSuit()==topCard.getSuit()))) {
				int firstValue = indexes[0];
				indexes[0] =indexes[i];
				indexes[i]=firstValue;
				firstCard=true;
				break;
			}
		}	
		
		if(!firstCard) {
			return false;
		}
		
		for(int i=1; i<indexes.length;i++) {
			if(myHand.getCard(indexes[i]).getValue() != myHand.getCard(indexes[0]).getValue()) {
				return false;
			}
		}
		return true;
	}
	
	public Card playMultiple(Hand myMand, Card topCard, int[] indexes) {
		for(int i=0; i<indexes.length; i++) {
			if(myHand.getCard(indexes[i]).getValue() == topCard.getValue() || myHand.getCard(indexes[i]).getSuit() == topCard.getSuit()) {
				int matchCard = indexes[0];
				indexes[0] = indexes[i];
				indexes[i]= matchCard;
			}
		}
		
		Card [] toBeRemoved = new Card[indexes.length];
		for(int i=0; i<indexes.length; i++) {
			toBeRemoved[i] = myHand.getCard(indexes[i]);
		}
		
		int counter = 0;
		Card finalCard = new Card();
		while (counter < toBeRemoved.length) {
			myHand.removeCard(toBeRemoved[counter]);
			if(!(toBeRemoved[counter].equals(topCard)))
				finalCard = toBeRemoved[counter];
			counter++;
		}
		
		return finalCard;
	}
	
	public Card chooseSuit(Card topCard, int choiceCard) {
		System.out.println("Which suit would you like to set the top card to; spades, hearts, diamonds, or clubs");
		boolean changedSuit= false;
		while(changedSuit==false) {
			String suitChoice= TextIO.getlnString();
			suitChoice=suitChoice.toLowerCase();
			if(suitChoice.equals("spades")) {
				topCard= new Card(8, 0);
				changedSuit=true;
			}
			else if(suitChoice.equals("hearts")) {
				topCard= new Card(8, 1);
				changedSuit=true;
			}
			else if(suitChoice.equals("diamonds")) {
				topCard= new Card(8, 2);
				changedSuit=true;
			}
			else if(suitChoice.equals("clubs")) {
				topCard= new Card(8, 3);
				changedSuit=true;
			}
			else {
				System.out.println("Please enter a valid response");
			}
		
		}
		return topCard;
	}
	
	public String askToSort() {
		String sortChoice="";
		boolean chosenSortMethod= false;
		while(chosenSortMethod== false) {
			System.out.println("Would you like to sort your cards by value or suit");
			sortChoice= TextIO.getlnString();
			sortChoice= sortChoice.toLowerCase();
			if(sortChoice.equals("value")) {
				chosenSortMethod=true;
			}
			else if(sortChoice.equals("suit")) {
				chosenSortMethod=true;
			}
			else {
				System.out.println("Please enter a valid response");
			}
		}
			return sortChoice;
	}
	public ArrayList<Integer> indexForMultipleValues(Hand hand, Card topCard) {
		int matchingCardVal=0;
		int counter = 0;
		for(int i=0;i<hand.getCardCount();i++ ) {
			if(hand.getCard(i).getValue()==topCard.getValue()) {
				matchingCardVal = hand.getCard(i).getValue();
				counter++;
			}
		}
		
		if(counter>1) {
			ArrayList<Integer> valueIndexes= new ArrayList<Integer>();
			for(int j=0;j<hand.getCardCount(); j++ ) {
				if(hand.getCard(j).getValue() == matchingCardVal) {
						valueIndexes.add(j);
				}		 
			}
			return valueIndexes;
		}
		ArrayList<Integer> zero= new ArrayList<Integer>();
		zero.add(0);
		return zero;
	}
	public ArrayList<Integer> indexForMultipleSuits(Hand hand, Card topCard){
		int matchingCardSuit=0;
		for(int i=0;i<hand.getCardCount(); i++ ) {
			if(hand.getCard(i).getSuit()==topCard.getSuit()) {
				matchingCardSuit=hand.getCard(i).getValue();
				int counter=0;
				for(int j=0; j<hand.getCardCount(); j++) {
					if(!(hand.getCard(i).equals(hand.getCard(j))) && hand.getCard(j).getValue()== matchingCardSuit) {
						counter++;
					}
				}
				
				if(counter>0) {
					ArrayList<Integer> suitIndexes= new ArrayList<Integer>();
					for(int j=0;j<hand.getCardCount(); j++ ) {
						 if(matchingCardSuit==hand.getCard(j).getValue()) {
							 suitIndexes.add(j);
						 }
					 }
					 return suitIndexes;
				}
				counter=0;
				matchingCardSuit=0;
			}
		}
		ArrayList<Integer> zero= new ArrayList<Integer>();
		zero.add(0);
		return zero;
		
	}
	
	
	 public boolean compareCards(Card myCard, Card topCard) {
		 if(myCard.getSuit()== topCard.getSuit()) {
			 return true;
		 }
		 else if(myCard.getValue()==topCard.getValue()) {
			 return true;
		 }
		 else {
			 return false;
		 }
	 }

	public boolean compareValues(Hand myHand, Card dealtCard) {
		boolean playableCard=false;
		for(int i=0; i<myHand.getCardCount(); i++) {
			if(myHand.getCard(i).getValue()==dealtCard.getValue()||myHand.getCard(i).getValue()==8) {
				playableCard=true;
			}
		}
		return playableCard;
	}
	public boolean compareSuits(Hand myHand, Card dealtCard) {
		boolean playableCard=false;
		for(int i=0; i<myHand.getCardCount(); i++) {
			if(myHand.getCard(i).getSuit()==dealtCard.getSuit()) {
				playableCard=true;
			}
		}
		return playableCard;
	}
	public int mostCardsOfSuit(Hand computerHand, Card topCard) {
		int [] countSuits= new int[4];
		for(int i=0; i<computerHand.getCardCount(); i++) {
			if(computerHand.getCard(i).getSuit()==0) {
				countSuits[0]++;
			}
			if(computerHand.getCard(i).getSuit()==1) {
				countSuits[1]++;
			}
			if(computerHand.getCard(i).getSuit()==2) {
				countSuits[2]++;
			}
			if(computerHand.getCard(i).getSuit()==3) {
				countSuits[3]++;
			}
			
		}
		int maxSuit=0;
		for(int i=1; i<countSuits.length; i++) {
			if(countSuits[i] >= countSuits[i-1]) {
				maxSuit=i;
			}
		}
		return maxSuit;
		
	}
	public Card AIPlayRound(Hand computerHand, Card topCard ) {
		int counter=0;
		while(!compareValues(computerHand, topCard) && !compareSuits(computerHand, topCard) && counter<3) {
			Card forcedDraw= gameDeck.dealCard();
			computerHand.addCard(forcedDraw);
			System.out.println("The computer drew a " + forcedDraw.toString());
			if(forcedDraw.getValue()==8) {
				break;
			}
			counter++;
		}
		if(counter==3) {
			System.out.println("The computer drew three cards, it is now your turn");
		}
		ArrayList<Integer> indexes;
		ArrayList<Integer> suitsIndexes= indexForMultipleSuits(computerHand, topCard);
		ArrayList<Integer> valuesIndexes= indexForMultipleValues(computerHand, topCard);
		if(suitsIndexes.size() > valuesIndexes.size()) {
			 indexes = suitsIndexes;
		}
		else {
			indexes=valuesIndexes;
		}
		
		if(indexes.size()>1) {
			System.out.println("Computer plays: ");
			Card[] removedCards = new Card[indexes.size()];
			for (int i=0; i<computerHand.getCardCount(); i++) {
				for (int j=0; j<indexes.size(); j++) {
					if(computerHand.getCard(i).equals(computerHand.getCard(indexes.get(j)))){
						removedCards[j] = computerHand.getCard(i);
						
					}
				}
			}
			for(int i=0; i<removedCards.length; i++) {
				if(removedCards[i].getValue()==topCard.getValue() || removedCards[i].getSuit()==topCard.getSuit()) {
					System.out.println(removedCards[i]);
					computerHand.removeCard(removedCards[i]);
					break;
				}
			}
			
			Card returnedCard = null;
			for(int i=0; i<removedCards.length; i++) {
				for(int k=0; k<computerHand.getCardCount();k++) {
					if(removedCards[i].equals(computerHand.getCard(k))) {
						System.out.println(removedCards[i]);
						returnedCard = removedCards[i];
						computerHand.removeCard(removedCards[i]);
					}
				}
			}
			
			return returnedCard;
		
		}
		else {
				for(int i=0; i<computerHand.getCardCount(); i++) {
					if(computerHand.getCard(i).getSuit() == topCard.getSuit()) {
						if(computerHand.getCard(i).getValue()==8) {
							Card computerChangedSuitCard= new Card(8,mostCardsOfSuit(computerHand,topCard));
							System.out.println("The computer played " + computerHand.getCard(i).toString() + " as " + computerChangedSuitCard.toString());
							computerHand.removeCard(computerHand.getCard(i));
							return computerChangedSuitCard;
						}
						return computerHand.getCard(i);
					}
				}
				
			for(int i=0; i<computerHand.getCardCount(); i++) {
				if(computerHand.getCard(i).getValue()== topCard.getValue()) {
					if(computerHand.getCard(i).getValue()==8) {
						Card computerChangedSuitCard= new Card(8,mostCardsOfSuit(computerHand,topCard));
						System.out.println("The computer played " + computerHand.getCard(i).toString() + " as " + computerChangedSuitCard.toString());
						computerHand.removeCard(computerHand.getCard(i));
						return computerChangedSuitCard;
					}
					return computerHand.getCard(i);
				}
		}
			for(int i=0; i<computerHand.getCardCount(); i++) {
					if(computerHand.getCard(i).getValue()==8) {
						Card computerChangedSuitCard= new Card(8,mostCardsOfSuit(computerHand,topCard));
						System.out.println("The computer played " + computerHand.getCard(i).toString() + " as " + computerChangedSuitCard.toString());
						computerHand.removeCard(computerHand.getCard(i));
						return computerChangedSuitCard;
			
					}
				}
		}
			
		return topCard;
	}
}
