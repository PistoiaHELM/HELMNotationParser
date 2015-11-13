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
package org.helm.notation2.parser.GroupingSection;

import org.helm.notation2.parser.State;
import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.AnnotationSection.AnnotationsParser;
import org.helm.notation2.parser.ExceptionParser.GroupingSectionException;
import org.helm.notation2.parser.ExceptionParser.NotationException;
import org.helm.notation2.parser.Notation.Grouping.GroupingNotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GroupingParser class to parse the group id
 * 
 * @author hecht
 */
public class GroupingParser implements State {
  private StateMachineParser _parser;

  private String group_id = "";

  private static final Logger logger = LoggerFactory.getLogger(GroupingParser.class);

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
  public void doAction(Character cha) throws GroupingSectionException, NotationException {
    /* group section is finished: section was empty: annotation section starts */
    if (cha == '$') {
      /* no details */
      if (group_id == "" && _parser.notationContainer.getListOfGroupings().size() == 0) {
        logger.info("Group section is empty:");
        logger.info("Transition to annotation section:");
        _parser.setState(new AnnotationsParser(_parser));
      } else {
        throw new GroupingSectionException("Missing details about the group: " + group_id);
      }

    }

    /* detailed information about the group is starting */
    else if (cha == '(') {
      if (_parser.check_groupId(group_id)) {
        logger.info("Group ID is read:");
        _parser.notationContainer.addGrouping(new GroupingNotation(group_id));
        _parser.setState(new GroupingDetailedInformationParser(_parser));
      } else {
        logger.error("Invalid group ID: " + group_id);
        throw new GroupingSectionException("Invalid group Id: " + group_id);
      }
    }

    /* add characters to the group id */
    else {
      group_id += cha;
    }

  }
}
