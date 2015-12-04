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

import org.helm.notation2.parser.FinalState;
import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.helm.notation2.parser.exceptionparser.FinalStateException;
import org.jdom.JDOMException;
import org.testng.annotations.Test;

/**
 * FinalStateTest class to test the transition of the class FinalState
 * 
 * @author hecht
 */
public class FinalStateTest {
  StateMachineParser parser;

  /**
   * the reading of any character in the final state should produce an exception
   * 
   * @throws ExceptionState
   * @throws JDOMException
   * @throws IOException
   */
  @Test(expectedExceptions = FinalStateException.class)
  public void goToFinalStateWithException() throws ExceptionState, IOException, JDOMException {
    parser = new StateMachineParser();
//    parser.setState(new FinalState(parser));
    parser.setState(new FinalState());
    parser.doAction('j');
  }

}
