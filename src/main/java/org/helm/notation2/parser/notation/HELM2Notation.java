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
package org.helm.notation2.parser.notation;

import java.util.ArrayList;
import java.util.List;

import org.helm.notation2.parser.notation.annotation.AnnotationNotation;
import org.helm.notation2.parser.notation.connection.ConnectionNotation;
import org.helm.notation2.parser.notation.grouping.GroupingNotation;
import org.helm.notation2.parser.notation.polymer.PolymerNotation;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * HELM2Notation this class contains all notation objects for HELM2
 *
 * @author hecht
 *
 */
public class HELM2Notation {

  private List<PolymerNotation> listOfPolymers = new ArrayList<PolymerNotation>();

  private List<ConnectionNotation> listOfConnections = new ArrayList<ConnectionNotation>();

  private List<GroupingNotation> listOfGroupings = new ArrayList<GroupingNotation>();

  private List<AnnotationNotation> annotationSection = new ArrayList<AnnotationNotation>();

  /**
   * method to add the notation of a simple polymer type
   *
   * @param pol new polymer notation
   */
  @JsonIgnore
  public void addPolymer(PolymerNotation pol) {
    listOfPolymers.add(pol);
  }

  /**
   * method to get the current simple polymer type
   *
   * @return current polymer notation
   */
  @JsonIgnore
  public PolymerNotation getCurrentPolymer() {
    return listOfPolymers.get(listOfPolymers.size() - 1);
  }

  /**
   * method to get all simple polymer types
   *
   * @return all simple polymer types in a List
   */
  public List<PolymerNotation> getListOfPolymers() {
    return listOfPolymers;
  }

  /**
   * method to get the simple polymer type
   * @param string 
   * @return
   */
  public PolymerNotation getSimplePolymer(String string) {
    for (PolymerNotation polymer : listOfPolymers) {
      if (polymer.getPolymerID().getId().equals(string)) {
        return polymer;
      }
    }
    return null;
  }

  /**
   * method to change the last polymer notation
   *
   * @param not New PolymerNotation
   */
  public void changeLastPolymerNotation(PolymerNotation not) {
    listOfPolymers.set(listOfPolymers.size() - 1, not);
  }

  /**
   * method to add a connection
   *
   * @param con new connection notation
   */
  public void addConnection(ConnectionNotation con) {

    listOfConnections.add(con);
  }

  /**
   * method to get the current connection
   *
   * @return the current connection notation
   */
  @JsonIgnore
  public ConnectionNotation getCurrentConnection() {
    return listOfConnections.get(listOfConnections.size() - 1);
  }

  /**
   * method to change the last connection notation
   *
   * @param not new connection notation
   */
  public void changeConnectionNotation(ConnectionNotation not) {
    listOfConnections.set(listOfConnections.size() - 1, not);
  }

  /**
   * method to get a list of all connections
   *
   * @return all connection notations in a list
   */
  public List<ConnectionNotation> getListOfConnections() {
    return listOfConnections;
  }

  /**
   * method to add a grouping notation
   *
   * @param not new grouping notation
   */
  public void addGrouping(GroupingNotation not) {
    listOfGroupings.add(not);
  }

  /**
   * method to get the current grouping notation
   *
   * @return GroupingNotation
   */
  @JsonIgnore
  public GroupingNotation getCurrentGroupingNotation() {
    if (listOfGroupings.size() == 0) {
      return null;
    }
    return listOfGroupings.get(listOfGroupings.size() - 1);
  }

  /**
   * method to get all grouping notations
   *
   * @return all grouping notations in a list
   */
  public List<GroupingNotation> getListOfGroupings() {
    return listOfGroupings;
  }

  /**
   * method to change the current grouping notation
   *
   * @param not new grouping notation
   */
  public void changeLastGroupingNotation(GroupingNotation not) {
    listOfGroupings.set(listOfGroupings.size() - 1, not);
  }

  /**
   * method to set the annotation
   *
   * @param anno
   */
  public void addAnnotation(AnnotationNotation anno) {
    annotationSection.add(anno);
  }

  /**
   * method to get the annotation
   *
   * @return annotation
   */
  public List<AnnotationNotation> getListOfAnnotations() {
    return annotationSection;
  }

  @JsonIgnore
  public AnnotationNotation getCurrentAnnotation() {
    return annotationSection.get(annotationSection.size() - 1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    String output = "PolymerSection: " + listOfPolymers.toString() + "\n";
    if (!(listOfConnections.isEmpty())) {
      output += "ConnectionSection: " + listOfConnections.toString() + "\n";
    }

    if (!(listOfGroupings.isEmpty())) {
      output += "GroupingSection: " + listOfGroupings.toString() + "\n";
    }

    if (!(annotationSection.isEmpty())) {
      output += "AnnotationSection: " + annotationSection.toString() + "\n";
    }

    return output;
  }

  /**
   * method to generate for all sections a HELM2 string
   *
   * @return HELM2
   */
  public String toHELM2() {
    String output = "";
    /* first section: simple polymer section */
    output += polymerToHELM2() + "$";

    /* second section: connection section */
    output += connectionToHELM2() + "$";

    /* third section: grouping section */
    output += groupingToHELM2() + "$";

    /* fourth section: annotation section */
    output += annotationToHELM2() + "$";

    /* add version number */
    output += "V2.0";

    return output;
  }

  /**
   * method to generate a valid HELM2 string for the first section
   *
   * @return valid HELM2 string
   */
  public String polymerToHELM2() {
    StringBuilder notation = new StringBuilder();
    for (int i = 0; i < listOfPolymers.size(); i++) {
      if (listOfPolymers.get(i).isAnnotationHere()) {
        notation.append(listOfPolymers.get(i).getPolymerID() + "{" + listOfPolymers.get(i).toHELM2() + "}\""
            + listOfPolymers.get(i).getAnnotation() + "\"|");
      } else {
        notation.append(listOfPolymers.get(i).getPolymerID() + "{" + listOfPolymers.get(i).toHELM2() + "}" + "|");
      }
    }

    notation.setLength(notation.length() - 1);
    return notation.toString();
  }

  /**
   * method to generate a valid HELM2 string for the second section
   *
   * @return valid HELM2 String
   */
  public String connectionToHELM2() {
    if (listOfConnections.size() == 0) {
      return "";
    }
    StringBuilder notation = new StringBuilder();
    for (int i = 0; i < listOfConnections.size(); i++) {
      notation.append(listOfConnections.get(i).toHELM2() + "|");
    }

    notation.setLength(notation.length() - 1);
    return notation.toString();
  }

  /**
   * method to generate a valid HELM2 string for the third section
   *
   * @return valid HELM2 String
   */
  public String groupingToHELM2() {
    if (listOfGroupings.size() == 0) {
      return "";
    }
    StringBuilder notation = new StringBuilder();
    for (int i = 0; i < listOfGroupings.size(); i++) {
      notation.append(listOfGroupings.get(i).toHELM2() + "|");
    }

    notation.setLength(notation.length() - 1);
    return notation.toString();
  }

  /**
   * method to generate a valid HELM2 string for the fourth section
   *
   * @return valid HELM2 String
   */
  public String annotationToHELM2() {
    if (!(annotationSection.isEmpty())) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < annotationSection.size(); i++) {
        sb.append(annotationSection.get(i).toHELM2() + "|");
      }

      sb.setLength(sb.length() - 1);
      return sb.toString();
    }
    return "";
  }

  /**
   * method to get the ID's from all polymers and groups
   *
   * @return
   */
  @JsonIgnore
  public List<String> getPolymerAndGroupingIDs() {
    List<String> listOfIDs = new ArrayList<String>();
    for (PolymerNotation polymer : listOfPolymers) {
      listOfIDs.add(polymer.getPolymerID().getId());
    }

    for (GroupingNotation grouping : listOfGroupings) {
      listOfIDs.add(grouping.getGroupID().getId());
    }
    return listOfIDs;
  }

  /**
   * method to get the ID's from all polymers
   *
   * @return
   */
  @JsonIgnore
  public List<String> getPolymerIDs() {
    List<String> listOfIDs = new ArrayList<String>();
    for (PolymerNotation polymer : listOfPolymers) {
      listOfIDs.add(polymer.getPolymerID().getId());
    }
    return listOfIDs;
  }

  /**
   * method to get the ID's from all polymers
   *
   * @return
   */
  @JsonIgnore
  public List<String> getGroupIDs() {
    List<String> listOfIDs = new ArrayList<String>();

    for (GroupingNotation grouping : listOfGroupings) {
      listOfIDs.add(grouping.getGroupID().getId());
    }
    return listOfIDs;
  }

  /**
   * method to get a specific polymer by its id
   * 
   * @param id polymer id
   * @return polymer
   */
  @JsonIgnore
  public PolymerNotation getPolymerNotation(String id) {
    for (PolymerNotation polymer : listOfPolymers) {
      if (polymer.getPolymerID().getId().equals(id)) {
        return polymer;
      }
    }

    return null;
  }

}
