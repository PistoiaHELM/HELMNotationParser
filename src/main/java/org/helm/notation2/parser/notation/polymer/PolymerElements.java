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
package org.helm.notation2.parser.notation.polymer;

import java.io.IOException;
import java.util.List;

import org.helm.notation2.parser.exceptionparser.HELM1ConverterException;
import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.exceptionparser.SimplePolymerSectionException;
import org.jdom2.JDOMException;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * PolymerElements
 * 
 * @author hecht
 */
public abstract class PolymerElements {

  protected List<MonomerNotation> listMonomerNotations;

  protected HELMEntity entity;

  public PolymerElements(HELMEntity entity) {
    this.entity = entity;
  }

  /**
   * method to get a list of all elements
   * 
   * @return
   */
  public abstract List<MonomerNotation> getListOfElements();

  /**
   * method to add a monomer to the polymer
   * 
   * @param str Monomer
   * @throws SimplePolymerSectionException
   * @throws JDOMException
   * @throws IOException
   * @throws NotationException
   */
  public abstract void addMonomerNotation(String str) throws SimplePolymerSectionException, NotationException,
      IOException, JDOMException;

  /**
   * method to get the current monomer
   * 
   * @return current Monomer
   */
  @JsonIgnore
  public abstract MonomerNotation getCurrentMonomerNotation();

  /**
   * method to change the current Monomer
   * 
   * @param not MonomerNotation
   */
  public abstract void changeMonomerNotation(MonomerNotation not);

  /**
   * method to generate a valid HELM2 of this object
   * 
   * @return valid HELM2
   */
  public String toHELM2() {
    StringBuilder notation = new StringBuilder();
    for (int i = 0; i < listMonomerNotations.size(); i++) {
      notation.append(listMonomerNotations.get(i).toHELM2() + ".");

    }

    notation.setLength(notation.length() - 1);
    return notation.toString();
  }

  /**
   * @return
   * @throws HELM1ConverterException
   */
  public String toHELM() throws HELM1ConverterException {
    StringBuilder notation = new StringBuilder();
    for (int i = 0; i < listMonomerNotations.size(); i++) {
      notation.append(listMonomerNotations.get(i).toHELM() + ".");

    }

    notation.setLength(notation.length() - 1);
    return notation.toString();
  }

}
