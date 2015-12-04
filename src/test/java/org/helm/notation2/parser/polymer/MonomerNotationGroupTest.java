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
import org.helm.notation2.parser.notation.polymer.MonomerNotationGroupMixture;
import org.helm.notation2.parser.notation.polymer.MonomerNotationGroupOr;
import org.jdom.JDOMException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MonomerNotationGroupTest {

  @Test
  public void MonomerNotationGroupTestOr() throws NotationException, IOException, JDOMException {
    MonomerNotationGroupOr group = new MonomerNotationGroupOr("K,R", "PEPTIDE");
    Assert.assertTrue(group.getListOfElements().size() == 2);
    Assert.assertEquals(group.getListOfElements().get(0).getMonomerNotation().getID(), "K");
    Assert.assertEquals(group.getListOfElements().get(0).getValue().get(0), 1.0);
  }

  @Test
  public void MonomerNotationGroupTestAND() throws NotationException, IOException, JDOMException {
    MonomerNotationGroupMixture group = new MonomerNotationGroupMixture("K+R",
        "PEPTIDE");
    Assert.assertTrue(group.getListOfElements().size() == 2);
    Assert.assertEquals(group.getListOfElements().get(0).getMonomerNotation().getID(), "K");
    Assert.assertEquals(group.getListOfElements().get(0).getValue().get(0), 1.0);
  }
}
