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
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * MonomerNotationGroupElement
 * 
 * @author hecht
 */
public class MonomerNotationGroupElement {
  MonomerNotation monomer;

  double numberOne;

  double numberTwo;

  boolean isInterval = false;

  boolean isDefault = true;

  /**
   * Constructs with the MonomerNotation and the ratio
   * 
   * @param not MonomerNotation
   * @param one value of the MonomerNotation
   */
  public MonomerNotationGroupElement(MonomerNotation not, double one, boolean isDefault) {
    this.monomer = not;
    this.numberOne = one;
    this.isDefault = isDefault;

  }

  /**
   * Constructs with the MonomerNotation and a given interval according to two numbers
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
  public MonomerNotation getMonomer() {
    return this.monomer;
  }

  /**
   * method to get the ratio or the interval of this group, in the case of an interval it returns a list of two values
   * 
   * @return List of the values
   */
  public ArrayList<Double> getValue() {
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
    return monomer.getID();
  }

}
