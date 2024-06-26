package dartcraftReloaded.util;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ServerHelper {

    private ServerHelper(){}

    public static boolean isClientWorld(World world){
        return world.isRemote;
    }

    public static boolean isServerWorld(World world){
        return !world.isRemote;
    }

    public static boolean isSinglePlayerServer() {

        return FMLCommonHandler.instance().getMinecraftServerInstance() != null;
    }

    public static boolean isMultiPlayerServer() {

        return FMLCommonHandler.instance().getMinecraftServerInstance() == null;
    }
}
