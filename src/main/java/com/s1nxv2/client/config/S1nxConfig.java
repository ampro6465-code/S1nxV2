package com.s1nxv2.client.config;

import java.util.LinkedHashMap;
import java.util.Map;

/** Runtime-only settings intentionally scoped to local worlds and test sessions. */
public final class S1nxConfig {
    public boolean sandboxOnly = true;
    public boolean movementEnabled = false;
    public double flySpeed = 0.08D;
    public double verticalBoost = 0.35D;
    public boolean stepAssist = false;
    public boolean sprintAssist = false;
    public boolean fullBright = false;
    public boolean hideWeather = false;
    public boolean hudOverlay = true;
    public boolean autoJumpMacro = false;
    public boolean autoUseMacro = false;
    public final Map<String, String> notes = new LinkedHashMap<>();

    public S1nxConfig() {
        notes.put("Safety", "Tools are intended for single-player, LAN, and controlled test servers only.");
        notes.put("Movement", "Tune fly speed, vertical boost, step assist, and sprint assist from the GUI.");
        notes.put("Rendering", "Toggle fullbright, weather hiding, and the compact HUD overlay.");
        notes.put("Macros", "Automation macros are simple local input helpers for repeatable sandbox tests.");
    }

    public boolean canRunOnCurrentWorld(boolean integratedServerRunning) {
        return !sandboxOnly || integratedServerRunning;
    }
}
