package dartcraftReloaded.items;

import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.capablilities.UpgradeTome.UpgradeTomeProvider;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.capablilities.UpgradeTome.IUpgradeTome;
import dartcraftReloaded.handlers.GUIHandler;
import dartcraftReloaded.tileEntity.TileEntityInfuser;
import dartcraftReloaded.util.DartUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;

import static dartcraftReloaded.Constants.UPGRADE_TOME;

public class ItemUpgradeTome extends Item {

    public ItemUpgradeTome() {
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        this.setTranslationKey(UPGRADE_TOME);
        this.setRegistryName(UPGRADE_TOME);
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        IUpgradeTome cap = stack.getCapability(CapabilityHandler.CAPABILITY_UPGRADETOME, null);
        if (cap != null) {
            tooltip.add(TextFormatting.BLUE+"Tier "+cap.getLevel());
            if (cap.getLevel() < 8) {
                tooltip.add(TextFormatting.WHITE+String.valueOf(cap.getUpgradePoints())+TextFormatting.BLUE+" Force Points");
                tooltip.add(TextFormatting.BLUE+"Next Tier: "+TextFormatting.WHITE+(TileEntityInfuser.getThreshold(cap.getLevel()) - cap.getUpgradePoints()));
            }
            else {
                tooltip.add(TextFormatting.BLUE+"Mastered!");
            }

        }
    }

    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, UPGRADE_TOME);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if (!stack.hasCapability(CapabilityHandler.CAPABILITY_UPGRADETOME, null))
            return new UpgradeTomeProvider(CapabilityHandler.CAPABILITY_UPGRADETOME, null);
        else
            return null;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        IUpgradeTome cap = stack.getCapability(CapabilityHandler.CAPABILITY_UPGRADETOME, null);
        if (player.isCreative() && player.isSneaking()) {
            cap.setLevel((cap.getLevel() % 8) + 1);
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        } else {
            player.openGui(DartcraftReloaded.instance, GUIHandler.BOOK, world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
            return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        player.openGui(DartcraftReloaded.instance, GUIHandler.BOOK, worldIn, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
        return EnumActionResult.PASS;
    }

}