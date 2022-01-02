package com.integral.enigmaticlegacy.packets.clients;

import java.util.function.Supplier;

import com.integral.enigmaticlegacy.handlers.SuperpositionHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * Packet for bursting out a bunch of dragon breath particles.
 * @author Integral
 */

public class PacketRecallParticles {
	
	private double x;
	private double y;
	private double z;
	private int num;
	private boolean check;

	  public PacketRecallParticles(double x, double y, double z, int number, boolean checkSettings) {
	    this.x = x;
	    this.y = y;
	    this.z = z;
	    this.num = number;
	    this.check = checkSettings;
	  }

	  public static void encode(PacketRecallParticles msg, PacketBuffer buf) {
	    buf.writeDouble(msg.x);
	    buf.writeDouble(msg.y);
	    buf.writeDouble(msg.z);
	    buf.writeInt(msg.num);
	    buf.writeBoolean(msg.check);
	  }

	  public static PacketRecallParticles decode(PacketBuffer buf) {
		  return new PacketRecallParticles(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt(), buf.readBoolean());
	  }


	  public static void handle(PacketRecallParticles msg, Supplier<NetworkEvent.Context> ctx) {

		    ctx.get().enqueueWork(() -> {    	
		      ClientPlayer player = Minecraft.getInstance().player;
		      
		    	int amount = msg.num;
			      
			      if (msg.check)
			    	  amount *= SuperpositionHandler.getParticleMultiplier();
			      
			      for (int counter = 0; counter <= amount; counter++)
		    		player.level.addParticle(ParticleTypes.DRAGON_BREATH, true, msg.x, msg.y, msg.z, (Math.random()-0.5D)*0.2D, (Math.random()-0.5D)*0.2D, (Math.random()-0.5D)*0.2D);
		      
		      
		    });
		    ctx.get().setPacketHandled(true);
	  }

}
