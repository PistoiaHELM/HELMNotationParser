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
package org.helm.notation2.parser;

import org.helm.notation2.parser.connectionsection.BetweenInlineConnectionParser;
import org.helm.notation2.parser.notation.connection.ConnectionNotation;
import org.helm.notation2.parser.notation.polymer.PolymerNotation;
import org.helm.notation2.parser.simplepolymerssection.BetweenInlineMonomerParser;
import org.helm.notation2.parser.simplepolymerssection.BetweenParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * InlineAnnotationsParser class to read/catch the inline annotation
 * 
 * @author hecht
 */
public class InlineAnnotationsParser implements State {

  private StateMachineParser _parser;

  private int sectionCounter; // information about the next state after the annotation

  private static final Logger LOG = LoggerFactory.getLogger(InlineAnnotationsParser.class);

  private String comment = "";

  /**
   * Constructor with the state machine parser, an information about the next state
   * 
   * @param pParser StateMachineParser
   * @param counter information about the following state
   */
  public InlineAnnotationsParser(StateMachineParser pParser, int counter) {
    this._parser = pParser;
    this.sectionCounter = counter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doAction(char cha) {

    if (cha == '\"') {

      /* annotation for first section:simple polymer section */
      if (sectionCounter == 1) {
        LOG.info("Add annotation to simple polymer:");
        PolymerNotation current = _parser.notationContainer.getCurrentPolymer();
        _parser.notationContainer.changeLastPolymerNotation(new PolymerNotation(current.getPolymerID(),
            current.getPolymerElements(), comment));
        _parser.setState(new BetweenParser(_parser));
      }

      /* annotation for second section:connection section */
      else if (sectionCounter == 2) {
        LOG.info("Add annotation to connection section:");
        ConnectionNotation current = _parser.notationContainer.getCurrentConnection();
        _parser.notationContainer.changeConnectionNotation(new ConnectionNotation(current.getSourceId(),
            current.getTargetId(), current.getSourceUnit(), current.getTargetUnit(), current.getrGroupSource(),
            current.getrGroupTarget(), comment));
        _parser.setState(new BetweenInlineConnectionParser(_parser));
      }

      /* annotation for a single monomer in the first section */
      else if (sectionCounter == 11) {
        LOG.info("Add annotation to a single monomer:");
        _parser.notationContainer.getCurrentPolymer().getPolymerElements().getCurrentMonomerNotation().setAnnotation(comment);
        _parser.setState(new BetweenInlineMonomerParser(_parser));
      }

    }

    else {
      comment += (cha);
    }
  }

}
