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
package NotationTest.Polymer;

import java.io.IOException;

import org.helm.notation2.parser.ExceptionParser.NotationException;
import org.helm.notation2.parser.Notation.Polymer.MonomerNotationUnit;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * MonomerNotationTest
 * 
 * @author hecht
 */
public class MonomerNotationTest {

  /**
   * method to test the constructor of the MonomerNotation class
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationID() throws IOException, NotationException {
    String test = "A";
    MonomerNotationUnit unit = new MonomerNotationUnit(test, "PEPTIDE");
    Assert.assertEquals(unit.getID(), test);
  }

  /**
   * method to test the constructor of the MonomerNotation class
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationID_() throws IOException, NotationException {
    String test = "_";
    MonomerNotationUnit unit = new MonomerNotationUnit(test, "PEPTIDE");
    Assert.assertEquals(unit.getCount(), "0");
  }

  /**
   * method to test the constructor of the MonomerNotation class
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationIDValue() throws IOException, NotationException {
    String test = "?";
    MonomerNotationUnit unit = new MonomerNotationUnit(test, "PEPTIDE");
    Assert.assertEquals(unit.getCount(), "0..n");
  }

  /**
   * method to test the default count of the MonomerNotation class
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationDefaultCount() throws IOException, NotationException {
    String test = "A";
    MonomerNotationUnit unit = new MonomerNotationUnit(test, "PEPTIDE");
    Assert.assertEquals(unit.getCount(), "1");
  }

  /**
   * method to test setting a new count
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationCount() throws IOException, NotationException {
    String id = "A";
    String test = "5";
    MonomerNotationUnit unit = new MonomerNotationUnit(id, "PEPTIDE");
    unit.setCount(test);
    Assert.assertEquals(unit.getCount(), test);
  }

  /**
   * method to test the default annotation the annotation should be null and the
   * boolean false
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationDefaultAnnotation() throws IOException, NotationException {
    String id = "A";
    MonomerNotationUnit unit = new MonomerNotationUnit(id, "PEPTIDE");
    Assert.assertEquals(unit.getAnnotation(), null);
    Assert.assertFalse(unit.isAnnotationTrue());
  }

  /**
   * method to test setting a new annotation
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationAnnotation() throws IOException, NotationException {
    String id = "A";
    String test = "5";
    MonomerNotationUnit unit = new MonomerNotationUnit(id, "PEPTIDE");
    unit.setAnnotation(test);
    Assert.assertEquals(unit.getAnnotation(), test);
    Assert.assertTrue(unit.isAnnotationTrue());
  }

}
