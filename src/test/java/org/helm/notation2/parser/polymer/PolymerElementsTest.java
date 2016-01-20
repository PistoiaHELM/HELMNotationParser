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
package org.helm.notation2.parser.polymer;

import java.io.IOException;

import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.exceptionparser.SimplePolymerSectionException;
import org.helm.notation2.parser.notation.polymer.MonomerNotationUnit;
import org.helm.notation2.parser.notation.polymer.PeptideEntity;
import org.helm.notation2.parser.notation.polymer.PolymerListElements;
import org.helm.notation2.parser.notation.polymer.PolymerSingleElements;
import org.helm.notation2.parser.notation.polymer.RNAEntity;
import org.jdom2.JDOMException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PolymerElementsTest {
  @Test
  public void testConstructorList() throws IOException, NotationException, JDOMException {
    PolymerListElements item = new PolymerListElements(new RNAEntity("RNA1"));
    MonomerNotationUnit not = new MonomerNotationUnit("U", "RNA");
    item.addMonomerNotation("U");
    item.addMonomerNotation("A");
    Assert.assertTrue(item.getListOfElements().size() == 2);
    Assert.assertEquals(item.getCurrentMonomerNotation().getID(), "A");
    item.changeMonomerNotation(not);
    Assert.assertEquals(item.getCurrentMonomerNotation(), not);
  }

  @Test(expectedExceptions = SimplePolymerSectionException.class)
  public void testConstructorSingle() throws SimplePolymerSectionException,
      NotationException, IOException, JDOMException {
    PolymerSingleElements item = new PolymerSingleElements(new PeptideEntity(
        "PEPTIDE1"));
    item.addMonomerNotation("K");
    item.addMonomerNotation("A");

  }
}
