/**
 * ***************************************************************************** Copyright C 2015, The Pistoia Alliance
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *****************************************************************************
 */
package org.helm.notation2.parser.annotationsection;

import org.helm.notation2.parser.FinalState;
import org.helm.notation2.parser.State;
import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.exceptionparser.AnnotationSectionException;
import org.helm.notation2.parser.notation.annotation.AnnotationNotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AnnotationsParser class to read all characters in the last section, annotation section
 * 
 * @author hecht
 */
public class AnnotationsParser implements State {

  private StateMachineParser _parser;

  private static final Logger LOG = LoggerFactory.getLogger(AnnotationsParser.class);

  private String annotation = "";

  private int bracketCounterOpen = 0;

  private int bracketCounterClose = 0;

  private int parenthesisCounterOpen = 0;

  private int parenthesisCounterClose = 0;

  private int curlyBracketCounterOpen = 0;

  private int curlyBracketCounterClose = 0;

  /**
   * Constructor with the state machine parser
   * 
   * @param pParser
   */
  public AnnotationsParser(StateMachineParser pParser) {
    _parser = pParser;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doAction(char cha) throws AnnotationSectionException {
    /* list of attributes is finished */
    if (cha == '$') {
      if (checkBracketsParenthesis()) {
        LOG.info("Annotation section is finished:");
        if (annotation != "") {
          _parser.notationContainer.addAnnotation(new AnnotationNotation(annotation));
        }
        LOG.info("Transition to FinalState:");
        _parser.setState(new FinalState());
      }
      else {
        LOG.info("Annotation section is not valid: " + annotation);
        throw new AnnotationSectionException("Annotation section is not valid: " + annotation);
      }
    }
 else if (cha == '|') {
        if (checkBracketsParenthesis()) {
          LOG.info(" new annotation is starting");
          if (annotation != "") {
            _parser.notationContainer.addAnnotation(new AnnotationNotation(annotation));
          _parser.setState(new AnnotationsParser(_parser));
          } else {
            LOG.info("Annotation section is not valid: ");
            throw new AnnotationSectionException("Annotation section is not valid: ");
          }
        }
      }
    else {
      annotation += cha;
      if (cha == '{') {
        curlyBracketCounterOpen++;
      }
      if (cha == '}') {
        curlyBracketCounterClose++;
      }
      if (cha == '[') {
        bracketCounterOpen++;
      }
      if (cha == ']') {
        bracketCounterClose++;
      }
      if (cha == '(') {
        parenthesisCounterOpen++;
      }
      if (cha == ')') {
        parenthesisCounterClose++;
      }
    }

  }

  /**
   * method to check if all open brackets are closed
   * 
   * @return true if all open brackets are close, false otherwise
   */
  private boolean checkBracketsParenthesis() {
    LOG.debug("Check of brackets in the annotation section:");
    if (bracketCounterOpen == bracketCounterClose
        && parenthesisCounterOpen == parenthesisCounterClose
        && curlyBracketCounterOpen == curlyBracketCounterClose) {
      return true;

    }
    return false;
  }
}
