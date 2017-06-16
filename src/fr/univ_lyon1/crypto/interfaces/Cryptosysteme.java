package fr.univ_lyon1.crypto.interfaces;

import java.math.BigInteger;

import fr.univ_lyon1.crypto.cle.Cle;

public interface  Cryptosysteme<T> {
	
	public void keyGen();
	public T encrypt(String message, Cle public_dest);
	public String decrypt(T message);
	
}
