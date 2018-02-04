package com.fireboyev.menutest;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStack.Builder;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.text.Text;

import com.fireboyev.menuapi.objects.Button;
import com.fireboyev.menuapi.objects.ButtonExecutor;
import com.fireboyev.menuapi.objects.Menu;
import com.fireboyev.menutest.menus.HomeMenu;

public class GuiCommand implements CommandExecutor {
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (src instanceof Player) {
			Player player = (Player) src;
			Menu menu = new Menu("Gui Test", new InventoryDimension(9, 2));
			ItemStack sword = Sponge.getRegistry().createBuilder(Builder.class).itemType(ItemTypes.DIAMOND_SWORD)
					.build();
			Button button = new Button(sword, 12);
			menu.registerButton(button);
			button.setExecutor(new ButtonExecutor() {
				public void onButtonClick(ClickInventoryEvent event, Menu menu, Button button, Player player) {
					player.sendMessage(Text.of("IT WORKS!"));
					Button b;
					System.out.println(button.getItemStack().getItem().getType().getName());
					if (button.getItemStack().getItem().getType().equals(ItemTypes.DIAMOND_SWORD)) {
						button.setItemStack(
								Sponge.getRegistry().createBuilder(Builder.class).itemType(ItemTypes.APPLE).build());
						b = new Button(Sponge.getRegistry().createBuilder(Builder.class)
								.itemType(ItemTypes.CRAFTING_TABLE).build(), 0);
					} else {
						button.setItemStack(Sponge.getRegistry().createBuilder(Builder.class)
								.itemType(ItemTypes.DIAMOND_SWORD).build());
						b = new Button(
								Sponge.getRegistry().createBuilder(Builder.class).itemType(ItemTypes.ANVIL).build(), 0);
					}
					menu.registerButton(b);
					menu.refresh(player);
				}
			});
			Button menuOpener = new Button(ItemStack.builder().itemType(ItemTypes.ARROW).build(), 17);
			menu.registerButton(menuOpener);
			menuOpener.setExecutor(new ButtonExecutor() {
				
				@Override
				public void onButtonClick(ClickInventoryEvent event, Menu menu, Button button, Player player) {
					player.sendMessage(Text.of("Opening the home menu..."));
					HomeMenu.create(player);
				}
			});
			menu.Open(player);
		}
		return CommandResult.success();
	}
}
