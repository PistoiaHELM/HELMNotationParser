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
 * @author
 * 
 */
public class HELM2Notation {

  private List<PolymerNotation> polymers = new ArrayList<PolymerNotation>();
  
  private List<ConnectionNotation> connections = new ArrayList<ConnectionNotation>();

  private List<GroupingNotation> groupings = new ArrayList<GroupingNotation>();
  
  private AnnotationNotation annotationSection = new AnnotationNotation();





  /**
   * method to add the notation of a simple polymer type
   * 
   * @param pol new polymer notation
   */
  @JsonIgnore
  public void addPolymer(PolymerNotation pol){
    polymers.add(pol);
  }
  
  /**
   * method to get the current simple polymer type
   * 
   * @return current polymer notation
   */
  @JsonIgnore
  public PolymerNotation getCurrentPolymer(){
    return polymers.get(polymers.size()-1);
  }
  
  /**
   * method to get all simple polymer types
   * 
   * @return all simple polymer types in a List
   */
  public List<PolymerNotation> getListOfPolymers() {
    return polymers;
  }

  /**
   * method to get the simple polymer type
   */
  public PolymerNotation getSimplePolymer(String string) {
    for (PolymerNotation polymer : polymers) {
      if (polymer.getPolymerID().getID().equals(string)) {
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
    polymers.set(polymers.size() - 1, not);
  }

  /**
   * method to add a connection
   * 
   * @param con new connection notation
   */
  public void addConnection(ConnectionNotation con) {

    connections.add(con);
  }
 

  /**
   * method to get the current connection
   * 
   * @return the current connection notation
   */
  @JsonIgnore
  public ConnectionNotation getCurrentConnection() {
    return connections.get(connections.size() - 1);
  }

  /**
   * method to change the last connection notation
   * 
   * @param not new connection notation
   */
  public void changeConnectionNotation(ConnectionNotation not) {
    connections.set(connections.size() - 1, not);
  }

  /**
   * method to get a list of all connections
   * 
   * @return all connection notations in a list
   */
  public List<ConnectionNotation> getListOfConnections() {
    return connections;
  }

  /**
   * method to add a grouping notation
   * 
   * @param not new grouping notation
   */
  public void addGrouping(GroupingNotation not) {
    groupings.add(not);
  }

  /**
   * method to get the current grouping notation
   * 
   * @return GroupingNotation
   */
  @JsonIgnore
  public GroupingNotation getCurrentGroupingNotation() {
    if (groupings.size() == 0) {
      return null;
    }
    return groupings.get(groupings.size() - 1);
  }


  /**
   * method to get all grouping notations
   * 
   * @return all grouping notations in a list
   */
  public List<GroupingNotation> getListOfGroupings() {
    return groupings;
  }

  /**
   * method to change the current grouping notation
   * 
   * @param not new grouping notation
   */
  public void changeLastGroupingNotation(GroupingNotation not) {
    groupings.set(groupings.size() - 1, not);
  }

  /**
   * method to set the annotation
   * 
   * @param anno
   */
  public void addAnnotation(AnnotationNotation anno) {
    annotationSection = anno;
  }

  /**
   * method to get the annotation
   * 
   * @return annotation
   */
  public AnnotationNotation getAnnotation() {
    return annotationSection;
  }
  
  /**
   * {@inheritDoc}
   */
  public String toString() {
    String output = "PolymerSection: " + polymers.toString() + "\n";
    if (!(connections.isEmpty())) {
      output += "ConnectionSection: " + connections.toString() + "\n";
    }

    if (!(groupings.isEmpty())) {
      output += "GroupingSection: " + groupings.toString() + "\n";
    }

    if (annotationSection.getAnnotation() != null) {
      output += annotationSection;
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

  public String toHELM1(){
    return "";
  }



  /**
   * method to generate a valid HELM2 string for the first section
   * 
   * @return valid HELM2 string
   */
  private String polymerToHELM2(){
    StringBuilder notation = new StringBuilder();
    for (int i = 0; i < polymers.size(); i++) {
      if (polymers.get(i).isAnnotationTrue()) {
        notation.append(polymers.get(i).getPolymerID() + "{" + polymers.get(i).toHELM2() + "}\""
            + polymers.get(i).getAnnotation() + "\"|");
      } else {
        notation.append(polymers.get(i).getPolymerID() + "{" + polymers.get(i).toHELM2() + "}" + "|");
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
  private String connectionToHELM2() {
    if (connections.size() == 0) {
      return "";
    }
    StringBuilder notation = new StringBuilder();
    for (int i = 0; i < connections.size(); i++) {
      notation.append(connections.get(i).toHELM2() + "|");
    }

    notation.setLength(notation.length() - 1);
    return notation.toString();
  }

  /**
   * method to generate a valid HELM2 string for the third section
   * 
   * @return valid HELM2 String
   */
  private String groupingToHELM2() {
    if (groupings.size() == 0) {
      return "";
    }
    StringBuilder notation = new StringBuilder();
    for (int i = 0; i < groupings.size(); i++) {
      notation.append(groupings.get(i).toHELM2() + "|");
    }

    notation.setLength(notation.length() - 1);
    return notation.toString();
  }

  /**
   * method to generate a valid HELM2 string for the fourth section
   * 
   * @return valid HELM2 String
   */
  private String annotationToHELM2(){
    if ((annotationSection.getAnnotation() != "")) {
      return annotationSection.getAnnotation();
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
    for (PolymerNotation polymer : polymers) {
      listOfIDs.add(polymer.getPolymerID().getID());
    }
    
    for (GroupingNotation grouping : groupings) {
      listOfIDs.add(grouping.getGroupID().getID());
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
    for (PolymerNotation polymer : polymers) {
      listOfIDs.add(polymer.getPolymerID().getID());
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

    for (GroupingNotation grouping : groupings) {
      listOfIDs.add(grouping.getGroupID().getID());
    }
    return listOfIDs;
  }

  /**
   * method to get a specific polymer
   * 
   * @param id
   * @return
   */
  @JsonIgnore
  public PolymerNotation getPolymerNotation(String id) {
    for (PolymerNotation polymer : polymers) {
      if (polymer.getPolymerID().getID().equals(id)) {
        return polymer;
      }
    }

    return null;
  }

}
