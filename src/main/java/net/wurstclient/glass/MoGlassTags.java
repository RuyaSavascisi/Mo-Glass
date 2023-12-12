/*
 * Copyright (c) 2019-2023 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.glass;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.wurstclient.glass.mixin.ChunkLightProviderMixin;

public enum MoGlassTags
{
	;
	
	/**
	 * <p>
	 * Blocks in this tag aren't opaque, but need to be considered opaque for
	 * lighting calculations. This is necessary for tinted glass slabs and
	 * stairs to properly block light.
	 *
	 * <p>
	 * Minecraft's lighting engine automatically calculates how smaller blocks
	 * like stairs and slabs will sometimes block light depending on how they
	 * are placed, but it only does this for blocks that are marked as opaque.
	 * Blocks made of glass, however, can't be opaque.
	 *
	 * <p>
	 * This is fine for vanilla tinted glass blocks, since full blocks can skip
	 * this calculation altogether and simply block all light in all directions.
	 * But for smaller tinted glass blocks like slabs and stairs, this needs to
	 * be fixed.
	 *
	 * <p>
	 * See {@link ChunkLightProviderMixin} for how I modified the default
	 * lighting engine to make this work. If your mod replaces the default
	 * lighting engine (hi jellysquid), you will likely need to make a similar
	 * change to make it compatible with Mo Glass's tinted glass blocks.
	 */
	public static final TagKey<Block> OPAQUE_FOR_LIGHTING =
		createTag("opaque_for_lighting");
	
	private static TagKey<Block> createTag(String idPath)
	{
		return TagKey.of(RegistryKeys.BLOCK,
			new Identifier("mo_glass", idPath));
	}
}
