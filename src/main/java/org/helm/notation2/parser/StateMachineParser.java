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
package org.helm.notation2.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.helm.notation2.parser.exceptionparser.SimplePolymerSectionException;
import org.helm.notation2.parser.notation.HELM2Notation;
import org.helm.notation2.parser.simplepolymerssection.SimplePolymersParser;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * StateMachineParser class to represent the state machine to parse the HELM2
 * string
 *
 * @author hecht
 */
public class StateMachineParser implements State {

  private State state;

  private List<String> polymerElements = new ArrayList<String>(); // saves all
                                                                  // simple
                                                                  // polymer in
                                                                  // a
                                                                  // list

  public HELM2Notation notationContainer = new HELM2Notation();

  private static final Logger LOG = LoggerFactory.getLogger(StateMachineParser.class);

  /**
   * Constructs the state machine with the start state
   */
  public StateMachineParser() {
    setState(new SimplePolymersParser(this));
  }

  /**
   * method to set the current state
   *
   * @param state current state
   */
  public void setState(State state) {
    this.state = state;
  }

  /**
   * method to get the current state
   *
   * @return state
   */
  public State getState() {
    return this.state;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doAction(char car) throws ExceptionState{
    this.state.doAction(car);

  }

  /**
   * method to validate the polymer id in the simple polymer section the id can
   * be peptide, rna, chem or blob
   *
   * @param polymerId ID of a polymer
   * @return true if the ID is valid, false otherwise
   */
  public boolean checkPolymerId(String polymerId) {
    LOG.debug("Validation of polymerID: " + polymerId);
    String pattern = "PEPTIDE[1-9][0-9]*|RNA[1-9][0-9]*|CHEM[1-9][0-9]*|BLOB[1-9][0-9]*";

    Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    Matcher m = p.matcher(polymerId);
    if (m.matches()) {
      LOG.debug("PolymerID is valid: " + polymerId);
      return true;
    }

    LOG.debug("PolymerID is not valid: " + polymerId);
    return false;
  }

  /**
   * method to validate the polymer id in the connection section the id can be
   * peptide, rna, chem or blob the ratio + range was also included, the
   * ambiguity is also proven
   *
   * @param polymerId ID of a polymer
   * @return true if the ID is valid, false otherwise
   */
  public boolean checkPolymeridConnection(String polymerId) {
    LOG.debug("Validation of polymerID in the connection section:");
    String ratio = "(:[1-9][0-9]*(\\.[0-9]+)?)?";
    String id = "(PEPTIDE[1-9][0-9]*|RNA[1-9][0-9]*|CHEM[1-9][0-9]*|BLOB[1-9][0-9]*|G[1-9][0-9]*)";
    String pattern = "(\\(" + id + ratio + "(," + id + ratio + ")+\\)" + ratio + "|" + id + ratio + ")";
    Pattern p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
    Matcher m  = p.matcher(polymerId);
    if (m.matches()) {
      LOG.debug("PolymerID in the connection section is valid:");
      return true;
    }

    LOG.debug("PolymerID in the connection section is not valid: " + polymerId);
    return false;
  }

  /**
   * method to validate the details about the connections; hydrogen bonds are
   * here included
   *
   * @param d connection details
   * @return true if the connection details are valid, false otherwise
   */
  public boolean checkDetailsConnections(String d) {
    LOG.debug("Validation of connection's details:");
    String group = "\\((\\D|\\d)((,|\\+)(\\D|\\d))+\\)";
    String partOne = "([1-9][0-9]*|\\D|\\?|" + group + ")";
    String partTwo = "(R[1-9][0-9]*+|\\?)";
    String element = partOne + ":" + partTwo;
    String patternConnection = element + "-" + element;
    String hydrogenBondPartner = partOne + ":pair";
    String hydrogenBondPattern = hydrogenBondPartner + "-" + hydrogenBondPartner;
    Pattern pConnection = Pattern.compile(patternConnection , Pattern.CASE_INSENSITIVE);
    Matcher mConnection = pConnection.matcher(d);
    Pattern pHydrogen = Pattern.compile(hydrogenBondPattern, Pattern.CASE_INSENSITIVE);
    Matcher mHydrogen = pHydrogen.matcher(d);
    if (mConnection.matches() || mHydrogen.matches()) {
      LOG.debug("Connection's details are valid:");
      return true;
    }
    LOG.debug("Connection's details are not valid: " + d);
    return false;
  }

  /**
   * method to validate the group id
   *
   * @param d ID of a group
   * @return true if the connection details are valid, false otherwise
   */
  public boolean checkGroupId(String d) {
	  Pattern p = Pattern.compile("G[1-9][0-9]*", Pattern.CASE_INSENSITIVE);
	  Matcher m = p.matcher(d);
    LOG.debug("Validation of groupID:");
    if (m.matches()) {
      LOG.debug("GroupID is valid:");
      return true;
    }
    LOG.debug("GroupID is not valid:");
    return false;
  }

  /**
   * method to validate the details about the group information; this part can
   * be separated after + to get the id for each single group element : to get
   * the ratio for each single group element
   *
   * @param d group information
   * @return true if the group information is valid, false otherwise
   */
  public boolean checkDetailsGroup(String d) {
    LOG.debug("Validation of group's details:");
    String id = "(PEPTIDE[1-9][0-9]*+|RNA[1-9][0-9]*|CHEM[1-9][0-9]*|BLOB[1-9][0-9]*|G[1-9][0-9]*)";
    String number = "[1-9][0-9]*(\\.[0-9]+)?";
    String ratio = number + "(-" + number + ")?";
    String pattern = id + "(:" + ratio + ")?((\\+|,)" + id + "(:" + ratio + ")?)+";
    
    Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    Matcher m = p.matcher(d);
    if (m.matches()) {
      LOG.debug("Group's details are valid:");
      return true;
    }
    LOG.debug("Group's details are not valid: " + d);
    return false;
  }

  /**
   * method to validate the repeating section it can be a single number or a
   * range
   *
   * @param str repeating information
   * @return true if the information is valid, false otherwise
   */
  public boolean checkRepeating(String str) {
    String pattern = "\\d+|\\d+-\\d+";
    if (str.matches(pattern)) {
      return true;
    }
    return false;
  }

  /**
   * method to add the polymer id to the list
   *
   * @param str polymerID
   */
  public void addPolymer(String str) {
    polymerElements.add(str.toUpperCase());
  }

  /**
   * method to check if the list containing all polymer elements is empty
   *
   * @return true of no polymer id is found, false otherwise
   */
  public boolean isEmpty() {
    if (polymerElements.isEmpty()) {
      return true;
    }
    return false;
  }

  /**
   * method to check if the last added polymer element is a peptide or a rna
   *
   * @return true, if the last added polymer is a peptide or a rna, false
   *         otherwise
   * @throws SimplePolymerSectionException if list of polymers is null
   */
  public boolean isPeptideOrRna() throws SimplePolymerSectionException {
    if (polymerElements.size() >= 1) {
      if (polymerElements.get(polymerElements.size() - 1).matches("(PEPTIDE[1-9][0-9]*+|RNA[1-9][0-9]*)")) {
        return true;
      }
      return false;
    } else {
      throw new SimplePolymerSectionException("No Polymer Id is found");
    }
  }

  /**
   * method to generate a JSON-Object from the NotationContainer
   *
   * @return NotationContainer in JSON-Format
   */
  protected String toJSON() {
    ObjectMapper mapper = new ObjectMapper();

    try {
      String jsonINString = mapper.writeValueAsString(notationContainer);
      jsonINString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(notationContainer);

      return jsonINString;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
