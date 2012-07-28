public class Sentence {
	private String sentence;
	private String[] words;

	public Sentence(String[] words) {
		super();
		this.words = words;
	}
	public Sentence(String[] words,String sentence) {
		super();
		this.setSentence(sentence);
		this.words = words;
	}

	public Sentence() {
	}
	
	public int size() {
		return words.length;
	}

	@Override
	public String toString() {
		return sentence;
		//return "Sentence [words=" + Arrays.toString(words) + "]";
	}


	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	
	
}
