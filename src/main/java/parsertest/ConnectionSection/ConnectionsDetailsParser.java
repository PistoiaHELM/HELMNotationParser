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
package parsertest.ConnectionSection;

import java.io.IOException;

import org.helm.notation.MonomerException;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import parsertest.InlineAnnotationsParser;
import parsertest.State;
import parsertest.StateMachineParser;
import parsertest.ExceptionParser.ConnectionSectionException;
import parsertest.ExceptionParser.NotationException;
import parsertest.GroupingSection.GroupingParser;
import parsertest.Notation.Connection.ConnectionNotation;

/**
 * ConnectionsDetailsParser class to parse the details about the connection
 * 
 * @author hecht
 */
public class ConnectionsDetailsParser implements State {
  private StateMachineParser _parser;

  private static final Logger logger = LoggerFactory.getLogger(BetweenInlineConnectionParser.class);

  private String details = "";

  /**
   * Constructs with the state machine parser
   * 
   * @param pParser
   */
  public ConnectionsDetailsParser(StateMachineParser pParser) {
    _parser = pParser;
  }

  /**
   * {@inheritDoc}
   * 
   * @throws NotationException
   * @throws JDOMException
   * @throws IOException
   * @throws MonomerException
   * @throws org.jdom2.JDOMException
   * @throws org.helm.notation.NotationException
   */
  @Override
  public void doAction(Character cha) throws ConnectionSectionException,
      NotationException, MonomerException, IOException, JDOMException,
      org.jdom2.JDOMException, org.helm.notation.NotationException {
    /* a new connection is starting */
    if (cha == '|') {
      if (_parser.check_detailsconnections(details))
        try {
          {
            logger.info("A new connection is starting:");
            ConnectionNotation current =
                _parser.notationContainer.getCurrentConnection();
            _parser.notationContainer.changeConnectionNotation(new ConnectionNotation(
                current.getSourceId(),
                current.getTargetId(), details));
            ;
            _parser.setState(new ConnectionsParser(_parser));
          }
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      else {
        logger.error("Details about the connection are not corret: " + details);
        throw new ConnectionSectionException("Details about the connection are not correct: " + details);
      }

    }

    /* connection section is finished */
    else if (cha == '$') {
      if (_parser.check_detailsconnections(details))
        try {
          {
            logger.info("Connection section is finished:");
            ConnectionNotation current =
                _parser.notationContainer.getCurrentConnection();
            _parser.notationContainer.changeConnectionNotation(new ConnectionNotation(
                current.getSourceId(),
                current.getTargetId(), details));
            ;
            logger.info("Transition to group section");
            _parser.setState(new GroupingParser(_parser));
          }
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      else {
        throw new ConnectionSectionException("Details about the connection are not correct: " + details);
      }
    }

    /* start of an annotation */
    else if (cha == '\"') {
      if (_parser.check_detailsconnections(details))
        try {
          {
            logger.info("Add annotation to connection:");
            ConnectionNotation current =
                _parser.notationContainer.getCurrentConnection();
            _parser.notationContainer.changeConnectionNotation(new ConnectionNotation(
                current.getSourceId(),
                current.getTargetId(), details));
            ;
            _parser.setState(new InlineAnnotationsParser(_parser, 2));
          }
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      else {
        logger.error("Details about the connection are not corret: " + details);
        throw new ConnectionSectionException("Details about the connection are not correct: " + details);
      }

    }

    /* add characters to connection description */
    else {
      details += cha;
    }
  }

}
