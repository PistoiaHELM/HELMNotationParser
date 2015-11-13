/**
 * *****************************************************************************
 * Copyright C 2015, The Pistoia Alliance
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *****************************************************************************
 */
package parsertest.SimplePolymersSectionTest;

import java.io.IOException;

import org.helm.notation2.parser.InlineAnnotationsParser;
import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.ExceptionParser.ExceptionState;
import org.helm.notation2.parser.ExceptionParser.SimplePolymerSectionException;
import org.helm.notation2.parser.SimplePolymersSection.BetweenParser;
import org.helm.notation2.parser.SimplePolymersSection.RepeatingMonomerParser;
import org.helm.notation2.parser.SimplePolymersSection.SimplePolymersNotationParser;
import org.helm.notation2.parser.SimplePolymersSection.SimplePolymersParser;
import org.jdom.JDOMException;
import org.testng.annotations.Test;

public class SimplePolymersNotationParserTest {

StateMachineParser parser;
	
	@Test 
  public void keepThisStateTest() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
    parser.setState(new SimplePolymersNotationParser(parser));
		parser.doAction('P');
		
		if(!(parser.getState() instanceof SimplePolymersNotationParser)){
			throw new SimplePolymerSectionException("");
		}  
  }
  
	@Test 
  public void initalizeThisStateTest() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
		parser.setState(new SimplePolymersParser(parser));
    String test = "PEPTIDE1{P.";
		for (int i = 0; i < test.length(); i ++){
			parser.doAction(test.charAt(i));
		}
		
		if(!(parser.getState() instanceof SimplePolymersNotationParser)){
			throw new SimplePolymerSectionException("");
		}  
  }
	
	@Test(expectedExceptions = SimplePolymerSectionException.class)
  public void initalizeThisStateTestWithException() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
    parser.setState(new SimplePolymersNotationParser(parser));

		parser.doAction('.');

  }
	
	@Test
  public void goToBetweenParser() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
    parser.setState(new SimplePolymersParser(parser));
    String test = "PEPTIDE1{G.R}";
		for (int i = 0; i < test.length(); i ++){
			parser.doAction(test.charAt(i));
		}
		if(!(parser.getState() instanceof BetweenParser)){
			throw new SimplePolymerSectionException("");
		}   
	}
	
	/*Description can also be empty*/
	//@Test(expectedExceptions = SimplePolymerSectionException.class)
  public void goToBetweenParserWithException() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
    parser.setState(new SimplePolymersNotationParser(parser));
    String test = "}";
    for (int i = 0; i < test.length(); i ++){
      parser.doAction(test.charAt(i));
    }
    if(!(parser.getState() instanceof BetweenParser)){
      throw new SimplePolymerSectionException("");
    }
		
		  
	}
	
  // @Test(expectedExceptions = SimplePolymerSectionException.class)
  public void Reading$WithException() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
    parser.setState(new SimplePolymersNotationParser(parser));
		parser.doAction('$');
		
		  
	}
  
    @Test
  public void goToInlineAnnotaitonsParser() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
    String test = "PEPTIDE1{A.P\"mutation";
		for (int i = 0; i < test.length(); i ++){
			parser.doAction(test.charAt(i));
		}
		if(!(parser.getState() instanceof InlineAnnotationsParser)){
			throw new SimplePolymerSectionException("");
		}  
	}
    
    @Test(expectedExceptions = SimplePolymerSectionException.class)
  public void goToInlineAnnotaitonsParserWithException() throws ExceptionState, IOException, JDOMException {
  		parser = new StateMachineParser();
    String test = "PEPTIDE{test\"";
      for (int i = 0; i < test.length(); i ++){
        parser.doAction(test.charAt(i));
      }
  	
  
  		 
  	}
      
    
    @Test
  public void goToRepeatingMonomerParser() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
		parser.setState(new SimplePolymersParser(parser));
    String test = "PEPTIDE1{P'";
		for (int i = 0; i < test.length(); i ++){
			parser.doAction(test.charAt(i));
		}
		
		if(!(parser.getState() instanceof RepeatingMonomerParser)){
			throw new SimplePolymerSectionException("");
		}  
	}
    
    @Test(expectedExceptions = SimplePolymerSectionException.class)
  public void goToRepeatingMonomerParserWithException() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
    parser.setState(new SimplePolymersNotationParser(parser));
		parser.doAction('\'');
	
	}
    
    @Test
  public void testSMILESFunction() throws ExceptionState, IOException, JDOMException {
   		parser = new StateMachineParser();
    parser.setState(new SimplePolymersNotationParser(parser));
   		String test = "[[($$$...\"\"\")]]";
   		for (int i = 0; i < test.length(); i ++){
   			parser.doAction(test.charAt(i));
   		}
   		
   		if(!(parser.getState() instanceof SimplePolymersNotationParser)){
   			throw new SimplePolymerSectionException("");
   		}  
   	}
    
    @Test(expectedExceptions = SimplePolymerSectionException.class)
  public void testSMILESFunctionWithException() throws ExceptionState, IOException, JDOMException {
   		parser = new StateMachineParser();
    parser.setState(new SimplePolymersNotationParser(parser));
   		String test = "[[($$$...\"\"\")]'";
   		for (int i = 0; i < test.length(); i ++){
   			parser.doAction(test.charAt(i));
   		}
   		
   		if(!(parser.getState() instanceof RepeatingMonomerParser)){
   			throw new SimplePolymerSectionException("");
   		}  
   	}
  
}
