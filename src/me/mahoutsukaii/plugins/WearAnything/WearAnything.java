package me.mahoutsukaii.plugins.WearAnything;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijikokun.bukkit.Permissions.Permissions;

public class WearAnything extends JavaPlugin{
	
	public static final Logger log = Logger.getLogger("Minecraft");
	Permissions CurrentPermissions = null;
	
	
	public void setupPermissions() {
		Plugin plugin = this.getServer().getPluginManager().getPlugin("Permissions");

		if (CurrentPermissions == null) {
			// Permission plugin already registered
			return;
		}

		if (plugin != null) {
			CurrentPermissions = (Permissions) plugin;
		} else {
			log.log(Level.CONFIG, "[WearAnything] Needs permissions plugin. disabling...");
			this.getServer().getPluginManager().disablePlugin(this);
		}
	}

	public void onDisable() {
		System.out.println(this + " is now disabled!");
		
	}

	public void onEnable() {
		System.out.println(this + " is now enabled!");
		
	}


	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		String commandName = command.getName().toLowerCase();
		String[] trimmedArgs = args;
		
		if(commandName.equals("wear"))
		{
			return wear(sender, trimmedArgs);
		}
		return false;
		
	}
	
	
	public boolean wear(CommandSender sender, String[] args)
	{
		boolean auth = false;
		
		if(sender instanceof Player)
		{
			if(Permissions.Security.permission((Player)sender,"wearanything.wear"))
			{
				auth = true;
			}
		}
		else
		{
			sender.sendMessage("Console can't wear things!");
			return true;
		}
		if(!auth)
		return false;
		
		Player player = (Player)sender;
		MaterialData materialData = new MaterialData(Material.PORTAL);
		ItemStack itemStack = materialData.toItemStack();
		
		if(player.getItemInHand().getTypeId() != 0)
		itemStack = player.getItemInHand();
		
		itemStack.setAmount(1);
		
		player.getInventory().setHelmet(itemStack);
		player.saveData();
		player.sendMessage(ChatColor.GREEN + "Enjoy your new hat!");
		return true;
		
	}



}
