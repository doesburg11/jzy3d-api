package org.jzy3d.plot3d.primitives;

import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.IMultiColorable;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.painters.Painter;
import org.jzy3d.plot3d.rendering.view.Camera;
import org.jzy3d.plot3d.transform.Transform;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.glu.GLU;

public class ConcurrentScatterMultiColor extends ScatterMultiColor implements IMultiColorable {
    public ConcurrentScatterMultiColor(Coord3d[] coordinates, Color[] colors, ColorMapper mapper) {
        this(coordinates, colors, mapper, 1.0f);
    }

    public ConcurrentScatterMultiColor(Coord3d[] coordinates, ColorMapper mapper) {
        this(coordinates, null, mapper, 1.0f);
    }

    public ConcurrentScatterMultiColor(Coord3d[] coordinates, Color[] colors, ColorMapper mapper, float width) {
        super(coordinates, colors, mapper, width);
    }
    
    @Override
    public void draw(Painter painter, GL gl, GLU glu, Camera cam) {
        doTransform(painter, gl, glu, cam);

        painter.glPointSize(width);
        painter.glBegin(GL.GL_POINTS);

        if (coordinates != null) {
            synchronized (coordinates) { // difference with super type is here
                for (Coord3d coord : coordinates) {
                    Color color = mapper.getColor(coord); 
                    painter.color(color);
                    painter.vertex(coord, spaceTransformer);
                }
            }
        }
        painter.glEnd();

        doDrawBounds(painter, gl, glu, cam);
    }

    @Override
    public void applyGeometryTransform(Transform transform) {
        synchronized (coordinates) {
            for (Coord3d c : coordinates) {
                c.set(transform.compute(c));
            }
        }
        updateBounds();
    }

    @Override
    public void updateBounds() {
        bbox.reset();
        synchronized (coordinates) {
            for (Coord3d c : coordinates)
                bbox.add(c);
        }
    }

}