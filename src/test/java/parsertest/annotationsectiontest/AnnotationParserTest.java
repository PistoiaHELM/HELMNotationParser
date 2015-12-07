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
package parsertest.annotationsectiontest;

import java.io.IOException;

import org.helm.notation2.parser.FinalState;
import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.annotationsection.AnnotationsParser;
import org.helm.notation2.parser.exceptionparser.AnnotationSectionException;
import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.helm.notation2.parser.exceptionparser.SimplePolymerSectionException;
import org.jdom2.JDOMException;
import org.testng.annotations.Test;


public class AnnotationParserTest {

	StateMachineParser parser;
		
		@Test 
  public void keepThisState() throws ExceptionState, IOException, JDOMException {
			parser = new StateMachineParser();
			parser.setState(new AnnotationsParser(parser));
			parser.doAction('H');
			
			if(!(parser.getState() instanceof AnnotationsParser)){
				throw new AnnotationSectionException("");
			}  
		}
		
		@Test(expectedExceptions = AnnotationSectionException.class)
  public void keepToFinalStateWithException() throws ExceptionState, IOException, JDOMException {
			parser = new StateMachineParser();
			parser.setState(new AnnotationsParser(parser));
			String test = "H($";
			for(int i = 0; i<test.length(); ++i){
				parser.doAction(test.charAt(i));
			}
		}
		
		
		@Test 
  public void goToFinalState() throws ExceptionState, IOException, JDOMException {
			parser = new StateMachineParser();
			parser.setState(new AnnotationsParser(parser));
			parser.doAction('$');
			
			if(!(parser.getState() instanceof FinalState)){
				throw new SimplePolymerSectionException("");
			}  
		}

}
