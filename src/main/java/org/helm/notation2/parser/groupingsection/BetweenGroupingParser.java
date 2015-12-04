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
package org.helm.notation2.parser.groupingsection;

import org.helm.notation2.parser.State;
import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.annotationsection.AnnotationsParser;
import org.helm.notation2.parser.exceptionparser.GroupingSectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BetweenGroupingParser class to represent the interstate between a finished group and all possible states
 * 
 * @author hecht
 */
public class BetweenGroupingParser implements State {
  private StateMachineParser _parser;

  private static final Logger LOG = LoggerFactory.getLogger(BetweenGroupingParser.class);

  /**
   * Constructs with the state machine parser
   * 
   * @param pParser
   */
  public BetweenGroupingParser(StateMachineParser pParser) {
    _parser = pParser;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doAction(char cha) throws GroupingSectionException {
    /* a new group is starting */
    if (cha == '|') {
      LOG.info("A new group is starting;");
      _parser.setState(new GroupingParser(_parser));
    }

    /* group section is finished: start of the annotation section */
    else if (cha == '$') {
      LOG.info("Group section is finished:");
      LOG.info("Transition to annotation section:");
      _parser.setState(new AnnotationsParser(_parser));

    }

    /* add characters to the grouping section */
    else {
      LOG.error("Group section is not valid: " + cha);
      throw new GroupingSectionException("Group section is not valid: " + cha);
    }

  }
}
