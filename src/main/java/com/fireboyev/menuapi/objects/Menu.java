package com.fireboyev.menuapi.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.Inventory.Builder;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.type.OrderedInventory;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import com.fireboyev.menuapi.MenuAPI;

public class Menu {
	private List<Button> buttons;
	private Inventory inv;
	private String name;

	public Menu(String name, InventoryDimension size) {
		this.buttons = new ArrayList<Button>();
		Inventory inv = ((Builder) Sponge.getRegistry().createBuilder(Builder.class)).of(InventoryArchetypes.CHEST)
				.property(InventoryDimension.PROPERTY_NAME, size)
				.property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of(name))).build(MenuAPI.getInstance());
		this.inv = inv;
		this.name = name;
	}

	public void registerButton(Button button) {
		button.setMenu(this);
		getSlot(button.getRawPos()).set(button.getItemStack());
		buttons.add(button);
	}

	public boolean unregisterButton(Button button) {
		return buttons.remove(button);
	}

	public boolean unregisterButton(SlotIndex index) {
		return buttons.remove(getButtonByPos(index));
	}

	public boolean unregisterButton(int index) {
		return unregisterButton(new SlotIndex(index));
	}

	public String getName() {
		return name;
	}

	public void UnregisterButtons() {
		buttons.clear();
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public void refresh(Player player) {
		Task.Builder taskBuilder = Sponge.getRegistry().createBuilder(Task.Builder.class);
		taskBuilder.execute(new Runnable() {
			public void run() {
				inv.clear();
				for (Button button : buttons) {
					getSlot(button.getSlotIndex()).set(button.getItemStack());
				}
			}
		}).delayTicks(1).submit(MenuAPI.getInstance());
	}

	public Button getButtonByPos(SlotIndex pos) {
		for (Button button : buttons) {
			if (button.getSlotIndex().getValue() == pos.getValue()) {
				return button;
			}
		}
		return new Button(null, pos.getValue());
	}

	public Slot getSlot(int pos) {
		return getSlot(new SlotIndex(pos));
	}

	public Slot getSlot(SlotIndex pos) {
		OrderedInventory o = inv.query(OrderedInventory.class);
		Slot slot = o.getSlot(pos).get();
		return slot;
	}

	public SlotIndex getSlotPos(Slot slot) {
		return slot.getProperty(SlotIndex.class, "slotindex").get();
	}

	public void registerButtons(Collection<Button> buttons) {
		buttons.addAll(buttons);
		for (Button button : buttons) {
			button.setMenu(this);
		}
	}

	public void Open(Player player) {
		MenuAPI.registerViewer(player, this);
		player.openInventory(inv, Cause.builder().named("Menu Open", MenuAPI.getInstance()).build());
	}

}
