package net.ethanol.de.modules;

import meteordevelopment.meteorclient.systems.modules.Module;
import net.ethanol.de.Client;
import net.ethanol.de.gui.EthanolScreen;

public class Ethanol extends Module {
    public Ethanol() {
        super(Client.Main, "Ethanol", "Ethanol");
        this.runInMainMenu = true;
    }

    @Override
    public void onActivate() {
        mc.setScreen(new EthanolScreen());
        toggle();
        super.onActivate();
    }
}
