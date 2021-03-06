package com.vildaberper.DefaultCommands.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vildaberper.DefaultCommands.L;
import com.vildaberper.DefaultCommands.Misc;
import com.vildaberper.DefaultCommands.Perm;
import com.vildaberper.DefaultCommands.Util;

public class Kick{
	public static boolean kick(CommandSender sender, Command command, String label, String[] args){
		if(args.length >= 1){
			if(Misc.getPlayers(sender, args[0]).size() != 0){
				String message = "";

				if(args.length >= 2){
					message = args[1];
					for(int i = 2; i < args.length; i++){
						message += " " + args[i];
					}
				}
				if(sender instanceof Player && Misc.getPlayers(sender, args[0]).size() != 1 && !Perm.hasPermission((Player) sender, "dc.kick.all")){
					return false;
				}
				if(sender instanceof Player && Misc.getPlayers(sender, args[0]).size() == 1){
					if(Misc.getPlayers(sender, args[0]).get(0) == (Player) sender && !Perm.hasPermission((Player) sender, "dc.kick.self")){
						return false;
					}else if(Misc.getPlayers(sender, args[0]).get(0) != (Player) sender && !Perm.hasPermission((Player) sender, "dc.kick.other")){
						return false;
					}
				}
				for(Player player : Misc.getPlayers(sender, args[0])){
					player.kickPlayer(Util.replaceColor(message));
				}
				Misc.sendMessage(sender, Misc.getString("c_kick").replace("<player>", Misc.getSenderName(sender)).replace("<players>", Misc.getPlayerNames(Misc.getPlayers(sender, args[0]))).replace("<reason>", message));
				L.log(Misc.getString("c_kick").replace("<player>", Misc.getSenderName(sender)).replace("<players>", Misc.getPlayerNames(Misc.getPlayers(sender, args[0]))).replace("<reason>", message));
				return true;
			}else{
				Misc.sendString(sender, "invalid_player");
			}
		}
		return false;
	}
}