package org.example;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;

public class DependencyInjectionPlugin extends JavaPlugin {

    @Inject
    private SimpleCommand command;

    @Override
    public void onEnable() {
        // Fetch dependencies. We only have to do it this way for our main class.
        SimpleBinderModule module = new SimpleBinderModule(this);
        Injector injector = module.createInjector();
        injector.injectMembers(this);

        // Now we can register our command executor
        // Be sure to register the command in your plugin.yml
        this.getCommand("test").setExecutor(this.command);
    }
}
