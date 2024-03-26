package net.ethanol.de;

import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.ethanol.de.modules.Ethanol;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rocks.ethanol.ethanolapi.server.listener.EthanolServerListener;

public class Client extends MeteorAddon {
    public static final Logger LOG = LoggerFactory.getLogger(Client.class);
    public static final Category Main = new Category("Ethanol", Items.BOWL.getDefaultStack());
    public static EthanolServerListener EthanolListener;

    @Override
    public void onInitialize() {
        Modules.get().add(new Ethanol());
    }

    @Override
    public String getPackage() {
        return "net.ethanol.de";
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(Main);
    }
}
