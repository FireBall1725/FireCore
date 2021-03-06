package com.fireball1725.firecore.common.blocks;

import com.fireball1725.firecore.common.tileentities.TileEntityBase;
import com.fireball1725.firecore.common.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockBase extends Block implements IBlockRenderer {
    protected boolean isInventory = false;
    protected String resourcePath = "";
    protected String internalName = "";
    protected String modId = "";

    protected BlockBase(Material material, String resourcePath, String modId) {
        super(material);

        setStepSound(SoundType.STONE);
        setHardness(2.2F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 0);
        this.resourcePath = resourcePath;
        this.modId = modId;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    @Override
    public String getUnlocalizedName() {
        String blockName = getUnwrappedUnlocalizedName(super.getUnlocalizedName());

        return String.format("tile.%s.%s", this.modId, blockName);
    }

    private String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, false);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntityBase tileEntity = TileHelper.getTileEntity(world, pos, TileEntityBase.class);
        if (tileEntity != null && tileEntity.hasCustomName()) {
            final ItemStack itemStack = new ItemStack(this, 1, tileEntity.getBlockMetadata());
            itemStack.setStackDisplayName(tileEntity.getCustomName());

            ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
            drops.add(itemStack);

            return drops;
        }
        return super.getDrops(world, pos, state, fortune);
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        final IOrientable rotatable = this.getOrientable(world, pos);
        if (rotatable != null && rotatable.canBeRotated()) {
            if (this.hasCustomRotation()) {
                this.customRotateBlock(rotatable, axis);
                return true;
            } else {
                EnumFacing forward = rotatable.getForward();

                for (int rs = 0; rs < 4; rs++) {
                    forward = Platform.rotateAround(forward, axis);

                    if (this.isValidOrientation(world, pos, forward)) {
                        rotatable.setOrientation(forward);
                        return true;
                    }
                }
            }
        }

        return super.rotateBlock(world, pos, axis);
    }

    protected boolean hasCustomRotation() {
        return false;
    }

    protected void customRotateBlock(final IOrientable rotatable, final EnumFacing axis) {

    }

    public boolean isValidOrientation(final World world, final BlockPos pos, final EnumFacing forward) {
        return true;
    }

    public IOrientable getOrientable(final IBlockAccess world, final BlockPos pos) {
        if (this instanceof IOrientableBlock)
            return this.getOrientable(world, pos);
        return null;
    }

    @Override
    public EnumFacing[] getValidRotations(World world, BlockPos pos) {
        return new EnumFacing[0];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockRenderer() {
        final String resourcePath = String.format("%s:%s", this.modId, this.resourcePath);

        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(resourcePath, getPropertyString(state.getProperties()));
            }
        });
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockItemRenderer() {
        final String resourcePath = String.format("%s:%s", this.modId, this.resourcePath);

        List<ItemStack> subBlocks = new ArrayList<ItemStack>();
        getSubBlocks(Item.getItemFromBlock(this), null, subBlocks);

        for (ItemStack itemStack : subBlocks) {
            IBlockState blockState = this.getStateFromMeta(itemStack.getItemDamage());
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), itemStack.getItemDamage(), new ModelResourceLocation(resourcePath, Platform.getPropertyString(blockState.getProperties())));

        }
    }
}
