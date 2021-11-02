package com.seedfinding.mccore.block;

import com.seedfinding.mccore.nbt.tag.NBTCompound;
import com.seedfinding.mccore.util.pos.BPos;

public class Tile {

	private BPos pos;
	private BlockState blockState;
	private NBTCompound blockEntity;

	public Tile(BPos pos, BlockState blockState, NBTCompound blockEntity) {
		this.pos = pos;
		this.blockState = blockState;
		this.blockEntity = blockEntity;
	}

	public BPos getPos() {
		return this.pos;
	}

	public void setPos(BPos pos) {
		this.pos = pos;
	}

	public BlockState getBlockState() {
		return this.blockState;
	}

	public void setBlockState(BlockState blockState) {
		this.blockState = blockState;
	}

	public NBTCompound getBlockEntity() {
		return this.blockEntity;
	}

	public void setBlockEntity(NBTCompound blockEntity) {
		this.blockEntity = blockEntity;
	}

	public Tile copy() {
		return new Tile(this.getPos(), this.getBlockState(), this.getBlockEntity());
	}

}
