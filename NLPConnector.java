import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Properties;
import java.lang.StringBuilder;

import edu.stanford.nlp.io.EncodingPrintWriter.out;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class NLPConnector {
	public static String analyze(String storytext, String fileURI) {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		// Initialize an Annotation with some text to be annotated. The text is
		// the argument to the constructor.
		Annotation annotation = new Annotation(storytext);
		
		// run all the selected Annotators on this text
		pipeline.annotate(annotation);
		String result="";
		List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		try {
			
			Files.write(Paths.get(fileURI), "".getBytes());
			for (int i = 0; i < sentences.size(); ++i) {
				CoreMap test_sentence = sentences.get(i);
				String tmp = test_sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class)
						.toString(SemanticGraph.OutputFormat.LIST);
				Files.write(Paths.get(fileURI),
						tmp.getBytes(),
						StandardOpenOption.APPEND);
				result += tmp;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String convertNLPToProlog(String nlptext) {
		return convertNLPToProlog(nlptext, "");
	}
	public static String convertNLPToProlog(String nlptext, String outputFile) {
		final String ACTION = "action(%s, %s, %s).\n";
		
		String result = "";
		boolean firstStmt = true;
		if (outputFile.length()!=0) {
			try {
				Files.write(Paths.get(outputFile), "".getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// split per root tag
		String[] sentences = nlptext.split("root");
		for (int i = 0; i < sentences.length; ++i) {
			String sentence = sentences[i];
			if (sentence.indexOf("nsub") > 0) {
				// it does have atleast 1 nsub tag
				int index = sentence.indexOf("nsub");
				while (index >= 0) {
					// using string handling to extract afforder and affordee
					String affordance = sentence.substring(sentence.indexOf('(', sentence.indexOf("nsub", index)) + 1,
							sentence.indexOf(',', sentence.indexOf("nsub", index)));
					String afforder = sentence.substring(sentence.indexOf(',', sentence.indexOf("nsub", index)) + 2,
							sentence.indexOf(')', sentence.indexOf("nsub", index)));
					String affordee;

					// look for dobj with affordance
					if (sentence.indexOf("dobj("+affordance, index) == -1) {
						if (sentence.indexOf("cop", index) == -1) {
							index = sentence.indexOf("nsub", index + 1);
							continue;
						} else {
							affordee = sentence.substring(
									sentence.indexOf(',', sentence.indexOf("cop(" + affordance)) + 2,
									sentence.indexOf(')', sentence.indexOf("cop(" + affordance)));
							String tmp=null;
							tmp = affordance;
							affordance = affordee;
							affordee = tmp;

						}
					} else {
						affordee = sentence.substring(
								sentence.indexOf(',', sentence.indexOf("dobj(" + affordance)) + 2,
								sentence.indexOf(')', sentence.indexOf("dobj(" + affordance)));
					}
					index = sentence.indexOf("nsub", index + 1);
					if (afforder.equalsIgnoreCase(affordee) || afforder.equalsIgnoreCase(affordance)
							|| affordance.equalsIgnoreCase(affordee))
						continue;
					result += afforder + " " + affordance + " " + affordee + '\n';
					if (outputFile.length()!=0) {
						try {
							String[] args = {afforder, affordance, affordee};
							for (int j = 0; j<args.length; j++) {
								args[j] = args[j].toLowerCase().substring(0, args[j].lastIndexOf('-'));
							}
							Files.write(Paths.get(outputFile),
									String.format(ACTION, args[0], args[1], args[2]).getBytes(),
									StandardOpenOption.APPEND);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		out.println(result);
		return result;
	}
}
