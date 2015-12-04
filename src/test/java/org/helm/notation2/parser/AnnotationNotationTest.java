<<<<<<< HEAD:src/test/java/org/helm/notation2/parser/AnnotationNotationTest.java
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

import org.helm.notation2.parser.StateMachineParser;
import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.jdom.JDOMException;
import org.testng.annotations.Test;

/**
 * AnnotationNotationTest
 * 
 * @author hecht
 */
public class AnnotationNotationTest {

  StateMachineParser parser;

  @Test
  public void testAnnotationNotation() throws ExceptionState, IOException, JDOMException {
    parser = new StateMachineParser();

    String test =
        "PEPTIDE1{C.C.C.C.C.C}|PEPTIDE2{A.C.A.A.A.A}|BLOB1{Bead}$PEPTIDE1,BLOB1,2:R3-?:R1$$PEPTIDE1{Type:Peptide,Name:Gold-conjugated peptide}|BLOB1{Type:Gold particle,Name:Au10,Diameter:10nm}$";
    ;
    for (int i = 0; i < test.length(); ++i) {
      parser.doAction(test.charAt(i));
    }

    System.out.println(parser.notationContainer.getAnnotation());

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
package parsertest.Notation.Polymer;

import java.io.IOException;

import org.helm.notation.MonomerException;
import org.helm.notation.MonomerFactory;
import org.helm.notation.MonomerStore;
import org.jdom2.JDOMException;

import parsertest.ExceptionParser.NotationException;
import parsertest.Notation.ValidationMethod;

/**
 * MonomerNotationUnit
 * 
 * @author hecht
 */
public class MonomerNotationUnit extends MonomerNotation {

  /**
   * Constructs with the given String
   * 
   * @param str Monomer
   * @throws JDOMException
   * @throws IOException
   * @throws MonomerException
   * @throws NotationException
   * @throws org.helm.notation.NotationException
   */
  public MonomerNotationUnit(String str, String type) throws MonomerException,
      IOException, JDOMException, NotationException,
      org.helm.notation.NotationException {
    super(str, type);

    /* Validation of Monomer */
    if (new ValidationMethod().isMonomerValid(str, type)) {

    } else {
      throw new NotationException("Monomer was not valid: " + str);
    }

  }



  /**
   * {@inheritDoc}
   */
  @Override
  public String toHELM2() {

    String text = unit;
    if (isDefault == false) {
      if (unit.length() > 1) {
        text = "(" + unit + ")";
      }
      text += "'" + count + "'";
    }

    if (isAnnotationHere) {
      text += "\"" + annotation + "\"";
    }
    return text;
  }


}
>>>>>>> 028ee9b5dc0c7557d6e1cfa25a041b95a69e9c58:src/main/java/parsertest/Notation/Polymer/MonomerNotationUnit.java
