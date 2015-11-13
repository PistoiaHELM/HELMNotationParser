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
package org.helm.notation2.parser.SimplePolymersSection;

import org.helm.notation2.parser.State;
import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.ConnectionSection.ConnectionsParser;
import org.helm.notation2.parser.ExceptionParser.NotationException;
import org.helm.notation2.parser.ExceptionParser.SimplePolymerSectionException;
import org.helm.notation2.parser.Notation.Polymer.PolymerNotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SimplePolymersParser class to parse the simple polymer id
 * 
 * @author hecht
 */
public class SimplePolymersParser implements State {

  private String polymer_id = "";

  private StateMachineParser _parser;

  private static final Logger logger = LoggerFactory.getLogger(SimplePolymersParser.class);

  /**
   * Constructs with a state machine
   * 
   * @param pParser StateMachineParser
   */
  public SimplePolymersParser(StateMachineParser pParser) {
    _parser = pParser;
  }

  /**
   * {@inheritDoc}
   * 
   * @throws NotationException
   * @throws SimplePolymerSectionException
   */
  @Override
  public void doAction(Character cha) throws SimplePolymerSectionException, NotationException {

    /* list of simple polymers is finished */
    if (cha == '$') {
      // id has to be empty
      if (polymer_id == "") {
        if (!(_parser.isEmpty())) {
          logger.info("Simple polymer section is finished:");
          logger.info("Transition to connection section:");
          _parser.setState(new ConnectionsParser(_parser));
        }
        else{
          logger.error("Simple polymer ID has to be defined:");
          throw new SimplePolymerSectionException("A simple polymer has to be defined"); 
        }
      }

      else {
        logger.error("Incorrect input in the simple polymer section:");
        throw new SimplePolymerSectionException("Incorrect input in the simple polymer section:");
      }

    }

    /* simple polymer notation is starting */
    else if (cha == '{') {
      if ((_parser.check_polymerid(polymer_id)) && polymer_id != "") {
        logger.info("Simple polymer ID is read:");
        _parser.notationContainer.addPolymer(new PolymerNotation(polymer_id));
        _parser.addPolymer(polymer_id);
        _parser.setState(new SimplePolymersNotationParser(_parser));
      }

      else {
        logger.error("Polymer ID is not correct: " + polymer_id);
        throw new SimplePolymerSectionException("Polymer ID is not correct: " + polymer_id);
      }
    }

    /* add characters to polymer id */
    else {
      polymer_id += cha;
    }

  }

}
