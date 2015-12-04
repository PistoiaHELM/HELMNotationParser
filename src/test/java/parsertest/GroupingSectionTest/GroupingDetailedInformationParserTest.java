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
package parsertest.groupingsectiontest;

import java.io.IOException;

import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.exceptionparser.ConnectionSectionException;
import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.helm.notation2.parser.exceptionparser.GroupingSectionException;
import org.helm.notation2.parser.groupingsection.BetweenGroupingParser;
import org.helm.notation2.parser.groupingsection.GroupingDetailedInformationParser;
import org.helm.notation2.parser.groupingsection.GroupingParser;
import org.jdom.JDOMException;
import org.testng.annotations.Test;

public class GroupingDetailedInformationParserTest {
	StateMachineParser parser;
	
	@Test 
  public void keepThisState() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
		parser.setState(new GroupingDetailedInformationParser(parser));
		parser.doAction('H');
		
		if(!(parser.getState() instanceof GroupingDetailedInformationParser)){
			throw new GroupingSectionException("");
		}  
	}
	
	@Test 
  public void goToBetweenGroupingParser() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
    parser.setState(new GroupingParser(parser));
    String test = "G1(G1+G2)";
		for(int i = 0; i < test.length(); i ++){
			parser.doAction(test.charAt(i));
		}
		
		if(!(parser.getState() instanceof BetweenGroupingParser)){
			throw new ConnectionSectionException("");
		}  
	}
	
	
	@Test(expectedExceptions = GroupingSectionException.class)
  public void goToBetweenGroupingParserWithException() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
		parser.setState(new GroupingDetailedInformationParser(parser));
		String test = "h)";
		for(int i = 0; i < test.length(); i ++){
			parser.doAction(test.charAt(i));
		}
	}
	
	
	
}
