package com.seedfinding.mccore.util.pos;

import com.seedfinding.mccore.util.block.BlockDirection;
import com.seedfinding.mccore.util.block.BlockMirror;
import com.seedfinding.mccore.util.block.BlockRotation;
import com.seedfinding.mccore.util.math.Vec3i;

@SuppressWarnings("unused")
public class BPos extends Vec3i {

	public static final BPos ORIGIN = new BPos(0, 0, 0);

	public BPos(int x, int y, int z) {
		super(x, y, z);
	}

	public BPos(Vec3i vec3i) {
		super(vec3i.getX(), vec3i.getY(), vec3i.getZ());
	}

	public BPos add(BPos pos) {
		return this.add(pos.getX(), pos.getY(), pos.getZ());
	}

	public BPos subtract(BPos pos) {
		return this.subtract(pos.getX(), pos.getY(), pos.getZ());
	}

	public BPos shl(int amount) {
		return this.shl(amount, amount, amount);
	}

	public BPos shr(int amount) {
		return this.shr(amount, amount, amount);
	}

	public BPos add(int x, int y, int z) {
		return new BPos(this.getX() + x, this.getY() + y, this.getZ() + z);
	}

	public BPos subtract(int x, int y, int z) {
		return new BPos(this.getX() - x, this.getY() - y, this.getZ() - z);
	}

	public BPos shl(int bx, int by, int bz) {
		return new BPos(this.getX() << bx, this.getY() << by, this.getZ() << bz);
	}

	public BPos shr(int bx, int by, int bz) {
		return new BPos(this.getX() >> bx, this.getY() >> by, this.getZ() >> bz);
	}

	public BPos toChunkCorner() {
		return new BPos(this.getX() & -16, this.getY(), this.getZ() & -16);
	}

	public CPos toChunkPos() {
		return new CPos(this.getX() >> 4, this.getZ() >> 4);
	}

	public BPos relative(BlockDirection direction) {
		return new BPos(this.getX() + direction.getVector().getX(), this.getY() + direction.getVector().getY(), this.getZ() + direction.getVector().getZ());
	}

	public BPos relative(BlockDirection direction, int offset) {
		return offset == 0 ? this : new BPos(this.getX() + direction.getVector().getX() * offset, this.getY() + direction.getVector().getY() * offset, this.getZ() + direction.getVector().getZ() * offset);
	}

	public BPos relative(BlockDirection.Axis axis, int offset) {
		if (offset == 0) {
			return this;
		} else {
			int i = axis == BlockDirection.Axis.X ? offset : 0;
			int j = axis == BlockDirection.Axis.Y ? offset : 0;
			int k = axis == BlockDirection.Axis.Z ? offset : 0;
			return new BPos(this.getX() + i, this.getY() + j, this.getZ() + k);
		}
	}

	public RPos toRegionPos(int regionSize) {
		int x = this.getX() < 0 ? this.getX() - regionSize + 1 : this.getX();
		int z = this.getZ() < 0 ? this.getZ() - regionSize + 1 : this.getZ();
		return new RPos(x / regionSize, z / regionSize, regionSize);
	}

	public BPos transform(BlockMirror mirror, BlockRotation rotation, BPos pivot) {
		return rotation.rotate(mirror.mirror(this), pivot);
	}

}
