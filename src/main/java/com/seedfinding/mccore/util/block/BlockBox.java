package com.seedfinding.mccore.util.block;

import com.seedfinding.mccore.util.math.Vec3i;
import com.seedfinding.mccore.util.pos.BPos;

import java.util.Objects;

@SuppressWarnings("unused")
public class BlockBox {

	public int minX;
	public int minY;
	public int minZ;
	public int maxX;
	public int maxY;
	public int maxZ;

	public BlockBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	public BlockBox(int xMin, int zMin, int xMax, int zMax) {
		this.minX = xMin;
		this.minZ = zMin;
		this.maxX = xMax;
		this.maxZ = zMax;
		this.minY = 1;
		this.maxY = 512;
	}

	public BlockBox(Vec3i v1, Vec3i v2) {
		this.minX = Math.min(v1.getX(), v2.getX());
		this.minY = Math.min(v1.getY(), v2.getY());
		this.minZ = Math.min(v1.getZ(), v2.getZ());
		this.maxX = Math.max(v1.getX(), v2.getX());
		this.maxY = Math.max(v1.getY(), v2.getY());
		this.maxZ = Math.max(v1.getZ(), v2.getZ());
	}

	public static BlockBox empty() {
		return new BlockBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
	}

	public static BlockBox rotated(int x, int y, int z, int offsetX, int offsetY, int offsetZ, int sizeX, int sizeY, int sizeZ, BlockRotation rotation) {
		switch (rotation) {
			case COUNTERCLOCKWISE_90: // WEST
				return new BlockBox(x - sizeZ + 1 + offsetZ, y + offsetY, z + offsetX, x + offsetZ, y + sizeY - 1 + offsetY, z + sizeX - 1 + offsetX);
			case CLOCKWISE_90: // EAST
				return new BlockBox(x + offsetZ, y + offsetY, z + offsetX, x + sizeZ - 1 + offsetZ, y + sizeY - 1 + offsetY, z + sizeX - 1 + offsetX);
			case CLOCKWISE_180: // SOUTH
				return new BlockBox(x + offsetX, y + offsetY, z + offsetZ, x + sizeX - 1 + offsetX, y + sizeY - 1 + offsetY, z + sizeZ - 1 + offsetZ);
			case NONE: // NORTH
				return new BlockBox(x + offsetX, y + offsetY, z - sizeZ + 1 + offsetZ, x + sizeX - 1 + offsetX, y + sizeY - 1 + offsetY, z + offsetZ);

		}
		return null;
	}

	public static BlockBox getBoundingBox(BPos anchor, BlockRotation rotation, BPos pivot, BlockMirror mirror, BPos size) {
		BPos rotationSize = rotation.getSize(size);
		int pivotX = pivot.getX();
		int pivotZ = pivot.getZ();
		int sizedRotationX = rotationSize.getX() - 1;
		int sizedRotationY = rotationSize.getY() - 1;
		int sizedRotationZ = rotationSize.getZ() - 1;
		BlockBox blockBox = new BlockBox(0, 0, 0, 0, 0, 0);
		switch (rotation) {
			case COUNTERCLOCKWISE_90:
				blockBox = new BlockBox(pivotX - pivotZ, 0, pivotX + pivotZ - sizedRotationZ, pivotX - pivotZ + sizedRotationX, sizedRotationY, pivotX + pivotZ);
				break;
			case CLOCKWISE_90:
				blockBox = new BlockBox(pivotX + pivotZ - sizedRotationX, 0, pivotZ - pivotX, pivotX + pivotZ, sizedRotationY, pivotZ - pivotX + sizedRotationZ);
				break;
			case CLOCKWISE_180:
				blockBox = new BlockBox(pivotX + pivotX - sizedRotationX, 0, pivotZ + pivotZ - sizedRotationZ, pivotX + pivotX, sizedRotationY, pivotZ + pivotZ);
				break;
			case NONE:
				blockBox = new BlockBox(0, 0, 0, sizedRotationX, sizedRotationY, sizedRotationZ);
		}

		switch (mirror) {
			case LEFT_RIGHT:
				blockBox = mirrorAABB(rotation, sizedRotationZ, sizedRotationX, blockBox, BlockDirection.NORTH, BlockDirection.SOUTH);
				break;
			case FRONT_BACK:
				blockBox = mirrorAABB(rotation, sizedRotationX, sizedRotationZ, blockBox, BlockDirection.WEST, BlockDirection.EAST);
			case NONE:
		}
		return blockBox.offset(anchor.getX(), anchor.getY(), anchor.getZ());
	}

	private static BlockBox mirrorAABB(BlockRotation rotation, int x, int z, BlockBox blockBox, BlockDirection blockDirection, BlockDirection blockDirection1) {
		BPos moveAmount = BPos.ORIGIN;
		if (rotation != BlockRotation.CLOCKWISE_90 && rotation != BlockRotation.COUNTERCLOCKWISE_90) {
			if (rotation == BlockRotation.CLOCKWISE_180) {
				moveAmount = moveAmount.relative(blockDirection1, x);
			} else {
				moveAmount = moveAmount.relative(blockDirection, x);
			}
		} else {
			moveAmount = moveAmount.relative(rotation.rotate(blockDirection), z);
		}

		return blockBox.offset(moveAmount.getX(), 0, moveAmount.getZ());
	}

	public BlockBox offset(int x, int y, int z) {
		return new BlockBox(
				this.minX + x, this.minY + y, this.minZ + z,
				this.maxX + x, this.maxY + y, this.maxZ + z
		);
	}

	public void move(int x, int y, int z) {
		this.minX += x;
		this.minY += y;
		this.minZ += z;
		this.maxX += x;
		this.maxY += y;
		this.maxZ += z;
	}

	public BPos getInside(BPos offset, BlockRotation rotation) {
		switch (rotation) {
			case NONE:
				return new BPos(this.minX + offset.getX(), this.minY + offset.getY(), this.minZ + offset.getZ());
			case CLOCKWISE_90:
				return new BPos(this.minX - offset.getZ(), this.minY + offset.getY(), this.minZ + offset.getX());
			case CLOCKWISE_180:
				return new BPos(this.minX - offset.getX(), this.minY + offset.getY(), this.minZ - offset.getZ());
			case COUNTERCLOCKWISE_90:
				return new BPos(this.minX + offset.getZ(), this.minY + offset.getY(), this.minZ - offset.getX());
		}
		return null;
		// we assume the offset is not larger than the bb

	}

	public BlockBox getRotated(BlockRotation rotation) {
        /*
        We use the NONE referential as such

        1********
        *       *
        *       * X
        *       * | - Z
        ********2

        1=(minX,minY,minZ) (first point of the template)
        2=(maxX,maxY,maxZ) (second point of the template)

         */
		switch (rotation) {
			case COUNTERCLOCKWISE_90: // WEST
				return new BlockBox(this.minX, this.minY, this.maxZ, this.maxX, this.maxY, this.minZ);
			case CLOCKWISE_90: // EAST
				return new BlockBox(this.maxX, this.minY, this.minZ, this.minX, this.maxY, this.maxZ);
			case CLOCKWISE_180: // SOUTH
				return new BlockBox(this.maxX, this.minY, this.maxZ, this.minX, this.maxY, this.minZ);
			case NONE: // NORTH
				return this;

		}
		return null;
	}

	public boolean intersects(BlockBox box) {
		return this.maxX >= box.minX && this.minX <= box.maxX && this.maxZ >= box.minZ && this.minZ <= box.maxZ && this.maxY >= box.minY && this.minY <= box.maxY;
	}

	public boolean intersectsXZ(int minX, int minZ, int maxX, int maxZ) {
		return this.maxX >= minX && this.minX <= maxX && this.maxZ >= minZ && this.minZ <= maxZ;
	}

	public boolean contains(Vec3i v) {
		return v.getX() >= this.minX && v.getX() <= this.maxX && v.getZ() >= this.minZ && v.getZ() <= this.maxZ && v.getY() >= this.minY && v.getY() <= this.maxY;
	}

	public Vec3i getDimensions() {
		return new Vec3i(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
	}

	public void encompass(BlockBox box) {
		this.minX = Math.min(this.minX, box.minX);
		this.minY = Math.min(this.minY, box.minY);
		this.minZ = Math.min(this.minZ, box.minZ);
		this.maxX = Math.max(this.maxX, box.maxX);
		this.maxY = Math.max(this.maxY, box.maxY);
		this.maxZ = Math.max(this.maxZ, box.maxZ);
	}

	public int getXSpan() {
		return this.maxX - this.minX + 1;
	}

	public int getYSpan() {
		return this.maxY - this.minY + 1;
	}

	public int getZSpan() {
		return this.maxZ - this.minZ + 1;
	}

	public Vec3i getCenter() {
		return new Vec3i(this.minX + this.getXSpan() / 2, this.minY + this.getYSpan() / 2, this.minZ + this.getZSpan() / 2);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BlockBox)) return false;
		BlockBox blockBox = (BlockBox) o;
		return minX == blockBox.minX && minY == blockBox.minY && minZ == blockBox.minZ && maxX == blockBox.maxX && maxY == blockBox.maxY && maxZ == blockBox.maxZ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public String toString() {
		return "BlockBox{" +
				"minX=" + minX +
				", minY=" + minY +
				", minZ=" + minZ +
				", maxX=" + maxX +
				", maxY=" + maxY +
				", maxZ=" + maxZ +
				'}';
	}
}
