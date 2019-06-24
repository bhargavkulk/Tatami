package com.wags13.tatami;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod.EventBusSubscriber(modid=Tatami.ID)
public class Registry {

    private static List<Block> blocks = new ArrayList<>();
    private static List<Item> items = new ArrayList<>();
    private static List<IForgeRegistryEntry.Impl<IRecipe>> recipes = new ArrayList<>();
    private static Map<Block, Item> blockItemMap = new HashMap<>();

    public static void registerBlock(Block block, String registryname) {
        block.setRegistryName(registryname);
        block.setUnlocalizedName(Tatami.ID + ":" + block.getRegistryName().toString());
        blocks.add(block);
    }

    public static void registerBlockWithItemBlock(Block block, String registryname) {
        block.setRegistryName(registryname);
        block.setUnlocalizedName(Tatami.ID + ":" + block.getRegistryName().toString());

        Item item = new ItemBlock(block);
        item.setRegistryName(registryname);

        blocks.add(block);
        items.add(item);
        blockItemMap.put(block, item);
    }

    public static void registerBlockWithCustomItem(Block block, Item item, String registryname) {
        block.setRegistryName(registryname);
        block.setUnlocalizedName(Tatami.ID + ":" + block.getRegistryName().toString());

        item.setRegistryName(registryname);
        item.setUnlocalizedName(Tatami.ID + ":" + block.getRegistryName().toString());

        blocks.add(block);
        items.add(item);
        blockItemMap.put(block, item);
    }

    public static void registerShapedOreRecipe(ResourceLocation group, ItemStack result, Object... recipe) {
        recipes.add(new ShapedOreRecipe(group, result, recipe));
    }

    public static void registerShapelessOreRecipe(ResourceLocation group, ItemStack result, Object... recipe) {
        recipes.add(new ShapedOreRecipe(group, result, recipe));
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(blocks.toArray(new Block[blocks.size()]));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(items.toArray(new Item[items.size()]));
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        blockItemMap.values().forEach( item ->
                ModelLoader.setCustomModelResourceLocation(
                        item,
                        0,
                        new ModelResourceLocation(item.getRegistryName(), "inventory")
                )
        );
    }

    @SubscribeEvent
    public void init(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().registerAll(recipes.toArray(new IRecipe[recipes.size()]));
    }

}
