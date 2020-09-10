package me.projet.npc.npcmain;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPC {
    private static List<EntityPlayer> NPC = new ArrayList<EntityPlayer>();
    public static EntityPlayer createNPC(Player player, Player target, String name){
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldServer = ((CraftWorld) Bukkit.getWorld(player.getWorld().getName())).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.RED + name);
        EntityPlayer npc = new EntityPlayer(server,worldServer,gameProfile,new PlayerInteractManager(worldServer));
        String[] skin = getSkin(target);
        gameProfile.getProperties().put("textures",new Property("textures",skin[0],skin[1]));
        npc.setLocation(player.getLocation().getX(),
                player.getLocation().getY(),
                player.getLocation().getZ(),
                player.getLocation().getYaw(),
                player.getLocation().getPitch());
        addNPCPacket(npc);
        return npc;

    }
    public static void addNPCPacket(EntityPlayer npc){
        for (Player player : Bukkit.getOnlinePlayers()){
            PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        }

    }
    public static void addJoinPacket(Player player){
        for (EntityPlayer npc : NPC){
            PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc,(byte) (npc.yaw * 256 / 360)));
        }

    }
    public static List<EntityPlayer> getNPCs(){
        return NPC;
    }
    public static String[] getSkin(Player player){
        EntityPlayer p = ((CraftPlayer) player).getHandle();
        GameProfile profile = p.getProfile();
        Property property = profile.getProperties().get("textures").iterator().next();
        String textures = property.getValue();
        String signature = property.getSignature();
        return new String[] {textures,signature};


    }





}
