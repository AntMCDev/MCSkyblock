package com.ant.mcskyblock.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

// Code adapted from Vaskii's Botania source
// https://github.com/Vazkii/Botania
public class ConfigHandler {
    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class Common {
        public final ForgeConfigSpec.BooleanValue phantomElytra;
        public final ForgeConfigSpec.BooleanValue witchNetherWart;
        public final ForgeConfigSpec.BooleanValue enderDragonHead;
        public final ForgeConfigSpec.BooleanValue clericShulkerShellTrade;
        public final ForgeConfigSpec.BooleanValue wanderingTraderSponge;
        public final ForgeConfigSpec.BooleanValue wanderingTraderLavaBucket;
        public final ForgeConfigSpec.BooleanValue wanderingTraderChorusFlower;
        public final ForgeConfigSpec.BooleanValue wanderingTraderHeartOfTheSea;
        public final ForgeConfigSpec.BooleanValue wanderingTraderEndPortalFrame;
        public final ForgeConfigSpec.BooleanValue piglinNetherrack;
        public final ForgeConfigSpec.BooleanValue piglinSoulSoil;
        public final ForgeConfigSpec.BooleanValue piglinNylium;
        public final ForgeConfigSpec.BooleanValue piglinFungus;
        public final ForgeConfigSpec.BooleanValue piglinAncientDebris;
        public final ForgeConfigSpec.BooleanValue heroOfTheVillageClericDiamonds;
        public final ForgeConfigSpec.BooleanValue stopBatSpawns;
        public final ForgeConfigSpec.BooleanValue witchHutYZero;
        public final ForgeConfigSpec.BooleanValue voidNether;
        public final ForgeConfigSpec.BooleanValue voidEnd;
        public final ForgeConfigSpec.IntValue islandRadius;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("lootTables");
            phantomElytra = builder.comment("Enabling this gives phantoms a 1% chance to drop an elytra when killed by a player").define("phantomElytra", true);
            witchNetherWart = builder.comment("Enabling this gives witches a chance to drop nether wart").define("witchNetherWart", true);
            enderDragonHead = builder.comment("Enabling this makes the ender dragon drop its head when killed by a player").define("enderDragonHead", true);
            builder.pop();

            builder.push("trades");
            clericShulkerShellTrade = builder.comment("Enabling this will give cleric villagers a chance to trade 2 shulker shells for 20+ emeralds").define("clericShulkerShellTrade", true);
            wanderingTraderSponge = builder.comment("Enabling this will give wandering traders a generic trade of a sponge for 3 emeralds").define("wanderingTraderSponge", true);
            wanderingTraderLavaBucket = builder.comment("Enabling this will give wandering traders a rare trade of a lava bucket for 5 emeralds").define("wanderingTraderLavaBucket", true);
            wanderingTraderChorusFlower = builder.comment("Enabling this will give wandering traders a rare trade of a chorus flower for 6 emeralds").define("wanderingTraderChorusPlant", true);
            wanderingTraderHeartOfTheSea = builder.comment("Enabling this will give wandering traders a rare trade of a heart of the sea for 10 emeralds").define("wanderingTraderHeartOfTheSea", true);
            wanderingTraderEndPortalFrame = builder.comment("Enabling this will give wandering traders a rare trade of an end portal frame for 50 emeralds").define("wanderingTraderEndPortalFrame", true);
            builder.pop();

            builder.push("barters");
            piglinNetherrack = builder.comment("Enabling this will give piglins a high chance to drop 8-16 netherrack").define("piglinNetherrack", true);
            piglinSoulSoil = builder.comment("Enabling this will give piglins a moderate chance to drop an 4-8 soul soil").define("piglinSoulSoil", true);
            piglinNylium = builder.comment("Enabling this will give piglins a moderate chance to drop 4-8 nylium").define("piglinNylium", true);
            piglinFungus = builder.comment("Enabling this will give piglins a low chance to drop 1-2 fungi").define("piglinFungus", true);
            piglinAncientDebris = builder.comment("Enabling this will give piglins a very low chance to drop an ancient debris").define("piglinAncientDebris", true);
            builder.pop();

            builder.push("rewards");
            heroOfTheVillageClericDiamonds = builder.comment("Enabling this will give Cleric villagers a chance to give a diamond when the player has the Hero of the Village effect").define("heroOfTheVillageClericDiamonds", true);
            builder.pop();

            builder.push("spawns");
            stopBatSpawns = builder.comment("Enabling this will stop bats from spawning").define("stopBatSpawns", true);
            witchHutYZero = builder.comment("Enabling this will generate witch huts at Y:0 instead of Y:64").define("witchHutYZero", false);
            builder.pop();

            builder.push("dimensions");
            voidNether = builder.comment("Enabling this will generate a void nether").define("voidNether", true);
            voidEnd = builder.comment("Enabling this will generate a void end").define("voidEnd", true);
            islandRadius = builder.comment("This defines the radius of the starting skyblock island").defineInRange("islandRadius", 1, 1, 10);
            builder.pop();
        }
    }
}
