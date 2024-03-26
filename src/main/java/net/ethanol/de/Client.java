package net.ethanol.de;

import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rocks.ethanol.ethanolapi.server.listener.EthanolServerListener;

public class Client extends MeteorAddon {

    public static final Logger LOG = LoggerFactory.getLogger(Client.class);
    public static final Category Main = new Category("Autism", Items.BOWL.getDefaultStack());
    public static EthanolServerListener EthanolListener;

    @Override
    public void onInitialize() {
        LOG.info("Initializing Autism Client");

        // Modules

        // Commands

        // Hud

    }

    @Override
    public String getPackage() {
        return "net.ogmur.auth";
    }

//    @Override
//    public GithubRepo getRepo() {
//        return new GithubRepo("PaxCodesSometimes", "Autism", "master");
//    }

    @Override
    public void onRegisterCategories() {
//        Modules.registerCategory(Main);
    }
}
