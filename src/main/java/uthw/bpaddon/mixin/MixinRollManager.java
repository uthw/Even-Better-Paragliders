package uthw.bpaddon.mixin;

import net.combatroll.internals.RollManager;
import net.cravencraft.betterparagliders.capabilities.StaminaOverride;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tictim.paraglider.api.movement.Movement;
import tictim.paraglider.impl.movement.PlayerMovement;
import uthw.bpaddon.BPAddonMain;
import uthw.bpaddon.config.BPAddonConfig;

@Mixin(RollManager.class)
public class MixinRollManager {
    /**
     * Prevents the player from rolling if they don't have enough stamina
     * @param player
     * @param cir
     */
    @Inject(method = "isRollAvailable", at = @At("HEAD"), remap = false, cancellable = true)
    private void onIsRollAvailable(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (player.isCreative() || player.isSpectator() || BPAddonConfig.dodgeRollCost() <= 0) {
            return;
        }

        try {
            Movement movement = Movement.get(player);
            if (movement instanceof PlayerMovement playerMovement && playerMovement.stamina().isDepleted()) {
                cir.setReturnValue(false); // Prevent rolling
            }
        } catch (Exception e) {
            BPAddonMain.LOGGER.error("Failed to check for depleted stamina:", e);
        }
    }

    /**
     * Visually deducts stamina on the player's stamina wheel when they roll
     * @param player
     * @param ci
     */
    @Inject(method = "onRoll", at = @At("HEAD"), remap = false)
    private void onRoll(LocalPlayer player, CallbackInfo ci) {
        if (player.isCreative() || player.isSpectator() || BPAddonConfig.dodgeRollCost() <= 0) {
            return;
        }

        try {
            Movement movement = Movement.get(player);
            if (movement instanceof PlayerMovement playerMovement) {
                if (playerMovement.stamina() instanceof StaminaOverride staminaOverride) {

                    int staminaCost = (int) BPAddonConfig.dodgeRollCost();
                    staminaOverride.setTotalActionStaminaCost(staminaOverride.getTotalActionStaminaCost() + staminaCost);

                }
            }
        } catch (Exception e) {
            BPAddonMain.LOGGER.error("Failed to deduct client stamina for dodge roll:", e);
        }
    }
}
