package com.fireboyev.menuapi.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.type.GridInventory;
import org.spongepowered.api.item.inventory.type.OrderedInventory;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import com.fireboyev.menuapi.MenuAPI;

public class Menu {
	private List<Button> buttons;
	private Inventory inv;
	private String name;

	public Menu(String name, InventoryDimension size) {
		this(Text.of(name), size);
	}
	public Menu(Text name, InventoryDimension size) {
		this.buttons = new ArrayList<Button>();
		this.inv = Inventory.builder().of(InventoryArchetypes.MENU_GRID).property(InventoryTitle.of(name))
				.property(size).build(MenuAPI.getInstance());
		this.name = name.toString();
	}

	public void registerButton(Button button) {
		button.setMenu(this);
		getSlot(button.getSlotIndex()).get().set(button.getItemStack());
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
					getSlot(button.getSlotIndex()).get().set(button.getItemStack());
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

	public Optional<Slot> getSlot(int pos) {
		return getSlot(new SlotIndex(pos));
	}

	public Optional<Slot> getSlot(SlotIndex pos) {
		GridInventory g = inv.query(GridInventory.class);
		return g.getSlot(pos);
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
		//see if this fixes problem
		player.closeInventory();
		MenuAPI.removeViewer(player);
		Menu menu = this;
		Task.builder().delayTicks(10).execute(new Runnable() {
			
			@Override
			public void run() {
				MenuAPI.registerViewer(player, menu);
				player.openInventory(inv);
			}
		}).submit(MenuAPI.getInstance());
	}

	public void Close(Player player) {
		player.closeInventory();
		MenuAPI.removeViewer(player);
	}

}
