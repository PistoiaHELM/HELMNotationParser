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
package org.helm.notation2.parser.notation.grouping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.helm.notation2.parser.exceptionparser.NotationException;
import org.helm.notation2.parser.notation.polymer.GroupEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GroupingNotation
 *
 * @author hecht
 */
public final class GroupingNotation {

  private GroupEntity groupID;

  private GroupingAmbiguity ambiguity;

  public GroupingNotation() {

  }

  /**
   * Constructs with the given String
   *
   * @param str Group ID
   * @throws NotationException
   */
  public GroupingNotation(String str) throws NotationException {
    if (str.matches("G[1-9][0-9]*")) {
      this.groupID = new GroupEntity(str);
    } else {
      throw new NotationException("Group Id is not correct: " + str);
    }
  }

  /**
   * Constructs with the GroupEntity and details about the group elements
   *
   * @param group GroupEntity
   * @param ambi details about the group elements
   * @throws NotationException
   */
  public GroupingNotation(GroupEntity group, String ambi) throws NotationException {
    this.groupID = group;
    defineAmbiguity(ambi);
  }

  /**
   * method to get details about the group elements
   *
   * @return GroupingAmbiguity
   */
  public GroupingAmbiguity getAmbiguity() {
    return ambiguity;
  }

  /**
   * method to add ambiguity to the group
   *
   * @param a details about the group elements
   * @throws NotationException
   */
  private void defineAmbiguity(String a) throws NotationException {
    Pattern patternAND = Pattern.compile("\\+");
    Matcher m = patternAND.matcher(a);

    /* mixture */
    if (m.find()) {
      setAmbiguity(new GroupingMixture(a));
    } /* or case */ else {
      setAmbiguity(new GroupingOr(a));
    }

  }

  private void setAmbiguity(GroupingAmbiguity a) {
    ambiguity = a;
  }

  /**
   * method to get the groupID
   *
   * @return groupID
   */
  public GroupEntity getGroupID() {
    return groupID;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "GroupID: " + groupID + " " + ambiguity.toString();
  }

  /**
   * method to get a valid HELM2 notation of the group
   *
   * @return valid HELM2 notation
   */
  public String toHELM2() {
    return groupID + "(" + ambiguity.toHELM2() + ")";
  }

}
