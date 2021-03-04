package linearpub;

import linkedlist.DynamicListImpl;
//import linkedlist.LinkedList;

/*
 * LinkedListFactory
 * 	Factory that generates and returns linked list
 */

public class LinkedListFactory {

	public static <T> DynamicList<T> newList() {
		//return new empty LinkedList
//		return null;
		//Add your class name and un-comment next line (and comment prev line)
		return new DynamicListImpl<T>();
	}

}
