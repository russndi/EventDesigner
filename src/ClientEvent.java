import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
//T.L
public class ClientEvent {

	//potential refactor using a multimap

	private LocalDate eventDate; //required
	private BigDecimal budgetAmount; //required
	private int tableCount = 0;

	private Optional<Integer> guestCount = Optional.empty(); 
	private Optional<String> eventTheme = Optional.empty(); //optional eventually will dropped in favor of implementing inherited types of events
	private Optional<String> colorPalette = Optional.empty(); //optional

	private int numTallAnchorArrangements = 0;
	private int numLowFloralArrangements = 0;
	private int numLargeFloralRingArrangements = 0;
	private int numSmallFloralRingArrangements = 0;
	private int numVotiveArrangements = 0;
	private BigDecimal estimatedEventCost;

	


	public ClientEvent() {
		estimatedEventCost = new BigDecimal("0.00");
	}



	public LocalDate getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

	public BigDecimal getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(BigDecimal budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	public Optional<Integer> getGuestCount() {
		return this.guestCount;
	}

	public void setGuestCount(Optional<Integer> guestCount) {
		this.guestCount = guestCount;
	}

	public int getTableCount() {
		return this.tableCount;
	}

	public void setTableCount(int tableCount) {
		this.tableCount = tableCount;
	}

	public Optional<String> getEventTheme() {
		return this.eventTheme;
	}

	public void setEventTheme(Optional<String> eventTheme) {
		this.eventTheme = eventTheme;
	}

	public Optional<String> getColorPalette() {
		return this.colorPalette;
	}

	public void setColorPalette(Optional<String> colorPalette) {
		this.colorPalette = colorPalette;
	}
	
	public BigDecimal getEstimatedEventCost() {
		return estimatedEventCost;
	}
	
	private void setEstimatedEventCost(BigDecimal estimatedEventCost) {
		this.estimatedEventCost = estimatedEventCost;
	}



	public HashMap<Object, Integer> designEvent(Object...arrangements) { //
		int totalNumArrangements = arrangements.length;
		
		HashMap<Object, Integer> determinedArrangements = new HashMap<Object, Integer>();
		Arrays.stream(arrangements).forEach(arrangement -> determinedArrangements.put(arrangement, 0));
		
		if (arrangements.length == 0) {
			//eventually throw a message asking client to select an arrangement type. 
		}

		placeArrangements(this.getTableCount(), totalNumArrangements, determinedArrangements);				
		placeRemainingArrangements(this.getTableCount(), determinedArrangements);
		calculateEstimatedCostArrangements(determinedArrangements);
		
		
		return determinedArrangements;
		
	}


	public HashMap<Object, Integer> placeArrangements(int tableCount, int totalNumArrangements, //evenly distributes arrangements to each type, visibility changed for testing
			HashMap<Object, Integer> determinedArrangements) {

		if (determinedArrangements.containsKey(TallAnchorArrangement.getTallAnchorArrangement()) && determinedArrangements.get(TallAnchorArrangement.getTallAnchorArrangement()) == 0) {
			numTallAnchorArrangements = TallAnchorArrangement.getTallAnchorArrangement().determineNumTallArrangements(tableCount, totalNumArrangements);
			totalNumArrangements--;
			tableCount = tableCount - numTallAnchorArrangements;
			determinedArrangements.put(TallAnchorArrangement.getTallAnchorArrangement(), numTallAnchorArrangements);
		}

		int amountForEachType = tableCount / totalNumArrangements;
		
		determinedArrangements.forEach((key, value) -> {
			if (value.intValue() < 1) {
				determinedArrangements.put(key, amountForEachType);
			}
		});
		
		return determinedArrangements;
	}

	
	public HashMap<Object, Integer> placeRemainingArrangements(int tableCount, HashMap<Object, Integer> determinedArrangements) { //visibility for testing, because HashMap accesses out of order it unsure which arrangement will get incrementing consider LinkedHashMap
		int tablesWithArrangements = determinedArrangements.values().stream().mapToInt(Integer::intValue).sum();
		int numTablesThatNeedArrangements = tableCount - determinedArrangements.values().stream().mapToInt(Integer::intValue).sum();
		int index = 0;
		
		HashMap<Object, Integer> determinedRemainingArrangements = new HashMap<Object, Integer>(determinedArrangements);
		
		Iterator<Entry<Object, Integer>> iterator = determinedRemainingArrangements.entrySet().iterator();
		
		while (iterator.hasNext() && index != numTablesThatNeedArrangements ) {
			Entry<Object, Integer> arrangement = iterator.next();
			
			if(arrangement.getKey().equals(TallAnchorArrangement.getTallAnchorArrangement())) {
				iterator.remove();
			}
			
			else {
				determinedArrangements.put(arrangement.getKey(), arrangement.getValue() + 1);
				index++;
				iterator.remove();
			}
		}
		
		return determinedArrangements;
	}

	public void calculateEstimatedCostArrangements (HashMap<Object, Integer> determinedArrangements) {
		BigDecimal totalCost = new BigDecimal ("0.00");
		
		Iterator<Map.Entry<Object, Integer>> arrangements = determinedArrangements.entrySet().iterator();
		
		while(arrangements.hasNext()) {
			Map.Entry<Object, Integer> arrangement = arrangements.next();
			totalCost = totalCost.add(calculateIndividualCostArrangement(arrangement));
		}
		
		setEstimatedEventCost(totalCost);
	}



	private BigDecimal calculateIndividualCostArrangement(Entry<Object, Integer> arrangement) {
		
		BigDecimal individualCost = new BigDecimal ("0.00");
		
		if (arrangement.getKey().equals(TallAnchorArrangement.getTallAnchorArrangement())) {
			return individualCost.add(TallAnchorArrangement.getTallAnchorArrangement().getArrangementPrice().multiply(new BigDecimal (arrangement.getValue().intValue())));
		}
		
		if (arrangement.getKey().equals(VotiveArrangement.getVotiveArrangement())) {
			return individualCost.add(VotiveArrangement.getVotiveArrangement().getArrangementPrice().multiply(new BigDecimal (arrangement.getValue().intValue())));
		}
		
		if (arrangement.getKey().equals(SmallFloralRingArrangement.getSmallFloralRingArrangement())) {
			return individualCost.add(SmallFloralRingArrangement.getSmallFloralRingArrangement().getArrangementPrice().multiply(new BigDecimal (arrangement.getValue().intValue())));
		}
		
		if (arrangement.getKey().equals(LargeFloralRingArrangement.getLargeFloralRingArrangement())) {
			return individualCost.add(LargeFloralRingArrangement.getLargeFloralRingArrangement().getArrangementPrice().multiply(new BigDecimal (arrangement.getValue().intValue())));
		}
		
		if (arrangement.getKey().equals(LowFloralArrangement.getLowFloralArrangement())) {
			return individualCost.add(LowFloralArrangement.getLowFloralArrangement().getArrangementPrice().multiply(new BigDecimal (arrangement.getValue().intValue())));
		}
		
		return individualCost;
	}
	
}


