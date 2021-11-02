package com.seedfinding.mccore.gen;

import com.seedfinding.mccore.block.Tile;
import com.seedfinding.mccore.util.block.BlockBox;
import com.seedfinding.mccore.util.block.BlockMirror;
import com.seedfinding.mccore.util.block.BlockRotation;
import com.seedfinding.mccore.util.pos.BPos;

public class StructurePlacement {

	public BlockBox box;
	public BlockMirror mirror;
	public BlockRotation rotation;
	public BPos pivot;

	public StructurePlacement() {
		this.box = BlockBox.empty();
		this.mirror = BlockMirror.NONE;
		this.rotation = BlockRotation.NONE;
		this.pivot = BPos.ORIGIN;
	}

	public Tile transform(Tile tile) {
		return this.transformAndSet(tile.copy());
	}

	public Tile transformAndSet(Tile tile) {
		tile.setPos(tile.getPos().transform(mirror, rotation, pivot));
		return tile;
	}

}
