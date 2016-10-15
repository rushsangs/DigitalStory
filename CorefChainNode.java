public class CorefChainNode
	{
		int line_no;
		int word_no;
		String word;
		String root_word;
		
		public CorefChainNode(String word, String root_word, int line_no, int word_no){
			this.line_no = line_no;
			this.word = word;
			this.word_no = word_no;
			this.root_word= root_word;
		}

		public int getLine_no() {
			return line_no;
		}

		public void setLine_no(int line_no) {
			this.line_no = line_no;
		}

		public int getWord_no() {
			return word_no;
		}

		public void setWord_no(int word_no) {
			this.word_no = word_no;
		}

		public String getWord() {
			return word;
		}

		public void setWord(String word) {
			this.word = word;
		}

		public String getRoot_word() {
			return root_word;
		}

		public void setRoot_word(String root_word) {
			this.root_word = root_word;
		}
	}