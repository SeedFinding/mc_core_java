package com.seedfinding.mccore.gen;

import com.seedfinding.mccore.block.Blocks;
import com.seedfinding.mccore.block.Tile;
import com.seedfinding.mccore.util.data.Identifier;
import com.seedfinding.mccore.util.math.Vec3i;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mcseed.rand.JRand;

import java.util.List;
import java.util.stream.Collectors;

public class StructurePiece {

	private final PieceInfo info;
	private BPos position;
	private StructurePlacement placement;

	public StructurePiece(PieceInfo info) {
		this.info = info;
	}

	public PieceInfo getInfo() {
		return this.info;
	}

	public void generate(JRand rand) {
		List<Tile> jigsaws = this.getJigsaws(rand);

		for (Tile jigsaw : jigsaws) {
			Vec3i facing = JigsawHelper.getFacing(jigsaw.getBlockState()).getVector();
			BPos nextPieceStart = jigsaw.getPos().add(facing.getX(), facing.getY(), facing.getZ());

			Identifier pool = new Identifier(jigsaw.getBlockEntity().getString("pool"));

			boolean thisContainsNext = this.placement.box.contains(nextPieceStart);
		}
	}

	public List<Tile> getJigsaws(JRand rand) {
		int paletteId = rand.nextInt(this.info.getPalettes().size());

		List<Tile> tiles = this.info.getTiles().stream().sorted(PieceInfo.TILE_SORTER)
				.filter(tile -> tile.getBlockState(paletteId).getBlock().getId() == Blocks.JIGSAW.getId())
				.map(tile -> tile.copy(paletteId)).map(this.placement::transformAndSet)
				.peek(tile -> tile.setPos(tile.getPos().add(this.position)))
				.filter(tile -> this.placement.box.contains(tile.getPos()))
				.peek(tile -> JigsawHelper.rotate(tile.getBlockState(), this.placement.rotation))
				.collect(Collectors.toList());

		rand.shuffle(tiles);

		return tiles;
	}

}
