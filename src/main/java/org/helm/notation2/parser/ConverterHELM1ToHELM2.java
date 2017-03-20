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


/**
 * ConverterHELM1ToHELM2
 * 
 * @author hecht
 */
public class ConverterHELM1ToHELM2 {



  /**
   * Constructor to convert HELM1 into HELM2
   */
  public ConverterHELM1ToHELM2() {

  }

  /**
   * method to convert the given string into the HELM2 format
   * 
   * @param str HELM1 Notation
   * @return HELM2 Notation
   */
  public String doConvert(String str) {

	//check for just missing V2.0
	  
	  ParserHELM2 parser = new ParserHELM2();
	  try{
	  parser.parse(str + "V2.0");
	  return str + "V2.0";
	  }
	  catch(Exception e){
		
    /* Use String Builder */
    /* simple add character -> split works then */
    String helm1 = str + "f";


    StringBuilder helm2 = new StringBuilder();
    String[] sections = helm1.split("}\\$");

    /* Section 1 is accepted in full, no changes necessary, Section 1 has to be there */
    helm2.append(sections[0] + "}$");

    StringBuilder sb = new StringBuilder();
    for (int i = 1; i < sections.length; i++) {
      sb.append(sections[i] + "}$");
    }

    helm1 = "$" + sb.toString();
    sections = helm1.split("\\$");

     /*
     * Section 2 connection section; section 3 is added to the second section,
     * Section 2 is not necessary, is this section really there
     */
     if (sections.length >= 2) {
       if (!(sections[1].isEmpty())) {
        helm2.append(sections[1]);
       }
     }

     /* Add hydrogen bonds to the connection section */
     if (sections.length >= 3) {
       if (!(sections[2].isEmpty())) {
        
         if (!(sections[1].isEmpty())) {
          helm2.append("|" + sections[2]);
         } else {
          helm2.append(sections[2]);
         }
       }
       
       /*Group section*/
      helm2.append("$");

      helm2.append("$");
       
       /*Add annotation to the annotation section*/
       if (sections.length >= 4) {
         if (!(sections[3].isEmpty())) {
          helm2.append(sections[3]);
         }
       }

     }
     
    /* Add version number to indicate HELM2 notation */
    helm2.append("$V2.0");
    return helm2.toString();
	  }
 
  }
}

