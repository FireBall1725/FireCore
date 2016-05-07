package com.fireball1725.firecore;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class FireCoreCreativeTabs {
    public static final CreativeTabs tabGeneral = new CreativeTabs(ModInfo.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Items.apple;
        }

        @Override
        public String getTabLabel() {
            return ModInfo.MOD_ID + ".general";
        }
    };
}
