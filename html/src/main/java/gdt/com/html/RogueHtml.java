package gdt.com.html;

import gdt.com.core.Rogue;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class RogueHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new Rogue();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
