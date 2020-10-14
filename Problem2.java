/**
 * Problem 2: Search
You are given an array A of n integers. The elements of A are sorted in ascending order, and
are not necessarily unique. You are also given a target integer x. Implement a search algorithm
which finds a location j in the array such that all elements in the range A[0], …, A[j - 1] are
strictly less than x, and all elements in the range A[j], …, A[n - 1] are greater than or equal to
x. (Note that x itself need not appear in A for these conditions to be satisfied.) If no suitable
location is found, return -1. Your solution should have runtime O(log n).

 * @author Kim
 *
 */
public class Problem2 {

	public static void main(String[] args) {
		int[] array1 = {1, 1, 2, 2, 3, 4, 7, 8, 8, 9};
		
		// would prefer to use jUnit tests here, but I wasn't sure whether external libraries were allowed
		boolean test1 = (binarySearch(array1, 4) == 5);
		System.out.println("simple search? " + test1);

		boolean test2 = (binarySearch(array1, 1) == -1);
		System.out.println("first value in array? " + test2);
		
		boolean test3 = (binarySearch(array1, 2) == 2);
		System.out.println("duplicate value in array? " + test3);
		
		boolean test7 = (binarySearch(array1, 8) == 7);
		System.out.println("duplicate value in array? " + test7);
		
		boolean test4 = (binarySearch(array1, 5) == 6);
		System.out.println("binarySearch(array1, 5)=" + binarySearch(array1, 5) + " value not present in array? " + test4);
		
		boolean test5 = (binarySearch(array1, -1) == -1);
		System.out.println("value before beginning of array? " + test5);
		
		boolean test6 = (binarySearch(array1, 10) == -1);
		System.out.println("value greater than end of array? " + test6);	

		boolean test8 = (binarySearch(array1, 9) == 9);
		System.out.println("last value in array? " + test8);	
	}

    /**
     * Searches the specified array of ints for the specified value using the
     * binary search algorithm.  The array must be sorted prior to making this call.  If it
     * is not sorted, the results are undefined.  If the array contains
     * multiple elements with the specified value, the index of the first element is returned.
     * If array does not contain the target, the insertion point index is returned.
     * If target is less than or equal to the first value in the array, -1 is returned.
     * If target is greater than the last value in the array, -1 is returned. 
     */
	static int binarySearch(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;

        // if target falls outside range of array values then no suitable location is found, return -1
        if (target <= array[low] || target > array[high])
        	return -1;
        
        while (low <= high) {
        	// find the value at the midpoint of the array
            int mid = (low + high) / 2;	// rounds down if (low+high) is not even
            int midVal = array[mid];

            // if midpoint value is less than target
            if (midVal < target)
            	// then narrow search to top half of array
                low = mid + 1;
            else if (midVal > target)
            	// then narrow search to bottom half of array
                high = mid - 1;
            else {
            	// target found
            	// need to make sure that we've found the first instance of target
            	while (array[mid-1] == target)
            		mid--;		// TODO: this is not log(n) complex 
                return mid;
            }
        }
        return low;  // target not found, this is the insertion point
    }
}
