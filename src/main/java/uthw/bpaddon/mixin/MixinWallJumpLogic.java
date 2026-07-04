package uthw.bpaddon.mixin;

import com.jahirtrap.walljump.logic.WallJumpLogic;
import net.cravencraft.betterparagliders.capabilities.StaminaOverride;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tictim.paraglider.api.movement.Movement;
import tictim.paraglider.impl.movement.PlayerMovement;
import uthw.bpaddon.BPAddonMain;
import uthw.bpaddon.config.BPAddonConfig;

@Mixin(WallJumpLogic.class)
public class MixinWallJumpLogic {
    /**
     * Prevent the player from doing a wall jump if they have no stamina left
     * @param pl
     * @param ci
     */
    @Inject(method = "doWallJump", at = @At("HEAD"), remap = false, cancellable = true)
    private static void onDoWallJump(LocalPlayer pl, CallbackInfo ci) {
        if (pl.isCreative() || pl.isSpectator() || BPAddonConfig.wallJumpCost() <= 0) {
            return;
        }

        try {
            Movement movement = Movement.get(pl);

            if (movement instanceof PlayerMovement playerMovement && playerMovement.stamina().isDepleted()) {
                ci.cancel();
            }
        } catch (Exception e) {
            BPAddonMain.LOGGER.error("Failed to check for depleted stamina:", e);
        }
    }

    @Inject(method = "wallJump", at = @At("HEAD"), remap = false)
    private static void onWallJump(LocalPlayer pl, float up, CallbackInfo ci) {
        if (pl.isCreative() || pl.isSpectator() || BPAddonConfig.wallJumpCost() <= 0) {
            return;
        }

        try {
            Movement movement = Movement.get(pl);
            if (movement instanceof PlayerMovement playerMovement) {
                if (playerMovement.stamina() instanceof StaminaOverride staminaOverride) {

                    int staminaCost = (int) BPAddonConfig.wallJumpCost();

                    // Deduct the stamina visually on the client
                    staminaOverride.setTotalActionStaminaCost(staminaOverride.getTotalActionStaminaCost() + staminaCost);
                }
            }
        } catch (Exception e) {
            BPAddonMain.LOGGER.error("Failed to deduct client stamina for wall jump:", e);
        }
    }
}
