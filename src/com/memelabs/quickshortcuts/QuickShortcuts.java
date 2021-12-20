package com.memelabs.quickshortcuts;

import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.graphics.drawable.Icon;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.YailList;

import java.util.Arrays;

public class QuickShortcuts extends AndroidNonvisibleComponent {

  public Activity activity;
  public Context context;
  public Intent intent;
  public Icon icon;

  public QuickShortcuts(ComponentContainer container) {
    super(container.$form());
    activity = container.$context();
    context = activity;
  }

  @SimpleFunction(description = "Creates a shortcut with the given name, icon and starting screen.")
  public void CreateShortcut(String id ,String name, String description, String icon, String intent) {
    ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
    String packageName = context.getClass().getPackage().getName();
    Intent shortcutIntent;
    try {
      shortcutIntent = new Intent(Intent.ACTION_VIEW, null, context, Class.forName(packageName + "." + intent));
    } catch (Exception e) {
      throw new YailRuntimeError(e.toString(), "Exception");
    }
    ShortcutInfo shortcut = new ShortcutInfo.Builder(context, id)
      .setShortLabel(name)
      .setLongLabel(description)
      .setIcon(Icon.createWithContentUri("//" + icon))
      .setIntent(shortcutIntent)
      .build();
    
    shortcutManager.pushDynamicShortcut(shortcut);
  }

  @SimpleFunction(description = "Removes the shortcut with the given name.")
  public void RemoveShortcut(String id) {
    ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
    shortcutManager.removeDynamicShortcuts(Arrays.asList(id));
  }

  @SimpleFunction(description = "Checks whether the user's device supports shortcuts.")
  public boolean SupportsShortcuts() {
    ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
    return shortcutManager.isRequestPinShortcutSupported();
  }
  
  @SimpleFunction(description = "Removes all shortcuts.")
  public void RemoveAllShortcuts() {
    ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
    shortcutManager.removeAllDynamicShortcuts();
  }
}