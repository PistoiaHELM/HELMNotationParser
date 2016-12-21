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
import java.util.List;

import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.helm.notation2.parser.notation.connection.ConnectionNotation;
import org.jdom2.JDOMException;
import org.testng.annotations.Test;

public class ConnectionNotationTest {
  StateMachineParser parser;

  @Test
  public void testConnectionNotation() throws ExceptionState, IOException, JDOMException {
    parser = new StateMachineParser();

    String test =
        "PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}|CHEM1{[[*]SCCCc1ccccc1 |$_R1;;;;;;;;;;$|]}$PEPTIDE1,CHEM1,C:R3-1:R1\"Specific Conjugation\"$$V2.0";
    for (int i = 0; i < test.length(); ++i) {
      parser.doAction(test.charAt(i));
    }

    List<ConnectionNotation> not = parser.notationContainer.getListOfConnections();
    System.out.println(not.toString());

  }

  @Test
  public void testConnectionNotationTwo() throws ExceptionState, IOException, JDOMException {
    parser = new StateMachineParser();

    String test =
        "PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}|CHEM1{[[*]SCCCc1ccccc1 |$_R1;;;;;;;;;;$|]}$PEPTIDE1,CHEM1,C:R3-1:R1\"Specific Conjugation\"|BLOB1,CHEM2,3:?-?:?$$V2.0";
    for (int i = 0; i < test.length(); ++i) {
      parser.doAction(test.charAt(i));
    }

    List<ConnectionNotation> not = parser.notationContainer.getListOfConnections();
    System.out.println(not.toString());

  }

  @Test
  public void testConnectionGrouping() throws ExceptionState, IOException, JDOMException {
    parser = new StateMachineParser();

    String test =
        "PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}|CHEM1{[[*]SCCCc1ccccc1 |$_R1;;;;;;;;;;$|]}$PEPTIDE1,CHEM1,(C,R):R3-1:R1\"Specific Conjugation\"|BLOB1,CHEM2,3:?-?:?$$V2.0";
    for (int i = 0; i < test.length(); ++i) {
      parser.doAction(test.charAt(i));
    }

    List<ConnectionNotation> not = parser.notationContainer.getListOfConnections();
    System.out.println(not.toString());

  }
  


}
