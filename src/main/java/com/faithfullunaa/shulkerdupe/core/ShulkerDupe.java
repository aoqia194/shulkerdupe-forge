package com.faithfullunaa.shulkerdupe.core;

import static com.faithfullunaa.shulkerdupe.core.utils.Shared.should_dupe;
import static com.faithfullunaa.shulkerdupe.core.utils.Shared.should_dupe_all;

import org.slf4j.Logger;

import com.faithfullunaa.shulkerdupe.core.screen.Chat;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("shulkerdupe")
public class ShulkerDupe
{
	// Directly reference a slf4j logger
	private static final Minecraft CLIENT = Minecraft.getInstance();
	private static final Logger LOGGER = LogUtils.getLogger();

	public static boolean fra = true;
	public static int thex = 0;
	public static int they = 0;

	public ShulkerDupe()
	{
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event)
	{
		LOGGER.info("Hello!");
		// PacketHandler.init();
	}

	// JUICE

	public static void set_fra(boolean fra)
	{
		ShulkerDupe.fra = fra;
	}

	@SubscribeEvent
	public void tick(final TickEvent.ClientTickEvent event)
	{
		boolean is_shulker_opened = (CLIENT.screen instanceof ShulkerBoxScreen);

		if (should_dupe || should_dupe_all)
		{
			HitResult hit = CLIENT.hitResult;

			if (hit instanceof BlockHitResult)
			{
				BlockHitResult block_hit = (BlockHitResult) hit;

				if (CLIENT.level.getBlockState(block_hit.getBlockPos()).getBlock() instanceof ShulkerBoxBlock && is_shulker_opened)
				{
					CLIENT.gameMode.continueDestroyBlock(block_hit.getBlockPos(), Direction.DOWN);
				} else
				{
					Chat.log("You need to be looking at the shulker box!");
					CLIENT.player.closeContainer();

					should_dupe = false;
					should_dupe_all = false;
				}
			}
		}

		if (is_shulker_opened)
		{

		} else
		{
			set_fra(true);
		}
	}
}
