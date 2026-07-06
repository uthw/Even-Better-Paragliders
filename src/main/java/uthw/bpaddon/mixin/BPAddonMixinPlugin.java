package uthw.bpaddon.mixin;

import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class BPAddonMixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        // Only load the walljump mixins if walljump is enabled
        if (mixinClassName.contains("MixinWallJumpLogic") || mixinClassName.contains("MixinMessageWallJump")) {
            return LoadingModList.get().getModFileById("walljump") != null;
        }

        // Only load the shield expansion mixin if shield expansion is enabled
        if (mixinClassName.contains("MixinCalculateStaminaUtilsShield")) {
            return LoadingModList.get().getModFileById("shieldexp") != null;
        }

        // Only load the combat roll mixin if combat roll is enabled
        if (mixinClassName.contains("MixinRollManager")) {
            return LoadingModList.get().getModFileById("combatroll") != null;
        }

        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
