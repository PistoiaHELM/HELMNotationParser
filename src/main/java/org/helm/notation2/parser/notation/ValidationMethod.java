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
import org.jdom2.JDOMException;

/**
 * Class containing validation methods
 *
 * @author hecht
 */
public final class ValidationMethod {

  /**
   * Default constructor.
   */
  private ValidationMethod() {

  }

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
  public static MonomerNotation decideWhichMonomerNotation(String str, String type) throws NotationException,
      IOException, JDOMException {
    MonomerNotation mon;
    /* group ? */
    if (str.startsWith("(") && str.endsWith(")")) {
      String str2 = str.substring(1, str.length() - 1);

      Pattern patternAND = Pattern.compile("\\+");
      Pattern patternOR = Pattern.compile(",");
      /* Mixture of elements */
      if (patternAND.matcher(str).find()) {
        mon = new MonomerNotationGroupMixture(str2, type);
      } /* or - groups */ else if (patternOR.matcher(str).find()) {
        mon = new MonomerNotationGroupOr(str2, type);
      } else {
        if (str.contains(".")) {
          mon = new MonomerNotationList(str2, type);
        } else {
          /* monomer unit is just in brackets */
          if (type == "RNA") {
            mon = new MonomerNotationUnitRNA(str2, type);
          } else {
            mon = new MonomerNotationUnit(str2, type);
          }
        }

      }
    } else {
      if (type == "RNA") {
        // if (str.startsWith("[") && str.endsWith("]")) {
        // mon = new MonomerNotationUnitRNA(str, type);
        // }
        mon = new MonomerNotationUnitRNA(str, type);
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
  public static MonomerNotationGroupElement decideWhichMonomerNotationInGroup(String str, String type, double one,
      double two, boolean interval, boolean isDefault) throws NotationException,
          IOException,
          JDOMException {
    MonomerNotation element;

    element = decideWhichMonomerNotation(str, type);

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
    } else if (str.matches("RNA[1-9][0-9]*")) {
      item = new RNAEntity(str);
    } else if (str.matches("BLOB[1-9][0-9]*")) {
      item = new BlobEntity(str);
    } else if (str.matches("CHEM[1-9][0-9]*")) {
      item = new ChemEntity(str);
    } else if (str.matches("G[1-9][0-9]*")) {
      item = new GroupEntity(str);
    } else {
      throw new NotationException("ID is wrong: " + str);
    }

    return item;

  }

}
