package dartcraftReloaded.handlers;

import dartcraftReloaded.networking.InfuserMessage;
import dartcraftReloaded.Constants;
import dartcraftReloaded.networking.SoundMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


public class PacketHandler {

    public static final SimpleNetworkWrapper packetHandler = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.modId);

    private static int id = 0;

    public static void init(){
        packetHandler.registerMessage(InfuserMessage.class, InfuserMessage.class, id++, Side.SERVER);
        packetHandler.registerMessage(SoundMessage.class, SoundMessage.class, id++, Side.CLIENT);
    }

    public static void sendToServer(IMessage message){
        packetHandler.sendToServer(message);
    }

    public static void sendToClient(IMessage message, EntityPlayerMP player){
        packetHandler.sendTo(message, player);
    }

    public static void sendPacket(Entity player, Packet<?> packet) {
        if(player instanceof EntityPlayerMP && ((EntityPlayerMP) player).connection != null) {
            ((EntityPlayerMP) player).connection.sendPacket(packet);
        }
    }
}
