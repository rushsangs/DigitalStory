import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class NLPConnector {
	public static void analyze(String storytext, String fileURI) {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		// Initialize an Annotation with some text to be annotated. The text is the argument to the constructor.
		Annotation annotation = new Annotation(storytext);
		
		// run all the selected Annotators on this text
		pipeline.annotate(annotation);
		
		List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		try {	    
			Files.write(Paths.get(fileURI), "".getBytes());
			for(int i =0; i< sentences.size(); ++i)
			{
					CoreMap test_sentence = sentences.get(i);
				    Files.write(Paths.get(fileURI), 
				    		test_sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class).toString(SemanticGraph.OutputFormat.LIST).getBytes(),
				    		StandardOpenOption.APPEND);

			}
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
