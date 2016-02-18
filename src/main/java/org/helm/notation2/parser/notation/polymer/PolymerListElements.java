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
import java.util.ArrayList;
import java.util.List;

import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.notation.ValidationMethod;
import org.jdom2.JDOMException;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * PolymerListElements class to represent a list of polymer elements
 *
 * @author hecht
 */
public class PolymerListElements extends PolymerElements {

  public PolymerListElements() {

  }

  /**
   * Constructor
   */
  public PolymerListElements(HELMEntity entity) {
    super(entity);
    this.listMonomerNotations = new ArrayList<MonomerNotation>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<MonomerNotation> getListOfElements() {

    return this.listMonomerNotations;
  }

  /**
   * {@inheritDoc}
   *
   * @throws JDOMException
   * @throws IOException
   * @throws NotationException
   */
  @Override
  public void addMonomerNotation(String str) throws NotationException, IOException, JDOMException {
    this.listMonomerNotations.add(ValidationMethod.decideWhichMonomerNotation(str, this.entity.getType()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @JsonIgnore
  public MonomerNotation getCurrentMonomerNotation() {
    return this.listMonomerNotations.get(this.listMonomerNotations.size() - 1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void changeMonomerNotation(MonomerNotation not) {
    this.listMonomerNotations.set(this.listMonomerNotations.size() - 1, not);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return this.listMonomerNotations.toString();
  }

}
