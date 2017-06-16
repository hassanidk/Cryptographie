package fr.univ_lyon1.votes;

import java.math.BigInteger;

import fr.univ_lyon1.crypto.cle.Cle;
import fr.univ_lyon1.crypto.cryptosysteme.Paillier;
/**
 * Dispose du couple de clé (pk,sk) envoyé par poste (ou autre)
 * @author loki
 *
 */
public class Electeur {
	Cle publique;
	Cle privee;
	Paillier secure_vote;
	Vote vote;
	
	public Electeur(Vote vote){
		this.secure_vote = new Paillier();
		this.publique = secure_vote.getPubliqueKey();
		this.privee = secure_vote.getPrivateKey();
		this.vote = vote;
	}
	
	public Cle getPublicKey(){
		return publique;
	}
	
	public BigInteger chiffreVote(Cle pk_admin){
		
		BigInteger voteChiffre = secure_vote.EncryptDouble(vote.ordinal(), pk_admin);
		//System.out.println("VOte = "+vote+" votechiffre="+ voteChiffre);
		return voteChiffre;
	}
}
