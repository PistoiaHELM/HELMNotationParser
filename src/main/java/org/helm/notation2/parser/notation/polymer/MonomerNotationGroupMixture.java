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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.helm.notation2.parser.exceptionparser.NotationException;
import org.jdom2.JDOMException;

/**
 * MonomerNotationGroupMixture class to represent a mixture group
 *
 * @author hecht
 */
public class MonomerNotationGroupMixture extends MonomerNotationGroup {

  /**
   * Constructs with the given String a mixture group
   *
   * @param str details about the mixture group
   * @param type Type of the current polymer type
   * @throws JDOMException
   * @throws IOException
   * @throws NotationException
   */
  public MonomerNotationGroupMixture(String str, String type) throws NotationException, IOException, JDOMException {

	  super(str, type);
    /* Mixture elements are separated by + */
    String[] parts = str.split("\\+");
    for (int i = 0; i < parts.length; ++i) {
      /* Ratio can be defined -> default is 1 */
      //String[] item = parts[i].split("((:)\\?)|((:)\\d.)");
      String[] item;
      Pattern pattern = Pattern.compile("(:)\\d+(?!\\])|(:)\\?");
      Matcher matcher = pattern.matcher(parts[i]);
      if(matcher.find()){
    	  item = new String[2];
    	  item[0] = parts[i].substring(0, matcher.start());
    	  item[1] = parts[i].substring(matcher.start()+1);
      }else{
    	  item = new String[1];
    	  item[0] = parts[i];
    	  }
      double ratio = 1;
      double ratio2 = 1;
      boolean interval = false;
      boolean isDefault = true;
      if (item.length > 1) {
        /* an interval can be there or not */
        if (item[1].split("-").length > 1) {
          interval = true;
          ratio = Float.parseFloat(item[1].split("-")[0]);
          ratio2 = Float.parseFloat(item[1].split("-")[1]);
          isDefault = false;
        } else {
          /* unknown ratio */
          if (item[1].contains("?")) {
            ratio = -1;
            isDefault = false;
          } else {
            ratio = Float.parseFloat(item[1]);
            isDefault = false;
          }

        }
      }

      /* Add element to the list */
      addElement(item[0], type, ratio, ratio2, interval, isDefault);
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
      String text = elements.get(i).getMonomerNotation().getUnit();
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
      notation.append(text + "+");
    }

    notation.setLength(notation.length() - 1);
    notation.append(")");

    if (!(isDefault)) {
      notation.append("'" + count + "'");
    }

    if (isAnnotationHere) {
      notation.append("\"" + annotation + "\"");
    }

    return notation.toString();

  }
}
