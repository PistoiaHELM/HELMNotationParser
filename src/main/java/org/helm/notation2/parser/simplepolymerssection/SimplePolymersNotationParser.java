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

import java.io.IOException;

import org.helm.notation2.parser.InlineAnnotationsParser;
import org.helm.notation2.parser.State;
import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.exceptionparser.SimplePolymerSectionException;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SimplePolymersNotationParser class to parse the notation of an simple polymer
 * 
 * @author hecht
 */
public class SimplePolymersNotationParser implements State {
  private StateMachineParser _parser;

  private String monomer = "";

  private int bracketCounterOpen = 0;

  private int bracketCounterClose = 0;

  private int parenthesisCounterOpen = 0;

  private int parenthesisCounterClose = 0;

  private static final Logger logger = LoggerFactory.getLogger(SimplePolymersNotationParser.class);

  /**
   * Constructs with the state machine
   * 
   * @param pParser StateMachineParser
   */
  public SimplePolymersNotationParser(StateMachineParser pParser) {
    _parser = pParser;

  }

  /**
   * {@inheritDoc}
   * 
   * @throws JDOMException
   * @throws IOException
   * @throws NotationException
   */
  @Override
  public void doAction(char cha) throws SimplePolymerSectionException, NotationException, IOException, JDOMException {
    /* simple polymer notation is finished; can also be empty */
    if (cha == '}' && checkBracketsParenthesis()) {
      // initialize object - normal monomer type
      if (!(monomer.equals(""))) {
        logger.info("Monomer unit is read:");
        _parser.notationContainer.getCurrentPolymer().getPolymerElements().addMonomerNotation(monomer);
        _parser.setState(new BetweenParser(_parser));
      } else {
        throw new SimplePolymerSectionException("Monomer unit is missing: ");
      }
    }

    /* a new monomer unit is starting. This is only valid for peptide and rna not for chem and blob */
    else if (cha == '.' && checkBracketsParenthesis()) {

      if (monomer != "") {
        if (_parser.isPeptideOrRna()) {
          logger.info("Monomer unit is read: " + monomer);
          logger.info("New monomer unit is starting:");
          _parser.notationContainer.getCurrentPolymer().getPolymerElements().addMonomerNotation(monomer);
          _parser.setState(new SimplePolymersNotationParser(_parser));
        } else {
          logger.error("Only one monomer unit is allowed: " + monomer);
          throw new SimplePolymerSectionException("Only one monomer unit is allowed: " + monomer);
        }
      } else {
        logger.error("Monomer unit is missing: " + monomer);
        throw new SimplePolymerSectionException("Monomer unit is missing: " + monomer);
      }

    }

    /* an additional annotation is given */
    else if (cha == '"' && checkBracketsParenthesis()) {
      if (monomer != "") {
        logger.info("Monomer unit is read: " + monomer);
        logger.info("Annotation for monomer unit is starting:");
        _parser.notationContainer.getCurrentPolymer().getPolymerElements().addMonomerNotation(monomer);
        _parser.setState(new InlineAnnotationsParser(_parser, 11));
      } else {
        logger.error("Monomer unit is missing:");
        throw new SimplePolymerSectionException("Monomer unit is missing:");
      }
    }

    /* the monomer unit is being repeated. This is only valid for peptide and rna not for chem and blob */
    else if (cha == '\'' && checkBracketsParenthesis()) {

      if (monomer != "") {
        if (_parser.isPeptideOrRna()) {
          logger.info("Monomer unit is read: " + monomer);
          _parser.notationContainer.getCurrentPolymer().getPolymerElements().addMonomerNotation(monomer);
          _parser.setState(new RepeatingMonomerParser(_parser));
        } else {
          logger.error("Monomer unit shall be not repeated: ");
          throw new SimplePolymerSectionException("Monomer unit shall be not repeated: ");
        }
      }

      else {
        logger.error("Monomer unit is missing");
        throw new SimplePolymerSectionException("Monomer unit is missing");
      }

    }

    /* an invalid $ */
    // else if (cha == '$' && checkBracketsParenthesis()) {
    // throw new SimplePolymerSectionException("Monomer unit is missing:");
    // }

    /* check all brackets */
    else if (cha == '[' || cha == ']' || cha == '(' || cha == ')') {
      monomer += cha;
      if (cha == '[') {
        bracketCounterOpen += 1;
      }

      else if (cha == ']') {
        bracketCounterClose += 1;
      }

      else if (cha == '(') {
        parenthesisCounterOpen += 1;

      }

      else {
        parenthesisCounterClose += 1;

      }
    }

    /* add characters */
    else {
      monomer += cha;
    }

  }

  /**
   * method to check if all open brackets are closed
   * 
   * @return true if all open brackets are close, false otherwise
   */
  private boolean checkBracketsParenthesis() {
    if (bracketCounterOpen == bracketCounterClose && parenthesisCounterOpen == parenthesisCounterClose) {
      return true;
    }
    return false;
  }

}
