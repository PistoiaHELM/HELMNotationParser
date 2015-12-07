/**
 * ***************************************************************************** Copyright C 2015, The Pistoia Alliance
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *****************************************************************************
 */
package org.helm.notation2.parser;

import java.io.IOException;

import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.jdom2.JDOMException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OutputWriterTest {
  StateMachineParser parser;

  @Test
  public void testHELM2Output() throws ExceptionState, IOException, JDOMException {
    parser = new StateMachineParser();

    String test =
        "PEPTIDE1{A.X.G.C.(_,N).(A:10,G:30,R:30).T.C.F.D.W\"mutation\".(A:?+G:1.5).C}|RNA1{R(A)P.(R(N)P)'4'.(R(G)P)'3-7'\"mutation\"}|CHEM1{?}|BLOB1{BEAD}\"Animated Polystyrene\"$PEPTIDE1,BLOB1,X:R3-?:?\"Specific Conjugation\"|PEPTIDE1,CHEM1,(A+T):R3-?:?|PEPTIDE1,PEPTIDE1,(4,8):pair-12:pair$G1(PEPTIDE1:1+RNA1:2.5-2.7+BLOB1)|G2(G1:45,CHEM1:55)${\"Name\":\"lipid nanoparticle with RNA payload and peptide ligand\"}$";
    ;
    for (int i = 0; i < test.length(); ++i) {
      parser.doAction(test.charAt(i));
    }
    test += "V2.0";
    Assert.assertEquals(test, parser.notationContainer.toHELM2());
  }

  @Test
  public void testHELM2OutputExtended() throws ExceptionState, IOException, JDOMException {
    parser = new StateMachineParser();

    String test =
        "PEPTIDE1{A.X.G.C.(_,N).(A:10,G:30,R:30)'4'\"Group is repeated\".T.C.F.D.W\"mutation\".(A:?+G:1.5).C}|RNA1{R(A)P.(R(N)P)'4'.(R(G)P)'3-7'\"mutation\"}|CHEM1{?}|BLOB1{BEAD}\"Animated Polystyrene\"$PEPTIDE1,BLOB1,X:R3-?:?\"Specific Conjugation\"|PEPTIDE1,CHEM1,(A+T):R3-?:?|PEPTIDE1,PEPTIDE1,(4,8):pair-12:pair$G1(PEPTIDE1:1+RNA1:2.5-2.7+BLOB1)|G2(G1:45,CHEM1:55)${\"Name\":\"lipid nanoparticle with RNA payload and peptide ligand\"}$";
    ;
    for (int i = 0; i < test.length(); ++i) {
      parser.doAction(test.charAt(i));
    }
    test += "V2.0";

    Assert.assertEquals(test, parser.notationContainer.toHELM2());
  }
}
