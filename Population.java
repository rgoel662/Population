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
	
	public void run(){
		populateList();
		printIntroduction();
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

	public void populateList(){
		Scanner kb = FileUtils.openToRead(DATA_FILE);
		kb.useDelimiter("[\t\n]");
		while(kb.hasNext()){
			cities.add(new City(kb.next(), kb.next(), kb.next(), kb.nextInt()));
		}
	}

	private void swap(List<City> original, int x, int y) {
		City temp = original.get(x);
		original.set(x, original.get(y));
		original.set(y, temp);
	}
	
	public void sortGivenArray(List<City> inputArray){       
        divide(0, inputArray.size()-1);
    }
    
    public void divide(int startIndex,int endIndex){
        
        //Divide till you breakdown your list to single element
        if(startIndex<endIndex && (endIndex-startIndex)>=1){
            int mid = (endIndex + startIndex)/2;
            divide(startIndex, mid);
            divide(mid+1, endIndex);        
            
            //merging Sorted array produce above into one sorted array
            merger(startIndex,mid,endIndex);            
        }       
    }   
    
    public void merger(int startIndex,int midIndex,int endIndex){
        
        //Below is the mergedarray that will be sorted array Array[i-midIndex] , Array[(midIndex+1)-endIndex]
        ArrayList<Integer> mergedSortedArray = new ArrayList<Integer>();
        
        int leftIndex = startIndex;
        int rightIndex = midIndex+1;
        
        while(leftIndex<=midIndex && rightIndex<=endIndex){
            if(inputArray.get(leftIndex)<=inputArray.get(rightIndex)){
                mergedSortedArray.add(inputArray.get(leftIndex));
                leftIndex++;
            }else{
                mergedSortedArray.add(inputArray.get(rightIndex));
                rightIndex++;
            }
        }       
        
        //Either of below while loop will execute
        while(leftIndex<=midIndex){
            mergedSortedArray.add(inputArray.get(leftIndex));
            leftIndex++;
        }
        
        while(rightIndex<=endIndex){
            mergedSortedArray.add(inputArray.get(rightIndex));
            rightIndex++;
        }
        
        int i = 0;
        int j = startIndex;
        //Setting sorted array to original one
        while(i<mergedSortedArray.size()){
            inputArray.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }

	public void leastPopCities(){
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

		System.out.println("\nFifty least populous cities");
		System.out.printf("%4s %-22s %-22s %-12s %12s\n", "", "State", "City", "Type", "Population");
		for (int i = 0; i < 50; i++){
			System.out.printf("%4s %s\n", (i+1 + ":"), cities.get(i));
		}
		System.out.println("\n");
		System.out.println("Elapsed Time: " + (endMillisec - startMillisec) + " milliseconds\n\n");

	}

	public void mostPopCities(){

	}

	public void first50Cities(){

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

		System.out.println("\nFifty cities sorted by name");
		System.out.printf("%4s %-22s %-22s %-12s %12s\n", "", "State", "City", "Type", "Population");
		for (int i = 0; i < 50; i++){
			System.out.printf("%4s %s\n", (i+1 + ":"), cities.get(i));
		}
		System.out.println("\n");
		System.out.println("Elapsed Time: " + (endMillisec - startMillisec) + " milliseconds\n\n");

	}

	public void last50Cities(){

	}

	public void mostPopInState(){
		List<City> citiesInState = new ArrayList<City>();
		boolean gotten = true;
		String state;
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

		int indexToSwitch = 0;
		int loopCounter = 0;
		
		long startMillisec = System.currentTimeMillis();
		for (int j = 0; j < citiesInState.size(); j++) {
			for (int i = 1; i < citiesInState.size() - loopCounter; i++) {
				if (citiesInState.get(indexToSwitch).compareTo(citiesInState.get(i)) > 0)
					indexToSwitch = i;
			}
			
			swap(citiesInState, indexToSwitch, citiesInState.size() - loopCounter - 1);
			indexToSwitch = 0;
			
			loopCounter++;
		}		
		long endMillisec = System.currentTimeMillis();

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

	public void sameNameCities(){
		List<City> sameName = new ArrayList<City>();
		boolean gotten = true;
		String name;
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

		int indexToSwitch = 0;
		int loopCounter = 0;
		
		long startMillisec = System.currentTimeMillis();
		for (int j = 0; j < sameName.size(); j++) {
			for (int i = 1; i < sameName.size() - loopCounter; i++) {
				if (sameName.get(indexToSwitch).compareTo(sameName.get(i)) > 0)
					indexToSwitch = i;
			}
			swap(sameName, indexToSwitch, sameName.size() - loopCounter - 1);
			indexToSwitch = 0;
			
			loopCounter++;
		}		
		long endMillisec = System.currentTimeMillis();

		System.out.println("\nCity " + name + " sorted by population");
		System.out.printf("%4s %-22s %-22s %-12s %12s\n", "", "State", "City", "Type", "Population");
		for (int i = 0; i < sameName.size(); i++){
			System.out.printf("%4s %s\n", (i+1 + ":"), sameName.get(i));
		}
		System.out.println("\n");
		System.out.println("Elapsed Time: " + (endMillisec - startMillisec) + " milliseconds\n\n");
	}
}
