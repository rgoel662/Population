import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
/**
 *	Population - Sorts various cities by different criteria
 *
 *	Requires FileUtils and Prompt classes.
 *
 *	@author	Rishabh Goel
 *	@since	1/12/23
 */
public class Population {
	
	// List of cities
	private List<City> cities;
	
	// US data file
	private final String DATA_FILE = "usPopData2017.txt";
	
	public Population(){
		cities = new ArrayList<City>();
	}
	
	/**	Prints the introduction to Population */
	public void printIntroduction() {
		System.out.println("   ___                  _       _   _");
		System.out.println("  / _ \\___  _ __  _   _| | __ _| |_(_) ___  _ __ ");
		System.out.println(" / /_)/ _ \\| '_ \\| | | | |/ _` | __| |/ _ \\| '_ \\ ");
		System.out.println("/ ___/ (_) | |_) | |_| | | (_| | |_| | (_) | | | |");
		System.out.println("\\/    \\___/| .__/ \\__,_|_|\\__,_|\\__|_|\\___/|_| |_|");
		System.out.println("           |_|");
		System.out.println();
	}
	
	/**	Print out the choices for population sorting */
	public void printMenu() {
		System.out.println("1. Fifty least populous cities in USA (Selection Sort)");
		System.out.println("2. Fifty most populous cities in USA (Merge Sort)");
		System.out.println("3. First fifty cities sorted by name (Insertion Sort)");
		System.out.println("4. Last fifty cities sorted by name descending (Merge Sort)");
		System.out.println("5. Fifty most populous cities in named state");
		System.out.println("6. All cities matching a name sorted by population");
		System.out.println("9. Quit");
	}
	
	public static void main(String[] args){
		Population pl = new Population();
		pl.run();
	}
	
	/**
	 * Runner method that runs all other methods
	 */
	public void run(){
		populateList();
		printIntroduction();
		System.out.println(cities.size() + " cities in database\n");
		int choice = 0;
		while (choice != 9){
			printMenu();
			choice = Prompt.getInt("Enter Selection");
			switch(choice){
				case 1: leastPopCities(); //selection sort
						break;
				case 2: mostPopCities(); //merge sort
						break;
				case 3: first50Cities(); //insertion sort
						break;
				case 4: last50Cities(); //merge sort
						break;
				case 5: mostPopInState(); 
						break;
				case 6: sameNameCities();
						break;
			}
		}
	}

	/**
	 * Populates the cities arraylist by reading the DATA_FILE
	 */
	public void populateList(){
		Scanner kb = FileUtils.openToRead(DATA_FILE);
		kb.useDelimiter("[\t\n]");
		while(kb.hasNext()){
			cities.add(new City(kb.next(), kb.next(), kb.next(), kb.nextInt()));
		}
	}

	/**
	 * Helper method for swapping two indicies of a List
	 * 
	 * @param original	The list where the indicies will be swapped
	 * @param x			The first index	
	 * @param y			The second index
	 */
	private void swap(List<City> original, int x, int y) {
		City temp = original.get(x);
		original.set(x, original.get(y));
		original.set(y, temp);
	}
	
	/**
	 * Merge sort method
	 * 
	 * @param arrToSort	The array to sort
	 * @param startIdx	The starting index
	 * @param endIdx	The ending index
	 * @param isNames	Whether or not you are sorting by names
	 */
	private void mergeSort(List<City> arrToSort, int startIdx, int endIdx, boolean isNames)
	{
		if (startIdx >= endIdx) //array contains just a single element
			return; 

		int midIdx = startIdx + (endIdx - startIdx) / 2; //middle index
		mergeSort(arrToSort, startIdx, midIdx, isNames); //Divide the left half recursively
		mergeSort(arrToSort, midIdx + 1, endIdx, isNames); //Divide the right half recursively
				
		merge(arrToSort, startIdx, midIdx, endIdx, isNames); //merge the left and right half
	}
	
	/**
	 * Merge method of the merge sort algorithm
	 * 
	 * @param arrToSort	The array to sort
	 * @param startIdx	The start index
	 * @param midIdx	The middle index
	 * @param endIdx	The last index
	 * @param isNames	Whether or not you are sorting by name
	 */
	private void merge(List<City> arrToSort, int startIdx, int midIdx, int endIdx, boolean isNames)
	{
		List<City> leftArr = new ArrayList<City>(); 
		List<City> rightArr = new ArrayList<City>();
			
		//Initializing the left and right arrays
		for(int i=0; i<midIdx - startIdx + 1; i++)
			leftArr.add(arrToSort.get(startIdx + i));
			
		for(int i=0; i<endIdx - midIdx; i++)
			rightArr.add(arrToSort.get(midIdx + i + 1));
			
		//merging the left and right arrays into a single sorted array
		int leftArrIdx = 0, rightArrIdx = 0, sortedArrIdx = startIdx;
		while((leftArrIdx < leftArr.size()) && (rightArrIdx < rightArr.size()))
		{	
			if (isNames){
				if(leftArr.get(leftArrIdx).compareNames(rightArr.get(rightArrIdx)) > 0)
				{
					arrToSort.set(sortedArrIdx, leftArr.get(leftArrIdx));
					leftArrIdx += 1;
				}
				else
				{
					arrToSort.set(sortedArrIdx, rightArr.get(rightArrIdx));
					rightArrIdx += 1;
				}
				sortedArrIdx += 1;
			} else {
				if(leftArr.get(leftArrIdx).compareTo(rightArr.get(rightArrIdx)) > 0)
				{
					arrToSort.set(sortedArrIdx, leftArr.get(leftArrIdx));
					leftArrIdx += 1;
				}
				else
				{
					arrToSort.set(sortedArrIdx, rightArr.get(rightArrIdx));
					rightArrIdx += 1;
				}
				sortedArrIdx += 1;
			}
			
		}
			
		//Adding the rest of the elements of left array if present
		while(leftArrIdx < leftArr.size())
		{
			arrToSort.set(sortedArrIdx, leftArr.get(leftArrIdx));
			leftArrIdx += 1;
			sortedArrIdx += 1;
		}
			
		//Adding the rest of the elements of right array if present
		while(rightArrIdx < rightArr.size())
		{
			arrToSort.set(sortedArrIdx, rightArr.get(rightArrIdx));
			rightArrIdx += 1;
			sortedArrIdx += 1;
		}
	}
	
	/**
	 * This will sort cities by population in ascending order using selection sort
	 */
	public void leastPopCities(){

		//performing sort
		int indexToSwitch = 0;
		int loopCounter = 0;
		
		long startMillisec = System.currentTimeMillis();
		for (int j = 0; j < cities.size(); j++) {
			for (int i = 1; i < cities.size() - loopCounter; i++) {
				if (cities.get(indexToSwitch).compareTo(cities.get(i)) < 0)
					indexToSwitch = i;
			}
			
			swap(cities, indexToSwitch, cities.size() - loopCounter - 1);
			indexToSwitch = 0;
			
			loopCounter++;
		}		
		long endMillisec = System.currentTimeMillis();

		//printing output
		System.out.println("\nFifty least populous cities");
		System.out.printf("%4s %-22s %-22s %-12s %12s\n", "", "State", "City", "Type", "Population");
		for (int i = 0; i < 50; i++){
			System.out.printf("%4s %s\n", (i+1 + ":"), cities.get(i));
		}
		System.out.println("\n");
		System.out.println("Elapsed Time: " + (endMillisec - startMillisec) + " milliseconds\n\n");

	}

	/**
	 * This will sort cities by population in descending order using merge sort
	 */
	public void mostPopCities(){
		
		//performing sort
		long startMillisec = System.currentTimeMillis();
		mergeSort(cities, 0, cities.size()-1, false);
		long endMillisec = System.currentTimeMillis();

		//printing output
		System.out.println("\nFifty most populous cities");
		System.out.printf("%4s %-22s %-22s %-12s %12s\n", "", "State", "City", "Type", "Population");
		for (int i = 0; i < 50; i++){
			System.out.printf("%4s %s\n", (i+1 + ":"), cities.get(i));
		}
		System.out.println("\n");
		System.out.println("Elapsed Time: " + (endMillisec - startMillisec) + " milliseconds\n\n");
	}

	/**
	 * This will sort cities by name in ascending order using insertion sort
	 */
	public void first50Cities(){

		//performing sort
		long startMillisec = System.currentTimeMillis();
		for (int i = 1; i < cities.size(); i++) {
			if (cities.get(i).compareNames(cities.get(i-1)) < 0) {
				int temp = i;
				while (temp > 0 && cities.get(temp).compareNames(cities.get(temp-1)) < 0){
					swap(cities, temp-1, temp);
					temp--;
				}
			}
		}
		long endMillisec = System.currentTimeMillis();

		//printing output
		System.out.println("\nFifty cities sorted by name");
		System.out.printf("%4s %-22s %-22s %-12s %12s\n", "", "State", "City", "Type", "Population");
		for (int i = 0; i < 50; i++){
			System.out.printf("%4s %s\n", (i+1 + ":"), cities.get(i));
		}
		System.out.println("\n");
		System.out.println("Elapsed Time: " + (endMillisec - startMillisec) + " milliseconds\n\n");

	}

	/**
	 * This will sort the last 50 cities by name using merge sort
	 */
	public void last50Cities(){

		//performing sort
		long startMillisec = System.currentTimeMillis();
		mergeSort(cities, 0, cities.size()-1, true);
		long endMillisec = System.currentTimeMillis();

		//printing output
		System.out.println("\nFifty cities sorted by names descending");
		System.out.printf("%4s %-22s %-22s %-12s %12s\n", "", "State", "City", "Type", "Population");
		for (int i = 0; i < 50; i++){
			System.out.printf("%4s %s\n", (i+1 + ":"), cities.get(i));
		}
		System.out.println("\n");
		System.out.println("Elapsed Time: " + (endMillisec - startMillisec) + " milliseconds\n\n");
	}

	/**
	 * This will sort cities by most populous cities in a given state using merge sort
	 */
	public void mostPopInState(){
		List<City> citiesInState = new ArrayList<City>(); // cities in a given state
		boolean gotten = true;
		String state;

		//getting all the cities in a state
		do{
			state = Prompt.getString("Enter state name (ie. Alabama)");
			for (int i = 0; i < cities.size(); i++){
				if (state.equalsIgnoreCase(cities.get(i).getState())){
					citiesInState.add(cities.get(i));
					gotten = true;
				}
			}
			if (!gotten){
				System.out.println("ERROR: " + state + " is not valid");
			}
		} while (!gotten);

		//performing the sort
		long startMillisec = System.currentTimeMillis();
		mergeSort(citiesInState, 0, citiesInState.size()-1, false);
		long endMillisec = System.currentTimeMillis();

		//printing output, if output has less than 50 then only print as many as there are
		System.out.println("\nFifty most populous cities in " + state);
		System.out.printf("%4s %-22s %-22s %-12s %12s\n", "", "State", "City", "Type", "Population");
		if(citiesInState.size() >= 50){
			for (int i = 0; i < 50; i++){
				System.out.printf("%4s %s\n", (i+1 + ":"), citiesInState.get(i));
			}
		}else {
			for (int i = 0; i < citiesInState.size(); i++){
				System.out.printf("%4s %s\n", (i+1 + ":"), citiesInState.get(i));
			}
		}
		System.out.println("\n");
		System.out.println("Elapsed Time: " + (endMillisec - startMillisec) + " milliseconds\n\n");
	}

	/**
	 * This will sort all the cities of the same name by population using merge sort
	 */
	public void sameNameCities(){
		List<City> sameName = new ArrayList<City>(); // array of cities with same names
		boolean gotten = true;
		String name;

		//going through the entire list and finding the city with same name
		do{
			name = Prompt.getString("Enter city name");
			for (int i = 0; i < cities.size(); i++){
				if (name.equalsIgnoreCase(cities.get(i).getName())){
					sameName.add(cities.get(i));
					gotten = true;
				}
			}
			if (!gotten){
				System.out.println("ERROR: " + name + " is not valid");
			}
		} while (!gotten);

		//sorting sameName
		long startMillisec = System.currentTimeMillis();
		mergeSort(sameName, 0, sameName.size() - 1, false);
		long endMillisec = System.currentTimeMillis();

		//printing output
		System.out.println("\nCity " + name + " sorted by population");
		System.out.printf("%4s %-22s %-22s %-12s %12s\n", "", "State", "City", "Type", "Population");
		for (int i = 0; i < sameName.size(); i++){
			System.out.printf("%4s %s\n", (i+1 + ":"), sameName.get(i));
		}
		System.out.println("\n");
		System.out.println("Elapsed Time: " + (endMillisec - startMillisec) + " milliseconds\n\n");
	}
}
