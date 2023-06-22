package heaps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JOptionPane;
import heaps.util.HeapToStrings;
import heaps.validate.MinHeapValidator;
import timing.Ticker;

public class MinHeap<E, P extends Comparable<P>> implements MinPriorityQueue<E,P> {
	
	private static final int INVALID_POSITION = -1;    // Sentinel value for an invalid position in heap storage
	
	/**
	 * Class to hold a PQEntry (Value and Priority pair).
	 * Also includes data needed specifically for an Adaptable Heap-based PQ's Entry: 
	 *  the position of the Entry in the heap storage.  This is useful when updating an Entry's
	 *  priority directly via the Entry object, to avoid needing to search through the 
	 *  heap storage to find the Entry in order to fix up the heap to restore the heap
	 *  ordering property, if necessary.
	 * 
	 * This is an "inner class".  See: https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html for details.
	 * 
	 */
    private class HeapPQEntry implements PQEntry<E, P> {
    	// DONE: Do not modify
		private final E element;  // Data Element
		private P priority;       // Priority
		private int position;     // Current index in heap storage
		
		/**
		 * Construct a new Entry element 
		 * 
		 * @param element element data
		 * @param priority priority value
		 */
		private HeapPQEntry(E element, P priority) {
			// DONE: Do not modify
			this.element = element;
			this.priority = priority;	
			this.position = INVALID_POSITION;
		}
		
		@Override
		public E getElement() {
			// DONE: Do not modify
			return element;
		}

		@Override
		public P getPriority() {
			// DONE: Do not modify
			return priority;
		}

		@Override
		public void updatePriority(P newPriority) {
			// DONE: Do not modify
			// Change the priority
			this.priority = newPriority; 
			// Ask the MinHeap to fix up the heap as necessary 
			//  to restore the heap ordering property, in light of the updated priority.
			MinHeap.this.repairHeapAtEntry(this);
		}
		
		@Override
		public String toString() {
			// DONE: Do not modify (We really really mean that.  The unit tests depend on this!)
			return "(E=" + element + ", P=" + priority + ")";
		}
	}
    
    /****************************** CLASS FIELDS ******************************/


    // Heap storage: an array of Entry objects
	private final ArrayList<HeapPQEntry> entries;

	// Bookkeeping: not necessary for functionality
	private final Ticker ticker;

    // DONE: Do not add more instance variables


	@SuppressWarnings("unchecked")
	@Override
	public Iterator<PQEntry<E, P>> iterator() {
		// DONE: Do not modify (We really really mean that.  The unit tests depend on this!)		
		ArrayList<? extends PQEntry<E,P>> alsoEntries = this.entries;
		return (Iterator<PQEntry<E, P>>)alsoEntries.iterator();
	}

	
	/**
	 *   Represent the heap as a sequence of ordered pairs, with each
	 *   pair containing the data and priority of an Entry in the heap.
	 *   Sequence is in order of heap storage, so level-by-level starting 
	 *   with the root. 
	 */
	public String toString() {
		// DONE: Do not modify (We really really mean that.  The unit tests depend on this!)
		String s = "";
		for(PQEntry<?,?> e:entries) {
			s+=e + " ";
		}
		return s.trim();  // Remove the trailing space
	}
	
	/**
	 * Create a min heap with given capacity and bookkeeping ticker.
	 * 
	 * @param maxSize max anticipated size of heap
	 * @param ticker bookkeeping operations accumulator
	 */
	@SuppressWarnings("unchecked")
	public MinHeap(int maxSize, Ticker ticker) {
		// DONE: Do not modify
		this.entries = new ArrayList<HeapPQEntry>(maxSize);
		this.ticker = ticker;
	}	

	/**
	 * Create an empty min heap.
	 */
	public MinHeap() {
		// DONE: Do not modify
		this.entries = new ArrayList<HeapPQEntry>();
		this.ticker = new Ticker();
	}
    
	/**
	 * Swap the Entries at indices i and j in the heap storage.
	 * 
	 * @param i position of one object to swap
	 * @param j position of other object to swap
	 */
	private void swap(int i, int j) {
		HeapPQEntry temp = entries.get(i); // set temporary copy of i
		ticker.tick();
		
		// changing elements
		entries.set(i, entries.get(j)); // replace element at i with j data
		ticker.tick();
		entries.set(j, temp); // replace element at j with temp (i) data
		ticker.tick();
		
		int tempPosition = entries.get(i).position;
		ticker.tick();
		
		// changing positions
		entries.get(i).position = entries.get(j).position;
		ticker.tick();
		entries.get(j).position = tempPosition;
		ticker.tick();
	
	}

	@Override
	public boolean isEmpty() {
		// DONE: Do not modify
		return entries.isEmpty();
	}

	@Override
	public int size() {
		return entries.size();
	}

	/**
	 * Update heap to restore the heap ordering property if necessary,
	 *  given that the Entry passed as a parameter (and only this Entry) 
	 *  may currently violate the heap ordering property due to having its
	 *  priority updated.
	 *
	 * @param entry Entry with which to start 
	 */
	private void repairHeapAtEntry(MinHeap<E, P>.HeapPQEntry entry) { 
		bubbleUp(entry.position);
		ticker.tick();
		bubbleDown(entry.position);
		ticker.tick();
	}

	@Override
	public PQEntry<E, P> insert(E thing, P priority) {
		HeapPQEntry newEntry = new HeapPQEntry(thing, priority);
		ticker.tick();
		newEntry.position = entries.size();
		ticker.tick();
		entries.add(newEntry); 
		ticker.tick();
		bubbleUp(newEntry.position);
		ticker.tick();
		return newEntry;
	}

	
	@Override
	public PQEntry<E, P> extractMin() {
		HeapPQEntry first = entries.get(0);
		ticker.tick();
		HeapPQEntry last = entries.get(size() - 1);
		ticker.tick();
		last.position = 0;
		ticker.tick();
		
		entries.remove(size() - 1);
		ticker.tick();
		
		if (entries.size() == 0) {
			ticker.tick();
			return first;
		}
		
		entries.set(0, last);
		ticker.tick();
		bubbleDown(0);
		ticker.tick();
		return first;
		
	}

	/**
	 * Remove Entry stored at the specified index from the heap,
	 *  and repair the heap as necessary.
	 *  
	 *  
	 * @param index index in array storage at which to remove Entry
	 */
	private void removeAtIndex(int index) {
		// TODO	
		// NOTE: implementing this method is optional.
		//       Full credit may be earned for the assignment
		//       without implementing this method.
	}
	
	@Override
	public PQEntry<E, P> peekMin() {
		// DONE: Do not modify
		return isEmpty() ? null : entries.get(0);
	}

	/**
	 * Index of the Entry in heap storage that is the
	 *  parent of the index passed as a parameter.
	 * @param index current index
	 * @return parent index
	 */
	private int parentIndex(int index) {
		ticker.tick();
        return (index - 1) / 2;
	}

	/**
	 * Index of the Entry in heap storage that is the
	 *  left child of the index passed as a parameter.
	 * @param index current index
	 * @return left child index
	 */
	private int leftChildIndex(int index) {
		ticker.tick();
        return (index * 2) + 1;
		
	}
	
	/**
	 * Index of the Entry in heap storage that is the
	 *  right child of the index passed as a parameter.
	 * @param index current index
	 * @return right child index
	 */
	private int rightChildIndex(int index) {
		ticker.tick();
        return (index * 2) + 2;
	}

	/**
	 * Propagate the Entry stored in the heap at the index
	 * passed in as a parameter downward as necessary 
	 * to restore the heap ordering property.
	 * @param startIndex current index
	 */
	private void bubbleDown(int startIndex) {
		HeapPQEntry startEntry = entries.get(startIndex);
		ticker.tick();
		
		int leftChild = leftChildIndex(startIndex);
		ticker.tick();
		int rightChild = rightChildIndex(startIndex);
		ticker.tick();
		int bubbleChild = 0;
		ticker.tick();
		
		//P leftChildPriority = entries.get(leftChild).getPriority();
		//P rightChildPriority = entries.get(rightChild).getPriority();
		
		if (leftChild > size() - 1) {
			ticker.tick();
			return;
		}
		if (rightChild > size() - 1) {
			bubbleChild = leftChild;
			ticker.tick();
		} else if (entries.get(leftChild).getPriority().compareTo(entries.get(rightChild).getPriority()) <= 0){
			bubbleChild = leftChild;
			ticker.tick();
		} else {
			bubbleChild = rightChild;
			ticker.tick();
		}
		
		P bubblePriority = entries.get(bubbleChild).getPriority();
		ticker.tick();
		P parentPriority = entries.get(startIndex).getPriority();
		ticker.tick();
		
		
		if (bubblePriority.compareTo(parentPriority) < 0) {
			swap(bubbleChild, startIndex);
			ticker.tick();
			bubbleDown(bubbleChild);
			ticker.tick();
		}
	}
	
	/**
	 * Propagate the Entry stored in the heap at the index
	 * passed in as a parameter upward as necessary 
	 * to restore the heap ordering property.
	 * @param startIndex current index
	 */
	private void bubbleUp(int startIndex) {
		while (startIndex > 0) {
			ticker.tick();
			if (entries.get(startIndex).getPriority().compareTo(entries.get(parentIndex(startIndex)).getPriority()) < 0) {
				ticker.tick();
				swap(startIndex, parentIndex(startIndex));
				ticker.tick();
				startIndex = parentIndex(startIndex);
				ticker.tick();
		    } else {
		    	ticker.tick();
		    	return;
		    }
		}
		
	}
		
	
	@Override
	public boolean remove(PQEntry<E, P> entry) {
		// Type cast the PQEntry to get access to the .position
		HeapPQEntry heapEntry = (HeapPQEntry)entry;

		// TODO
		// NOTE: implementing this method is optional.
		//       Full credit may be earned for the assignment
		//       without implementing this method.
        return false;
	}

	
	/**
	 * This is not the unit test, but you can run this as a Java Application
	 * and it will insert and extract 100 elements into the heap, printing
	 * the heap each time it inserts.
	 * 
	 * @param args arguments to main method
	 * @throws IllegalAccessException illegal access
	 * @throws IllegalArgumentException illegal argument
	 */
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		JOptionPane.showMessageDialog(null, "You are welcome to run this, but be sure also to run the TestMinHeap JUnit test");
		MinHeap<Integer, Integer> h = new MinHeap<Integer, Integer>(500, new Ticker());
		MinHeapValidator<Integer, Integer> v = new MinHeapValidator<Integer, Integer>(h);
		Random r = new Random();
		for (int i=0; i < 100; ++i) {
			v.check();
			Integer value = r.nextInt(1000);
			h.insert(value, value);
			v.check();
			System.out.println(HeapToStrings.toTree(h));
			//System.out.println("heap is " + h);
		}
		while (!h.isEmpty()) {
			int next = h.extractMin().getElement();
			System.out.println("Got " + next);
		}
	}


}
