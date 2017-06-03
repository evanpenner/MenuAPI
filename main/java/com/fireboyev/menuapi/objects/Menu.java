package com.fireboyev.menuapi.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.Inventory.Builder;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;

import com.fireboyev.menuapi.MenuAPI;

public class Menu {
	private List<Button> buttons;
	private Inventory inv;
	private String name;

	public Menu(String name, InventoryDimension size) {
		this.buttons = new ArrayList<Button>();
		Menu menu = this;
		Inventory inv = ((Builder) Sponge.getRegistry().createBuilder(Builder.class)).of(InventoryArchetypes.CHEST)
				.property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of(name)))
				.property(InventoryDimension.PROPERTY_NAME, size).listener(ClickInventoryEvent.class, event -> {
					if (menu != null) {
						Button button = menu.getButtonByItemStack(
								event.getTransactions().get(0).getSlot().peek().get());
						button.getExecutor().onButtonClick(event, menu, button,
								event.getTargetInventory().getViewers().iterator().next());
					}
				}).listener(InteractInventoryEvent.Close.class, event -> {
					MenuAPI.removeViewer(event.getTargetInventory().getViewers().iterator().next());
				}).build(MenuAPI.getInstance());
		this.inv = inv;
		this.name = name;
	}

	public void registerButton(Button button) {
		button.setMenu(this);
		getSlot(button.getSlot()).set(button.getItemStack());
		buttons.add(button);
	}

	public String getName() {
		return name;
	}

	public void refresh(Player player) {
		MenuAPI.removeViewer(player);
		for (Button button : buttons) {
			getSlot(button.getSlot()).set(button.getItemStack());
		}
		MenuAPI.registerViewer(player, this);
	}

	public Slot getSlot(int pos) {
		int index = 0;
		Iterable<Slot> slotIter = inv.slots();
		for (Slot slot : slotIter) {
			if (index == pos) {
				return slot;
			}
			index++;
		}
		return null;
	}

	public void registerButtons(Collection<Button> buttons) {
		buttons.addAll(buttons);
		for (Button button : buttons) {
			button.setMenu(this);
		}
	}

	public Button getButtonByItemStack(ItemStack item) {
		for (Button button : buttons) {
			if (button.getItemStack().equals(item)) {
				return button;
			}
		}
		return null;
	}

	public void Open(Player player) {
		MenuAPI.registerViewer(player, this);
		player.openInventory(inv, Cause.builder().named("Menu Open", MenuAPI.getInstance()).build());
	}

	public List<Button> getButtons() {
		return buttons;
	}
}
