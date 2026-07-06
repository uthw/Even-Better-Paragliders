package uthw.bpaddon.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BPAddonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue ENABLE_WEAPON_OVERRIDE;
    public static final ForgeConfigSpec.BooleanValue PARRIES_COST_ZERO_STAMINA;

    public static final ForgeConfigSpec.DoubleValue WALL_JUMP_COST;

    public static final ForgeConfigSpec.DoubleValue DODGE_ROLL_COST;

    private static ForgeConfigSpec.DoubleValue DEFAULT_BASE_COST;
    private static ForgeConfigSpec.DoubleValue HAMMER_COST;
    private static ForgeConfigSpec.DoubleValue ANCHOR_COST;
    private static ForgeConfigSpec.DoubleValue CLAYMORE_COST;
    private static ForgeConfigSpec.DoubleValue DOUBLE_AXE_COST;
    private static ForgeConfigSpec.DoubleValue HALBERD_COST;
    private static ForgeConfigSpec.DoubleValue LANCE_COST;
    private static ForgeConfigSpec.DoubleValue GLAIVE_COST;
    private static ForgeConfigSpec.DoubleValue SCYTHE_COST;
    private static ForgeConfigSpec.DoubleValue AXE_COST;
    private static ForgeConfigSpec.DoubleValue MACE_COST;
    private static ForgeConfigSpec.DoubleValue TRIDENT_COST;
    private static ForgeConfigSpec.DoubleValue SPEAR_COST;
    private static ForgeConfigSpec.DoubleValue SWORD_COST;
    private static ForgeConfigSpec.DoubleValue CORAL_BLADE_COST;
    private static ForgeConfigSpec.DoubleValue CUTLASS_COST;
    private static ForgeConfigSpec.DoubleValue TWIN_BLADE_COST;
    private static ForgeConfigSpec.DoubleValue BATTLESTAFF_COST;
    private static ForgeConfigSpec.DoubleValue KATANA_COST;
    private static ForgeConfigSpec.DoubleValue RAPIER_COST;
    private static ForgeConfigSpec.DoubleValue WAND_COST;
    private static ForgeConfigSpec.DoubleValue SICKLE_COST;
    private static ForgeConfigSpec.DoubleValue CLAW_COST;
    private static ForgeConfigSpec.DoubleValue DAGGER_COST;
    private static ForgeConfigSpec.DoubleValue SOUL_KNIFE_COST;
    private static ForgeConfigSpec.DoubleValue FIST_COST;

    private static ForgeConfigSpec.DoubleValue THROWN_WEAPON_COST;

    public static double defaultBaseCost() {
        return (Double)DEFAULT_BASE_COST.get();
    }
    public static double hammerCost() {
        return (Double)HAMMER_COST.get();
    }
    public static double anchorCost() {
        return (Double)ANCHOR_COST.get();
    }
    public static double claymoreCost() {
        return (Double)CLAYMORE_COST.get();
    }
    public static double doubleAxeCost() {
        return (Double)DOUBLE_AXE_COST.get();
    }
    public static double halberdCost() {
        return (Double)HALBERD_COST.get();
    }
    public static double lanceCost() {
        return (Double)LANCE_COST.get();
    }
    public static double glaiveCost() {
        return (Double)GLAIVE_COST.get();
    }
    public static double scytheCost() {
        return (Double)SCYTHE_COST.get();
    }
    public static double axeCost() {
        return (Double)AXE_COST.get();
    }
    public static double maceCost() {
        return (Double)MACE_COST.get();
    }
    public static double tridentCost() {
        return (Double)TRIDENT_COST.get();
    }
    public static double spearCost() {
        return (Double)SPEAR_COST.get();
    }
    public static double swordCost() {
        return (Double)SWORD_COST.get();
    }
    public static double coralBladeCost() {
        return (Double)CORAL_BLADE_COST.get();
    }
    public static double cutlassCost() {
        return (Double)CUTLASS_COST.get();
    }
    public static double twinBladeCost() {
        return (Double)TWIN_BLADE_COST.get();
    }
    public static double battlestaffCost() {
        return (Double)BATTLESTAFF_COST.get();
    }
    public static double katanaCost() {
        return (Double)KATANA_COST.get();
    }
    public static double rapierCost() {
        return (Double)RAPIER_COST.get();
    }
    public static double wandCost() {
        return (Double)WAND_COST.get();
    }
    public static double sickleCost() {
        return (Double)SICKLE_COST.get();
    }
    public static double clawCost() {
        return (Double)CLAW_COST.get();
    }
    public static double daggerCost() {
        return (Double)DAGGER_COST.get();
    }
    public static double soulKnifeCost() {
        return (Double)SOUL_KNIFE_COST.get();
    }
    public static double fistCost() {
        return (Double)FIST_COST.get();
    }

    public static double wallJumpCost() { return (Double)WALL_JUMP_COST.get(); }

    public static double dodgeRollCost() {  return (Double)DODGE_ROLL_COST.get(); }

    public static double thrownWeaponCost() { return (Double)THROWN_WEAPON_COST.get(); }

    static {
        BUILDER.push("General Settings");
        ENABLE_WEAPON_OVERRIDE = BUILDER
                .comment("If true, attack stamina costs for the Better Paragliders mod are calculated by the weapon type values below.",
                        "If false, Better Paragliders's default formula is used instead, which is based on the weapon's attack damage and reach.")
                .define("enable_weapon_override", true);

        PARRIES_COST_ZERO_STAMINA = BUILDER
                .comment("If true, Shield Expansion parries do not cost stamina.")
                .define("parries_cost_zero_stamina", true);

        BUILDER.pop();

        BUILDER.push("Weapon Stamina Costs");
        BUILDER
                .comment("If this feature is enabled, weapon attacks will be calculated by their parent (swing animation type). For example, a wooden sword and netherite sword will both use sword_cost.",
                        "A weapon's parent can be found by looking at fallback_compatibility.json in Better Combat's config, or if explicitly defined, its mod's datapack's weapon_attributes folder.",
                        "If a weapon has a custom parent not listed here, it will use default_base_cost. For these weapons, you can use Better Paragliders's datapack support to set their cost.",
                        "Remember that these costs are affected by the multipliers defined in Better Paragliders's server config.");
        DEFAULT_BASE_COST = BUILDER.defineInRange("default_base_cost", 18.0, 0.0, 999.0);
        HAMMER_COST = BUILDER.defineInRange("hammer_cost", 12.0, 0.0, 999.0);
        ANCHOR_COST = BUILDER.defineInRange("anchor_cost", 12.0, 0.0, 999.0);
        CLAYMORE_COST = BUILDER.defineInRange("claymore_cost", 9.0, 0.0, 999.0);
        DOUBLE_AXE_COST = BUILDER.defineInRange("double_axe_cost", 9.0, 0.0, 999.0);
        HALBERD_COST = BUILDER.defineInRange("halberd_cost", 7.0, 0.0, 999.0);
        LANCE_COST = BUILDER.defineInRange("lance_cost", 7.0, 0.0, 999.0);
        GLAIVE_COST = BUILDER.defineInRange("glaive_cost", 6.5, 0.0, 999.0);
        SCYTHE_COST = BUILDER.defineInRange("scythe_cost", 6.5, 0.0, 999.0);
        AXE_COST = BUILDER.defineInRange("axe_cost", 5.0, 0.0, 999.0);
        MACE_COST = BUILDER.defineInRange("mace_cost", 5.0, 0.0, 999.0);
        TRIDENT_COST = BUILDER.defineInRange("trident_cost", 5.0, 0.0, 999.0);
        SPEAR_COST = BUILDER.defineInRange("spear_cost", 4.0, 0.0, 999.0);
        SWORD_COST = BUILDER.defineInRange("sword_cost", 3.0, 0.0, 999.0);
        CORAL_BLADE_COST = BUILDER.defineInRange("coral_blade_cost", 3.0, 0.0, 999.0);
        CUTLASS_COST = BUILDER.defineInRange("cutlass_cost", 2.5, 0.0, 999.0);
        TWIN_BLADE_COST = BUILDER.defineInRange("twin_blade_cost", 2.5, 0.0, 999.0);
        BATTLESTAFF_COST = BUILDER.defineInRange("battlestaff_cost", 5.5, 0.0, 999.0);
        KATANA_COST = BUILDER.defineInRange("katana_cost", 2.0, 0.0, 999.0);
        RAPIER_COST = BUILDER.defineInRange("rapier_cost", 1.5, 0.0, 999.0);
        WAND_COST = BUILDER.defineInRange("wand_cost", 1.5, 0.0, 999.0);
        SICKLE_COST = BUILDER.defineInRange("sickle_cost", 1.0, 0.0, 999.0);
        CLAW_COST = BUILDER.defineInRange("claw_cost", 1.0, 0.0, 999.0);
        DAGGER_COST = BUILDER.defineInRange("dagger_cost", 0.8, 0.0, 999.0);
        SOUL_KNIFE_COST = BUILDER.defineInRange("soul_knife_cost", 0.8, 0.0, 999.0);
        FIST_COST = BUILDER.defineInRange("fist_cost", 0.5, 0.0, 999.0);

        BUILDER.pop();

        BUILDER.push("Thrown Weapon Cost");
        THROWN_WEAPON_COST = BUILDER
                .comment("The default stamina cost for thrown weapons like tridents while right click is held. Using a value of 0 will disable this integration.")
                .comment("You can override this default using a datapack and defining the thrown weapon as if it was ranged.")
                .defineInRange("thrown_weapon_cost", 8.0, 0.0, 999.0);

        BUILDER.pop();

        BUILDER.push("Wall Jump Cost");
        WALL_JUMP_COST = BUILDER.comment("The stamina cost for performing a wall jump. Using a value of 0 will disable this integration.")
                .defineInRange("wall_jump_cost", 20.0, 0.0, 999.0);

        BUILDER.push("Dodge Roll Cost");
        DODGE_ROLL_COST = BUILDER.comment("The stamina cost for performing a dodge roll using the Combat Roll mod. Using a value of 0 will disable this integration.")
                .comment("If you use this feature, consider lowering the cooldown in Combat Roll's server config so stamina becomes the main limiting factor.")
                .defineInRange("dodge_roll_cost", 30.0, 0.0, 999.0);

        SPEC = BUILDER.build();
    }
}
