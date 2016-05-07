package com.fireball1725.firecore.common.util;

import com.fireball1725.firecore.FireCore;
import com.fireball1725.firecore.common.blocks.BlockBase;
import com.fireball1725.firecore.common.items.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Locale;

public class RegistrationHelper {
    public static Block registerBlock(Class<? extends Block> blockClass) {
        return registerBlock(blockClass, ItemBlock.class);
    }

    public static Block registerBlock(Class<? extends Block> blockClass, Class<? extends ItemBlock> itemBlockClass) {
        Block block = null;
        ItemBlock itemBlock;
        String internalName;

        try {
            block = blockClass.getConstructor().newInstance();
            itemBlock = itemBlockClass.getConstructor(Block.class).newInstance(block);

            internalName = ((BlockBase) block).getInternalName();

            if (!internalName.equals(internalName.toLowerCase(Locale.US)))
                throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Item: %s", internalName));

            if (internalName.isEmpty())
                throw new IllegalArgumentException(String.format("Unlocalized name cannot be blank! Item: %s", blockClass.getCanonicalName()));

            block.setRegistryName(internalName);
            block.setUnlocalizedName(internalName);
            itemBlock.setRegistryName(block.getRegistryName());

            GameRegistry.register(block);
            GameRegistry.register(itemBlock);

            if (block instanceof IBlockRenderer && Platform.isClient()) {
                ((IBlockRenderer) block).registerBlockRenderer();
                ((IBlockRenderer) block).registerBlockItemRenderer();
            }

            FireCore.instance.logHelper.info(String.format("Registered block (%s)", blockClass.getCanonicalName()));
        } catch (Exception ex) {
            FireCore.instance.logHelper.fatal(String.format("Fatal Error while registering block (%s)", blockClass.getCanonicalName()));
            ex.printStackTrace();
        }

        return block;
    }

    public static Item registerItem(Class<? extends Item> itemClass) {
        Item item = null;
        String internalName;

        try {
            item = itemClass.getConstructor().newInstance();

            internalName = ((ItemBase) item).getInternalName();

            if (!internalName.equals(internalName.toLowerCase(Locale.US)))
                throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Item: %s", internalName));

            if (internalName.isEmpty())
                throw new IllegalArgumentException(String.format("Unlocalized name cannot be blank! Item: %s", itemClass.getCanonicalName()));

            item.setRegistryName(internalName);
            item.setUnlocalizedName(internalName);

            GameRegistry.register(item);

            if (item instanceof IItemRenderer && Platform.isClient()) {
                ((IItemRenderer) item).registerItemRenderer();
            }

            FireCore.instance.logHelper.info(String.format("Registered item (%s)", itemClass.getCanonicalName()));
        } catch (Exception ex) {
            FireCore.instance.logHelper.fatal(String.format("Fatal Error while registering item (%s)", itemClass.getCanonicalName()));
            ex.printStackTrace();
        }

        return item;
    }
}