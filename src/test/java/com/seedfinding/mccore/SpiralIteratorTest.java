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
	@DisplayName("smallRange")
	public void testSmallRange() {
		SpiralIterator<BPos> spiral = new SpiralIterator<>(new BPos(0, 0, 0), new BPos(1, 0, 1), BPos::new);
		Iterator<BPos> iterator = spiral.iterator();
		assertEquals(iterator.next(), new BPos(0, 0, 0));
		assertEquals(iterator.next(), new BPos(0, 0, 1));
		assertEquals(iterator.next(), new BPos(1, 0, 1));
		assertEquals(iterator.next(), new BPos(1, 0, 0));
		assertEquals(iterator.next(), new BPos(1, 0, -1));
		assertEquals(iterator.next(), new BPos(0, 0, -1));
		assertEquals(iterator.next(), new BPos(-1, 0, -1));
		assertEquals(iterator.next(), new BPos(-1, 0, 0));
		assertEquals(iterator.next(), new BPos(-1, 0, 1));
		assertFalse(iterator.hasNext());
	}
}
