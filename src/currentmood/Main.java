package currentmood;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;
import currentmood.UI.*;
import currentmood.util.CMTwitter;
import currentmood.util.CSVFile;
import currentmood.util.NotConnectedException;
import currentmood.util.Proxy;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Win win = new Win(); 
		WinProxy winp= new WinProxy();
		
		//CMTwitter cmTwitter = new CMTwitter();
		//cmTwitter.setProxy(new Proxy("cache-etu.univ-lille1.fr", 3128));
		
		CSVFile file = new CSVFile("/home/m1/tanghe/test.csv");
		
		// Écriture :
		List<List<String>> content = new ArrayList<List<String>>();
		content.add(new ArrayList<String>());
		content.get(0).add("1");
		content.get(0).add("2");
		content.get(0).add("3");
		content.get(0).add("4");
		content.get(0).add("5");
		
		content.add(new ArrayList<String>());
		content.get(1).add("5");
		content.get(1).add("4");
		content.get(1).add("3");
		content.get(1).add("2");
		content.get(1).add("1");
		
		try {
			file.writeCSV(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Lecture :
		try {
			content = file.readCSV();
			
			for(List<String> line : content)
			{
				System.out.print("| ");
				for(String cell : line)
				{
					System.out.print(cell + " | ");
				}
				
				System.out.println();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
