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
package org.helm.notation2.parser.simplepolymerssection;

import org.helm.notation2.parser.InlineAnnotationsParser;
import org.helm.notation2.parser.State;
import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.connectionsection.ConnectionsParser;
import org.helm.notation2.parser.exceptionparser.SimplePolymerSectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BetweenParser class represents an interstate between a finished simple
 * polymer description and all possible states
 *
 * @author hecht
 */
public class BetweenParser implements State {

  private StateMachineParser _parser;

  private static final Logger LOG = LoggerFactory.getLogger(BetweenParser.class);

  /**
   * Constructs with the state macchine parser
   *
   * @param pParser StateMachineParser
   */
  public BetweenParser(StateMachineParser pParser) {
    _parser = pParser;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doAction(char cha) throws SimplePolymerSectionException {

    /* an additional annotation is starting */
    if (cha == '\"') {
      LOG.info("Annotation for simple polymer is starting:");
      _parser.setState(new InlineAnnotationsParser(_parser, 1));
    } /* a new unit is starting */ else if (cha == '|') {
      LOG.info("One simple polymer is finished:");
      LOG.info("New simple polymer is starting:");
      _parser.setState(new SimplePolymersParser(_parser));
    } /* a new section is starting */ else if (cha == '$') {
      LOG.info("One simple polymer is finished:");
      LOG.info("Simple polymer section is finished:");
      LOG.info("Transition to connection section:");
      _parser.setState(new ConnectionsParser(_parser));
    } else {
      LOG.error("Invalid syntax in simple polymer section: " + cha);
      throw new SimplePolymerSectionException("Invalid syntax in simple polymer section: " + cha);
    }
  }

}
