package uthw.bpaddon.integration;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.thecelestialworkshop.celestisynth.api.animation.player.CSAnimator;

/**
 * This fixes a memory leak in Celestisynth where it retains animation data when you leave a world or change dimensions.
 * I should make this its own mod, however I'm lazy.
 */
public class CelestisynthMemLeakFix {
    /**
     * Fires when a player dies or is unloaded
     */
    @SubscribeEvent
    public void onEntityLeaveWorld(EntityLeaveLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            return;
        }

        if (event.getEntity() instanceof AbstractClientPlayer player) {
            CSAnimator.animationData.remove(player);
            CSAnimator.otherAnimationData.remove(player);
            CSAnimator.mirroredAnimationData.remove(player);
        }
    }

    /**
     * Fires when a player leaves a world or dimension
     */
    @SubscribeEvent
    public void onLevelUnload(LevelEvent.Unload event) {
        if (!event.getLevel().isClientSide()) {
            return;
        }

        int cachedAnimations = CSAnimator.animationData.size();

        if (cachedAnimations > 0) {
            CSAnimator.animationData.clear();
            CSAnimator.otherAnimationData.clear();
            CSAnimator.mirroredAnimationData.clear();
        }
    }
}
