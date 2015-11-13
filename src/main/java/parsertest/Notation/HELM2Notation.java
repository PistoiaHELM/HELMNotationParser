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
package parsertest.Notation;

import java.io.IOException;
import java.util.ArrayList;

import org.helm.notation.MonomerException;
import org.helm.notation.MonomerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import parsertest.Notation.Annotation.AnnotationNotation;
import parsertest.Notation.Connection.ConnectionNotation;
import parsertest.Notation.Grouping.GroupingNotation;
import parsertest.Notation.Polymer.PolymerNotation;

/**
 * HELM2Notation this class contains all notation objects for HELM2
 * 
 * @author
 * 
 */
public class HELM2Notation {

  ArrayList<PolymerNotation> polymer = new ArrayList<PolymerNotation>();
  
  ArrayList<ConnectionNotation> connection = new ArrayList<ConnectionNotation>();

  ArrayList<GroupingNotation> grouping = new ArrayList<GroupingNotation>();
  
  AnnotationNotation annotationSection = new AnnotationNotation();

  org.helm.notation.MonomerStore monomerStore;

  private void loadMonomerStore() throws MonomerException, IOException,
      org.jdom2.JDOMException {
    MonomerFactory monomerFactory = MonomerFactory.getInstance();

    monomerStore = monomerFactory.getMonomerStore();
  }

  /**
   * method to add the notation of a simple polymer type
   * 
   * @param pol new polymer notation
   */
  @JsonIgnore
  public void addPolymer(PolymerNotation pol){
    polymer.add(pol);
  }
  
  /**
   * method to get the current simple polymer type
   * 
   * @return current polymer notation
   */
  @JsonIgnore
  public PolymerNotation getCurrentPolymer(){
    return polymer.get(polymer.size()-1);
  }
  
  /**
   * method to get all simple polymer types
   * 
   * @return all simple polymer types in a List
   */
  public ArrayList<PolymerNotation> getListOfPolymers() {
    return polymer;
  }

  /**
   * method to change the last polymer notation
   * 
   * @param not New PolymerNotation
   */
  public void changeLastPolymerNotation(PolymerNotation not) {
    polymer.set(polymer.size() - 1, not);
  }

  /**
   * method to add a connection
   * 
   * @param con new connection notation
   */
  public void addConnection(ConnectionNotation con) {

    connection.add(con);
  }
 

  /**
   * method to get the current connection
   * 
   * @return the current connection notation
   */
  @JsonIgnore
  public ConnectionNotation getCurrentConnection() {
    return connection.get(connection.size() - 1);
  }

  /**
   * method to change the last connection notation
   * 
   * @param not new connection notation
   */
  public void changeConnectionNotation(ConnectionNotation not) {
    connection.set(connection.size() - 1, not);
  }

  /**
   * method to get a list of all connections
   * 
   * @return all connection notations in a list
   */
  public ArrayList<ConnectionNotation> getListOfConnections() {
    return connection;
  }

  /**
   * method to add a grouping notation
   * 
   * @param not new grouping notation
   */
  public void addGrouping(GroupingNotation not) {
    grouping.add(not);
  }

  /**
   * method to get the current grouping notation
   * 
   * @return GroupingNotation
   */
  @JsonIgnore
  public GroupingNotation getCurrentGroupingNotation() {
    if (grouping.size() == 0) {
      return null;
    }
    return grouping.get(grouping.size() - 1);
  }


  /**
   * method to get all grouping notations
   * 
   * @return all grouping notations in a list
   */
  public ArrayList<GroupingNotation> getListOfGroupings() {
    return grouping;
  }

  /**
   * method to change the current grouping notation
   * 
   * @param not new grouping notation
   */
  public void changeLastGroupingNotation(GroupingNotation not) {
    grouping.set(grouping.size() - 1, not);
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
    String output = "PolymerSection: " + polymer.toString() + "\n";
    if (!(connection.isEmpty())) {
      output += "ConnectionSection: " + connection.toString() + "\n";
    }

    if (!(grouping.isEmpty())) {
      output += "GroupingSection: " + grouping.toString() + "\n";
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



  /**
   * method to generate a valid HELM2 string for the first section
   * 
   * @return valid HELM2 string
   */
  private String polymerToHELM2(){
    StringBuilder notation = new StringBuilder();
    for (int i = 0; i < polymer.size(); i++) {
      if (polymer.get(i).isAnnotationTrue()) {
        notation.append(polymer.get(i).getPolymerID() + "{" + polymer.get(i).toHELM2() + "}\""
            + polymer.get(i).getAnnotation() + "\"|");
      } else {
        notation.append(polymer.get(i).getPolymerID() + "{" + polymer.get(i).toHELM2() + "}" + "|");
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
    if (connection.size() == 0) {
      return "";
    }
    StringBuilder notation = new StringBuilder();
    for (int i = 0; i < connection.size(); i++) {
      notation.append(connection.get(i).toHELM2() + "|");
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
    if (grouping.size() == 0) {
      return "";
    }
    StringBuilder notation = new StringBuilder();
    for (int i = 0; i < grouping.size(); i++) {
      notation.append(grouping.get(i).toHELM2() + "|");
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

}
