package com.tagger;

import java.util.HashMap;

public class WordObject {

	private int count;
	private HashMap<String, TagProbMap> tagProb = new HashMap<String, TagProbMap>();

	public WordObject() {
		// TODO Auto-generated constructor stub
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public HashMap<String, TagProbMap> getTagProb() {
		return tagProb;
	}

	public void setTagProb(HashMap<String, TagProbMap> tagProb) {
		this.tagProb = tagProb;
	}

}
