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

import com.fasterxml.jackson.annotation.JsonIgnore;

import parsertest.ExceptionParser.NotationException;
import parsertest.Notation.ValidationMethod;

/**
 * PolymerNotation class to represent a polymer with its content
 * 
 * @author hecht
 */
public final class PolymerNotation {

  PolymerEntity polymerID;

  String annotation;

  boolean isAnnotationHere = false;

  PolymerElements elements;

  /**
   * Constructs with the given String
   * 
   * @param str polymer ID
   * @throws NotationException
   */
  public PolymerNotation(String str) throws NotationException {

    polymerID = (PolymerEntity) new ValidationMethod().decideWhichEntity(str);
    decideWhichPolymerElements();


  }

  /**
   * Constructs with a given PolymerEnttiy, PolymerElements and an annotation
   * 
   * @param poly PolymerEntity
   * @param ele PolymerElements
   * @param annotation new annotation
   */
  public PolymerNotation(PolymerEntity poly, PolymerElements ele, String annotation) {
    this.polymerID = poly;
    this.elements = ele;
    setAnnotation(annotation);
  }

  /**
   * method to generate the right PolymerElements, in the case of Chem and Blob only one Monomer is allowed
   */
  private void decideWhichPolymerElements() {
    if (polymerID instanceof RNAEntity || polymerID instanceof PeptideEntity) {
      this.elements = new PolymerListElements(polymerID);
    } else {
      this.elements = new PolymerSingleElements(polymerID);
    }

  }
  
  /**
   * method to add/set the annotation
   * 
   * @param str new annotation
   */
  private void setAnnotation(String str) {
    this.annotation = str;
    this.isAnnotationHere = true;
  }

  /**
   * method to get the polymer entity
   * 
   * @return polymer ID
   */
  public PolymerEntity getPolymerID() {
    return polymerID;
  }

  /**
   * method to get the PolymerElements
   * 
   * @return PolymerElements
   */
  public PolymerElements getPolymerElements() {
    return elements;
  }

  /**
   * method to get the annotation of the simple polymer
   * 
   * @return annotation
   */
  public String getAnnotation() {
    return annotation;
  }

  /**
   * method to check if an annotation is there
   * 
   * @return true if the annotation is there, false otherwise
   */
  @JsonIgnore
  public boolean isAnnotationTrue() {
    return isAnnotationHere;
  }

  /**
   * {@inheritDoc}
   */
  public String toString() {
    if (isAnnotationTrue()) {
      return "PolymerID: " + polymerID + "\nElements: " + elements.toString() + "Annotation: " + annotation;
    } else {
      return "PolymerID: " + polymerID + "\nElements: " + elements.toString();
    }

  }



  /**
   * method to generate a valid HELM2 notation for this object
   * 
   * @return valid HELM2 notation
   */
  public String toHELM2() {
    return elements.toHELM2();
  }

}
