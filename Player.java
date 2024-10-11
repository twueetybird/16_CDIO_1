import java.util.Random;

public class Player { // All the visibility in this class can be private including the constructor, as it resides within the scope of the Dice-class
	private int sum = 0;
	private int dice1, dice2;
	private int lastDice1, lastDice2;

	public void roll(Random random) {
		this.lastDice1 = this.dice1;
		this.lastDice2 = this.dice2;
		this.dice1 = random.nextInt(1, 6 + 1);
		this.dice2 = random.nextInt(1, 6 + 1);
		this.sum += this.dice1 + this.dice2;
	}

	// Method to check if roll is 2 identicals and not when both dice are 1
	public boolean identicalRoll() {
		return dice1 == dice2 && !(dice1 == 1 && dice2 == 1);
	}

	public int getSum() {
		return sum;
	}

	public void resetSum() {
		sum = 0;
	}

	public void addToSum(int value) {
		sum += value;
	}

	public int getDice1() {
		return dice1;
	}

	public int getDice2() {
		return dice2;
	}

	public int getLastDice1() {
		return lastDice1;
	}

	public int getLastDice2() {
		return lastDice2;
	}
}
