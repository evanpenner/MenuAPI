package com.fireboyev.menutest.menus;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.text.Text;

import com.fireboyev.menuapi.objects.Button;
import com.fireboyev.menuapi.objects.ButtonExecutor;
import com.fireboyev.menuapi.objects.Menu;

public class HomeMenu {
	public static void create(Player player) {
		Menu menu = new Menu("Menu", new InventoryDimension(9, 3));
		Button toOpen = new Button(ItemStack.builder().itemType(ItemTypes.ANVIL).build(), 10);
		menu.registerButton(toOpen);
		toOpen.setExecutor(new ButtonExecutor() {
			
			@Override
			public void onButtonClick(ClickInventoryEvent event, Menu menu, Button button, Player player) {
				player.sendMessage(Text.of("Sub Menu"));
				SubMenu.create(player);
			}
		});
		menu.Open(player);
	}
}
