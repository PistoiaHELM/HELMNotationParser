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
import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.notation.grouping.GroupingNotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GroupingParser class to parse the group id
 *
 * @author hecht
 */
public class GroupingParser implements State {
  private StateMachineParser _parser;

  private String groupId = "";

  private static final Logger LOG = LoggerFactory.getLogger(GroupingParser.class);

  /**
   * Constructs with the state machine parser
   *
   * @param pParser
   */
  public GroupingParser(StateMachineParser pParser) {
    _parser = pParser;
  }

  /**
   * {@inheritDoc}
   *
   * @throws NotationException
   */
  @Override
  public void doAction(char cha) throws GroupingSectionException, NotationException {
    /*
     * group section is finished: section was empty: annotation section starts
     */
    if (cha == '$') {
      /* no details */
      if (groupId == "" && _parser.notationContainer.getListOfGroupings().size() == 0) {
        LOG.info("Group section is empty:");
        LOG.info("Transition to annotation section:");
        _parser.setState(new AnnotationsParser(_parser));
      } else {
        throw new GroupingSectionException("Missing details about the group: " + groupId);
      }

    } /* detailed information about the group is starting */ else if (cha == '(') {
      if (_parser.checkGroupId(groupId)) {
        LOG.info("Group ID is read:");
        _parser.notationContainer.addGrouping(new GroupingNotation(groupId));
        _parser.setState(new GroupingDetailedInformationParser(_parser));
      } else {
        LOG.error("Invalid group ID: " + groupId);
        throw new GroupingSectionException("Invalid group Id: " + groupId);
      }
    } /* add characters to the group id */ else {
      groupId += cha;
    }

  }
}
