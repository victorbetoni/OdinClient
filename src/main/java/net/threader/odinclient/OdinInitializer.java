package net.threader.odinclient;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.options.KeyBinding;

public class OdinInitializer implements ModInitializer {
	@Override
	public void onInitialize() {
		OdinClient.INSTANCE.initialize();
	}
}
