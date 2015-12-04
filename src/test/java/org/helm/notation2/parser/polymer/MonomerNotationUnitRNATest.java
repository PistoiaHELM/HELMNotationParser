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
import org.helm.notation2.parser.notation.polymer.MonomerNotationUnitRNA;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MonomerNotationUnitRNATest {

  /**
   * method to test the constructor of the MonomerNotationUnitRNA class
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationUnitRNA() throws IOException, NotationException {
    String test = "R(A)P";
    MonomerNotationUnitRNA unit = new MonomerNotationUnitRNA(test, "RNA");
    Assert.assertEquals(unit.getID(), test);
  }

  /**
   * method to test the constructor of the MonomerNotationUnitRNA class
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationUnitRNAContents() throws IOException, NotationException {
    String test = "R(A)P";
    MonomerNotationUnitRNA unit = new MonomerNotationUnitRNA(test, "RNA");
    Assert.assertEquals(unit.getContents().get(0).getID(), "R");
    Assert.assertEquals(unit.getContents().get(1).getID(), "A");
    Assert.assertEquals(unit.getContents().get(2).getID(), "P");
  }

  /**
   * method to test the constructor of the MonomerNotationUnitRNA class
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationUnitRNAContentsSpecial() throws IOException, NotationException {
    String test = "RP";
    MonomerNotationUnitRNA unit = new MonomerNotationUnitRNA(test, "RNA");
    Assert.assertEquals(unit.getContents().get(0).getID(), "R");
    Assert.assertEquals(unit.getContents().get(1).getID(), "P");
  }

  /**
   * method to test the constructor of the MonomerNotationUnitRNA class
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationUnitRNAContentsSpecialTwo() throws IOException, NotationException {
    String test = "[dR]P";
    MonomerNotationUnitRNA unit = new MonomerNotationUnitRNA(test, "RNA");
    Assert.assertEquals(unit.getContents().get(0).getID(), "[dR]");
    Assert.assertEquals(unit.getContents().get(1).getID(), "P");
  }

  /**
   * method to test the constructor of the MonomerNotationUnitRNA class
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationUnitRNAContentsSpecialThree() throws IOException, NotationException {
    String test = "RP";
    MonomerNotationUnitRNA unit = new MonomerNotationUnitRNA(test, "RNA");
    Assert.assertEquals(unit.getContents().get(0).getID(), "R");
    Assert.assertEquals(unit.getContents().get(1).getID(), "P");
  }

  /**
   * method to test the constructor of the MonomerNotationUnitRNA class
   * 
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationUnitRNAContentsSpecialFour() throws IOException, NotationException {
    String test = "R([dabA])P";
    MonomerNotationUnitRNA unit = new MonomerNotationUnitRNA(test, "RNA");
    Assert.assertEquals(unit.getContents().get(0).getID(), "R");
    Assert.assertEquals(unit.getContents().get(1).getID(), "[dabA]");
    Assert.assertEquals(unit.getContents().get(2).getID(), "P");
  }

  /**
   * method to test the constructor of the MonomerNotationUnitRNA class
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationUnitRNAContentsSpecialFive() throws IOException, NotationException {
    String test = "R([dabA])P";
    MonomerNotationUnitRNA unit = new MonomerNotationUnitRNA(test, "RNA");
    Assert.assertEquals(unit.getContents().get(0).getID(), "R");
    Assert.assertEquals(unit.getContents().get(1).getID(), "[dabA]");
    Assert.assertEquals(unit.getContents().get(2).getID(), "P");
  }

  /**
   * method to test the constructor of the MonomerNotationUnitRNA class
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationUnitRNAContentsSpecialSix() throws IOException, NotationException {
    String test = "[sP]";
    MonomerNotationUnitRNA unit = new MonomerNotationUnitRNA(test, "RNA");
    Assert.assertEquals(unit.getContents().get(0).getID(), "[sP]");
  }

  /**
   * method to test the constructor of the MonomerNotationUnitRNA class
   * 
   * @throws IOException
   * @throws NotationException
   */
  @Test
  public void testMonomerNotationUnitRNAContentsSpecialSeven() throws IOException, NotationException {
    String test = "R[sP]";
    MonomerNotationUnitRNA unit = new MonomerNotationUnitRNA(test, "RNA");
    Assert.assertEquals(unit.getContents().get(0).getID(), "R");
  }
}
