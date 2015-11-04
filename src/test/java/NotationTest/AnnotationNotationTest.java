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
package NotationTest;


import org.testng.annotations.Test;

import parsertest.StateMachineParser;
import parsertest.ExceptionParser.ExceptionState;

/**
 * AnnotationNotationTest
 * 
 * @author hecht
 */
public class AnnotationNotationTest {

  StateMachineParser parser;

  @Test
  public void testAnnotationNotation() throws ExceptionState

  {
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
