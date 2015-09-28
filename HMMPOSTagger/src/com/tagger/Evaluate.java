package com.tagger;

public class Evaluate {

	public Evaluate() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			// Check for number of arguments.
			if (args.length != 4) {
				System.err
						.println("You must call Evaluate as "
								+ "follows:\n\njava Evaluate "
								+ "-ref <Refrence_File_Name> -sys <Tagged_File_Name> \n");
				System.exit(1);
			}

			// Read in the file names.
			String refFileName = args[1];
			String inputFileName = args[3];

			// String refFileName = "hw3_test_ref_00.txt";
			// String inputFileName = "tagged.txt";

			String refFile = System.getProperty("user.dir") + "\\"
					+ refFileName;
			String inputFile = System.getProperty("user.dir") + "\\"
					+ inputFileName;

			Evaluator evaluator = new Evaluator();

			evaluator.compare(inputFile, refFile);

			evaluator.printMaps();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
