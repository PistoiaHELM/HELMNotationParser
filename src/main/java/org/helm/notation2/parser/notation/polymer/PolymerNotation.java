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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.helm.notation2.parser.exceptionparser.HELM1ConverterException;
import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.notation.ValidationMethod;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * PolymerNotation class to represent a polymer with its content
 *
 * @author hecht
 */
public final class PolymerNotation {

  private PolymerEntity polymerID;

  private String annotation;

  private boolean isAnnotationHere = false;

  private PolymerElements elements;

  @JsonIgnore
  private Map<Integer, MonomerNotation> mapOfMonomers = new HashMap<Integer, MonomerNotation>();

  @JsonIgnore
  private Map<String, String> mapIntraConnection = new HashMap<String, String>();;

  /**
   * Constructs with the given String
   *
   * @param str polymer ID
   * @throws NotationException
   */
  public PolymerNotation(String str) throws NotationException {
    polymerID = (PolymerEntity) ValidationMethod.decideWhichEntity(str);
    decideWhichPolymerElements();
  }

  /**
   * Constructs with a given PolymerEnttiy, PolymerElements
   *
   * @param poly PolymerEntity
   * @param ele PolymerElements
   */
  public PolymerNotation(PolymerEntity poly, PolymerElements ele) throws NotationException {
    this.polymerID = poly;
    this.elements = ele;
  }

  /**
   * Constructs with a given PolymerEnttiy, PolymerElements and an annotation
   *
   * @param poly PolymerEntity
   * @param ele PolymerElements
   * @param anno new annotation
   */
  public PolymerNotation(PolymerEntity poly, PolymerElements ele, String anno) {
    this.polymerID = poly;
    this.elements = ele;
    if (anno != null) {
      setAnnotation(anno);
    }
  }

  /**
   * method to generate the right PolymerElements, in the case of Chem and Blob
   * only one Monomer is allowed
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
    return this.polymerID;
  }

  /**
   * method to get the PolymerElements
   *
   * @return PolymerElements
   */
  public PolymerElements getPolymerElements() {
    return this.elements;
  }

  /**
   * method to get the annotation of the simple polymer
   *
   * @return annotation
   */
  public String getAnnotation() {
    return this.annotation;
  }

  /**
   * method to check if an annotation is there
   *
   * @return true if the annotation is there, false otherwise
   */
  @JsonIgnore
  public boolean isAnnotationTrue() {
    return this.isAnnotationHere;
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
    return this.elements.toHELM2();
  }

  /**
   * method to generate a valid HELM notation for this object
   *
   * @return
   * @throws HELM1ConverterException
   */
  public String toHELM() throws HELM1ConverterException {
    if (polymerID instanceof BlobEntity) {
      throw new HELM1ConverterException("Can't be downgraded to HELM1-Format");
    }
    return this.elements.toHELM();
  }

  @JsonIgnore
  public MonomerNotation getMonomerNotation(int count) {
    initializeMapOfMonomersAndMapOfIntraConnection();

    if (mapOfMonomers.containsKey(count)) {
      return mapOfMonomers.get(count);
    }
    return null;
  }

  public void initializeMapOfMonomersAndMapOfIntraConnection() {
    int multiply = 1;
    int value = 0;
    int lastValue = -1;
    for (MonomerNotation element : elements.getListOfElements()) {
      try {
        // multiply = Integer.parseInt(element.getCount());
        multiply = 1;
        if (multiply < 1) {
          multiply = 1;
        }
      } catch (NumberFormatException ex) {
        multiply = 1;
      }

// if (element instanceof MonomerNotationList) {
// for (int z = 0; z < multiply; z++) {
// for (MonomerNotation monomerNotationUnit : ((MonomerNotationList)
// element).getListofMonomerUnits()) {
// value++;
// lastValue++;
// mapOfMonomers.put(value, monomerNotationUnit);
// if (lastValue != 0) {
// mapIntraConnection.put(lastValue + "$R2", "");
// mapIntraConnection.put(value + "$R1", "");
// }
// }
// }
// }
      if (element instanceof MonomerNotationUnitRNA) {
        for (int z = 0; z < multiply; z++) {
          lastValue = value;
          for (MonomerNotationUnit monomerNotationUnit : ((MonomerNotationUnitRNA) element).getContents()) {
            value++;
            mapOfMonomers.put(value, monomerNotationUnit);
          }

          /* Intra nucleotide Connections will be not scanned */
          if (value >= 4) {
            mapIntraConnection.put(lastValue + "$R2", "");
            int val = lastValue + 1;
            mapIntraConnection.put(val + "$R1", "");
          }
        }

      } else {
        for (int z = 0; z < multiply; z++) {
          value++;
          lastValue++;
          mapOfMonomers.put(value, element);
          if (lastValue != 0) {
            mapIntraConnection.put(lastValue + "$R2", "");
            mapIntraConnection.put(value + "$R1", "");
          }
        }

      }
    }
  }

  @JsonIgnore
  public Map<String, String> getMapIntraConnection() {
    return mapIntraConnection;
  }

  @JsonIgnore
  public List<MonomerNotation> getListMonomers() {
    List<MonomerNotation> listMonomerNotation = new ArrayList<MonomerNotation>();
    for (MonomerNotation monomerNotation : elements.getListOfElements()) {
      if (monomerNotation instanceof MonomerNotationUnit) {
        listMonomerNotation.add(monomerNotation);
      } else {
        if (monomerNotation instanceof MonomerNotationGroup) {
          for (MonomerNotationGroupElement groupElement : ((MonomerNotationGroup) monomerNotation).getListOfElements()) {
            listMonomerNotation.add(groupElement.getMonomerNotation());
          }
        }
        if (monomerNotation instanceof MonomerNotationList) {
          listMonomerNotation.addAll(((MonomerNotationList) monomerNotation).getListofMonomerUnits());
        }
      }
    }
    return listMonomerNotation;
  }

}
