package graph;

/** A priority queue: implemented using a min heap.
 *  You may not use any Java built-in classes, you should implement
 *  PriorityQueue yourself. You may use/modify the MinHeap code posted
 *  by the instructor under Examples, as long as you understand it. */
public class PriorityQueue {



	int[] positions ;
	PriorityQueueNode[] heap ;
	int maxsize;
	int tmpsize;

	/** Insert a new element (nodeId, priority) into the heap.
     *  For this project, the priority is the current "distance"
     *  for this nodeId in Dikstra's algorithm. */

	public PriorityQueue(int size) {
		// FILL IN
		maxsize = size + 1;
		//initialize heap size
		heap = new PriorityQueueNode[maxsize];
		heap[0] = new PriorityQueueNode(Integer.MIN_VALUE, -1);
		//initialize positions array
		positions = new int[size];

	}
	private int parent(int pos) {
		return pos / 2;
	}

	/** Return the index of the left child */
	private int leftChild(int pos) {
		return 2 * pos;
	}

	/** Return the index of the right child */
	private int rightChild(int pos) {
		return 2 * pos + 1;
	}

	/** Returns true if the node in a given position is a leaf */
	private boolean isLeaf(int pos) {
		return ((pos > tmpsize / 2) && (pos <= tmpsize));
	}
	private void swap(int pos1, int pos2){
		PriorityQueueNode tmp;
		//sawp position in the heap
		tmp = heap[pos1];
		heap[pos1] = heap[pos2];
		heap[pos2] = tmp;

		//get the id for vertex in the heap
		int tmpVertex1id = heap[pos1].getID();
		int tmpVertex2id = heap[pos2].getID();

		//update postition array
		//int tmpVertex = positions[tmpVertex1id];
		positions[tmpVertex1id] = pos1;//positions[tmpVertex2id];
		positions[tmpVertex2id] = pos2;//tmpVertex;

	}


	private void pushdown(int position) {
		int smallestchild;
		while (!isLeaf(position)) {
			smallestchild = leftChild(position); // set the smallest child to left child
			if ((smallestchild < tmpsize) && (heap[smallestchild].getCost() > heap[smallestchild + 1].getCost()))
				smallestchild = smallestchild + 1; // right child was smaller, so smallest child = right child

			// the value of the smallest child is less than value of current,
			// the heap is already valid
			if (heap[position].getCost() <= heap[smallestchild].getCost())
				return;
			swap(position, smallestchild);
			position = smallestchild;
		}
	}


    public boolean isempty()
	{
	   if(tmpsize == 0)
	   {
	   	return true;
	   }
	   return false;
	}


	public void insert( int nodeId, int priority)
	{
       PriorityQueueNode newElem = new PriorityQueueNode(nodeId,priority);

		tmpsize++;
		heap[tmpsize] = newElem;
		positions[nodeId] = tmpsize;

		int current = tmpsize;
		while(heap[current].getCost()<heap[parent(current)].getCost())
		{
			swap(current, parent(current));
			current = parent(current);
		}

		// FILL IN CODE

	}

    /**
     * Remove the element with the minimum priority
     * from the min heap and return its nodeId.
     * @return nodeId of the element with the smallest priority
     */
	public int removeMin()
	{
		swap(1, tmpsize);
		tmpsize--;
		if(tmpsize != 0){
			pushdown(1);
		}
		return heap[tmpsize + 1].getID();
		// FILL IN CODE
	}

    /**
     * Reduce the priority of the element with the given nodeId to newPriority.
     * You may assume newPriority is less or equal to the current priority for this node.
     * @param nodeId id of the node
     * @param newPriority new value of priority
     */
	public void reduceKey(int nodeId, int newPriority)
	{
		int updatePos = positions[nodeId];

		PriorityQueueNode p = heap[updatePos];

		if(newPriority < p.getCost()){

			p.setPriority(newPriority);
			int current = updatePos;

			while (heap[current].getCost() <heap[parent(current)].getCost())
			{
				swap(current, parent(current));
				current = parent(current);
			}

		}
	}
	public int getPosition(int a)
	{
		return positions[a];
	}

}

