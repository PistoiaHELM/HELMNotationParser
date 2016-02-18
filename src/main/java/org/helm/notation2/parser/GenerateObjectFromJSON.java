/*--
 *
 * @(#) GenerateObjectFromJSON.java
 *
 *
 */
package org.helm.notation2.parser;

import java.io.IOException;

import org.helm.notation2.parser.notation.HELM2Notation;
import org.helm.notation2.parser.notation.grouping.GroupingMixture;
import org.helm.notation2.parser.notation.grouping.GroupingOr;
import org.helm.notation2.parser.notation.polymer.BlobEntity;
import org.helm.notation2.parser.notation.polymer.MonomerNotationGroup;
import org.helm.notation2.parser.notation.polymer.MonomerNotationGroupMixture;
import org.helm.notation2.parser.notation.polymer.MonomerNotationGroupOr;
import org.helm.notation2.parser.notation.polymer.MonomerNotationList;
import org.helm.notation2.parser.notation.polymer.MonomerNotationUnit;
import org.helm.notation2.parser.notation.polymer.MonomerNotationUnitRNA;
import org.helm.notation2.parser.notation.polymer.PeptideEntity;
import org.helm.notation2.parser.notation.polymer.PolymerEntity;
import org.helm.notation2.parser.notation.polymer.PolymerListElements;
import org.helm.notation2.parser.notation.polymer.PolymerSingleElements;
import org.helm.notation2.parser.notation.polymer.RNAEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

/**
 * {@code GenerateObjectFromJSON} TODO comment me
 *
 * @author
 * @version $Id$
 */
public class GenerateObjectFromJSON {

  /** The Logger for this class */
  private static final Logger LOG = LoggerFactory.getLogger(GenerateObjectFromJSON.class);

  public static HELM2Notation generateFromJSON(String json) throws JsonParseException, JsonMappingException, IOException {

    HELM2Notation helm2notation = null;
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.registerSubtypes(PeptideEntity.class, BlobEntity.class, PolymerEntity.class, GroupingMixture.class);
    mapper.registerSubtypes(PolymerListElements.class, PolymerSingleElements.class);
    mapper.registerSubtypes(MonomerNotationUnit.class, MonomerNotationUnitRNA.class, MonomerNotationList.class, MonomerNotationGroupOr.class, MonomerNotationGroupMixture.class, MonomerNotationGroup.class);
    mapper.registerSubtypes(GroupingOr.class, GroupingMixture.class);
    // JSON from file to Object
    HELM2Notation obj = mapper.readValue(json, HELM2Notation.class);
    return obj;
  }

}
