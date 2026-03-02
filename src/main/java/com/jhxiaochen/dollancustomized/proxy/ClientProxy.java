package com.jhxiaochen.dollancustomized.proxy;

import net.minecraftforge.common.MinecraftForge;

public class ClientProxy implements CommonProxy {

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void init() {
    }

    @Override
    public void postInit() {

    }




}
