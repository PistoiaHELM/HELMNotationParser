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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * MonomerNotationGroupElement
 *
 * @author hecht
 */
public class MonomerNotationGroupElement {
  private MonomerNotation monomer;

  private double numberOne;

  private double numberTwo;

  private boolean isInterval = false;

  private boolean isDefault = true;

  /**
   * Constructs with the MonomerNotation and the ratio
   *
   * @param not MonomerNotation
   * @param one value of the MonomerNotation
   * @param defaultValue if the value is a default value 
   */
  public MonomerNotationGroupElement(MonomerNotation not, double one, boolean defaultValue) {
    this.monomer = not;
    this.numberOne = one;
    this.isDefault = defaultValue;

  }

  /**
   * Constructs with the MonomerNotation and a given interval according to two
   * numbers
   *
   * @param not MonomerNotation
   * @param one value of the MonomerNotation
   * @param two value of the MonomerNotation
   */
  public MonomerNotationGroupElement(MonomerNotation not, double one, double two) {
    this.monomer = not;
    this.numberOne = one;
    this.numberTwo = two;
    this.isInterval = true;
    this.isDefault = false;

  }

  /**
   * method the get the monomer
   *
   * @return MonomerNotation
   */
  public MonomerNotation getMonomerNotation() {
    return this.monomer;
  }

  public void setMonomerNotation(MonomerNotation mon) {
    this.monomer = mon;
  }

  /**
   * method to get the ratio or the interval of this group, in the case of an
   * interval it returns a list of two values
   *
   * @return List of the values
   */
  public List<Double> getValue() {
    if (this.isInterval) {
      return new ArrayList<Double>(Arrays.asList(numberOne, numberTwo));
    } else {
      return new ArrayList<Double>(Arrays.asList(numberOne));
    }

  }

  /**
   * method to check if the interval is there or not
   *
   * @return true if the interval is there, false otherwise
   */
  @JsonIgnore
  public boolean isInterval() {

    return isInterval;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    if (isInterval) {
      return "ElementID: " + monomer.toString() + "\nIntervall: " + numberOne + "-" + numberTwo;
    }

    return "ElementID: " + monomer.toString() + "\nValue: " + numberOne;
  }

  /**
   * method to check if the value is a default one
   *
   * @return true if the value is default, false otherwise
   */
  @JsonIgnore
  public boolean isDefaultValue() {

    return isDefault;
  }

  /**
   * method to generate a valid HELM2 notation for this object
   *
   * @return valid HELM2 notation
   */
  public String toHELM2() {
    return monomer.getUnit();
  }

}
