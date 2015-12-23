/**
 * ***************************************************************************** Copyright C 2015, The Pistoia Alliance
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *****************************************************************************
 */
package org.helm.notation2.parser.notation.connection;

import java.io.IOException;

import org.helm.notation2.parser.exceptionparser.HELM1ConverterException;
import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.notation.ValidationMethod;
import org.helm.notation2.parser.notation.polymer.HELMEntity;
import org.jdom2.JDOMException;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ConnectionNotation class to save each connection
 * 
 * @author hecht
 */
public final class ConnectionNotation {
  private HELMEntity sourceId;

  private HELMEntity targetId;

  private String sourceUnit;

  private String targetUnit;

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
    this.sourceId = ValidationMethod.decideWhichEntity(str);
  }

  /**
   * Constructs with the given Source Entity and the given target ID
   * 
   * @param firstID Entity of the Source ID
   * @param secondID Target ID
   * @throws NotationException
   */
  public ConnectionNotation(HELMEntity firstID, String secondID) throws NotationException {
    this.sourceId = firstID;
    this.targetId = ValidationMethod.decideWhichEntity(secondID);
  }

  /**
   * Constructs with the two given Entities, Source ID and Target ID, and the given details about the connection
   * 
   * @param firstID Entity of the Source ID
   * @param secondID Entity of the Target ID
   * @param details connection details
   * @throws JDOMException
   * @throws IOException
   * @throws NotationException
   */
  public ConnectionNotation(HELMEntity firstID, HELMEntity secondID, String details) throws NotationException,
      IOException, JDOMException {
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
  public ConnectionNotation(HELMEntity firstID, HELMEntity secondID, String sourceUnit, String targetUnit,
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
   * @throws JDOMException
   * @throws IOException
   * @throws NotationException
   */
  private void addDetails(String str) throws NotationException, IOException,
      JDOMException {
    String[] parts = str.split("-");

    /* MonomerUnit */
    sourceUnit = parts[0].split(":")[0];
    targetUnit = parts[1].split(":")[0];

    /* R-group */
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
  public HELMEntity getSourceId() {
    return sourceId;
  }

  /**
   * method to get the target ID of the current connection
   * 
   * @return
   */
  public HELMEntity getTargetId() {
    return targetId;
  }

  /**
   * method to get the Source Unit of the current connection
   * 
   * @return source unit
   */
  public String getSourceUnit() {
    return sourceUnit;
  }

  /**
   * method to get the Target Unit of the current connection
   * 
   * @return target unit
   */
  public String getTargetUnit() {
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
          + rGroupSource + "\nTargetUnit: " + targetUnit + " RGroup: " + rGroupTarget + "\nAnnotation: "
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
      return sourceId.getID() + "," + targetId.getID() + "," + sourceUnit + ":"
          + rGroupSource + "-" + targetUnit + ":" + rGroupTarget + "\"" + annotation + "\"";
    } else {
      return sourceId.getID() + "," + targetId.getID() + "," + sourceUnit + ":"
          + rGroupSource + "-" + targetUnit + ":" + rGroupTarget;
    }
  }

  public String toHELM() throws HELM1ConverterException {
    String connectiondetails = sourceUnit + ":"
        + rGroupSource + "-" + targetUnit + ":" + rGroupTarget;
    String text = sourceId.getID() + "," + targetId.getID() + "," + connectiondetails;
    if (!(connectiondetails.matches("\\d:R\\d-\\d:R\\d") || connectiondetails.matches("\\d:pair-\\d:pair"))) {
      throw new HELM1ConverterException("Can't be downgraded to the HELM1-format");
    }
    if (connectiondetails.matches("\\d:pair-\\d:pair")) {
      text = "";
    }
    return text;
  }

  public String toReverseHELM() throws HELM1ConverterException {
    String connectiondetails = targetUnit + ":"
        + rGroupTarget + "-" + sourceUnit + ":" + rGroupSource;
    String text = targetId.getID() + "," + sourceId.getID() + "," + connectiondetails;
    if (!(connectiondetails.matches("\\d:R\\d-\\d:R\\d") || connectiondetails.matches("\\d:pair-\\d:pair"))) {
      throw new HELM1ConverterException("Can't be downgraded to the HELM1-format");
    }

    if (connectiondetails.matches("\\d:pair-\\d:pair")) {
      text = "";
    }

    return text;

  }

  public void setAnnotation(String notation) {
    annotation = notation;
    isAnnotationHere = true;
  }

}
