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
package parsertest.ConnectionSectionTest;

import java.io.IOException;

import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.ConnectionSection.ConnectionsParser;
import org.helm.notation2.parser.ConnectionSection.ConnectionsReadSecondIDParser;
import org.helm.notation2.parser.ExceptionParser.ConnectionSectionException;
import org.helm.notation2.parser.ExceptionParser.ExceptionState;
import org.helm.notation2.parser.GroupingSection.GroupingParser;
import org.jdom.JDOMException;
import org.testng.annotations.Test;


public class ConnectionsParserTest {

StateMachineParser parser;
	
	@Test 
  public void keepThisState() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
		parser.setState(new ConnectionsParser(parser));
		parser.doAction('H');
		
		if(!(parser.getState() instanceof ConnectionsParser)){
			throw new ConnectionSectionException("");
		}  
	}
	
	@Test 
  public void goToGroupingParser() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
		parser.setState(new ConnectionsParser(parser));
		parser.doAction('$');
		
		if(!(parser.getState() instanceof GroupingParser)){
			throw new ConnectionSectionException("");
		}  
	}
	
	@Test(expectedExceptions = ConnectionSectionException.class)
  public void goToGroupingParserWithException() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
		parser.setState(new ConnectionsParser(parser));
		String test = "hallo$";
		for(int i = 0; i < test.length(); i ++){
			parser.doAction(test.charAt(i));
		}
	}
	
	
	@Test 
  public void goToConnectionsReadSecondIDParser() throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
		parser.setState(new ConnectionsParser(parser));
		String test = "CHEM1,";
		for(int i = 0; i < test.length(); i ++){
			parser.doAction(test.charAt(i));
		}
		
		if(!(parser.getState() instanceof ConnectionsReadSecondIDParser)){
			throw new ConnectionSectionException("");
		}  
	}
	
	@Test(expectedExceptions = ConnectionSectionException.class)
  public void goToConnectionsReadSecondIDParserWithException()
      throws ExceptionState, IOException, JDOMException {
		parser = new StateMachineParser();
		parser.setState(new ConnectionsParser(parser));
		String test = "PEPTID,";
		for(int i = 0; i < test.length(); i ++){
			parser.doAction(test.charAt(i));
		}
		
	}
		
}
