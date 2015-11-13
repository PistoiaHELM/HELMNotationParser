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
package org.helm.notation2.parser.Notation.Grouping;

import java.text.DecimalFormat;

import org.helm.notation2.parser.ExceptionParser.NotationException;

/**
 * GroupingAnd
 * 
 * @author hecht
 */
public class GroupingMixture extends GroupingAmbiguity {

  GroupingElement element;


  /**
   * Constructs with the given String representing a list of grouping elements Mixture with ratio or interval
   * 
   * @param str whole group
   * @throws NotationException
   */
  public GroupingMixture(String str) throws NotationException {
    /* Mixture elements are separated by + */
    String[] parts = str.split("\\+|\\(\\+|\\+\\)|\\(\\+\\)");
    for (int i = 0; i < parts.length; ++i) {
      /* Ratio can be defined -> default is 1 */
      String[] item = parts[i].split(":");
      double ratio = 1;
      double ratio2 = 1;
      boolean interval = false;
      boolean isDefault = true;
      if (item.length > 1) {
        /* an interval can be there or not */
        if(item[1].split("-").length >1){
          interval = true;
          ratio = Double.parseDouble(item[1].split("-")[0]);
          ratio2 = Double.parseDouble(item[1].split("-")[1]);
          isDefault = false;
        }
        else{
          /* unknown ratio */
          if (item[1].contains("?")) {
            ratio = -1;
          } else {
            ratio = Double.parseDouble(item[1]);
            isDefault = false;
          }
        }
      }
      
      if (!(interval)) {
        element = new GroupingElement(item[0], ratio, isDefault);
      } else {
        element = new GroupingElement(item[0], ratio, ratio2);
      }
      
      addElement(element);

    }

  }

  /**
   * {@inheritDoc}
   */
  public String toHELM2(){
    DecimalFormat d = new DecimalFormat("#.##");
  StringBuilder notation = new StringBuilder();
  for(int i = 0; i < elements.size();i++){
      if (!(elements.get(i).isDefaultValue())) {
        String value = d.format(elements.get(i).getValue().get(0)).replace(',', '.');
      if (elements.get(i).getValue().size() > 1) {
          value += "-" + d.format(elements.get(i).getValue().get(1)).replace(',', '.');
      }
      notation.append(elements.get(i).getID() + ":" + value + "+");
      } else {
        notation.append(elements.get(i).getID() + "+");
      }

  }

    notation.setLength(notation.length() - 1);

    return notation.toString();
  }

 
}
