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
package org.helm.notation2.parser.Notation.Polymer;

import java.io.IOException;
import java.util.ArrayList;

import org.helm.notation2.parser.ExceptionParser.NotationException;
import org.helm.notation2.parser.Notation.ValidationMethod;
import org.jdom.JDOMException;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * PolymerListElements class to represent a list of polymer elements
 * 
 * @author hecht
 */
public class PolymerListElements extends PolymerElements {


  /**
   * Constructor
   */
  public PolymerListElements(Entity entity) {
    super(entity);
    elements = new ArrayList<MonomerNotation>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ArrayList<MonomerNotation> getListOfElements() {

    return elements;
  }

  /**
   * {@inheritDoc}
   * 
   * @throws JDOMException
   * @throws IOException
   * @throws NotationException
   */
  @Override
  public void addMonomer(String str) throws NotationException, IOException, JDOMException {
    elements.add(new ValidationMethod().decideWhichMonomerNotation(str, entity.getType()));
  }


  /**
   * {@inheritDoc}
   */
  @Override
  @JsonIgnore
  public MonomerNotation getCurrentMonomer() {
    return elements.get(elements.size() - 1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void changeMonomer(MonomerNotation not) {
    elements.set(elements.size() - 1, not);
  }

  /**
   * {@inheritDoc}
   */
  public String toString() {
    return elements.toString();
  }

}
