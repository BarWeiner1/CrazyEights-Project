//Crazy Eights Game as Final Project For Object Oriented Programming
//Involves a Roll Playing game against the CPU
//The goal of this game is to get rid of all of your cards, before the computer.
//You will be prompted to enter the index of which card you would like to play, do not play indexes you dont have
//In order to play a card it must be the same suit as the top card, or the same number as the top card.
//You can play an Eight at any point, and change the suit that the next played card must.
//If you have no cards to play, you will automatically be dealt cards until you can play one.
//However if you are dealt three cards, it becomes the computers turn.
public class CrazyEightsRunner {

	public static void main(String[] args) {
		CrazyEightsGame game= new CrazyEightsGame();
		System.out.println("Welcome to the Crazy Eights game");
		System.out.println("The goal of this game is to get rid of all of your cards, before the computer. ");
		System.out.println("You will be prompted to enter the index of which card you would like to play, do not play indexes you dont have.");
		System.out.println("In order to play a card it must be the same suit as the top card, or the same number as the top card.");
		System.out.println("You can play an Eight at any point, and change the suit that the next played card must.");
		System.out.println("If you have no cards to play, you will automatically be dealt cards until you can play one.");
		System.out.println("However if you are dealt three cards, it becomes the computers turn.");
		System.out.println("Good luck and have Fun!");
		System.out.println("");
		game.playRound();
		System.out.println("Game Over");
	}

}
