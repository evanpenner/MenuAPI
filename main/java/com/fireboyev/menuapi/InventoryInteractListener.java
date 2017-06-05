package com.fireboyev.menuapi;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.util.Tristate;

import com.fireboyev.menuapi.objects.Button;
import com.fireboyev.menuapi.objects.ButtonExecutor;
import com.fireboyev.menuapi.objects.Menu;

public class InventoryInteractListener {
	@Listener
	public void onClose(InteractInventoryEvent.Close ev, @First Player player) {
		MenuAPI.removeViewer(player);
	}

	@Listener(order = Order.EARLY)
	public void onClick(ClickInventoryEvent event, @First Player player,
			@Getter("getTargetInventory") Container container) {
		if (isInInventoryMenu(player)) {
			if (!event.getTargetInventory().equals(player.getInventory())) {
				Menu menu = MenuAPI.getViewingMenu(player);
				SlotIndex slotPos = event.getTransactions().get(0).getSlot().getProperty(SlotIndex.class, "slotindex")
						.get();
				Button button = menu.getButtonByPos(slotPos);
				// System.out.println("SLOT POSITION: " + slotPos.getValue());
				ButtonExecutor exe = button.getExecutor();
				if (exe != null) {
					exe.onButtonClick(event, menu, button, player);
				}
			}
		}
	}

	@Listener(order = Order.LATE)
	@IsCancelled(Tristate.FALSE)
	public void onClick(ClickInventoryEvent ev, @First Player player) {
		if (isInInventoryMenu(player)) {
			ev.setCancelled(true);
		}
	}

	@Listener(order = Order.LATE)
	@IsCancelled(Tristate.FALSE)
	public void onDrop(DropItemEvent ev, @First Player player) {
		if (isInInventoryMenu(player)) {
			ev.setCancelled(true);
		}
	}

	private boolean isInInventoryMenu(Player player) {
		return MenuAPI.getViewingMenu(player) != null;
	}
}
