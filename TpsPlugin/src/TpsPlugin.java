package com.serverfriends.tps;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class TpsPlugin extends JavaPlugin {
    private volatile double tps = 20.0;
    private long lastTime = System.nanoTime();
    private int tickCount = 0;

    public double getTps() {
        return tps;
    }

    public void onEnable() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                tickCount++;
                long now = System.nanoTime();
                long diff = now - lastTime;
                if (diff >= 1_000_000_000L) {
                    double current = (tickCount * 1_000_000_000.0) / diff;
                    if (current > 20.0) current = 20.0;
                    tps = current;
                    tickCount = 0;
                    lastTime = now;
                }
            }
        }, 1L, 1L);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tps")) {
            String msg = ChatColor.GREEN + "TPS: " + String.format("%.1f", tps);
            sender.sendMessage(msg);
            return true;
        }
        return false;
    }
}
