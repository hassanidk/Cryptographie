package tp1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import fr.univ_lyon1.crypto.utils.Utils;

public class Exercice1 {
	
	public String  dechiffrage_exo_un(String chemin) throws IOException{

		int c;
		int i = 0;
		int decalage = 0;
		int bigramme = 0;
		int bigramme_max = 0;
		int mod;
		boolean getDecalage = false;
		char character;
		String text_decode = "";
		String text_dechiffre = "";
		
		// Méthode Brut de Force
		while (i <Utils.nb_lettre){
			text_decode ="";
			BufferedReader reader = new BufferedReader(
				    new InputStreamReader(
				            new FileInputStream(chemin),
				            Charset.forName("UTF-8")));
			while((c = reader.read()) != -1) {
				if (getDecalage == false){
					decalage =  Math.abs ( ((int) Utils.frequence[i] - c) );
					getDecalage = true;
				}
				mod = c+decalage;
				if (mod > 122){ //Permet de rester dans l'alphabet ;)
					mod = 97 + (mod - 123);
				}
				if ((char) c != '\n'){  
				  character = (char) (mod);
				}else{
				  character = '\n';
				}
				text_decode = text_decode+character;	
			}
			bigramme = Utils.getBigrammes(text_decode);
			if (bigramme > bigramme_max){
				
				bigramme_max = bigramme;
				text_dechiffre = text_decode;
			}
			reader.close();
			getDecalage = false;
			i++;
			
		}
		System.out.println("---Déchiffrage terminé---");
		
		return text_dechiffre;
		
	}
}
