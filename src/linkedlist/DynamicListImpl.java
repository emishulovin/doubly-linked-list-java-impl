package linkedlist;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import linearpub.DynamicList;

public class DynamicListImpl<E> implements DynamicList<E> {

//	private int listSize;
	private Node<E> firstNode;
	private Node<E> lastNode;

	public DynamicListImpl(Node<E> node) {
		this.firstNode = node;
	}

	public DynamicListImpl() {
		this.firstNode = null;
		this.lastNode = null;
	}

	/**
	 * Return number of elements in this list.
	 */
	@Override
	public int size() {
		
		// should be updated if there is any CUD (almost CRUD) method activity
		// maybe wont use this. performant, but too many methods need code now.
//		return listSize;
		if (this.firstNode == null) {
			return 0;
		}
		Node<E> node = firstNode;
		int count = 0;
		while (node != null) {
			count++;
			node = node.getNext();
		}
		return count;
	}

	/**
	 * Return true is this list contains no elements.
	 */
	@Override
	public boolean isEmpty() {
		// should be true if a list is used but without nodes.
		return this.size() == 0;
	}

	/**
	 * Return element at given index.
	 */
	@Override
	public E get(int index) {
		Node<E> node = this.firstNode;
		int count = 0;
		while (count != index) {
			count++;
			node = node.getNext();
		}
		return node.getData();
	}

	/**
	 * Return first element Throws RuntimeException if list is empty
	 */
	@Override
	public E first() {
		// do I need to catch these as well?
		E first = this.firstNode.getData();
		if (first == null) {
			throw new RuntimeException();
		}
		return first;
	}

	/**
	 * Return last element Throws RuntimeException if list is empty
	 */
	@Override
	public E last() {

		E last = this.lastNode.getData();
		if (last == null) {
			throw new RuntimeException();
		}
		return last;
	}

	/**
	 * Add the passed element to start of list
	 */
	@Override
	public void addFirst(E newElem) {
		Node<E> node = new Node<>(newElem);

		if (this.size() == 0) {
			this.firstNode = node;
			this.lastNode = node;
			return;
		} 
		node.setNext(this.firstNode);
		this.firstNode.setPrev(node);
		this.firstNode = node;
	}

	/**
	 * Add the passed element to end of list
	 */
	@Override
	public void addLast(E newElem) {
		Node<E> node = new Node<E>(newElem);
		node.setNext(null);
		node.setPrev(this.lastNode);
		this.lastNode.setNext(node);
		this.lastNode = node;
	}

	/**
	 * Alias for "addLast" (same functionality).
	 */
	@Override
	public void add(E newElem) {
		//adding a case if the first, because the given smoke test uses that format instead of addFirst method
		if (this.size() == 0) {
			this.addFirst(newElem);
		} else {
			this.addLast(newElem);
		}
	}

	/**
	 * Insert passed element into list at the passed index Throws
	 * IndexOutOfBoundsException if passed index is invalid.
	 */
	@Override
	public void insert(int index, E newElem) {
		if (index > this.size() || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (index == this.size()) {
			this.addLast(newElem);
			return;
		}
		if (index == 0) {
			this.addFirst(newElem);
			return;
		}
		Node<E> node = firstNode;
		// find it
		int count = 0;
		while (count != index) {
			count++;
			node = node.getNext();
		}
		// insert
		Node<E> newNode = new Node<E>(newElem);
		newNode.setPrev(node.getPrev());
		newNode.setNext(node);
		node.getPrev().setNext(newNode);
		node.setPrev(newNode);
	}

	/**
	 * Remove first element Return removed element Throws RuntimeException if list
	 * is empty
	 */
	@Override
	public E removeFirst() {
		if (this.isEmpty()) {
			throw new RuntimeException();
		}
		Node<E> node = new Node<>(firstNode.getData());
		this.firstNode = this.firstNode.getNext();
		//  have to reset the next's prev, which was until now pointing to the first.
		this.firstNode.setPrev(null);
		return node.getData();
	}

	/**
	 * Remove last element Return removed element Throws RuntimeException if list is
	 * empty
	 */
	@Override
	public E removeLast() {
		if (this.isEmpty()) {
			throw new RuntimeException();
		}
		Node<E> node = this.lastNode;
		this.lastNode = this.lastNode.getPrev();
		//  have to reset the prev's next, which was until now pointing to the last.
		this.lastNode.setNext(null);
		return node.getData();
	}

	/**
	 * Reset the list so it is empty. If list is already empty, then do nothing No
	 * action is performed on the elements.
	 *
	 */
	@Override
	public void removeAll() {
		// will garbage collection collect all the nodes?
		this.firstNode = null;
		// needs this because pointers still exist, although test dosent cover it. 
		// from other side of list.
		// System.out.println(this.lastNode.getPrev().getData());
		this.lastNode = null;
	}

	/**
	 * Remove elem at index Return the removed element Throws
	 * IndexOutOfBoundsException if passed index is invalid.
	 */
	@Override
	public E removeIndex(int index) {
		if (index > this.size() || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		Node<E> node = findIndex(index);
		// set prev node's next to point to current nodes next
		node.getPrev().setNext(node.getNext());
		// set next node's prev to point to current nodes prev
		node.getNext().setPrev(node.getPrev());
		// dont  need to delete this node, nothing is pointing to it in the
		// stack.
		return node.getData();
	}

	/**
	 * Return a Java "List<E>" containing all elements in this list.
	 */
	@Override
	public List<E> toJavaList() {
		List<E> list = new ArrayList<>();
		Node<E> node = this.firstNode;
		while (node != null) {
			list.add(node.getData());
			node = node.getNext();
		}
		return list;
	}

	/**
	 * Return a new list containing the elements of this list between the given
	 * index "start" (inclusive) and the given index "stop" (exclusive). Throws
	 * IndexOutOfBoundsException if either passed index is invalid.
	 */
	@Override
	public DynamicList<E> subList(int start, int stop) {

		Node<E>node = this.findIndex(start);

		// System.out.println("Test. Here's the first node requrested: " + node.getData());
		Node<E> newFirst = node;
		int count = 0;
		// exclusive this time
		while (stop - start - 1 != count && node.getNext() != null) {
			node = node.getNext();
			count++;
		}
		// System.out.println("Count and data of last node: "+ count + " " + node.getData());
		// point the last to null
		node.setNext(null);
		DynamicList<E> newList = new DynamicListImpl<>(newFirst);
		return newList;
	}

	/**
	 * Remove first matching element (where searchFct outputs true) Return the
	 * removed element If no match, return null
	 */
	@Override
	public E remove(Function<E, Boolean> searchFct) {
		// uses removeIndex instead of repeating removal logic.
		Node<E> node = this.firstNode;
		int count = 0;
		while (node.getData() != null) {
			if (searchFct.apply(node.getData()) == true) {
				return this.removeIndex(count);
			}
			node = node.getNext();
			count++;
		}
		return null;
	}

	/**
	 * Return first matching element (where searchFct outputs true) Return -1 if no
	 * match
	 */
	@Override
	public int find(Function<E, Boolean> searchFct) {
		Node<E> node = this.firstNode;
		int count = 0;
		while (node.getData() != null) {
			if (searchFct.apply(node.getData()) == true) {
				// return count;
				return count;
			}
			node = node.getNext();
			count++;
		}
		return -1;
	}

	//if int
	// @Override
	// public E find(Function<E, Boolean> searchFct) {
	// 	Node<E> node = this.firstNode;
	// 	int count = 0;
	// 	while (node.getData() != null) {
	// 		if (searchFct.apply(node.getData()) == true) {
	// 			// return count;
	// 			return node.getData();
	// 		}
	// 		node = node.getNext();
	// 		count++;
	// 	}
	// 	return -1;
	// }

	/**
	 * Returns one-line user-friendly message about this object Helpful method
	 * especially for debugging.
	 */
	public String toString() {
		return "This is a linked list!";
	}

	public Node<E> findIndex(int index) {
		Node<E> node = this.firstNode;
		int count = 0;
		while (count != index) {
			count++;
			node = node.getNext();
		}
		return node;
	}

}
