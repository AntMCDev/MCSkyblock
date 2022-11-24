package com.ant.mcskyblock.common.world.level.structure;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.config.Config;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.apache.logging.log4j.Level;

import java.util.HashMap;

/**
 * [COMMON] HELPER CLASS - This is used to hold the state of whether given structures should generate
 */
public class SkyBlockStructureTracker {

    private static HashMap<String, Boolean> enabledMap = new HashMap<String, Boolean>();

    /**
     *
     */
    private SkyBlockStructureTracker(){}

    /**
     *
     */
    public static void rescan(){
        enabledMap.clear();
        enabledMap.put( "minecraft:pillager_outpost",        Config.INSTANCE.structures.GEN_PILLAGER_OUTPOST);
        enabledMap.put( "minecraft:mineshaft",               Config.INSTANCE.structures.GEN_MINESHAFT);
        enabledMap.put( "minecraft:mineshaft_mesa",          Config.INSTANCE.structures.GEN_MINESHAFT_MESA);
        enabledMap.put( "minecraft:mansion",                 Config.INSTANCE.structures.GEN_WOODLAND_MANSION);
        enabledMap.put( "minecraft:jungle_pyramid",          Config.INSTANCE.structures.GEN_JUNGLE_TEMPLE);
        enabledMap.put( "minecraft:desert_pyramid",          Config.INSTANCE.structures.GEN_DESERT_PYRAMID);
        enabledMap.put( "minecraft:igloo",                   Config.INSTANCE.structures.GEN_IGLOO);
        enabledMap.put( "minecraft:shipwreck",               Config.INSTANCE.structures.GEN_SHIPWRECK);
        enabledMap.put( "minecraft:shipwreck_beached",       Config.INSTANCE.structures.GEN_SHIPWRECK);
        enabledMap.put( "minecraft:swamp_hut",               Config.INSTANCE.structures.GEN_SWAMP_HUT);
        enabledMap.put( "minecraft:stronghold",              Config.INSTANCE.structures.GEN_STRONGHOLD);
        enabledMap.put( "minecraft:monument",                Config.INSTANCE.structures.GEN_OCEAN_MONUMENT);
        enabledMap.put( "minecraft:ocean_ruin_cold",         Config.INSTANCE.structures.GEN_OCEAN_RUIN);
        enabledMap.put( "minecraft:ocean_ruin_warm",         Config.INSTANCE.structures.GEN_OCEAN_RUIN);
        enabledMap.put( "minecraft:fortress",                Config.INSTANCE.structures.GEN_FORTRESS);
        enabledMap.put( "minecraft:nether_fossil",           Config.INSTANCE.structures.GEN_NETHER_FOSSIL);
        enabledMap.put( "minecraft:end_city",                Config.INSTANCE.structures.GEN_END_CITY);
        enabledMap.put( "minecraft:buried_treasure",         Config.INSTANCE.structures.GEN_BURIED_TREASURE);
        enabledMap.put( "minecraft:bastion_remnant",         Config.INSTANCE.structures.GEN_BASTION_REMNANT);
        enabledMap.put( "minecraft:village_plains",          Config.INSTANCE.structures.GEN_VILLAGE);
        enabledMap.put( "minecraft:village_desert",          Config.INSTANCE.structures.GEN_VILLAGE);
        enabledMap.put( "minecraft:village_savanna",         Config.INSTANCE.structures.GEN_VILLAGE);
        enabledMap.put( "minecraft:village_snowy",           Config.INSTANCE.structures.GEN_VILLAGE);
        enabledMap.put( "minecraft:village_taiga",           Config.INSTANCE.structures.GEN_VILLAGE);
        enabledMap.put( "minecraft:ruined_portal",           Config.INSTANCE.structures.GEN_RUINED_PORTAL);
        enabledMap.put( "minecraft:ruined_portal_desert",    Config.INSTANCE.structures.GEN_RUINED_PORTAL);
        enabledMap.put( "minecraft:ruined_portal_jungle",    Config.INSTANCE.structures.GEN_RUINED_PORTAL);
        enabledMap.put( "minecraft:ruined_portal_swamp",     Config.INSTANCE.structures.GEN_RUINED_PORTAL);
        enabledMap.put( "minecraft:ruined_portal_mountain",  Config.INSTANCE.structures.GEN_RUINED_PORTAL);
        enabledMap.put( "minecraft:ruined_portal_ocean",     Config.INSTANCE.structures.GEN_RUINED_PORTAL);
        enabledMap.put( "minecraft:ruined_portal_nether",    Config.INSTANCE.structures.GEN_RUINED_PORTAL);
        enabledMap.put( "minecraft:ancient_city",            Config.INSTANCE.structures.GEN_ANCIENT_CITY);

        importModdedStructures();
    }

    private static void importModdedStructures() {
        if(Config.INSTANCE.structures.CUSTOM_STRUCTURES.length > 0 ) {
            for (String resourceKey : Config.INSTANCE.structures.CUSTOM_STRUCTURES) {
                if (!enabledMap.containsKey(resourceKey)) {
                    enabledMap.put(resourceKey, true);
                }
            }
        }
    }

    public static Boolean isEnabled(ResourceKey<Structure> structure){
        Boolean ret = false;
        String ssKey = structure.location().toString();
        if(enabledMap.containsKey( ssKey )){
            ret = enabledMap.get( ssKey );
        } else  {
            SkyBlock.LOGGER.log(Level.INFO,"Ummmmm ......" + ssKey );
        }
        return ret;
    }

    public static Boolean isEnabled(Holder<Structure> structure){
        Boolean ret = false;
        String ssKey = structure.unwrapKey().map(resourceKey -> resourceKey.location().toString() ).orElse("[unregistered]");
        if(enabledMap.containsKey( ssKey )){
            ret = enabledMap.get( ssKey );
        } else  {
            SkyBlock.LOGGER.log(Level.INFO,"Ummmmm ......" + ssKey );
        }
        return ret;
    }
}