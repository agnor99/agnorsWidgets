package com.agnor99.agnorsWidgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AgnorsWidgets.MOD_ID)
public class AgnorsWidgets {
    public static final String MOD_ID = "agnors_widgets";

    public boolean screenDebug = false;
    public AgnorsWidgets() {
        if(screenDebug)MinecraftForge.EVENT_BUS.addListener(this::openScreen);
    }
    private void openScreen(LivingEvent.LivingJumpEvent event) {
        if(event.getEntity() instanceof ClientPlayerEntity) {
            Minecraft.getInstance().displayGuiScreen(new ExampleScreen());
        }
    }
}
