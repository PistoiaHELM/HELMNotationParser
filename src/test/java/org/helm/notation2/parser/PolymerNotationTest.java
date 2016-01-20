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

import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.jdom2.JDOMException;
import org.testng.annotations.Test;

/**
 * PolymerNotationTest
 * 
 * @author
 */
public class PolymerNotationTest {

  StateMachineParser parser;

  /*
   * method to test unchanged input in the simple polymer section
   */
  @Test
  public void testSimpleInput() throws ExceptionState, IOException, JDOMException {
    parser = new StateMachineParser();
    String test =
        "PEPTIDE1{A'3'.A.A.A.A.A.A\"mutation\".A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}$$$$";

    for (int i = 0; i < test.length(); ++i) {
      parser.doAction(test.charAt(i));
    }

    System.out.println(parser.notationContainer.getListOfPolymers().toString());

  }

  /*
   * method to test unchanged input in the simple polymer section
   */
  @Test
  public void testInputGroupOr() throws ExceptionState, IOException, JDOMException {
    parser = new StateMachineParser();
    String test = "PEPTIDE1{A.X.(C:1,B:1)\"Test\"}$$$$";
    parser = new StateMachineParser();
    for (int i = 0; i < test.length(); ++i) {
      parser.doAction(test.charAt(i));
    }

    System.out.println(parser.notationContainer.getListOfPolymers().toString());

  }

  /*
   * method to test unchanged input in the simple polymer section
   */
  @Test
  public void testInputGroupMixture() throws ExceptionState, IOException, JDOMException {
    parser = new StateMachineParser();
    String test = "PEPTIDE3{A'3-6'.X\"Test\".(C:1+B:2.2-2.3)\"Test\"}\"Hallo\"$$$$";
    parser = new StateMachineParser();
    for (int i = 0; i < test.length(); ++i) {
      parser.doAction(test.charAt(i));
    }

    System.out.println(parser.notationContainer.getListOfPolymers().toString());

  }

  /*
   * method to test unchanged input in the simple polymer section
   */
  @Test
  public void testInputCount() throws ExceptionState, IOException, JDOMException {
    parser = new StateMachineParser();
    String test = "PEPTIDE3{?._.X\"Test\".(C:1+B:2.2-2.3)\"Test\"}\"Hallo\"$$$$";
    parser = new StateMachineParser();
    for (int i = 0; i < test.length(); ++i) {
      parser.doAction(test.charAt(i));
    }

    System.out.println(parser.notationContainer.getListOfPolymers().toString());

  }

  /*
   * method to test more complicated monomer units
   */
  @Test
  public void testGroupRepeating() throws ExceptionState, IOException, JDOMException {
    parser = new StateMachineParser();
    String test = "PEPTIDE3{(H'12'\"Test\".G}$$$$";
    parser = new StateMachineParser();
    for (int i = 0; i < test.length(); ++i) {
      parser.doAction(test.charAt(i));
    }

    System.out.println(parser.notationContainer.getListOfPolymers().toString());

  }
}
