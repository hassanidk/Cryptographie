package fr.univ_lyon1.crypto.cryptosysteme;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import fr.univ_lyon1.crypto.cle.Cle;
import fr.univ_lyon1.crypto.cle.ElGamalCle;
import fr.univ_lyon1.crypto.interfaces.Cryptosysteme;
import fr.univ_lyon1.crypto.utils.ArithmetiqueModulaire;
import fr.univ_lyon1.crypto.utils.Ascii;

public class ElGamal implements Cryptosysteme<BigInteger[]>{
	private ArithmetiqueModulaire ar;
	private ElGamalCle publique;
	private Cle privee;

	
	public ElGamal(){
		this.ar = new ArithmetiqueModulaire();
		this.publique = new ElGamalCle();
		this.privee = new Cle();
		keyGen();
	}
	public void keyGen() {

		Random rnd = new Random();
		BigInteger p = BigInteger.probablePrime(15, rnd);
		BigInteger pprime = p.subtract(BigInteger.ONE).divide(new BigInteger("2"));
		BigInteger g = ar.getRandomG(p);
		BigInteger x = ar.getRandomBigInteger(pprime.subtract(BigInteger.ONE));
		BigInteger h = g.modPow(x, p);
		
		privee.setCle(p, x);
		publique.setCle(p,g,h);

		
	}

	public BigInteger[] encrypt(String message, Cle public_dest) {
		BigInteger [] coupleEncrypt = new BigInteger[2];
		ElGamalCle pk_dest = new ElGamalCle();
		if (public_dest instanceof ElGamalCle){
			 pk_dest = (ElGamalCle) public_dest;
		}else{
			return null;
		}
		BigInteger pprime = ar.getPrimeElgamal(pk_dest.getP());
		BigInteger r = ar.getRandomBigInteger(pprime);
		BigInteger gamalMessage = Ascii.convertAsciiGamal(message);
		coupleEncrypt[0] = pk_dest.getG().modPow(r, pk_dest.getP());
		coupleEncrypt[1] = gamalMessage.multiply(pk_dest.getH().modPow(r, pk_dest.getP()));
		return coupleEncrypt;
	}
	
	public BigInteger[] encryptInteger(BigInteger message, Cle public_dest){
		BigInteger [] coupleEncrypt = new BigInteger[2];
		ElGamalCle pk_dest = new ElGamalCle();
		if (public_dest instanceof ElGamalCle){
			 pk_dest = (ElGamalCle) public_dest;
		}else{
			return null;
		}
		BigInteger pprime = ar.getPrimeElgamal(pk_dest.getP());
		BigInteger r = ar.getRandomBigInteger(pprime);
		coupleEncrypt[0] = pk_dest.getG().modPow(r, pk_dest.getP());
		coupleEncrypt[1] = message.multiply(pk_dest.getH().modPow(r, pk_dest.getP()));
		return coupleEncrypt;
	}

	public String decrypt(BigInteger[]  coupleEncrypt) {
		BigInteger hr = coupleEncrypt[0].modPow(privee.getSecondParam(), privee.getFirstParam());
		BigInteger decryptMessageinInt = coupleEncrypt[1].divide(hr);
		// ON retrouve le message d'origine
		String messageDecryptinInt = decryptMessageinInt.toString();
		String[] partsMessage = messageDecryptinInt.split("128");
		String decryptMessage = "";
		for (int i = 0; i< partsMessage.length;++i){
			decryptMessage = decryptMessage+Ascii.getChar(Integer.valueOf(partsMessage[i]));
		}
		return decryptMessage;
	}
	
	public BigInteger decryptInteger(BigInteger[]  coupleEncrypt) {
		BigInteger hr = coupleEncrypt[0].modPow(privee.getSecondParam(), privee.getFirstParam());
		BigInteger decryptMessageinInt = coupleEncrypt[1].divide(hr);
		return decryptMessageinInt;
	}
	
	public ElGamalCle getPubliqueKey(){
		return publique;
	}
	
	public ArrayList<BigInteger> signature(BigInteger message) {
		ArrayList<BigInteger> listVariable =  new ArrayList<BigInteger>();
		boolean check_s = true;
		BigInteger p_subtract_un = publique.getP().subtract(BigInteger.ONE);
		while(check_s){
			BigInteger k = ar.getRandomBigInteger(publique.getP().subtract(BigInteger.ONE)   );
			while (k.gcd(publique.getP().subtract(BigInteger.ONE)).intValue()!=1){
				k = ar.getRandomBigInteger(publique.getP().subtract(BigInteger.ONE)   );
			}
		
			BigInteger r = publique.getG().modPow(k, publique.getP());
			BigInteger s = ((message.subtract(privee.getSecondParam().multiply(r))).multiply(k.modInverse(p_subtract_un))).mod(p_subtract_un);
			
	
			if (s.intValue()!=0){
				check_s = false;
				listVariable.add(r);
				listVariable.add(s);
			}
		}
		
		
		return listVariable;
		
	}
	
	public boolean verifSignature(BigInteger intMessage, ArrayList<BigInteger>r_s, ElGamalCle pk_sender) {
	
		BigInteger u = ((pk_sender.getH().modPow(r_s.get(0), pk_sender.getP())).multiply(r_s.get(0).modPow(r_s.get(1), pk_sender.getP()))).mod(pk_sender.getP());
		BigInteger v = pk_sender.getG().modPow(intMessage, pk_sender.getP());
		return (u.equals(v));
	}
	
	
	

}
