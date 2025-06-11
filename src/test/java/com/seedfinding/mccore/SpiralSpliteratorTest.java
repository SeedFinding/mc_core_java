package com.seedfinding.mccore;

import com.seedfinding.mccore.util.data.SpiralSpliterator;
import com.seedfinding.mccore.util.pos.BPos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

public class SpiralSpliteratorTest {
	@Test
	@DisplayName("range")
	public void testRange() {
		SpiralSpliterator<BPos> spiral = new SpiralSpliterator<>(new BPos(0, 0, 0), 2, BPos::new);
		List<BPos> coords = new ArrayList<>();
		spiral.forEachRemaining(coords::add);
		Iterator<BPos> iterator = coords.iterator();

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
	@DisplayName("spliteratorSize")
	public void testSpliteratorSize() {
		SpiralSpliterator<BPos> spiral = new SpiralSpliterator<>(new BPos(50, 0, -100), 2, BPos::new);
		assertEquals(25, spiral.getExactSizeIfKnown());
		assertEquals(25, StreamSupport.stream(spiral, false).count());
	}

	@Test
	@DisplayName("spliteratorSizeWithStep")
	public void testSpliteratorSizeWithStep() {
		SpiralSpliterator<BPos> spiral = new SpiralSpliterator<>(new BPos(50, 0, -100), 6, 3, BPos::new);
		assertEquals(25, spiral.getExactSizeIfKnown());
		assertEquals(25, StreamSupport.stream(spiral, false).count());
	}
}
