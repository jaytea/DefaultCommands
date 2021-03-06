package com.vildaberper.DefaultCommands.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vildaberper.DefaultCommands.L;
import com.vildaberper.DefaultCommands.Misc;
import com.vildaberper.DefaultCommands.Perm;

public class Reset{
	public static boolean reset(CommandSender sender, Command command, String label, String[] args){
		if(args.length == 0 && sender instanceof Player){
			((Player) sender).chat("/dcreset " + ((Player) sender).getName());
			return true;
		}else if(args.length == 1){
			if(Misc.getPlayers(sender, args[0]).size() != 0){
				if(sender instanceof Player && Misc.getPlayers(sender, args[0]).size() != 1 && !Perm.hasPermission((Player) sender, "dc.reset.all")){
					return false;
				}
				if(sender instanceof Player && Misc.getPlayers(sender, args[0]).size() == 1){
					if(Misc.getPlayers(sender, args[0]).get(0) == (Player) sender && !Perm.hasPermission((Player) sender, "dc.reset.self")){
						return false;
					}else if(Misc.getPlayers(sender, args[0]).get(0) != (Player) sender && !Perm.hasPermission((Player) sender, "dc.reset.other")){
						return false;
					}
				}
				for(Player player : Misc.getPlayers(sender, args[0])){
					Misc.removePlayerFromLists(player);
					Misc.setHide(player.getName(), false);
				}
				L.log(Misc.getSenderCmdMsg("c_reset", sender, Misc.getPlayers(sender, args[0])));
				Misc.sendMessage(sender, Misc.getSenderCmdMsg("c_reset", sender, Misc.getPlayers(sender, args[0])));
				return true;
			}else{
				Misc.sendString(sender, "invalid_player");
			}
		}
		return false;
	}
}