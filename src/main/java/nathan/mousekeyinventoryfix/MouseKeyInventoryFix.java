package nathan.mousekeyinventoryfix;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MouseKeyInventoryFix implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("mousekeyinventoryfix");
    
    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");
    }
}
