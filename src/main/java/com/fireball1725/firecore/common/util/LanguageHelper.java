package com.fireball1725.firecore.common.util;

import com.fireball1725.firecore.ModInfo;
import net.minecraft.util.text.translation.I18n;

public enum LanguageHelper {
    MESSAGE("message"),
    LABEL("label"),
    BLOCK("tile"),
    ITEM("item"),
    DESCRIPTION("description"),
    JEI("jei"),
    NONE(""),;

    private String name;

    LanguageHelper(String name) {
        this.name = name;
    }

    public String translateMessage(String message, String modId) {
        if (this.name.equals(""))
            return I18n.translateToLocal(message);

        return I18n.translateToLocal(String.format("%s.%s.%s", this.name, modId, message));
    }

    //Todo: for later Minecraft.getMinecraft().thePlayer.uniqueID.toString() == "4f3a8d1e-33c1-44e7-bce8-e683027c7dac"

}