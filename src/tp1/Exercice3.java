package tp1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Exercice3 {
	/**
	 * 
	 * @param chemin du fichier
	 * a correspond à 0, b à 1, c à 2...
	 * Algo : 
	 * 	- Lecture du fichier caractere par caractere
	 *  - A chaque caractere, on fait le matching avec sa place dans l'alphabet
	 *  - Chaque caractere <=> x = A'*(y-B) où
	 *  	* y valeur du caractere
	 *  	* A' Inverse modulaire
	 *  	* x la valeur du caractere dechiffre
	 */
	public void dechifrage_exo_3(String chemin){
		try {
			BufferedReader reader = new BufferedReader(
				    new InputStreamReader(
				            new FileInputStream(chemin),
				            Charset.forName("UTF-8")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
