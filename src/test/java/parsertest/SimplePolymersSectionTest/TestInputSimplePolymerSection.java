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
package parsertest.SimplePolymersSectionTest;



import org.testng.Assert;
import org.testng.annotations.Test;

import parsertest.ParserHELM2;
import parsertest.ExceptionParser.ExceptionState;
import parsertest.ExceptionParser.SimplePolymerSectionException;

import parsertest.Notation.Polymer.MonomerNotationGroup;
import parsertest.Notation.Polymer.MonomerNotationGroupOr;



public class TestInputSimplePolymerSection {
	ParserHELM2 parser = new ParserHELM2();
	String test;
	

	/*
	 * method to test unchanged input in the simple polymer section
	 */
	@Test 
  	public void testSimpleInput() throws ExceptionState {
		test="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}$$$$V2.0";
		parser.parse(test);
    /*
     * Test for the right Notation checks if the polymertype is correct
     */

    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerID().getID().equals("PEPTIDE1"));
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(1).getPolymerID().getID().equals("PEPTIDE2"));
  }
		
	/*
	 * method to test the new repeating function 
	 */
	@Test 
  	public void testMonomerRepeatingUnits() throws ExceptionState {
		test="PEPTIDE1{A'23'.C.D'12'.E'24-25'}|PEPTIDE2{G'22'.C.S'8'.P.P.P.P.P.P.P.P.P.K'6'}$$$$V2.0";
		parser.parse(test);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(0).getCount().equals("23"));
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(3).getCount().equals("24-25"));
  }
	
	/*
	 * method to test the new repeating function with exception
	 */
	@Test (expectedExceptions = SimplePolymerSectionException.class)
  	public void testMonomerRepeatingUnitsWithException() throws ExceptionState {
		test="PEPTIDE1{A'23'.C.D.'12'.E'24'}|PEPTIDE2{G'22'.C.S'8'.P.P.P.P.P.P.P.P.P.K'6'}$$$$V2.0";
		parser.parse(test);
  }

 
	/*
   * method to test the inclusion of the inline annotations for one monomer
   */
  @Test(expectedExceptions = SimplePolymerSectionException.class)
  	public void testInlineAnnoationsWithException() throws ExceptionState {
		test="RNA1{R(A)P.R(G)P.R(U)\"mutation\"P.R(C)P.P(C)}|RNA2{R(U)P.R(G)P.R(G)P.R(G)P.R(G)P.R(A)P.R(G)}$RNA1,RNA2,20:pair-8:pair|RNA1,RNA2,17:pair-11:pair|RNA1,RNA2,8:pair-20:pair|RNA1,RNA2,14:pair-14:pair|RNA1,RNA2,11:pair-17:pair$$RNA1{StrandType:ss}|RNA2{StrandType:as}$V2.0";
		parser.parse(test);
  }  
	
	/*
   * method to test the inclusion of the inline annotations for one monomer
   */
	@Test
  public void testInlineAnnotations() throws ExceptionState {
		test="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C\"mutation\".D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}$$$$V2.0";
		parser.parse(test);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(21).getAnnotation().equals("mutation"));
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(20).isAnnotationTrue() == false);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().size() == 1);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().size() == 65);
  }
	
	/*
   * method to test the inclusion of the inline annotations this is not allowed yet due to the fact that the monomer
   * unit is not finished
   */
	@Test
  	public void testInlineAnnoationsInBrackets() throws ExceptionState {
		test="RNA1{R(A)P.R(G)P.R(U\"mutation\")P.R(G)P.R(G)P.R(A)P.R(C)P.P(C)}$$$$V2.0";
		parser.parse(test);
  }  
	
	/*
	 * method to test the repeated monomer fragments
	 */
	@Test
  	public void testRepeatedFragments() throws ExceptionState {
		test="RNA1{R(A)P.(R(G)P.R(U)P.R(G)P)'15'.R(G)P.R(A)P.R(C)P.P(C)}$$$$V2.0";
		parser.parse(test);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(1).getCount().equals("15"));
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(0).getCount().equals("1"));
  } 
	
	/*
	 * method to test the repeated monomer fragments with an error
	 */
	@Test (expectedExceptions = SimplePolymerSectionException.class)
  	public void testRepeatedFragmentsWithException() throws ExceptionState {
		test="RNA1{R(A)P.(R(G)P.R(U)P.R(G)P)'15'R(G)P.R(A)P.R(C)P.P(C)}$$$$V2.0";
		parser.parse(test);
	} 
	
	/*
	 * method to test the inline annotation after a polymer
	 */
	@Test
  	public void testInlineAnnotationsPolymer() throws ExceptionState {
		test="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}\"HC\"|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.X.X.X.X.X.X.K.K.K}\"LC\"$$$$V2.0";
		parser.parse(test);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getAnnotation().equals("HC"));
	} 
	
	/*
	 * method to test the inline annotation after a polymer with an exception
	 */
	@Test (expectedExceptions = SimplePolymerSectionException.class)
  	public void testInlineAnnotationsPolymerWithException() throws ExceptionState {
		test="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}\"HC\"PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.X.X.X.X.X.X.K.K.K}\"LC\"$$$$V2.0";
		parser.parse(test);
	} 
	
	/*
	 * method to test the inline annotation after a polymer with unknown structure
	 * 
	 */
	@Test 
  public void testInlineAnnotationsPolymerUnknown() throws ExceptionState {
		test="PEPTIDE1{*}\"IL6\"$$$$V2.0";
		parser.parse(test);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(0).getCount().equals("1"));
	} 
	
	
	
	/*
	 * method to test the ambiguity
	 */
	@Test 
  	public void testLysineThereOrNot() throws ExceptionState {
		test="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.(_,K)}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}$$$$V2.0";
		parser.parse(test);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().size()
        - 1).getID().equals("_,K"));

    MonomerNotationGroup current =
        (MonomerNotationGroup) parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().size()
            - 1);
    Assert.assertTrue(current.getListOfElements().size() == 2);
    Assert.assertTrue(current.getListOfElements().get(0).getMonomer().getID().equals("_"));
    Assert.assertTrue(current.getListOfElements().get(1).getMonomer().getID().equals("K"));
    Assert.assertTrue(current instanceof MonomerNotationGroupOr);

	} 
	
	/*
	 * method to test the monomer repeating units
	 */
	@Test 
  	public void testMonomerRepeating() throws ExceptionState {
		test="PEPTIDE1{A.G.D.A'55'}$$$$V2.0";
		parser.parse(test);

    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().size()
        - 1).getCount().equals("55"));
	} 
	
	/*
	 * method to test the monomer repeating units
	 */
	@Test 
  	public void testMonomerRepeatingRange() throws ExceptionState {
		test="PEPTIDE1{A.G.[repeatingMonomer]'70-100'}$$$$V2.0";
		parser.parse(test);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().size()
        - 1).getCount().equals("70-100"));
	} 
	
	
	/*
	 * method to test the new simple polymer type blob
	 */
	@Test 
  	public void testNewSimplePolymerType() throws ExceptionState {
		test="BLOB1{Antibody}\"HER2\"$$$$V2.0";
		parser.parse(test);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerID().getID().equals("BLOB1"));
		
		
		test="BLOB1{Gold Particle}\"Gold 2.5nm\"$$$$V2.0"; 
		parser.parse(test);
		
		test="PEPTIDE1{A.G.C}|CHEM1{?}\"Payload\"|BLOB1{Antibody}\"Her2\"$BLOB1,CHEM1,?:?-1:?|G1,PEPTIDE1,?:?-K:R3$G1(BLOB1+CHEM1:4.5)$$V2.0";
		parser.parse(test);
	} 
	
	/*
	 * method to test the glycosylation
	 */
	@Test 
  	public void testGlycosylation() throws ExceptionState {
		test="CHEM1{BMA}|CHEM2{MAN}|CHEM3{MAN}|CHEM4{NAG}|CHEM5{FUL}|CHEM6{NAG}|CHEM7{NAG}|CHEM8{NAG}|CHEM9{FUL}|CHEM10{BMA}|CHEM11{BMA}|CHEM12{NAG}|CHEM13{NAG}|CHEM14{FUL}|CHEM15{NAG}|CHEM16{FUL}|CHEM17{NAG}|CHEM18{MAN}|CHEM19{BMA}|CHEM20{MAN}|CHEM21{MAN}|CHEM22{NAG}|CHEM23{NAG}|CHEM24{FUL}|CHEM25{MAN}|CHEM26{BMA}|CHEM27{NAG}|CHEM28{NAG}|PEPTIDE1{H.M.E.L.A.L.[Ngly].V.T.E.S.F.D.A.W.E.N.T.V.T.E.Q.A.I.E.D.V.W.Q.L.F.E.T.S.I.K.P.C.V.K.L.S.P.L.C.I.G.A.G.H.C.[Ngly].T.S.I.I.Q.E.S.C.D.K.H.Y.W.D.T.I.R.F.R.Y.C.A.P.P.G.Y.A.L.L.R.C.[Ngly].D.T.[Ngly].Y.S.G.F.M.P.K.C.S.K.V.V.V.S.S.C.T.R.M.M.E.T.Q.T.S.T.W.F.G.F.[Ngly].G.T.R.A.E.[Ngly].R.T.Y.I.Y.W.H.G.R.D.[Ngly].R.T.I.I.S.L.N.K.Y.Y.[Ngly].L.T.M.K.C.R.G.A.G.W.C.W.F.G.G.N.W.K.D.A.I.K.E.M.K.Q.T.I.V.K.H.P.R.Y.T.G.T.[Ngly].N.T.D.K.I.[Ngly].L.T.A.P.R.G.G.D.P.E.V.T.F.M.W.T.N.C.R.G.E.F.L.Y.C.K.M.N.W.F.L.N.W.V.E.D.R.D.V.T.N.Q.R.P.K.E.R.H.R.R.N.Y.V.P.C.H.I.R.Q.I.I.N.T.W.H.K.V.G.K.N.V.Y.L.P.P.R.E.G.D.L.T.C.[Ngly].S.T.V.T.S.L.I.A.N.I.D.W.T.D.G.[Ngly].Q.T.[Ngly].I.T.M.S.A.E.V.A.E.L.Y.R.L.E.L.G.D.Y.K.L.V.E.I.T}|CHEM29{NAG}|CHEM30{NAG}|CHEM31{BMA}|CHEM32{MAN}|CHEM33{MAN}|CHEM34{MAN}|CHEM35{NAG}|CHEM36{NAG}|CHEM37{BMA}|CHEM38{MAN}|CHEM39{BMA}|CHEM40{BMA}|CHEM41{MAN}|CHEM42{NAG}|CHEM43{NAG}|CHEM44{BMA}|CHEM45{NAG}|CHEM46{NAG}|CHEM47{FUL}|CHEM48{NDG}|CHEM49{NAG}|CHEM50{MAN}|CHEM51{BMA}|CHEM52{BMA}|CHEM53{NAG}|CHEM54{NDG}|CHEM55{FUL}$CHEM1,CHEM2,1:R2-1:R1|CHEM5,CHEM6,1:R1-1:R3|CHEM16,CHEM15,1:R1-1:R3|PEPTIDE1,CHEM30,52:R3-1:R1|PEPTIDE1,CHEM8,87:R3-1:R3|CHEM19,CHEM21,1:R3-1:R1|CHEM8,CHEM7,1:R1-1:R2|CHEM22,CHEM23,1:R1-1:R2|PEPTIDE1,CHEM28,292:R3-1:R1|PEPTIDE1,CHEM43,273:R3-1:R1|PEPTIDE1,CHEM46,146:R3-1:R1|PEPTIDE1,CHEM15,135:R3-1:R1|CHEM10,CHEM8,1:R1-1:R2|PEPTIDE1,CHEM36,289:R3-1:R1|CHEM51,CHEM50,1:R2-1:R1|CHEM39,CHEM41,1:R3-1:R1|PEPTIDE1,CHEM12,124:R3-1:R1|PEPTIDE1,CHEM29,7:R3-1:R1|CHEM39,CHEM38,1:R1-1:R2|CHEM20,CHEM19,1:R1-1:R2|CHEM17,CHEM15,1:R1-1:R2|CHEM12,CHEM13,1:R2-1:R1|CHEM46,CHEM47,1:R3-1:R1|CHEM32,CHEM31,1:R1-1:R3|CHEM52,CHEM51,1:R3-1:R1|PEPTIDE1,CHEM23,84:R3-1:R1|CHEM28,CHEM27,1:R2-1:R1|CHEM4,CHEM1,1:R2-1:R1|CHEM31,CHEM33,1:R2-1:R1|PEPTIDE1,CHEM54,190:R3-1:R1|CHEM34,CHEM33,1:R1-1:R2|CHEM18,CHEM17,1:R1-1:R2|CHEM31,CHEM35,1:R1-1:R2|CHEM9,CHEM7,1:R1-1:R3|CHEM26,CHEM27,1:R1-1:R2|CHEM54,CHEM53,1:R2-1:R1|CHEM6,CHEM4,1:R2-1:R1|CHEM1,CHEM3,1:R3-1:R1|CHEM25,CHEM26,1:R1-1:R2|CHEM54,CHEM55,1:R3-1:R1|CHEM43,CHEM42,1:R2-1:R1|CHEM40,CHEM37,1:R2-1:R1|CHEM46,CHEM45,1:R2-1:R1|CHEM12,CHEM14,1:R3-1:R1|CHEM42,CHEM40,1:R2-1:R1|PEPTIDE1,CHEM6,118:R3-1:R1|CHEM24,CHEM23,1:R1-1:R3|CHEM22,CHEM19,1:R2-1:R1|CHEM53,CHEM52,1:R2-1:R1|CHEM48,CHEM49,1:R2-1:R1|PEPTIDE1,CHEM48,184:R3-1:R1|CHEM45,CHEM44,1:R2-1:R1|CHEM37,CHEM38,1:R2-1:R1|CHEM11,CHEM10,1:R1-1:R2|CHEM36,CHEM35,1:R2-1:R1$$$V2.0";
		parser.parse(test);
	} 
	
	
	
}
