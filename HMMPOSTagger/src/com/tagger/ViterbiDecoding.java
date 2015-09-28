package com.tagger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViterbiDecoding {

	private HashMap<String, HashMap<String, Double>> transProb = new HashMap<String, HashMap<String, Double>>();
	private HashMap<String, HashMap<String, Double>> observProb = new HashMap<String, HashMap<String, Double>>();
	private HashMap<String, HashMap<String, Double>> wordPOSTag = new HashMap<String, HashMap<String, Double>>();
	private HashMap<String, Double> tagProb = new HashMap<String, Double>();

	public ViterbiDecoding() {
		// TODO Auto-generated constructor stub
	}

	public void readTP(String fileName) {

		FileReader file = null;
		BufferedReader reader = null;
		String line = "";

		try {

			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {

				if (line.equalsIgnoreCase("Transaction Probabilities:")) {

					while ((line = reader.readLine()) != null) {

						if (line.equalsIgnoreCase("")) {
							return;
						}

						String st[] = line.split(" ");
						if (st.length == 3) {

							String tag1 = st[0].toUpperCase();
							String tag2 = st[1].toUpperCase();
							double prob = Double.parseDouble(st[2]);

							if (transProb.containsKey(tag1)) {

								HashMap<String, Double> temp = transProb
										.get(tag1);

								if (temp.containsKey(tag2)) {
									System.out
											.println("Duplicate Enteries for Tag-Tag - ["
													+ tag1 + "," + tag2 + "]\n");
									// something is wrong
								} else {

									temp.put(tag2, prob);
								}
								transProb.put(tag1, temp);

							} else {

								HashMap<String, Double> temp = new HashMap<String, Double>();
								temp.put(tag2, prob);
								transProb.put(tag1, temp);
							}
						}
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

	public void readOP(String fileName) {

		FileReader file = null;
		BufferedReader reader = null;
		String line = "";

		try {

			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {

				file = new FileReader(fileName);
				reader = new BufferedReader(file);
				while ((line = reader.readLine()) != null) {

					if (line.equalsIgnoreCase("Observation Probabilities:")) {

						while ((line = reader.readLine()) != null) {

							if (line.equalsIgnoreCase("")) {
								return;
							}

							String st[] = line.split(" ");
							if (st.length == 3) {

								String tag = st[0].toUpperCase();
								String word = st[1].toLowerCase();
								double prob = Double.parseDouble(st[2]);

								if (observProb.containsKey(tag)) {

									HashMap<String, Double> temp = observProb
											.get(tag);

									if (temp.containsKey(word)) {
										// something is wrong
										System.out
												.println("Duplicate Enteries for Tag-Word - ["
														+ tag
														+ ","
														+ word
														+ "]\n");
									} else {

										temp.put(word, prob);
									}
									observProb.put(tag, temp);

								} else {

									HashMap<String, Double> temp = new HashMap<String, Double>();
									temp.put(word, prob);
									observProb.put(tag, temp);
								}
							}
						}
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

	public void readWordPOSTag(String fileName) {

		FileReader file = null;
		BufferedReader reader = null;
		String line = "";

		try {

			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {

				if (line.equalsIgnoreCase("wordPOSTag:")) {

					while ((line = reader.readLine()) != null) {

						if (line.equalsIgnoreCase("")) {
							return;
						}

						String st[] = line.split(" ");
						if (st.length == 3) {

							String word = st[0].toLowerCase();
							String tag = st[1].toUpperCase();
							double prob = Double.parseDouble(st[2]);

							if (wordPOSTag.containsKey(word)) {

								HashMap<String, Double> temp = wordPOSTag
										.get(word);

								if (temp.containsKey(tag)) {
									// something is wrong
									System.out
											.println("Duplicate Enteries for Word-Tag - ["
													+ word + "," + tag + "]\n");

								} else {

									temp.put(tag, prob);
								}
								wordPOSTag.put(word, temp);

							} else {

								HashMap<String, Double> temp = new HashMap<String, Double>();
								temp.put(tag, prob);
								wordPOSTag.put(word, temp);
							}
						}
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

	public void readTagProb(String fileName) {

		FileReader file = null;
		BufferedReader reader = null;
		String line = "";

		try {

			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {

				if (line.equalsIgnoreCase("Tag Probablilties:")) {

					while ((line = reader.readLine()) != null) {

						if (line.equalsIgnoreCase("")) {
							return;
						}

						String st[] = line.split(" ");
						if (st.length == 2) {

							String tag = st[0].toUpperCase();
							double prob = Double.parseDouble(st[1]);

							if (tagProb.containsKey(tag)) {
								// something is wrong
								System.out
										.println("Duplicate Enteries for Tag - ["
												+ tag + "]\n");
							} else {
								tagProb.put(tag, prob);
							}
						}
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

	public void decode(String inputFileName, String outputFileName) {

		File outputFile = new File(outputFileName);
		FileWriter fileWriter = null;
		BufferedWriter writer = null;

		FileReader inputFile = null;
		BufferedReader reader = null;
		String line = "";

		try {
			fileWriter = new FileWriter(outputFileName);
			writer = new BufferedWriter(fileWriter);

			// if file doesnt exists, then create it
			if (!outputFile.exists()) {
				outputFile.createNewFile();
			}

			inputFile = new FileReader(inputFileName);
			reader = new BufferedReader(inputFile);

			String[][] tags = convertMapToIndex(transProb);

			while ((line = reader.readLine()) != null) {

				line = "<s> " + line + " <\\s>";
				String[] st = line.split(" ");
				String[] tempST = line.split(" ");

				// perform viterbi decoding

				HashMap<String, HashMap<String, Double>> viterbiMatrix = new HashMap<String, HashMap<String, Double>>();
				HashMap<String, HashMap<String, String>> path = new HashMap<String, HashMap<String, String>>();

				// Initialization
				HashMap<String, Double> tempHashMap = new HashMap<String, Double>();
				for (int tempCounter = 0; tempCounter < tags.length; tempCounter++) {
					String tempTag = tags[tempCounter][1];
					tempHashMap.put(tempTag, 0.0);
				}
				tempHashMap.put("<S>", 1.0);
				viterbiMatrix.put("<s>", tempHashMap);

				String prevWord = "<s>";

				// Induction
				// Rows - Symbol [WORDS]
				// Cols - State [TAGS]
				// For each WORD - row
				for (int wordCounter = 1; wordCounter < st.length; wordCounter++) {

					// int tagCounter = 0;
					// For each TAG - col
					String currWord = st[wordCounter];
					HashMap<String, Double> viterbiRow = new HashMap<String, Double>();
					HashMap<String, String> pathRow = new HashMap<String, String>();

					for (int tagCounter1 = 0; tagCounter1 < tags.length; tagCounter1++) {

						String tag1 = tags[tagCounter1][1];

						HashMap<String, Double> maxVal = new HashMap<String, Double>();

						for (int tagCounter2 = 0; tagCounter2 < tags.length; tagCounter2++) {

							String tag2 = tags[tagCounter2][1];

							// String checkWord = "";
							// String checkTag = "";

							// Previous row values
							double prev = 0.0;
							if (viterbiMatrix.containsKey(prevWord
									.toLowerCase())) {

								if (viterbiMatrix.get(prevWord.toLowerCase())
										.containsKey(tag2.toUpperCase())) {

									prev = viterbiMatrix.get(
											prevWord.toLowerCase()).get(
											tag2.toUpperCase());
								}
							}

							// Transaction Prob
							double tempTransProb = 0.0;
							if (transProb.containsKey(tag2.toUpperCase())) {

								if (transProb.get(tag2.toUpperCase())
										.containsKey(tag1.toUpperCase())) {

									tempTransProb = transProb.get(
											tag2.toUpperCase()).get(
											tag1.toUpperCase());
								}
							}

							// Observation Prob
							double tempObservProb = 0.0;
							if (!wordPOSTag.containsKey(currWord.toLowerCase())) {
								// Unknown Word

								double x = 1 / (double) (transProb.size() - 2);
								// tempObservProb = (0.01 * x)
								//  (double) tagProb.get(tag1
								// .toUpperCase());

								tempObservProb = x;
								tempST[wordCounter] = currWord + "||UNK";

							} else {

								// for Known word
								if (observProb.containsKey(tag1.toUpperCase())) {

									if (observProb
											.get(tag1.toUpperCase())
											.containsKey(currWord.toLowerCase())) {

										tempObservProb = observProb.get(
												tag1.toUpperCase()).get(
												currWord.toLowerCase());

									}
								}
								tempST[wordCounter] = currWord;
							}

							// if (checkWord.equalsIgnoreCase(currWord)
							// && checkTag.equalsIgnoreCase(tag1)) {
							// System.out.println(">> " + checkWord + " "
							// + checkTag + " " + prev + " "
							// + tempTransProb + " " + tempObservProb);
							// }

							maxVal.put(tag2,
									(prev * tempObservProb * tempTransProb));
						}

						// Get the max
						Map.Entry<String, Double> maxEntry = null;
						for (Map.Entry<String, Double> entry : maxVal
								.entrySet()) {
							if (maxEntry == null
									|| entry.getValue().compareTo(
											maxEntry.getValue()) > 0.0) {
								maxEntry = entry;
							}
						}

						String tag = maxEntry.getKey();
						Double max = maxEntry.getValue();

						viterbiRow.put(tag1.toUpperCase(), max);
						if (max > 0) {
							pathRow.put(tag1.toUpperCase(), tag.toUpperCase());
						}

					}

					viterbiMatrix.put(currWord.toLowerCase(), viterbiRow);
					path.put(currWord.toLowerCase(), pathRow);

					// System.out.println("PrevWord : " + prevWord);
					// System.out.println("currWord : " + currWord);
					prevWord = currWord;

				}

				// for (Entry<String, HashMap<String, Double>> entry :
				// viterbiMatrix
				// .entrySet()) {
				//
				// HashMap<String, Double> h = entry.getValue();
				//
				// for (Entry<String, Double> entry2 : h.entrySet()) {
				// System.out.println("Matrix : " + entry.getKey() + " "
				// + entry2.getKey() + " -> " + entry2.getValue());
				// }
				//
				// }

				// for (Entry<String, HashMap<String, String>> entry : path
				// .entrySet()) {
				//
				// HashMap<String, String> h = entry.getValue();
				//
				// for (Entry<String, String> entry2 : h.entrySet()) {
				// System.out.println("path : " + entry.getKey() + " "
				// + entry2.getKey() + " -> " + entry2.getValue());
				// }
				//
				// }

				String[] finalTags = new String[st.length];

				// find the path
				finalTags[st.length - 1] = "<//S>";
				for (int wordCounter = st.length - 1; wordCounter > 0; wordCounter--) {

					// Get the max
					Map.Entry<String, Double> maxEntry = null;
					for (Map.Entry<String, Double> entry : viterbiMatrix.get(
							st[wordCounter].toLowerCase()).entrySet()) {
						if (maxEntry == null
								|| entry.getValue().compareTo(
										maxEntry.getValue()) > 0.0) {
							maxEntry = entry;
						}
					}

					String tag = maxEntry.getKey();
					// Double max = maxEntry.getValue();

					finalTags[wordCounter - 1] = path.get(
							st[wordCounter].toLowerCase()).get(
							tag.toUpperCase());
				}

				String outputLine = "";
				for (int i = 1; i < st.length - 1; i++) {
					outputLine = outputLine + tempST[i] + "/" + finalTags[i]
							+ " ";
				}
				writer.write(outputLine + "\n");
			}

			writer.close();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void printMaps() {

		try {
			System.out.println("transProb : " + transProb.size());
			System.out.println("observProb : " + observProb.size());
			System.out.println("wordPOSTag : " + wordPOSTag.size());

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public String[][] convertMapToIndex(
			HashMap<String, HashMap<String, Double>> map) {

		String[][] twoDarray = new String[map.size()][2];

		Object[] keys = map.keySet().toArray();

		for (int row = 0; row < twoDarray.length; row++) {

			if (!keys[row].toString().equalsIgnoreCase("<S>")) {

				twoDarray[row][0] = Integer.toString(row);
				twoDarray[row][1] = keys[row].toString();

			} else {

				twoDarray[row][0] = Integer.toString(row);
				twoDarray[row][1] = twoDarray[0][1];

				// twoDarray[0][0] = "0";
				twoDarray[0][1] = "<S>";
			}
		}

		// for (int i = 0; i < twoDarray.length; i++) {
		// for (int j = 0; j < twoDarray[i].length; j++) {
		// System.out.print(twoDarray[i][j] + "\t");
		// }
		// System.out.println();
		// }

		return twoDarray;
	}

	public String[][] convertMapToArray(
			HashMap<String, HashMap<String, Double>> map) {

		String[][] twoDarray = new String[map.size() + 1][map.size() + 1];

		Object[] keys = map.keySet().toArray();
		// Object[] values = map.values().toArray();

		for (int row = 1; row < twoDarray.length; row++) {

			for (int col = 1; col < twoDarray.length; col++) {

				twoDarray[0][0] = "";
				twoDarray[row][0] = keys[row - 1].toString();
				twoDarray[0][col] = keys[col - 1].toString();

				if (map.containsKey(keys[row - 1])) {

					if (map.get(keys[row - 1]).containsKey(keys[col - 1])) {
						// System.out.println(map.get(keys[row - 1])
						// .get(keys[col - 1]).toString());
						twoDarray[row][col] = map.get(keys[row - 1])
								.get(keys[col - 1]).toString();
					} else {
						twoDarray[row][col] = "0.0";
					}
				}
			}
		}

		// for (int i = 0; i < twoDarray.length; i++) {
		// for (int j = 0; j < twoDarray[i].length; j++) {
		//
		// System.out.print(twoDarray[i][j] + "\t");
		// }
		// System.out.println();
		// }

		return twoDarray;
	}
}
