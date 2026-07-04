package com.s1nxv2.client.feature;

import com.s1nxv2.client.config.S1nxConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

public final class FeatureManager {
    private final S1nxConfig config;
    private double previousGamma = -1.0D;

    public FeatureManager(S1nxConfig config) { this.config = config; }

    public void tick(MinecraftClient client) {
        if (client.player == null) return;
        boolean allowed = config.canRunOnCurrentWorld(client.isIntegratedServerRunning());
        client.player.getAbilities().setFlySpeed((float) (allowed && config.movementEnabled ? config.flySpeed : 0.05F));
        client.player.getAbilities().allowFlying = allowed && config.movementEnabled || client.player.getAbilities().creativeMode;
        if (!allowed) return;
        if (config.sprintAssist && client.options.forwardKey.isPressed()) client.player.setSprinting(true);
        if (config.stepAssist) client.player.setStepHeight(1.0F);
        if (config.movementEnabled && client.options.jumpKey.isPressed()) client.player.addVelocity(0, config.verticalBoost * 0.05D, 0);
        if (config.autoJumpMacro && client.player.isOnGround()) client.player.jump();
        if (config.autoUseMacro && client.interactionManager != null) client.options.useKey.setPressed(true);
    }

    public void applyRenderOptions(MinecraftClient client) {
        SimpleOption<Double> gamma = client.options.getGamma();
        if (config.fullBright) {
            if (previousGamma < 0) previousGamma = gamma.getValue();
            gamma.setValue(15.0D);
        } else if (previousGamma >= 0) {
            gamma.setValue(previousGamma);
            previousGamma = -1.0D;
        }
    }

    public Text status(MinecraftClient client) {
        boolean allowed = config.canRunOnCurrentWorld(client.isIntegratedServerRunning());
        return Text.literal("S1nxV2 " + (allowed ? "ready" : "locked to sandbox") + " | move=" + config.movementEnabled + " render=" + config.fullBright + " macros=" + (config.autoJumpMacro || config.autoUseMacro));
    }
}
