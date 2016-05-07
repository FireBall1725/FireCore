package com.fireball1725.firecore;

import com.fireball1725.firecore.common.config.Config;
import com.fireball1725.firecore.common.integrations.IntegrationsManager;
import com.fireball1725.firecore.common.util.LogHelper;
import com.fireball1725.firecore.proxy.IProxy;
import com.google.common.base.Stopwatch;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, certificateFingerprint = ModInfo.FINGERPRINT, dependencies = ModInfo.DEPENDENCIES, version = ModInfo.VERSION_BUILD, guiFactory = ModInfo.GUI_FACTORY)
public class FireCore {
    String static currentmodid;
    
    //call when you start Integrating with core
    public static void Startin(String Modid){
        this.currentmodid = Modid;
    }
    //end inetgration with Core
    public static void Endin(){
        this.currentmodid = null;
    }
    
    @Mod.Instance(ModInfo.MOD_ID)
    public static FireCore instance;

    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY_CLASS, serverSide = ModInfo.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    public static Configuration configuration;

    public LogHelper logHelper = new LogHelper(ModInfo.MOD_NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        logHelper.info("Pre Initialization (Started)");

        proxy.registerConfiguration(event.getSuggestedConfigurationFile());

        proxy.registerBlocks();
        proxy.registerItems();

        proxy.registerGUIs();

        proxy.registerFurnaceRecipes();
        proxy.registerEvents();

        proxy.registerRenderers();

        IntegrationsManager.instance().index();
        IntegrationsManager.instance().preInit();

        logHelper.info("Pre Initialization (Ended after " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms)");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        logHelper.info("Initialization (Started)");

        proxy.registerRecipes();

        IntegrationsManager.instance().init();

        logHelper.info("Initialization (Ended after " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms)");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        logHelper.info("Post Initialization (Started)");

        IntegrationsManager.instance().postInit();

        logHelper.info("Post Initialization (Ended after " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms)");
    }

    @SubscribeEvent
    public void onConfigurationChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(ModInfo.MOD_ID)) {
            Config.loadConfiguration();
        }
    }
}
