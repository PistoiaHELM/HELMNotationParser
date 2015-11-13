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



import java.io.IOException;

import org.helm.notation2.parser.ParserHELM2;
import org.helm.notation2.parser.ExceptionParser.ExceptionState;
import org.helm.notation2.parser.ExceptionParser.SimplePolymerSectionException;
import org.helm.notation2.parser.Notation.Polymer.MonomerNotationGroup;
import org.helm.notation2.parser.Notation.Polymer.MonomerNotationGroupOr;
import org.jdom.JDOMException;
import org.testng.Assert;
import org.testng.annotations.Test;



public class TestInputSimplePolymerSection {
	ParserHELM2 parser = new ParserHELM2();
	String test;
	

	/*
	 * method to test unchanged input in the simple polymer section
	 */
	@Test 
  public void testSimpleInput() throws ExceptionState, IOException, JDOMException {
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
  public void testMonomerRepeatingUnits() throws ExceptionState, IOException, JDOMException {
		test="PEPTIDE1{A'23'.C.D'12'.E'24-25'}|PEPTIDE2{G'22'.C.S'8'.P.P.P.P.P.P.P.P.P.K'6'}$$$$V2.0";
		parser.parse(test);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(0).getCount().equals("23"));
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(3).getCount().equals("24-25"));
  }
	
	/*
	 * method to test the new repeating function with exception
	 */
	@Test (expectedExceptions = SimplePolymerSectionException.class)
  public void testMonomerRepeatingUnitsWithException() throws ExceptionState, IOException, JDOMException {
		test="PEPTIDE1{A'23'.C.D.'12'.E'24'}|PEPTIDE2{G'22'.C.S'8'.P.P.P.P.P.P.P.P.P.K'6'}$$$$V2.0";
		parser.parse(test);
  }

 
	/*
   * method to test the inclusion of the inline annotations for one monomer
   */
  @Test(expectedExceptions = SimplePolymerSectionException.class)
  public void testInlineAnnoationsWithException() throws ExceptionState, IOException, JDOMException {
		test="RNA1{R(A)P.R(G)P.R(U)\"mutation\"P.R(C)P.P(C)}|RNA2{R(U)P.R(G)P.R(G)P.R(G)P.R(G)P.R(A)P.R(G)}$RNA1,RNA2,20:pair-8:pair|RNA1,RNA2,17:pair-11:pair|RNA1,RNA2,8:pair-20:pair|RNA1,RNA2,14:pair-14:pair|RNA1,RNA2,11:pair-17:pair$$RNA1{StrandType:ss}|RNA2{StrandType:as}$V2.0";
		parser.parse(test);
  }  
	
	/*
   * method to test the inclusion of the inline annotations for one monomer
   */
	@Test
  public void testInlineAnnotations() throws ExceptionState, IOException, JDOMException {
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
  public void testInlineAnnoationsInBrackets() throws ExceptionState, IOException, JDOMException {
		test="RNA1{R(A)P.R(G)P.R(U\"mutation\")P.R(G)P.R(G)P.R(A)P.R(C)P.P(C)}$$$$V2.0";
		parser.parse(test);
  }  
	
	/*
	 * method to test the repeated monomer fragments
	 */
	@Test
  public void testRepeatedFragments() throws ExceptionState, IOException, JDOMException {
    test =
        "RNA1{R(A)P.(R(G)P.R(U)P.R(G)P)'15'.R(G)P.R(A)P.R(C)P.P(C)}|PEPTIDE1{(A.T)'5'.K}$$$$V2.0";
		parser.parse(test);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(1).getCount().equals("15"));
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(0).getCount().equals("1"));
    System.out.println(parser.getJSON());
  } 
	
	/*
	 * method to test the repeated monomer fragments with an error
	 */
  @Test(expectedExceptions = SimplePolymerSectionException.class)
  public void testRepeatedFragmentsWithException() throws ExceptionState, IOException, JDOMException {
    test = "RNA1{R(A)P.(R(G)P.R(U)P.R(G)P)'15'R(G)P.R(A)P.R(C)P.P(C)}$$$$V2.0";
		parser.parse(test);
	} 

	
	/*
	 * method to test the inline annotation after a polymer
	 */
	@Test
  public void testInlineAnnotationsPolymer() throws ExceptionState, IOException, JDOMException {
		test="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}\"HC\"|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.X.X.X.X.X.X.K.K.K}\"LC\"$$$$V2.0";
		parser.parse(test);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getAnnotation().equals("HC"));
	} 
	
	/*
	 * method to test the inline annotation after a polymer with an exception
	 */
	@Test (expectedExceptions = SimplePolymerSectionException.class)
  public void testInlineAnnotationsPolymerWithException() throws ExceptionState, IOException, JDOMException {
		test="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}\"HC\"PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.X.X.X.X.X.X.K.K.K}\"LC\"$$$$V2.0";
		parser.parse(test);
	} 
	
	/*
	 * method to test the inline annotation after a polymer with unknown structure
	 * 
	 */
	@Test 
  public void testInlineAnnotationsPolymerUnknown() throws ExceptionState, IOException, JDOMException {
		test="PEPTIDE1{*}\"IL6\"$$$$V2.0";
		parser.parse(test);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(0).getCount().equals("1"));
	} 
	
	
	
	/*
	 * method to test the ambiguity
	 */
	@Test 
  public void testLysineThereOrNot() throws ExceptionState, IOException, JDOMException {
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
  public void testMonomerRepeating() throws ExceptionState, IOException, JDOMException {
		test="PEPTIDE1{A.G.D.A'55'}$$$$V2.0";
		parser.parse(test);

    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().size()
        - 1).getCount().equals("55"));
	} 
	
	/*
	 * method to test the monomer repeating units
	 */
	@Test 
  public void testMonomerRepeatingRange() throws ExceptionState, IOException, JDOMException {
    test = "PEPTIDE1{A.G.R'70-100'}$$$$V2.0";
		parser.parse(test);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().get(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerElements().getListOfElements().size()
        - 1).getCount().equals("70-100"));
	} 
	
	
	/*
	 * method to test the new simple polymer type blob
	 */
	@Test 
  public void testNewSimplePolymerType() throws ExceptionState, IOException, JDOMException {
		test="BLOB1{Antibody}\"HER2\"$$$$V2.0";
		parser.parse(test);
    Assert.assertTrue(parser.getHELM2Notation().getListOfPolymers().get(0).getPolymerID().getID().equals("BLOB1"));
		
		
		test="BLOB1{Gold Particle}\"Gold 2.5nm\"$$$$V2.0"; 
		parser.parse(test);
		
		test="PEPTIDE1{A.G.C}|CHEM1{?}\"Payload\"|BLOB1{Antibody}\"Her2\"$BLOB1,CHEM1,?:?-1:?|G1,PEPTIDE1,?:?-K:R3$G1(BLOB1+CHEM1:4.5)$$V2.0";
		parser.parse(test);
	} 
	
	
	
	

}
