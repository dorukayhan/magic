import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import java.security.SecureRandom;

public class TheWholeThing {
	static Scanner in;
	static BigInteger wow;

	public static void main(String[] args) {
		in = new Scanner(System.in);
		say("Welcome to the Totally Not Breakable Encryption Utility! (P.S ignore the project name, it's just a meme reference");
		say("First, pick a mode: Will you generate a keypair, encrypt a message, or decrypt a message?");
		String mode = in.nextLine();
		switch (mode.toLowerCase()) {
		case "generate a keypair":
		case "generate keypair":
		case "generate":
			say("Generating a private key and its companion public key...");
			generateKeypair();
			break;
		case "encrypt":
		case "encrypt message":
		case "encrypt a message":
			encrypt();
			break;
		case "decrypt":
		case "decrypt message":
		case "decrypt a message":
			decrypt();
			break;
		}
	}

	static void generateKeypair() {
		BigInteger firstBase = generatePrime(220);
		say("1/6 done");
		BigInteger secondBase = generatePrime(220).multiply(generatePrime(16));
		say("2/6 done");
		BigInteger modulus = firstBase.multiply(secondBase);
		say("3/6 done");
		BigInteger totient = magicallyCalculateTheTotientOfPTimesQ(firstBase, secondBase);
		say("4/6 done");
		BigInteger publicKey = idkWhatAmIDoingAnymore(BigInteger.valueOf(65537), totient);
		say("5/6 done");
		BigInteger privateKey = publicKey.modInverse(totient);
		say("6/6 done");
		say("Keys calculated!");
		say("Public modulus: " + Base64.getEncoder().encodeToString(modulus.toByteArray()));
		say("Public key: " + Base64.getEncoder().encodeToString(publicKey.toByteArray()));
		say("Private key: " + Base64.getEncoder().encodeToString(privateKey.toByteArray()));
	}

	static void encrypt() {
		say("Message to be encrypted");
		BigInteger message = new BigInteger(in.nextLine().getBytes());
		say("Public modulus");
		BigInteger modulus = new BigInteger(Base64.getDecoder().decode(in.nextLine()));
		say("Public key");
		BigInteger key = new BigInteger(Base64.getDecoder().decode(in.nextLine()));
		say("Generating ciphertext...");
		String ciphertext = Base64.getEncoder().encodeToString(message.modPow(key, modulus).toByteArray());
		say("Done!");
		say("Ciphertext: " + ciphertext);
	}

	static void decrypt() {
		say("Ciphertext");
		BigInteger cipherQuoteUnquoteText = new BigInteger(Base64.getDecoder().decode(in.nextLine()));
		say("Public modulus");
		BigInteger modulus = new BigInteger(Base64.getDecoder().decode(in.nextLine()));
		say("Private key");
		BigInteger key = new BigInteger(Base64.getDecoder().decode(in.nextLine()));
		say("Retrieving original message...");
		String message = new String(cipherQuoteUnquoteText.modPow(key, modulus).toByteArray());
		say("Done!");
		say("Original message: " + message);
	}

	static BigInteger generatePrime(int sizeInBytes) {
		BigInteger result = new BigInteger(sizeInBytes * 8, 256, new SecureRandom());
		if(sizeInBytes / (2*2*2*2) == 1) wow = result;
		return result;
	}

	static void say(String s) {
		System.out.println(s);
	}

	private static BigInteger magicallyCalculateTheTotientOfPTimesQ(BigInteger p, BigInteger q) {
		return p.subtract(BigInteger.ONE).multiply(q.divide(wow).subtract(BigInteger.ONE)).multiply(wow.subtract(BigInteger.ONE));
	}
	
	static BigInteger idkWhatAmIDoingAnymore(BigInteger arg0, BigInteger arg1){
		while(arg0.compareTo(arg1) == -1){
			if(arg1.gcd(arg0).equals(BigInteger.ONE))
				return arg0;
			arg0 = arg0.nextProbablePrime();
		}
		throw new Error("Bad luck");
	}
}
