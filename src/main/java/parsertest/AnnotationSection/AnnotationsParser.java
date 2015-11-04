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
package parsertest.AnnotationSection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import parsertest.FinalState;
import parsertest.State;
import parsertest.StateMachineParser;
import parsertest.ExceptionParser.AnnotationSectionException;
import parsertest.Notation.Annotation.AnnotationNotation;

/**
 * AnnotationsParser class to read all characters in the last section, annotation section
 * 
 * @author hecht
 */
public class AnnotationsParser implements State {

  private StateMachineParser _parser;

  private static final Logger logger = LoggerFactory.getLogger(AnnotationsParser.class);

  private String annotation = "";

  private int bracket_counter_open = 0;

  private int bracket_counter_close = 0;

  private int parenthesis_counter_open = 0;

  private int parenthesis_counter_close = 0;

  private int curly_bracket_counter_open = 0;

  private int curly_bracket_counter_close = 0;


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
  public void doAction(Character cha) throws AnnotationSectionException {
    /* list of attributes is finished */
    if (cha == '$') {
      if (check_brackets_parenthesis()) {
        logger.info("Annotation section is finished:");
        if (annotation != "") {
          _parser.notationContainer.addAnnotation(new AnnotationNotation(annotation));
        }
        logger.info("Transition to FinalState:");
        _parser.setState(new FinalState(_parser));
      }
 else {
        logger.info("Annotation section is not valid: " + annotation);
        throw new AnnotationSectionException("Annotation section is not valid: " + annotation);
      }
    }

    else {
      annotation += cha;
      if (cha == '{') {
        curly_bracket_counter_open += 1;
      }
      if (cha == '}') {
        curly_bracket_counter_close += 1;
      }
      if (cha == '[') {
        bracket_counter_open += 1;
      }
      if (cha == ']') {
        bracket_counter_close += 1;
      }
      if (cha == '(') {
        parenthesis_counter_open += 1;
      }
      if (cha == ')') {
        parenthesis_counter_close += 1;
      }
    }

  }

  /**
   * method to check if all open brackets are closed
   * 
   * @return true if all open brackets are close, false otherwise
   */
  private boolean check_brackets_parenthesis() {
    logger.debug("Check of brackets in the annotation section:");
    if (bracket_counter_open == bracket_counter_close
        && parenthesis_counter_open == parenthesis_counter_close
        && curly_bracket_counter_open == curly_bracket_counter_close) {
      return true;

    }
    return false;
  }
}
