package com.ysq.test.entity;

import java.util.List;

public class WordWithAll {

	private Word word;
	private Pronunciation pronunciation;
	private List<Meaning> meanings;
	
	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public Pronunciation getPronunciation() {
		return pronunciation;
	}

	public void setPronunciation(Pronunciation pronunciation) {
		this.pronunciation = pronunciation;
	}

	public List<Meaning> getMeanings() {
		return meanings;
	}

	public void setMeanings(List<Meaning> meanings) {
		this.meanings = meanings;
	}

	public class Meaning {
		private PartOfSpeech partOfSpeech;
		private Explain explain;
		private List<Example> examples;
		public PartOfSpeech getPartOfSpeech() {
			return partOfSpeech;
		}
		public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
			this.partOfSpeech = partOfSpeech;
		}
		public Explain getExplain() {
			return explain;
		}
		public void setExplain(Explain explain) {
			this.explain = explain;
		}
		public List<Example> getExamples() {
			return examples;
		}
		public void setExamples(List<Example> examples) {
			this.examples = examples;
		}
	}
}
