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
package org.helm.notation2.parser.Notation.Polymer;

import java.util.ArrayList;
import java.util.HashMap;

import org.helm.notation2.parser.ExceptionParser.NotationException;
import org.helm.notation2.parser.Notation.ValidationMethod;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

  @JsonIgnore
  HashMap<Integer, MonomerNotation> mapOfMonomers =
      new HashMap<Integer, MonomerNotation>();

  @JsonIgnore
  HashMap<String, String> mapIntraConnection = new HashMap<String, String>();;

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

  @JsonIgnore
  public MonomerNotation getMonomer(int count) {
    initializeMapOfMonomersAndMapOfIntraConnection();

    if (mapOfMonomers.containsKey(count)) {
      return mapOfMonomers.get(count);
    }
    return null;
  }

  public void initializeMapOfMonomersAndMapOfIntraConnection() {
    int value = 0;
    int lastValue = -1;
    for (int i = 0; i < elements.getListOfElements().size(); i++) {
      MonomerNotation e = elements.getListOfElements().get(i);
      if (e instanceof MonomerNotationList) {
        for (int j =
            0; j < ((MonomerNotationList) e).getListofMonomerUnits().size(); j++) {
          value += 1;
          lastValue += 1;
          mapOfMonomers.put(value, ((MonomerNotationList) e).getListofMonomerUnits().get(j));
          if (lastValue != 0) {
            mapIntraConnection.put(lastValue + "$R2", "");
            mapIntraConnection.put(value + "$R1", "");
          }
        }
      }
      else if (e instanceof MonomerNotationUnitRNA) {
        lastValue = value;
        for (int j =
            0; j < ((MonomerNotationUnitRNA) e).getContents().size(); j++) {
          value += 1;
          mapOfMonomers.put(value, ((MonomerNotationUnitRNA) e).getContents().get(j));
          }

        /* Intra nucleotide Connections will be not scanned */
        if (value >= 4) {
          mapIntraConnection.put(lastValue + "$R2", "");
          int val = lastValue + 1;
          mapIntraConnection.put(val + "$R1", "");
        }

      }
      
      else{
        value += 1;
        lastValue += 1;
        mapOfMonomers.put(value, e);
        if(lastValue != 0){
          mapIntraConnection.put(lastValue + "$R2", "");
          mapIntraConnection.put(value + "$R1", "");
        }

      }
  }
  }

  @JsonIgnore
  public HashMap<String, String> getMapIntraConnection() {
    return mapIntraConnection;
  }

  public ArrayList<MonomerNotation> getListMonomers() {
    ArrayList<MonomerNotation> items = new ArrayList<MonomerNotation>();
    for (int i = 0; i < elements.getListOfElements().size(); i++) {
      MonomerNotation mon = elements.getListOfElements().get(i);
      if (mon instanceof MonomerNotationUnit) {
        items.add(mon);
      }
 else {
        if (mon instanceof MonomerNotationGroup) {
          MonomerNotationGroup group = (MonomerNotationGroup) mon;
          for (int j = 0; j < group.getListOfElements().size(); ++j) {
            items.add(group.getListOfElements().get(j).getMonomer());
          }
        }
        if (mon instanceof MonomerNotationList) {
          MonomerNotationList list = (MonomerNotationList) mon;
          items.addAll(list.getListofMonomerUnits());
        }
      }
      }
    return items;
    }


}
