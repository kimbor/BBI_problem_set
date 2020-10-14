import java.util.Arrays;

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
		
		boolean test1 = (binarySearch(array1, 4) == 5);
		boolean test2 = (binarySearch(array1, 1) == -1);
		boolean test3 = (binarySearch(array1, 2) == 2);
		boolean test4 = (binarySearch(array1, 5) == 6);
		boolean test5 = (binarySearch(array1, -1) == -1);
		boolean test6 = (binarySearch(array1, 10) == 9);
		System.out.println("Test 1 simple search? " + test1);
		System.out.println("Test 2 first value in array? " + test2);
		System.out.println("Test 3 duplicate value in array? " + test3);
		System.out.println("Test 4 value not present in array? " + test4);
		System.out.println("Test 5 value before beginning of array? " + test5);
		System.out.println("Test 6 value greater than end of array? " + test6);	
	}

    /**
     * Searches the specified array of ints for the specified value using the
     * binary search algorithm.  The array must be sorted prior to making this call.  If it
     * is not sorted, the results are undefined.  If the array contains
     * multiple elements with the specified value, there is no guarantee which
     * one will be found.
     *
     * @param a the array to be searched
     * @param key the value to be searched for
     * @return index of the search key, if it is contained in the array;
     *         otherwise, <code>(-(<i>insertion point</i>) - 1)</code>.  The
     *         <i>insertion point</i> is defined as the point at which the
     *         key would be inserted into the array: the index of the first
     *         element greater than the key, or {@code a.length} if all
     *         elements in the array are less than the specified key.  Note
     *         that this guarantees that the return value will be &gt;= 0 if
     *         and only if the key is found.
     */
	static int binarySearch(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;

        if (target <= array[0])
        	return -1;
        
        while (low <= high) {
            int mid = (low + high) / 2;	// rounds down in case (low+high) is not even
            int midVal = array[mid];

            if (midVal < target)
                low = mid + 1;
            else if (midVal > target)
                high = mid - 1;
            else
                return mid; // target found
        }
        return -(low + 1);  // target not found.
    }
}
