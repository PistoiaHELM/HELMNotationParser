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
package parsertest.simplepolymerssectiontest;

import java.io.IOException;

import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.helm.notation2.parser.exceptionparser.SimplePolymerSectionException;
import org.helm.notation2.parser.simplepolymerssection.BetweenMonomerParser;
import org.helm.notation2.parser.simplepolymerssection.BetweenParser;
import org.helm.notation2.parser.simplepolymerssection.SimplePolymersNotationParser;
import org.jdom.JDOMException;
import org.testng.annotations.Test;

/**
 * BetweenInlineMonomerParser
 * 
 * @author hecht
 */
public class BetweenInlineMonomerParser {

  StateMachineParser parser;

  @Test
  public void goToSimplePolymerNotationParser() throws ExceptionState, IOException, JDOMException {
    parser = new StateMachineParser();
    parser.setState(new BetweenMonomerParser(parser));
    parser.doAction('.');

    if (!(parser.getState() instanceof SimplePolymersNotationParser)) {
      throw new SimplePolymerSectionException("");
    }
  }

  @Test
  public void goToConnection() throws ExceptionState, IOException, JDOMException {
    parser = new StateMachineParser();
    parser.setState(new BetweenMonomerParser(parser));
    parser.doAction('}');

    if (!(parser.getState() instanceof BetweenParser)) {
      throw new SimplePolymerSectionException("");
    }
  }

  @Test(expectedExceptions = SimplePolymerSectionException.class)
  public void goToWithException() throws ExceptionState, IOException, JDOMException {
    parser = new StateMachineParser();
    parser.setState(new BetweenMonomerParser(parser));
    parser.doAction('h');
  }
}
