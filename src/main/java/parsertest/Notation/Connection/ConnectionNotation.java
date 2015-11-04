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
package parsertest.Notation.Connection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import parsertest.ExceptionParser.NotationException;
import parsertest.Notation.ValidationMethod;
import parsertest.Notation.Polymer.Entity;
import parsertest.Notation.Polymer.MonomerNotation;

/**
 * ConnectionNotation class to save each connection
 * 
 * @author hecht
 */
public final class ConnectionNotation {
  private Entity sourceId;

  private Entity targetId;

  private MonomerNotation sourceUnit;

  private MonomerNotation targetUnit;

  private String rGroupSource;

  private String rGroupTarget;

  private String annotation;

  private boolean isAnnotationHere = false;


  /**
   * Constructs with the given Source ID a Connection Notation
   * 
   * @param str source ID of the connection
   * @throws NotationException
   */
  public ConnectionNotation(String str) throws NotationException {
    this.sourceId = new ValidationMethod().decideWhichEntity(str);
  }

  /**
   * Constructs with the given Source Entity and the given target ID
   * 
   * @param firstID Entity of the Source ID
   * @param secondID Target ID
   * @throws NotationException
   */
  public ConnectionNotation(Entity firstID, String secondID) throws NotationException {
    this.sourceId = firstID;
    this.targetId = new ValidationMethod().decideWhichEntity(secondID);
  }

  /**
   * Constructs with the two given Entities, Source ID and Target ID, and the given details about the connection
   * 
   * @param firstID Entity of the Source ID
   * @param secondID Entity of the Target ID
   * @param details connection details
   */
  public ConnectionNotation(Entity firstID, Entity secondID, String details) {
    this.sourceId = firstID;
    this.targetId = secondID;
    addDetails(details);
  }

  /**
   * Constructs a Connection Notation with an annotation
   * 
   * @param firstID Entity of the Source ID
   * @param secondID Entity of the Target ID
   * @param sourceUnit MonomerNotation of the Source
   * @param targetUnit MonomerNotation of the Target
   * @param rGroupSource RGroup of the Source
   * @param rGroupTarget RGroup of the Target
   * @param annotation new annotation
   */
  public ConnectionNotation(Entity firstID, Entity secondID, MonomerNotation sourceUnit, MonomerNotation targetUnit,
      String rGroupSource, String rGroupTarget, String annotation) {
    this.sourceId = firstID;
    this.targetId = secondID;
    this.sourceUnit = sourceUnit;
    this.targetUnit = targetUnit;
    this.rGroupSource = rGroupSource;
    this.rGroupTarget = rGroupTarget;
    addAnnotations(annotation);
  }


  /**
   * method to set the details of the current connection
   * 
   * @param method to add connection details
   */
  private void addDetails(String str) {
    String[] parts = str.split("-");

    /* MonomerUnit */
    sourceUnit = new ValidationMethod().decideWhichMonomerNotation(parts[0].split(":")[0]);
    targetUnit = new ValidationMethod().decideWhichMonomerNotation(parts[1].split(":")[0]);
    
    
    /*R-group*/
    rGroupSource = parts[0].split(":")[1];
    rGroupTarget = parts[1].split(":")[1];
  }


  /**
   * method to set the inline annotations
   * 
   * @param str new annotation
   */
  private void addAnnotations(String str) {
    annotation = str;
    isAnnotationHere = true;
  }


  /**
   * method to check if an annotation is there or not
   * 
   * @return true if an annotation is there, false otherwise
   */
  @JsonIgnore
  public boolean isAnnotationTrue() {
    return isAnnotationHere;
  }

  /**
   * method to get the annotation of the current connection
   * 
   * @return Annotation
   */
  public String getAnnotation() {
    return annotation;
  }


  /**
   * method to get the source ID of the current connection
   * 
   * @return Source ID
   */
  public Entity getSourceId() {
    return sourceId;
  }

  /**
   * method to get the target ID of the current connection
   * 
   * @return
   */
  public Entity getTargetId() {
    return targetId;
  }

  /**
   * method to get the Source Unit of the current connection
   * 
   * @return source unit
   */
  public MonomerNotation getSourceUnit() {
    return sourceUnit;
  }

  /**
   * method to get the Target Unit of the current connection
   * 
   * @return target unit
   */
  public MonomerNotation getTargetUnit() {
    return targetUnit;
  }

  /**
   * method to get the r Group of the source in the current connection
   * 
   * @return rGroupSource
   */
  public String getrGroupSource() {
    return rGroupSource;
  }

  /**
   * method to get the r Group of the target in the current connection
   * 
   * @return rGroupTarget
   */
  public String getrGroupTarget() {
    return rGroupTarget;
  }


  /**
   * {@inheritDoc}
   */
  public String toString() {
    if (isAnnotationTrue()) {
      return "SourceID: " + sourceId + "\nTargetID: " + targetId + "\nDetails: " + "\nSourceUnit: " + sourceUnit
          + " RGroup: "
          + rGroupSource + "\nTargetUnit: " + targetUnit+ " RGroup: " + rGroupTarget + "\nAnnotation: "
          + annotation;
    } else {
      return "SourceID: " + sourceId + "\nTargetID: " + targetId + "\nDetails: " + "\nSourceUnit: "
          + sourceUnit.toString()
          + " RGroup: "
          + rGroupSource + "\nTargetUnit: " + targetUnit.toString() + " RGroup: " + rGroupTarget;
    }

  }

  /**
   * method to get a valid HELM2 of the connection notation
   * 
   * @return valid HELM2
   */
  public String toHELM2() {
    if (isAnnotationTrue()) {
      return sourceId.getID() + "," + targetId.getID() + "," + sourceUnit.toHELM2() + ":"
          + rGroupSource + "-" + targetUnit.toHELM2() + ":" + rGroupTarget + "\"" + annotation + "\"";
    } else {
      return sourceId.getID() + "," + targetId.getID() + "," + sourceUnit.toHELM2() + ":"
          + rGroupSource + "-" + targetUnit.toHELM2() + ":" + rGroupTarget;
    }
  }

}
