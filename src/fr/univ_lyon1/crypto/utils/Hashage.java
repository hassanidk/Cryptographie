package fr.univ_lyon1.crypto.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import fr.univ_lyon1.crypto.cle.Cle;

public class Hashage {
	public static BigInteger V256 = new BigInteger("256");
	public static BigInteger hashRSA(BigInteger intMessage, Cle public_key){
		
		try {
			String value_hash ="";
			int l = 0;
			int n = public_key.getFirstParam().bitLength();
			int n_add_100 = n + 100;
			
			
			BigInteger r = new BigInteger(256, new Random());
			BigInteger modN = public_key.getFirstParam();
			MessageDigest m = MessageDigest.getInstance("SHA-256");
			m.update(intMessage.toByteArray());
			byte[] digest = m.digest();
			for (int i =0; i<digest.length;++i){
				String valueByte = String.valueOf(digest[i]).replaceAll("[-]", "");
				while (l*256 < n_add_100){
					l++;
					
				}
				value_hash = value_hash+valueByte+r.toString()+String.valueOf(l);
			}
	
			BigInteger hashMessage = new BigInteger(value_hash);
			hashMessage = hashMessage.mod(modN);
			return hashMessage;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			
			return null;
		}
		
	}
	
	
}
