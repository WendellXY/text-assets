/*
 *   Utility.java
 *   MyJava
 *   Created by Wendell Wang on 2020.
 *
 *   Copyright © 2020 Wendell Wang. All rights reserved.
 */

package resources.java;

interface UtilityGettable<E> {
    Range range();

    E get(int index);
}

public class Utility {
    /**
     * Checks if the {@code index} is within the bounds of the range from
     * {@code 0} (inclusive) to {@code length} (exclusive).
     *
     * <p>The {@code index} is defined to be out of bounds if any of the
     * following inequalities is true:
     * <ul>
     *  <li>{@code index < 0}</li>
     *  <li>{@code index >= length}</li>
     *  <li>{@code length < 0}, which is implied from the former inequalities</li>
     * </ul>
     *
     * @param index    current index of array
     * @param endIndex maximum index of array
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public static void checkIndex(int index, int endIndex) throws IndexOutOfBoundsException {
        if (index < 0 || index >= endIndex)
            throw new IndexOutOfBoundsException();
    }

    /**
     * check if the {@code val} is always positive or zero
     * @param val value to check
     * @throws RuntimeException if the value is negative
     */
    public static void isAlwaysNonNegative(int val) throws RuntimeException {
        if (val < 0)
            throw new RuntimeException("value " + val + " cannot be negative.");
    }

    /**
     * Returns true if the two specified objects implements {@code UtilityGettable} are equal to one another. The two
     * objects are considered equal if both objects contain the same number of elements, and all corresponding pairs of
     * elements in the two objects are equal. Two objects e1 and e2 are considered equal if Objects.equals(e1, e2). In
     * other words, the two objects are equal if they contain the same elements in the same order. Also, two array
     * references are considered equal if both are null.
     *
     * @param x one object to be tested for equality
     * @param y the other object to be tested for equality
     * @return true if the two arrays are equal
     */
    public static boolean equals(UtilityGettable<?> x, UtilityGettable<?> y) {
        if (x.getClass() == y.getClass()) {
            Range range = x.range();
            if (!range.equals(y.range())) return false;
            for (int index : range)
                if (!x.get(index).equals(y.get(index)))
                    return false;
            return true;
        }
        return false;
    }
}
