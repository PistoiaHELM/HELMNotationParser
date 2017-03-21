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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * MonomerNotationGroup
 *
 * @author hecht
 */

public abstract class MonomerNotationGroup extends MonomerNotation {
  @JsonIgnore
  public List<MonomerNotationGroupElement> elements = new ArrayList<MonomerNotationGroupElement>();

  /**
   * Constructs with the given string
   *
   * @param str Monomer in string format
   * @param type polymer type of monomer
   */
  public MonomerNotationGroup(String str, String type) {
    super(str, type);
  }

  /**
   * method to add a single element to the group
   *
   * @param str Monomer
   * @param type type of current polymer
   * @param one value of the monomer
   * @param two value of the monomer
   * @param interval is the value on interval
   * @param isDefault if the value is the default one
   * @throws NotationException if notation is not valid
   */
  public void addElement(String str, String type, double one, double two, boolean interval, boolean isDefault) throws NotationException{
    this.elements.add(ValidationMethod.decideWhichMonomerNotationInGroup(str, type, one, two, interval, isDefault));

  }

  /**
   * method to get the current group element
   *
   * @return MonomerNotationGroupElement
   */
  @JsonIgnore
  public MonomerNotationGroupElement getCurrentElement() {
    return this.elements.get(this.elements.size() - 1);
  }

  /**
   * method to get a list of all group elements
   *
   * @return List of all group elements
   */
  public List<MonomerNotationGroupElement> getListOfElements() {
    return this.elements;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    if (isAnnotationTrue()) {
      return "Grouping section:\n " + elements.toString() + "\nCount: " + count + "\nAnnotation: " + annotation;
    } else {
      return "Grouping section:\n " + elements.toString() + "\nCount: " + count;
    }
  }

  @Override
  public String toHELM() throws HELM1ConverterException {
    throw new HELM1ConverterException("Can't be downcasted to HELM1-Format due to a group element");
  }

}
