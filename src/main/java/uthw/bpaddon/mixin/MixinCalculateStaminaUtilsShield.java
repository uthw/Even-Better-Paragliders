package uthw.bpaddon.mixin;

import net.cravencraft.betterparagliders.utils.CalculateStaminaUtils;
import net.minecraft.world.entity.player.Player;
import org.infernalstudios.shieldexp.access.LivingEntityAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uthw.bpaddon.BPAddonMain;
import uthw.bpaddon.config.BPAddonConfig;

@Mixin(CalculateStaminaUtils.class)
public class MixinCalculateStaminaUtilsShield {
    /**
     * Cancels stamina consumption if a blocked attack was parried.
     * @param player
     * @param blockedDamage
     * @param cir
     */
    @Inject(method = "calculateBlockStaminaCost", at = @At("HEAD"), remap = false, cancellable = true)
    private static void calculateBlockStaminaCost(Player player, float blockedDamage, CallbackInfoReturnable<Integer> cir) {
        if (!BPAddonConfig.PARRIES_COST_ZERO_STAMINA.get()) {
            return;
        }

        try {
            int parryWindow = LivingEntityAccess.get(player).getParryWindow();

            if (parryWindow > 0) {
                cir.setReturnValue(0);
            }
        } catch (Exception e) {
            BPAddonMain.LOGGER.error("Failed to get Shield Expansion parry window: ", e);
        }
    }
}