package com.fireball1725.firecore.common.network;

import com.fireball1725.firecore.ModInfo;
import com.fireball1725.firecore.common.network.messages.PacketButtonClick;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MOD_ID.toLowerCase());

    public static void init() {
        INSTANCE.registerMessage(PacketButtonClick.class, PacketButtonClick.class, 0, Side.SERVER);
    }
}
