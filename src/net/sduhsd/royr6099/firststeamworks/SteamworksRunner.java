package net.sduhsd.royr6099.firststeamworks;

import javax.swing.JFrame;
import java.awt.Component;

public class SteamworksRunner extends JFrame {
	public SteamworksRunner() {
		super("STEAMWORKS");
		setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

		Steamworks game = new Steamworks();
		((Component) game).setFocusable(true);

		getContentPane().add(game);

		setVisible(true);
	}

	public static void main(String args[]) {
		new SteamworksRunner();
	}
}
