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
package org.helm.notation2.parser.notation.polymer;

import java.io.IOException;

import org.helm.notation2.parser.exceptionparser.HELM1ConverterException;
import org.helm.notation2.parser.exceptionparser.NotationException;

/**
 * MonomerNotationUnit
 *
 * @author hecht
 */
public class MonomerNotationUnit extends MonomerNotation {

  public MonomerNotationUnit() {

  }

  /**
   * Constructs with the given String
   *
   * @param str Monomer
   * @throws NotationException if notation is not valid
   */
  public MonomerNotationUnit(String str, String type) throws  NotationException {
    super(str, type);
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

  @Override
  public String toHELM() throws HELM1ConverterException {

    String text = unit;

    /* check for appropriate units */
    if (!(isDefault)) {
      if (unit.length() > 1) {
        text = "(" + unit + ")";
      }
    }

    if (unit.equals("X") || unit.contains("(N)")) {
      throw new HELM1ConverterException("not possible to downgrade to HELM1 Format");
    }

    /*
     * convert the count in an appropriate way: throw an exception if it is not
     * an explicit integer value
     */
    try {
      int integerValue = Integer.parseInt(count);
      if (integerValue < 1 || integerValue > 1) {
        throw new HELM1ConverterException("Count of MonomerNotationUnit was not possible to downgrade");
      }
      // for (int i = 0; i < integer_value - 1; i++) {
      // text += "." + unit;
      // }
    } catch (NumberFormatException e) {
      throw new HELM1ConverterException("Count of MonomerNotationUnit was not possible to downgrade");
    }

    /* no annotation here!!! -> forget simple the annotation */

    return text;
  }

}
