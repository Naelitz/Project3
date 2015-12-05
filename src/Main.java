
public class Main
{
	static int[] numArray; // Will hold the array of ints.
	static double[][] statArray = new double[7][4]; // Used to arrange stats
													// more neatly.
	static int[] pratt; // Set to hold the values of Pratt's sequence.
	static int LENGTH = 142; // This is length of pratt array.
	static int size;
	static int repCount = 10;
	static int swapCount;
	static int compareCount;
	static double time;
	static int bubbleAvg;

	public static void main(String[] args)
	{
		prattInterval();
		// Prints label to know order of stats.
		System.out.println("size \t Bubble Swaps \t Bubble Compare \t Bubble time "
				+ "\t Selection Swaps \t Selection Compares \t Selection Time "
				+ "\t Insertion Swaps \t Insertion Compares \t Insertion time "
				+ "\t Quick Swaps \t Quick Compares \t Quick Time "
				+ "\t Hibbard Swaps \t Hibbard Compares \t Hibbard Time "
				+ "\t Knuth Swaps \t Knuth Compares \t Knuth time " + "\t Pratt Swaps \t Pratt Compares \t Pratt time");
		for (size = 100; size <= 20000; size += 100)
		{
			// Sets stats to zero because the array only
			// holds one size worth of stats.
			initStatArray();

			// Runs through the sorts for amount of reps needed.
			for (int currentRep = 1; currentRep < repCount; currentRep++)
			{
				initValues(); // Creates array
				bubbleSort();
				storeStat(0); // Stores the stats for bubble
				statClear(); // Resets counters
				initValues();
				selectionSort();
				storeStat(1); // Stores the stats for selection
				statClear();
				initValues();
				insertionSort();
				storeStat(2); // Stores the stats for insertion
				statClear();
			}
			// This loop increases the rep count for the faster sorts.
			for (int currentRep = 1; currentRep < repCount * 100; currentRep++)
			{
				initValues();
				quickS(0, size - 1);
				storeStat(3); // Stores stats for Quick Sort
				statClear();
				initValues();
				shellSort(hibbardsinterval(), 2);
				storeStat(4); // Stores stats for Hibbard's Shell sort
				statClear();
				initValues();
				shellSort(knuthsInterval(), 3);
				storeStat(5); // Stores stats for Knuth's Shell sort
				statClear();
				//initValues();
				//prattSort();
				//storeStat(6);
				//statClear();
			}
			printStat();
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
		{
			if (numArray[index] < numArray[indexOfMin])
				indexOfMin = index;
			compareCount++;
		}
		return indexOfMin;
	}

	static void quickS(int first, int last)
	{
		double startTime = System.currentTimeMillis();

		if (first < last)
		{
			int i = first;		//Start at the two ends
			int j = last + 1;	//of the list.
			int x = numArray[first]; // This is the pivot value
			do
			{
				do		// Moving left
				{
					i++;
					compareCount++;
				} while (numArray[i] < x && i < size - 1);

				do		// Moving right
				{
					j--;
					compareCount++;
				} while (numArray[j] > x && j > first);

				if (i < j)
					swap(i, j);
				else
					break;

			} while (true);
			swap(first, j);
			quickS(first, j - 1); //Recursively call each side
			quickS(j + 1, last);  //of the list
			time += System.currentTimeMillis() - startTime;

		}
	}

	static void print(int size)
	{
		// This method is used to simply print the entire
		// array to confirm that it is being sorted.
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

	static void initValues()
	// Initializes the values array with random integers from 0 to 999999.
	{
		numArray = new int[size];
		for (int index = 0; index < size; index++)
			numArray[index] = (int) (Math.random() * 1000000);
	}

	static void shellSort(int a, int b)
	// Sorts items in the array using interval 
	// passed to it in the header.
	{
		double startTime = System.currentTimeMillis();
		int interval = a;
		do
		{
			for (int i = 0; i < (size - interval); i++)
			{
				for (int j = i; j >= 0; j -= interval)
				{
					if (numArray[j] <= numArray[j + interval])
					{
						compareCount++;
						break;
					} else
						swap(j, j + interval);
				}
			}
			interval /= b; // Divides the interval by number passed
							// in the header.
		} while (interval > 0);

		time += (System.currentTimeMillis() - startTime);

	}

	static int hibbardsinterval()
	{
		int d = 1;
		while (d < size)
			d *= 2;
		d -= 1;
		d /= 2;
		return d;
	}

	static int knuthsInterval()
	{
		int d = 1;
		while (d < size)
		{
			d *= 3;
			d += 1;
		}
		d /= 3;
		return d;
	}

	static void prattInterval()
	{
		int i, last2ind = 0, last3ind = 0;
		pratt = new int[LENGTH];

		pratt[0] = 1;
		for (i = 1; i < LENGTH; ++i)
		{
			if (pratt[last2ind] * 2 < pratt[last3ind] * 3)
			{
				pratt[i] = pratt[last2ind] * 2;
				last2ind++;
			} else if (pratt[last2ind] * 2 > pratt[last3ind] * 3)
			{
				pratt[i] = pratt[last3ind] * 3;
				last3ind++;
			} else
			{
				/* avoid repeats: don't output the same # twice! */
				pratt[i] = pratt[last2ind] * 2;
				last2ind++;
				last3ind++;
			}
		}
	}

	static void prattSort()
	{
		
		double startTime = System.currentTimeMillis();
		int interval = 1;
		while (pratt[interval] < size)
			interval++;
		interval--;
		// p = 0;
		do
		{

			for (int i = 0; i < (size - pratt[interval]); i++)
			{
				for (int j = i; j >= 0; j -= pratt[interval])
				{
					if (numArray[j] <= numArray[j + pratt[interval]])
					{
						compareCount++;
						break;
					} else
						swap(j, j + pratt[interval]);
				}
			}
			interval--;
		} while (interval > 0);

		time += (System.currentTimeMillis() - startTime);

	}

	static void printStat()
	{
		System.out.print("\n" + size + "\t");
		for (int i = 0; i < 6; i++)
		{
			for (int j = 1; j < 4; j++)
			{
				if (i > 2) // Then it is a faster sort and uses more reps.
					System.out.print(statArray[i][j] / (repCount * 100) + "\t");
				else
					System.out.print(statArray[i][j] / repCount + "\t");

			}

		}
	}

	static void storeStat(int sort)
	{
		statArray[sort][0] = size;
		statArray[sort][1] += swapCount;
		statArray[sort][2] += compareCount;
		statArray[sort][3] += time;

	}

	static void initStatArray()
	{
		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				statArray[i][j] = 0;
			}
		}
	}
}
