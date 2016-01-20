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
import java.util.ArrayList;
import java.util.List;

import org.helm.notation2.parser.exceptionparser.HELM1ConverterException;
import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.notation.ValidationMethod;
import org.jdom2.JDOMException;

/**
 * MonomerNotationList
 * 
 * 
 * @author hecht
 */
public class MonomerNotationList extends MonomerNotation {

  private List<MonomerNotation> list = new ArrayList<MonomerNotation>();

  /**
   * @param str
   * @param type
   * @throws JDOMException
   * @throws IOException
   * @throws NotationException
   */
  public MonomerNotationList(String str, String type) throws NotationException, IOException, JDOMException {
    super(str, type);
    setMonomerNotationUnitList(str, type);
  }

  public List<MonomerNotation> getListofMonomerUnits() {
    return list;
  }

  private void setMonomerNotationUnitList(String str, String type)
      throws NotationException, IOException, JDOMException {
    String[] items = str.split("\\.");
    for (String item : items) {
      list.add(ValidationMethod.decideWhichMonomerNotation(item, type));
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toHELM2() {
    return "(" + unit + ")";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toHELM() throws HELM1ConverterException {
    throw new HELM1ConverterException("Can't be downgraded to HELM1-Format");
  }

// public String toHELM() throws HELM1ConverterException {
// /* combine the single compounds together */
// String result = "";
// String unit = "";
// for (int i = 0; i < list.size(); i++) {
// unit += list.get(i).toHELM() + ".";
// }
//
// unit = unit.substring(0, unit.length() - 1);
//
// try {
// int integer_value = Integer.parseInt(count);
// result = unit;
// for (int i = 0; i < integer_value - 1; i++) {
// result = result + "." + unit;
// }
// return result;
// } catch (NumberFormatException e) {
// throw new HELM1ConverterException("Can't be downgraded to HELM1-Format");
// }
//
// }

}
