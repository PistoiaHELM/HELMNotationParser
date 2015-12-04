<<<<<<< HEAD:src/main/java/org/helm/notation2/parser/notation/ValidationMethod.java
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


import java.io.IOException;
import java.util.regex.Pattern;

import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.notation.polymer.BlobEntity;
import org.helm.notation2.parser.notation.polymer.ChemEntity;
import org.helm.notation2.parser.notation.polymer.GroupEntity;
import org.helm.notation2.parser.notation.polymer.HELMEntity;
import org.helm.notation2.parser.notation.polymer.MonomerNotation;
import org.helm.notation2.parser.notation.polymer.MonomerNotationGroupElement;
import org.helm.notation2.parser.notation.polymer.MonomerNotationGroupMixture;
import org.helm.notation2.parser.notation.polymer.MonomerNotationGroupOr;
import org.helm.notation2.parser.notation.polymer.MonomerNotationList;
import org.helm.notation2.parser.notation.polymer.MonomerNotationUnit;
import org.helm.notation2.parser.notation.polymer.MonomerNotationUnitRNA;
import org.helm.notation2.parser.notation.polymer.PeptideEntity;
import org.helm.notation2.parser.notation.polymer.RNAEntity;
import org.jdom.JDOMException;



/**
 * Class containing validation methods
 * 
 * @author hecht
 */
public final class ValidationMethod {




  /**
   * method to decide which of the MonomerNotation classes should be initialized
   * 
   * @param str
   * @param type
   * @return
   * @throws NotationException
   * @throws IOException
   * @throws JDOMException
   */
  public static MonomerNotation decideWhichMonomerNotation(String str, String type) throws NotationException, IOException, JDOMException {
    MonomerNotation mon;
      /* group ? */
    if (str.startsWith("(") && str.endsWith(")")) {
      String str2 = str.substring(1, str.length() - 1);

      Pattern patternAND = Pattern.compile("\\+");
      Pattern patternOR = Pattern.compile(",");
      /* Mixture of elements */
      if (patternAND.matcher(str).find()) {
        mon = new MonomerNotationGroupMixture(str2, type);
      }
      /* or - groups */
      else if (patternOR.matcher(str).find()) {
        mon = new MonomerNotationGroupOr(str2, type);
      }
 else {
        if (str.contains(".")) {
          mon = new MonomerNotationList(str2, type);
        }
 else {
          /* monomer unit is just in brackets */
          if (type == "RNA") {
            mon = new MonomerNotationUnitRNA(str2, type);
          }
 else {
        mon = new MonomerNotationUnit(str2, type);
          }
        }

      }
    }

    else {
      if (type == "RNA") {
        if (str.startsWith("[") && str.endsWith("]")) {
          mon = new MonomerNotationUnit(str, type);
        }

        else {
          mon = new MonomerNotationUnitRNA(str, type);
        }
      } else {
        mon = new MonomerNotationUnit(str, type);
      }

    }
    return mon;
  }

  /**
   * method to decide which of the two Constructors of
   * MonomerNotaitonGroupElement should be called
   * 
   * @param str Monomer
   * @param one count of Monomer
   * @param two count of Monomer
   * @param interval Has the monomer instead of one count an interval
   * @param isDefault Is the count of the monomer default = 1
   * @return MonomerNotationGroupElement
   * @throws NotationException
   * @throws IOException
   * @throws JDOMException
   */
  public static MonomerNotationGroupElement decideWhichMonomerNotationInGroup(String str, String type, double one, double two, boolean interval, boolean isDefault) throws NotationException,
      IOException,
      JDOMException {
    MonomerNotation element;


    element = decideWhichMonomerNotation(str,type);

    if (interval) {
      return new MonomerNotationGroupElement(element, one, two);
    } else {
      return new MonomerNotationGroupElement(element, one, isDefault);
    }
  }

  /**
   * method to decide which of the Entities classes should be initialized
   * 
   * @param str polymer ID
   * @return Entity
   * @throws NotationException
   */
  public static HELMEntity decideWhichEntity(String str) throws NotationException {

    HELMEntity item;

    if (str.matches("PEPTIDE[1-9][0-9]*")) {
      item = new PeptideEntity(str);
    }
 else if (str.matches("RNA[1-9][0-9]*")) {
      item = new RNAEntity(str);
    }
 else if (str.matches("BLOB[1-9][0-9]*")) {
      item = new BlobEntity(str);
    }
 else if (str.matches("CHEM[1-9][0-9]*")) {
      item = new ChemEntity(str);
    }
 else if (str.matches("G[1-9][0-9]*")) {
      item = new GroupEntity(str);
    }

    else {
      throw new NotationException("ID is wrong: " + str);
    }

    return item;

  }
  

 



}
=======
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
import java.util.regex.Pattern;

import org.helm.notation.MonomerException;
import org.helm.notation.MonomerFactory;
import org.helm.notation.MonomerStore;
import org.helm.notation.tools.SimpleNotationParser;
import org.helm.notation.tools.StructureParser;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import parsertest.ExceptionParser.NotationException;
import parsertest.Notation.Polymer.BlobEntity;
import parsertest.Notation.Polymer.ChemEntity;
import parsertest.Notation.Polymer.Entity;
import parsertest.Notation.Polymer.GroupEntity;
import parsertest.Notation.Polymer.MonomerNotation;
import parsertest.Notation.Polymer.MonomerNotationGroupElement;
import parsertest.Notation.Polymer.MonomerNotationGroupMixture;
import parsertest.Notation.Polymer.MonomerNotationGroupOr;
import parsertest.Notation.Polymer.MonomerNotationList;
import parsertest.Notation.Polymer.MonomerNotationUnit;
import parsertest.Notation.Polymer.PeptideEntity;
import parsertest.Notation.Polymer.RNAEntity;


/**
 * Class containing validation methods
 * 
 * @author hecht
 */
public class ValidationMethod {


  private static final Logger logger =
      LoggerFactory.getLogger(ValidationMethod.class);

  /**
   * method to decide which of the MonomerNotation classes should be initialized
   * 
   * @param str monomer
   * @return MonomerNotation
   * @throws NotationException
   * @throws IOException
   * @throws MonomerException
   * @throws JDOMException
   * @throws org.jdom2.JDOMException
   * @throws org.helm.notation.NotationException
   */
  public MonomerNotation decideWhichMonomerNotation(String str, String type)
      throws NotationException, MonomerException, IOException, JDOMException,
      org.jdom2.JDOMException, org.helm.notation.NotationException {
    MonomerNotation mon;

      /* group ? */
    if (str.startsWith("(")) {

      String str2 = str.substring(1, str.length() - 1);
      Pattern patternAND = Pattern.compile("\\+");
      Pattern patternOR = Pattern.compile(",");
      /* Mixture of elements */
      if (patternAND.matcher(str).find()) {
        mon = new MonomerNotationGroupMixture(str2, type);
      }
      /* or - groups */
      else if (patternOR.matcher(str).find()) {
        mon = new MonomerNotationGroupOr(str2, type);
      }
 else {
        if (str.contains(".")) {
          mon = new MonomerNotationList(str2, type);
        }
 else {
        mon = new MonomerNotationUnit(str2, type);
        }

      }
    }
      /* monomer unit is just in brackets */
    else {

      mon = new MonomerNotationUnit(str, type);

    }
    return mon;
  }

  /**
   * method to decide which of the two Constructors of
   * MonomerNotaitonGroupElement should be called
   * 
   * @param str Monomer
   * @param one count of Monomer
   * @param two count of Monomer
   * @param interval Has the monomer instead of one count an interval
   * @param isDefault Is the count of the monomer default = 1
   * @return MonomerNotationGroupElement
   * @throws NotationException
   * @throws JDOMException
   * @throws IOException
   * @throws MonomerException
   * @throws org.jdom2.JDOMException
   * @throws org.helm.notation.NotationException
   */
  public MonomerNotationGroupElement decideWhichMonomerNotationInGroup(
      String str, String type, double one, double two,
      boolean interval, boolean isDefault) throws NotationException,
          MonomerException, IOException, JDOMException,
          org.jdom2.JDOMException, org.helm.notation.NotationException {
    MonomerNotation element;


    element = decideWhichMonomerNotation(str,type);

    if (interval) {
      return new MonomerNotationGroupElement(element, one, two);
    } else {
      return new MonomerNotationGroupElement(element, one, isDefault);
    }
  }

  /**
   * method to decide which of the Entities classes should be initialized
   * 
   * @param str polymer ID
   * @return Entity
   * @throws NotationException
   */
  public Entity decideWhichEntity(String str) throws NotationException {

    Entity item;

    if (str.matches("PEPTIDE[1-9][0-9]*")) {
      item = new PeptideEntity(str);
    }
 else if (str.matches("RNA[1-9][0-9]*")) {
      item = new RNAEntity(str);
    }
 else if (str.matches("BLOB[1-9][0-9]*")) {
      item = new BlobEntity(str);
    }
 else if (str.matches("CHEM[1-9][0-9]*")) {
      item = new ChemEntity(str);
    }
 else if (str.matches("G[1-9][0-9]*")) {
      item = new GroupEntity(str);
    }

    else {
      throw new NotationException("ID is wrong: " + str);
    }

    return item;

  }
  

  public boolean isMonomerValid(String str, String type)
      throws MonomerException, IOException, org.jdom2.JDOMException,
      org.helm.notation.NotationException, NotationException {
    MonomerFactory monomerFactory = MonomerFactory.getInstance();
    /* Search in Database */
    MonomerStore monomerStore = monomerFactory.getMonomerStore();
    if (monomerStore.hasMonomer(type, str)) {
      logger.info("Monomer is locate in the database: " + str);
      return true;
    }
    
     else if (str.charAt(0) == '[' && str.charAt(str.length() - 1) == ']' &&
     monomerStore.hasMonomer(type, str.substring(1, str.length() - 1))) {
     logger.info("Monomer is locate in the database: " + str); return true; }


    /* polymer type is Blob: accept all */
    else if (type.equals("BLOB")) {
      logger.info("Blob's Monomer Type: " + str);
      return true;
    }

    /* new unknown monomer for peptide */
    else if (type.equals("PEPTIDE") && str.equals("X")) {
      logger.info("Unknown monomer type for peptide: " + str);
      return true;
    }

    /* new unknown monomer for peptide */
    else if (type.equals("RNA") && str.equals("N")) {
      logger.info("Unknown monomer type for rna: " + str);
      return true;
    }

 

    /* new unknown types */
    else if (str.equals("?") || str.equals("_")) {
      logger.info("Unknown types: " + str);
      return true;
    }

    /* nucleotide */
    else if (type.equals("RNA")) {
        SimpleNotationParser.getMonomerIDList(str, type, monomerStore);
        logger.info("Nucleotide type for RNA: " + str);
        return true;
    }

    else{
      
      /* SMILES Check */
      if (str.charAt(0) == '[' && str.charAt(str.length() - 1) == ']') {
        str = str.substring(1, str.length() - 1);
      }

      return StructureParser.validateSmiles(str);
      

    }
  }



}
>>>>>>> 028ee9b5dc0c7557d6e1cfa25a041b95a69e9c58:src/main/java/parsertest/Notation/ValidationMethod.java
