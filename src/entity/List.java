package entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

public class List<T> implements Iterable<T>, Serializable {
    private T[] data;
    private int capacity = 20;
    private int size = 0;

    public List(T[] data) {
        this.data = data;
    }

    public void insert(T element) {
        if (size + 1 >= capacity) {
            resize();
        }
        data[size++] = element;
    }

    public void resize() {
        capacity *= 2;

        T[] newElements = (T[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            newElements[i] = data[i];
        }

        data = newElements;
    }

    public void replaceElement(T oldElement, T newElement) {
        for (int i = 0; i < size; i++) {
            if(data[i].equals(oldElement)) {
                data[i] = newElement;
            }
        }
    }
    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.println("Номер: " + i + " " + data[i]);
        }
    }

    public T[] getAll() {
        return data;
    }

    public int getSize() {
        return size;
    }

    public void remove(T element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) {
                for (int j = i; j < size -1; j++) {
                    data[j] = data[j + 1];
                }
                size--;
                break;
            }
        }
        data[size + 1] = null;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (T element : data) {
            if (element != null) {
                stringBuilder.append(element.toString());
                stringBuilder.append("\n");
            }
        }
        return !stringBuilder.isEmpty() ? stringBuilder.toString() : "Нет элементов в массиве";
    }


    private class ListIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            return data[currentIndex++];
        }
    }
}
