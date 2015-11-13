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
package parsertest.Notation.Polymer;

import java.io.IOException;

import org.helm.notation.MonomerException;
import org.helm.notation.MonomerFactory;
import org.helm.notation.MonomerStore;
import org.jdom2.JDOMException;

import parsertest.ExceptionParser.NotationException;
import parsertest.Notation.ValidationMethod;

/**
 * MonomerNotationUnit
 * 
 * @author hecht
 */
public class MonomerNotationUnit extends MonomerNotation {

  /**
   * Constructs with the given String
   * 
   * @param str Monomer
   * @throws JDOMException
   * @throws IOException
   * @throws MonomerException
   * @throws NotationException
   * @throws org.helm.notation.NotationException
   */
  public MonomerNotationUnit(String str, String type) throws MonomerException,
      IOException, JDOMException, NotationException,
      org.helm.notation.NotationException {
    super(str, type);

    /* Validation of Monomer */
    if (new ValidationMethod().isMonomerValid(str, type)) {

    } else {
      throw new NotationException("Monomer was not valid: " + str);
    }

  }



  /**
   * {@inheritDoc}
   */
  @Override
  public String toHELM2() {

    String text = unit;
    if (isDefault == false) {
      if (unit.length() > 1) {
        text = "(" + unit + ")";
      }
      text += "'" + count + "'";
    }

    if (isAnnotationHere) {
      text += "\"" + annotation + "\"";
    }
    return text;
  }


}
