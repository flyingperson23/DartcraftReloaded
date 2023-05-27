package dartcraftReloaded.items.tools;

import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.ExperienceTome.IExperienceTome;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.capablilities.Modifiable.ModifiableProvider;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.handlers.PacketHandler;
import dartcraftReloaded.items.ItemBase;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.items.nonburnable.EntityNonBurnableItem;
import dartcraftReloaded.items.nonburnable.ItemInertCore;
import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.networking.SoundMessage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;


/**
 * Created by BURN447 on 2/23/2018.
 */
public class ItemForceRod extends ItemBase {
    public ItemForceRod(){
        super(Constants.FORCE_ROD);
        setHasSubtypes(true);
        setTranslationKey(Constants.FORCE_ROD);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        this.setMaxDamage(50);
        this.setMaxStackSize(1);
    }

    private EnumActionResult doAction(ItemStack i, EntityPlayer p, World w) {
        i.damageItem(1, p);
        PacketHandler.sendToClient(new SoundMessage(0), (EntityPlayerMP) p);
        return EnumActionResult.SUCCESS;
    }
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null))
            return new ModifiableProvider(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        else
            return null;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        IModifiable cap = playerIn.getHeldItem(handIn).getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        if (cap.hasModifier(Constants.LUCK)) {
            playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(26), cap.getLevel(Constants.LUCK) * 30));
        }
        if (cap.hasModifier(Constants.SPEED)) {
            playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(1), cap.getLevel(Constants.SPEED) * 30, cap.getLevel(Constants.SPEED) * 2));
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        if (held.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).hasModifier(Constants.HEAT)) {
            List<Entity> list = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ())));
            for (Entity i : list) {
                if (i instanceof EntityLiving) {
                    i.setFire(held.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).getLevel(Constants.HEAT));
                }
            }
        }
        if (!worldIn.isRemote) {
            if (worldIn.getBlockState(pos.offset(facing)).getBlock().equals(Blocks.FIRE)) {
                worldIn.setBlockToAir(pos.offset(facing));
                List<Entity> list = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())));
                boolean bw = false;
                for (Entity i: list) {
                    if(i instanceof EntityItem) {
                        if(((EntityItem) i).getItem().getItem() instanceof ItemInertCore) {
                            EntityItem bottledWither = new EntityNonBurnableItem(worldIn, pos.getX(), pos.getY()  + 1, pos.getZ(), new ItemStack(ModItems.bottledWither, ((EntityItem) i).getItem().getCount()));
                            worldIn.spawnEntity(bottledWither);
                            doAction(held, player, worldIn);
                            bw = true;
                        }
                    }
                }
                if(bw) {
                    for(Entity i: list) {
                        if(i instanceof EntityItem) {
                            if (((EntityItem) i).getItem().getItem() instanceof ItemInertCore) {
                                worldIn.removeEntity(i);
                                return EnumActionResult.SUCCESS;
                            }
                        }
                    }
                }
            } else if (worldIn.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN)) {
                worldIn.setBlockState(pos, ModBlocks.infuser.getDefaultState(), 3);
                worldIn.notifyBlockUpdate(pos, Blocks.OBSIDIAN.getDefaultState(), ModBlocks.infuser.getDefaultState(), 3);
                return doAction(held, player, worldIn);
            } else if (worldIn.getBlockState(pos).getBlock().equals(Blocks.SAPLING)) {
                IBlockState i = worldIn.getBlockState(pos);
                worldIn.setBlockState(pos, ModBlocks.forceSapling.getDefaultState(), 3);
                worldIn.notifyBlockUpdate(pos, i, ModBlocks.forceSapling.getDefaultState(), 3);
                return doAction(held, player, worldIn);
            }
            else {
                List<Entity> list = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ())));
                //If it is a subset of items, it will drop swap an item
                for(Entity i: list) {
                    if (i instanceof EntityItem) {
                        //Armor
                        if (((EntityItem) i).getItem().getItem() == Items.BOOK) {
                            worldIn.removeEntity(i);
                            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.upgradeTome)));
                            return doAction(held, player, worldIn);
                        } else if (((EntityItem) i).getItem().getItem() == ModItems.experienceTome) {
                            IExperienceTome cap = ((EntityItem) i).getItem().getCapability(CapabilityHandler.CAPABILITY_EXPTOME, null);
                            int num = cap.getExperienceValue() / 100;
                            worldIn.removeEntity(i);
                            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.upgradeCore, num)));
                            return doAction(held, player, worldIn);
                        } else if(((EntityItem) i).getItem().getItem() instanceof ItemArmor) {
                            if (((ItemArmor) ((EntityItem) i).getItem().getItem()).armorType == EntityEquipmentSlot.CHEST) {
                                if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 6)));
                                    return doAction(held, player, worldIn);

                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 6)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.forceChest, 1)));
                                    return doAction(held, player, worldIn);
                                }
                            }
                            if (((ItemArmor) ((EntityItem) i).getItem().getItem()).armorType == EntityEquipmentSlot.LEGS) {
                                if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 5)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 5)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.forceLegs, 1)));
                                    return doAction(held, player, worldIn);
                                }
                            }
                            if (((ItemArmor) ((EntityItem) i).getItem().getItem()).armorType == EntityEquipmentSlot.FEET) {
                                if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 3)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 3)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.forceBoots, 1)));
                                    return doAction(held, player, worldIn);
                                }
                            }
                            if (((ItemArmor) ((EntityItem) i).getItem().getItem()).armorType == EntityEquipmentSlot.HEAD) {
                                if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 4)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 4)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.forceHelmet, 1)));
                                    return doAction(held, player, worldIn);
                                }
                            }
                        }
                    }
                }
            }
        }

        return EnumActionResult.PASS;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> lores, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, lores, flagIn);
        stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).addText(lores);
    }

}