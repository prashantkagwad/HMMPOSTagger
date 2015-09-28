package com.tagger;

public class Train {

	public Train() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			// Check for number of arguments.
			if (args.length != 4) {
				System.err
						.println("You must call Train as "
								+ "follows:\n\njava Train "
								+ "-train <Training_File_Name> -model <Model_File_Name> \n");
				System.exit(1);
			}

			// Read in the file names.
			String trainingFileName = args[1];
			String lmFileName = args[3];

			// String trainingFileName = "hw3_train.txt";
			// String lmFileName = "model.txt";

			String trainingFile = System.getProperty("user.dir") + "\\"
					+ trainingFileName;
			String lmFile = System.getProperty("user.dir") + "\\" + lmFileName;

			long start = System.currentTimeMillis();

			Processor processor = new Processor();

			processor.readTrainingFile(trainingFile);
			processor.computeProbs(lmFile);

			// System.out.println(processor.getTotalNumberOfWords());
			// p.printMaps();
			// p.printWordDetails("preliminary");

			long elapsedTime = System.currentTimeMillis() - start;
			System.out.println("Model file generated in " + elapsedTime
					+ " milli-seconds at " + lmFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
