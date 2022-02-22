package net.peligon.PeligonEnchants.libaries;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomEnchants {

    public static final Enchantment AUTOSELL = new Wrapper("autosell", "Autosell", 1);
    public static final Enchantment AQUATIC = new Wrapper("aquatic", "Aquatic", 1); // HELMETS
    public static final Enchantment GLOWING = new Wrapper("glowing", "Glowing", 1); // HELMETS
    public static final Enchantment TELEPATHY = new Wrapper("telepathy", "Telepathy", 1); // ALL TOOLS
    public static final Enchantment HASTE = new Wrapper("haste", "Haste", 3); // All Armor
    public static final Enchantment AUTOSMELT = new Wrapper("autosmelt", "Auto Smelt", 1); // PICKAXES
    public static final Enchantment OXYGENATE = new Wrapper("oxygenate", "Oxygenate", 3); // ALL TOOLS
    public static final Enchantment SPRINGS = new Wrapper("springs", "Springs", 3); // BOOTS
    public static final Enchantment POISONED = new Wrapper("poisoned", "Poisoned", 4); // ALL ARMOR
    public static final Enchantment STORMCALLER = new Wrapper("stormcaller", "Stormcaller", 4); // ALL ARMOR
    public static final Enchantment VOODOO = new Wrapper("voodoo", "Voodoo", 6); // ALL ARMOR
    public static final Enchantment VENOM = new Wrapper("venom", "Venom", 3); // BOWS
    public static final Enchantment MOLTEN = new Wrapper("molten", "Molten", 4); // ALL ARMOR
    public static final Enchantment ENDERSHIFT = new Wrapper("endershift", "Ender Shift", 3); // HELMETS, BOOTS
    public static final Enchantment BLIND = new Wrapper("blind", "Blind", 3); // SWORDS, BOWS
    public static final Enchantment POISON = new Wrapper("poison", "Poison", 3); // SWORDS
    public static final Enchantment TRAP = new Wrapper("trap", "Trap", 3); // SWORDS
    public static final Enchantment HEADLESS = new Wrapper("headless", "Headless", 3); // SWORDS
    public static final Enchantment DECAPITATION = new Wrapper("decapitation", "Decapitation", 3); // AXES
    public static final Enchantment CONFUSION = new Wrapper("confusion", "Confusion", 3); // AXES
    public static final Enchantment LIGHTNING = new Wrapper("lightning", "Lightning", 3); // BOWS
    public static final Enchantment FEATHERWEiGHT = new Wrapper("featherweight", "Featherweight", 3); // SWORDS
    public static final Enchantment VAMPIRE = new Wrapper("vampire", "Vampire", 3); // SWORDS
    public static final Enchantment RAVENOUS = new Wrapper("ravenous", "Ravenous", 4); // AXES

    public static final Enchantment OBSIDIANDESTROYER = new Wrapper("obsidiandestroyer", "Obsidian Destroyer", 5); // PICKAXE
    public static final Enchantment EXPERIENCE = new Wrapper("experience", "Experience", 5); // ALL TOOLS
    public static final Enchantment OBLITERATE = new Wrapper("obliterate", "Obliterate", 5); // ALL WEAPONS
    public static final Enchantment EXPLOSIVE = new Wrapper("explosive", "Explosive", 5); // BOWS

    public static final Enchantment BERSERK = new Wrapper("berserk", "Berserk", 5);
    public static final Enchantment SELFDESTRUCT = new Wrapper("selfdestruct", "Self Destruct", 3);
    public static final Enchantment SKILLSWIPE = new Wrapper("skillswipe", "Skill Swipe", 5);
    public static final Enchantment VIRUS = new Wrapper("virus", "Virus", 4);
    public static final Enchantment CURSE = new Wrapper("curse", "Curse", 5);
    public static final Enchantment ROCKETESCAPE = new Wrapper("rocketescape", "Rocket Escape", 3);
    public static final Enchantment PUMMEL = new Wrapper("pummel", "Pummel", 3);
    public static final Enchantment FROZEN = new Wrapper("frozen", "Frozen", 3);
    public static final Enchantment WITHER = new Wrapper("wither", "Wither", 5);
    public static final Enchantment SHOCKWAVE = new Wrapper("shockwave", "Shockwave", 5);
    public static final Enchantment CACTUS = new Wrapper("cactus", "Cactus", 2);
    public static final Enchantment REFORGE = new Wrapper("reforge", "Reforge", 10);
    public static final Enchantment DEMONFORGED = new Wrapper("demonforged", "Demon Forged", 4);
    public static final Enchantment UNDEADRUSE = new Wrapper("undeadruse", "Undead Ruse", 10);
    public static final Enchantment EXECUTE = new Wrapper("execute", "Execute", 7);

    public static void register() {
        registerEnchant(AUTOSELL);                  registerEnchant(HEADLESS);                  registerEnchant(CONFUSION);
        registerEnchant(EXPERIENCE);                registerEnchant(LIGHTNING);                 registerEnchant(DECAPITATION);
        registerEnchant(OBLITERATE);                registerEnchant(HASTE);                     registerEnchant(AUTOSMELT);
        registerEnchant(AQUATIC);                   registerEnchant(GLOWING);                   registerEnchant(OXYGENATE);
        registerEnchant(FEATHERWEiGHT);             registerEnchant(OBSIDIANDESTROYER);         registerEnchant(EXPLOSIVE);
        registerEnchant(BERSERK);                   registerEnchant(SELFDESTRUCT);              registerEnchant(MOLTEN);
        registerEnchant(RAVENOUS);                  registerEnchant(ENDERSHIFT);                registerEnchant(SKILLSWIPE);
        registerEnchant(VIRUS);                     registerEnchant(CURSE);                     registerEnchant(TELEPATHY);
        registerEnchant(ROCKETESCAPE);              registerEnchant(BLIND);                     registerEnchant(POISON);
        registerEnchant(PUMMEL);                    registerEnchant(POISONED);                  registerEnchant(STORMCALLER);
        registerEnchant(VENOM);                     registerEnchant(VAMPIRE);                   registerEnchant(FROZEN);
        registerEnchant(WITHER);                    registerEnchant(SHOCKWAVE);                 registerEnchant(CACTUS);
        registerEnchant(REFORGE);                   registerEnchant(VOODOO);                    registerEnchant(SPRINGS);
        registerEnchant(DEMONFORGED);               registerEnchant(UNDEADRUSE);                registerEnchant(EXECUTE);
        registerEnchant(TRAP);
    }

    private static void registerEnchant(Enchantment enchantment) {
        if (!((List) Arrays.<Enchantment>stream(Enchantment.values()).collect(Collectors.toList())).contains(enchantment))
            registerEnchantment(enchantment);
    }

    public static void registerEnchantment(Enchantment enchantment) {
        boolean registered = true;
        try {
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, Boolean.valueOf(true));
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            registered = false;
        }
    }

}
