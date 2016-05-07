package com.fireball1725.firecore.proxy;

import java.io.File;

public interface IProxy {
    /**
     * Register Blocks
     */
    void registerBlocks();

    /**
     * Register Items
     */
    void registerItems();

    /**
     * Register Furnace Recipes
     */
    void registerFurnaceRecipes();

    /**
     * Register Recipes
     */
    void registerRecipes();

    /**
     * Register Events
     */
    void registerEvents();

    /**
     * Register GUIs
     */
    void registerGUIs();

    /**
     * Register Renderers
     */
    void registerRenderers();

    /**
     * Register Configuration
     * @param configFile Configuration File
     */
    void registerConfiguration(File configFile);
}

