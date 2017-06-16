package fr.univ_lyon1.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

import fr.univ_lyon1.crypto.cle.Cle;
import fr.univ_lyon1.crypto.cle.ElGamalCle;
import fr.univ_lyon1.crypto.cryptosysteme.ElGamal;
import fr.univ_lyon1.crypto.cryptosysteme.Paillier;
import fr.univ_lyon1.crypto.cryptosysteme.Rsa;
import fr.univ_lyon1.crypto.utils.Hashage;
import fr.univ_lyon1.votes.Commissaire;
import fr.univ_lyon1.votes.Electeur;
import fr.univ_lyon1.votes.Vote;


public class App {
	public static String mRSA 		= "Hello RSA, what's up ?";
	public static String mElGamal 	= "Hello ElGamal, what's up?";
	public static String mPailler 	= "Hello Paillier, what's up?";
	
	public static int 	 mPailler1	= 12;
	public static int	 mPailler2 	= 20;
	public static int 	 mPailler3 	= 30;
	
	public static BigInteger n 	= new BigInteger("10");
	
	
	private static final SecureRandom random = new SecureRandom();
	
	public static void main(String args[]){
//		testRSA();
//		testElGamal();
		testPailler();
//		
//		testVote();
//		
	}
	
	/*
	 * Alice envoi un message à bob qui le décrypte
	 */
	
	public static void testRSA(){
		System.out.println("Decryptage RSA");
		Rsa aliceRSA = new Rsa();
		Rsa bobRSA = new Rsa();
		
		String messageCrypte = aliceRSA.encrypt(mRSA, bobRSA.getPubliqueKey());
		String messageDecrypte = bobRSA.decrypt(messageCrypte);
		
		System.out.println("Message decrypté :");
		System.out.println(messageDecrypte);
		
		// On verifie si Alice envoie bien le message
		//  Probleme, ne marche que sur des petites chaines de caracteres 
		// Edit : J'ai supprimé ces fonctions
		// On signe uniquement les BigInteger du coup 
		
		BigInteger intCrypte = aliceRSA.encryptInteger(n, bobRSA.getPubliqueKey());
		BigInteger hashCrypte = Hashage.hashRSA(intCrypte, aliceRSA.getPubliqueKey());
		BigInteger signature = aliceRSA.signature(hashCrypte); //intCrypte
		boolean checkSignature = bobRSA.verifSignature(hashCrypte, signature, aliceRSA.getPubliqueKey()); //intCrypte
		if (checkSignature){
			System.out.println("Le message est signée, on est sur que c'est Alice qui a envoyé le message\n"
					+ "+Le message décrypté est :");
			BigInteger intDecrypte = bobRSA.decryptInteger(intCrypte);
			System.out.println(intDecrypte);
		}
		
	}
	public static void testElGamal(){
		System.out.println("Decryptage EL GAMAL");
		ElGamal aliceElGamal = new ElGamal();
		ElGamal bobElGamal = new ElGamal();
		BigInteger[] messageCrypte = new BigInteger[2];

		
		messageCrypte = aliceElGamal.encrypt(mElGamal, bobElGamal.getPubliqueKey());
		String messageDecrypte = bobElGamal.decrypt(messageCrypte);
		System.out.println("Message decrypté :");
		System.out.println(messageDecrypte);
		
		// On teste l'encryption des integer ainsi que la vérification de signature
		messageCrypte = aliceElGamal.encryptInteger(n, bobElGamal.getPubliqueKey());
		
		ArrayList<BigInteger> signature = aliceElGamal.signature(messageCrypte[1]);
		boolean check_signature = bobElGamal.verifSignature(messageCrypte[1], signature,  aliceElGamal.getPubliqueKey());
		if (check_signature){
			System.out.println("Le message est signée, on est sur que c'est Alice qui a envoyé le message\n"
					+ "Le message décrypté est :");
			BigInteger intDecrypte = bobElGamal.decryptInteger(messageCrypte);
			System.out.println(intDecrypte);
		}
	}
	
	public static void testPailler(){
		System.out.println("Décyptage Pailler : Algo de decryption banale d'un message");
		Paillier alicePailler = new Paillier();
		Paillier bobPailler = new Paillier();
		
		BigInteger messageCrypte = alicePailler.encrypt(mPailler, bobPailler.getPubliqueKey());
		String messageDecrypte = bobPailler.decrypt(messageCrypte);
		
		System.out.println("Résultat = "+messageDecrypte);
		System.out.println("--Avec deux chiffrés m");
		System.out.println("Pour cette partie, Alice envoit deux message M1, M2 qu'elle decide de chiffrer. "
				+ "\nPailler va nous ressortir la somme des M1 , M2. L'avantage de pailler ici? "
				+ "\nImpossible de retrouver les messages d'origines(Tu retrouvera pas 12 et 20, c'est impossible), du coup c'est utile pour un systeme de vote"
				+ "\n Exemple : A l'election presidentielle, tu veux juste avoir le résult finale, et tu ne dois pas savoir "
				+ "qui a voté pour qui (Ce serait con et pas démocratique \n"
				+ "Paillier est cryptage homomorphique (Check wiki, c'est des math blablabla)");
		// On envoit des chiffré sans chaine de caractere. Cela cause un problème pour la conversion Ascii
		BigInteger m1 = alicePailler.EncryptDouble(mPailler1, bobPailler.getPubliqueKey());
		BigInteger m2 = alicePailler.EncryptDouble(mPailler2, bobPailler.getPubliqueKey());
		BigInteger m3 = alicePailler.EncryptDouble(mPailler3, bobPailler.getPubliqueKey());
		
		BigInteger nn = bobPailler.getPubliqueKey().getFirstParam().multiply(bobPailler.getPubliqueKey().getFirstParam());
		String messagesDecrypt = bobPailler.DecryptDouble( (m1.multiply(m2).multiply(m3)).mod(nn));
		System.out.println("Le résultat est :"+messagesDecrypt
				+"\n (20+12), mais tu sais pas comment on l' a fait pour l'obtenir"
				+"\n Pour encrypter deux message en meme temps, tu dois les 'ajouter', ce qui revient a multiplier (Homomorphismee blablbalabl)");
		
	}
	/**
	 * Vote non fiable. Deux entités, electeur comissaire
	 * Aucune verification de l'electeur si il est bien inscrit sur liste
	 * Comissaire qui peut tricher et ajouter des voix, c'est lui qui dépouille
	 * Il peut donc creer n'importe quel voie vu que personne le controlle
	 * 
	 * Avec ca on peut retrouver qui a voté je crois, donc pas cool
	 */
	public static void testVote(){
		Electeur[] allElecteurs = new Electeur[10];
		Commissaire commisaireVote = new Commissaire();
		ArrayList<Cle> allPublique = new ArrayList<Cle>();
		ArrayList<BigInteger> votesChiffre = new ArrayList<BigInteger>();
		for (int i =0; i<allElecteurs.length;++i){
			System.out.println("L'électeur "+i+" a voté");
			allElecteurs[i] = new Electeur(randomEnum(Vote.class));
			allPublique.add(allElecteurs[i].getPublicKey());
			votesChiffre.add(allElecteurs[i].chiffreVote(commisaireVote.getPublicKey()));
		}
		System.out.println("Fin de la période de vote");
		System.out.println("P C B");
		commisaireVote.depouille(votesChiffre);
		
		//commisaireVote.DecryptDouble(allVote);
		
		
		
	}
	
	public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
	
}
