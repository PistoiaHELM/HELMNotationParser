/*--
 *
 * @(#) MonomerNotationList.java
 *
 *
 */
package org.helm.notation2.parser.Notation.Polymer;

import java.io.IOException;
import java.util.ArrayList;

import org.helm.notation2.parser.ExceptionParser.NotationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * {@code MonomerNotationList}
 * TODO comment me
 * 
 * @author 
 * @version $Id$
 */
public class MonomerNotationList extends MonomerNotation {

  ArrayList<MonomerNotationUnit> list = new ArrayList<MonomerNotationUnit>();

  /**
   * @param str
   * @param type
   * @throws JDOMException
   * @throws IOException
   * @throws NotationException
   */
  public MonomerNotationList(String str, String type) throws NotationException, IOException {
    super(str, type);
    setMonomerNotationUnitList(str, type);
  }

  /** The Logger for this class */
  private static final Logger LOG =
      LoggerFactory.getLogger(MonomerNotationList.class);


  public ArrayList<MonomerNotationUnit> getListofMonomerUnits() {
    return list;
  }

  public void setMonomerNotationUnitList(String str, String type)
      throws NotationException, IOException {
    String[] items = str.split("\\.");
    for(int i = 0; i < items.length; i ++){
      list.add(new MonomerNotationUnit(items[i], type));
    }
    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toHELM2() {
    // TODO Auto-generated method stub
    return unit;
  }


}
