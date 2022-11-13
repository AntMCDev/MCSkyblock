package com.ant.mcskyblock.fabric.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

// REQ FABRIC
public abstract class AbstractPacket implements IPacket {
    @Override
    public void registerOnClient() {
        ClientPlayNetworking.registerGlobalReceiver(getIdentifier(), this::executeOnClient);
    }

    @Override
    public void registerOnServer() {
        ServerPlayNetworking.registerGlobalReceiver(getIdentifier(), this::executeOnServer);
    }
}
