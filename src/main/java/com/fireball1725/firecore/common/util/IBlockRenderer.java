package com.fireball1725.firecore.common.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IBlockRenderer {
    @SideOnly(Side.CLIENT)
    void registerBlockRenderer();

    @SideOnly(Side.CLIENT)
    void registerBlockItemRenderer();
}
