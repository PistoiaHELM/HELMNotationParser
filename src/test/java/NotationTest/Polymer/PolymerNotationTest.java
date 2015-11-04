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

import org.testng.Assert;
import org.testng.annotations.Test;

import parsertest.ExceptionParser.NotationException;
import parsertest.Notation.Polymer.BlobEntity;
import parsertest.Notation.Polymer.ChemEntity;
import parsertest.Notation.Polymer.PeptideEntity;
import parsertest.Notation.Polymer.PolymerNotation;
import parsertest.Notation.Polymer.RNAEntity;

/**
 * PolymerNotationTest
 * 
 * @author hecht
 */
public class PolymerNotationTest {

  @Test
  public void testPolymerNotationIDPEPTIDE() throws NotationException {
    String name = "PEPTIDE1";

    PolymerNotation current = new PolymerNotation(name);
    Assert.assertTrue(current.getPolymerID() instanceof PeptideEntity);
  }

  @Test
  public void testPolymerNotationIDRNA() throws NotationException {
    String name = "RNA1";

    PolymerNotation current = new PolymerNotation(name);
    Assert.assertTrue(current.getPolymerID() instanceof RNAEntity);
  }

  @Test
  public void testPolymerNotationIDCHEM() throws NotationException {
    String name = "CHEM1";
    PolymerNotation current = new PolymerNotation(name);
    Assert.assertTrue(current.getPolymerID() instanceof ChemEntity);
  }

  @Test
  public void testPolymerNotationIDBLOB() throws NotationException {
    String name = "BLOB1";

    PolymerNotation current = new PolymerNotation(name);
    Assert.assertTrue(current.getPolymerID() instanceof BlobEntity);
  }

  @Test(expectedExceptions = NotationException.class)
  public void testPolymerNotationIDBLOBWithException() throws NotationException {
    String name = "BLO1";
    PolymerNotation current = new PolymerNotation(name);
  }

  @Test
  public void testPolymerNotationAnnotation() throws NotationException {
    String name = "RNA1";
    PolymerNotation pre = new PolymerNotation(name);
    PolymerNotation current = new PolymerNotation(pre.getPolymerID(), pre.getPolymerElements(),
        "Annotation");
    Assert.assertEquals(current.getAnnotation(), "Annotation");
    Assert.assertTrue(current.isAnnotationTrue());

  }

}
