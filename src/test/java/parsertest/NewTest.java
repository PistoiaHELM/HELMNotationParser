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
package parsertest;



import parsertest.ExceptionParser.ExceptionState;

public class NewTest {

 // @Test
  public void f() throws ExceptionState {
	  ParserHELM2 parser = new ParserHELM2();
	  String test = "";
	 
	  
	  //Monomer repeating units
	  System.out.println("Monomer repeating units");
	  test="PEPTIDE1{A'23'.C.D'12'.E'24-25'}|PEPTIDE2{G'22'.C.S'8'.P.P.P.P.P.P.P.P.P.K'6'}$$$$V2.0";
	  parser.parse(test);
	  
	  test="PEPTIDE1{A'23'.C.D'12'.E'25-22'}|PEPTIDE2{G'22'.C.S'8'.P.P.P.P.P.P.P.P.P.K'6'}$$$$V2.0";
	  parser.parse(test);
	   
	  //Connection Ambiguity
	  System.out.println("Connection Ambiguity");
	  test="PEPTIDE1{A'23'.C.D'12'.E'22-25'}|PEPTIDE2{G'22'.C.S'8'.P.P.P.P.P.P.P.P.P.K'6'}$PEPTIDE0$$$V2.0";
	  parser.parse(test);
	  
	  test="PEPTIDE1{C.C.C.C.C.C}|BLOB1{Gold_Particle}\"Au10,Diameter:10nm\"$PEPTIDE1,BLOB1,C:R3-?:?$G${�Name�:�Gold particle conjugated with peptides�,�Load�:26}$V2.0";
	  parser.parse(test);
	  
	  //Extended Annotation
	  System.out.println("Extended Annotation");
	  test="PEPTIDE1{A'23'.C.D'12'.E'22-25'}|PEPTIDE2{G'22'.C.S'8'.P.P.P.P.P.P.P.P.P.K'6'}$$${\"PEPTIDE1\": {\"ChainType\": \"hc\" },\"PEPITDE2\": {\"ChainType\": \"lc\"}}$V2.0";
	  parser.parse(test);
	  
	  //Inline Annotations
	  System.out.println("Inline Annotations");
	  test ="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C\"mutation\".D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}$$$$V2.0";
	  parser.parse(test);
	  
	  test ="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}\"HC\"|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.X.X.X.X.X.X.K.K.K}\"LC\"$$$$V2.0";
	  parser.parse(test);
	  
	  
	  test ="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}}$PEPTIDE1,CHEM1,C:R3-1:R1\"Specific Conjugation\"$$$V2.0";
	  parser.parse(test);
	  
	  test ="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}|CHEM1{[*]SCCCc1ccccc1 |$_R1;;;;;;;;;;$|}$PEPTIDE1,CHEM1,C:R3-1:R1\"Specific Conjugation\"$$$V2.0";
	  parser.parse(test);
	  
	  
	  //Monomer ambiguity
	  System.out.println("Monomer ambiguity");
	  test = "PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.(_,K)}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}$$$$V2.0";
	  parser.parse(test);
	  //[Aha] als Monomer!!! noch einbauen
	  test = "PEPTIDE1{A.A.A.A.(A:1+G:1+X:1).A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}$$$$V2.0";
	  parser.parse(test);
	  
	  System.out.println("Testcases excel sheet");
	  //Testcases excel-sheet
	  /*Testcase 1*/
	  test = "PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}|PEPTIDE2{G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.G.C.S.S.S.S.S.S.S.S.S.P.P.P.P.P.P.P.P.P.K.K.K.K.K.K.K.K.K.K.K.K.K}$$$$V2.0";
	  parser.parse(test);
	  
	  /*Testcase 2*/
	  test = "PEPTIDE1{A'23'.C.D'12'.E'24'}|PEPTIDE2{G'22'.C.S'8'.P.P.P.P.P.P.P.P.P.K'6'}$$$$V2.0";
	  parser.parse(test);
	  
	  /*Testcase 3 -> error case*/
	  test="PEPTIDE1{A'23'.C.D.'12'.E'24'}|PEPTIDE2{G'22'.C.S'8'.P.P.P.P.P.P.P.P.P.K'6'}$$$$V2.0";
	  parser.parse(test);
	  
	  /*Testcase 4*/
	  test="RNA1{R(A)P.R(G)P.R(U)P.R(C)P.P(C)}|RNA2{R(U)P.R(G)P.R(G)P.R(G)P.R(G)P.R(A)P.R(G)}$$RNA1,RNA2,20:pair-8:pair|RNA1,RNA2, 17:pair-11:pair|RNA1,RNA2,8;pair-20:pair|RNA1,RNA2,14;pair-14:pair|RNA1,RNA2,11;pair-17:pair$RNA1{StrandType:ss}|RNA2{StrandType:as}$V2.0";
	  parser.parse(test);
	  
	  /*Testcase 5 -> error case!!!*/
	  test="RNA1{R(A)P.R(G)P.R(U)\"mutation\"P.R(C)P.P(C)}|RNA2{R(U)P.R(G)P.R(G)P.R(G)P.R(G)P.R(A)P.R(G)}$$RNA1,RNA2,20:pair-8:pair|RNA1,RNA2, 17:pair-11:pair|RNA1,RNA2,8;pair-20:pair|RNA1,RNA2,14;pair-14:pair|RNA1,RNA2,11;pair-17:pair$RNA1{StrandType:ss}|RNA2{StrandType:as}$V2.0";
	  parser.parse(test);
	  
	  /*Testcase 6*/
	  test="PEPTIDE1{A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.A.C\"mutation\".D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.D.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E}V2.0";
	  parser.parse(test);
	  
	  /*Testcase 7*/
	  test="RNA1{R(A)P.R(G)P.R(U\"mutation\")P.R(G)P.R(G)P.R(A)P.R(C)P.P(C)}V2.0";
	  parser.parse(test);
	  
	  /*Testcase 8*/
	  test="";
	  //parser.parse(test);
	  
  }
}
