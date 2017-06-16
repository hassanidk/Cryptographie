package fr.univ_lyon1.crypto.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Ascii {
	public static int[] convertAscii(String message){
		int [] tabAscii = new int[message.length()];
		for (int i = 0; i < message.length();i++){
			tabAscii[i] = message.charAt(i);
		}
		return tabAscii;
	}
	
	public static BigInteger convertAsciiGamal(String message){
		String messageGamal = "";
		int [] tabAscii = convertAscii(message);
		for (int i = 0; i<tabAscii.length;++i){
			messageGamal = messageGamal+tabAscii[i]+"128";
		}
		BigInteger valueGamal = new BigInteger(messageGamal);
		return valueGamal;
	}

	public static String getChar(int indice){
		char c = (char) indice;
		String character = String.valueOf(c);
		return character;
	}
}
