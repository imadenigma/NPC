package me.projet.npc.npcmain;

import net.minecraft.server.v1_16_R2.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Map;

public final class NPCMain extends JavaPlugin {
    public final Map<Integer, EntityPlayer> schema = new HashMap<Integer, EntityPlayer>();
    public int times = 0;
     // 20 ticks = 1 second

    private static NPCMain instance;
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new NPCjoin(),this);
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("createnpc")){
            if (!(sender instanceof Player)){
                return true;
            }
            if (args.length == 2) {
                Player player = (Player) sender;
                Player target = Bukkit.getPlayer(args[0]);
                NPC.createNPC(player,target,args[1]);
                player.sendMessage("NPC Created");
                schema.put(times,NPC.createNPC(player,target,args[1]));
                player.sendMessage("His ID IS " + times);
                times += 1;

            }
        }
        if (label.equalsIgnoreCase("select")){
            if (!(sender instanceof Player)){
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 1){
                int index = Integer.parseInt(args[0]);



                player.sendMessage(ChatColor.RED + "You Have A Slave Now");
            }


        }


        return false;
    }
    public static NPCMain getInstance(){
        return instance;
    }

}
