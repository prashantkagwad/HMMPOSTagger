package com.tagger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class Processor {

	private HashMap<String, TagObject> tagMap = new HashMap<String, TagObject>();
	private HashMap<String, WordObject> wordMap = new HashMap<String, WordObject>();
	private int totalNumberOfWords;

	public HashMap<String, TagObject> getTagMap() {
		return tagMap;
	}

	public void setTagMap(HashMap<String, TagObject> tagMap) {
		this.tagMap = tagMap;
	}

	public HashMap<String, WordObject> getWordMap() {
		return wordMap;
	}

	public void setWordMap(HashMap<String, WordObject> wordMap) {
		this.wordMap = wordMap;
	}

	public int getTotalNumberOfWords() {
		return totalNumberOfWords;
	}

	public void setTotalNumberOfWords(int totalNumberOfWords) {
		this.totalNumberOfWords = totalNumberOfWords;
	}

	public Processor() {
		// TODO Auto-generated constructor stub
	}

	public void readTrainingFile(String fileName) {

		FileReader file = null;
		BufferedReader reader = null;
		String line = "";

		try {

			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {

				line = "<s>/<S> " + line + " <\\s>/<\\S>";
				StringTokenizer st = new StringTokenizer(line, " ");

				while (st.hasMoreTokens()) {

					String token = st.nextToken().toLowerCase();
					String[] stringCombo = token.split("/");

					String word = stringCombo[0];
					String tag = stringCombo[1];

					// Insert the word in hashMap for possible POS Tags [word -
					// tag]
					if (wordMap.containsKey(word)) {

						WordObject wordObject = wordMap.get(word);
						wordObject.setCount(wordObject.getCount() + 1);

						HashMap<String, TagProbMap> tagMap = wordObject
								.getTagProb();
						if (tagMap.containsKey(tag)) {
							TagProbMap tagProbMap = tagMap.get(tag);
							tagProbMap.setCount(tagProbMap.getCount() + 1);
							tagMap.put(tag, tagProbMap);
						} else {
							TagProbMap tagProbMap = new TagProbMap();
							tagProbMap.setCount(1);
							tagMap.put(tag, tagProbMap);
						}
						wordObject.setTagProb(tagMap);
						wordMap.put(word, wordObject);

					} else {

						WordObject wordObject = new WordObject();
						wordObject.setCount(1);

						HashMap<String, TagProbMap> tagMap = wordObject
								.getTagProb();

						if (tagMap.containsKey(tag)) {

							TagProbMap tagProbMap = tagMap.get(tag);
							tagProbMap.setCount(tagProbMap.getCount() + 1);
							tagMap.put(tag, tagProbMap);

						} else {

							TagProbMap tagProbMap = new TagProbMap();
							tagProbMap.setCount(1);
							tagMap.put(tag, tagProbMap);
						}
						wordObject.setTagProb(tagMap);
						wordMap.put(word, wordObject);
					}

					// Insert the tag in tagMap [tag - word]
					if (tagMap.containsKey(tag)) {

						TagObject tagObject = tagMap.get(tag);
						tagObject.setCount(tagObject.getCount() + 1);

						HashMap<String, WordProbMap> wordMap = tagObject
								.getWordProb();

						if (wordMap.containsKey(word)) {

							WordProbMap wordProbMap = wordMap.get(word);
							wordProbMap.setCount(wordProbMap.getCount() + 1);
							wordMap.put(word, wordProbMap);

						} else {

							WordProbMap wordProbMap = new WordProbMap();
							wordProbMap.setCount(1);
							wordMap.put(word, wordProbMap);
						}

						tagObject.setWordProb(wordMap);
						tagMap.put(tag, tagObject);

					} else {

						TagObject tagObject = new TagObject();
						tagObject.setCount(1);

						HashMap<String, WordProbMap> wordMap = tagObject
								.getWordProb();

						if (wordMap.containsKey(word)) {

							WordProbMap wordProbMap = wordMap.get(word);
							wordProbMap.setCount(wordProbMap.getCount() + 1);
							wordMap.put(word, wordProbMap);

						} else {

							WordProbMap wordProbMap = new WordProbMap();
							wordProbMap.setCount(1);
							wordMap.put(word, wordProbMap);
						}

						tagObject.setWordProb(wordMap);
						tagMap.put(tag, tagObject);
					}

					totalNumberOfWords++;
				}
			}

			// for tag-tag probabilities [Transaction Probabilities]
			computeBiTagCount(fileName);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (file != null) {
				try {
					reader.close();
					file.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void computeBiTagCount(String fileName) {

		FileReader file = null;
		BufferedReader reader = null;
		String line = "";

		try {

			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {

				line = "<s>/<S> " + line + " <\\s>/<\\S>";
				String st[] = line.split(" ");

				for (int iterator = 0; iterator < st.length - 1; iterator++) {

					String[] set1 = st[iterator].toLowerCase().split("/");
					String[] set2 = st[iterator + 1].toLowerCase().split("/");

					String tag1 = set1[1];
					String tag2 = set2[1];

					if (tagMap.containsKey(tag1)) {

						TagObject tagObject = tagMap.get(tag1);
						HashMap<String, TagProbMap> tagTransMap = tagObject
								.getTransProb();

						if (tagTransMap.containsKey(tag2)) {

							TagProbMap tagProbMap = tagTransMap.get(tag2);
							tagProbMap.setCount(tagProbMap.getCount() + 1);

							tagTransMap.put(tag2, tagProbMap);
							tagObject.setTransProb(tagTransMap);

						} else {

							TagProbMap tagProbMap = new TagProbMap();
							tagProbMap.setCount(1);

							tagTransMap.put(tag2, tagProbMap);
							tagObject.setTransProb(tagTransMap);

						}
						tagMap.put(tag1, tagObject);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (file != null) {
				try {
					reader.close();
					file.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void computeProbs(String fileName) {

		File file = new File(fileName);
		FileWriter fileWriter = null;
		BufferedWriter writer = null;

		try {

			fileWriter = new FileWriter(fileName);
			writer = new BufferedWriter(fileWriter);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			writer.write("Transaction Probabilities:\n");
			// compute for tag-tag probs ~ Transaction Probs [1]
			for (Entry<String, TagObject> tag1Entry : tagMap.entrySet()) {

				String tag1 = tag1Entry.getKey();
				TagObject tagObject = tag1Entry.getValue();

				int wordCount = tagObject.getCount();

				HashMap<String, TagProbMap> transProb = tagObject
						.getTransProb();

				for (Entry<String, TagProbMap> tag2Entry : transProb.entrySet()) {

					String tag2 = tag2Entry.getKey();
					TagProbMap tagProbMap = tag2Entry.getValue();

					int tagCount = tagProbMap.getCount();

					double prob = (double) tagCount / wordCount;

					writer.write(tag1.toUpperCase() + " " + tag2.toUpperCase()
							+ " " + prob + "\n");

					tagProbMap.setProb(prob);
					transProb.put(tag2, tagProbMap);
				}

				tagObject.setTransProb(transProb);
				tagMap.put(tag1, tagObject);
			}
			writer.write("<\\S> <\\S> 0\n");
			writer.write("\n");

			writer.write("Observation Probabilities:\n");
			// compute for tag-word probs ~ Observation Probs [2]
			for (Entry<String, TagObject> tagEntry : tagMap.entrySet()) {

				String tag = tagEntry.getKey();
				TagObject tagObject = tagEntry.getValue();

				int wordCount = tagObject.getCount();

				HashMap<String, WordProbMap> wordProb = tagObject.getWordProb();

				for (Entry<String, WordProbMap> wordEntry : wordProb.entrySet()) {

					String word = wordEntry.getKey();
					WordProbMap tagProbMap = wordEntry.getValue();

					int tagCount = tagProbMap.getCount();

					double prob = (double) tagCount / wordCount;

					writer.write(tag.toUpperCase() + " " + word.toLowerCase()
							+ " " + prob + "\n");

					tagProbMap.setProb(prob);
					wordProb.put(word, tagProbMap);
				}
				tagObject.setWordProb(wordProb);
				tagMap.put(tag, tagObject);
			}
			writer.write("\n");

			writer.write("wordPOSTag:\n");
			// compute for word-tag probs ~ possible POS tags for a word [3]
			for (Entry<String, WordObject> wordEntry : wordMap.entrySet()) {

				String word = wordEntry.getKey();
				WordObject wordObject = wordEntry.getValue();

				int wordCount = wordObject.getCount();

				HashMap<String, TagProbMap> tagProb = wordObject.getTagProb();

				for (Entry<String, TagProbMap> tagEntry : tagProb.entrySet()) {

					String tag = tagEntry.getKey();
					TagProbMap tagProbMap = tagEntry.getValue();

					int tagCount = tagProbMap.getCount();

					double prob = (double) tagCount / wordCount;

					writer.write(word.toLowerCase() + " " + tag.toUpperCase()
							+ " " + prob + "\n");

					tagProbMap.setProb(prob);
					tagProb.put(tag, tagProbMap);
				}

				wordObject.setTagProb(tagProb);
				wordMap.put(word, wordObject);
			}
			writer.write("\n");

			writer.write("Tag Probablilties:\n");
			// compute for word-tag probs ~ possible POS tags for a word [3]
			for (Entry<String, TagObject> tag1Entry : tagMap.entrySet()) {

				String tag = tag1Entry.getKey();
				TagObject tagObject = tag1Entry.getValue();

				int tagCount = tagObject.getCount();

				double prob = tagCount / (double) totalNumberOfWords;
				writer.write(tag.toUpperCase() + " " + prob + "\n");
			}
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (fileWriter != null) {
				try {
					writer.close();
					fileWriter.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void printMaps() {

		try {
			System.out.println("TotalNumberOfWords : " + totalNumberOfWords);
			System.out.println("wordMap : " + wordMap.size());
			System.out.println("TagMap : " + tagMap.size());

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void printTagDetails(String key) {

		try {
			System.out
					.println("-------------------------------------------------------------");
			TagObject f = tagMap.get(key);
			System.out.println("Key : " + key);
			System.out.println("Count : " + f.getCount());

			HashMap<String, TagProbMap> z = f.getTransProb();

			for (Entry<String, TagProbMap> tagEntry : z.entrySet()) {

				String tag = tagEntry.getKey();
				TagProbMap tagProbMap = tagEntry.getValue();

				System.out.println("Tag : " + tag);
				System.out.println("Prob : " + tagProbMap.getProb());
			}
			System.out
					.println("-------------------------------------------------------------");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void printWordDetails(String key) {

		try {
			System.out
					.println("-------------------------------------------------------------");
			WordObject f = wordMap.get(key);
			System.out.println("Key : " + key);
			System.out.println("Count : " + f.getCount());

			HashMap<String, TagProbMap> z = f.getTagProb();

			for (Entry<String, TagProbMap> tagEntry : z.entrySet()) {

				String tag = tagEntry.getKey();
				TagProbMap tagProbMap = tagEntry.getValue();

				System.out.println("Tag : " + tag);
				System.out.println("Prob : " + tagProbMap.getProb());
			}
			System.out
					.println("-------------------------------------------------------------");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
