import java.util.Random;
import java.util.Scanner;

public class Dice {

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
			if (input.equalsIgnoreCase("exit")) {
				System.out.println("Goodbye :(");
				break; // Break loop and to close scanner and exit
			}
			Player current = playingPlayer ? player2 : player1;
			
			// Player roll
			current.roll(random);

			System.out.println((playingPlayer ? "Player 2" : "Player 1") + " rolled: " + current.getDice1() + " and " + current.getDice2() + " | sum: " + current.getSum());

			if (current.getSum() >= 40 && current.getDice1() != current.getDice2()) {
				System.out.println((playingPlayer ? "Player 2" : "Player 1") + " has 40 points or more. If they roll two identical dice that are not ones they wil win!");
			}

			// Checks if player rolled 2 identical, if not other player gets a turn, otherwise, current player gets another turn
			if (!twoEquals(current, playingPlayer)) {
				playingPlayer = !playingPlayer; // Setup playingPlayer for player change next turn
			} else {
				if (current.getSum() < 40) {
					System.out.println((playingPlayer ? "Player 2" : "Player 1") + " rolled 2 identical and gets another turn");
				}
				if (current.getDice1() == 6 && current.getDice2() == 6) {
					System.out.println((playingPlayer ? "Player 2" : "Player 1") + " rolled two sixes! If they roll two sixes next turn again they will win!");
				}
			}

			if (current.getSum() >= 40 + current.getDice1() + current.getDice2()) {
				if (twoEquals(current, playingPlayer)) {
					if (current.identicalRoll()) { // Are they identical but not ones
						System.out.println((playingPlayer ? "Player 2" : "Player 1") + " won");
						break; // Stop the game
					} else if (ifBothOne(current, playingPlayer)) {
						// The player loses all their points if two 1's are rolled
						current.resetSum();
						System.out.println((playingPlayer ? "Player 2" : "Player 1") + " rolled two ones and lost all their points | sum: " + current.getSum());
					}
				}
			} else {

				if (ifBothSixes(current, playingPlayer)) {
					break;
				}
			}
			System.out.println((playingPlayer ? "Player 2" : "Player 1") + " | Type any string to roll");
		}

		scanner.close();
    }

	private boolean twoEquals(Player current, boolean playingPlayer) {
		if (current.getDice1() == current.getDice2()) {
			return true;
		}

		return false;
	}

	private boolean ifBothOne(Player current, boolean playingPlayer) {
		if (current.getDice1() == 1 && current.getDice2() == 1) {
			return true;
		}
		return false;
	}

	private boolean ifBothSixes(Player current, boolean playingPlayer) {
		if ((current.getLastDice1() == 6) && (current.getDice2() == 6) && (current.getDice1() == 6) && (current.getDice2() == 6)) {
			System.out.println("Congratulations! " + (playingPlayer ? "Player 2" : "Player 1") + " won the game by rolling four sixes in a row!");
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		new Dice();
	}

}
