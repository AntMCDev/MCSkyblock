package com.ant.mcskyblock.common.config;

import java.lang.reflect.Field;

public interface ConfigHelper {
    class IntData {
        public boolean isSlider;
        public Integer min;
        public Integer max;

        public IntData(boolean isSlider, Integer min, Integer max) {
            this.isSlider = isSlider;
            this.min = min;
            this.max = max;
        }
    }

    static IntData getIntData(Field f) {
        switch (f.getName()) {
            case "MAIN_ISLAND_RADIUS" -> { return new IntData(true, 1, 10); }
            case "SUB_ISLAND_RADIUS" -> { return new IntData(true, 1, 7); }
            case "MAIN_ISLAND_DEPTH", "SUB_ISLAND_DEPTH" -> { return new IntData(true, 1, 5); }
            case "MAIN_ISLAND_DISTANCE", "SUB_ISLAND_DISTANCE" -> { return new IntData(false, 32, null); }
            case "TAIGA_SPAWN_RADIUS" -> { return new IntData(true, 0, 64); }
            case "MAIN_ISLAND_Y" -> { return new IntData(true, -64, 128); }
        }
        if (f.getName().contains("_WEIGHT")) { return new IntData(true, 0, 100); }
        return new IntData(false, null, null);
    }
}
