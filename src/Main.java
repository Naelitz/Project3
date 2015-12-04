
public class Main
{
	static int[] numArray; // Will hold the array of ints.
	static int[] statArray; // Used to arrange stats more neatly.
	static int size;
	static int repCount = 10;
	static int swapCount;
	static int compareCount;
	static double time;
	static int bubbleAvg;

	public static void main(String[] args)
	{
		for (size = 100; size <= 20000; size += 100)
		{
			numArray = new int[size];
			for (int i = 0; i < size; i++)
			{
				numArray[i] = (int) (Math.random() * 1000000);
				// System.out.println("index: " + i + " is " + numArray[i]);
			}
			for (int i = 1; i <= repCount; i++)
			{
				// make call to sorting method here (just remove //)
				// bubbleSort();
				//insertionSort();
				// selectionSort();
				 quickSort(0, size - 1);
				// shellSort();
				// TODO Auto-generated method stub
			}
			System.out.println(size + " " + time / repCount);
			statClear();
		}
	}

	static public boolean isSorted()
	// Returns true if the array values are sorted and false otherwise.
	{
		boolean sorted = true;
		for (int index = 0; index < (size - 1); index++)
			if (numArray[index] > numArray[index + 1])
				sorted = false;
		return sorted;
	}

	static void bubbleSort()
	{
		double startTime = System.currentTimeMillis();

		// Sorts the values array using the bubble sort algorithm.
		{
			int current = 0;

			while (current < (size - 1))
			{
				bubbleUp(current, size - 1);
				current++;
			}
		}
		time += (System.currentTimeMillis() - startTime);
	}

	static void bubbleUp(int startIndex, int endIndex)
	// Switches adjacent pairs that are out of order
	// between values[startIndex]..values[endIndex]
	// beginning at values[endIndex].
	{
		for (int index = endIndex; index > startIndex; index--)
		{
			if (numArray[index] < numArray[index - 1])
			{
				swap(index, index - 1);
			}
			compareCount++;
		}

	}

	static public void swap(int index1, int index2)
	// Precondition: index1 and index2 are >= 0 and < SIZE.
	//
	// Swaps the integers at locations index1 and index2 of the values array.
	{
		int temp = numArray[index1];
		numArray[index1] = numArray[index2];
		numArray[index2] = temp;
		swapCount++;
	}

	static void insertionSort()
	{
		// Sorts the values array using the insertion sort algorithm.
		double startTime = System.currentTimeMillis();
		for (int count = 1; count < size; count++)
			insertItem(0, count);
		time += (System.currentTimeMillis() - startTime);
	}

	static void insertItem(int startIndex, int endIndex)
	// Upon completion, values[0]..values[endIndex] are sorted.
	{
		boolean finished = false;
		int current = endIndex;
		boolean moreToSearch = true;
		while (moreToSearch && !finished)
		{
			compareCount++;
			if (numArray[current] < numArray[current - 1])
			{
				swap(current, current - 1);
				current--;
				moreToSearch = (current != startIndex);
			} else
				finished = true;
		}
	}

	static void selectionSort()
	{
		// Sorts the values array using the selection sort algorithm.
		double startTime = System.currentTimeMillis();
		int endIndex = size - 1;
		for (int current = 0; current < endIndex; current++)
			swap(current, minIndex(current, endIndex));
		time += (System.currentTimeMillis() - startTime);
	}

	static int minIndex(int startIndex, int endIndex)
	// Returns the index of the smallest value in
	// values[startIndex]..values[endIndex].
	{
		int indexOfMin = startIndex;
		for (int index = startIndex + 1; index <= endIndex; index++)
			if (numArray[index] < numArray[indexOfMin])
				indexOfMin = index;
		compareCount++;
		return indexOfMin;
	}

	static int split(int first, int last)
	{
		int splitVal = numArray[first];
		int saveF = first;
		boolean onCorrectSide;

		first++;
		do
		{
			onCorrectSide = true;
			while (onCorrectSide) // move first toward last
				if (numArray[first] > splitVal)
					onCorrectSide = false;
				else
				{
					first++;
					onCorrectSide = (first <= last);
				}

			onCorrectSide = (first <= last);
			while (onCorrectSide) // move last toward first
				if (numArray[last] <= splitVal)
					onCorrectSide = false;
				else
				{
					last--;
					onCorrectSide = (first <= last);
				}

			if (first < last)
			{
				swap(first, last);
				first++;
				last--;
			}
		} while (first <= last);

		swap(saveF, last);
		return last;
	}

	static void quickSort(int first, int last)
	{
		System.out.println(first + " " + last);
		double startTime = System.currentTimeMillis();
		if (first < last)
		{
			int splitPoint;

			splitPoint = split(first, last);
			// values[first]..values[splitPoint - 1] <= splitVal
			// values[splitPoint] = splitVal
			// values[splitPoint+1]..values[last] > splitVal

			quickSort(first, splitPoint - 1);
			quickSort(splitPoint + 1, last);
		}
		time += (System.currentTimeMillis() - startTime);
		
	}

	static void shellSort()
	{
		
	}

	static void print(int size)
	{
		for (int i = 0; i < size; i++)
		{
			System.out.println(numArray[i]);
		}
	}

	static void statClear()
	{
		swapCount = 0;
		compareCount = 0;
		time = 0;
	}

}
