package game.graphics.screens;

import application.GameClient;
import game.graphics.screens.transitions.Transition;

public class ScreenManager 
{
	private static AbstractScreen currentScreen;

	public static AbstractScreen getCurrentScreen()
	{
		return currentScreen;
	}
	
	public static void setScreen(AbstractScreen screen)
	{
		AbstractScreen oldScreen = currentScreen;
		screen.load(); 
		screen.update(0);
		currentScreen = screen;
	
		if(oldScreen != null && currentScreen != screen)
			oldScreen.unload();
	}

	public static void setScreen(AbstractScreen screen, Transition transition1, Transition transition2)
	{
		if(ScreenManager.getCurrentScreen() == null)
			ScreenManager.setScreen(screen);
		
		TransitionScreen transitionScreen = new TransitionScreen(GameClient.getInstance(), transition1, screen, transition2);
		transitionScreen.load();
		currentScreen = transitionScreen;
	}
}
