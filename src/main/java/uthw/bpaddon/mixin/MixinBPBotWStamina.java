package uthw.bpaddon.mixin;

import net.cravencraft.betterparagliders.capabilities.StaminaOverride;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tictim.paraglider.api.movement.Movement;
import tictim.paraglider.impl.stamina.BotWStamina;
import uthw.bpaddon.config.BPAddonConfig;

@Mixin(value = BotWStamina.class, priority = 500)
public abstract class MixinBPBotWStamina {

    @Unique
    private int bpaddon$lastSeenDelay = 0;

    @Inject(method = "update", at = @At("HEAD"), remap = false)
    private void overrideBPConfigValue(Movement movement, CallbackInfo ci) {
        int currentDelay = movement.recoveryDelay();

        if (currentDelay == 10) {
            // If the delay is at least 0, BP is draining stamina
            int actionCost = ((StaminaOverride) this).getTotalActionStaminaCost();

            if (this.bpaddon$lastSeenDelay != 11 && actionCost >= 0) {
                int configDelay = BPAddonConfig.staminaRecoveryDelay();

                if (configDelay != 10) {
                    movement.setRecoveryDelay(configDelay);
                    currentDelay = configDelay;
                }
            }
        }

        this.bpaddon$lastSeenDelay = currentDelay;
    }
}