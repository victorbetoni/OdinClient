package net.threader.odinclient;

import net.fabricmc.api.ModInitializer;

public class OdinInitializer implements ModInitializer {
	@Override
	public void onInitialize() {
		OdinClient.INSTANCE.initialize();
	}
}
