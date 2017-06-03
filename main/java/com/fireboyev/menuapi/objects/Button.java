package com.fireboyev.menuapi.objects;

import org.spongepowered.api.item.inventory.ItemStack;

public class Button {
	private ItemStack item;
	private int slot;
	private Menu menu;
	private ButtonExecutor executor;

	public Button(ItemStack item, int slot) {
		this.item = item;
		this.slot = slot;
	}

	public int getSlot() {
		return slot;
	}

	public ItemStack getItemStack() {
		return item;
	}

	public void setItemStack(ItemStack item) {
		getMenu().getSlot(slot).set(item);
		this.item = item;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Menu getMenu() {
		return menu;
	}

	public ButtonExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(ButtonExecutor executor) {
		this.executor = executor;
	}
}
