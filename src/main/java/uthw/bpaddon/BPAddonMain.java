package uthw.bpaddon;

import com.mojang.logging.LogUtils;
import net.cravencraft.betterparagliders.utils.CalculateStaminaUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import uthw.bpaddon.config.BPAddonConfig;
import uthw.bpaddon.integration.CombatRollIntegration;

@Mod("bpaddon")
public class BPAddonMain {
    public static final Logger LOGGER = LogUtils.getLogger();
    public BPAddonMain() {
        // Common config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BPAddonConfig.SPEC);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Default stamina costs for thrown weapons
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                try {
                    if (item instanceof TridentItem || item.getUseAnimation(item.getDefaultInstance()) == UseAnim.SPEAR) {
                        String itemId = item.getDescriptionId().replace("item.", "");

                        CalculateStaminaUtils.DATAPACK_RANGED_STAMINA_OVERRIDES.putIfAbsent(itemId, -999.0);
                    }
                } catch (Exception e) {

                }
            }

            // Load combat roll integration if the mod is installed
            if (ModList.get().isLoaded("combatroll")) {
                CombatRollIntegration.register();
            }
        });
    }
}