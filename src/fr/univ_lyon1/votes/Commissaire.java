package fr.univ_lyon1.votes;

import java.math.BigInteger;
import java.util.ArrayList;

import fr.univ_lyon1.crypto.cle.Cle;
import fr.univ_lyon1.crypto.cryptosysteme.Paillier;

/**
 * Dispose normalement de tuos les c1 des votants
 * DIspose normalement des empreintes generé a partir de c2
 * @author loki
 * Verifie le droit de vote de l'electeur normalement
 * Ici , il s'occupe du depouillementde deux cléfs
 * 
 */
public class Commissaire {

	private ArrayList<Cle> allPublicKey;
	private Paillier secure_vote;
	private Cle public_key;
	private Cle private_key;
	
	public Commissaire(){
		this.secure_vote = new Paillier();
		this.public_key = secure_vote.getPubliqueKey();
		this.private_key = secure_vote.getPrivateKey();
	}
	

	public void depouille(ArrayList<BigInteger> votesChiffres ){
		int[] resultat = new int[3];
		
		for (BigInteger vote: votesChiffres){
			switch(decryptDouble(vote)){
				case "0": resultat[0] = resultat[0] + 1; break;
				case "1": resultat[1] = resultat[1] + 1; break;
				case "2": resultat[2] = resultat[2] + 1; break;
				default: break;
			};
			
		}
		for (int i = 0; i<resultat.length;++i){
			System.out.print(resultat[i]+" ");
		}
	}
	
	public Cle getPublicKey(){
		return public_key;
	}
	
	public String decryptDouble(BigInteger message){
		return secure_vote.DecryptDouble(message);
	}
}
