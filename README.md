# S1nxV2 Sandbox Utility

S1nxV2 is a Minecraft 1.21.1 Fabric client-side utility mod for local sandbox, LAN, and controlled test-server workflows. It provides an in-game guide, a clean tabbed GUI, runtime movement tuning, visual testing toggles, and small automation macros for repeatable QA tasks.

## Features

- **In-game documentation**: press Right Shift to open a guide with usage and safety notes.
- **Movement tools**: runtime flight-speed tuning, vertical boost, step assist, and sprint assist.
- **Render tools**: fullbright, local weather-visibility suppression, and compact HUD status overlay.
- **Automation macros**: auto-jump and auto-use input helpers for repeatable sandbox tests.
- **Sandbox guardrail**: tools are locked to integrated/local worlds by default; the lock is visible and configurable in the GUI for controlled environments.

## Building

```bash
gradle build
```

The mod targets Java 21, Fabric Loader 0.16+, and Minecraft 1.21.1. This repository includes compile-only API stubs so `gradle build` can produce the client mod jar in restricted environments where Fabric Maven is unreachable; those stubs are not packaged into the runtime jar.
