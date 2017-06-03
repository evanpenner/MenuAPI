package com.fireboyev.menutest;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;

@Plugin(id = "menutest", name = "MenuTest", version = "1.0")
public class MenuTest {
	@Inject
	private Logger logger;
	private static MenuTest plugin;

	@Listener
	public void onServerStart(GameStartedServerEvent event) {
		plugin = this;
		CommandSpec guiCommand = CommandSpec.builder().description(Text.of("Opens The Test GUI"))
				.executor(new GuiCommand()).build();
		Sponge.getCommandManager().register(plugin, guiCommand, "gui");
		logger.info("MenuTest Loaded!");
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
}
