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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.testng.annotations.Test;

import parsertest.ParserHELM2;
import parsertest.ExceptionParser.ExceptionState;

/**
 * code NotationTest
 * 
 * @author hecht
 */
public class NotationTest {

  ParserHELM2 parser;

  /*
   * method to test the positive excel-sheet input
   */
  @Test
  public void testPositiveInput() throws ExceptionState {
    BufferedReader br = null;
    String test;
    int count = 0;
    try {

      String sCurrentLine;
      br = new BufferedReader(new FileReader("positive_testcases.txt"));

      while ((sCurrentLine = br.readLine()) != null) {
        if(count >0){
          test = sCurrentLine + "V2.0";
          parser = new ParserHELM2();
          parser.parse(test);

        }
        count += 1;
      }



    } catch (IOException e) {
      e.printStackTrace();
    }



  }

  /*
   * method to test the positive excel-sheet input
   */
  @SuppressWarnings("resource")
  @Test(expectedExceptions = ExceptionState.class)
  public void testNegativeInput() throws ExceptionState {
    BufferedReader br = null;
    String test;
    int count = 0;
    int count_error = 0;
    try {

      String sCurrentLine;
      br = new BufferedReader(new FileReader("negative_testcases.txt"));

      while ((sCurrentLine = br.readLine()) != null) {
        if (count > 0) {
          test = sCurrentLine + "V2.0";
          try {
            parser = new ParserHELM2();
            parser.parse(test);
            // System.out.println(parser.getHELM2Notation().getListOfConnections().size());
          } catch (ExceptionState e) {
            count_error += 1;
          }
        }
        count += 1;
        
        /* one negative testcase has to be handled in the MonomerDatabase */
        if (count_error == 8) {
          throw new ExceptionState("");
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
