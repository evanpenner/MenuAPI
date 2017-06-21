package com.fireboyev.menuapi.objects;

import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.SlotIndex;

public class Button {
	private ItemStack item;
	private Menu menu;
	private ButtonExecutor executor;
	private SlotIndex pos;
	private int rawpos;

	public Button(ItemStack item, int index) {
		this.item = item;
		this.pos = new SlotIndex(index);
		this.rawpos = index;
	}

	public SlotIndex getSlotIndex() {
		return pos;
	}

	public ItemStack getItemStack() {
		return item;
	}

	public void setItemStack(ItemStack item) {
		// getMenu().getSlot(getSlotIndex()).set(item); shouldn't need this now.
		this.item = item;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public int getRawPos() {
		return rawpos;
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
