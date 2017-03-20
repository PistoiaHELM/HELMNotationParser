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

import org.helm.notation2.parser.State;
import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.connectionsection.ConnectionsParser;
import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.exceptionparser.SimplePolymerSectionException;
import org.helm.notation2.parser.notation.polymer.PolymerNotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SimplePolymersParser class to parse the simple polymer id
 *
 * @author hecht
 */
public class SimplePolymersParser implements State {

  private String polymerId = "";

  private StateMachineParser _parser;

  private static final Logger LOG = LoggerFactory.getLogger(SimplePolymersParser.class);

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
   * @throws NotationException if notation is not valid
   * @throws SimplePolymerSectionException if simple polymer section is not valid
   */
  @Override
  public void doAction(char cha) throws SimplePolymerSectionException, NotationException {

    /* list of simple polymers is finished */
    if (cha == '$') {
      // id has to be empty
      if (polymerId == "") {
        if (!(_parser.isEmpty())) {
          LOG.info("Simple polymer section is finished:");
          LOG.info("Transition to connection section:");
          _parser.setState(new ConnectionsParser(_parser));
        } else {
          LOG.error("Simple polymer ID has to be defined:");
          throw new SimplePolymerSectionException("A simple polymer has to be defined");
        }
      } else {
        LOG.error("Incorrect input in the simple polymer section:");
        throw new SimplePolymerSectionException("Incorrect input in the simple polymer section:");
      }

    } /* simple polymer notation is starting */ else if (cha == '{') {
      if ((_parser.checkPolymerId(polymerId)) && polymerId != "") {
        LOG.info("Simple polymer ID is read:");
        _parser.notationContainer.addPolymer(new PolymerNotation(polymerId));
        _parser.addPolymer(polymerId);
        _parser.setState(new SimplePolymersNotationParser(_parser));
      } else {
        LOG.error("Polymer ID is not correct: " + polymerId);
        throw new SimplePolymerSectionException("Polymer ID is not correct: " + polymerId);
      }
    } /* add characters to polymer id */ else {
      polymerId += cha;
    }

  }

}
