
package com.github.qiq.qiqwhitelist;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class Commander implements CommandExecutor {
    public Commander() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 && main.Enable) {
            sender.sendMessage(ChatColor.RED + "/qwl add|delete|on|off");
            return true;
        }else if ((args.length == 0 || !args[0].equalsIgnoreCase("on")) && !main.Enable){
            sender.sendMessage(ChatColor.RED + "请先使用: /qwl on 启用白名单");
        } else {
            String cmd = null;
            cmd = command.getName();
            String player;
            if (cmd.equalsIgnoreCase("qiqwhitelist") && args[0].equalsIgnoreCase("add")) {
                if (!sender.hasPermission("qiqwhitelist.add")) {
                    sender.sendMessage(ChatColor.RED + "你没有权限使用这条指令!");
                    return true;
                } else if (args.length <= 1) {
                    sender.sendMessage(ChatColor.RED + "请使用: /qwl add [username]");
                    return true;
                } else {
                    player = args[1];
                    if (main.use_mysql) {
                        main.link.connect();
                        if (main.link.exists(player)) {
                            sender.sendMessage(ChatColor.RED + "该玩家已经在白名单里了!");
                            return true;
                        } else {
                            main.link.AddData(player);
                            main.link.closeConn();
                            sender.sendMessage(ChatColor.GREEN + "插入成功!");
                            return true;
                        }
                    } else if (main.playerconfigfile.contains("users." + player)) {
                        sender.sendMessage(ChatColor.RED + "该玩家已经在白名单里了!");
                        return true;
                    } else {
                        main.playerconfigfile.set("users." + player, true);
                        try {
                            main.playerconfigfile.save(main.playerConfig);
                        } catch (IOException var8) {
                            var8.printStackTrace();
                        }

                        sender.sendMessage(ChatColor.GREEN + "插入成功!");
                        return true;
                    }
                }
            } else if (cmd.equalsIgnoreCase("qiqwhitelist") && args[0].equalsIgnoreCase("delete")) {
                if (sender.hasPermission("qiqwhitelist.delete")) {
                    if (args.length <= 1) {
                        sender.sendMessage(ChatColor.RED + "请使用: /qwl delete [username]");
                        return true;
                    } else {
                        player = args[1];
                        if (main.use_mysql) {
                            main.link.connect();
                            if (main.link.exists(player)) {
                                main.link.delete(player);
                                main.link.closeConn();
                                sender.sendMessage(ChatColor.GREEN + "删除成功!");
                                return true;
                            } else {
                                sender.sendMessage(ChatColor.RED + "该玩家不存在!");
                                return true;
                            }
                        } else if (main.playerconfigfile.contains("users." + player)) {
                            main.playerconfigfile.set("users." + player, (Object)null);
                            try {
                                main.playerconfigfile.save(main.playerConfig);
                            } catch (IOException var9) {
                                var9.printStackTrace();
                            }
                            sender.sendMessage(ChatColor.GREEN + "删除成功!");
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.RED + "该玩家不存在!");
                            return true;
                        }
                    }
                }else {
                    sender.sendMessage(ChatColor.RED + "你没有权限使用这条指令!");
                    return true;
                }
            } else if (cmd.equalsIgnoreCase("qiqwhitelist") && args[0].equalsIgnoreCase("on")) {
                if (sender.hasPermission("qiqwhitelist.control")) {
                    if (!main.Enable) {
                        main.Enable = true;
                        main.config.set("Enable", true);
                        sender.sendMessage(ChatColor.GREEN + "白名单已开启!");
                    }else{
                        sender.sendMessage(ChatColor.RED + "白名单已在开启状态!");
                    }
                }else {
                    sender.sendMessage(ChatColor.RED + "你没有权限使用这条指令!");
                }
                return true;
            } else if (cmd.equalsIgnoreCase("qiqwhitelist") && args[0].equalsIgnoreCase("off")) {
                if (sender.hasPermission("qiqwhitelist.control")) {
                    if (main.Enable) {
                        main.Enable = false;
                        main.config.set("Enable", false);
                        sender.sendMessage(ChatColor.RED + "白名单已关闭!");
                    }else{
                        sender.sendMessage(ChatColor.RED + "白名单未启用!");
                    }
                }else {
                    sender.sendMessage(ChatColor.RED + "你没有权限使用这条指令!");
                }
                return true;
            } else if (cmd.equalsIgnoreCase("qiqwhitelist") && main.Enable) {
                sender.sendMessage(ChatColor.RED + "/qwl add|delete|on|off");
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "请先使用: /qwl on 启用白名单");
            }
        }
        return true;
    }
}
