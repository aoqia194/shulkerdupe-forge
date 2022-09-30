package com.faithfullunaa.shulkerdupe.core.event;

import static com.faithfullunaa.shulkerdupe.core.screen.Inventory.move_all_items;
import static com.faithfullunaa.shulkerdupe.core.screen.Inventory.move_item;
import static com.faithfullunaa.shulkerdupe.core.utils.Shared.enabled;
import static com.faithfullunaa.shulkerdupe.core.utils.Shared.should_dupe;
import static com.faithfullunaa.shulkerdupe.core.utils.Shared.should_dupe_all;

import com.faithfullunaa.shulkerdupe.core.ShulkerDupe;
import com.faithfullunaa.shulkerdupe.core.screen.Chat;
import com.faithfullunaa.shulkerdupe.core.utils.Shared;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = "shulkerdupe", bus = Bus.FORGE, value = Dist.CLIENT)
public class EventHandler
{
	private static Minecraft CLIENT = Minecraft.getInstance();

	@SubscribeEvent
	public static void on_chat(ClientChatEvent event)
	{
		String message = event.getOriginalMessage();
		if (message.equalsIgnoreCase("^shulkerdupe"))
		{
			Shared.enabled = !Shared.enabled;
			Chat.log(Shared.enabled ? "You can dupe now!" : "You can no longer dupe.");

			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void on_break_block(BlockEvent.BreakEvent event)
	{
		HitResult hit = CLIENT.hitResult;

		if (hit instanceof BlockHitResult)
		{
			if (event.getState().getBlock() instanceof ShulkerBoxBlock)
			{
				if (should_dupe)
				{
					move_item(0);
					should_dupe = false;
				} else if (should_dupe_all)
				{
					move_all_items();
					should_dupe_all = false;
				}
			}
		}
	}

	@SubscribeEvent
	public static void on_init_screenevent(ScreenEvent.InitScreenEvent.Post event)
	{
		Screen screen = event.getScreen();

		if (screen != null && screen instanceof ShulkerBoxScreen)
		{
			if (enabled)
			{
				if (get_fra())
				{
					set_fra(false);

					ShulkerDupe.thex = screen.width;
					ShulkerDupe.they = screen.height;

					event.addListener(new Button(((screen.width / 2) - 90), (((screen.height / 2) + 35) - 145), 50, 20, new TextComponent("Dupe"), (button) -> on_press_dupe()));
					event.addListener(new Button(((screen.width / 2) + 40), (((screen.height / 2) + 35) - 145), 50, 20, new TextComponent("Dupe All"), (button) -> on_press_dupe_all()));
				}

				if (screen.width != ShulkerDupe.thex || screen.height != ShulkerDupe.they)
				{
					set_fra(true);
				}
			}
		}
	}

	private static boolean get_fra()
	{
		return ShulkerDupe.fra;
	}

	private static void set_fra(boolean fra)
	{
		ShulkerDupe.fra = fra;
	}

	private static void on_press_dupe()
	{
		if (should_dupe_all)
			should_dupe_all = false;

		should_dupe = true;
	}

	private static void on_press_dupe_all()
	{
		if (should_dupe)
			should_dupe = false;

		should_dupe_all = true;
	}
}
