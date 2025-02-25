package com.aizistral.enigmaticlegacy.registries;

import com.aizistral.enigmaticlegacy.api.generic.ModRegistry;
import com.aizistral.enigmaticlegacy.client.Quote;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegisterEvent;

public class EnigmaticSounds extends AbstractRegistry<SoundEvent> {
	private static final EnigmaticSounds INSTANCE = new EnigmaticSounds();

	@ObjectHolder(value = MODID + ":misc.hhon", registryName = "sound_event")
	public static final SoundEvent CHARGED_ON = null;

	@ObjectHolder(value = MODID + ":misc.hhoff", registryName = "sound_event")
	public static final SoundEvent CHARGED_OFF = null;

	@ObjectHolder(value = MODID + ":misc.shield_trigger", registryName = "sound_event")
	public static final SoundEvent SHIELD_TRIGGER = null;

	@ObjectHolder(value = MODID + ":misc.deflect", registryName = "sound_event")
	public static final SoundEvent DEFLECT = null;

	@ObjectHolder(value = MODID + ":misc.write", registryName = "sound_event")
	public static final SoundEvent WRITE = null;

	@ObjectHolder(value = MODID + ":misc.learn", registryName = "sound_event")
	public static final SoundEvent LEARN = null;

	@ObjectHolder(value = MODID + ":misc.sword_hit_reject", registryName = "sound_event")
	public static final SoundEvent SWORD_HIT_REJECT = null;

	@ObjectHolder(value = MODID + ":misc.uneat", registryName = "sound_event")
	public static final SoundEvent EAT_REVERSE = null;

	private EnigmaticSounds() {
		super(ForgeRegistries.SOUND_EVENTS);
		this.register("misc.hhon");
		this.register("misc.hhoff");
		this.register("misc.shield_trigger");
		this.register("misc.deflect");
		this.register("misc.write");
		this.register("misc.learn");
		this.register("misc.sword_hit_reject");
		this.register("misc.uneat");
	}

	private void register(String name) {
		this.register(name, () -> new SoundEvent(new ResourceLocation(MODID, name)));
	}

	@Override
	protected void onRegister(RegisterEvent event) {
		Quote.getByID(0);
	}

}
