/*--
 *
 * @(#) GenerateObjectFromJSONTest.java
 *
 *
 */
package org.helm.notation2.parser;

import java.io.IOException;
import java.util.List;

import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.helm.notation2.parser.notation.HELM2Notation;
import org.helm.notation2.parser.notation.connection.ConnectionNotation;
import org.helm.notation2.parser.notation.grouping.GroupingMixture;
import org.helm.notation2.parser.notation.polymer.BlobEntity;
import org.helm.notation2.parser.notation.polymer.MonomerNotation;
import org.helm.notation2.parser.notation.polymer.MonomerNotationGroup;
import org.helm.notation2.parser.notation.polymer.MonomerNotationGroupMixture;
import org.helm.notation2.parser.notation.polymer.MonomerNotationGroupOr;
import org.helm.notation2.parser.notation.polymer.MonomerNotationList;
import org.helm.notation2.parser.notation.polymer.MonomerNotationUnit;
import org.helm.notation2.parser.notation.polymer.MonomerNotationUnitRNA;
import org.helm.notation2.parser.notation.polymer.PeptideEntity;
import org.helm.notation2.parser.notation.polymer.PolymerElements;
import org.helm.notation2.parser.notation.polymer.PolymerEntity;
import org.helm.notation2.parser.notation.polymer.PolymerListElements;
import org.helm.notation2.parser.notation.polymer.PolymerNotation;
import org.helm.notation2.parser.notation.polymer.PolymerSingleElements;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * GenerateObjectFromJSONTest
 *
 * @author hecht
 * @version $Id$
 */
public class GenerateObjectFromJSONTest {

  /** The Logger for this class */
  private static final Logger LOG = LoggerFactory.getLogger(GenerateObjectFromJSONTest.class);

  @Test
  public void test() throws JsonParseException, JsonMappingException, IOException, ExceptionState, JDOMException {

    StateMachineParser parser = new StateMachineParser();

    String test =
        "PEPTIDE1{C.C.C.C.C.C}|PEPTIDE2{A.C.A.A.A.A}|BLOB1{Bead}$PEPTIDE1,BLOB1,2:R3-?:R1$G1(PEPTIDE1+PEPTIDE2)$PEPTIDE1{Type:Peptide,Name:Gold-conjugated peptide}|BLOB1{Type:Gold particle,Name:Au10,Diameter:10nm}$";
    ;
    for (int i = 0; i < test.length(); ++i) {
      parser.doAction(test.charAt(i));
    }
    System.out.println(parser.toJSON());

    HELM2Notation helm2notation = GenerateObjectFromJSON.generateFromJSON(parser.toJSON());

    Assert.assertEquals(test + "V2.0", helm2notation.toHELM2());

    String mon = "{ \"classType\" : \"MonomerNotationUnit\",\"unit\" : \"Bead\",\"annotation\" : null,\"count\" : \"1\",\"type\" : \"BLOB\"}";

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerSubtypes(MonomerNotationUnit.class, MonomerNotationUnitRNA.class, MonomerNotationList.class, MonomerNotationGroupOr.class, MonomerNotationGroupMixture.class, MonomerNotationGroup.class);
    // JSON from file to Object
    MonomerNotation obj = mapper.readValue(mon, MonomerNotation.class);
    System.out.println(obj.toHELM2());

    String elements =
        "{ \"classType\" : \"PolymerSingleElements\",\"listOfElements\" : [ {\"classType\" : \"MonomerNotationUnit\",\"unit\" : \"Bead\",\"annotation\" : null,\"count\" : \"1\",\"type\" : \"BLOB\"} ],\"entity\" : {\"classType\" : \"BlobEntity\",\"id\" : \"BLOB1\", \"type\" : \"BLOB\"}}";
    mapper.registerSubtypes(PeptideEntity.class, BlobEntity.class, PolymerEntity.class, GroupingMixture.class, PolymerSingleElements.class, PolymerListElements.class);
    // mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
    // false);
    mapper.registerSubtypes(PolymerListElements.class, PolymerSingleElements.class);
    PolymerElements element = mapper.readValue(elements, PolymerElements.class);
    System.out.println(element.toHELM2());

    String polymer =
        "{\"polymerID\" : {\"classType\" : \"PeptideEntity\",\"id\" : \"PEPTIDE2\",\"type\" : \"PEPTIDE\" },\"annotation\" : null,\"annotationHere\" : false,\"polymerElements\" : {\"classType\" : \"PolymerListElements\",\"entity\" : {\"classType\" : \"PeptideEntity\",\"id\" : \"PEPTIDE2\",\"type\" : \"PEPTIDE\"}, \"listOfElements\" : [ {\"classType\" : \"MonomerNotationUnit\",\"unit\" : \"A\",\"annotation\" : null,\"count\" : \"1\",\"type\" : \"PEPTIDE\" }, {\"classType\" : \"MonomerNotationUnit\",\"unit\" : \"C\",\"annotation\" : null,\"count\" : \"1\",\"type\" : \"PEPTIDE\" }, {\"classType\" : \"MonomerNotationUnit\",\"unit\" : \"A\",\"annotation\" : null,\"count\" : \"1\",\"type\" : \"PEPTIDE\"}, {\"classType\" : \"MonomerNotationUnit\",\"unit\" : \"A\",\"annotation\" : null,\"count\" : \"1\", \"type\" : \"PEPTIDE\" }, {\"classType\" : \"MonomerNotationUnit\", \"unit\" : \"A\",\"annotation\" : null,\"count\" : \"1\",\"type\" : \"PEPTIDE\"}, {\"classType\" : \"MonomerNotationUnit\",\"unit\" : \"A\",\"annotation\" : null,\"count\" : \"1\",\"type\" : \"PEPTIDE\"} ] } }";
    PolymerNotation p = mapper.readValue(polymer, PolymerNotation.class);
    System.out.println(p.toHELM2());
  }

}
