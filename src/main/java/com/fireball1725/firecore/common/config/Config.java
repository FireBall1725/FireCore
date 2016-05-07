package com.fireball1725.firecore.common.config;

import com.fireball1725.firecore.ModInfo;
import com.fireball1725.firecore.FireCore;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.io.File;
import java.util.Arrays;

public class Config extends GuiConfig {
    public static Configuration configuration;

    public Config(GuiScreen parentScreen) {
        super(
                parentScreen,
                Arrays.asList(new IConfigElement[]{
                        new ConfigElement(FireCore.configuration.getCategory("SampleData"))
                }),
                ModInfo.MOD_ID, false, false, "Applied Logistics Configuration");
        titleLine2 = FireCore.configuration.getConfigFile().getAbsolutePath();
    }

    public static Configuration initConfig(File configFile) {
        if (configuration == null) {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
        return configuration;
    }

    public static void loadConfiguration() {
        ConfigSample.init(configuration);

        configuration.save();
    }
}