package fr.univ_lyon1.crypto.cryptosysteme;

import java.math.BigInteger;
import java.util.Random;

import fr.univ_lyon1.crypto.cle.Cle;
import fr.univ_lyon1.crypto.interfaces.Cryptosysteme;
import fr.univ_lyon1.crypto.utils.ArithmetiqueModulaire;
import fr.univ_lyon1.crypto.utils.Ascii;

public class Paillier implements Cryptosysteme<BigInteger>{
	private ArithmetiqueModulaire ar;
	private Cle publique;
	private Cle privee;
	/**
	 * cle publique = N 
	 * cle privee = Fi (p-1)(q-1)
	 * 
	 */
	public Paillier() {
		this.ar = new ArithmetiqueModulaire();
		this.publique = new Cle();
		this.privee = new Cle();
		keyGen();
		
	}
	public void keyGen() {
		Random rnd = new Random();
		BigInteger p = BigInteger.probablePrime(512, rnd);
		BigInteger q = BigInteger.probablePrime(512, rnd);
		BigInteger n = p.multiply(q);
		BigInteger fi = ar.getFi(p, q); // fi = (p-1)(q-1)

		publique.setCle(n, n); // Normalement c'est juste setCle (n) mais j'use Cle por les autres cryptosysteme
		privee.setCle(fi, fi);
		
	}

	public BigInteger encrypt(String message, Cle pk_dest) {
		// TODO Auto-generated method stub
		// Le message doit être inférieur à N
		// Permet d'encrypter des chaines de caracteres
		// Gavin lui encrypte juste des chiffres, moi j'ai pas le temps
		
		// Genere un R compris entre 1 et N. Il faut que r soit premier avec N
		BigInteger r = ar.getRandomBigInteger(pk_dest.getFirstParam());
		while (r.gcd(pk_dest.getFirstParam()).intValue() !=1){
			r = ar.getRandomBigInteger(pk_dest.getFirstParam());
		}
		// N²
		BigInteger nn = pk_dest.getFirstParam().multiply(pk_dest.getFirstParam());
		
		// Transforme la chaine de caractere en Ascii 
		BigInteger messageBig = Ascii.convertAsciiGamal(message);
		// G = N + 1
		BigInteger g = pk_dest.getFirstParam().add(BigInteger.ONE);
		
		// C = [(N+1)^m mod N² * r^n mod  N²]  mod N²
		// La formule Wiki doit etre différente et utilise des congruences si je me souviens bien
		// Or le java le fait pas, donc proprietes mathématiques utilisé sur les modulo
		BigInteger c = ((g.modPow(messageBig, nn)).multiply(r.modPow(pk_dest.getFirstParam(), nn))).mod(nn);
		
		return c;
	}
	
	public BigInteger EncryptDouble(int message, Cle pk_dest) {
		// TODO Auto-generated method stub
		// Le message doit être inférieur à N
		// EN gros c'est la meme fonction qu'en haut, sauf que je transforme pas en Ascii
		// ON a un int en parametre

		BigInteger r = ar.getRandomBigInteger(pk_dest.getFirstParam());
		while (r.gcd(pk_dest.getFirstParam()).intValue() !=1){
			r = ar.getRandomBigInteger(pk_dest.getFirstParam());
		}

		BigInteger nn = pk_dest.getFirstParam().multiply(pk_dest.getFirstParam());
		BigInteger messageBig = new BigInteger(String.valueOf(message));
		
		BigInteger g = pk_dest.getFirstParam().add(BigInteger.ONE);
		BigInteger c = ((g.modPow(messageBig, nn)).multiply(r.modPow(pk_dest.getFirstParam(), nn))).mod(nn);
		
		return c;
	}

	public String decrypt(BigInteger c_message) {
		// TODO Auto-generated method stub
		// Déclaration des différentes variables utiles (Regarde sur le TP)
		BigInteger n  = publique.getFirstParam();
		BigInteger nn = n.multiply(n);
		BigInteger fi = privee.getFirstParam();
		BigInteger mu = n.modInverse(fi);
		BigInteger r  = c_message.modPow(mu, n);
		BigInteger s  = r.modInverse(nn);
		BigInteger m  = (((c_message.multiply(s.modPow(n, nn))).mod(nn)).subtract(BigInteger.ONE)).divide(n);
		
	
		String decryptMessage = "";
		String messageDecryptinInt = m.toString();
		// Pourquoi split 128 ? Car ca permet de recuperer chacun des caracteres du message d'origine
		// ON construit ensuite le message decrypter, caractere par caractere
		String[] partsMessage = messageDecryptinInt.split("128");
		
		for (int i = 0; i< partsMessage.length;++i){

			decryptMessage = decryptMessage+Ascii.getChar(Integer.valueOf(partsMessage[i]));
		}

		return decryptMessage;
		
	}
	
	public String DecryptDouble(BigInteger c_message) {
		// TODO Auto-generated method stub
		// Meme methode qu'en haut, sauf pas de transformation Ascii
		BigInteger n  = publique.getFirstParam();
		BigInteger nn = n.multiply(n);
		BigInteger fi = privee.getFirstParam();
		BigInteger mu = n.modInverse(fi);
		BigInteger r  = c_message.modPow(mu, n);
		BigInteger s  = r.modInverse(nn);
		BigInteger m  = (((c_message.multiply(s.modPow(n, nn))).mod(nn)).subtract(BigInteger.ONE)).divide(n);
		
		String decryptMessage = "";
		String messageDecryptinInt = m.toString();
		
		String[] partsMessage = messageDecryptinInt.split("128");
		
		for (int i = 0; i< partsMessage.length;++i){
			decryptMessage = decryptMessage+partsMessage[i];
		}

		return decryptMessage;
		
	}
	

	public Cle getPubliqueKey(){
		return publique;
	}
	// Normalement on a pas accès à cette fonction
	// Utile pour le systeme de vote
	public Cle getPrivateKey(){
		return privee;
	}
}
