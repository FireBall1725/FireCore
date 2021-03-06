package com.fireball1725.firecore.common.integrations;

import com.fireball1725.firecore.FireCore;
import com.fireball1725.firecore.IntegrationModIDs;
import com.fireball1725.firecore.common.integrations.waila.Waila;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntegrationsManager {
    private static IntegrationsManager INSTANCE = new IntegrationsManager();
    private final List<IIntegration> integrationMods = new ArrayList<IIntegration>();

    public static IntegrationsManager instance() {
        return INSTANCE;
    }

    public void index() {
        Map<String, Class<? extends IIntegration>> integrationClasses = new HashMap<String, Class<? extends IIntegration>>();

        try {
            integrationClasses.put(IntegrationModIDs.WAILA, Waila.class);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        // hmm... configuration stuff?

        for (Map.Entry<String, Class<? extends IIntegration>> entry : integrationClasses.entrySet()) {
            if (Loader.isModLoaded(entry.getKey())) {
                try {
                    integrationMods.add(entry.getValue().newInstance());
                    FireCore.instance.logHelper.info("Integration with " + entry.getKey() + ": Enabled");
                } catch (Throwable ex) {
                    FireCore.instance.logHelper.error("Failed to load integration correctly");
                    ex.printStackTrace();
                }
            } else {
                FireCore.instance.logHelper.info("Integration with " + entry.getKey() + ": Disabled");
            }
        }
    }

    public void preInit() {
        for (IIntegration integration : integrationMods) {
            try {
                integration.preInit();
            } catch (Throwable ex) {
                FireCore.instance.logHelper.error("(Pre Init) Unable to load integration from " + integration.getClass());
                ex.printStackTrace();
            }
        }
    }

    public void init() {
        for (IIntegration integration : integrationMods) {
            try {
                integration.init();
            } catch (Throwable ex) {
                FireCore.instance.logHelper.error("(Init) Unable to load integration from " + integration.getClass());
                ex.printStackTrace();
            }
        }
    }

    public void postInit() {
        for (IIntegration integration : integrationMods) {
            try {
                integration.postInit();
            } catch (Throwable ex) {
                FireCore.instance.logHelper.error("(Post Init) Unable to load integration from " + integration.getClass());
                ex.printStackTrace();
            }
        }
    }
}
