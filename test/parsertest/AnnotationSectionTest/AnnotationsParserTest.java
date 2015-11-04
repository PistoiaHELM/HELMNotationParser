package parsertest.AnnotationSectionTest;

import org.testng.annotations.Test;

import parsertest.FinalState;
import parsertest.StateMachineParser;
import parsertest.AnnotationSection.AnnotationsParser;
import parsertest.ExceptionParser.AnnotationSectionException;
import parsertest.ExceptionParser.ExceptionState;
import parsertest.ExceptionParser.SimplePolymerSectionException;


public class AnnotationsParserTest {
	StateMachineParser parser;
	
	
	@Test 
  	public void goToFinalState() throws ExceptionState {
		parser = new StateMachineParser();
		parser.setState(new AnnotationsParser(parser));
		parser.doAction('$');
		
		if(!(parser.getState() instanceof FinalState)){
			throw new SimplePolymerSectionException("");
		}  
  }
	
	@Test 
  	public void keepThisState() throws ExceptionState {
		parser = new StateMachineParser();
		parser.setState(new AnnotationsParser(parser));
		parser.doAction('|');
		
		if(!(parser.getState() instanceof AnnotationsParser)){
			throw new SimplePolymerSectionException("");
		}
	}
	
	
	@Test(expectedExceptions = AnnotationSectionException.class)
  	public void goToFinalStateWithException() throws ExceptionState {
		parser = new StateMachineParser();
		parser.setState(new AnnotationsParser(parser));
		String test = "($";
		for (int i = 0; i < test.length(); i++){
			parser.doAction(test.charAt(i));
		}
		
	}
}
