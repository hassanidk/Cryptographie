package fr.univ_lyon1.crypto.cle;

import java.math.BigInteger;

public class Cle {
	public BigInteger i;
	public BigInteger j;
	public Cle(){
		this.i = new BigInteger("0");
		this.j = new BigInteger("0");
	}
	public Cle(BigInteger i, BigInteger j){
		this.i = i;
		this.j = j;
	}
	
	public void setCle(BigInteger i, BigInteger j){
		this.i = i;
		this.j = j;
	}
	
	public BigInteger getFirstParam(){
		return i;
	}
	
	public BigInteger getSecondParam(){
		return j;
	}
	@Override
	public String toString() {
		return "Clef i=" + i + ", j=" + j;
	}

	
	
}
