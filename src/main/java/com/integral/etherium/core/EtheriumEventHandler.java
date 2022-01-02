package com.integral.etherium.core;

import javax.annotation.Nullable;

import com.integral.enigmaticlegacy.objects.Vector3;
import com.integral.etherium.items.EtheriumArmor;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.PhantomEntity;
import net.minecraft.world.entity.passive.AnimalEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrowEntity;
import net.minecraft.world.entity.projectile.DamagingProjectileEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.ItemLootEntry;
import net.minecraft.world.level.storage.loot.LootEntry;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.RandomValueRange;
import net.minecraft.world.level.storage.loot.LootPool.Builder;
import net.minecraft.world.level.storage.loot.functions.SetCount;
import net.minecraft.world.item.alchemy.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class EtheriumEventHandler {
	private final IEtheriumConfig config;
	private final Item etheriumOre;

	public EtheriumEventHandler(IEtheriumConfig config, Item etheriumOre) {
		this.config = config;
		this.etheriumOre = etheriumOre;
	}

	@SubscribeEvent
	public void onEntityHurt(LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof Player) {
			Player player = (Player) event.getEntityLiving();

			/*
			 * Handler for knockback feedback and damage reduction of Etherium Armor Shield.
			 */

			if (EtheriumArmor.hasShield(player)) {
				if (event.getSource().getDirectEntity() instanceof LivingEntity) {
					LivingEntity attacker = ((LivingEntity) event.getSource().getEntity());
					Vector3 vec = Vector3.fromEntityCenter(player).subtract(Vector3.fromEntityCenter(event.getSource().getEntity())).normalize();
					attacker.knockback(0.75F, vec.x, vec.z);
					player.level.playSound(null, player.blockPosition(), this.config.getShieldTriggerSound(), SoundSource.PLAYERS, 1.0F, 0.9F + (float) (Math.random() * 0.1D));
					player.level.playSound(null, player.blockPosition(), this.config.getShieldTriggerSound(), SoundSource.PLAYERS, 1.0F, 0.9F + (float) (Math.random() * 0.1D));
				}

				event.setAmount(event.getAmount() * this.config.getShieldReduction().asModifierInverted());
			}
		}
	}

	@SubscribeEvent
	public void onEntityAttacked(LivingAttackEvent event) {
		if (event.getEntityLiving().level.isClientSide)
			return;

		/*
		 * Handler for immunities and projectile deflection.
		 */

		if (event.getEntityLiving() instanceof Player) {
			Player player = (Player) event.getEntityLiving();

			if (event.getSource().getDirectEntity() instanceof DamagingProjectileEntity || event.getSource().getDirectEntity() instanceof AbstractArrowEntity) {
				if (EtheriumArmor.hasShield(player)) {
					event.setCanceled(true);

					player.level.playSound(null, player.blockPosition(), this.config.getShieldTriggerSound(), SoundSource.PLAYERS, 1.0F, 0.9F + (float) (Math.random() * 0.1D));
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onLootTablesLoaded(LootTableLoadEvent event) {
		if (!this.config.isStandalone())
			return;

		if (event.getName().equals(LootTables.END_CITY_TREASURE)) {
			LootPool epic = constructLootPool("etherium", -11F, 2F,
					ItemLootEntry.lootTableItem(this.etheriumOre)
					.setWeight(60)
					.apply(SetCount.setCount(RandomValueRange.between(1.0F, 2F)))
					);

			LootTable modified = event.getTable();
			modified.addPool(epic);
			event.setTable(modified);
		}
	}

	private static LootPool constructLootPool(String poolName, float minRolls, float maxRolls, @Nullable LootEntry.Builder<?>... entries) {
		Builder poolBuilder = LootPool.lootPool();
		poolBuilder.name(poolName);
		poolBuilder.setRolls(RandomValueRange.between(minRolls, maxRolls));

		for (LootEntry.Builder<?> entry : entries) {
			if (entry != null) {
				poolBuilder.add(entry);
			}
		}

		LootPool constructedPool = poolBuilder.build();

		return constructedPool;

	}

}
