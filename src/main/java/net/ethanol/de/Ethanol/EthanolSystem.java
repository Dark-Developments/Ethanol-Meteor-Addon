package net.ethanol.de.Ethanol;

import meteordevelopment.meteorclient.systems.System;
import meteordevelopment.meteorclient.systems.Systems;

public class EthanolSystem extends System<EthanolSystem> {

    public String apiKey = "";

    public EthanolSystem(){
        super("EthanolSystem");
    }

    public static EthanolSystem get() {
        return Systems.get(EthanolSystem.class);
    }

    // causes some bugs

//    @Override
//    public NbtCompound toTag() {
//        NbtCompound tag = new NbtCompound();
//
//        tag.putString("apiKey", apiKey);
//
//        return tag;
//    }
//
//    @Override
//    public EthanolSystem fromTag(NbtCompound tag) {
//        apiKey = tag.getString("apiKey");
//
//        return this;
//    }

}
