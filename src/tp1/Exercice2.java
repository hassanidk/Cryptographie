package tp1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import fr.univ_lyon1.crypto.utils.Utils;

public class Exercice2 {
	
	public Exercice2(){
		
	}
	
	
	/**
	 * 
	 * @param chemin chemin du fichier
	 * Algo : 
	 * 	- Compter le nombre total T de lettre dans le fichier
	 *  - Créer les tableaux contenant de 1 lignes à T ligne pour caser les lettres
	 *  - Affecter à ces tableaux les lettres
	 *  - Construire les chaines de caractere colonne par colonne
	 *  - Bigramme, detection du mot FR
	 */
	public String dechifrage_exo_2(String chemin){
		String txt_dechiffre ="";
		int bigramme = 0;
		int bigramme_max = 0;
		int c ;
		try {
			BufferedReader reader_general = new BufferedReader(
				    new InputStreamReader(
				            new FileInputStream(chemin),
				            Charset.forName("UTF-8")));
			int nb_caractere = 0;
			while((c = reader_general.read()) != -1){
				nb_caractere++;
			}	
			reader_general.close();	
			
			for (int i = 1; i < nb_caractere; ++i){
				String txt="";
				BufferedReader reader = new BufferedReader(
					    new InputStreamReader(
					            new FileInputStream(chemin),
					            Charset.forName("UTF-8")));
				int j = (int)Math.round((double) nb_caractere/i);
				char tab[][] =  new char[i][j]; 
				for (int k =0; k <i ; ++k){
					for (int l = 0; l < j;++l){
						c = reader.read();
						if ( c==-1){
							tab[k][l] = '_';
						}else{
							tab[k][l] = (char) c;
						}
					}		
				}
				
				for (int l = 0; l< j;++l){
					for (int k =0; k < i;++k)
						txt = txt+tab[k][l];
				}
				
				bigramme = Utils.getBigrammes(txt);
				if (bigramme > bigramme_max){
					bigramme_max = bigramme;
					txt_dechiffre = txt;
				}
				reader.close();
			
			}
			return txt_dechiffre;
		}catch (IOException e){
			return "Une erreur est survenue lors du décryptage";
			
		}
	}
}
