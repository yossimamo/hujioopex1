package oop.ex4.crosswords;

import java.util.HashSet;
import java.util.Iterator;

public class SmallGridStrategy implements CrosswordStrategy {
	
	private CrosswordVacantEntries _shape;
	private CrosswordTerms _dict;
	private CrosswordOverlapManager _overlapManager;
	
	public SmallGridStrategy(CrosswordVacantEntries shape,
							 CrosswordTerms dict,
							 CrosswordOverlapManager overlapManager) {
		_shape = shape;
		_dict = dict;
		_overlapManager = overlapManager;
		
	}

	public int getUpperBoundQuality(int currentQuality) {
		// long startTime = System.currentTimeMillis();
		HashSet<MyCrosswordPosition> positionHashset = new HashSet<MyCrosswordPosition>();
		int sum = 0;
		int minTermLength = _dict.getMinAvailableTermLength();
		// No point in iterating on vacant entries that are shorter than the shortest term, since
		// no term can fit into them
		Iterator<CrosswordVacantEntry> vacantEntriesIterator = _shape.getIterator(minTermLength, _shape.getMaxVacantEntryLength());
		while (vacantEntriesIterator.hasNext()) {
			CrosswordVacantEntry nextEntry =  vacantEntriesIterator.next();
			// If we've already found a term which fits into the prefix
			// of this entry, we can sum this one up too.
			if (positionHashset.contains(nextEntry.getPosition())) {
				sum += nextEntry.getMaxCapacity();
				continue;
			}
			// Iterate only on terms that are exactly as long as the vacant entry,
			// since shorter terms were already iterated on for the prefixes of the
			// current entry
			Iterator<String> termsIterator = _dict.getIterator(nextEntry.getMaxCapacity(), nextEntry.getMaxCapacity());
			while (termsIterator.hasNext()) {
				if (_overlapManager.isMatch(termsIterator.next(), nextEntry)) {
					positionHashset.add((MyCrosswordPosition)nextEntry.getPosition());
					sum += nextEntry.getMaxCapacity();
					break;
				}
			}
		}
		// System.out.printf("getSumOfAvailableEntries took: %d ms\n", System.currentTimeMillis() - startTime);
		return currentQuality + sum;
	}

	public Iterator<CrosswordEntry> getIterator() {
		return new SmallGridStrategyIterator(_shape, _dict, _overlapManager);
	}

}
