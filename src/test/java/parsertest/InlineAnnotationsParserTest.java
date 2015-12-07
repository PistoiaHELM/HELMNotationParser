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
package parsertest;

import java.io.IOException;

import org.helm.notation2.parser.InlineAnnotationsParser;
import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.connectionsection.BetweenInlineConnectionParser;
import org.helm.notation2.parser.connectionsection.ConnectionsParser;
import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.helm.notation2.parser.exceptionparser.SimplePolymerSectionException;
import org.helm.notation2.parser.simplepolymerssection.BetweenInlineMonomerParser;
import org.helm.notation2.parser.simplepolymerssection.BetweenParser;
import org.jdom2.JDOMException;
import org.testng.annotations.Test;

public class InlineAnnotationsParserTest {
	StateMachineParser parser;
	
	@Test 
  public void keepThisState() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
    parser.setState(new InlineAnnotationsParser(parser, 1));
		parser.doAction('H');
		
		if(!(parser.getState() instanceof InlineAnnotationsParser)){
			throw new SimplePolymerSectionException("");
		}  
  }
	
	@Test 
  public void goToBetweenParser() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
    String test = "PEPTIDE1{A.P}\"\"";
    for (int i = 0; i < test.length(); i++) {
      parser.doAction(test.charAt(i));
    }
		
		if(!(parser.getState() instanceof BetweenParser)){
			throw new SimplePolymerSectionException("");
		}  
  }
	
	
	
	@Test 
  public void goToBetweenInlineConnectionParser() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
    parser.setState(new ConnectionsParser(parser));
    String test = "PEPTIDE1,PEPTIDE2,C:R3-?:?\"\"";
    for (int i = 0; i < test.length(); i++) {
      parser.doAction(test.charAt(i));
    }
		
		if(!(parser.getState() instanceof BetweenInlineConnectionParser)){
			throw new SimplePolymerSectionException("");
		}  
  }
	

	@Test 
  public void goToSimplePolymersNotationParserParser() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
    String test = "PEPTIDE1{A.P\"mutation\"";
    for (int i = 0; i < test.length(); i++) {
      parser.doAction(test.charAt(i));
    }
		
    if (!(parser.getState() instanceof BetweenInlineMonomerParser)) {
			throw new SimplePolymerSectionException("");
		}  
  }
	
	
}
