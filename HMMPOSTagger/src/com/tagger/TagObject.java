package com.tagger;

import java.util.HashMap;

public class TagObject {

	private int count;
	private HashMap<String, TagProbMap> transProb = new HashMap<String, TagProbMap>();
	private HashMap<String, WordProbMap> wordProb = new HashMap<String, WordProbMap>();

	public TagObject() {
		// TODO Auto-generated constructor stub
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public HashMap<String, TagProbMap> getTransProb() {
		return transProb;
	}

	public void setTransProb(HashMap<String, TagProbMap> transProb) {
		this.transProb = transProb;
	}

	public HashMap<String, WordProbMap> getWordProb() {
		return wordProb;
	}

	public void setWordProb(HashMap<String, WordProbMap> wordProb) {
		this.wordProb = wordProb;
	}

}
