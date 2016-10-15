

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
    NLPConnector.analyze("Did Bob kill Alice? Was steve sleeping? Does Patrick eat cake? When did Jack climb the beanstalk? Was the giant sleeping? Who did bob kill?  What did patrick eat? Is Daniel human? Where did Neil meet Alice? Why did Bob marry Alice?", "patrick_test.txt");
    File file = new File("patrick_test.txt");
	Scanner input = new Scanner(file);
	String s="";
	while(input.hasNext())
	{
		 s += input.nextLine();
		
	}
	String oaotext = NLPConnector.convertNLPToOAO(s);
	NLPConnector.convertOAOToProlog(oaotext, "patrick_test.pro");
  }

}
