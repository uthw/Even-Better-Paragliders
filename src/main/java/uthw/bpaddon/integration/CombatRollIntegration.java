package uthw.bpaddon.integration;

import net.combatroll.api.event.ServerSideRollEvents;
import net.cravencraft.betterparagliders.capabilities.StaminaOverride;
import tictim.paraglider.api.movement.Movement;
import tictim.paraglider.impl.movement.PlayerMovement;
import uthw.bpaddon.BPAddonMain;
import uthw.bpaddon.config.BPAddonConfig;

public class CombatRollIntegration {
    public static void register() {
        ServerSideRollEvents.PLAYER_START_ROLLING.register((player, velocity) -> {
            if (player == null || player.isCreative() || player.isSpectator() || BPAddonConfig.dodgeRollCost() <= 0) {
                return;
            }

            try {
                Movement movement = Movement.get(player);

                if (movement instanceof PlayerMovement playerMovement) {
                    if (playerMovement.stamina() instanceof StaminaOverride staminaOverride) {

                        // Drain stamina on the server side
                        int staminaCost = (int) BPAddonConfig.dodgeRollCost();
                        staminaOverride.setTotalActionStaminaCost(staminaOverride.getTotalActionStaminaCost() + staminaCost);

                    } else {
                        BPAddonMain.LOGGER.warn("Stamina is invalid");
                    }
                }
            } catch (Exception e) {
                BPAddonMain.LOGGER.error("Failed to deduct stamina for a dodge roll:", e);
            }
        });
    }
}
