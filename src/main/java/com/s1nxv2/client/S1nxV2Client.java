package com.s1nxv2.client;

import com.s1nxv2.client.config.S1nxConfig;
import com.s1nxv2.client.feature.FeatureManager;
import com.s1nxv2.client.gui.S1nxScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class S1nxV2Client implements ClientModInitializer {
    public static final String MOD_ID = "s1nxv2";
    private final S1nxConfig config = new S1nxConfig();
    private final FeatureManager features = new FeatureManager(config);
    private KeyBinding openKey;

    @Override public void onInitializeClient() {
        openKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.s1nxv2.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "category.s1nxv2"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openKey.wasPressed()) client.setScreen(new S1nxScreen(client.currentScreen, config));
            features.tick(client);
            features.applyRenderOptions(client);
            if (config.hideWeather && client.world != null && config.canRunOnCurrentWorld(client.isIntegratedServerRunning())) {
                client.world.setRainGradient(0.0F);
                client.world.setThunderGradient(0.0F);
            }
        });
        HudRenderCallback.EVENT.register(this::renderHud);
    }

    private void renderHud(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!config.hudOverlay || client.player == null || client.options.hudHidden) return;
        context.fill(6, 6, 214, 29, 0x99000000);
        context.drawText(client.textRenderer, features.status(client), 10, 11, 0xA7FFB5, false);
        if (!config.canRunOnCurrentWorld(client.isIntegratedServerRunning())) {
            context.drawText(client.textRenderer, Text.literal("Sandbox lock active: join a local world or disable lock."), 10, 21, 0xFFD27D, false);
        }
    }
}
