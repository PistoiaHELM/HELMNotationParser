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
package NotationTest.Grouping;

import org.testng.Assert;
import org.testng.annotations.Test;

import parsertest.ExceptionParser.NotationException;
import parsertest.Notation.Grouping.GroupingMixture;
import parsertest.Notation.Grouping.GroupingNotation;
import parsertest.Notation.Grouping.GroupingOr;
import parsertest.Notation.Polymer.GroupEntity;

public class GroupingNotationTest {

  @Test
  public void testConstructor() throws NotationException {
    GroupingNotation current = new GroupingNotation("G1");
    Assert.assertTrue(current.getGroupID() instanceof GroupEntity);
  }

  @Test(expectedExceptions = NotationException.class)
  public void testConstructorWithException() throws NotationException {
    GroupingNotation current = new GroupingNotation("1");
  }

  @Test
  public void testConstructorAmbiguityAND() throws NotationException {
    GroupingNotation pre = new GroupingNotation("G1");
    GroupingNotation current = new GroupingNotation(pre.getGroupID(), "PEPTIDE1+PEPTIDE2");
    Assert.assertTrue(current.getAmbiguity() instanceof GroupingMixture);
  }

  @Test
  public void testConstructorAmbiguityOR() throws NotationException {
    GroupingNotation pre = new GroupingNotation("G1");
    GroupingNotation current = new GroupingNotation(pre.getGroupID(), "PEPTIDE1,PEPTIDE2");
    Assert.assertTrue(current.getAmbiguity() instanceof GroupingOr);
  }
}
