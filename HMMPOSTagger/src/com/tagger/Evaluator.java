package com.tagger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Evaluator {

	private int totalNumberOfWords;
	private int knowWords;
	private int totalNumberOfKnownWords;
	private int unknownWords;
	private int totalNumberOfUnknownWords;

	public Evaluator() {
		// TODO Auto-generated constructor stub
	}

	public void compare(String inputFileName, String refFileName) {

		FileReader inputFile = null;
		BufferedReader inputReader = null;
		String inputLine = "";

		FileReader refFile = null;
		BufferedReader refReader = null;
		String refLine = "";

		try {

			inputFile = new FileReader(inputFileName);
			inputReader = new BufferedReader(inputFile);

			refFile = new FileReader(refFileName);
			refReader = new BufferedReader(refFile);

			while (((inputLine = inputReader.readLine()) != null)
					&& ((refLine = refReader.readLine()) != null)) {

				StringTokenizer stInput = new StringTokenizer(inputLine);
				StringTokenizer stRef = new StringTokenizer(refLine);

				if (stInput.countTokens() == stRef.countTokens()) {

					while (stInput.hasMoreTokens() && stRef.hasMoreTokens()) {

						String input[] = stInput.nextToken().split("/");
						String ref[] = stRef.nextToken().split("/");

						if (!input[0].contains("||")) {

							if (input[1].equalsIgnoreCase(ref[1])) {

								knowWords++;
							}

							totalNumberOfKnownWords++;
						} else {

							if (input[1].equalsIgnoreCase(ref[1])) {

								unknownWords++;
							}

							totalNumberOfUnknownWords++;
						}

						totalNumberOfWords++;
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			if (inputFile != null) {

				try {

					inputReader.close();
					inputFile.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
	}

	public void printMaps() {

		try {

			// System.out
			// .println("Total Number of Known Words Tagged Correctly : "
			// + knowWords);
			// System.out.println("Total Number of Known Words : "
			// + totalNumberOfKnownWords);
			// System.out
			// .println("Total Number of Unknown Words Tagged Correctly : "
			// + unknownWords);
			// System.out.println("Total Number of UnKnown Words : "
			// + totalNumberOfUnknownWords);
			// System.out.println("Total Number of Words : " +
			// totalNumberOfWords);

			System.out
					.println("Overall Accuracy       : "
							+ ((knowWords + unknownWords) / (double) totalNumberOfWords));
			System.out.println("Known Words Accuracy   : "
					+ (knowWords / (double) totalNumberOfKnownWords));
			System.out.println("UnKnown Words Accuracy : "
					+ (unknownWords / (double) totalNumberOfUnknownWords));

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
