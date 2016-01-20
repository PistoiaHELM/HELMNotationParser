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
package org.helm.notation2.parser.connectionsection;

import org.helm.notation2.parser.State;
import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.exceptionparser.ConnectionSectionException;
import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.helm.notation2.parser.notation.connection.ConnectionNotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ConnectionsReadSecondIDParser class to parse the id of the second partner of
 * the connection
 *
 * @author hecht
 */
public class ConnectionsReadSecondIDParser implements State {
  private StateMachineParser _parser;

  private String targetpolymerid = "";

  private static final Logger LOG = LoggerFactory.getLogger(ConnectionsReadSecondIDParser.class);

  /**
   * Constructs with the state machine parser
   *
   * @param pParser
   */
  public ConnectionsReadSecondIDParser(StateMachineParser pParser) {
    _parser = pParser;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doAction(char cha) throws ExceptionState {

    /*
     * third part of the connection is starting. It contains information about
     * the connection in detail
     */
    if (cha == ',') {
      if (_parser.checkPolymeridConnection(targetpolymerid)) {
        LOG.info("Target polmyer ID is read:");
        ConnectionNotation current = _parser.notationContainer.getCurrentConnection();
        _parser.notationContainer.changeConnectionNotation(new ConnectionNotation(current.getSourceId(),
            targetpolymerid));
        _parser.setState(new ConnectionsDetailsParser(_parser));
      } else {
        LOG.error("Target polymer ID is not correct in the connection section: " + targetpolymerid);
        throw new ConnectionSectionException("Target polymer ID is not correct in the connection section: "
            + targetpolymerid);
      }
    } /* Read the id of the target polymer id */ else {
      targetpolymerid += cha;
    }
  }
}
