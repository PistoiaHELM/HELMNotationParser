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
package parsertest.SimplePolymersSection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import parsertest.State;
import parsertest.StateMachineParser;
import parsertest.ExceptionParser.ExceptionState;
import parsertest.ExceptionParser.SimplePolymerSectionException;

/**
 * BetweenInlineMonomerParser class to represent the state between a finished monomer and all possible states
 * 
 * @author hecht
 */
public class BetweenInlineMonomerParser implements State {
  StateMachineParser _parser;

  private static final Logger logger = LoggerFactory.getLogger(BetweenMonomerParser.class);

  /**
   * Constructs with the state machine parser
   * 
   * @param pParser StateMachineParser
   */
  public BetweenInlineMonomerParser(StateMachineParser pParser) {
    _parser = pParser;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doAction(Character cha) throws ExceptionState {
    /* a new monomer section starts */
    if (cha == '.') {
      logger.info("monomer unit is finished:");
      logger.info("new monomer unit is starting:");
      _parser.setState(new SimplePolymersNotationParser(_parser));
    }
    /* polymer is finished */
    else if (cha == '}') {
      logger.info("simple polymer is finished");
      _parser.setState(new BetweenParser(_parser));
    }

    else {
      logger.error("Error in the simple polymers notation section");
      throw new SimplePolymerSectionException("Error in the simple polymers notation section");
    }

  }


}
