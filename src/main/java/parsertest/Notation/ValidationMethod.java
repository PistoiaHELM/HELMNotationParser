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


import java.util.regex.Pattern;

import parsertest.ExceptionParser.NotationException;
import parsertest.Notation.Polymer.BlobEntity;
import parsertest.Notation.Polymer.ChemEntity;
import parsertest.Notation.Polymer.Entity;
import parsertest.Notation.Polymer.GroupEntity;
import parsertest.Notation.Polymer.MonomerNotation;
import parsertest.Notation.Polymer.MonomerNotationGroupElement;
import parsertest.Notation.Polymer.MonomerNotationGroupMixture;
import parsertest.Notation.Polymer.MonomerNotationGroupOr;
import parsertest.Notation.Polymer.MonomerNotationUnit;
import parsertest.Notation.Polymer.PeptideEntity;
import parsertest.Notation.Polymer.RNAEntity;


/**
 * Class containing validation methods
 * 
 * @author hecht
 */
public class ValidationMethod {

  /**
   * method to decide which of the MonomerNotation classes should be initialized
   * 
   * @param str monomer
   * @return MonomerNotation
   */
  public MonomerNotation decideWhichMonomerNotation(String str) {
    MonomerNotation mon;
      /* group ? */
    if (str.startsWith("(")) {

      String str2 = str.substring(1, str.length() - 1);
      Pattern patternAND = Pattern.compile("\\+");
      Pattern patternOR = Pattern.compile(",");
      /* Mixture of elements */
      if (patternAND.matcher(str).find()) {
        mon = new MonomerNotationGroupMixture(str2);
      }
      /* or - groups */
      else if (patternOR.matcher(str).find()) {
        mon = new MonomerNotationGroupOr(str2);
      }
 else {
        mon = new MonomerNotationUnit(str2);
      }
    }
      /* monomer unit is just in brackets */
    else {
      mon = new MonomerNotationUnit(str);

    }
    return mon;
  }

  /**
   * method to decide which of the two Constructors of MonomerNotaitonGroupElement should be called
   * 
   * @param str Monomer
   * @param one count of Monomer
   * @param two count of Monomer
   * @param interval Has the monomer instead of one count an interval
   * @param isDefault Is the count of the monomer default = 1
   * @return MonomerNotationGroupElement
   */
  public MonomerNotationGroupElement decideWhichMonomerNotationInGroup(String str, double one, double two,
      boolean interval, boolean isDefault) {
    MonomerNotation element;


    element = decideWhichMonomerNotation(str);

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
}
