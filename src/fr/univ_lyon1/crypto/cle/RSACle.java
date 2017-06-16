package fr.univ_lyon1.crypto.cle;

import java.math.BigInteger;

public class RSACle extends Cle {

	private BigInteger d;
	public RSACle(){}
	public RSACle(BigInteger p, BigInteger q, BigInteger d) {
		super(p, q);
		this.d = d;
		// TODO Auto-generated constructor stub
	}
	
	public void setCle(BigInteger p, BigInteger q, BigInteger d){
		this.i = p;
		this.j = q;
		this.d = d;
	}
	
	public BigInteger getD(){
		return d;
	}
	
	public BigInteger getN(){
		return j ;
	}

}
