package com.faithfullunaa.shulkerdupe.core.screen;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;

public class Inventory
{
	private static final Minecraft CLIENT = Minecraft.getInstance();

	public static void move_all_items()
	{
		for (int i = 0; i < 27; i++)
		{
			move_item(i);
		}
	}

	public static void move_item(int slot)
	{
		Chat.log("Moving item from Slot " + slot);

		if (CLIENT.screen instanceof ShulkerBoxScreen)
		{
			ShulkerBoxScreen screen = (ShulkerBoxScreen) CLIENT.screen;
			Int2ObjectArrayMap<ItemStack> stack = new Int2ObjectArrayMap<>();

			stack.put(slot, screen.getMenu().getSlot(slot).getItem());
			Chat.log("Sending QUICK_MOVE click packet!");
			CLIENT.level.sendPacketToServer(new ServerboundContainerClickPacket(screen.getMenu().containerId, 0, slot, 0, ClickType.QUICK_MOVE, screen.getMenu().getSlot(0).getItem(), (Int2ObjectMap) stack));
		}
	}
}
