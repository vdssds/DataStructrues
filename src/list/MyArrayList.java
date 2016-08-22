package list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<T> implements Iterable<T> {

	private static final int DEFAULT_CAPACITY = 10;
	private int theSize;
	private T[] items;
	
	public MyArrayList() {
		clear();
	}
	
	public void clear(){
		theSize = 0;
		ensureCapacity(DEFAULT_CAPACITY);
	}
	
	public int size() {
		return theSize;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public void trimToSize() {
		ensureCapacity(size());
	}
	
	public T get(int index) {
		if(index < 0 || index >= size())
			throw new ArrayIndexOutOfBoundsException();
		return items[index];
	}
	
	public T set(int index, T newValue) {
		T t = get(index);
		items[index] = newValue;
		return t;
	}
	
	@SuppressWarnings("unchecked")
	private void ensureCapacity(int newCapacity) {
		if(newCapacity < theSize) return;
		T[] olds = items;
		items = (T[]) new Object[newCapacity];
		for(int i = 0; i < theSize; i++){
			items[i] = olds[i];
		}
	}
	
	public boolean add(T e) {
		add(size(), e);
		return true;
	}
	
	public void add(int index, T e) {
		if(items.length == size()){
			ensureCapacity(size() * 2 + 1);
		}
		for(int i = theSize; i > index; i-- ){
			items[i] = items[i - 1];
		}
		items[index] = e;
		theSize++;
	}
	
	public T remove(int index) {
		T t = items[index];
		for(int i = index; i < size() - 1; i++){
			items[i] = items[i + 1];
		}
		theSize--;
		return t;
	}

	@Override
	public Iterator<T> iterator() {
		return new ArrayListIterator();
	}
	
	private class ArrayListIterator implements Iterator<T>{

		private int cursor = 0;
		@Override
		public boolean hasNext() {
			return cursor < size();
		}

		@Override
		public T next() {
			if(!hasNext())
				throw new NoSuchElementException();
			return items[cursor++];
		}
		
		@Override
		public void remove() {
			MyArrayList.this.remove(--cursor);
		}
	}

}
