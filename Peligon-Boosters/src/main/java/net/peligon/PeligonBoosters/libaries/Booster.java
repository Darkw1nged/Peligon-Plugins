package net.peligon.PeligonBoosters.libaries;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Booster {

    private final String owner;
    private final String type;
    private final double multiplier;
    private final int length;

    public Booster(String owner, String type) {
        this.owner = owner;
        this.type = type;
        this.multiplier = 1;
        this.length = 60;
    }

    public Booster(String owner, String type, double multiplier, int length) {
        this.owner = owner;
        this.type = type;
        this.multiplier = multiplier;
        this.length = length;
    }
    Map<UUID, Integer> personalExperienceBooster = new HashMap<>();
    Map<UUID, Integer> personalGrowthBooster = new HashMap<>();
    Map<UUID, Integer> personalSpawnRateBooster = new HashMap<>();
    Map<UUID, Integer> personalOreDropBooster = new HashMap<>();
    Map<UUID, Integer> personalMobDropBooster = new HashMap<>();
    Map<UUID, Integer> personalMcMMOBooster = new HashMap<>();
    Map<UUID, Integer> personalJobBooster = new HashMap<>();

    Map<UUID, Integer> globalExperienceBooster = new HashMap<>();
    Map<UUID, Integer> globalGrowthBooster = new HashMap<>();
    Map<UUID, Integer> globalSpawnRateBooster = new HashMap<>();
    Map<UUID, Integer> globalOreDropBooster = new HashMap<>();
    Map<UUID, Integer> globalMobDropBooster = new HashMap<>();
    Map<UUID, Integer> globalMcMMOBooster = new HashMap<>();
    Map<UUID, Integer> globalJobBooster = new HashMap<>();

}
