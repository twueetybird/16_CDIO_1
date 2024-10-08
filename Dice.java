import java.util.Random;
import java.util.Scanner;

public class Dice {
	
	private class Player { // All the visibility in this class can be private including the constructor, as it resides within the scope of the Dice-class
		private int sum;
		private int dice1, dice2;
		private int lastDice1, lastDice2;
		private Random random;
		
		private Player(Random random) {
			this.sum = 0;
			this.random = random;
		}
		
		private int getSum() {
			return this.sum;
		}
		
		private void reset() {
			this.sum = 0;
		}
		
		private void roll() {
			this.lastDice1 = dice1;
			this.lastDice2 = dice2;
			this.dice1 = random.nextInt(1, 6);
			this.dice2 = random.nextInt(1, 6);
			sum += dice1 + dice2;
		}
		
		//method to check if roll is 2 identicals and not when both dice are 1
		private boolean identicalRoll() {
			return dice1 == dice2 && !(dice1 == 1 && dice2 == 1); 
		}
	}
	
	private Player player1;
	private Player player2;
	
	private boolean playingPlayer = false; // false = player1

	private Random random;
	
	private Dice() { // Private is fine here, considering the main method is within this class
		random = new Random(System.nanoTime()); // Create random number generator using the current time in nanoseconds as a seed
		player1 = new Player(random);
		player2 = new Player(random);
		
		Scanner scanner = new Scanner(System.in);
		scanner.useLocale(java.util.Locale.ENGLISH);
		
		System.out.println("Hello and welcome to Dice!");
		System.out.println("Type any string to continue/roll, type \"exit\" to exit game");
		
		String input;
		while (scanner.hasNext()) {
			input = scanner.nextLine(); // Getting an actual input is necessary as we cannot retrieve keystrokes directly from the OS without using third party libraries
			if (input.equalsIgnoreCase("exit")) {
				System.out.println("Goodbye :(");
				break; // Break loop and to close scanner and exit
			}
			Player current = playingPlayer ? player2 : player1;
			current.roll();

			//i changed it in order for both die values get displayed 
			System.out.println((playingPlayer ? "Player 2" : "Player 1") + " rolled: " + current.dice1 + " and " + current.dice2 + " | sum: " + current.getSum());
			
			//checks if player rolled 2 identical, if not other player gets turn, else, player gets another turn 
			if (!current.identicalRoll()) {
				playingPlayer = !playingPlayer; 
			} else {
				System.out.println((playingPlayer ? "Player 2" : "Player 1") + " rolled 2 identical and gets another turn");
			}
			
			
			if (current.sum >= 40) {
				System.out.println((playingPlayer ? "Player 2" : "Player 1") + " won");
				player1.reset();
				player2.reset();
				playingPlayer = false;
				break;
			}

            // Extra assignemnts goes here 
			ifBothOne();
			
			System.out.println((playingPlayer ? "Player 2" : "Player 1") + " | Type any string to roll");
		}
		
		scanner.close();
	}

	private void ifBothOne() {
        // . Spilleren mister alle sine point hvis spilleren slår to 1'ere

        	if (current.dice1 == 1 && current.dice2 == 1) {

            	current.reset();

        	}

    	}
	
	public static void main(String[] args) {
		new Dice();
	}

}
