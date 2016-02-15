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
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * MonomerNotationUnitRNA class to represent nucleotides
 *
 * @author hecht
 */
public class MonomerNotationUnitRNA extends MonomerNotationUnit {

  private List<MonomerNotationUnit> contents = new ArrayList<MonomerNotationUnit>();

  private int bracketCounterOpen = 0;

  private int bracketCounterClose = 0;

  private int parenthesisCounterOpen = 0;

  private int parenthesisCounterClose = 0;

  private List<String> contentsInformation = new ArrayList<String>();

  /**
   * @param str
   * @param type
   * @throws IOException
   * @throws NotationException
   */
  public MonomerNotationUnitRNA(String str, String type) throws IOException, NotationException {
    super(str, type);
    setRNAContents(str);
  }

  /**
   * method to set for each nucleotide the sugar, the base and the phosphat
   *
   * @param str
   * @throws NotationException
   * @throws IOException
   */
  private void setRNAContents(String str) throws NotationException, IOException {
    /* Nucleotide with all contents */
    String[] list;

    // if (str.contains("(")) {
    List<String> items = extractContents(str);
    for (String item : items) {
      contents.add(new MonomerNotationUnit(item, type));

    }

    // } /* nucleotide contains no base, but a modified sugar or phosphat */
    // else if (str.contains("[")) {
    // if (str.startsWith("[")) {
    // str = str.replace("]", "]$");
    // } else {
    // str = str.replace("[", "$[");
    // }
    // list = str.split("\\$");
    // for (int i = 0; i < list.length; i++) {
    // contents.add(new MonomerNotationUnit(list[i], type));
    // }
    // } /* nucleotide contains only standard sugar and/or phosphat */ else {
    // for (int i = 0; i < str.length(); i++) {
    // contents.add(new MonomerNotationUnit(Character.toString(str.charAt(i)),
    // type));
    // }
    // }

  }

  /**
   * method to get the contents of this nucleotide
   *
   * @return
   */
  public List<MonomerNotationUnit> getContents() {
    return this.contents;
  }

  private List<String> extractContents(String notation) {
    List<String> items = new ArrayList<String>();
    StringBuilder sb = new StringBuilder();
    for (char cha : notation.toCharArray()) {
      if (cha == '(') {
        if (!(checkBracketsParenthesis())) {
          sb.append(cha);
        } else {
          if (items.size() > 0) {
            contentsInformation.add("R");
          }
          contentsInformation.add("X");
        }
        parenthesisCounterOpen += 1;

      } else if (cha == ')') {
        parenthesisCounterClose += 1;
        if (checkBracketsParenthesis()) {
          items.add(sb.toString());
          sb = new StringBuilder();
        } else {
          sb.append(cha);
        }
      } else if (cha == '[') {
        bracketCounterOpen += 1;
        sb.append(cha);

      } else if (cha == ']') {
        bracketCounterClose += 1;
        sb.append(cha);
        if (checkBracketsParenthesis()) {
          items.add(sb.toString());
          sb = new StringBuilder();
        }
      } else {
        sb.append(cha);
        if (checkBracketsParenthesis()) {
          items.add(sb.toString());
          sb = new StringBuilder();
        }

      }
    }
    if (contentsInformation.size() == 0) {
      contentsInformation.add("R");
    }

    /* problem case */
    if (items.size() == 1) {

    }

    if (items.size() != contentsInformation.size()) {
      contentsInformation.add("P");
    }
    return items;

  }

  /**
   * method to check if all open brackets are closed
   *
   * @return true if all open brackets are close, false otherwise
   */
  private boolean checkBracketsParenthesis() {
    if (bracketCounterOpen == bracketCounterClose && parenthesisCounterOpen == parenthesisCounterClose) {
      return true;
    }
    return false;
  }

  /**
   * method to get the contents of this nucleotide
   *
   * @return
   */
  @JsonIgnore
  public List<String> getInformation() {
    return contentsInformation;
  }

}
