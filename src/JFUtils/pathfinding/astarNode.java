/*
 * The MIT License
 *
 * Copyright 2019 guest-kxryfn.
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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package JFUtils.pathfinding;

import java.util.Comparator;

/**
 *
 * @author Elias Eskelinen (Jonnelafin)
 */
public class astarNode implements Comparable<astarNode> {
    public astarNode cameFrom = null;
    public int x;
    public int y;
    public int value = 99999999;
    
    public astarNode(int x, int y, int score) {
        this.value = score;
        this.x = x;
        this.y = y;
    }
    
    public static final Comparator<astarNode> DESCENDING_COMPARATOR = new Comparator<astarNode>() {
        // Overriding the compare method to sort the age
        public int compare(astarNode d, astarNode d1) {
            return d.value - d1.value;
        }
    };
    
    @Override
    public int compareTo(astarNode t) {
        return DESCENDING_COMPARATOR.compare(this, t);
    }
}