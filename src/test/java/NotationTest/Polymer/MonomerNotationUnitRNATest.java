package NotationTest.Polymer;

import java.io.IOException;

import org.helm.notation2.parser.ExceptionParser.NotationException;
import org.helm.notation2.parser.Notation.Polymer.MonomerNotationUnitRNA;
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
