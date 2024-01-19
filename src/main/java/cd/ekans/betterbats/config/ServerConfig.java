package cd.ekans.betterbats.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.IntValue MAX_BAT_COUNT;
    public static final ForgeConfigSpec.IntValue COUNT_COOLDOWN;

    public ServerConfig() {
    }

    static {
        BUILDER.push("Settings");
        MAX_BAT_COUNT = BUILDER.comment("How many bat you have to eat before receiving the wither effect").defineInRange("max_bat_count", 8, 0, 746);
        COUNT_COOLDOWN = BUILDER.comment("The amount of time it takes (in seconds) for your bat count to reset").defineInRange("bat_count_timer", 1200, 0, 86400);
        SPEC = BUILDER.build();
    }
}
