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
import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.groupingsection.GroupingParser;
import org.helm.notation2.parser.notation.connection.ConnectionNotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ConnectionsParser class to parse the ID of the first partner of the connection
 * 
 * @author hecht
 */
public class ConnectionsParser implements State {
  private StateMachineParser _parser;

  private String sourcepolymerid = "";

  private int parenthesisCounterOpen = 0;

  private int parenthesisCounterClose = 0;

  private static final Logger LOG = LoggerFactory.getLogger(ConnectionsParser.class);

  /**
   * Constructs with the state machine parser
   * 
   * @param pParser
   */
  public ConnectionsParser(StateMachineParser pParser) {
    _parser = pParser;
  }

  /**
   * {@inheritDoc}
   * 
   * @throws NotationException
   */
  @Override
  public void doAction(char cha) throws ConnectionSectionException, NotationException {

    /* list of connections is empty */
    if (cha == '$') {
      if (sourcepolymerid == "" && _parser.notationContainer.getListOfConnections().size() == 0) {
        LOG.info("Connection section is empty:");
        LOG.info("Transition to group section:");
        _parser.setState(new GroupingParser(_parser));

      } else {
        LOG.error("Missing target polymer ID: ");
        throw new ConnectionSectionException("Missing target polymer id: ");
      }
    }
    /* target polymer ID is starting */
    else if (cha == ',' && checkBracketsParenthesis()) {
      if (_parser.checkPolymeridConnection(sourcepolymerid)) {
        LOG.info("Target polymer ID is read:");
        _parser.notationContainer.addConnection(new ConnectionNotation(sourcepolymerid));
        _parser.setState(new ConnectionsReadSecondIDParser(_parser));
      } else {
        LOG.error("Source polymer ID is not correct in the connection section: " + sourcepolymerid);
        throw new ConnectionSectionException("Source polymer id is not correct in the connection section: "
            + sourcepolymerid);
      }

    }

    else {
      sourcepolymerid += cha;
      if (cha == '(') {
        parenthesisCounterOpen += 1;
      }
      if (cha == ')') {
        parenthesisCounterClose += 1;
      }

    }
  }

  /**
   * method to check if all open brackets are closed
   * 
   * @return boolean
   */
  private boolean checkBracketsParenthesis() {
    if (parenthesisCounterOpen == parenthesisCounterClose) {
      return true;
    }
    return false;
  }
}
