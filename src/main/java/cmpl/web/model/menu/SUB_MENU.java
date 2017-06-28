package cmpl.web.model.menu;

/**
 * Enumeration pour les sous menu du menu du front office
 * 
 * @author Louis
 *
 */
public enum SUB_MENU {

  LASER_SOPRANO("soprano.href", "soprano.label", MENU.TECHNICS),
  LASER_HARMONY_ERBIUM("harmony.erbium.href", "harmony.erbium.label", MENU.TECHNICS),
  LASER_HARMONY_SWITCHED("harmony.switched.href", "harmony.switched.label", MENU.TECHNICS),
  LASER_HARMONY_DYE("harmony.dye.href", "harmony.dye.label", MENU.TECHNICS),
  LASER_HARMONY_LONG_PULSE("harmony.long.pulse.href", "harmony.long.pulse.label", MENU.TECHNICS),
  LEGATO("legato.href", "legato.label", MENU.TECHNICS),
  EXILIS_SKIN("exilis.skin.href", "exilis.skin.label", MENU.TECHNICS),
  EXILIS_GENITAL("exilis.genital.href", "exilis.genital.label", MENU.TECHNICS);

  private String href;
  private String label;
  private MENU parent;

  private SUB_MENU(String href, String label, MENU parent) {
    this.label = label;
    this.href = href;
    this.parent = parent;
  }

  public String getHref() {
    return href;
  }

  public String getLabel() {
    return label;
  }

  public MENU getParent() {
    return parent;
  }

}
