package com.seedfinding.mccore;

import com.seedfinding.mccore.util.data.SpiralIterator;
import com.seedfinding.mccore.util.pos.BPos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SpiralIteratorTest {
	@Test
	@DisplayName("range")
	public void testRange() {
		SpiralIterator<BPos> spiral = new SpiralIterator<>(new BPos(0, 0, 0), new BPos(2, 0, 2), BPos::new);
		Iterator<BPos> iterator = spiral.iterator();

		assertEquals(new BPos(0, 0, 0), iterator.next());
		assertEquals(new BPos(0, 0, 1), iterator.next());
		assertEquals(new BPos(1, 0, 1), iterator.next());
		assertEquals(new BPos(1, 0, 0), iterator.next());
		assertEquals(new BPos(1, 0, -1), iterator.next());
		assertEquals(new BPos(0, 0, -1), iterator.next());
		assertEquals(new BPos(-1, 0, -1), iterator.next());
		assertEquals(new BPos(-1, 0, 0), iterator.next());
		assertEquals(new BPos(-1, 0, 1), iterator.next());
		assertEquals(new BPos(-1, 0, 2), iterator.next());
		assertEquals(new BPos(0, 0, 2), iterator.next());
		assertEquals(new BPos(1, 0, 2), iterator.next());
		assertEquals(new BPos(2, 0, 2), iterator.next());
		assertEquals(new BPos(2, 0, 1), iterator.next());
		assertEquals(new BPos(2, 0, 0), iterator.next());
		assertEquals(new BPos(2, 0, -1), iterator.next());
		assertEquals(new BPos(2, 0, -2), iterator.next());
		assertEquals(new BPos(1, 0, -2), iterator.next());
		assertEquals(new BPos(0, 0, -2), iterator.next());
		assertEquals(new BPos(-1, 0, -2), iterator.next());
		assertEquals(new BPos(-2, 0, -2), iterator.next());
		assertEquals(new BPos(-2, 0, -1), iterator.next());
		assertEquals(new BPos(-2, 0, 0), iterator.next());
		assertEquals(new BPos(-2, 0, 1), iterator.next());
		assertEquals(new BPos(-2, 0, 2), iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	@DisplayName("iteratorSize")
	public void testIteratorSize() {
		SpiralIterator<BPos> spiral = new SpiralIterator<>(new BPos(50, 0, -100), new BPos(2, 0, 2), BPos::new);
		Spliterator<BPos> spliterator = Spliterators.spliteratorUnknownSize(spiral.iterator(), Spliterator.ORDERED);

		assertEquals(25, StreamSupport.stream(spliterator, false).count());
	}

	@Test
	@DisplayName("spliteratorSize")
	public void testSpliteratorSize() {
		SpiralIterator<BPos> spiral = new SpiralIterator<>(new BPos(100, 0, -25), new BPos(2, 0, 2), BPos::new);
		Spliterator<BPos> spliterator = spiral.spliterator();

		assertEquals(25, StreamSupport.stream(spliterator, false).count());
	}

	@Test
	@DisplayName("iteratorSizeWithStep")
	public void testIteratorSizeWithStep() {
		SpiralIterator<BPos> spiral = new SpiralIterator<>(new BPos(50, 0, -100), new BPos(50 - 6, 0, -100 - 6), new BPos(50 + 6, 0, -100 + 6), 3, BPos::new);
		Spliterator<BPos> spliterator = Spliterators.spliteratorUnknownSize(spiral.iterator(), Spliterator.ORDERED);

		assertEquals(25, StreamSupport.stream(spliterator, false).count());
	}

	@Test
	@DisplayName("spliteratorSizeWithStep")
	public void testSpliteratorSizeWithStep() {
		SpiralIterator<BPos> spiral = new SpiralIterator<>(new BPos(100, 0, -25), new BPos(100 - 6, 0, -25 - 6), new BPos(100 + 6, 0, -25 + 6), 3, BPos::new);
		Spliterator<BPos> spliterator = spiral.spliterator();

		assertEquals(25, StreamSupport.stream(spliterator, false).count());
	}
}
