

import java.io.*;
import java.util.*;

import edu.stanford.nlp.hcoref.data.CorefChain;
import edu.stanford.nlp.hcoref.CorefCoreAnnotations;
import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;

/** This class demonstrates building and using a Stanford CoreNLP pipeline. */
public class TestNlp {

  /** Usage: java -cp "*" StanfordCoreNlpDemo [inputFile [outputTextFile [outputXmlFile]]] */
  public static void main(String[] args) throws IOException {
    NLPConnector.analyze("Ned Stark met King Robert in Winterfell, and King Robert asked Ned to accompany him to Kings Landing. Ned took Sansa with him as he wanted Sansa to marry Robert's son Joffrey. Ned and Sansa went to Kings Landing. Cersei Lannister was unhappy about it because she wanted to rule Kings Landing but Ned would stop her from doing so.", "patrick_test.txt");
    File file = new File("patrick_test.txt");
	Scanner input = new Scanner(file);
	String s="";
	while(input.hasNext())
	{
		 s += input.nextLine();
		
	}
	NLPConnector.convertNLPToProlog(s);
  }

}
