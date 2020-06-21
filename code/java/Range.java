/*
 *   Range.java
 *   MyJava
 *   Created by Wendell Wang on 2020.
 *
 *   Copyright © 2020 Wendell Wang. All rights reserved.
 */

package resources.java;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Range implements Iterator<Integer>, Iterable<Integer> {
    public final int startIndex, endIndex;

    private int currentIndex;

    public final int steps;

    public Range(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.currentIndex = startIndex;
        this.endIndex = endIndex;
        this.steps = 1;
    }

    public Range(int startIndex, int endIndex, int steps) {
        this.startIndex = startIndex;
        this.currentIndex = startIndex;
        this.endIndex = endIndex;
        this.steps = steps;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return currentIndex + steps <= endIndex;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Integer next() {
        if (hasNext()) {
            currentIndex += steps;
            return currentIndex - steps;
        }
        throw new NoSuchElementException();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Integer> iterator() {
        currentIndex = startIndex;
        return this;
    }

    @Override
    public String toString() {
        return startIndex + "..<" + endIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Range)) return false;
        Range range = (Range) o;
        return startIndex == range.startIndex &&
               endIndex == range.endIndex &&
               steps == range.steps;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startIndex, endIndex, steps);
    }
}
