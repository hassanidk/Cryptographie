package fr.univ_lyon1.crypto.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ArithmetiqueModulaire {
	private final static BigInteger ZERO = BigInteger.ZERO;
	private final static BigInteger UN = BigInteger.ONE;
	private final static BigInteger DEUX = new BigInteger("2");
	
	
	public int countElementInversible(String domaine){
		int nbElem = 0;
		BigInteger bi1, bi2, bi3, bi4;
		bi4 = UN;
		for (int i =0; i<2000000;++i){
			bi1 = Utils.rndBigInt(new BigInteger("2000000"));
			bi2 = new BigInteger(domaine);
			bi3 = bi1.gcd(bi2);
			///System.out.println(bi3);
			if (bi3.equals(bi4))
				nbElem++;
		}
		return nbElem;
	}
	
	public boolean isPrime(int num) {
        if (num < 2) 
        	return false;
        if (num == 2) 
        	return true;
        if (num % 2 == 0) 
        	return false;
        for (int i = 3; i * i <= num; i += 2)
            if (num % i == 0) 
            	return false;
        return true;
	}
	
	public boolean returnPrime(BigInteger number) {
	    if (!number.isProbablePrime(5))
	        return false;

	    BigInteger two = DEUX;
	    if (!two.equals(number) && BigInteger.ZERO.equals(number.mod(two)))
	        return false;

	    for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) {
	        if (BigInteger.ZERO.equals(number.mod(i)))
	            return false;
	    }
	    return true;
	}
	/**
	 * GEnere la liste des des elements d'un groupe Z/nZ ainsi que leur ordre respectif
	 * @param n
	 */
	public BigInteger ordre(BigInteger n){
		HashMap<BigInteger, BigInteger> elem_ordre = new HashMap<BigInteger, BigInteger>();
		BigInteger pprime = n.subtract(UN).divide(DEUX);
		for (BigInteger i = n; i.compareTo(ZERO) >0; i = i.subtract(UN)){
			if (n.gcd(i).intValue() == 1){
				elem_ordre.put(i, ZERO);
			}
		}
		for (BigInteger key : elem_ordre.keySet()){
			System.out.print("Clé = "+key);
			System.out.println(" Value :"+elem_ordre.get(key));
		}
		
		for (BigInteger key : elem_ordre.keySet()){
			BigInteger exponent = UN;
			while (key.modPow(exponent, n).intValue() !=1){
				exponent = exponent.add(UN);
			}
			elem_ordre.put(key, exponent);
		}
		for (BigInteger key : elem_ordre.keySet()){
			System.out.print("Clé = "+key);
			System.out.println(" Value :"+elem_ordre.get(key));
		}
		
		BigInteger g = getRandomG(n);
		return g;

	}
	
	public BigInteger getRandomG(BigInteger p){
		Random rnd = new Random();
		BigInteger g = getRandomBigInteger(p);
		BigInteger pPrime = p.subtract(UN).divide(DEUX);
        while (!g.modPow(pPrime, p).equals(UN)) {
            if (g.modPow(pPrime.multiply(DEUX), p).equals(UN))
                g = g.modPow(DEUX, p);
            else
                g = getRandomBigInteger(p);
        }
        return g;
	}
	/**
	 * Return un nombre entre 0 et n-1
	 * @param pprime
	 * @return
	 */
	public BigInteger getRandomBigInteger(BigInteger n){
		Random rnd = new Random();
		return new BigInteger(n.bitLength()+100, rnd).mod(n);

	}
	/**
	 * Return (p-1)/2
	 * @param p
	 * @return
	 */
	public BigInteger getPrimeElgamal(BigInteger p){
		BigInteger pprime = p.subtract(UN).divide(DEUX);
		return pprime;
	}
	
	public BigInteger generatePrime(){
		Random rnd = new Random();
		BigInteger number = DEUX;
		while (!returnPrime(number)){
			number = new BigInteger(512, rnd );
		}
		return number;
	}
	
	public boolean nonPrimeFermat(int p){
		int a = 2;
		double r = Math.pow(a, p-1) % p;
		if (r!=1)
			return false;
		if (r==0)
			return false;
		return true;
	}
	
	public int testFermat(){
		boolean test1, test2;
		int count = 0;
		for (int i =2; i<10000;++i){
			test1 = isPrime(i);
			test2 = nonPrimeFermat(i);
			if (test2)
				
			if (test1 !=test2){
				count++;
			}
		}
		return count;
	}
	
	public BigInteger[] getPremiers512(){
		Random rnd = new Random();
		BigInteger bi1, bi2, bi3, bi4;
		bi2 = ZERO;
		bi3 = ZERO;
		bi4 = new BigInteger("9");
		while (bi4.intValue() != 1){
			bi1 = new BigInteger("1");
			bi2 = new BigInteger(512, rnd );
			bi3 = new BigInteger(512, rnd);
			bi4 = bi2.gcd(bi3);
		}
		BigInteger [] result = new BigInteger[2];
		result[0] = bi2;
		result[1] = bi3;
		return result;
	}
	
	public BigInteger getN(BigInteger p, BigInteger q){
		return p.multiply(q);
		
	}
	
	public BigInteger getFi(BigInteger p, BigInteger q){
		BigInteger un = UN;
		BigInteger factone = p.subtract(un);
		BigInteger facttwo = q.subtract(un);
		return factone.multiply(facttwo);
	}
	
	public BigInteger getPrime(BigInteger m){
		Random rnd = new Random();
		BigInteger un = DEUX;
		BigInteger c = DEUX;
		while (un.intValue() !=1){
			c = new BigInteger(5, rnd);
			un = m.gcd(c);
		}
		return c;
	}
	
	// A > B
	public  BigInteger bezout(BigInteger a, BigInteger b) {

		BigInteger r = a;
		BigInteger u = UN;
		BigInteger v = ZERO;
		BigInteger rr = b;
		BigInteger uu = ZERO;
		BigInteger vv = UN;
		
		BigInteger q, rs, us, vs;
		while (rr.intValue()!=0){
			q = r.divide(rr);
			rs = r; us = u; vs = v;
			r = rr; u = uu; v = vv;
			rr = rs.subtract(q.multiply(rr));
			uu = us.subtract(q.multiply(uu));
			vv = vs.subtract(q.multiply(vv));
		}
		
		BigInteger k = ZERO;
		int i =0;
		// Cas ou v > M
		if (v.compareTo(a) == 1){
			while (v.compareTo(a) == 1){
				v = v.subtract(a.multiply(k));
				k = k.add(UN);	
			}
		}
		// Cas ou v < 2
		if (v.compareTo(DEUX) == -1){
			while (v.compareTo(DEUX) == -1){
				v = v.subtract(a.multiply(k));
				k = k.add(new BigInteger("-1"));	
			}
		}
		
		v = v.subtract(a.multiply(k));
		
		return v;
		
	}
	
	public BigInteger primeC(BigInteger p, BigInteger q){
		BigInteger fi = getFi(p,q);
		BigInteger un = UN;
		BigInteger pgcd = new BigInteger("-1");
		Random rnd = new Random();
		BigInteger e = new BigInteger(512, rnd);
		while (pgcd.intValue()!=1){
			while (!returnPrime(e)){
				e = new BigInteger(512,rnd);
			}
			pgcd =e.gcd(un);
		}
		return e;	
	}
	
	public BigInteger inverseModulaire(BigInteger e, BigInteger fi){
		BigInteger d = e.modInverse(fi);
		return d;
		
	}
	public BigInteger pow(BigInteger base, BigInteger exponent) {
		  BigInteger result = UN;
		  while (exponent.signum() > 0) {
		    if (exponent.testBit(0)) result = result.multiply(base);
		    base = base.multiply(base);
		    exponent = exponent.shiftRight(1);
		  }
		  return result;
		}

	

}