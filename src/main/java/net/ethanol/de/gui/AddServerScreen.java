package net.ethanol.de.gui;

import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.GuiThemes;
import meteordevelopment.meteorclient.gui.WindowScreen;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.Settings;
import meteordevelopment.meteorclient.settings.StringSetting;
import meteordevelopment.meteorclient.systems.Systems;
import net.ethanol.de.Ethanol.EthanolSystem;
import net.minecraft.item.Item;

public class AddServerScreen extends WindowScreen {
    private final Settings settings = new Settings();
    private final SettingGroup sg = settings.getDefaultGroup();

    private final Setting<String> server = sg.add(new StringSetting.Builder()
        .name("Server Key")
        .description("The authkey of the server.")
        .defaultValue("")
        .build()
    );

    WindowScreen parent;
    public AddServerScreen(WindowScreen parent) {
        super(GuiThemes.get(), "Add ServerAuth");
        this.parent = parent;
    }

    @Override
    public void initWidgets() {
        WContainer settingsContainer = add(theme.verticalList()).widget();
        settingsContainer.add(theme.settings(settings)).expandX();

        add(theme.button("Add Server")).expandX().widget().action = () -> {
            Systems.get(EthanolSystem.class).sharedServers.add(server.get());
            close();
        };
    }
}
