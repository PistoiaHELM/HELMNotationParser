<<<<<<< HEAD:src/main/java/org/helm/notation2/parser/notation/polymer/HELMEntity.java
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


/**
 * Entity
 * 
 * @author
 */
public abstract class HELMEntity {
  protected String id;

  protected String type;

  /**
   * Constructs an Entity
   * 
   * @param str entity name
   */
  public HELMEntity(String str) {
    id = str;
  }

  /**
   * {@inheritDoc}
   */
  public String toString() {
    return id;
  }

  /**
   * method to get the ID of the Entity
   * 
   * @return ID
   */
  public String getID() {
    return id;
  }

  /**
   * method to get the Type of the Entity
   * 
   * @return type
   */
  public String getType(){
    return type;
  }
}
=======
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
package parsertest.Notation.Polymer;


/**
 * Entity
 * 
 * @author
 */
public abstract class Entity {
  String id;

  String type;

  /**
   * Constructs an Entity
   * 
   * @param str entity name
   */
  public Entity(String str) {
    id = str;
  }

  /**
   * {@inheritDoc}
   */
  public String toString() {
    return id;
  }

  /**
   * method to get the ID of the Entity
   * 
   * @return ID
   */
  public String getID() {
    return id;
  }

  /**
   * method to get the Type of the Entity
   * 
   * @return type
   */
  public String getType(){
    return type;
  }
}
>>>>>>> 028ee9b5dc0c7557d6e1cfa25a041b95a69e9c58:src/main/java/parsertest/Notation/Polymer/Entity.java
