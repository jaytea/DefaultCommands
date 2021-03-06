package com.vildaberper.DefaultCommands.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vildaberper.DefaultCommands.L;
import com.vildaberper.DefaultCommands.Misc;
import com.vildaberper.DefaultCommands.Perm;

public class God{
	public static boolean god(CommandSender sender, Command command, String label, String[] args){
		if(args.length == 0 && sender instanceof Player){
			((Player) sender).chat("/dcgod " + ((Player) sender).getName());
			return true;
		}else if(args.length == 1){
			if(Misc.getPlayers(sender, args[0]).size() != 0){
				if(sender instanceof Player && Misc.getPlayers(sender, args[0]).size() != 1 && !Perm.hasPermission((Player) sender, "dc.god.all")){
					return false;
				}
				if(sender instanceof Player && Misc.getPlayers(sender, args[0]).size() == 1){
					if(Misc.getPlayers(sender, args[0]).get(0) == (Player) sender && !Perm.hasPermission((Player) sender, "dc.god.self")){
						return false;
					}else if(Misc.getPlayers(sender, args[0]).get(0) != (Player) sender && !Perm.hasPermission((Player) sender, "dc.god.other")){
						return false;
					}
				}
				for(Player player : Misc.getPlayers(sender, args[0])){
					if(Misc.isGod(player.getEntityId())){
						Misc.setGod(player.getEntityId(), false);
					}else{
						Misc.setGod(player.getEntityId(), true);
					}
				}
				if(Misc.getPlayers(sender, args[0]).size() > 1){
					L.log(Misc.getSenderCmdMsg("c_god", sender, Misc.getPlayers(sender, args[0])));
					Misc.sendMessage(sender, Misc.getSenderCmdMsg("c_god", sender, Misc.getPlayers(sender, args[0])));
				}else{
					L.log(Misc.getSenderCmdMsg("c_god", sender, Misc.getPlayers(sender, args[0]), Misc.isGod(Misc.getPlayers(sender, args[0]).get(0).getEntityId())));
					Misc.sendMessage(sender, Misc.getSenderCmdMsg("c_god", sender, Misc.getPlayers(sender, args[0]), Misc.isGod(Misc.getPlayers(sender, args[0]).get(0).getEntityId())));
				}
				return true;
			}else{
				Misc.sendString(sender, "invalid_player");
			}
		}
		return false;
	}
}