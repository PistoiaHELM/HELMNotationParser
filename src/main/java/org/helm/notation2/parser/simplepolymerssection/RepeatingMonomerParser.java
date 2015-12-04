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
import org.helm.notation2.parser.exceptionparser.SimplePolymerSectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RepeatingMonomerParser class to parse the repeating information
 * 
 * @author hecht
 */
public class RepeatingMonomerParser implements State {

  private StateMachineParser _parser;

  private static final Logger LOG = LoggerFactory.getLogger(RepeatingMonomerParser.class);

  private String repeating = "";

  /**
   * Constructs with the state machine parser
   * 
   * @param pParser StateMachineParser
   */
  public RepeatingMonomerParser(StateMachineParser pParser) {
    _parser = pParser;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doAction(char cha) throws SimplePolymerSectionException {

    /* repeating information is finished */
    if (cha == '\'') {
      if (_parser.checkRepeating(repeating)) {
        String[] range = repeating.split("-");
        int first = Integer.parseInt(range[0]);
        if (range.length > 1) {
          int second = Integer.parseInt(range[1]);
          /* range is wrong */
          if (second - first <= 0) {
            LOG.error("Information about repeating is wrong: " + repeating);
            throw new SimplePolymerSectionException("Information about repeating is wrong: " + repeating);
          } else {
            LOG.info("Monomer unit is repeated:");
            _parser.notationContainer.getCurrentPolymer().getPolymerElements().getCurrentMonomerNotation().setCount(repeating);
            _parser.setState(new BetweenMonomerParser(_parser));
          }

        }

        else {
          LOG.info("Monomer unit is repeated:");
          _parser.notationContainer.getCurrentPolymer().getPolymerElements().getCurrentMonomerNotation().setCount(repeating);
          _parser.setState(new BetweenMonomerParser(_parser));
        }

      } else {
        LOG.error("Information about repeating is wrong: " + repeating);
        throw new SimplePolymerSectionException("Information about repeating is wrong: " + repeating);
      }
    }

    /* add characters to repeating information */
    else {
      repeating += cha;
    }
  }

}
