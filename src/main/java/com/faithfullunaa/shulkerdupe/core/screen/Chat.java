package com.faithfullunaa.shulkerdupe.core.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class Chat
{
	private static final Minecraft CLIENT = Minecraft.getInstance();

	public static void log(String msg)
	{
		if (CLIENT.player != null)
		{
			Component chat_msg = new TextComponent("[shulkerdupe]: " + msg);
			CLIENT.player.displayClientMessage(chat_msg, false);
		}
	}
}
