package com.integral.enigmaticlegacy.items;

import java.util.List;

import javax.annotation.Nullable;

import com.integral.enigmaticlegacy.EnigmaticLegacy;
import com.integral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.integral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.integral.enigmaticlegacy.items.generic.ItemBase;
import com.integral.enigmaticlegacy.items.generic.ItemBasePotion;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AntiforbiddenPotion extends ItemBasePotion {

	public AntiforbiddenPotion() {
		super(getDefaultProperties().stacksTo(1).rarity(Rarity.EPIC));
		this.setRegistryName(new ResourceLocation(EnigmaticLegacy.MODID, "antiforbidden_potion"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.antiforbiddenPotion1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.antiforbiddenPotion2");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
		}
	}

	@Override
	public void onConsumed(World worldIn, Player player, ItemStack potion) {
		EnigmaticLegacy.forbiddenFruit.defineConsumedFruit(player, false);
	}

	@Override
	public boolean canDrink(World world, Player player, ItemStack potion) {
		return EnigmaticLegacy.forbiddenFruit.haveConsumedFruit(player);
	}



}
