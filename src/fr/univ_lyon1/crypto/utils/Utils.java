
package fr.univ_lyon1.crypto.utils;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;



public class Utils {
	/*
	 * Chemins des fichiers d'exercices
	 */
	
	public static String _TP1_EXERCICE1_1 = "resources/ex1_chiffre";
	public static String _TP1_EXERCICE1_2 = "resources/ex1_chiffre2";
	public static String _TP1_EXERCICE2 = "resources/ex2_chiffre";
	public static String _TP1_EXERCICE3_1 = "resources/ex3_chiffre";
	public static String _TP1_EXERCICE3_2 = "resources/ex3_chiffre2";
	
	
	// TP 1
	public static char [] frequence = {
			'e',
			's',
			'a',
			'i',
			't',
			'n',
			'r',
			'u',
			'l',
			'o',
			'd',
			'c',
			'p',
			'm',
			'é',
			'v',
			'q',
			'f',
			'b',
			'g',
			'h',
			'j',
			'à',
			'x',
			'y',
			'è',
			'ê',
			'z',
			'w',
			'ç',
			'ù',
			'k',
			'î',
			'ï',
			'ë',};

	public static int nb_lettre = frequence.length;
	
	 @SuppressWarnings("serial")
	static final  Map<String, Integer> tab_bigramme = new HashMap<String, Integer>(){{
		put("es" , 305);
		put("te" , 163);
		put("ou" , 118);
		put("ec" , 100);
		put("eu" , 89);
		put("ep" , 82);
		put("le" , 246);
		put("se" , 155);
		put("ai" , 117);
		put("ti" , 98);
		put("ur" , 88);
		put("nd" , 80);
		put("en" , 242);
		put("et" , 143);
		put("em" , 113);
		put("ce" , 98);
		put("co" , 87);
		put("ns" , 79);
		put("de" , 215);
		put("el" , 441);
		put("it" , 112);
		put("ed" , 96);
		put("ar" , 86);
		put("pa" , 78);
		put("re" , 209);
		put("qu" , 134);
		put("me" , 104);
		put("ie" , 94);
		put("tr" , 86);
		put("us" , 76);
		put("nt" , 197);
		put("an" , 30);
		put("is" , 103);
		put("ra" , 92);
		put("ue" , 85);
		put("sa" , 75);
		put("on" , 164);
		put("ne" , 124);
		put("la" , 101);
		put("in" , 90);
		put("ta" , 85);
		put("ss" , 73);
		put("er" , 163);
		
	}};
	
	 @SuppressWarnings("serial")
		static final  Map<String, Integer> tab_alphabet = new HashMap<String, Integer>(){{
			put("a" , 0 );
			put("b" , 1 );
			put("c" , 2 );
			put("d" , 3 );
			put("e" , 4 );
			put("f" , 5 );
			put("g" , 6 );
			put("h" , 7 );
			put("i" , 8 );
			put("j" , 9 );
			put("k" , 10 );
			put("l" , 11 );
			put("m" , 12 );
			put("n" , 13 );
			put("o" , 14 );
			put("p" , 15 );
			put("q" , 16 );
			put("r" , 17 );
			put("s" , 18 );
			put("t" , 19 );
			put("u" , 20 );
			put("v" , 21 );
			put("w" , 22 );
			put("x" , 23 );
			put("y" , 24 );
			put("z" , 25 );
			
		}};
	
	public static int [] nb_premier_x26 = {
			1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25
	};


	
			
	public static int nb_apparition = 10000;
	/*
	 * Methode qui determine si le texte se rapproche d'un texte francais
	 * PLus la valeur de retour est elevé, plus le texte est français
	 */
	public static int getBigrammes(String text){
		int occurence;
		double freq;
		int point = 0; 
		for (Map.Entry<String,Integer> entree : tab_bigramme.entrySet()){
			occurence = StringUtils.countMatches(text, entree.getKey());
			
			freq = (double) occurence / text.length();
			if (freq >0){
				if (freq-0.2 < entree.getValue()/nb_apparition && freq+0.2 > entree.getValue()/nb_apparition){
					point++;		
				}
			}
		}
		return point;
	}
	// FIN TP1
	
	public static BigInteger rndBigInt(BigInteger max) {
	    Random rnd = new Random();
	    do {
	        BigInteger i = new BigInteger(max.bitLength(), rnd);
	        if (i.compareTo(max) <= 0)
	            return i;
	    } while (true);
	}
	
	public static int pgcd(int a, int b){
		BigInteger b1 = new BigInteger(""+a);
		BigInteger b2 = new BigInteger(""+b);
		BigInteger gcd = b1.gcd(b2);
	    return gcd.intValue();
	}
}
