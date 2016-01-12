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
package org.helm.notation2.parser.grouping;

import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.notation.grouping.GroupingElement;
import org.helm.notation2.parser.notation.polymer.PeptideEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupingElementTest {
  @Test
  public void testConstructor() throws NotationException {
    GroupingElement elem = new GroupingElement("PEPTIDE1", 3, true);
    Assert.assertEquals(elem.getValue().get(0), 3.0);
    Assert.assertTrue(elem.getValue().size() == 1);
    Assert.assertTrue(elem.getID() instanceof PeptideEntity);
  }

  @Test
  public void testConstructorTwo() throws NotationException {
    GroupingElement elem = new GroupingElement("PEPTIDE1", 3, 4);
    Assert.assertEquals(elem.getValue().get(0), 3.0);
    Assert.assertEquals(elem.getValue().get(1), 4.0);
    Assert.assertTrue(elem.getValue().size() == 2);
    Assert.assertTrue(elem.getID() instanceof PeptideEntity);
  }
}