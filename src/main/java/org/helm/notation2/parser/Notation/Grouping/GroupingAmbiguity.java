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
package org.helm.notation2.parser.Notation.Grouping;

import java.util.ArrayList;

/**
 * GroupingAmbiguity represents a single group in the grouping section
 * 
 * @author hecht
 */
public abstract class GroupingAmbiguity {

  ArrayList<GroupingElement> elements = new ArrayList<GroupingElement>();

  /**
   * method to add a element to the group
   * 
   * @param e new element of the group
   */
  public void addElement(GroupingElement e) {
    elements.add(e);
  }

  /**
   * method to get all elements of this group
   * 
   * @return list of all elements in the group
   */
  public ArrayList<GroupingElement> getListOfElements() {
    return elements;
  }

  /**
   * {@inheritDoc}
   */
  public String toString() {
    return elements.toString();
  }

  /**
   * method to get a valid HELM2 notation of the group
   * 
   * @return valid HELM2
   */
  public abstract String toHELM2();

}
