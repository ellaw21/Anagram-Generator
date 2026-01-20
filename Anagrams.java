package anagrams;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//Ella Warnock
//I pledge my honor that I have abided by the Stevens Honor System.


public class Anagrams {
	final Integer[] primes =  
		{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 
		31, 37, 41, 43, 47, 53, 59, 61, 67, 
		71, 73, 79, 83, 89, 97, 101};
	Map<Character,Integer> letterTable = new HashMap<Character,Integer>();
	Map<Long,ArrayList<String>> anagramTable;

	
	//Iterates through the alphabet and assigns each letter a-z with the associated index in primes
	public void buildLetterTable() {
		int i = 0;
		for(char alphabet = 'a'; alphabet <= 'z'; alphabet++){
			letterTable.put(alphabet, primes[i]);
			i++;
		}
	}
	
	Anagrams() {
		buildLetterTable();
		anagramTable = new HashMap<Long,ArrayList<String>>();
	}
	
	
	//Adds given string to table of anagrams
	public void addWord(String s) {
		Long key = myHashCode(s);
		ArrayList<String> keyList = anagramTable.get(key);
		
		//If the ArrayList returned form the key is null then the bucket has no duplicates as the list is empty
		if(keyList == null) {
			keyList = new ArrayList<String>();
			keyList.add(s);
			anagramTable.put(key, keyList);
		}
		
		else {
			for(int i = 0; i < keyList.size(); i++) {
				if(keyList.get(i) == s) {
					throw new IllegalArgumentException("addWord: duplicate value");
				}
			}
		
		//If there are no duplicates, add it to the list
			keyList.add(s);
			anagramTable.put(key, keyList);
		}
		
	}
	
	
	
	//returns the product of the keys of all characters in the string
	public long myHashCode(String s) {
		if(s == null || s == "") {
		    throw new IllegalArgumentException("String is empty");
		}
		
		long store = 1;
		
		for(int i = 0; i < s.length(); i++) {
			store = store * letterTable.get(s.charAt(i));
		}
		
		return store;
	
	}
	
	public void processFile(String s) throws IOException {
		FileInputStream fstream = new FileInputStream(s);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		while ((strLine = br.readLine()) != null)   {
		  this.addWord(strLine);
		}
		br.close();
	}
	
	
	
	//returns a list of hashMap entries that have the largest amount of words
	public ArrayList<Map.Entry<Long,ArrayList<String>>> getMaxEntries() {
		int max = 0;
		ArrayList<Map.Entry<Long,ArrayList<String>>> lst = new ArrayList<Map.Entry<Long,ArrayList<String>>>();
		
		
		//If the table has no entries
		if(anagramTable.isEmpty()) {
			return null;
		}
		
		//iterates through hashMaps and finds the ArrayList with the greatest length and saves them to list lst
		for(Map.Entry<Long,ArrayList<String>> element: anagramTable.entrySet()) {
		  if(element.getValue().size() > max) {
			  max = element.getValue().size();
			  lst.clear();
			  lst.add(element);
		  }
		  else if(element.getValue().size() == max) {
			  lst.add(element);
		  }
		}
		
		return lst;
	}
	
	
	
	
	public static void main(String[] args) {
		Anagrams a = new Anagrams();
		
		final long startTime = System.nanoTime();    
		try {
			a.processFile("words_alpha.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<Map.Entry<Long,ArrayList<String>>> maxEntries = a.getMaxEntries();
		final long estimatedTime = System.nanoTime() - startTime;
		final double seconds = ((double) estimatedTime/1000000000);
		System.out.println("Time: "+ seconds);
		System.out.println("List of max anagrams: "+ maxEntries);
	}
}
