import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class ClientEventTest {
	final ClientEventBuilder builder = new BuilderImpl();
	
	public Client c = new Client("Jane", "Doe", Optional.of("John"), "555-555-5555", builder);
	public LocalDate expectedEventDate = LocalDate.of(2018, 12, 25);
	public BigDecimal expectedBudgetAmount = new BigDecimal("2000.00");
	public Optional<Integer> expectedGuestCount = Optional.empty();
	public int expectedTableCount = 35;
	public Optional<String> expectedEventTheme = Optional.empty();
	public Optional<String> expectedColorPalette = Optional.empty();
	
	ClientEvent event = c.createEvent(expectedEventDate, expectedBudgetAmount, expectedGuestCount, 
									expectedTableCount, expectedEventTheme, expectedColorPalette);
	

	@Test
		public void testEventDate() {
		LocalDate actual = event.getEventDate();
		assertEquals(expectedEventDate, actual);
	}
	
	@Test
	
	public void testEventBudget() {
		BigDecimal actual = event.getBudgetAmount();
		assertEquals(expectedBudgetAmount, actual);
	}
	
	@Test 
	public void testEmptyGuestCount() {
		Optional<Integer> actual = event.getGuestCount();
		assertEquals(expectedGuestCount, actual);
	}
	
	@Test
	public void testEnteredGuestCount() {
		Optional<Integer> expectedEnteredGuestCount = Optional.of(190);
		event.setGuestCount(expectedEnteredGuestCount);
		Optional<Integer> actual = event.getGuestCount();
		assertEquals(expectedEnteredGuestCount, actual);
	}
	
	@Test 
	public void testEmptyTableCount() {
		Optional<Integer> actual = event.getGuestCount();
		assertEquals(expectedGuestCount, actual);
	}
	
	@Test 
	public void testEnteredTableCount() {
		int expectedEnteredTableCount = 19;
		event.setTableCount(expectedEnteredTableCount);
		int actual = event.getTableCount();
		assertEquals(expectedEnteredTableCount, actual);
	}
	
	@Test
	public void testEmptyEventTheme() {
		Optional<String> actual = event.getEventTheme();
		assertEquals(expectedEventTheme, actual);
	}
	
	@Test
	public void testEnteredEventTheme() {
		Optional<String> expectedEnteredEventTheme = Optional.of("wedding");
		event.setEventTheme(expectedEnteredEventTheme);
		Optional<String> actual = event.getEventTheme();
		assertEquals(expectedEnteredEventTheme, actual);
	}
	
	@Test
	public void testEmptyColorPalette() {
		Optional<String> actual = event.getColorPalette();
		assertEquals(expectedColorPalette, actual);
	}
	
	@Test
	public void testEnteredColorPalette() {
		Optional<String> expectedEnteredColorPalette = Optional.of("white and maroon");
		event.setColorPalette(expectedEnteredColorPalette);
		Optional<String> actual = event.getColorPalette();
		assertEquals(expectedEnteredColorPalette, actual);
	}
	
	@Test
	public void testPlaceArrangements0() { //only one type of arrangement, no talls
		HashMap<Object, Integer> expected = new HashMap<Object, Integer>();
		expected.put(VotiveArrangement.getVotiveArrangement(), 35);
		
		HashMap<Object, Integer> determinedArrangements = new HashMap<Object, Integer>();
		determinedArrangements.put(VotiveArrangement.getVotiveArrangement(), 35);
		
		HashMap<Object, Integer> actual = event.placeArrangements(event.getTableCount(), 
				determinedArrangements.size(), determinedArrangements);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPlaceArrangements1() { //two types of arrangements, no talls
		HashMap<Object, Integer> expected = new HashMap<Object, Integer>();
		expected.put(VotiveArrangement.getVotiveArrangement(), 17);
		expected.put(SmallFloralRingArrangement.getSmallFloralRingArrangement(), 17);
		
		HashMap<Object, Integer> determinedArrangements = new HashMap<Object, Integer>();
		determinedArrangements.put(VotiveArrangement.getVotiveArrangement(), 0);
		determinedArrangements.put(SmallFloralRingArrangement.getSmallFloralRingArrangement(), 0);
		
		HashMap<Object, Integer> actual = event.placeArrangements(event.getTableCount(), 
				determinedArrangements.size(), determinedArrangements);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPlaceArrangements2() { //three types of arrangements, no talls
		HashMap<Object, Integer> expected = new HashMap<Object, Integer>();
		expected.put(VotiveArrangement.getVotiveArrangement(), 11);
		expected.put(SmallFloralRingArrangement.getSmallFloralRingArrangement(), 11);
		expected.put(LargeFloralRingArrangement.getLargeFloralRingArrangement(), 11);
		
		HashMap<Object, Integer> determinedArrangements = new HashMap<Object, Integer>();
		determinedArrangements.put(VotiveArrangement.getVotiveArrangement(), 0);
		determinedArrangements.put(SmallFloralRingArrangement.getSmallFloralRingArrangement(), 0);
		determinedArrangements.put(LargeFloralRingArrangement.getLargeFloralRingArrangement(), 0);
		
		HashMap<Object, Integer> actual = event.placeArrangements(event.getTableCount(), 
				determinedArrangements.size(), determinedArrangements);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPlaceArrangements3() { //four types of arrangements, no talls
		HashMap<Object, Integer> expected = new HashMap<Object, Integer>();
		expected.put(VotiveArrangement.getVotiveArrangement(), 8);
		expected.put(SmallFloralRingArrangement.getSmallFloralRingArrangement(), 8);
		expected.put(LargeFloralRingArrangement.getLargeFloralRingArrangement(), 8);
		expected.put(LowFloralArrangement.getLowFloralArrangement(), 8);
		
		HashMap<Object, Integer> determinedArrangements = new HashMap<Object, Integer>();
		determinedArrangements.put(VotiveArrangement.getVotiveArrangement(), 0);
		determinedArrangements.put(SmallFloralRingArrangement.getSmallFloralRingArrangement(), 0);
		determinedArrangements.put(LargeFloralRingArrangement.getLargeFloralRingArrangement(), 0);
		determinedArrangements.put(LowFloralArrangement.getLowFloralArrangement(), 0);
		
		HashMap<Object, Integer> actual = event.placeArrangements(event.getTableCount(), 
				determinedArrangements.size(), determinedArrangements);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPlaceArrangements4() { //all types
		HashMap<Object, Integer> expected = new HashMap<Object, Integer>();
		expected.put(VotiveArrangement.getVotiveArrangement(), 6);
		expected.put(SmallFloralRingArrangement.getSmallFloralRingArrangement(), 6);
		expected.put(LargeFloralRingArrangement.getLargeFloralRingArrangement(), 6);
		expected.put(LowFloralArrangement.getLowFloralArrangement(), 6);
		expected.put(TallAnchorArrangement.getTallAnchorArrangement(), 9);
		
		HashMap<Object, Integer> determinedArrangements = new HashMap<Object, Integer>(); //designEvent does this
		determinedArrangements.put(VotiveArrangement.getVotiveArrangement(), 0);
		determinedArrangements.put(SmallFloralRingArrangement.getSmallFloralRingArrangement(), 0);
		determinedArrangements.put(LargeFloralRingArrangement.getLargeFloralRingArrangement(), 0);
		determinedArrangements.put(LowFloralArrangement.getLowFloralArrangement(), 0);
		determinedArrangements.put(TallAnchorArrangement.getTallAnchorArrangement(), 0);
		
		HashMap<Object, Integer> actual = event.placeArrangements(event.getTableCount(), 
				determinedArrangements.size(), determinedArrangements);
		
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPlaceRemainingArrangements0() { //no tall arrangements, tests sum of arrangements in the HashMap are the same as the number of tables
		HashMap<Object, Integer> expectedArrangements = new HashMap<Object, Integer>();
		expectedArrangements.put(VotiveArrangement.getVotiveArrangement(), 12);
		expectedArrangements.put(SmallFloralRingArrangement.getSmallFloralRingArrangement(), 12);
		expectedArrangements.put(LargeFloralRingArrangement.getLargeFloralRingArrangement(), 11);
		
		int expected = 35;
		
		
		HashMap<Object, Integer> determinedArrangements = new HashMap<Object, Integer>();
		determinedArrangements.put(VotiveArrangement.getVotiveArrangement(), 0);
		determinedArrangements.put(SmallFloralRingArrangement.getSmallFloralRingArrangement(), 0);
		determinedArrangements.put(LargeFloralRingArrangement.getLargeFloralRingArrangement(), 0);
		determinedArrangements.put(LowFloralArrangement.getLowFloralArrangement(), 0);
		
		event.placeArrangements(event.getTableCount(), 4, determinedArrangements);
		
		HashMap<Object, Integer> actualArrangements = event.placeRemainingArrangements(event.getTableCount(), determinedArrangements);
		int actual = actualArrangements.values().stream().mapToInt(Integer::intValue).sum();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPlaceRemainingArrangement1() { //4 arrangements included tall ones, tests sum of arrangements in the HashMap are the same as the number of tables & the number of Tall Arrangements doesn't change
		HashMap<Object, Integer> expectedArrangements = new HashMap<Object, Integer>();
		expectedArrangements.put(VotiveArrangement.getVotiveArrangement(), 9);
		expectedArrangements.put(SmallFloralRingArrangement.getSmallFloralRingArrangement(), 9);
		expectedArrangements.put(LargeFloralRingArrangement.getLargeFloralRingArrangement(), 8);
		expectedArrangements.put(TallAnchorArrangement.getTallAnchorArrangement(), 9);
		
		int expected = 35;
		
		HashMap<Object, Integer> determinedArrangements = new HashMap<Object, Integer>();
		determinedArrangements.put(VotiveArrangement.getVotiveArrangement(), 0);
		determinedArrangements.put(SmallFloralRingArrangement.getSmallFloralRingArrangement(), 0);
		determinedArrangements.put(LargeFloralRingArrangement.getLargeFloralRingArrangement(), 0);
		determinedArrangements.put(TallAnchorArrangement.getTallAnchorArrangement(), 0);
		
		event.placeArrangements(event.getTableCount(), 4, determinedArrangements);
		
		HashMap<Object, Integer> actualArrangements = event.placeRemainingArrangements(event.getTableCount(), determinedArrangements);
		int actual = actualArrangements.values().stream().mapToInt(Integer::intValue).sum();
		
		assertEquals(expected, actual); //test sums
		assertEquals(expectedArrangements.get(TallAnchorArrangement.getTallAnchorArrangement()), actualArrangements.get(TallAnchorArrangement.getTallAnchorArrangement()));
	}
	
@Test
public void testDesignEvent() {
	
	HashMap<Object, Integer> actual = event.designEvent(VotiveArrangement.getVotiveArrangement(), SmallFloralRingArrangement.getSmallFloralRingArrangement(), 
						LargeFloralRingArrangement.getLargeFloralRingArrangement(), 
						TallAnchorArrangement.getTallAnchorArrangement());
	
	assertTrue(actual.containsKey(VotiveArrangement.getVotiveArrangement()));
	assertTrue(actual.containsKey(SmallFloralRingArrangement.getSmallFloralRingArrangement()));
	assertTrue(actual.containsKey(LargeFloralRingArrangement.getLargeFloralRingArrangement()));
	assertTrue(actual.containsKey(TallAnchorArrangement.getTallAnchorArrangement()));

}
	
}
