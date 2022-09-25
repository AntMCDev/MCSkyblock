package com.ant.mcskyblock.config;

import com.ant.mcskyblock.MCSkyBlock;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ConfigHandler {
    public static class Common {
        private static final String CONFIG_DIR = "config/";
        private static final String MOD_CONFIG_DIR = "mcskyblock-common.toml";

        private static final String LOOT_TABLES_TABLE = "lootTables";
        private static final String TRADES_TABLE = "trades";
        private static final String SPAWNS_TABLE = "spawns";
        private static final String BARTERS_TABLE = "barters";
        private static final String REWARDS_TABLE = "rewards";
        private static final String DIMENSIONS_TABLE = "dimensions";

        // Loot Table Config
        private static final String LOOT_TABLES_WITCH_NETHER_WART_KEY = "witchNetherWart";
        private static final String LOOT_TABLES_PHANTOM_ELYTRA_KEY = "phantomElytra";
        private static final String LOOT_TABLES_ENDER_DRAGON_HEAD_KEY = "enderDragonHead";
        private static final String LOOT_TABLES_DROWNED_GOLD_KEY = "drownedGold";
        public static final Boolean WITCH_NETHER_WART;
        public static final Boolean PHANTOM_ELYTRA;
        public static final Boolean ENDER_DRAGON_HEAD;
        public static final Boolean DROWNED_GOLD;

        // Trades Config
        private static final String TRADES_WANDERING_TRADER_END_PORTAL_FRAME_KEY = "wanderingTraderEndPortalFrame";
        private static final String TRADES_WANDERING_TRADER_SPONGE_KEY = "wanderingTraderSponge";
        private static final String TRADES_WANDERING_TRADER_GLOW_LICHEN_KEY = "wanderingTraderGlowLichen";
        private static final String TRADES_WANDERING_TRADER_SCULK_CATALYST_KEY = "wanderingTraderSculkCatalyst";
        private static final String TRADES_WANDERING_TRADER_LAVA_BUCKET_KEY = "wanderingTraderLavaBucket";
        private static final String TRADES_WANDERING_TRADER_GLOW_BERRIES_KEY = "wanderingTraderGlowBerries";
        private static final String TRADES_WANDERING_TRADER_CHORUS_PLANT_KEY = "wanderingTraderChorusPlant";
        private static final String TRADES_WANDERING_TRADER_HEART_OF_THE_SEA_KEY = "wanderingTraderHeartOfTheSea";
        private static final String TRADES_WANDERING_TRADER_DISC_OTHERSIDE_KEY = "wanderingTraderDiscOtherside";
        private static final String TRADES_WANDERING_TRADER_DISC_5_KEY = "wanderingTraderDisc5";
        private static final String TRADES_WANDERING_TRADER_DISC_PIGSTEP_KEY = "wanderingTraderDiscPigstep";
        private static final String TRADES_WANDERING_TRADER_REDSTONE_KEY = "wanderingTraderRedstone";
        private static final String TRADES_MASON_DEEPSLATE_KEY = "masonDeepslateTrade";
        private static final String TRADES_MASON_TUFF_KEY = "masonTuffTrade";
        private static final String TRADES_MASON_CALCITE_KEY = "masonCalciteTrade";
        private static final String TRADES_MASON_REINFORCED_DEEPSLATE_KEY = "masonReinforcedDeepslateTrade";
        private static final String TRADES_CLERIC_SHULKER_SHELL_KEY = "clericShulkerShellTrade";
        private static final String TRADES_CLERIC_ECHO_SHARD_KEY = "clericEchoShardTrade";
        public static final Boolean WANDERING_TRADER_END_PORTAL_FRAME;
        public static final Boolean WANDERING_TRADER_SPONGE;
        public static final Boolean WANDERING_TRADER_GLOW_LICHEN;
        public static final Boolean WANDERING_TRADER_SCULK_CATALYST;
        public static final Boolean WANDERING_TRADER_LAVA_BUCKET;
        public static final Boolean WANDERING_TRADER_GLOW_BERRIES;
        public static final Boolean WANDERING_TRADER_CHORUS_PLANT;
        public static final Boolean WANDERING_TRADER_HEART_OF_THE_SEA;
        public static final Boolean WANDERING_TRADER_DISC_OTHERSIDE;
        public static final Boolean WANDERING_TRADER_DISC_5;
        public static final Boolean WANDERING_TRADER_DISC_PIGSTEP;
        public static final Boolean WANDERING_TRADER_REDSTONE;
        public static final Boolean MASON_DEEPSLATE;
        public static final Boolean MASON_TUFF;
        public static final Boolean MASON_CALCITE;
        public static final Boolean MASON_REINFORCED_DEEPSLATE;
        public static final Boolean CLERIC_SHULKER_SHELL;
        public static final Boolean CLERIC_ECHO_SHARD;

        // Spawns Config
        private static final String SPAWNS_STOP_BAT_SPAWNS_KEY = "stopBatSpawns";
        private static final String SPAWNS_WITCH_HUT_Y_ZERO_KEY = "witchHutYZero";
        public static final Boolean STOP_BAT_SPAWNS;
        public static final Boolean WITCH_HUT_Y_ZERO;

        // Barters Config
        private static final String BARTERING_PIGLIN_ANCIENT_DEBRIS_KEY = "piglinAncientDebris";
        private static final String BARTERING_PIGLIN_NETHERRACK_KEY = "piglinNetherrack";
        private static final String BARTERING_PIGLIN_NYLIUM_KEY = "piglinNylium";
        public static final Boolean PIGLIN_ANCIENT_DEBRIS;
        public static final Boolean PIGLIN_NETHERRACK;
        public static final Boolean PIGLIN_NYLIUM;

        // Rewards Config
        private static final String HERO_OF_THE_VILLAGE_CLERIC_BUDDING_AMETHYST_KEY = "heroOfTheVillageClericBuddingAmethyst";
        private static final String HERO_OF_THE_VILLAGE_CLERIC_DIAMONDS_KEY = "heroOfTheVillageClericDiamonds";
        public static final Boolean HOTV_CLERIC_BUDDING_AMETHYST;
        public static final Boolean HOTV_CLERIC_DIAMOND;

        // Dimensions Config
        private static final String DIMENSIONS_ISLAND_RADIUS_KEY = "islandRadius";
        private static final String DIMENSIONS_SPAWN_ISLAND_KEY = "spawnIsland";
        private static final String DIMENSIONS_SPAWN_POSITION_KEY = "spawnPosition";
        private static final String DIMENSIONS_VOID_END_KEY = "voidEnd";
        private static final String DIMENSIONS_VOID_NETHER_KEY = "voidNether";
        private static final String DIMENSIONS_GENERATE_FORTRESS_KEY = "generateFortress";
        private static final String DIMENSIONS_GENERATE_BASTION_KEY = "generateBastion";
        private static final String DIMENSIONS_GENERATE_ANCIENT_CITY_KEY = "generateAncientCity";
        private static final String DIMENSIONS_GENERATE_PILLAGER_OUTPOST_KEY = "generatePillagerOutpost";
        private static final String DIMENSIONS_GENERATE_VILLAGE_KEY = "generateVillage";
        private static final String DIMENSIONS_GENERATE_STRONGHOLD_KEY = "generateStronghold";
        private static final String DIMENSIONS_GENERATE_OCEAN_MONUMENT_KEY = "generateOceanMonument";
        private static final String DIMENSIONS_GENERATE_SWAMP_HUT_KEY = "generateSwampHut";
        private static final String DIMENSIONS_GENERATE_MISC_STRUCTURES_KEY = "generateMiscStructures";
        private static final String DIMENSIONS_GENERATE_END_GATEWAYS_KEY = "generateEndGateways";
        private static final String DIMENSIONS_ADJUST_STRUCTURE_Y_KEY = "adjustStructureY";
        public static final Boolean SPAWN_ISLAND;
        public static final String SPAWN_POSITION;
        public static final Long ISLAND_RADIUS;
        public static final Boolean VOID_END;
        public static final Boolean VOID_NETHER;
        public static final Boolean GENERATE_FORTRESS;
        public static final Boolean GENERATE_BASTION;
        public static final Boolean GENERATE_ANCIENT_CITY;
        public static final Boolean GENERATE_PILLAGER_OUTPOST;
        public static final Boolean GENERATE_VILLAGE;
        public static final Boolean GENERATE_STRONGHOLD;
        public static final Boolean GENERATE_OCEAN_MONUMENT;
        public static final Boolean GENERATE_SWAMP_HUT;
        public static final Boolean GENERATE_MISC_STRUCTURES;
        public static final Boolean GENERATE_END_GATEWAYS;
        public static final Integer ADJUST_STRUCTURE_Y;

        static {
            MCSkyBlock.LOGGER.info("Loading config file");

            File configDir = new File(CONFIG_DIR);
            if (!configDir.exists()) {
                boolean success = configDir.mkdir();
                if (!success) {
                    throw new RuntimeException("Error creating the config directory");
                }
            }

            Toml toml = new Toml();
            File f = new File(CONFIG_DIR + MOD_CONFIG_DIR);
            boolean change = false;
            Map<String, Object> map = new HashMap<>();

            if (f.exists()) {
                toml.read(f);
                map = toml.toMap();
            } else {
                try {
                    boolean success = f.createNewFile();
                    change = true;
                    if (!success) {
                        throw new RuntimeException("Error creating the " + MOD_CONFIG_DIR + " config file");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }

            // Loot Table Config Initialisation
            change = populateTOMLTable(LOOT_TABLES_TABLE, map) || change;
            Map<String, Object> lootTableConfig = (HashMap<String, Object>)map.get(LOOT_TABLES_TABLE);
            change = populateTOMLProperty(LOOT_TABLES_WITCH_NETHER_WART_KEY, lootTableConfig, true) || change;
            change = populateTOMLProperty(LOOT_TABLES_PHANTOM_ELYTRA_KEY, lootTableConfig, true) || change;
            change = populateTOMLProperty(LOOT_TABLES_ENDER_DRAGON_HEAD_KEY, lootTableConfig, true) || change;
            change = populateTOMLProperty(LOOT_TABLES_DROWNED_GOLD_KEY, lootTableConfig, true) || change;
            WITCH_NETHER_WART = (Boolean)lootTableConfig.get(LOOT_TABLES_WITCH_NETHER_WART_KEY);
            PHANTOM_ELYTRA = (Boolean)lootTableConfig.get(LOOT_TABLES_PHANTOM_ELYTRA_KEY);
            ENDER_DRAGON_HEAD = (Boolean)lootTableConfig.get(LOOT_TABLES_ENDER_DRAGON_HEAD_KEY);
            DROWNED_GOLD = (Boolean)lootTableConfig.get(LOOT_TABLES_DROWNED_GOLD_KEY);

            // Trades Config Initialisation
            change = populateTOMLTable(TRADES_TABLE, map) || change;
            Map<String, Object> tradesConfig = (HashMap<String, Object>)map.get(TRADES_TABLE);
            change = populateTOMLProperty(TRADES_WANDERING_TRADER_END_PORTAL_FRAME_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_WANDERING_TRADER_SPONGE_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_WANDERING_TRADER_GLOW_LICHEN_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_WANDERING_TRADER_SCULK_CATALYST_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_WANDERING_TRADER_LAVA_BUCKET_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_WANDERING_TRADER_GLOW_BERRIES_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_WANDERING_TRADER_CHORUS_PLANT_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_WANDERING_TRADER_HEART_OF_THE_SEA_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_WANDERING_TRADER_DISC_OTHERSIDE_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_WANDERING_TRADER_DISC_5_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_WANDERING_TRADER_DISC_PIGSTEP_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_WANDERING_TRADER_REDSTONE_KEY, tradesConfig, false) || change;
            change = populateTOMLProperty(TRADES_MASON_DEEPSLATE_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_MASON_TUFF_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_MASON_CALCITE_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_MASON_REINFORCED_DEEPSLATE_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_CLERIC_SHULKER_SHELL_KEY, tradesConfig, true) || change;
            change = populateTOMLProperty(TRADES_CLERIC_ECHO_SHARD_KEY, tradesConfig, true) || change;
            WANDERING_TRADER_END_PORTAL_FRAME = (Boolean)tradesConfig.get(TRADES_WANDERING_TRADER_END_PORTAL_FRAME_KEY);
            WANDERING_TRADER_SPONGE = (Boolean)tradesConfig.get(TRADES_WANDERING_TRADER_SPONGE_KEY);
            WANDERING_TRADER_GLOW_LICHEN = (Boolean)tradesConfig.get(TRADES_WANDERING_TRADER_GLOW_LICHEN_KEY);
            WANDERING_TRADER_SCULK_CATALYST = (Boolean)tradesConfig.get(TRADES_WANDERING_TRADER_SCULK_CATALYST_KEY);
            WANDERING_TRADER_LAVA_BUCKET = (Boolean)tradesConfig.get(TRADES_WANDERING_TRADER_LAVA_BUCKET_KEY);
            WANDERING_TRADER_GLOW_BERRIES = (Boolean)tradesConfig.get(TRADES_WANDERING_TRADER_GLOW_BERRIES_KEY);
            WANDERING_TRADER_CHORUS_PLANT = (Boolean)tradesConfig.get(TRADES_WANDERING_TRADER_CHORUS_PLANT_KEY);
            WANDERING_TRADER_HEART_OF_THE_SEA = (Boolean)tradesConfig.get(TRADES_WANDERING_TRADER_HEART_OF_THE_SEA_KEY);
            WANDERING_TRADER_DISC_OTHERSIDE = (Boolean)tradesConfig.get(TRADES_WANDERING_TRADER_DISC_OTHERSIDE_KEY);
            WANDERING_TRADER_DISC_5 = (Boolean)tradesConfig.get(TRADES_WANDERING_TRADER_DISC_5_KEY);
            WANDERING_TRADER_DISC_PIGSTEP = (Boolean)tradesConfig.get(TRADES_WANDERING_TRADER_DISC_PIGSTEP_KEY);
            WANDERING_TRADER_REDSTONE = (Boolean)tradesConfig.get(TRADES_WANDERING_TRADER_REDSTONE_KEY);
            MASON_DEEPSLATE = (Boolean)tradesConfig.get(TRADES_MASON_DEEPSLATE_KEY);
            MASON_TUFF = (Boolean)tradesConfig.get(TRADES_MASON_TUFF_KEY);
            MASON_CALCITE = (Boolean)tradesConfig.get(TRADES_MASON_CALCITE_KEY);
            MASON_REINFORCED_DEEPSLATE = (Boolean)tradesConfig.get(TRADES_MASON_REINFORCED_DEEPSLATE_KEY);
            CLERIC_SHULKER_SHELL = (Boolean)tradesConfig.get(TRADES_CLERIC_SHULKER_SHELL_KEY);
            CLERIC_ECHO_SHARD = (Boolean)tradesConfig.get(TRADES_CLERIC_ECHO_SHARD_KEY);

            // Spawns Config Initialisation
            change = populateTOMLTable(SPAWNS_TABLE, map) || change;
            Map<String, Object> spawnsConfig = (HashMap<String, Object>)map.get(SPAWNS_TABLE);
            change = populateTOMLProperty(SPAWNS_STOP_BAT_SPAWNS_KEY, spawnsConfig, true) || change;
            change = populateTOMLProperty(SPAWNS_WITCH_HUT_Y_ZERO_KEY, spawnsConfig, false) || change;
            STOP_BAT_SPAWNS = (Boolean)spawnsConfig.get(SPAWNS_STOP_BAT_SPAWNS_KEY);
            WITCH_HUT_Y_ZERO = (Boolean)spawnsConfig.get(SPAWNS_WITCH_HUT_Y_ZERO_KEY);

            // Barters Config Initialisation
            change = populateTOMLTable(BARTERS_TABLE, map) || change;
            Map<String, Object> bartersConfig = (HashMap<String, Object>)map.get(BARTERS_TABLE);
            change = populateTOMLProperty(BARTERING_PIGLIN_ANCIENT_DEBRIS_KEY, bartersConfig, true) || change;
            change = populateTOMLProperty(BARTERING_PIGLIN_NETHERRACK_KEY, bartersConfig, true) || change;
            change = populateTOMLProperty(BARTERING_PIGLIN_NYLIUM_KEY, bartersConfig, true) || change;
            PIGLIN_ANCIENT_DEBRIS = (Boolean)bartersConfig.get(BARTERING_PIGLIN_ANCIENT_DEBRIS_KEY);
            PIGLIN_NETHERRACK = (Boolean)bartersConfig.get(BARTERING_PIGLIN_NETHERRACK_KEY);
            PIGLIN_NYLIUM = (Boolean)bartersConfig.get(BARTERING_PIGLIN_NYLIUM_KEY);

            // Rewards Config Initialisation
            change = populateTOMLTable(REWARDS_TABLE, map) || change;
            Map<String, Object> rewardsConfig = (HashMap<String, Object>)map.get(REWARDS_TABLE);
            change = populateTOMLProperty(HERO_OF_THE_VILLAGE_CLERIC_BUDDING_AMETHYST_KEY, rewardsConfig, true) || change;
            change = populateTOMLProperty(HERO_OF_THE_VILLAGE_CLERIC_DIAMONDS_KEY, rewardsConfig, true) || change;
            HOTV_CLERIC_BUDDING_AMETHYST = (Boolean)rewardsConfig.get(HERO_OF_THE_VILLAGE_CLERIC_BUDDING_AMETHYST_KEY);
            HOTV_CLERIC_DIAMOND = (Boolean)rewardsConfig.get(HERO_OF_THE_VILLAGE_CLERIC_DIAMONDS_KEY);

            // Dimensions Config Initialisation
            change = populateTOMLTable(DIMENSIONS_TABLE, map) || change;
            Map<String, Object> dimensionsConfig = (HashMap<String, Object>)map.get(DIMENSIONS_TABLE);
            change = populateTOMLProperty(DIMENSIONS_ISLAND_RADIUS_KEY, dimensionsConfig, 1L) || change;
            change = populateTOMLProperty(DIMENSIONS_SPAWN_ISLAND_KEY, dimensionsConfig, true) || change;
            change = populateTOMLProperty(DIMENSIONS_SPAWN_POSITION_KEY, dimensionsConfig, "0,64,0") || change;
            change = populateTOMLProperty(DIMENSIONS_VOID_END_KEY, dimensionsConfig, true) || change;
            change = populateTOMLProperty(DIMENSIONS_VOID_NETHER_KEY, dimensionsConfig, true) || change;
            change = populateTOMLProperty(DIMENSIONS_GENERATE_FORTRESS_KEY, dimensionsConfig, false) || change;
            change = populateTOMLProperty(DIMENSIONS_GENERATE_BASTION_KEY, dimensionsConfig, false) || change;
            change = populateTOMLProperty(DIMENSIONS_GENERATE_ANCIENT_CITY_KEY, dimensionsConfig, false) || change;
            change = populateTOMLProperty(DIMENSIONS_GENERATE_PILLAGER_OUTPOST_KEY, dimensionsConfig, false) || change;
            change = populateTOMLProperty(DIMENSIONS_GENERATE_VILLAGE_KEY, dimensionsConfig, false) || change;
            change = populateTOMLProperty(DIMENSIONS_GENERATE_STRONGHOLD_KEY, dimensionsConfig, false) || change;
            change = populateTOMLProperty(DIMENSIONS_GENERATE_OCEAN_MONUMENT_KEY, dimensionsConfig, false) || change;
            change = populateTOMLProperty(DIMENSIONS_GENERATE_SWAMP_HUT_KEY, dimensionsConfig, false) || change;
            change = populateTOMLProperty(DIMENSIONS_GENERATE_MISC_STRUCTURES_KEY, dimensionsConfig, false) || change;
            change = populateTOMLProperty(DIMENSIONS_GENERATE_END_GATEWAYS_KEY, dimensionsConfig, false) || change;
            change = populateTOMLProperty(DIMENSIONS_ADJUST_STRUCTURE_Y_KEY, dimensionsConfig, 0L) || change;
            ISLAND_RADIUS = (Long)dimensionsConfig.get(DIMENSIONS_ISLAND_RADIUS_KEY);
            SPAWN_ISLAND = (Boolean)dimensionsConfig.get(DIMENSIONS_SPAWN_ISLAND_KEY);
            SPAWN_POSITION = dimensionsConfig.get(DIMENSIONS_SPAWN_POSITION_KEY).toString();
            VOID_END = (Boolean)dimensionsConfig.get(DIMENSIONS_VOID_END_KEY);
            VOID_NETHER = (Boolean)dimensionsConfig.get(DIMENSIONS_VOID_NETHER_KEY);
            GENERATE_FORTRESS = (Boolean)dimensionsConfig.get(DIMENSIONS_GENERATE_FORTRESS_KEY);
            GENERATE_BASTION = (Boolean)dimensionsConfig.get(DIMENSIONS_GENERATE_BASTION_KEY);
            GENERATE_ANCIENT_CITY = (Boolean)dimensionsConfig.get(DIMENSIONS_GENERATE_ANCIENT_CITY_KEY);
            GENERATE_PILLAGER_OUTPOST = (Boolean)dimensionsConfig.get(DIMENSIONS_GENERATE_PILLAGER_OUTPOST_KEY);
            GENERATE_VILLAGE = (Boolean)dimensionsConfig.get(DIMENSIONS_GENERATE_VILLAGE_KEY);
            GENERATE_STRONGHOLD = (Boolean)dimensionsConfig.get(DIMENSIONS_GENERATE_STRONGHOLD_KEY);
            GENERATE_OCEAN_MONUMENT = (Boolean)dimensionsConfig.get(DIMENSIONS_GENERATE_OCEAN_MONUMENT_KEY);
            GENERATE_SWAMP_HUT = (Boolean)dimensionsConfig.get(DIMENSIONS_GENERATE_SWAMP_HUT_KEY);
            GENERATE_MISC_STRUCTURES = (Boolean)dimensionsConfig.get(DIMENSIONS_GENERATE_MISC_STRUCTURES_KEY);
            GENERATE_END_GATEWAYS = (Boolean)dimensionsConfig.get(DIMENSIONS_GENERATE_END_GATEWAYS_KEY);
            ADJUST_STRUCTURE_Y = Math.toIntExact((Long)dimensionsConfig.get(DIMENSIONS_ADJUST_STRUCTURE_Y_KEY));

            if (change) {
                TomlWriter writer = new TomlWriter.Builder()
                        .indentValuesBy(0)
                        .indentTablesBy(4)
                        .padArrayDelimitersBy(1)
                        .build();

                try {
                    writer.write(map, f);
                } catch (IOException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }
        }

        private static boolean populateTOMLTable(String tableName, Map<String, Object> map) {
            if (!map.containsKey(tableName)) {
                if (map.containsKey("\"" + tableName + "\"")) {
                    map.put(tableName,  (HashMap<String, Object>)map.get("\"" + tableName + "\""));
                    map.remove("\"" + tableName + "\"");
                } else {
                    map.put(tableName, new HashMap<String, Object>());
                }
                return true;
            }
            return false;
        }

        private static boolean populateTOMLProperty(String propertyName, Map<String, Object> map, Object defaultValue) {
            if (!map.containsKey(propertyName)) {
                map.put(propertyName, defaultValue);
                return true;
            }
            return false;
        }
    }
}
