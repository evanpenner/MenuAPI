package com.fireboyev.menuapi.objects;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;

public interface ButtonExecutor {
	void onButtonClick(ClickInventoryEvent event, Menu menu, Button button, Player player);
}
