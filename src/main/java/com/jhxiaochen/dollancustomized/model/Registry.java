package com.jhxiaochen.dollancustomized.model;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class Registry {

    public Registry(){

    }

    public final List<ModElement> elements = new ArrayList<>();
    public final List<Supplier<Block>>     blocks   = new ArrayList<>();
    public final List<Supplier<Item>>  items           = new ArrayList<>();
    public List<Registry.ModElement> getElements() {
        return elements;
    }

    public List<Supplier<Block>> getBlocks() {
        return blocks;
    }

    public List<Supplier<Item>> getItems() {
        return items;
    }

    public void preInit(FMLPreInitializationEvent event) {
        try {
            for (ASMDataTable.ASMData asmData : event.getAsmData().getAll(ModElement.Tag.class.getName())) {
                Class<?> clazz = Class.forName(asmData.getClassName());
                if (clazz.getSuperclass() == Registry.ModElement.class)
                    elements.add((Registry.ModElement) clazz.getConstructor(this.getClass()).newInstance(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.shuffle(elements);
        elements.forEach(Registry.ModElement::initElements);
    }

    public static class ModElement {
        @Retention(RetentionPolicy.RUNTIME)
        public @interface Tag {
        }
        protected final Registry registry;
        public ModElement(Registry registry) {
            this.registry = registry;

        }

        public void initElements() {
        }

        public void init(FMLInitializationEvent event) {
        }

        public void preInit(FMLPreInitializationEvent event) {
        }

        public void registerBlockItemModel(ModelRegistryEvent event) {
        }

    }

}
