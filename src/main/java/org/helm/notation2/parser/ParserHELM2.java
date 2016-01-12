/**
 * ***************************************************************************** Copyright C 2015, The Pistoia Alliance
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *****************************************************************************
 */
package org.helm.notation2.parser;

import java.io.IOException;

import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.helm.notation2.parser.exceptionparser.FinalStateException;
import org.helm.notation2.parser.exceptionparser.NotValidHELM2Exception;
import org.helm.notation2.parser.notation.HELM2Notation;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ParserHELM2 class to parse strings in the HELM2 format
 * 
 * @author hecht
 */
public class ParserHELM2 {

  private StateMachineParser parser;

  private static final Logger LOG = LoggerFactory.getLogger(ParserHELM2.class);

  /**
   * method to parse the given HELM2 string in the case of an invalid HELM2 notation exception is thrown
   * 
   * @param test HELM2 Notation
   * @throws ExceptionState
   * @throws JDOMException
   * @throws IOException
   * @throws org.jdom2.JDOMException
   * 
   */
  public void parse(String test) throws ExceptionState,
      IOException, JDOMException {

    parser = new StateMachineParser();
    test = test.trim();
    if (test.substring(test.length() - 4).matches("V2\\.0") || test.substring(test.length() - 4).matches("v2\\.0")) {

      for (int i = 0; i < test.length() - 4; i++) {
        parser.doAction(test.charAt(i));
      }

      if (!(parser.getState() instanceof FinalState)) {
        LOG.error("Invalid input: Final State was not reached:");
        throw new FinalStateException("Invalid input: Final State was not reached");
      }
    } else {
      LOG.error("Invalid input: HELM2 standard is missing:");
      throw new NotValidHELM2Exception("Invalid input: HELM2 standard is missing");
    }
  }

  /**
   * method to get from the read HELM2 notation the generated objects in the JSON-Output
   * 
   * @return HELM2 in JSON-Output
   */
  public String getJSON() {
    return parser.toJSON();
  }

  /**
   * method to get from the read HELM2 notation a HELM2 notation
   * 
   * @return HELM2Notation
   */
  public HELM2Notation getHELM2Notation() {
    return parser.notationContainer;
  }

}
