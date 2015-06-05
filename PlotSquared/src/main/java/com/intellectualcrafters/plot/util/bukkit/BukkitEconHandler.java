package com.intellectualcrafters.plot.util.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import com.intellectualcrafters.plot.object.BukkitOfflinePlayer;
import com.intellectualcrafters.plot.object.BukkitPlayer;
import com.intellectualcrafters.plot.object.OfflinePlotPlayer;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.EconHandler;

public class BukkitEconHandler extends EconHandler {
    
    private Economy econ;
    private Permission perms;
    
    public boolean init() {
        if (econ == null || perms == null) {
            setupPermissions();
            setupEconomy();
        }
        return econ != null && perms != null;
    }
    
    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            perms = permissionProvider.getProvider();
        }
        return (perms != null);
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }

        return (econ != null);
    }

    @Override
    public double getMoney(PlotPlayer player) {
        econ.getBalance(player.getName());
        return 0;
    }

    @Override
    public double withdrawMoney(PlotPlayer player, double amount) {
        econ.withdrawPlayer(player.getName(), amount);
        return 0;
    }

    @Override
    public double depositMoney(PlotPlayer player, double amount) {
        econ.depositPlayer(player.getName(), amount);
        return 0;
    }

    @Override
    public double depositMoney(OfflinePlotPlayer player, double amount) {
        econ.depositPlayer(((BukkitOfflinePlayer) player).player, amount);
        return 0;
    }

    @Override
    public void setPermission(PlotPlayer player, String perm, boolean value) {
        if (value) {
            perms.playerAdd(((BukkitPlayer) player).player, perm);
        }
        else {
            perms.playerRemove(((BukkitPlayer) player).player, perm);
        }
    }

    @Override
    public boolean getPermission(PlotPlayer player, String perm) {
        return player.hasPermission(perm);
    }
}