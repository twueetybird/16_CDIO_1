package com.ioouteractive;

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

		// method to check if roll is 2 identicals and not when both dice are 1
		private boolean identicalRoll() {
			return dice1 == dice2 && !(dice1 == 1 && dice2 == 1);
		}
	}

	private Player player1;
	private Player player2;

	private boolean playingPlayer = false; // false = player1

	private Random random;

	private Dice() { // Private is fine here, considering the main method is within this class
		random = new Random(System.nanoTime()); // Create random number generator using the current time in nanoseconds
												// as a seed
		player1 = new Player(random);
		player2 = new Player(random);

		Scanner scanner = new Scanner(System.in);
		scanner.useLocale(java.util.Locale.ENGLISH);

		System.out.println("Hello and welcome to Dice!");
		System.out.println("Type any string to continue/roll, type \"exit\" to exit game");

		String input;
		while (scanner.hasNext()) {
			input = scanner.nextLine(); // Getting an actual input is necessary as we cannot retrieve keystrokes
										// directly from the OS without using third party libraries
			if (input.equalsIgnoreCase("exit")) {
				System.out.println("Goodbye :(");
				break; // Break loop and to close scanner and exit
			}
			Player current = playingPlayer ? player2 : player1;
			current.roll();

			System.out.println((playingPlayer ? "Player 2" : "Player 1") + " rolled: " + current.dice1 + " and "
					+ current.dice2 + " | sum: " + current.sum);

			// checks if player rolled 2 identical, if not other player gets turn, else,
			// player gets another turn
			if (!current.identicalRoll()) {
				playingPlayer = !playingPlayer;
			} else {
				System.out.println(
						(playingPlayer ? "Player 2" : "Player 1") + " rolled 2 identical and gets another turn");
			}

			if (current.sum >= 40) {
				if (twoEquals(current, playingPlayer)) {
					if (ifBothOne(current, playingPlayer)) {
						break;
					}
					System.out.println((playingPlayer ? "Player 2" : "Player 1") + " won");
					break;
				}
			} else {
				ifBothOne(current, playingPlayer);

				if (ifBothSixes(current, playingPlayer)) {
					break;
				}
			}

			System.out.println((playingPlayer ? "Player 2" : "Player 1") + " | Type any string to roll");
		}

		scanner.close();
	}

	private boolean twoEquals(Player current, boolean playingPlayer) {
		if (current.dice1 == current.dice2) {
			if (current.dice1 == 1) {
				return false;
			}
			return true;
		}

		return false;
	}

	private boolean ifBothOne(Player current, boolean playingPlayer) {
		// The player loses all their points if two 1's are rolled
		if (current.dice1 == 1 && current.dice2 == 1) {
			current.reset();
			System.out.println((playingPlayer ? "Player 2" : "Player 1") + " rolled two ones | sum: " + current.sum);
			return true;
		}
		return false;
	}

	private boolean ifBothSixes(Player current, boolean playingPlayer) {
		if ((current.lastDice1 == 6) && (current.lastDice2 == 6) && (current.dice1 == 6) && (current.dice2 == 6)) {
			System.out.println("Congratulations! " + (playingPlayer ? "Player 2" : "Player 1")
					+ " won the game by rolling four sixes in a row!");
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		new Dice();
	}

}
