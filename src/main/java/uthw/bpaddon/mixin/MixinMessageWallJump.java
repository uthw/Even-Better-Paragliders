package uthw.bpaddon.mixin;

import com.jahirtrap.walljump.network.message.MessageWallJump;
import net.cravencraft.betterparagliders.capabilities.StaminaOverride;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tictim.paraglider.api.movement.Movement;
import tictim.paraglider.impl.movement.PlayerMovement;
import uthw.bpaddon.BPAddonMain;
import uthw.bpaddon.config.BPAddonConfig;

import java.util.function.Supplier;

@Mixin(MessageWallJump.class)
public class MixinMessageWallJump {
    @Inject(method = "handle", at = @At("HEAD"), remap = false)
    private static void onHandleWallJump(MessageWallJump message, Supplier<NetworkEvent.Context> supplier, CallbackInfo ci) {
        if (message.didWallJump()) {
            supplier.get().enqueueWork(() -> {
                ServerPlayer player = supplier.get().getSender();

                if (player == null || player.isCreative() || player.isSpectator() || BPAddonConfig.wallJumpCost() <= 0) {
                    return;
                }

                try {
                    Movement movement = Movement.get(player);

                    // Cast Movement to PlayerMovement
                    if (movement instanceof PlayerMovement playerMovement) {
                        // Further cast Stamina to StaminaOverride
                        if (playerMovement.stamina() instanceof StaminaOverride staminaOverride) {
                            int staminaCost = (int)BPAddonConfig.wallJumpCost();
                            staminaOverride.setTotalActionStaminaCost(staminaOverride.getTotalActionStaminaCost() + staminaCost);
                        } else {
                            BPAddonMain.LOGGER.warn("Stamina is invalid");
                        }
                    } else {
                        BPAddonMain.LOGGER.warn("Movement is invalid");
                    }
                } catch (Exception e) {
                    BPAddonMain.LOGGER.error("Failed to deduct stamina for a wall jump:", e);
                }
            });
        }
    }
}
