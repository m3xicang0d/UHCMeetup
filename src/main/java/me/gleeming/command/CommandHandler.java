package me.gleeming.command;

import com.google.common.reflect.ClassPath;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.gleeming.command.help.Help;
import me.gleeming.command.help.HelpNode;
import me.gleeming.command.node.CommandNode;
import me.gleeming.command.paramter.ParamProcessor;
import me.gleeming.command.paramter.Processor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@SuppressWarnings("UnstableApiUsage")
public class CommandHandler {
    @Getter @Setter private static JavaPlugin javaPlugin;
    
    public static void init(JavaPlugin javaPlugin) {
        javaPlugin = javaPlugin;
    } 
    
    /**
     * Registers commands based off a file path
     * @param path Path
     */
    @SneakyThrows
    public static void registerCommands(String path) {
        ClassPath.from(javaPlugin.getClass().getClassLoader()).getAllClasses().stream()
                .filter(info -> info.getPackageName().startsWith(path))
                .forEach(info -> registerCommands(info.load()));
    }

    /**
     * Registers the commands in the class
     * @param commandClass Class
     */
    @SneakyThrows
    public static void registerCommands(Class<?> commandClass) {
        registerCommands(commandClass.newInstance());
    }

    /**
     * Registers the commands in the class
     * @param commandClass Class
     */
    private static void registerCommands(Object commandClass) {
        Arrays.stream(commandClass.getClass().getDeclaredMethods()).forEach(method -> {
            Command command = method.getAnnotation(Command.class);
            if(command == null) return;

            new CommandNode(commandClass, method, command);
        });

        Arrays.stream(commandClass.getClass().getDeclaredMethods()).forEach(method -> {
            Help help = method.getAnnotation(Help.class);
            if(help == null) return;

            HelpNode helpNode = new HelpNode(commandClass, help.names(), help.permission(), method);
            CommandNode.getNodes().forEach(node -> node.getNames().forEach(name -> Arrays.stream(help.names())
                    .map(String::toLowerCase)
                    .filter(helpName -> name.toLowerCase().startsWith(helpName))
                    .forEach(helpName -> node.getHelpNodes().add(helpNode))));
        });
    }

    /**
     * Registers processors based off a file path
     * @param path Path
     */
    @SneakyThrows
    public static void registerProcessors(String path) {
        ClassPath.from(javaPlugin.getClass().getClassLoader()).getAllClasses().stream()
                .filter(info -> info.getPackageName().startsWith(path))
                .filter(info -> info.load().getSuperclass().equals(Processor.class))
                .forEach(info -> {
                    try { ParamProcessor.createProcessor((Processor<?>) info.load().newInstance());
                    } catch(Exception exception) { exception.printStackTrace(); }
                });
    }

}
