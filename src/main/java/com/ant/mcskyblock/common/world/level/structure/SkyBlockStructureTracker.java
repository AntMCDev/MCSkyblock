package com.ant.mcskyblock.common.world.level.structure;

import com.ant.mcskyblock.common.SkyBlock;
import com.ant.mcskyblock.common.config.SkyBlockConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.apache.logging.log4j.Level;

import java.util.HashMap;

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
        enabledMap.put( "minecraft:pillager_outpost",        SkyBlockConfig.STRUCTURES.GEN_PILLAGER_OUTPOST);
        enabledMap.put( "minecraft:mineshaft",               SkyBlockConfig.STRUCTURES.GEN_MINESHAFT);
        enabledMap.put( "minecraft:mineshaft_mesa",          SkyBlockConfig.STRUCTURES.GEN_MINESHAFT_MESA);
        enabledMap.put( "minecraft:mansion",                 SkyBlockConfig.STRUCTURES.GEN_WOODLAND_MANSION);
        enabledMap.put( "minecraft:jungle_pyramid",          SkyBlockConfig.STRUCTURES.GEN_JUNGLE_TEMPLE);
        enabledMap.put( "minecraft:desert_pyramid",          SkyBlockConfig.STRUCTURES.GEN_DESERT_PYRAMID);
        enabledMap.put( "minecraft:igloo",                   SkyBlockConfig.STRUCTURES.GEN_IGLOO);
        enabledMap.put( "minecraft:shipwreck",               SkyBlockConfig.STRUCTURES.GEN_SHIPWRECK);
        enabledMap.put( "minecraft:shipwreck_beached",       SkyBlockConfig.STRUCTURES.GEN_SHIPWRECK_BEACHED);
        enabledMap.put( "minecraft:swamp_hut",               SkyBlockConfig.STRUCTURES.GEN_SWAMP_HUT);
        enabledMap.put( "minecraft:stronghold",              SkyBlockConfig.STRUCTURES.GEN_STRONGHOLD);
        enabledMap.put( "minecraft:monument",                SkyBlockConfig.STRUCTURES.GEN_OCEAN_MONUMENT);
        enabledMap.put( "minecraft:ocean_ruin_cold",         SkyBlockConfig.STRUCTURES.GEN_OCEAN_RUIN_COLD);
        enabledMap.put( "minecraft:ocean_ruin_warm",         SkyBlockConfig.STRUCTURES.GEN_OCEAN_RUIN_WARM);
        enabledMap.put( "minecraft:fortress",                SkyBlockConfig.STRUCTURES.GEN_FORTRESS);
        enabledMap.put( "minecraft:nether_fossil",           SkyBlockConfig.STRUCTURES.GEN_NETHER_FOSSIL);
        enabledMap.put( "minecraft:end_city",                SkyBlockConfig.STRUCTURES.GEN_END_CITY);
        enabledMap.put( "minecraft:buried_treasure",         SkyBlockConfig.STRUCTURES.GEN_BURIED_TREASURE);
        enabledMap.put( "minecraft:bastion_remnant",         SkyBlockConfig.STRUCTURES.GEN_BASTION_REMNANT);
        enabledMap.put( "minecraft:village_plains",          SkyBlockConfig.STRUCTURES.GEN_VILLAGE_PLAINS);
        enabledMap.put( "minecraft:village_desert",          SkyBlockConfig.STRUCTURES.GEN_VILLAGE_DESERT);
        enabledMap.put( "minecraft:village_savanna",         SkyBlockConfig.STRUCTURES.GEN_VILLAGE_SAVANNA);
        enabledMap.put( "minecraft:village_snowy",           SkyBlockConfig.STRUCTURES.GEN_VILLAGE_SNOWY);
        enabledMap.put( "minecraft:village_taiga",           SkyBlockConfig.STRUCTURES.GEN_VILLAGE_TAIGA);
        enabledMap.put( "minecraft:ruined_portal",           SkyBlockConfig.STRUCTURES.GEN_RUINED_PORTAL_STANDARD);
        enabledMap.put( "minecraft:ruined_portal_desert",    SkyBlockConfig.STRUCTURES.GEN_RUINED_PORTAL_DESERT);
        enabledMap.put( "minecraft:ruined_portal_jungle",    SkyBlockConfig.STRUCTURES.GEN_RUINED_PORTAL_JUNGLE);
        enabledMap.put( "minecraft:ruined_portal_swamp",     SkyBlockConfig.STRUCTURES.GEN_RUINED_PORTAL_SWAMP);
        enabledMap.put( "minecraft:ruined_portal_mountain",  SkyBlockConfig.STRUCTURES.GEN_RUINED_PORTAL_MOUNTAIN);
        enabledMap.put( "minecraft:ruined_portal_ocean",     SkyBlockConfig.STRUCTURES.GEN_RUINED_PORTAL_OCEAN);
        enabledMap.put( "minecraft:ruined_portal_nether",    SkyBlockConfig.STRUCTURES.GEN_RUINED_PORTAL_NETHER);
        enabledMap.put( "minecraft:ancient_city",            SkyBlockConfig.STRUCTURES.GEN_ANCIENT_CITY);

        importModdedStructures();
    }

    private static void importModdedStructures() {
        if( SkyBlockConfig.STRUCTURES.CUSTOM_STRUCTURES.length > 0 ) {
            for (String resourceKey : SkyBlockConfig.STRUCTURES.CUSTOM_STRUCTURES) {
                if (!enabledMap.containsKey(resourceKey)) {
                    enabledMap.put(resourceKey, true);
                }
            }
        }
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

    public static Boolean areAllEnabled(HolderSet<Structure> structure){
        boolean ret = true;
        for (int i = 0, j = structure.size(); i < j; ++i) {
            ret = isEnabled(structure.get(i));
            if (!ret) { break; }
        }
        return ret;
    }
}