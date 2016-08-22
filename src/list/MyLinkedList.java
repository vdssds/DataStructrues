package list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements Iterable<T> {
	
	private class Node{
		public Node(T d, Node p, Node n) {
			data = d;
			prev = p;
			next = n;
		}
		
		public T data;
		public Node prev;
		public Node next;
	}
	
	private int size = 0;
	private int modCount = 0;
	private Node beginMarker;
	private Node endMarker;
	
	public MyLinkedList() {
		clear();
	}
	
	public void clear() {
		beginMarker = new Node(null, null, null);
		endMarker = new Node(null, null, null);
		beginMarker.next = endMarker;
		size = 0;
		modCount++;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	public boolean add(T t){
		add(size, t);
		return true;
	}
	public void add(int index, T t){
		addBefore(getNode(index), t);
	}
	
	public T get(int index){
		return getNode(index).data;
	}
	
	public T set(int index, T newVal){
		Node p = getNode(index);
		T oldData = p.data;
		p.data = newVal;
		return oldData;
	}
	
	public T remove(int index){
		return remove(getNode(index));
	}

	private T remove(Node p) {
		p.next.prev = p.prev;
		p.prev.next = p.next;
		size--;
		modCount++;
		return p.data;
	}

	public void addBefore(Node p, T t) {
		Node newNode = new Node(t, p.prev, p);
		p.prev.next = newNode;
		p.prev = newNode;
		size++;
		modCount++;
	}

	private Node getNode(int index) {
		Node p;
		if(index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		if(index < (size / 2)){
			p = beginMarker.next;
			for(int i = 0; i < index; i++){
				p = p.next;
			}
		}
		else{
			p = endMarker;
			for(int i = size; i > index; i--){
				p = p.prev;
			}
		}
		return p;
	}

	@Override
	public Iterator<T> iterator() {
		return new LinkedListIterator();
	}

	private class LinkedListIterator implements Iterator<T>{
		
		private Node current = beginMarker.next;
		private int expectedModCount = modCount;
		private boolean okToRemove = false; 

		@Override
		public boolean hasNext() {
			return current != endMarker;
		}

		@Override
		public T next() {
			if(modCount != expectedModCount)
				throw new ConcurrentModificationException();
			if(!hasNext())
				throw new NoSuchElementException();
			T nextItem = current.data;
			current = current.next;
			okToRemove = true;
			return nextItem;
		}
		
		@Override
		public void remove() {
			if(modCount != expectedModCount)
				throw new ConcurrentModificationException();
			if(!okToRemove)
				throw new IllegalStateException();
			MyLinkedList.this.remove(current.prev);
			okToRemove = false;
			expectedModCount++;
		}
	}
}
