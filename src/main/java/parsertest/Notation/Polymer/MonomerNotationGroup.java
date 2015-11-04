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

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import parsertest.Notation.ValidationMethod;

/**
 * MonomerNotationGroup
 * 
 * @author hecht
 */
public abstract class MonomerNotationGroup extends MonomerNotation {

  protected ArrayList<MonomerNotationGroupElement> elements = new ArrayList<MonomerNotationGroupElement>();


  /**
   * Constructs with the given string
   * 
   * @param str Monomer
   */
  public MonomerNotationGroup(String str) {
    super(str);
  }

  /**
   * method to add a single element to the group
   * 
   * @param str Monomer
   * @param one value of the monomer
   * @param two value of the monomer
   * @param interval is the value on interval
   */
  public void addElement(String str, double one, double two, boolean interval, boolean isDefault) {

    elements.add(new ValidationMethod().decideWhichMonomerNotationInGroup(str, one, two, interval, isDefault));

  }



  /**
   * method to get the current group element
   * 
   * @return MonomerNotationGroupElement
   */
  @JsonIgnore
  public MonomerNotationGroupElement getCurrentElement() {
    return elements.get(elements.size() - 1);
  }

  /**
   * method to get a list of all group elements
   * 
   * @return List of all group elements
   */
  public ArrayList<MonomerNotationGroupElement> getListOfElements() {
    return elements;
  }

  /**
   * {@inheritDoc}
   */
  public String toString() {
    if (isAnnotationTrue()) {
    return "Grouping section:\n " + elements.toString() + "\nCount: " + count + "\nAnnotation: " + annotation;
    } else {
      return "Grouping section:\n " + elements.toString() + "\nCount: " + count;
    }
  }



}
