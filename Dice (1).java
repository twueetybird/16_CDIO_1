import java.util.Random;
import java.util.Scanner;

public class Dice {

	private class Player { // All the visibility in this class can be private including the constructor, as it resides within the scope of the Dice-class
		private int sum = 0;
		private int dice1, dice2;
		private int lastDice1, lastDice2;

		private void roll(Random random) {
			this.lastDice1 = this.dice1;
			this.lastDice2 = this.dice2;
			this.dice1 = random.nextInt(1, 6 + 1);
			this.dice2 = random.nextInt(1, 6 + 1);
			this.sum += this.dice1 + this.dice2;
		}

		// Method to check if roll is 2 identicals and not when both dice are 1
		private boolean identicalRoll() {
			return dice1 == dice2 && !(dice1 == 1 && dice2 == 1);
		}
	}

	private Player player1;
	private Player player2;

	private boolean playingPlayer = false; // false = player1

	private Dice() { // Private is fine here, considering the main method is within this class
		Random random = new Random(System.nanoTime()); // Create random number generator using the current time in nanoseconds as a seed
		player1 = new Player();
		player2 = new Player();

        runGame(random);
	}

    private void runGame(Random random) {
		Scanner scanner = new Scanner(System.in);
		scanner.useLocale(java.util.Locale.ENGLISH);

		System.out.println("Hello and welcome to Dice!");
		System.out.println("Type any string to continue/roll, type \"exit\" to exit game");

		String input;
		while (scanner.hasNext()) {
			input = scanner.nextLine(); // Getting an actual input is necessary as we cannot retrieve keystrokes directly from the OS without using third party libraries
			long start = System.nanoTime();
			if (input.equalsIgnoreCase("exit")) {
				System.out.println("Goodbye :(");
				break; // Break loop and to close scanner and exit
			}
			Player current = playingPlayer ? player2 : player1;
			
			// Player roll
			current.roll(random);

			System.out.println((playingPlayer ? "Player 2" : "Player 1") + " rolled: " + current.dice1 + " and " + current.dice2 + " | sum: " + current.sum);

			if (current.sum >= 40 && current.dice1 != current.dice2) {
				System.out.println((playingPlayer ? "Player 2" : "Player 1") + " has 40 points or more. If they roll two identical dice that are not ones they wil win!");
			}

			// Checks if player rolled 2 identical, if not other player gets a turn, otherwise, current player gets another turn
			if (!twoEquals(current, playingPlayer)) {
				playingPlayer = !playingPlayer; // Setup playingPlayer for player change next turn
			} else {
				if (current.sum < 40) {
					System.out.println((playingPlayer ? "Player 2" : "Player 1") + " rolled 2 identical and gets another turn");
				}
				if (current.dice1 == 6 && current.dice2 == 6) {
					System.out.println((playingPlayer ? "Player 2" : "Player 1") + " rolled two sixes! If they roll two sixes next turn again they will win!");
				}
			}

			if (current.sum >= 40 + current.dice1 + current.dice2) {
				if (twoEquals(current, playingPlayer)) {
					if (current.identicalRoll()) { // Are they identical but not ones
						System.out.println((playingPlayer ? "Player 2" : "Player 1") + " won");
						break; // Stop the game
					} else if (ifBothOne(current, playingPlayer)) {
						// The player loses all their points if two 1's are rolled
						current.sum = 0;
						System.out.println((playingPlayer ? "Player 2" : "Player 1") + " rolled two ones and lost all their points | sum: " + current.sum);
					}
				}
			} else {

				if (ifBothSixes(current, playingPlayer)) {
					break;
				}
			}
			long time = System.nanoTime() - start;
			System.out.println("Time for one round : " + time + " nanoseconds");
			System.out.println((playingPlayer ? "Player 2" : "Player 1") + " | Type any string to roll");
		}

		scanner.close();
    }

	private boolean twoEquals(Player current, boolean playingPlayer) {
		if (current.dice1 == current.dice2) {
			return true;
		}

		return false;
	}

	private boolean ifBothOne(Player current, boolean playingPlayer) {
		if (current.dice1 == 1 && current.dice2 == 1) {
			return true;
		}
		return false;
	}

	private boolean ifBothSixes(Player current, boolean playingPlayer) {
		if ((current.lastDice1 == 6) && (current.lastDice2 == 6) && (current.dice1 == 6) && (current.dice2 == 6)) {
			System.out.println("Congratulations! " + (playingPlayer ? "Player 2" : "Player 1") + " won the game by rolling four sixes in a row!");
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		new Dice();
	}

}
