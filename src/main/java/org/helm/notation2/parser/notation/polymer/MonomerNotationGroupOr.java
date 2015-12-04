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
import java.text.DecimalFormat;

import org.helm.notation2.parser.exceptionparser.NotationException;
import org.jdom.JDOMException;

/**
 * MonomerNotationGroupOr class to represent an or group
 * 
 * @author hecht
 */
public class MonomerNotationGroupOr extends MonomerNotationGroup {

  /**
   * Constructs with the given String an Or group
   * 
   * @param str details about the group
   * @param type type of the current polymer
   * @throws JDOMException
   * @throws IOException
   * @throws NotationException
   */
  public MonomerNotationGroupOr(String str, String type) throws NotationException, IOException, JDOMException {
    super(str, type);
    /* Or elements are separated by , */
    String[] parts = str.split(",");
    boolean interval = false;
    for (int i = 0; i < parts.length; ++i) {
      /* Ratio can be defined -> default is 1 */
      String[] item = parts[i].split(":");
      double probability = 1;
      double probability2 = 1;
      boolean isDefault = true;
      interval = false;
      if (item.length > 1) {
        /* an interval can be there or not */
        if (item[1].split("-").length > 1) {
          interval = true;
          probability = Float.parseFloat(item[1].split("-")[0]);
          probability2 = Float.parseFloat(item[1].split("-")[1]);
          isDefault = false;
        } else {
          if ((Math.abs(Float.parseFloat(item[1]) - 1.0) > 0.00000001)) {
            probability = Float.parseFloat(item[1]);
            isDefault = false;
          }
          /* unknown ratio */
          if (item[1].contains("?")) {
            probability = -1;
            isDefault = false;
          }

        }
      }

      /* Add element to the list */
      addElement(item[0], type, probability, probability2, interval, isDefault);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toHELM2() {
    StringBuilder notation = new StringBuilder();
    DecimalFormat d = new DecimalFormat("#.##");
    notation.append("(");
    for (int i = 0; i < elements.size(); i++) {
      String text = elements.get(i).getMonomerNotation().getID();
      if (!(elements.get(i).isDefaultValue())) {
        String value = d.format(elements.get(i).getValue().get(0)).replace(',', '.');
        if (elements.get(i).isInterval()) {
          value += "-" + d.format(elements.get(i).getValue().get(1)).replace(',', '.');
        }
        
        if (value.equals("-1")) {
          value = "?";
        }

        text += ":" + value;
      }
      notation.append(text + ",");
    }

    notation.setLength(notation.length() - 1);
    notation.append(")");

    if (isDefault == false) {
      notation.append("'" + count + "'");
    }

    if (isAnnotationHere) {
      notation.append("\"" + annotation + "\"");
    }

    return notation.toString();

  }



  
}
