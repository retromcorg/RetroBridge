package org.retromc.retrobridge.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.retromc.retrobridge.RetroBridge;
import org.retromc.retrobridge.bridge.BridgeModuleType;
import org.retromc.retrobridge.bridge.BridgeProvider;

public class RetroBridgeCommand implements CommandExecutor {
    private final RetroBridge plugin;

    public RetroBridgeCommand(RetroBridge plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("retrobridge.status") && !sender.isOp()) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        if (args.length == 0 || "status".equalsIgnoreCase(args[0])) {
            sender.sendMessage("RetroBridge active providers:");
            BridgeModuleType[] modules = BridgeModuleType.values();
            for (int i = 0; i < modules.length; i++) {
                BridgeModuleType module = modules[i];
                BridgeProvider provider = plugin.getBridgeManager().getActiveProvider(module);
                if (provider == null) {
                    sender.sendMessage("- " + module.name() + ": none");
                } else {
                    String source = String.valueOf(plugin.getBridgeManager().getActiveProviderSource(module));
                    sender.sendMessage("- " + module.name() + ": " + provider.getProviderName()
                            + " (owner=" + provider.getOwningPluginName()
                            + ", source=" + source + ")");
                }
            }
            return true;
        }

        //TODO: Add a reload command

        sender.sendMessage("Usage: /" + label + " [status]");
        return true;
    }
}
