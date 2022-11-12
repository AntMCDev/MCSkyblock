package com.ant.mcskyblock.structurecheck;

// On start up we check our options and
// inject

import com.ant.mcskyblock.common.MCSkyBlock;
import com.ant.mcskyblock.common.config.SkyBlockConfigManager;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.apache.logging.log4j.Level;

import java.util.HashMap;

// NON FABRIC

public class StructureTracker{

    private static HashMap<String, Boolean> enabledMap = new HashMap<String, Boolean>();
    public StructureTracker(){
    }
    public static void rescan(){
        enabledMap.clear();
        enabledMap.put( "minecraft:pillager_outpost",        SkyBlockConfigManager.pillagerOutpost() );
        enabledMap.put( "minecraft:mineshaft",               SkyBlockConfigManager.mineshaft() );
        enabledMap.put( "minecraft:mineshaft_mesa",          SkyBlockConfigManager.mineshaftMesa() );
        enabledMap.put( "minecraft:mansion",                 SkyBlockConfigManager.mansion() );
        enabledMap.put( "minecraft:jungle_pyramid",          SkyBlockConfigManager.jungleTemple() );
        enabledMap.put( "minecraft:desert_pyramid",          SkyBlockConfigManager.desertPyramid() );
        enabledMap.put( "minecraft:igloo",                   SkyBlockConfigManager.igloo() );
        enabledMap.put( "minecraft:shipwreck",               SkyBlockConfigManager.shipwreck() );
        enabledMap.put( "minecraft:shipwreck_beached",       SkyBlockConfigManager.shipwreckBeached() );
        enabledMap.put( "minecraft:swamp_hut",               SkyBlockConfigManager.witchHut() );
        enabledMap.put( "minecraft:stronghold",              SkyBlockConfigManager.stronghold() );
        enabledMap.put( "minecraft:monument",                SkyBlockConfigManager.oceanMonument() );
        enabledMap.put( "minecraft:ocean_ruin_cold",         SkyBlockConfigManager.oceanRuinCold() );
        enabledMap.put( "minecraft:ocean_ruin_warm",         SkyBlockConfigManager.oceanRuinWarm() );
        enabledMap.put( "minecraft:fortress",                SkyBlockConfigManager.fortress() );
        enabledMap.put( "minecraft:nether_fossil",           SkyBlockConfigManager.netherFossil() );
        enabledMap.put( "minecraft:end_city",                SkyBlockConfigManager.endCities() );
        enabledMap.put( "minecraft:buried_treasure",         SkyBlockConfigManager.buiredTreasure() );
        enabledMap.put( "minecraft:bastion_remnant",         SkyBlockConfigManager.bastions() );
        enabledMap.put( "minecraft:village_plains",          SkyBlockConfigManager.villagePlains() );
        enabledMap.put( "minecraft:village_desert",          SkyBlockConfigManager.villageDesert() );
        enabledMap.put( "minecraft:village_savanna",         SkyBlockConfigManager.villageSavanna() );
        enabledMap.put( "minecraft:village_snowy",           SkyBlockConfigManager.villageSnowy() );
        enabledMap.put( "minecraft:village_taiga",           SkyBlockConfigManager.villageTiaga() );
        enabledMap.put( "minecraft:ruined_portal",           SkyBlockConfigManager.ruinedPortalStandard() );
        enabledMap.put( "minecraft:ruined_portal_desert",    SkyBlockConfigManager.ruinedPortalDesert() );
        enabledMap.put( "minecraft:ruined_portal_jungle",    SkyBlockConfigManager.ruinedPortalJungle() );
        enabledMap.put( "minecraft:ruined_portal_swamp",     SkyBlockConfigManager.ruinedPortalSwamp() );
        enabledMap.put( "minecraft:ruined_portal_mountain",  SkyBlockConfigManager.ruinedPortalMountain() );
        enabledMap.put( "minecraft:ruined_portal_ocean",     SkyBlockConfigManager.ruinedPortalOcean() );
        enabledMap.put( "minecraft:ruined_portal_nether",    SkyBlockConfigManager.ruinedPortalNether() );
        enabledMap.put( "minecraft:ancient_city",            SkyBlockConfigManager.ancientCities() );

        importModedStructures();
    }

    private static void importModedStructures() {
        if( SkyBlockConfigManager.customStructures().length > 0 ) {
            for (String resourceKey : SkyBlockConfigManager.customStructures()) {
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
}