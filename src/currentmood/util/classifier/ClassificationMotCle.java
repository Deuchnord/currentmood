package currentmood.util.classifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import currentmood.util.Tweet;

/**
 *Ce classifier n'est pas statique En effet, il aura la liste des mots clés en attribut, il aura donc un constructeur
 *
 */
public class ClassificationMotCle {
	protected String[] positifs,negatifs;
	
	public ClassificationMotCle(String cheminPositif, String cheminNegatif)
	{
		byte[] fichier;
		try {
			fichier = Files.readAllBytes(Paths.get(cheminPositif));
			  String textpositif = new String(fichier);
			  System.out.println(textpositif);
			  this.positifs = textpositif.split(", ");
			  fichier = Files.readAllBytes(Paths.get(cheminNegatif));
			  String textnegatif = new String(fichier);
			  System.out.println(textnegatif);
			  this.negatifs = textnegatif.split(", ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param tw Le tweet à évaluer.
	 * @return Le tweet évalué
	 */
	public Tweet evaluateTweet(Tweet tw)
	{
		String[] mots = tw.getText().split(" ");
		int nbPositif=0;
		int nbNegatif=0;
		for(int i =0; i<mots.length;i++)
		{
			for(int j =0; j<this.positifs.length;j++)
			{
				if(mots[i].equals(positifs[j]))
				{
					nbPositif++;
				}
			}
			for(int j =0; j<this.negatifs.length;j++)
			{
				if(mots[i].equals(negatifs[j]))
				{
					nbNegatif++;
				}
			}
		}
		
		System.out.println("positif : "+nbPositif);
		System.out.println("négatif : "+nbNegatif);
		
		if(nbPositif==nbNegatif)
		{
			tw.setValue(Tweet.NEUTRAL);
		}
		else if(nbPositif>nbNegatif)
		{
			tw.setValue(Tweet.GOOD);
		}
		else if(nbNegatif>nbPositif)
		{
			tw.setValue(Tweet.BAD);
		}
		
		return tw;
	}

}
