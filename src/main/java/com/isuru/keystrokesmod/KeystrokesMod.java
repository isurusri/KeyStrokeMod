package com.isuru.keystrokesmod;

import com.isuru.keystrokesmod.render.GuiScreenKeystrokes;
import com.isuru.keystrokesmod.render.KeystrokesRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@Mod(
    name = "KeystrokesMod",
    modid = "keystrokesmod",
    version = "v1.0",
    acceptedMinecraftVersions = "[1.12.2]")
public class KeystrokesMod {
  private static KeystrokesSettings settings;
  private static KeystrokesRenderer renderer;
  private static boolean openGui;

  @Mod.EventHandler
  public void init(final FMLInitializationEvent event) {
    KeystrokesMod.settings =
        new KeystrokesSettings(new File(Minecraft.getMinecraft().mcDataDir, "keystrokes.dat"));
    KeystrokesMod.renderer = new KeystrokesRenderer();
    try {
      KeystrokesMod.settings.load();
    } catch (IOException e) {
      Logger.getLogger("KeystrokesMod").warning("Failed to load Keystrokes settings file!");
      e.printStackTrace();
    }
    ClientCommandHandler.instance.registerCommand((ICommand) new CommandKeystrokes());
    FMLCommonHandler.instance().bus().register((Object) KeystrokesMod.renderer);
    FMLCommonHandler.instance().bus().register((Object) this);
  }

  @SubscribeEvent
  public void onClientTick(final TickEvent.ClientTickEvent event) {
    if (KeystrokesMod.openGui) {
      Minecraft.getMinecraft().displayGuiScreen((GuiScreen) new GuiScreenKeystrokes());
      KeystrokesMod.openGui = false;
    }
  }

  public static KeystrokesSettings getSettings() {
    return KeystrokesMod.settings;
  }

  public static KeystrokesRenderer getRenderer() {
    return KeystrokesMod.renderer;
  }

  public static void openGui() {
    KeystrokesMod.openGui = true;
  }
}
