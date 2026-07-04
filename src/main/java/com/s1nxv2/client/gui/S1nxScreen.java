package com.s1nxv2.client.gui;

import com.s1nxv2.client.config.S1nxConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class S1nxScreen extends Screen {
    private final Screen parent;
    private final S1nxConfig config;
    private int tab = 0;
    private final String[] tabs = {"Guide", "Movement", "Render", "Macros"};

    public S1nxScreen(Screen parent, S1nxConfig config) {
        super(Text.literal("S1nxV2 Sandbox Utility"));
        this.parent = parent;
        this.config = config;
    }

    @Override protected void init() { rebuild(); }

    private void rebuild() {
        clearChildren();
        int x = width / 2 - 150;
        for (int i = 0; i < tabs.length; i++) {
            final int idx = i;
            addDrawableChild(ButtonWidget.builder(Text.literal((tab == i ? "§a" : "") + tabs[i]), b -> { tab = idx; rebuild(); })
                    .dimensions(x + i * 76, 28, 72, 20).build());
        }
        int y = 64;
        if (tab == 1) {
            addToggle("Movement: ", config.movementEnabled, v -> config.movementEnabled = v, y); y += 24;
            addButton("Fly speed: " + String.format("%.2f", config.flySpeed), () -> config.flySpeed = config.flySpeed >= 0.30D ? 0.03D : config.flySpeed + 0.03D, y); y += 24;
            addButton("Vertical boost: " + String.format("%.2f", config.verticalBoost), () -> config.verticalBoost = config.verticalBoost >= 1.0D ? 0.10D : config.verticalBoost + 0.10D, y); y += 24;
            addToggle("Step assist: ", config.stepAssist, v -> config.stepAssist = v, y); y += 24;
            addToggle("Sprint assist: ", config.sprintAssist, v -> config.sprintAssist = v, y);
        } else if (tab == 2) {
            addToggle("Fullbright: ", config.fullBright, v -> config.fullBright = v, y); y += 24;
            addToggle("Hide weather intent: ", config.hideWeather, v -> config.hideWeather = v, y); y += 24;
            addToggle("HUD overlay: ", config.hudOverlay, v -> config.hudOverlay = v, y);
        } else if (tab == 3) {
            addToggle("Sandbox-only lock: ", config.sandboxOnly, v -> config.sandboxOnly = v, y); y += 24;
            addToggle("Auto jump macro: ", config.autoJumpMacro, v -> config.autoJumpMacro = v, y); y += 24;
            addToggle("Auto use macro: ", config.autoUseMacro, v -> config.autoUseMacro = v, y);
        }
        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> close()).dimensions(width / 2 - 50, height - 32, 100, 20).build());
    }

    private void addToggle(String label, boolean value, BoolSetter setter, int y) { addButton(label + (value ? "ON" : "OFF"), () -> setter.set(!value), y); }
    private void addButton(String label, Runnable action, int y) { addDrawableChild(ButtonWidget.builder(Text.literal(label), b -> { action.run(); rebuild(); }).dimensions(width / 2 - 120, y, 240, 20).build()); }

    @Override public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 10, 0xFFFFFF);
        if (tab == 0) drawGuide(context);
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawGuide(DrawContext context) {
        List<String> lines = new ArrayList<>();
        lines.add("A client utility kit for controlled Minecraft 1.21.1 Fabric sandbox testing.");
        lines.add("Open this panel with the configured S1nxV2 keybinding; all changes are runtime-only.");
        lines.add("");
        for (Map.Entry<String, String> entry : config.notes.entrySet()) lines.add(entry.getKey() + ": " + entry.getValue());
        int y = 64;
        for (String line : lines) { context.drawTextWithShadow(textRenderer, Text.literal(line), width / 2 - 170, y, 0xD8E6FF); y += 14; }
    }

    @Override public void close() { if (client != null) client.setScreen(parent); }
    @FunctionalInterface private interface BoolSetter { void set(boolean value); }
}
