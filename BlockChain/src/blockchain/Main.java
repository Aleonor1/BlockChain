package blockchain;

import java.security.MessageDigest;
import java.util.Date;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter how many zeros the hash must starts with: ");
		String numberOfZeros = keyboard.nextLine();
		System.out.println();
		BlockChain blockchain = new BlockChain(Integer.parseInt(numberOfZeros));
		blockchain.createBlock();
		blockchain.createBlock();
		blockchain.createBlock();
		blockchain.createBlock();
		blockchain.createBlock();

		blockchain.print();

	}

}
