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
package parsertest.connectionsectiontest;

import java.io.IOException;

import org.helm.notation2.parser.ParserHELM2;
import org.helm.notation2.parser.exceptionparser.ConnectionSectionException;
import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.jdom.JDOMException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestInputConnectionSection {
	
	ParserHELM2 parser = new ParserHELM2();
	String test;
	
	/*
	 * method to test the inline annotation after a connection
	 * SMILE has to be in Brackets
	 * 
	 */
	@Test 
  public void testInlineAnnotationsConnection() throws ExceptionState, IOException, JDOMException {
		test="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}|CHEM1{[[*]SCCCc1ccccc1 |$_R1;;;;;;;;;;$|]}$PEPTIDE1,CHEM1,C:R3-1:R1\"Specific Conjugation\"$$$V2.0";
		parser.parse(test);
    Assert.assertEquals(parser.getHELM2Notation().getListOfConnections().get(0).getAnnotation(), "Specific Conjugation");
	} 
	
  /*
   * method to test the inclusion of the hydrogen bond in the connection section
   */
  @Test
  public void testHydrogenBondInConnectionSection() throws ExceptionState, IOException, JDOMException {
    test =
        "RNA1{R(A)P.R(G)P.R(U)P.R(C)P.P(C)}|RNA2{R(U)P.R(G)P.R(G)P.R(G)P.R(G)P.R(A)P.R(G)}$RNA1,RNA2,20:pair-8:pair|RNA1,RNA2,17:pair-11:pair|RNA1,RNA2,8:pair-20:pair|RNA1,RNA2,14:pair-14:pair|RNA1,RNA2,11:pair-17:pair$$RNA1{StrandType:ss}|RNA2{StrandType:as}$V2.0";
    parser.parse(test);

    Assert.assertTrue(parser.getHELM2Notation().getListOfConnections().get(0).getSourceId().getID().equals("RNA1"));
    Assert.assertTrue(parser.getHELM2Notation().getListOfConnections().get(0).getTargetId().getID().equals("RNA2"));
    Assert.assertTrue(parser.getHELM2Notation().getListOfConnections().get(0).getSourceUnit().equals("20"));
    Assert.assertTrue(parser.getHELM2Notation().getListOfConnections().get(0).getTargetUnit().equals("8"));
    Assert.assertTrue(parser.getHELM2Notation().getListOfConnections().get(0).getrGroupSource().equals("pair"));
    Assert.assertTrue(parser.getHELM2Notation().getListOfConnections().get(0).getrGroupSource().equals("pair"));
    Assert.assertTrue(parser.getHELM2Notation().getListOfConnections().size() == 5);
  }

	/*
	 * method to test the inline annotation after a connection with exception
	 * 
	 */
	@Test (expectedExceptions = ConnectionSectionException.class)
  public void testInlineAnnotationsConnectionWithException() throws ExceptionState, IOException, JDOMException {
		test="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}|CHEM1{[[*]SCCCc1ccccc1 |$_R1;;;;;;;;;;$|]}$PEPTIDE1,CHEM1,C:R3-1:R1,\"Specific Conjugation\"$$$V2.0";
		parser.parse(test);
	} 

	
	/*
   * 
   * 
   */
	@Test
  public void testConnectionMonomer() throws ExceptionState, IOException, JDOMException {
    test =
        "PEPTIDE1{A.A.A.A.A.A.C.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}|CHEM1{[C[C@H](N[*])C([*])=O |$;;;_R1;;_R2;$|]}$PEPTIDE1,CHEM1,C:R3-1:R1$$$V2.0";
		parser.parse(test);
    Assert.assertEquals(parser.getHELM2Notation().getListOfConnections().get(0).getSourceUnit(), "C");
	} 
	
	/*
	 * method to test the ambiguity in the connection case
	 * 
	 */
	@Test
  public void testConnectionMonomerAmbiguity() throws ExceptionState, IOException, JDOMException {
    test =
        "PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}|CHEM1{C[C@H](N[*])C([*])=O |$;;;_R1;;_R2;$|}$PEPTIDE2,CHEM1,(C,K):R3-1:R1$$$V2.0";
    parser.parse(test);
    Assert.assertEquals(parser.getHELM2Notation().getListOfConnections().get(0).getSourceUnit(), "(C,K)");
	} 
	
	/*
	 * method to test the ambiguity in the connection case
	 * 
	 */
	@Test
  public void testConnectionMonomerAmbiguityPartlyInformation()
      throws ExceptionState, IOException, JDOMException {
    test =
        "PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}|CHEM1{[sDBL]}$PEPTIDE1,CHEM1,?:?-?:?$$$V2.0";
		parser.parse(test);
    Assert.assertEquals(parser.getHELM2Notation().getListOfConnections().get(0).getSourceUnit(), "?");
	} 
	
	/*
	 * method to test the ambiguity in the connection case
	 * 
	 */
	@Test
  public void testConnectionMonomerAmbiguityNoInformation()
      throws ExceptionState, IOException, JDOMException {
    test =
        "PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}|CHEM1{[C[C@H](N[*])C([*])=O |$;;;_R1;;_R2;$|]}$PEPTIDE2,CHEM1,(C+K):R3-1:R1$$$V2.0";
		parser.parse(test);
    Assert.assertEquals(parser.getHELM2Notation().getListOfConnections().get(0).getSourceUnit(), "(C+K)");
    // Assert.assertTrue(parser.getHELM2Notation().getListOfConnections().get(0).getSourceUnit()
    // instanceof MonomerNotationGroup);
    // Assert.assertTrue(parser.getHELM2Notation().getListOfConnections().get(0).getSourceUnit()
    // instanceof MonomerNotationGroupMixture);
	} 
	
	
	/*
	 * method to test the statistical binding in the connection section
	 * 
	 */
	@Test
  public void testConnectionStatisticalBinding() throws ExceptionState, IOException, JDOMException {
    test =
        "PEPTIDE1{*}\"LC\"|PEPTIDE2{*}\"HC\"|PEPTIDE3{*}\"HC\"|PEPTIDE4{*}\"LC\"|CHEM1{*}$G1,CHEM1,K:R3-1:R1|PEPTIDE2,PEPTIDE3,250:R3-250:R3\"Hinge S-S connection\"|PEPTIDE2,PEPTIDE3,252:R3-252:R3\"Hinge S-S connection\"|PEPTIDE1,PEPTIDE2,120:R3-248:R3\"LC Hinge S-S connection\"|PEPTIDE4,PEPTIDE3,120:R3-248:R3\"LC Hinge S-S connection\"$G1(PEPTIDE1+PEPTIDE2+PEPTIDE3+PEPTIDE4)|G2(G1+CHEM1:4.5)$$V2.0";
		parser.parse(test);
	}
	
	
	/*
	 * method to test unknown binding
	 * 
	 */
	@Test
  public void testConnectionUnknownBinding() throws ExceptionState, IOException, JDOMException {
    test =
        "PEPTIDE1{C.C.C.C.C.C}|PEPTIDE2{A.C.A.A.A.A}|BLOB1{Bead}$PEPTIDE1,BLOB1,2:R3-?:R1$$PEPTIDE1{Type:Peptide,Name:Gold-conjugated peptide}|BLOB1{Type:Gold particle,Name:Au10,Diameter:10nm}$V2.0";
		parser.parse(test);
		test= "PEPTIDE1{C.C.C.C.C.C}|PEPTIDE2{A.C.A}|BLOB1{Gold Particle}\"Au10,Diameter:10nm\"$G1,BLOB1,C:R3-?:R1$G1(PEPTIDE1:49+PEPTIDE2:51)|G2(G1:24+BLOB1)$$V2.0";
		parser.parse(test);
	}
	
	
	
	
}

