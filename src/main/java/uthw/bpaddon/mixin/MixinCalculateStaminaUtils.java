package uthw.bpaddon.mixin;

import net.bettercombat.api.AttackHand;
import net.bettercombat.logic.PlayerAttackHelper;
import net.cravencraft.betterparagliders.attributes.BetterParaglidersAttributes;
import net.cravencraft.betterparagliders.config.ServerConfig;
import net.cravencraft.betterparagliders.utils.CalculateStaminaUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.UseAnim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uthw.bpaddon.config.BPAddonConfig;

@Mixin(CalculateStaminaUtils.class)
public class MixinCalculateStaminaUtils {
    /**
     * Overrides stamina consumption for melee weapons to make it based on the Better Combat weapon type instead of attack damage.
     * @param player
     * @param currentCombo
     * @param cir
     */
    @Inject(method = "calculateMeleeStaminaCost", at = @At("HEAD"), remap = false, cancellable = true)
    private static void onCalculateMeleeStaminaCost(Player player, int currentCombo, CallbackInfoReturnable<Integer> cir) {
        if (!BPAddonConfig.ENABLE_WEAPON_OVERRIDE.get()) {
            return;
        }

        double totalStaminaConsumption;
        AttackHand attackHand = PlayerAttackHelper.getCurrentAttack(player, currentCombo);
        boolean isTwoHanded = attackHand.attributes().isTwoHanded();
        String attackingItemId = attackHand.itemStack().getItem().getDescriptionId().replace("item.", "");

        // Datapack overrides
        if (CalculateStaminaUtils.DATAPACK_MELEE_STAMINA_OVERRIDES.containsKey(attackingItemId)) {
            totalStaminaConsumption = CalculateStaminaUtils.DATAPACK_MELEE_STAMINA_OVERRIDES.get(attackingItemId).intValue() * ServerConfig.meleeStaminaConsumption();
        } else {
            double reachFactor = attackHand.attributes().attackRange();
            String category = attackHand.attributes().category();
            if (category == null || category.isBlank()) category = "unknown";

            // Use config for stamina cost by weapon type
            double baseCost = BPAddonConfig.defaultBaseCost();
            switch (category) {
                case "hammer": baseCost = BPAddonConfig.hammerCost(); break;
                case "anchor": baseCost = BPAddonConfig.anchorCost(); break;
                case "claymore": baseCost = BPAddonConfig.claymoreCost(); break;
                case "double_axe": baseCost = BPAddonConfig.doubleAxeCost(); break;
                case "halberd": baseCost = BPAddonConfig.halberdCost(); break;
                case "lance": baseCost = BPAddonConfig.lanceCost(); break;
                case "glaive": baseCost = BPAddonConfig.glaiveCost(); break;
                case "scythe": baseCost = BPAddonConfig.scytheCost(); break;
                case "axe": baseCost = BPAddonConfig.axeCost(); break;
                case "mace": baseCost = BPAddonConfig.maceCost(); break;
                case "trident": baseCost = BPAddonConfig.tridentCost(); break;
                case "spear": baseCost = BPAddonConfig.spearCost(); break;
                case "sword": baseCost = BPAddonConfig.swordCost(); break;
                case "coral_blade": baseCost = BPAddonConfig.coralBladeCost(); break;
                case "cutlass": baseCost = BPAddonConfig.cutlassCost(); break;
                case "twin_blade": baseCost = BPAddonConfig.twinBladeCost(); break;
                case "battlestaff": baseCost = BPAddonConfig.battlestaffCost(); break;
                case "katana": baseCost = BPAddonConfig.katanaCost(); break;
                case "rapier": baseCost = BPAddonConfig.rapierCost(); break;
                case "wand": baseCost = BPAddonConfig.wandCost(); break;
                case "sickle": baseCost = BPAddonConfig.sickleCost(); break;
                case "claw": baseCost = BPAddonConfig.clawCost(); break;
                case "dagger": baseCost = BPAddonConfig.daggerCost(); break;
                case "soul_knife": baseCost = BPAddonConfig.soulKnifeCost(); break;
                case "fist": baseCost = BPAddonConfig.fistCost(); break;
                default: break;
            }

            totalStaminaConsumption = (baseCost + reachFactor) * ServerConfig.meleeStaminaConsumption();
        }

        // Reduce stamina based on one/two-handed and attributes
        if (isTwoHanded) {
            totalStaminaConsumption = (totalStaminaConsumption * ServerConfig.twoHandedStaminaConsumption()) - player.getAttributeValue(BetterParaglidersAttributes.TWO_HANDED_STAMINA_REDUCTION.get());
        } else {
            totalStaminaConsumption = (totalStaminaConsumption * ServerConfig.oneHandedStaminaConsumption()) - player.getAttributeValue(BetterParaglidersAttributes.ONE_HANDED_STAMINA_REDUCTION.get());
        }

        totalStaminaConsumption -= player.getAttributeValue(BetterParaglidersAttributes.BASE_MELEE_STAMINA_REDUCTION.get());

        // Use the result of this method instead of the original
        cir.setReturnValue((int) Math.ceil(totalStaminaConsumption));
    }

    @Inject(method = "calculateRangeStaminaCost", at = @At("HEAD"), remap = false, cancellable = true)
    private static void onCalculateRangeStaminaCost(Player player, CallbackInfoReturnable<Integer> cir) {
        Item item = player.getUseItem().getItem();

        if ((item instanceof TridentItem || item.getUseAnimation(player.getUseItem()) == UseAnim.SPEAR)
                && BPAddonConfig.thrownWeaponCost() > 0) {
            String itemId = item.getDescriptionId().replace("item.", "");

            double finalStaminaCost = BPAddonConfig.thrownWeaponCost();

            // Datapack override
            if (CalculateStaminaUtils.DATAPACK_RANGED_STAMINA_OVERRIDES.containsKey(itemId)) {
                double datapackValue = CalculateStaminaUtils.DATAPACK_RANGED_STAMINA_OVERRIDES.get(itemId);

                // Default value set during registry creation is -999. If not -999, item has an override
                if (datapackValue != -999.0) {
                    finalStaminaCost = datapackValue;
                }
            }

            // Better Paragliders stamina reduction
            int calculatedCost = (int) Math.round(finalStaminaCost - player.getAttributeValue(net.cravencraft.betterparagliders.attributes.BetterParaglidersAttributes.RANGE_STAMINA_REDUCTION.get()));

            cir.setReturnValue(Math.max(0, calculatedCost));
        }
        // Otherwise, it's a "real" ranged weapon so don't do anything
    }
}
