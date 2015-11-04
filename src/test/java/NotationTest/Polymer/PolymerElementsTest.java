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

import parsertest.ExceptionParser.SimplePolymerSectionException;
import parsertest.Notation.Polymer.MonomerNotationUnit;
import parsertest.Notation.Polymer.PolymerListElements;
import parsertest.Notation.Polymer.PolymerSingleElements;

public class PolymerElementsTest {
  @Test
  public void testConstructorList() {
    PolymerListElements item = new PolymerListElements();
    MonomerNotationUnit not = new MonomerNotationUnit("U");
    item.addMonomer("K");
    item.addMonomer("A");
    Assert.assertTrue(item.getListOfElements().size() == 2);
    Assert.assertEquals(item.getCurrentMonomer().getID(), "A");
    item.changeMonomer(not);
    Assert.assertEquals(item.getCurrentMonomer(), not);
  }

  @Test(expectedExceptions = SimplePolymerSectionException.class)
  public void testConstructorSingle() throws SimplePolymerSectionException {
    PolymerSingleElements item = new PolymerSingleElements();
    item.addMonomer("K");
    item.addMonomer("A");

  }
}
