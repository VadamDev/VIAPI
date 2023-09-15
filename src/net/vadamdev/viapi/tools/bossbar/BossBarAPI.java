package net.vadamdev.viapi.tools.bossbar;

public interface BossBarAPI {
    BossBar createBossBar(String title, BossBar.Type type);
    default BossBar createBossBar(String title) {
        return createBossBar(title, BossBar.Type.WITHER);
    }
}
