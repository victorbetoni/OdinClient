package net.threader.odinclient.command;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.threader.odinclient.OdinClient;
import net.threader.odinclient.feature.AbstractFeature;
import net.threader.odinclient.internal.api.command.CommandRunner;

import java.util.Optional;

public class ClientCommands {
    public static class ToggleFeatureCommand implements CommandRunner {
        @Override
        public void run(String[] args) {
            if(args.length < 1) {
                MinecraftClient.getInstance().player
                        .sendMessage(new TranslatableText("odinclient.message.command.toggle.usage")
                                .formatted(Formatting.RED), false);
                return;
            }
            String featureId = args[0];
            Optional<AbstractFeature> feature = OdinClient.INSTANCE.getFeatureManager().getFeature(featureId);
            feature.ifPresent(abstractFeature -> abstractFeature.setActivated(!abstractFeature.isActivated()));
        }
    }
}
