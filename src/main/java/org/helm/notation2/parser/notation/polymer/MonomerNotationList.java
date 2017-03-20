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

import org.helm.notation2.parser.InlineAnnotationsParser;
import org.helm.notation2.parser.exceptionparser.HELM1ConverterException;
import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.exceptionparser.SimplePolymerSectionException;
import org.helm.notation2.parser.notation.ValidationMethod;
import org.helm.notation2.parser.simplepolymerssection.BetweenParser;
import org.helm.notation2.parser.simplepolymerssection.RepeatingMonomerParser;
import org.helm.notation2.parser.simplepolymerssection.SimplePolymersNotationParser;
import org.jdom2.JDOMException;

/**
 * MonomerNotationList
 *
 *
 * @author hecht
 */
public class MonomerNotationList extends MonomerNotation {

  private List<MonomerNotation> list = new ArrayList<MonomerNotation>();

  private int bracketCounterOpen = 0;

  private int bracketCounterClose = 0;

  private int parenthesisCounterOpen = 0;

  private int parenthesisCounterClose = 0;

  /**
   * @param str notation
   * @param type polymer type
   * @throws NotationException if notation is not valid
   */
  public MonomerNotationList(String str, String type) throws NotationException {
    super(str, type);
    setMonomerNotationUnitList(str, type);
  }

  public List<MonomerNotation> getListofMonomerUnits() {
    return list;
  }

  private void setMonomerNotationUnitList(String str, String type)
      throws NotationException {
    /* Parsing Step is missing ! */
    List<String[]> items = parseMonomer(str);
    for (String[] item : items) {
      MonomerNotation current = ValidationMethod.decideWhichMonomerNotation(item[0], type);
      if (item[1] != "") {
        current.setCount(item[1]);
      }
      if (item[2] != "") {
        current.setAnnotation(item[2]);
      }
      list.add(current);
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

  private List<String[]> parseMonomer(String str) {
    boolean isCount = false;
    boolean isAnnotation = false;
    StringBuilder sb = new StringBuilder();
    String monomer = "";
    String count = "";
    String annotation = "";
    List<String[]> listElements = new ArrayList<String[]>();
    String[] array = new String[3];
    for (char cha : str.toCharArray()) {
      /* a new Monomer is starting */
      if (cha == '.' && checkBracketsParenthesis()) {
        if (!(isCount) && !(isAnnotation)) {
          listElements.add(new String[] {monomer, count, annotation});
          monomer = "";
          count = "";
          annotation = "";

        }

      } /* an additional annotation is given */ else if (cha == '"' && checkBracketsParenthesis()) {
        if (isAnnotation) {
          isAnnotation = false;
        } else {
          isAnnotation = true;
        }

      } else if (cha == '\'' && checkBracketsParenthesis()) {
        if (isCount) {
          isCount = false;
        } else {
          isCount = true;
        }

      } /* check all brackets */ else if (cha == '[' || cha == ']' || cha == '(' || cha == ')') {
        if (!(isCount) && !(isAnnotation)) {
          monomer += cha;
        } else if (isCount) {
          count += cha;
        } else if (isAnnotation) {
          annotation += cha;
        }
        if (cha == '[') {
          bracketCounterOpen += 1;
        } else if (cha == ']') {
          bracketCounterClose += 1;
        } else if (cha == '(') {
          parenthesisCounterOpen += 1;

        } else {
          parenthesisCounterClose += 1;

        }
      } /* add characters */ else {

        if (!(isCount) && !(isAnnotation)) {
          monomer += cha;
        } else if (isCount) {
          count += cha;
        } else if (isAnnotation) {
          annotation += cha;
        }

      }
    }
    array[0] = monomer;
    array[1] = count;
    array[2] = annotation;

    listElements.add(new String[] {monomer, count, annotation});
    return listElements;

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
