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

import com.fireboyev.menuapi.objects.Button;
import com.fireboyev.menuapi.objects.ButtonExecutor;
import com.fireboyev.menuapi.objects.Menu;

public class GuiCommand implements CommandExecutor {
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (src instanceof Player) {
			Player player = (Player) src;
			Menu menu = new Menu("Gui Test", new InventoryDimension(9, 1));
			ItemStack sword = Sponge.getRegistry().createBuilder(Builder.class).itemType(ItemTypes.DIAMOND_SWORD)
					.build();
			Button button = new Button(sword, 5);
			menu.registerButton(button);
			button.setExecutor(new ButtonExecutor() {
				public void onButtonClick(ClickInventoryEvent event, Menu menu, Button button, Player player) {
					if (button.getItemStack().getItem().equals(ItemTypes.DIAMOND_SWORD))
						button.setItemStack(
								Sponge.getRegistry().createBuilder(Builder.class).itemType(ItemTypes.APPLE).build());
					else
						button.setItemStack(Sponge.getRegistry().createBuilder(Builder.class)
								.itemType(ItemTypes.DIAMOND_SWORD).build());
					menu.refresh(player);
				}
			});
			menu.Open(player);
		}
		return CommandResult.success();
	}
}
