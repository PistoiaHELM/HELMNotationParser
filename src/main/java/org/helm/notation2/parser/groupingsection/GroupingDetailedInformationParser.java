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
import org.helm.notation2.parser.exceptionparser.GroupingSectionException;
import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.notation.grouping.GroupingNotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GroupingDetailedInformationParser class to parse detailed information about
 * the group
 *
 * @author hecht
 */
public class GroupingDetailedInformationParser implements State {
  private StateMachineParser _parser;

  private String details = "";

  private static final Logger LOG = LoggerFactory.getLogger(GroupingDetailedInformationParser.class);

  /**
   * Constructs with the state machine parser
   *
   * @param pParser current parser
   */
  public GroupingDetailedInformationParser(StateMachineParser pParser) {
    _parser = pParser;
  }

  /**
   * {@inheritDoc}
   *
   * @throws NotationException if notation is not valid
   */
  @Override
  public void doAction(char cha) throws GroupingSectionException, NotationException {
    /*
     * detailed information about the group is finished: annotation section
     * starts
     */
    if (cha == ')') {
      if (_parser.checkDetailsGroup(details)) {
        LOG.info("Group description is finished:");
        LOG.info("Transition to annotation section:");
        GroupingNotation current = _parser.notationContainer.getCurrentGroupingNotation();
        _parser.notationContainer.changeLastGroupingNotation(new GroupingNotation(current.getGroupID(), details));
        _parser.setState(new BetweenGroupingParser(_parser));
      } else {
        LOG.error("Group information is wrong: " + details);
        throw new GroupingSectionException("Group information is wrong: " + details);
      }
    } /* add characters to the details */ else {
      details += cha;
    }

  }

}
