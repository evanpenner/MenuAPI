package com.fireboyev.menuapi;

import java.util.HashMap;

import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import com.fireboyev.menuapi.objects.Menu;
import com.google.inject.Inject;

@Plugin(id = "menuapi", name = "MenuAPI", version = "1.0")
public class MenuAPI {
	private static HashMap<Player, Menu> menus;
	@Inject
	private Logger logger;
	private static MenuAPI plugin;

	@Listener
	public void onServerStart(GameStartedServerEvent event) {
		plugin = this;
		menus = new HashMap<Player, Menu>();
		logger.info("MenuAPI Loaded!");
	}

	public static Object getInstance() {
		return plugin;
	}

	@Inject
	private void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Logger getLogger() {
		return logger;
	}

	public static void registerViewer(Player player, Menu menu) {
		menus.put(player, menu);
	}

	public static void removeViewer(Player player) {
		menus.remove(player);
	}

	public static Menu getViewingMenu(Player player) {
		return menus.get(player);
	}
}
