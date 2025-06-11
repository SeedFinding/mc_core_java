package com.seedfinding.mccore.util.data;

import com.seedfinding.mccore.util.math.Vec3i;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * A {@link Spliterator} that loops in a spiral. The initial direction is in the positive
 * {@code z} direction. It is faster than {@link SpiralIterator} because it has less
 * per-element overhead and avoids {@link Iterator#hasNext()} calls.
 *
 * @param <T> a vector of type {@link Vec3i}
 */
@SuppressWarnings("unused")
public class SpiralSpliterator<T extends Vec3i> extends Spliterators.AbstractSpliterator<T> {

	private final int maxZ;
	private final int step;

	private int x;
	private int z;

	private int n = 2;
	private int i = 0;
	private int j = 0;

	private final Builder<T> builder;

	public SpiralSpliterator(T center, int radius, Builder<T> builder) {
		this(center, radius, 1, builder);
	}

	public SpiralSpliterator(T center, int radius, int step, Builder<T> builder) {
		this(center.getX(), center.getZ(), radius, step, builder);
	}

	public SpiralSpliterator(int centerX, int centerZ, int radius, Builder<T> builder) {
		this(centerX, centerZ, radius, 1, builder);
	}

	public SpiralSpliterator(int centerX, int centerZ, int radius, int step, Builder<T> builder) {
		super(((2L * radius) / step + 1) * ((2L * radius) / step + 1), Spliterator.DISTINCT | Spliterator.ORDERED | Spliterator.SIZED | Spliterator.NONNULL | Spliterator.IMMUTABLE);
		this.maxZ = centerZ + radius;
		this.step = step;

		this.x = centerX;
		this.z = centerZ;

		this.builder = builder;
	}

	@Override
	public boolean tryAdvance(Consumer<? super T> action) {
		if (this.z > this.maxZ) {
			return false;
		}
		T returnValue = this.builder.build(this.x, 0, this.z);
		if (this.j < (this.n >> 1)) {
			switch (i & 3) {
				case 0: this.z += this.step; break;
				case 1: this.x += this.step; break;
				case 2: this.z -= this.step; break;
				case 3: this.x -= this.step; break;
			}
			this.j++;
			action.accept(returnValue);
			return true;
		} else {
			this.j = 0;
			this.n++;
			this.i++;
			return tryAdvance(action);
		}
	}

	@FunctionalInterface
	public interface Builder<T extends Vec3i> {
		T build(int x, int y, int z);
	}
}
