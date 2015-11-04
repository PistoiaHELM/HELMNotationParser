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
package parsertest.GroupingSectionTest;

import org.testng.annotations.Test;

import parsertest.ParserHELM2;
import parsertest.ExceptionParser.ExceptionState;

public class TestInputGroupingSection {


	ParserHELM2 parser = new ParserHELM2();
	String test;  
	
	
	/*
	 * method to test the ambiguity in the grouping section
	 * 
	 */
	@Test
  	public void testGroupingAmbiguityOr() throws ExceptionState {
		test="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}|CHEM1{[[*]SCCCc1ccccc1 |$_R1;;;;;;;;;;$|]}$G1,CHEM1,C:R3-1:R1$G1(PEPTIDE1,PEPTIDE2)$$V2.0";
		parser.parse(test);
	} 
	
	/*
	 * method to test the ambiguity in the grouping section
	 * 
	 */
	@Test
  	public void testGroupingAmbiguityAnd() throws ExceptionState {
		test="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}|CHEM1{[[*]SCCCc1ccccc1 |$_R1;;;;;;;;;;$|]}$G1,CHEM1,C:R3-1:R1$G1(PEPTIDE1+PEPTIDE2)$$V2.0";
		parser.parse(test);
	} 
	
	/*
	 * method to test the ratio in the grouping section
	 * 
	 */
	@Test
  	public void testGroupingAmbiguityAndRatio() throws ExceptionState {
		test="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}|CHEM1{Dig}|CHEM2{Dig}$PEPTIDE1,CHEM1,C:R3-1:R1|PEPTIDE2,CHEM2,C:R3-1:R1$G1(PEPTIDE1+CHEM1:1.5)|G2(PEPTIDE2+CHEM2:1.5)$$V2.0";
		parser.parse(test);
		test="PEPTIDE1{Flu}|CHEM1{?}$PEPTIDE1,CHEM1,C:R3-1:R1\"annotation\"$G1(PEPTIDE1+CHEM1:2.5)$$V2.0";
		parser.parse(test);
		test="PEPTIDE1{Flu}|CHEM1{?}$PEPTIDE1,CHEM1,C:R3-1:R1\"annotation\"$G1(PEPTIDE1:2.5+CHEM1)$$V2.0";
		parser.parse(test);
		
	}
	
	/*
	 * method to test the range ratio in the grouping section
	 * 
	 */
	@Test
  	public void testGroupingAmbiguityAndRatioAndRange() throws ExceptionState {
		test="PEPTIDE1{Flu}|CHEM1{?}$PEPTIDE1,CHEM1,C:R3-1:R1\"annotation\"$G1(PEPTIDE1:1.5-2.5+CHEM1)$$V2.0";
		parser.parse(test);
		
	}
	
	
	/*
	 * method to test the ambiguity for unknown type
	 * 
	 */
	@Test
  	public void testGroupingUnknownRatio() throws ExceptionState {
		test="PEPTIDE1{C.C.C.C.C.C}|PEPTIDE2{A.C.A}|BLOB1{Gold Particle}\"Au10,Diameter:10nm\"$G1,BLOB1,C:R3-?:R1$G1(PEPTIDE1:49+PEPTIDE2:51)|G2(G1:24+BLOB1)$$V2.0";
		parser.parse(test);
		
	}
	
	
	
	
	
	
}
