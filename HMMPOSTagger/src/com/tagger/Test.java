package com.tagger;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			// Check for number of arguments.
			if (args.length != 6) {
				System.err
						.println("You must call Test as "
								+ "follows:\n\njava Test "
								+ "-test <Test_File_Name> -model <Model_File_Name> -o <Output_File_Name> \n");
				System.exit(1);
			}

			// Read in the file names.
			String testFileName = args[1];
			String lmFileName = args[3];
			String outputFileName = args[5];

			// String testFileName = "hw3_test_00.txt";
			// String lmFileName = "model.txt";
			// String outputFileName = "tagged.txt";

			String testFile = System.getProperty("user.dir") + "\\"
					+ testFileName;
			String lmFile = System.getProperty("user.dir") + "\\" + lmFileName;
			String outFile = System.getProperty("user.dir") + "\\"
					+ outputFileName;

			long start = System.currentTimeMillis();

			ViterbiDecoding viterbi = new ViterbiDecoding();

			// Read the model file
			viterbi.readTP(lmFile);
			viterbi.readOP(lmFile);
			viterbi.readWordPOSTag(lmFile);
			viterbi.readTagProb(lmFile);

			// Decode the text
			viterbi.decode(testFile, outFile);

			// HashMap<String, HashMap<String, Double>> map = v.getTransProb();
			// v.convertMapToArray(map);
			// viterbi.printMaps();

			long elapsedTime = System.currentTimeMillis() - start;
			System.out.println("Tagged file generated in " + elapsedTime
					+ " milli-seconds at " + outFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
