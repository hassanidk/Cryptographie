package fr.univ_lyon1.crypto.cryptosysteme;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import fr.univ_lyon1.crypto.cle.Cle;
import fr.univ_lyon1.crypto.cle.RSACle;
import fr.univ_lyon1.crypto.interfaces.Cryptosysteme;
import fr.univ_lyon1.crypto.utils.ArithmetiqueModulaire;
import fr.univ_lyon1.crypto.utils.Ascii;

public class Rsa implements Cryptosysteme<String> {
	private ArithmetiqueModulaire ar;
	private Cle publique;
	private RSACle privee;
	
	
	
	public Rsa() {
		this.ar = new ArithmetiqueModulaire();
		this.publique = new Cle();
		this.privee = new RSACle();
		keyGen();
		
	}
	
	public void keyGen() {

		Random rnd = new Random();
		BigInteger p = BigInteger.probablePrime(512, rnd);
		BigInteger q = BigInteger.probablePrime(512, rnd);
		while (p.equals(q)){
			q = BigInteger.probablePrime(3, rnd);
		}
		BigInteger n = p.multiply(q);
		BigInteger m = ar.getFi(p, q);
		BigInteger c = ar.getPrime(m);
		BigInteger d = c.modInverse(m);
		publique.setCle(n, c);
		BigInteger u;
		if (c.compareTo(m) == 1){
			 u = ar.bezout(c, m);
		}else{
			 u = ar.bezout(m, c);
		}
		privee.setCle(u, n, d);

		//System.out.println("p = "+p+" q = "+q+" n="+n+" m="+m+" c="+c+" u="+u+" d="+d);
	}
	
	

	public String encrypt(String message, Cle public_dest) {
		int[] encryptMessage = Ascii.convertAscii(message);
		String messageEncrypte = "";
		for (int i =0; i< message.length();++i){
			
			BigInteger character = new BigInteger(String.valueOf(encryptMessage[i]));
			character = character.modPow(public_dest.getSecondParam(), public_dest.getFirstParam());
			messageEncrypte = messageEncrypte+ character+" ";
		}
		
		return messageEncrypte;
		
	}
	
	public BigInteger encryptInteger(BigInteger message, Cle public_dest) {
		
		BigInteger messageEncrypte = message.modPow(public_dest.getSecondParam(), public_dest.getFirstParam());
		return messageEncrypte;
		
	}
	
	public BigInteger decryptInteger(BigInteger messageEncrypt){
		BigInteger messageDecrypte = messageEncrypt.modPow(privee.getFirstParam(), privee.getSecondParam());
		return messageDecrypte;
	}

	public String decrypt(String message) {
		String[] parts = message.split(" ");
		String messageDecrypte ="";
		for (int i =0; i<parts.length;++i){
			BigInteger character = new BigInteger(parts[i]);
			character = character.modPow(privee.getFirstParam(), privee.getSecondParam());
			messageDecrypte = messageDecrypte + Ascii.getChar(character.intValue());
		}
		
		return messageDecrypte;
	}
	
	public Cle getPubliqueKey(){
		return publique;
	}
	
	public BigInteger signature(BigInteger intMessage){
		BigInteger mSigne = intMessage.modPow(privee.getD(), privee.getN());
		return mSigne;
	}
	
	public boolean verifSignature(BigInteger intMessage, BigInteger mSigne, Cle pk_send){
		BigInteger verif = mSigne.modPow(pk_send.getSecondParam(), pk_send.getFirstParam());
		
		return verif.equals(intMessage);
	}
	

	
	
	
}
