package com.integral.enigmaticlegacy.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.integral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.integral.enigmaticlegacy.items.generic.ItemBase;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAction;
import net.minecraft.world.item.alchemy.EffectInstance;
import net.minecraft.world.item.alchemy.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Deprecated since update 1.2.0.
 * @author Integral
 */

@Deprecated
public class HastePotion extends ItemBase {

	public List<EffectInstance> effectList;

	public HastePotion(Rarity rarity, int duration, int amplifier) {
		super(ItemBase.getDefaultProperties().rarity(rarity).stacksTo(1).tab(null));

		this.effectList = new ArrayList<EffectInstance>();
		this.effectList.add(new EffectInstance(Effects.DIG_SPEED, duration, amplifier, false, true));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
		SuperpositionHandler.addPotionTooltip(this.effectList, stack, list, 1.0F);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		Player player = entityLiving instanceof Player ? (Player) entityLiving : null;
		if (player == null || !player.abilities.instabuild) {
			stack.shrink(1);
		}

		if (player instanceof ServerPlayer) {
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
		}

		if (!worldIn.isClientSide && player != null) {
			for (EffectInstance instance : this.effectList) {
				player.addEffect(new EffectInstance(instance));
			}
		}

		if (player == null || !player.abilities.instabuild) {
			if (stack.isEmpty())
				return new ItemStack(Items.GLASS_BOTTLE);

			if (player != null) {
				player.inventory.add(new ItemStack(Items.GLASS_BOTTLE));
			}
		}

		return stack;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.DRINK;
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, Player playerIn, Hand handIn) {
		playerIn.startUsingItem(handIn);
		return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getItemInHand(handIn));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(ItemStack stack) {
		return true;
	}

}
