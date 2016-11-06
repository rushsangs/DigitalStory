package ui;

import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class TextHighlighter {
	
	public static void highlightText(JTextArea textArea, Color color, int start, int end){
		Highlighter highlighter = textArea.getHighlighter();
	      HighlightPainter painter = 
	             new DefaultHighlighter.DefaultHighlightPainter(color);
	      try {
			highlighter.addHighlight(start, end, painter );
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

}
