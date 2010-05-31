package oop.ex4.crosswords;

import java.util.Iterator;

public interface PartitionedDataCollection<E> {
	 public boolean isUsed(E elem);
	 public Iterator<E> getRawDataIterator(int partitionNumber);
	 public int getNumOfPartitions();
}
