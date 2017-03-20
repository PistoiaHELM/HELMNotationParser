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
package org.helm.notation2.parser.notation.grouping;

import java.util.ArrayList;
import java.util.List;

import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.notation.ValidationMethod;
import org.helm.notation2.parser.notation.polymer.HELMEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * GroupingElement
 *
 * @author hecht
 */
public final class GroupingElement {

  private HELMEntity id;

  private List<Double> value = new ArrayList<Double>();

  @JsonIgnore
  private boolean isInterval = false;

  @JsonIgnore
  private boolean isDefault = true;

  public GroupingElement() {

  }

  /**
   * Constructs with the given string and the ratio
   *
   * @param str polymer ID
   * @param number value of the polymer ID
   * @param def is the value a default one
   * @throws NotationException if notation is not valid
   */
  public GroupingElement(String str, double number, boolean def) throws NotationException {
    this.id = ValidationMethod.decideWhichEntity(str);
    value.add(number);
    this.isDefault = def;
  }

  /**
   * Constructs with the given string and an interval according to two numbers
   *
   * @param str polymer ID
   * @param number1 value of the polymer ID
   * @param number2 value of the polymer ID
   * @throws NotationException if notation is not valid
   */
  public GroupingElement(String str, double number1, double number2) throws NotationException {
    this.id = ValidationMethod.decideWhichEntity(str);
    value.add(number1);
    value.add(number2);
    this.isDefault = false;
    this.isInterval = true;
  }

  /**
   * method to get the id of the grouping element
   *
   * @return polymer ID
   */
  public HELMEntity getID() {
    return id;
  }

  /**
   * method to get the the ratio or the interval in the case of a ratio only one
   * number is given, in the case of an interval two numbers are given
   *
   * @return List of values
   */
  public List<Double> getValue() {
    return value;
  }

  /**
   * method to check if the value is default
   *
   * @return true if the value is default, false otherwise
   */
  public boolean isDefaultValue() {
    return isDefault;
  }

  public boolean isInterval() {
    return this.isInterval;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    if (isInterval) {
      return "ElementID: " + id + "\nInterval: " + value.get(0) + "-" + value.get(1);
    }

    return "ElementID: " + id + "\nValue: " + value.get(0);
  }

}
