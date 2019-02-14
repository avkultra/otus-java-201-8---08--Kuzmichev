package ru.otus.H031;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    private int size = 0;
    private final static int start_capacity = 20;
    private int capacity = start_capacity;
    private Object[] array;

    public MyArrayList() {

        this.array = new Object[start_capacity];
    }

    public MyArrayList(int capacity) {
        if (capacity <= 0)
        {
            throw new IllegalArgumentException("Wrong capacity: "+     capacity);
        }
        this.array = new Object[capacity];
    }

    public MyArrayList(Collection<T> c) {
        this();
        for(T item : c){
            add(item);
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty()
    {
       return (0 == size());
    }

    @Override
    public boolean contains(Object o)
    {
        boolean bRes = false;
        if (null == o) {
            return false;
        }

        for(int i = 0; i < this.size; ++i){
            if (null != this.array[i] && this.array[i].equals(o))
            {
                bRes = true;
                break;
            }
        }
        return bRes;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIteratorArrayList<T>(this);
    }

    @Override
    public Object[] toArray()
    {
        Object[] res = new Object[size];
        System.arraycopy(array, 0, res, 0, size);
        return res;
    }

    @Override
    public  <T> T[] toArray(T[] a)
    {
        throw new RuntimeException("the method not implemented");
    }


    @Override
    public boolean add(T e)
    {
        if(null == e)
        {
            throw new RuntimeException("illegal argument - null reference");
        }

        if (this.size == this.capacity) {
            increaseCapacity();
        }

        this.array[this.size] = e;
        this.size++;

        return true;
    }

    private void increaseCapacity() {
        this.capacity = 2 * this.size;
        Object[] incremented = new Object[this.capacity] ;
        System.arraycopy(array, 0, incremented, 0, array.length);
        array = incremented;
    }

    @Override
    public boolean remove(Object o)
    {
        if (null == o)
            return false;

        int index = indexOf(o);
        if (index >= 0) {
            remove(index);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        boolean res = true;
        for(Object item : c){
            if (!contains(item))
            {
                res = false;
                break;
            }
        }
        return res;
    }

    @Override
    public boolean addAll(Collection<? extends T> c)
    {
        for(T item : c){
            add(item);
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c)
    {
        throw new RuntimeException("the method not implemented");
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        boolean res = false;

        for(Object item : c){
            res = remove(item);

        }
        return res;
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        throw new RuntimeException("the method not implemented");
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < size; ++i)
            array[i] = null;

        size = 0;
    }

    @Override
    public T get(int index)
    {
        if (0 > index  || this.size <= index)
            throw new IndexOutOfBoundsException("Index must be > 0 and < " + this.size);

        return (T)array[index];
    }

    @Override
    public T set(int index, T element)
    {
        if (0 > index  || this.size <= index)
            throw new IndexOutOfBoundsException("Index must be > 0 and < " + this.size);

        T pre = (T)this.array[index];
        this.array[index] = element;

        return pre;
    }

    @Override
    public void add(int index, T element)
    {
        throw new RuntimeException("the method not implemented");
    }

    @Override
    public T remove(int index)
    {

        if (index < 0 || this.size <= index)
            throw new IndexOutOfBoundsException("Index must be > 0 and < " + this.size);

        T rem = (T)this.array[index] ;
        this.array[index] = null;
        for(int i = index; i < this.size - 1; i++){
            this.array[i] = this.array[i+1];
        }

        this.array[this.size - 1] = null;
        this.size--;
        return rem;
     }

    @Override
    public int indexOf(Object o)
    {
        if ( null == o) {
            for (int i = 0; i < size; ++i)
                if (null == array[i])
                    return i;
        } else {
            for (int i = 0; i < size; ++i)
                if (o.equals(array[i]))
                    return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o)
    {
        if (null == o || 0 == this.size)
            return -1;

        int index = -1;
        for(int i = this.size - 1; i > 0; --i){
            if (null != this.array[i] && this.array[i].equals(o))
            {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public ListIterator<T> listIterator()
    {
        return new MyIteratorArrayList<T>(this);
    }

    @Override
    public ListIterator<T> listIterator(int index)
    {
        return new MyIteratorArrayList<T>(this, index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new RuntimeException("the method not implemented");
    }

   public class MyIteratorArrayList<T> implements ListIterator<T> {
        private int icur;
        private MyArrayList array;

        MyIteratorArrayList(MyArrayList array) {
            this.icur= -1;
            this.array = array;
        }

        MyIteratorArrayList(MyArrayList array, int index) {
            this.icur = index;
            this.array = array;

        }

        public boolean hasNext() {
            return !this.array.isEmpty() && (array.size() - 1) > this.icur;
        }
        public boolean hasPrevious() {
            return !this.array.isEmpty() && 0 < this.icur;
        }
        public int nextIndex() {
            return ++icur;
        }
        public int previousIndex() {
            return ++icur;
        }
        public T next() throws NoSuchElementException {
            if(!this.hasNext()) {
                throw new NoSuchElementException();
            }
            ++ this.icur;
            return (T)array.get(this.icur);
        }
        public T previous() throws NoSuchElementException{
            if(!this.hasPrevious())
                throw new NoSuchElementException();
            --this.icur;
            return (T)array.get(this.icur);
        }
        public void add(T elem) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("add() not supported");
        }
        public void set(T elem) throws UnsupportedOperationException {
            if (0> this.icur || array.size() < this.icur )
                throw new IllegalStateException("you have to first invoke next() or prev()");

            array.set(this.icur, elem);
        }
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("remove() not supported");
        }
    }
}
