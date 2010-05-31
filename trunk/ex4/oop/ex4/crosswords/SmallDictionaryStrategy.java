package oop.ex4.crosswords;

import java.util.Iterator;

public class SmallDictionaryStrategy implements CrosswordStrategy {
	
	private CrosswordVacantEntries _shape;
	private CrosswordTerms _dict;
	private CrosswordOverlapManager _overlapManager;
	
	public SmallDictionaryStrategy(CrosswordVacantEntries shape,
								   CrosswordTerms dict,
								   CrosswordOverlapManager overlapManager) {
		_shape = shape;
		_dict = dict;
		_overlapManager = overlapManager;
	}
	
	public int getUpperBoundQuality(int currentQuality) {
		//long startTime = System.currentTimeMillis();
		int sum = 0;
		// No point in iterating on terms that are longer than the longest vacant
		// entry, since they cannot fit into any vacant entry
		Iterator<String> termsIterator = _dict.getIterator(Math.min(_dict.getMaxAvailableTermLength(), _shape.getMaxVacantEntryLength()), _dict.getMinAvailableTermLength());
		while (termsIterator.hasNext()) {
			String nextTerm =  termsIterator.next();
			// Iterate on vacant entries that are at least as long as the term
			Iterator<CrosswordVacantEntry> vacantEntriesIterator = _shape.getIterator(nextTerm.length(), _shape.getMaxVacantEntryLength());
			while (vacantEntriesIterator.hasNext()) {
				CrosswordVacantEntry vacantEntry = vacantEntriesIterator.next();
				if (_overlapManager.isMatch(nextTerm, vacantEntry)) {
					sum += nextTerm.length();
					break;
				}
			}
		}
		//System.out.printf("getSumOfAvailableWords took: %d ms\n", System.currentTimeMillis() - startTime);
		return currentQuality + sum;
	}

	public Iterator<CrosswordEntry> getIterator() {
		return new SmallDictionaryStrategyIterator(_shape, _dict, _overlapManager);
	}

}
