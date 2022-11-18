package com.ant.mcskyblock.common.world.level.structure;

import com.ant.mcskyblock.common.MCSkyBlock;
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
        enabledMap.put( "minecraft:pillager_outpost",        SkyBlockConfig.Structures.GEN_PILLAGER_OUTPOST);
        enabledMap.put( "minecraft:mineshaft",               SkyBlockConfig.Structures.GEN_MINESHAFT);
        enabledMap.put( "minecraft:mineshaft_mesa",          SkyBlockConfig.Structures.GEN_MINESHAFT_MESA);
        enabledMap.put( "minecraft:mansion",                 SkyBlockConfig.Structures.GEN_WOODLAND_MANSION);
        enabledMap.put( "minecraft:jungle_pyramid",          SkyBlockConfig.Structures.GEN_JUNGLE_TEMPLE);
        enabledMap.put( "minecraft:desert_pyramid",          SkyBlockConfig.Structures.GEN_DESERT_PYRAMID);
        enabledMap.put( "minecraft:igloo",                   SkyBlockConfig.Structures.GEN_IGLOO);
        enabledMap.put( "minecraft:shipwreck",               SkyBlockConfig.Structures.GEN_SHIPWRECK);
        enabledMap.put( "minecraft:shipwreck_beached",       SkyBlockConfig.Structures.GEN_SHIPWRECK_BEACHED);
        enabledMap.put( "minecraft:swamp_hut",               SkyBlockConfig.Structures.GEN_SWAMP_HUT);
        enabledMap.put( "minecraft:stronghold",              SkyBlockConfig.Structures.GEN_STRONGHOLD);
        enabledMap.put( "minecraft:monument",                SkyBlockConfig.Structures.GEN_OCEAN_MONUMENT);
        enabledMap.put( "minecraft:ocean_ruin_cold",         SkyBlockConfig.Structures.GEN_OCEAN_RUIN_COLD);
        enabledMap.put( "minecraft:ocean_ruin_warm",         SkyBlockConfig.Structures.GEN_OCEAN_RUIN_WARM);
        enabledMap.put( "minecraft:fortress",                SkyBlockConfig.Structures.GEN_FORTRESS);
        enabledMap.put( "minecraft:nether_fossil",           SkyBlockConfig.Structures.GEN_NETHER_FOSSIL);
        enabledMap.put( "minecraft:end_city",                SkyBlockConfig.Structures.GEN_END_CITY);
        enabledMap.put( "minecraft:buried_treasure",         SkyBlockConfig.Structures.GEN_BURIED_TREASURE);
        enabledMap.put( "minecraft:bastion_remnant",         SkyBlockConfig.Structures.GEN_BASTION_REMNANT);
        enabledMap.put( "minecraft:village_plains",          SkyBlockConfig.Structures.GEN_VILLAGE_PLAINS);
        enabledMap.put( "minecraft:village_desert",          SkyBlockConfig.Structures.GEN_VILLAGE_DESERT);
        enabledMap.put( "minecraft:village_savanna",         SkyBlockConfig.Structures.GEN_VILLAGE_SAVANNA);
        enabledMap.put( "minecraft:village_snowy",           SkyBlockConfig.Structures.GEN_VILLAGE_SNOWY);
        enabledMap.put( "minecraft:village_taiga",           SkyBlockConfig.Structures.GEN_VILLAGE_TAIGA);
        enabledMap.put( "minecraft:ruined_portal",           SkyBlockConfig.Structures.GEN_RUINED_PORTAL_STANDARD);
        enabledMap.put( "minecraft:ruined_portal_desert",    SkyBlockConfig.Structures.GEN_RUINED_PORTAL_DESERT);
        enabledMap.put( "minecraft:ruined_portal_jungle",    SkyBlockConfig.Structures.GEN_RUINED_PORTAL_JUNGLE);
        enabledMap.put( "minecraft:ruined_portal_swamp",     SkyBlockConfig.Structures.GEN_RUINED_PORTAL_SWAMP);
        enabledMap.put( "minecraft:ruined_portal_mountain",  SkyBlockConfig.Structures.GEN_RUINED_PORTAL_MOUNTAIN);
        enabledMap.put( "minecraft:ruined_portal_ocean",     SkyBlockConfig.Structures.GEN_RUINED_PORTAL_OCEAN);
        enabledMap.put( "minecraft:ruined_portal_nether",    SkyBlockConfig.Structures.GEN_RUINED_PORTAL_NETHER);
        enabledMap.put( "minecraft:ancient_city",            SkyBlockConfig.Structures.GEN_ANCIENT_CITY);

        importModdedStructures();
    }

    private static void importModdedStructures() {
        if( SkyBlockConfig.Structures.CUSTOM_STRUCTURES.length > 0 ) {
            for (String resourceKey : SkyBlockConfig.Structures.CUSTOM_STRUCTURES) {
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
            MCSkyBlock.LOGGER.log(Level.INFO,"Ummmmm ......" + ssKey );
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