package currentmood.util;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
			Tweet tweet = new Tweet(new Long(line.get(0)), line.get(1), line.get(2), new Date(new Long(line.get(3))), line.get(4));
			
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
		if(wash)
			hashTweets = washTweets(hashTweets);
		
		writeTweetsInCSV(filename, hashTweets);
	}
	
	public static List<Tweet> washTweets(List<Tweet> hashTweets)
	{
		List<Tweet> newHashTweets = new ArrayList<Tweet>();
		
		for(Tweet tweet : hashTweets)
		{
			String text = tweet.getText();
			
			if(!text.matches("^RT "))
			{
				text.replaceAll("@([a-zA-Z0-9_.-])", " $1 "); // Suppression des @
				text.replaceAll("#([a-zA-Z0-9_])", " $1 "); // Suppression des #
				text.replaceAll("https?://[^ ,]", " "); // Suppression des liens
				text.replaceAll(":'?[D()]", " "); // Suppression des smileys :) :( :D :') :'( :'D
			}
			
			// Si le message se retrouve vide, on ne le met pas dans la hashmap à retourner
			if(!text.replace(" ", "").equals(""))
			{
				tweet.setText(text);
				newHashTweets.add(tweet);
			}
		}
		
		return newHashTweets;
	}

}
