package org.jzy3d.plot3d.text;

import org.jzy3d.colors.Color;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord2d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.painters.IPainter;
import org.jzy3d.plot3d.text.align.Halign;
import org.jzy3d.plot3d.text.align.Valign;
import org.jzy3d.plot3d.transform.space.SpaceTransformer;

public abstract class AbstractTextRenderer implements ITextRenderer {
  protected SpaceTransformer spaceTransformer;
  protected Coord2d defScreenOffset;
  protected Coord3d defSceneOffset;

  public AbstractTextRenderer() {
    defScreenOffset = new Coord2d();
    defSceneOffset = new Coord3d();
  }

  @Override
  public BoundingBox3d drawText(IPainter painter, String s, Coord3d position, Halign halign,
      Valign valign, Color color) {
    return drawText(painter, s, position, halign, valign, color, defScreenOffset, defSceneOffset);
  }

  @Override
  public BoundingBox3d drawText(IPainter painter, String s, Coord3d position, Halign halign,
      Valign valign, Color color, Coord2d screenOffset) {
    return drawText(painter, s, position, halign, valign, color, screenOffset, defSceneOffset);
  }

  @Override
  public BoundingBox3d drawText(IPainter painter, String s, Coord3d position, Halign halign,
      Valign valign, Color color, Coord3d sceneOffset) {
    return drawText(painter, s, position, halign, valign, color, defScreenOffset, sceneOffset);
  }

  @Override
  public SpaceTransformer getSpaceTransformer() {
    return spaceTransformer;
  }

  @Override
  public void setSpaceTransformer(SpaceTransformer transformer) {
    this.spaceTransformer = transformer;
  }

  protected void glRaster(IPainter painter, Coord3d position, Color color) {
    painter.glColor3f(color.r, color.g, color.b);
    painter.raster(position, spaceTransformer);
  }


}
