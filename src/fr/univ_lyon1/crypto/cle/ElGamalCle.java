package fr.univ_lyon1.crypto.cle;

import java.math.BigInteger;

public class ElGamalCle extends Cle {
	BigInteger h ;
	public ElGamalCle(){}
	public ElGamalCle(BigInteger p, BigInteger g, BigInteger h) {
		super(p, g);
		this.h = h;
	}
	

	
	public void setCle(BigInteger p, BigInteger g, BigInteger h){
		this.i = p;
		this.j = g;
		this.h = h;
	}
	
	public BigInteger getP(){
		return i;
	}
	
	public BigInteger getG(){
		return j;
	}
	
	public BigInteger getH(){
		return h;
	}
}	
