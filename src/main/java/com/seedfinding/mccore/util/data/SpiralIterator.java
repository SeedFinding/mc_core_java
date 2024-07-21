package com.seedfinding.mccore.util.data;

import com.seedfinding.mccore.util.math.Vec3i;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;

@SuppressWarnings("unused")
public class SpiralIterator<T extends Vec3i> implements Iterable<T> {

	private final T center;
	private final T lowerBound;
	private final T upperBound;
	private final int step;
	private final Builder<T> builder;

	public SpiralIterator(T center, T lowerBound, T upperBound, int step, Builder<T> builder) {
		this.center = center;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.step = step;
		this.builder = builder;
	}

	public SpiralIterator(T center, T radius, int step, Builder<T> builder) {
		this(center, builder.build(center.getX() - radius.getX(), 0, center.getZ() - radius.getZ()),
			builder.build(center.getX() + radius.getX(), 0, center.getZ() + radius.getZ()), step, builder);
	}

	public SpiralIterator(T center, T radius, Builder<T> builder) {
		this(center, radius, 1, builder);
	}

	@Override
	public @NotNull Iterator<T> iterator() {
		return new Iterator<T>() {
			private int x = center.getX();
			private int z = center.getZ();

			private float n = 1;
			private int i = 0;
			private int j = 0;

			@Override
			public boolean hasNext() {
				return x >= lowerBound.getX() && x <= upperBound.getX() && z >= lowerBound.getZ() && z <= upperBound.getZ();
			}

			@Override
			public T next() {
				try {
					return builder.build(x, 0, z);
				} finally {
					int floorN = (int) Math.floor(n);
					if (j < floorN) {
						switch (i % 4) {
							case 0: z += step; break;
							case 1: x += step; break;
							case 2: z -= step; break;
							case 3: x -= step; break;
						}
						j++;
					} else {
						j = 0;
						n += 0.5f;
						i++;
						return next();
					}
				}
			}
		};
	}

	@Override
	public Spliterator<T> spliterator() {
		return Spliterators.spliteratorUnknownSize(this.iterator(), Spliterator.ORDERED);
	}

	@FunctionalInterface
	public interface Builder<T extends Vec3i> {
		T build(int x, int y, int z);
	}
}
