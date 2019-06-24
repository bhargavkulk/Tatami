package com.wags13.tatami;

import com.wags13.tatami.features.FeatureLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Tatami.ID, name = Tatami.NAME, version = Tatami.VERSION)
public class Tatami {
    public static final String ID = "tatami";
    public static final String NAME = "Tatami";
    public static final String VERSION = "1.0.0";

    public static final CreativeTabs tabTatami = new CreativeTabs("tabTatami") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.REEDS);
        }
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        FeatureLoader.preInit();
    }
}
