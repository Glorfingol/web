package cmpl.web.menu;


/**
 * Enumeration pour les sous menu du menu du front office
 * 
 * @author Louis
 *
 */
public enum SUB_MENU {

  LASER_SOPRANO("soprano.href", "soprano.label", MENUS.TECHNICS),
  LASER_HARMONY_ERBIUM("harmony.erbium.href", "harmony.erbium.label", MENUS.TECHNICS),
  LASER_HARMONY_SWITCHED("harmony.switched.href", "harmony.switched.label", MENUS.TECHNICS),
  LASER_HARMONY_DYE("harmony.dye.href", "harmony.dye.label", MENUS.TECHNICS),
  LASER_HARMONY_LONG_PULSE("harmony.long.pulse.href", "harmony.long.pulse.label", MENUS.TECHNICS),
  LEGATO("legato.href", "legato.label", MENUS.TECHNICS),
  EXILIS_SKIN("exilis.skin.href", "exilis.skin.label", MENUS.TECHNICS),
  EXILIS_GENITAL("exilis.genital.href", "exilis.genital.label", MENUS.TECHNICS);

  private String href;
  private String label;
  private MENUS parent;

  private SUB_MENU(String href, String label, MENUS parent) {
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

  public MENUS getParent() {
    return parent;
  }

}
