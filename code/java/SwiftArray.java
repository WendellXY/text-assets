/*
 *   SwiftArray.java
 *   MyJava
 *   Created by Wendell Wang on 2020.
 *
 *   Copyright © 2020 Wendell Wang. All rights reserved.
 */

package resources.java;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

class SwiftArrayIterator<E> implements Iterator<E> {

    private final SwiftArray<E> array;

    private int currentIndex;

    SwiftArrayIterator(SwiftArray<E> array) {
        this.array = array;
        currentIndex = 0;
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
        return currentIndex < array.size();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public E next() {
        if (hasNext())
            return array.get(currentIndex++);
        throw new NoSuchElementException();
    }
}

/**
 * An ordered, random-access collection.
 * My custom array that could grow up automatically, using the similar name like {@code Swift} programming language
 * <h3></h3>
 * <h3>Overview</h3>
 * <body>
 * Arrays are one of the most commonly used data types in an app. You use arrays to organize your app’s data.
 * Specifically, you use the Array type to hold elements of a single type, the array’s Element type. An array can
 * store any kind of elements—from integers to strings to classes.
 * </body>
 *
 * @param <E> Element of the array
 */
public class SwiftArray<E> implements UtilityGettable<E>, Iterable<E> {

    /**
     * Default initial capacity.
     */
    private static final int default_capacity = 8;

    transient Object[] data;

    /**
     * capacity of the array
     */
    private int capacity;

    /**
     * the start index of the array
     */
    private final int startIndex = 0;

    /**
     * the end index of the array (not include)
     */
    private int endIndex;

    /* -------------------------------------------------Constructors------------------------------------------------ */

    /**
     * Constructs an empty array with a default_capacity.
     */
    public SwiftArray() {
        this.data = new Object[default_capacity];
        endIndex = 0;
        capacity = default_capacity;
    }

    /**
     * Constructs an empty array with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the array
     * @throws IllegalArgumentException if the specified initial capacity
     * is negative
     */
    public SwiftArray(int initialCapacity) {
        if (initialCapacity > 0) {
            this.data = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.data = new Object[]{};
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        endIndex = 0;
        capacity = initialCapacity;
    }

    /**
     * Constructs a array containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param c the collection whose elements are to be placed into this array
     * @throws NullPointerException if the specified collection is null
     */
    public SwiftArray(Collection<? extends E> c) {
        data = c.toArray();
        if ((endIndex = data.length) != 0) {
            // defend against c.toArray (incorrectly) not returning Object[]
            // (see e.g. https://bugs.openjdk.java.net/browse/JDK-6260652)
            if (data.getClass() != Object[].class)
                data = Arrays.copyOf(data, endIndex, Object[].class);
        } else {
            // replace with empty array.
            this.data = new Object[]{};
        }
        capacity = data.length;
    }

    /**
     * Constructs a array containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param c the collection whose elements are to be placed into this array
     * @throws NullPointerException if the specified collection is null
     */
    public SwiftArray(E[] c) {
        data = c;
        if ((endIndex = data.length) != 0) {
            // defend against c.toArray (incorrectly) not returning Object[]
            // (see e.g. https://bugs.openjdk.java.net/browse/JDK-6260652)
            if (data.getClass() != Object[].class)
                data = Arrays.copyOf(data, endIndex, Object[].class);
        } else {
            // replace with empty array.
            this.data = new Object[]{};
        }
        capacity = data.length;
    }

    /**
     * Constructs a array with {@code repeats} number of {@code item}
     *
     * @param item    the element to repeat
     * @param repeats the number of the repeat item
     */
    public SwiftArray(E item, int repeats) {
        this.data = new Object[repeats];
        endIndex = repeats;
        capacity = repeats;

        Arrays.fill(data, item);
    }

    /* -------------------------------------------------Basic Action------------------------------------------------ */

    /**
     * Returns the number of elements in this array.
     *
     * @return the number of elements in this array
     */
    public int size() {
        return endIndex - startIndex;
    }

    /**
     * Returns {@code true} if this array contains no elements.
     *
     * @return {@code true} if this array contains no elements
     */
    public boolean isEmpty() {
        return startIndex == endIndex;
    }

    @SuppressWarnings("unchecked")
    E data(int index) {
        return (E) data[index];
    }

    /**
     * Returns the element at the specified position in this array.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this array
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public E get(int index) {
        Utility.checkIndex(index, endIndex);
        return data(index);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("[");
        for (int i = startIndex; i < endIndex; i++) {
            string.append(data(i));
            if (i < endIndex - 1) {
                string.append(",");
            }
        }
        string.append("]");
        return string.toString();
    }

    public Range range() {
        return new Range(startIndex, endIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SwiftArray)) return false;
        SwiftArray<?> array = (SwiftArray<?>) o;
        return Utility.equals(this, array);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(startIndex, endIndex);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    /**
     * get the capacity of the array
     *
     * @return the capacity of the array
     */
    public int getCapacity() {
        return capacity;
    }


    /**
     * make a copy for the current instance
     *
     * @return a copy of the instance
     */
    @SuppressWarnings("unchecked")
    public SwiftArray<E> copy() {
        return new SwiftArray<>((E[]) this.data.clone());
    }


    /**
     * Replaces the element at the specified position in this array with
     * the specified element.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public void set(int index, E element) {
        Utility.checkIndex(index, endIndex);
        data[index] = element;
    }

    /* -----------------------------------------------Adding Elements----------------------------------------------- */

    /**
     * Appends the specified element to the end of this array.
     *
     * @param element element to be appended to this array
     */
    public void append(E element) {
        if (endIndex == capacity) {
            capacity *= 2;
            Object[] newObjects = new Object[capacity];
            System.arraycopy(data, 0, newObjects, 0, size());
            newObjects[endIndex] = element;
            data = newObjects;
        } else {
            data[endIndex] = element;
        }
        endIndex += 1;
    }


    /**
     * insert a new element at {@code index}, the elements after the index would be offset one position
     *
     * @param index   index of new element
     * @param element the new element to insert
     */
    public void insert(int index, E element) {
        if (index == endIndex) {
            append(element);
            return;
        } else if (endIndex == capacity) {
            capacity *= 2;
            Object[] newObjects = new Object[capacity];
            System.arraycopy(data, 0, newObjects, 0, index);
            newObjects[index] = element;
            System.arraycopy(data, index, newObjects, index + 1, size() - index);
            data = newObjects;
        } else {
            System.arraycopy(data, index, data, index + 1, size() - index);
            data[index] = element;
        }
        endIndex += 1;
    }

    /* ------------------------------------------------Combing Arrays----------------------------------------------- */

    /**
     * append sequence to the array
     *
     * @param sequence the collection whose elements are to be placed into this array
     */
    @SuppressWarnings("unchecked")
    public void append(Collection<? extends E> sequence) {
        Object[] newObjects = sequence.toArray();
        append((E[]) newObjects);
    }

    /**
     * append sequence to the array
     *
     * @param newObjects the collection whose elements are to be placed into this array
     */
    public void append(E[] newObjects) {
        final int total = newObjects.length + size();
        if (total < capacity) {
            System.arraycopy(newObjects, 0, data, endIndex, newObjects.length);
        } else {
            capacity = Math.max(total, capacity * 2);
            Object[] tmp = new Object[capacity];
            System.arraycopy(data, 0, tmp, 0, size());
            System.arraycopy(newObjects, 0, tmp, endIndex, newObjects.length);
            data = tmp;
        }
        endIndex = total;
    }


    /**
     * insert elements at {@code index}, the elements after the index would be offset one position
     *
     * @param index    index of new elements start point
     * @param elements the new elements to insert
     */
    public void insert(int index, E[] elements) {
        if (index == endIndex) {
            append(elements);
            return;
        } else {
            final int total = size() + elements.length;
            final int length = elements.length;
            if (total > capacity) {
                capacity = Math.max(total, capacity * 2);
                Object[] tmp = new Object[capacity];
                System.arraycopy(data, 0, tmp, 0, index);
                System.arraycopy(elements, 0, tmp, index, length);
                System.arraycopy(data, index, tmp, index + length, total - index - length);
                data = tmp;
            } else {
                System.arraycopy(data, index, data, index + length, total - index - length);
                System.arraycopy(elements, 0, data, index, length);
            }
        }
        endIndex += elements.length;
    }

    /* ----------------------------------------------Removing Elements---------------------------------------------- */

    /**
     * remove the element at the index
     *
     * @param index index of removed element
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public void remove(int index) {
        Utility.checkIndex(index, endIndex);
        System.arraycopy(data, index + 1, data, index, size() - index - 1);
        data[--endIndex] = null;
    }

    /**
     * remove the last element
     */
    public void removeLast() {
        data[--endIndex] = null;
    }

    /**
     * remove the last {@code count} elements
     *
     * @param count the number of elements to remove
     * @throws RuntimeException if count is negative
     */
    public void removeLast(int count) {
        Utility.isAlwaysNonNegative(count);
        for (int i = 0; i < count; i++) {
            removeLast();
        }
    }

    /**
     * remove the first element
     */
    public void removeFirst() {
        remove(0);
    }

    /**
     * remove the first {@code count} elements
     *
     * @param count the number of elements to remove
     * @throws RuntimeException if count is negative
     */
    public void removeFirst(int count) {
        Utility.isAlwaysNonNegative(count);
        for (int i = 0; i < count; i++) {
            removeFirst();
        }
    }

    /* ----------------------------------------------Excluding Elements--------------------------------------------- */

    /**
     * return the copy that removed first element
     *
     * @return the copy that removed first element
     */
    public SwiftArray<E> dropFirst() {
        SwiftArray<E> copy = copy();
        copy.removeFirst();
        return copy;
    }

    /**
     * return the copy that removed last element
     *
     * @return the copy that removed last element
     */
    public SwiftArray<E> dropLast() {
        SwiftArray<E> copy = copy();
        copy.removeLast();
        return copy;
    }

    /**
     * Returns a subsequence by skipping elements while {@code predicate} returns true and returning the remaining
     * elements.
     *
     * @param predicate A closure that takes an element of the sequence as its argument and returns true if the element
     *                  should be skipped or false if it should be included. Once the predicate returns false it will
     *                  not be called again.
     * @return a subsequence by skipping elements while predicate returns true and returning the remaining elements.
     * @throws NullPointerException if the specified action is null
     */
    public SwiftArray<E> drop(Predicate<E> predicate) {
        Objects.requireNonNull(predicate);
        SwiftArray<E> ret = new SwiftArray<>(capacity);

        forEach(e -> {
            if (predicate.test(e))
                ret.append(e);
        });

        return ret;
    }

    /* -----------------------------------------------Finding Elements---------------------------------------------- */

    /**
     * check if the array contains element
     *
     * @param element the element to check
     * @return {@code true} if the array contains the element, {@code false} if not contains
     */
    public boolean contains(E element) {
        for (int index : range())
            if (Objects.equals(data(index), element))
                return true;
        return false;
    }

    /**
     * check if there is any element match the {@code predicate}
     *
     * @param predicate the matched condition
     * @return {@code true} if there is a matched element, else {@code false}
     * @throws NullPointerException if the specified action is null
     */
    public boolean contains(Predicate<E> predicate) {
        Objects.requireNonNull(predicate);
        for (int index : range())
            if (predicate.test(data(index)))
                return true;
        return false;
    }

    /**
     * check if all elements match the {@code predicate}.
     *
     * <h3>Code Sample</h3>
     * <pre>
     *     MyArray<Integer> myArray = new MyArray<>(new Integer[]{2,4,6});
     *     myArray.allSatisfy( x -> x % 2 == 0);
     * </pre>
     *
     * @param predicate the matched condition
     * @return {@code true} if all elements matched, else {@code false}
     * @throws NullPointerException if the specified action is null
     */
    public boolean allSatisfy(Predicate<E> predicate) {
        Objects.requireNonNull(predicate);
        return !contains(predicate.negate());
    }

    /**
     * Returns the first element of the sequence that satisfies the given predicate.
     *
     * <h3>Code Sample</h3>
     * <pre>
     *     MyArray<Integer> myArray = new MyArray<>(new Integer[]{1,2,4,6});
     *     myArray.first(x -> x % 2 == 0); // 2
     *     myArray.first(x -> x % 6 == 0); // 6
     * </pre>
     *
     * @param predicate A closure that takes an element of the sequence as its argument and returns a Boolean value
     *                  indicating whether the element is a match.
     * @return The first element of the sequence that satisfies predicate, or null if there is no element that satisfies
     * predicate.
     * @throws NullPointerException if the specified action is null
     */
    public E first(Predicate<E> predicate) {
        Objects.requireNonNull(predicate);
        int index = firstIndexOf(predicate);
        return index != -1 ? data(index) : null;
    }

    /**
     * find index of first element that is equal to {@code element}
     *
     * <h3>Code Sample</h3>
     * <pre>
     *      MyArray<Integer> myArray = new MyArray<>(new Integer[]{1,2,4,6});
     *      myArray.firstIndexOf(2); // 1
     *      myArray.firstIndexOf(4); // 2
     * </pre>
     *
     * @param element the element to find
     * @return the index of the first element of the array, if no such element, return -1
     */
    public int firstIndexOf(E element) {
        for (int i = 0; i < endIndex; i++)
            if (Objects.equals(data(i), element))
                return i;
        return -1;
    }

    /**
     * Returns the first index in which an element of the collection satisfies the given predicate
     *
     * @param predicate A closure that takes an element as its argument and returns a Boolean value that indicates
     *                  whether the passed element represents a match.
     * @return The index of the first element for which predicate returns true. If no elements in the collection satisfy
     * the given predicate, returns -1.
     * @throws NullPointerException if the specified action is null
     */
    public int firstIndexOf(Predicate<E> predicate) {
        Objects.requireNonNull(predicate);
        for (int i = 0; i < endIndex; i++)
            if (predicate.test(data(i)))
                return i;
        return -1;
    }

    /**
     * Returns the first element of the sequence that satisfies the given predicate.
     *
     * <h3>Code Sample</h3>
     * <pre>
     *     MyArray<Integer> myArray = new MyArray<>(new Integer[]{1,2,4,2,4,6});
     *     myArray.last(x -> x % 2 == 0); // 6
     *     myArray.last(x -> x % 2 == 6); // 6
     * </pre>
     *
     * @param predicate A closure that takes an element of the sequence as its argument and returns a Boolean value
     *                  indicating whether the element is a match.
     * @return The first element of the sequence that satisfies predicate, or null if there is no element that satisfies
     * predicate.
     * @throws NullPointerException if the specified action is null
     */
    public E last(Predicate<E> predicate) {
        Objects.requireNonNull(predicate);
        int index = lastIndexOf(predicate);
        return index != -1 ? data(index) : null;
    }

    /**
     * find index of last element that is equal to {@code element}
     *
     * <h3>Code Sample</h3>
     * <pre>
     *     MyArray<Integer> myArray = new MyArray<>(new Integer[]{1,2,4,2,4,6});
     *     myArray.lastIndexOf(2); // 3
     *     myArray.lastIndexOf(4); // 3
     * </pre>
     *
     * @param element the element to find
     * @return the index of the last element of the array, if no such element, return -1
     */
    public int lastIndexOf(E element) {
        for (int i = endIndex - 1; i >= 0; i--)
            if (Objects.equals(data(i), element))
                return i;
        return -1;
    }

    /**
     * Returns the last index in which an element of the collection satisfies the given predicate
     *
     * @param predicate A closure that takes an element as its argument and returns a Boolean value that indicates
     *                  whether the passed element represents a match.
     * @return The index of the last element for which predicate returns true. If no elements in the collection satisfy
     * the given predicate, returns -1.
     * @throws NullPointerException if the specified action is null
     */
    public int lastIndexOf(Predicate<E> predicate) {
        Objects.requireNonNull(predicate);
        for (int i = endIndex - 1; i >= 0; i--)
            if (predicate.test(data(i)))
                return i;
        return -1;
    }

    /**
     * Returns a subsequence by skipping elements while {@code predicate} returns true and returning the remaining
     * elements.
     *
     * <h3>Code Sample</h3>
     * <pre>
     *     MyArray<Integer> myArray = new MyArray<>(new Integer[]{1,2,4,2,4,6});
     *     myArray.filter(x -> x % 2 == 0); // [2,4,2,4,6]
     *     myArray.filter(x -> x % 2 == 1); // [1]
     * </pre>
     *
     * @param predicate A closure that takes an element of the sequence as its argument and returns true if the element
     *                  should be skipped or false if it should be included. Once the predicate returns false it will
     *                  not be called again.
     * @return a subsequence by skipping elements while predicate returns true and returning the remaining elements.
     * @throws NullPointerException if the specified action is null
     */
    public SwiftArray<E> filter(Predicate<E> predicate) {
        Objects.requireNonNull(predicate);
        return drop(predicate);
    }

    /* --------------------------------------------Transforming an Array-------------------------------------------- */

    /**
     * Calls the given closure on each element in the sequence in the same order as a for-in loop.
     *
     * <h3>Code Sample</h3>
     * <pre>
     *     MyArray<E> ret = new MyArray<>(capacity);
     *
     *     forEach(e -> {
     *          if (predicate.test(e))
     *              ret.append(e);
     *     });
     * </pre>
     *
     * @param consumer A closure that takes an element of the sequence as a parameter.
     * @throws NullPointerException if the specified action is null
     */
    public void forEach(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        for (E t : this) consumer.accept(t);
    }

    /**
     * Returns an array containing the results of mapping the given closure over the sequence’s elements.
     *
     * <h3>Code Sample</h3>
     * <pre>
     *     MyArray<Integer> myArray = new MyArray<>(new Integer[]{1,2,4,2,4,6});
     *     myArray.map(integer -> integer.doubleValue() + 1); // [2.0,3.0,5.0,3.0,5.0,7.0]
     *     myArray.map(x -> x * 2); // [2,4,8,4,8,12]
     * </pre>
     *
     * @param function A mapping closure. transform accepts an element of this sequence as its parameter and returns a
     *                 transformed value of the same or of a different type.
     * @param <R>      the type of the element of return array
     * @return An array containing the transformed elements of this sequence.
     * @throws NullPointerException if the specified action is null
     */
    public <R> SwiftArray<R> map(Function<E, R> function) {
        Objects.requireNonNull(function);
        SwiftArray<R> newArray = new SwiftArray<>(getCapacity());

        Consumer<E> consumer = e -> newArray.append(function.apply(e));

        forEach(consumer);

        return newArray;
    }

    /**
     * Returns the result of combining the elements of the sequence using the given closure.
     *
     * <h3>Code Sample</h3>
     * <pre>
     *     MyArray<Integer> myArray = new MyArray<>(new Integer[]{1,2,3,4});
     *     myArray.reduce(0, Integer::sum); // 10
     * </pre>
     *
     * @param binaryOperator A closure that updates the accumulating value with an element of the sequence.
     * @return The final accumulated value. If the sequence has no elements, the result is initialResult.
     * @throws NullPointerException if the specified action is null
     */
    public E reduce(final E initialResult, BinaryOperator<E> binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        E ret = initialResult;

        for (int i = 0; i < endIndex; i++)
            ret = binaryOperator.apply(ret, data(i));

        return ret;
    }

    /* ---------------------------------------------------Iterate--------------------------------------------------- */

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<E> iterator() {
        return new SwiftArrayIterator<>(this);
    }
}
