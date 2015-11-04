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

import parsertest.Notation.Polymer.MonomerNotationGroupElement;
import parsertest.Notation.Polymer.MonomerNotationUnit;

public class MonomerNotationGroupGroupElementTest {

  @Test
  public void testConstructor() {
    MonomerNotationUnit unit = new MonomerNotationUnit("R");
    MonomerNotationGroupElement element = new MonomerNotationGroupElement(unit, 1, true);
    Assert.assertEquals(element.getValue().get(0), 1.0);
    Assert.assertTrue(element.getValue().size() == 1);
    Assert.assertEquals(element.getMonomer(), unit);
    Assert.assertFalse(element.isInterval());
  }

  @Test
  public void testConstructorRange() {
    MonomerNotationUnit unit = new MonomerNotationUnit("R");
    MonomerNotationGroupElement element = new MonomerNotationGroupElement(unit, 1, 3);

    Assert.assertEquals(element.getValue().get(1), 3.0);
    Assert.assertTrue(element.getValue().size() == 2);
    Assert.assertEquals(element.getMonomer(), unit);
    Assert.assertTrue(element.isInterval());
  }


}
