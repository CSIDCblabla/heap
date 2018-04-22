package TaskManager;


public class TaskManager {

    Task[] heap;
    int[] availableData; // the reference array
    int size;			 // the current number of tasks awaiting completion

    /**
     * Creates an empty data structure with the given capacity.
     * The capacity dictates how many different tasks may be put into the data structure.
     * Moreover, the capacity gives an upper bound to the serial number of tasks to be put in the data structure.
     * 
     */
    public TaskManager(int capacity){
    	this.heap = new Task [capacity];
    	this.availableData = new int [capacity + 1];
    	this.size = 0; 
    }

    /**
     * Returns the size of the heap.
     *
     * @return the size of the heap
     */
    public int size(){
		return this.size;
    }

    
    /**
     * Inserts a given Task into the heap.
     *
     * @param t - the Task to be inserted.
     */
    public void insert(Task t){

			this.heap [this.size + 1] = t;
			this.availableData[t.serial] = this.size + 1;	
			this.size++;
			if (this.size > 1) PercolateUp(this.heap , t);
	}
		

    /** Put a given task in the right place of a given heap, according 
     * the priority of the task.
     * @author dean
     * @param heap
     * @param t
     */
	public void PercolateUp(Task [] heap , Task t) {
		int a = this.availableData[t.serial];
		
		while (t.compareTo( heap[a / 2]) > 0) {
			
			this.availableData[heap[a / 2].serial] = a;
			this.availableData[heap[a].serial] = a / 2;
			
			Task k = heap [a];
			heap [a] = heap[a / 2];
			heap[a / 2] = k;
			
			if (a < 4) break;
			
			a = a / 2;
			}
		}
			
	

    /**
     * Returns the Task with the highest priority in the heap.
	 * You may not use any loops (or recursion) in this function.
     *
     * @return the Task with the highest priority in the heap.
     */
    public Task findMax(){
		return this.heap[1];
    }

    /**
     * Removes and returns the Task with the highest priority from the heap.
     * Recall that you are not allowed to traverse all elements of the heap array.
     *
     * @return the Task with the highest priority in the heap.
     */

    public Task extractMax(){
    	
		Task k = this.heap[1];
		if(size == 1) {
			size--;
			this.heap[1] = null;
			return k;
		}
		this.heap[1] = this.heap[size];
		this.heap[size] = null;
		
		this.availableData[k.serial] = 0;
		this.availableData[this.heap[1].serial] = 1;
		size--;
		if(size == 1) return k;
		PercolateDown(this.heap , this.heap[1]);
		return k;
    }
    /**Put a given task in the right place of a given heap, according 
     * the priority of the task.
     * @author dean
     * @param heap
     * @param t
     */
    public void PercolateDown(Task [] heap , Task t) {
    	int a = this.availableData[t.serial];
    	if(a == size / 2 && size % 2 == 0) {
			if(t.compareTo(heap[2 * a]) < 0) {

    			
				this.availableData[heap[2 * a].serial] = a;
				this.availableData[heap[a].serial] = 2 * a;
				
				Task k = t;
				heap[a] = heap[2 * a];
				heap[2 * a] = k;
			}
		}else {
				
    		while( (t.compareTo(heap[2 * a]) < 0 | t.compareTo(heap[(2 * a) + 1]) < 0)) {
    		
    			if(t.compareTo(heap[2 * a]) < 0) {

    			
    				this.availableData[heap[2 * a].serial] = a;
    				this.availableData[heap[a].serial] = 2 * a;
    				
    				Task k = t;
    				heap[a] = heap[2 * a];
    				heap[2 * a] = k;
    				a *= 2;
    				
    			} else {

    				this.availableData[heap[(2 * a) + 1].serial] = a;
    				this.availableData[heap[a].serial] = (2 * a) + 1;
    			
    				Task k = t;
    				heap[a] = heap[(2 * a) + 1];
    				heap[(2 * a) + 1] = k;
    				a *= 2 + 1;
    			}
    			if(a > (size / 2)) break;
    			if(size % 2 == 0 && a == size / 2) {
    				if(t.compareTo(heap[2 * a]) < 0) {

        			
    					this.availableData[heap[2 * a].serial] = a;
    					this.availableData[heap[a].serial] = 2 * a;
    				
    					Task k = t;
    					heap[a] = heap[2 * a];
    					heap[2 * a] = k;
    					break;
    				}
    			}
    		}
		}
    }
		


    
    /**
     * Updates the priority of a given task.
	 * Does nothing if the task is not already in the heap.
     * Recall that you are not allowed to traverse all elements of the heap array.
     * Think about what can go wrong in the heap as you change the priority of a given task. How will you fix it?
	 *
     * @param t - the given task
     * @param newPriority - the new priority of the given task.
     */
    public void updatePriority(Task t, int newPriority){
		if(this.availableData[t.serial] != 0 && t.priority != newPriority) {
			if (newPriority > t.priority) {
				t.priority = newPriority;
				PercolateUp(this.heap , t);
			}else {
				t.priority = newPriority;
				PercolateDown(this.heap , t);
			}
		}
    }
    
  
	
    /*
     * Test code; output should be:
     * task: abbreviate notes, priority: 10
	 * task: download new version, priority: 20
	 * task: bring food, priority: 11
	 * task: abbreviate notes, priority: 10
     * task: clear histories, priority: 3
     * task: download new version, priority: 0
     */
    public static void main (String[] args){
    	TaskManager demo = new TaskManager(10);
    	Task a = new Task(1, 10, "abbreviate notes");
    	Task b = new Task(2, 2, "bring food");
    	Task c = new Task(3, 3, "clear histories");
    	Task d = new Task(4, 20, "download new version");
    	
    	demo.insert(a);
    	System.out.println(demo.findMax());
    	
    	demo.insert(b);
    	demo.insert(c);
    	demo.insert(d);
    	//System.out.println(demo.extractMax());
    	demo.updatePriority(b, 11);
    	demo.updatePriority(d, 0);
  
    	System.out.println(demo.findMax());
    	
    	
    	System.out.println(demo.extractMax());
    	System.out.println(demo.extractMax());
    	System.out.println(demo.extractMax());
    	System.out.println(demo.extractMax());
    	
    }
}
