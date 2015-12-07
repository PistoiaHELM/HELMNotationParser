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
package parsertest.annotationsectiontest;

import java.io.IOException;

import org.helm.notation2.parser.ParserHELM2;
import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.jdom2.JDOMException;
import org.testng.annotations.Test;

public class TestInputAnnotationSection {

	ParserHELM2 parser = new ParserHELM2();
	String test;
	
	/*
	 * method to test the annotation
	 */
	@Test 
  public void testSimpleInput() throws ExceptionState, IOException, JDOMException {
		test="PEPTIDE1{C.C.C.C.C.C}|BLOB1{Gold_Particle}\"Au10,Diameter:10nm\"$PEPTIDE1,BLOB1,C:R3-?:?$G1(PEPTIDE1:20-34+BLOB1)${�Name�:�Gold particle conjugated with peptides�,�Load�:26}$V2.0";
		parser.parse(test);
		

    test =
        "PEPTIDE1{C.C.C.C.C.C}|PEPTIDE2{A.C.A.A.A.A}|BLOB1{Bead}$PEPTIDE1,BLOB1,2:R3-?:R1$$PEPTIDE1{Type:Peptide,Name:Gold-conjugated peptide}|BLOB1{Type:Gold particle,Name:Au10,Diameter:10nm}$V2.0";
		parser.parse(test);
		
		test= "RNA1{R(A)P.R(G)P}|RNA2{R(A)P.R(G)P}|PEPTIDE1{A.G.C.H.E}|CHEM1{?}\"Lipid_A\"$PEPTIDE1,CHEM1,3:R3-?:?$G1(RNA1+RNA2:2)|G2(G1+PEPTIDE1:5.0)${�Name�:�lipid nanoparticle with RNA payload and peptide ligand�}$V2.0";
		parser.parse(test);
  }
  
	
}
