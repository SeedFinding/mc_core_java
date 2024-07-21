package com.seedfinding.mccore;

import com.seedfinding.mccore.util.data.SpiralIterator;
import com.seedfinding.mccore.util.pos.BPos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

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
}
