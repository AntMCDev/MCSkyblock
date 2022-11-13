package com.ant.mcskyblock.common.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class SkyBlockConfigManager {
    public static SkyBlockConfigs GENERIC;

    public static boolean overworldIsSkyBlock(){return GENERIC.overworldIsSkyBlock; }
    public static boolean applyBiomeDecoration(){ return GENERIC.applyBiomeDecoration; }
    public static boolean generateIslands(){return GENERIC.generateIslands;}
    public static boolean netherIsSkyblock(){return GENERIC.netherIsSkyblock; }
    public static boolean endIsSkyblock(){return GENERIC.endIsSkyblock; }


    public static SkyBlockConfigs.TradingConfigs TRADES;
    public static SkyBlockConfigs.StructureConfigs STRUCTURES;
    public static SkyBlockConfigs.MobDropsConfigs MOB_DROPS;

    public void initialize() {
        AutoConfig.register(SkyBlockConfigs.class, GsonConfigSerializer::new);
        this.GENERIC =  AutoConfig.getConfigHolder(SkyBlockConfigs.class).getConfig();
        this.TRADES = this.GENERIC.tradingConfigs;
        this.STRUCTURES = this.GENERIC.structureConfigs;
        this.MOB_DROPS = this.GENERIC.mobDropsConfigs;
    }

    //////////////////////////////
    // generic settings
    /////////////////////////////
    public static String[] spawnCords(){return GENERIC.spawnCords;}
    public static boolean spawnMainIsland(){return GENERIC.spawnMainIsland;}
    public static int mainIslandRadius(){ return GENERIC.mainIslandRadius;}
    public static boolean stopBatSpawns(){ return  GENERIC.stopBatSpawns; }

    //////////////////////////////
    // structures
    /////////////////////////////
    // overworld
    public static boolean ancientCities(){ return STRUCTURES.GEN_ANCIENT_CITY; }
    public static boolean igloo(){ return STRUCTURES.GEN_IGLOO; }
    public static boolean jungleTemple(){ return STRUCTURES.GEN_JUNGLE_TEMPLE; }
    public static boolean mineshaft(){ return STRUCTURES.GEN_MINESHAFT; }
    public static boolean mineshaftMesa(){ return STRUCTURES.GEN_MINESHAFT_MESA; }
    public static boolean oceanMonument(){ return STRUCTURES.GEN_OCEAN_MONUMENT; }
    public static boolean oceanRuinCold(){ return STRUCTURES.GEN_OCEAN_RUIN_COLD; }
    public static boolean oceanRuinWarm(){ return STRUCTURES.GEN_OCEAN_RUIN_WARM; }
    public static boolean pillagerOutpost(){ return STRUCTURES.GEN_PILLAGER_OUTPOST; }
    public static boolean ruinedPortalDesert(){ return STRUCTURES.GEN_RUINED_PORTAL_DESERT; }
    public static boolean ruinedPortalJungle(){ return STRUCTURES.GEN_RUINED_PORTAL_JUNGLE; }
    public static boolean ruinedPortalMountain(){ return STRUCTURES.GEN_RUINED_PORTAL_MOUNTAIN; }
    public static boolean ruinedPortalOcean(){ return STRUCTURES.GEN_RUINED_PORTAL_OCEAN; }
    public static boolean ruinedPortalStandard(){ return STRUCTURES.GEN_RUINED_PORTAL_STANDARD; }
    public static boolean ruinedPortalSwamp(){ return STRUCTURES.GEN_RUINED_PORTAL_SWAMP; }
    public static boolean witchHut(){ return STRUCTURES.GEN_SWAMP_HUT; }
    public static boolean mansion(){ return STRUCTURES.GEN_WOODLAND_MANSION; }
    public static boolean buiredTreasure(){ return STRUCTURES.GEN_BURIED_TREASURE;}
    public static boolean desertPyramid(){ return STRUCTURES.GEN_DESERT_PYRAMID; }
    public static boolean shipwreck(){ return STRUCTURES.GEN_SHIPWRECK; }
    public static boolean shipwreckBeached(){ return STRUCTURES.GEN_SHIPWRECK_BEACHED; }
    public static boolean stronghold(){ return STRUCTURES.GEN_STRONGHOLD; }
    public static boolean villageDesert(){ return STRUCTURES.GEN_VILLAGE_DESERT; }
    public static boolean villagePlains(){ return STRUCTURES.GEN_VILLAGE_PLAINS; }
    public static boolean villageSavanna(){ return STRUCTURES.GEN_VILLAGE_SAVANNA; }
    public static boolean villageSnowy(){ return STRUCTURES.GEN_VILLAGE_SNOWY; }
    public static boolean villageTiaga(){ return STRUCTURES.GEN_VILLAGE_TAIGA; }
    // nether structures
    public static boolean bastions(){ return STRUCTURES.GEN_BASTION_REMNANT; }
    public static boolean fortress(){ return STRUCTURES.GEN_FORTRESS; }
    public static boolean netherFossil(){ return STRUCTURES.GEN_NETHER_FOSSIL; }
    public static boolean ruinedPortalNether(){ return STRUCTURES.GEN_RUINED_PORTAL_NETHER; }
    // end structures
    public static boolean endCities(){ return STRUCTURES.GEN_END_CITY; }


    public static String[] customStructures(){ return STRUCTURES.CUSTOM_STRUCTURES; }



    /////////////////////////////
    // trades
    /////////////////////////////
    public static boolean wanderingTraderEndPortalFrame(){ return TRADES.WANDERING_TRADER_END_PORTAL_FRAME ;}
    public static boolean wanderingTraderSponge(){ return TRADES.WANDERING_TRADER_SPONGE ;}
    public static boolean wanderingTraderGlowLichen(){ return TRADES.WANDERING_TRADER_GLOW_LICHEN ;}
    public static boolean wanderingTraderSculkCatalyst(){ return TRADES.WANDERING_TRADER_SCULK_CATALYST ;}
    public static boolean wanderingTraderLavaBucket(){ return TRADES.WANDERING_TRADER_LAVA_BUCKET ;}
    public static boolean wanderingTraderGlowBerries(){ return TRADES.WANDERING_TRADER_GLOW_BERRIES ;}
    public static boolean wanderingTraderChorusPlant(){ return TRADES.WANDERING_TRADER_CHORUS_PLANT ;}
    public static boolean wanderingTraderHeartOfTheSea(){ return TRADES.WANDERING_TRADER_HEART_OF_THE_SEA ;}
    public static boolean wanderingTraderDiscOtherside(){ return TRADES.WANDERING_TRADER_DISC_OTHERSIDE ;}
    public static boolean wanderingTraderDiscFive(){ return TRADES.WANDERING_TRADER_DISC_5 ;}
    public static boolean wanderingTraderDiscPigstep(){ return TRADES.WANDERING_TRADER_DISC_PIGSTEP ;}
    public static boolean wanderingTraderRedstone(){ return TRADES.WANDERING_TRADER_REDSTONE ;}
    public static boolean masonDeepslate(){ return TRADES.MASON_DEEPSLATE ;}
    public static boolean masonTuff(){ return TRADES.MASON_TUFF ;}
    public static boolean masonCalcite(){ return TRADES.MASON_CALCITE ;}
    public static boolean masonReinforcedDeepslate(){ return TRADES.MASON_REINFORCED_DEEPSLATE ;}
    public static boolean clericShulkerShell(){ return TRADES.CLERIC_SHULKER_SHELL ;}
    public static boolean clericEchoShard(){ return TRADES.CLERIC_ECHO_SHARD ;}
    /////
    public static boolean piglinAncientDebris(){ return TRADES.PIGLIN_ANCIENT_DEBRIS ;}
    public static boolean piglinNetherrack(){ return TRADES.PIGLIN_NETHERRACK ;}
    public static boolean piglinNylium(){ return TRADES.PIGLIN_NYLIUM ;}
    public static boolean hotvClericBuddingAmethyst(){ return TRADES.HOTV_CLERIC_BUDDING_AMETHYST ;}
    public static boolean hotvClericDiamond(){ return TRADES.HOTV_CLERIC_DIAMOND ;}

    /////////////////////////////
    // mob drops
    /////////////////////////////
    public static boolean witchDropsNetherWart(){ return MOB_DROPS.WITCH_NETHER_WART; }
    public static boolean phantomsDropElytra(){return  MOB_DROPS.PHANTOM_ELYTRA; }
    public static boolean enderDragonDropsHead(){ return MOB_DROPS.ENDER_DRAGON_HEAD; }
//    public static boolean drownDropsGold(){ return MOB_DROPS.DROWNED_GOLD; }

    public static boolean huskDropSand(){ return MOB_DROPS.HUSK_SAND; }

    public static boolean witherDropAncientDebris(){return MOB_DROPS.WITHER_ANCIENT_DEBRIS; }

    public static boolean tropicalFishDropCoral(){ return MOB_DROPS.TROPICAL_FISH_CORAL; }

}
