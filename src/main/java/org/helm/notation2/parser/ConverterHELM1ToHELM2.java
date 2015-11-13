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

import java.util.ArrayList;


/**
 * ConverterHELM1ToHELM2
 * 
 * @author hecht
 */
public class ConverterHELM1ToHELM2 {

  private String HELM1 = "";

  private String HELM2 = "";

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
    HELM1 = str;
    /*simple add character -> split works then*/
    HELM1 += "f";
    String[] sections = HELM1.split("}\\$");

    /* Section 1 is accepted in full, no changes necessary, Section 1 has to be there */
    HELM2 = sections[0] + "}$";
    HELM1 = "$" + sections[1];
    sections = HELM1.split("\\$");

     /*
     * Section 2 connection section; section 3 is added to the second section,
     * Section 2 is not necessary, is this section really there
     */
    ArrayList<String> group = new ArrayList<String>();
     if (sections.length >= 2) {
       if (!(sections[1].isEmpty())) {
        HELM2 += sections[1];
       }
     }

    System.out.println();
     /* Add hydrogen bonds to the connection section */
     if (sections.length >= 3) {
       if (!(sections[2].isEmpty())) {
        
         if (!(sections[1].isEmpty())) {
           HELM2 += "|" + sections[2];
         } else {
           HELM2 += sections[2];
         }
       }
       
       /*Group section*/
      HELM2 += "$";
      if (!(group.isEmpty())) {
        String notation = "";
        for (int i = 0; i < group.size(); i++) {
          notation += group.get(i) + "|";
        }
        notation = notation.substring(0, notation.length() - 1);
        HELM2 += notation;
      }

      HELM2 += "$";
       
       /*Add annotation to the annotation section*/
       if (sections.length >= 4) {
         if (!(sections[3].isEmpty())) {
           HELM2 += sections[3];
         }
       }

     }
     
    /* Add version number to indicate HELM2 notation */
     HELM2 += "$V2.0";

      return HELM2;

 
  }
}

