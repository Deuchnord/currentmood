package currentmood.util;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>This class contains all the functions to create a CSV file (*.csv).
 * A CSV file contains a table with a set of values separated by a comma per line.</p>
 * 
 * <p>A tweet in the CSV file is represented by its id, author's nickname, content, timestamp,
 * the query associated and its annotation.<br />
 * It is formated as this:</p>
 * <code>156156048640,potus,Vote for me!,1444567840,obama,2</code>
 * 
 * <p>For memory, these values are used for the humor of the tweet:</p>
 * <ul>		
 * 		<li>0: bad</li>		
 * 		<li>2: neutral</li>
 * 		<li>4: good</li>
 * </ul>
 */
public class CSVFile {
	
	/**
	 * Reads the content of the file and returns its content.
	 * @return the content of the CSV file. The first list is the lines, the second the cells.
	 * @throws IOException thrown if the file cannot be read, for instance if it does not exist.
	 */
	public static List<List<String>> readCSV(String fileName) throws IOException
	{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
		List<List<String>> returned = new ArrayList<List<String>>(); 
		
		String line = null;
		
		while((line = bufferedReader.readLine()) != null)
		{
			String[] splittedLine = line.split(",");
			List<String> lineAsList = new ArrayList<String>();
			for(String s : splittedLine)
				lineAsList.add(s);
			
			returned.add(lineAsList);
		}
		
		bufferedReader.close();
		
		return returned;
	}
	
	/**
	 * Writes the data given in parameters in a CSV file.
	 * @param data the content of the file. The first list is the lines, the second the cells.
	 * @throws IOException
	 */
	public static void writeCSV(String fileName, List<List<String>> data) throws IOException
	{
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
		
		String fileContent = "";
		
		for(List<String> line : data)
		{
			if(!fileContent.equals(""))
				fileContent += "\n";
			
			String lineString = "";
			for(String cell : line)
			{
				if(!lineString.equals(""))
					lineString += ",";
				
				lineString += cell;
			}
			
			fileContent += lineString;
			
		}
		
		bufferedWriter.write(fileContent);		
		bufferedWriter.close();
	}
	
	public static List<Tweet> readTweetsInCSV(String fileName) throws IOException
	{
		List<List<String>> csvContent = readCSV(fileName);
		
		List<Tweet> hashTweets = new ArrayList<Tweet>();
		
		for(List<String> line : csvContent)
		{
			Tweet tweet = new Tweet(new Long(line.get(0)), line.get(1), line.get(2).replace("\n", " "), new Date(new Long(line.get(3))), line.get(4));
			
			Integer notation;
			// If there is a notation, we give it to the HashMap,
			// else we give it the default value -1.
			if(line.size() == 6)
				notation = new Integer(line.get(5));
			else
				notation = new Integer(-1);
			
			tweet.setValue(notation);
			
			hashTweets.add(tweet);
		}
		
		return hashTweets;
	}
	
	public static void writeTweetsInCSV(String filename, List<Tweet> hashTweets) throws IOException
	{
		List<List<String>> csvContent = new ArrayList<List<String>>();
		for(Tweet tweet : hashTweets)
		{
			List<String> line = new ArrayList<String>();
			line.add(String.valueOf(tweet.getId()));
			line.add(tweet.getUser());
			line.add(tweet.getText());
			line.add(String.valueOf(tweet.getCreatedAt().getTime()));
			line.add(tweet.getQuery());
			line.add(String.valueOf(tweet.getValue()));
			
			csvContent.add(line);
		}
		
		writeCSV(filename, csvContent);
	}
	
	public static void writeTweetsInCSV(String filename, List<Tweet> hashTweets, boolean wash) throws IOException
	{
		List<Tweet> newHashTweets = hashTweets;
		
		if(wash)
			newHashTweets = washTweets(hashTweets);
		
		writeTweetsInCSV(filename, newHashTweets);
	}
	
	public static List<Tweet> washTweets(List<Tweet> tweetsList)
	{
		List<Tweet> newHashTweets = new ArrayList<Tweet>();
		
		for(Tweet tweet : tweetsList)
		{
			String text = tweet.getText();
			
			if(!text.matches("^RT.*"))
			{
				text = text.replaceAll("\n", " "); // Suppression des retours à la ligne (gênant pour le CSV
				text = text.replaceAll(":\\'?[D()]", " "); // Suppression des smileys :) :( :D :') :'( :'D
				text = text.replaceAll("https?://[^ ,]+", " "); // Suppression des liens
				text = text.replaceAll("@([a-zA-Z0-9_.-]+)", " @ "); // Suppression des usernames
				text = text.replaceAll("#([a-zA-Z0-9_]+)", " # "); // Suppression des hashtags
				text = text.replaceAll("( )*([?!,.:;\"])( )?", " "); // Suppression des espaces avant la ponctuation & le guillemet
				text = text.replaceAll("[$€£]([0-9.]+)(\\.)?", "\\$XX$2"); // Suppression des $, des € et des £
				text = text.replaceAll("([0-9.]+)[$€£]", "\\$XX"); // Suppression des $, des € et des £
				text = text.replaceAll("[0-9]{1,3}%", "XX%");
			
				// Si le message se retrouve vide, on ne le met pas dans la hashmap à retourner
				if(!text.replaceAll(" ", "").equals(""))
				{
					tweet.setText(text);
					newHashTweets.add(tweet);
				}
			}
		}
		
		return newHashTweets;
	}

}
