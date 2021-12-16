/*
 * Copyright (c) 2019-2021 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.glass.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StainedGlassBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;
import net.wurstclient.glass.MoGlass;
import net.wurstclient.glass.StainedGlassSlabBlock;
import net.wurstclient.glass.StainedGlassStairsBlock;

@Mixin(StainedGlassBlock.class)
public class StainedGlassBlockMixin extends AbstractGlassBlock
{
	@Shadow
	@Final
	private DyeColor color;
	
	private StainedGlassBlockMixin(MoGlass moGlass, Settings block$Settings_1)
	{
		super(block$Settings_1);
	}
	
	@Override
	public boolean isSideInvisible(BlockState blockState_1,
		BlockState blockState_2, Direction direction_1)
	{
		Block block_2 = blockState_2.getBlock();
		
		if(block_2 instanceof StainedGlassSlabBlock
			&& ((StainedGlassSlabBlock)block_2).getColor() == color)
			if(isInvisibleToGlassSlab(blockState_1, blockState_2, direction_1))
				return true;
			
		if(block_2 instanceof StainedGlassStairsBlock
			&& ((StainedGlassStairsBlock)block_2).getColor() == color)
			if(isInvisibleToGlassStairs(blockState_1, blockState_2,
				direction_1))
				return true;
			
		return super.isSideInvisible(blockState_1, blockState_2, direction_1);
	}
	
	private boolean isInvisibleToGlassSlab(BlockState blockState_1,
		BlockState blockState_2, Direction direction_1)
	{
		SlabType type2 = blockState_2.get(SlabBlock.TYPE);
		
		if(type2 == SlabType.DOUBLE)
			return true;
		
		if(direction_1 == Direction.UP)
			if(type2 != SlabType.TOP)
				return true;
			
		if(direction_1 == Direction.DOWN)
			if(type2 != SlabType.BOTTOM)
				return true;
			
		return false;
	}
	
	private boolean isInvisibleToGlassStairs(BlockState blockState_1,
		BlockState blockState_2, Direction direction_1)
	{
		BlockHalf half2 = blockState_2.get(StairsBlock.HALF);
		Direction facing2 = blockState_2.get(StairsBlock.FACING);
		
		// up
		if(direction_1 == Direction.UP)
			if(half2 == BlockHalf.BOTTOM)
				return true;
			
		// down
		if(direction_1 == Direction.DOWN)
			if(half2 == BlockHalf.TOP)
				return true;
			
		// other stairs rear
		if(facing2 == direction_1.getOpposite())
			return true;
		
		return false;
	}
}
