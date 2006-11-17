// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.commons.ui.swt.drawing.link;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public abstract class AbstractDrawableLink implements IDrawableLink {

    protected Point point1;

    protected Point point2;

    protected Rectangle boundsOfCalculate;

    protected Integer connectorWidth;

    protected IStyleLink style;

    /**
     * DOC amaumont AbstractDrawableLink constructor comment.
     * @param style
     */
    public AbstractDrawableLink(IStyleLink style) {
        super();
        this.style = style;
    }


    public void draw(GC gc) {
        drawBody(gc);
        drawExtremities(gc);
    }

    /**
     * DOC amaumont Comment method "drawExtremities".
     * 
     * @param gc
     */
    protected void drawExtremities(GC gc) {
        if (style.getExtremity1() != null) {
            style.getExtremity1().draw(gc, point1);
        }
        if (style.getExtremity2() != null) {
            style.getExtremity2().draw(gc, point2);
        }
    }

    protected abstract void drawBody(GC gc);

    /**
     * Getter for point1.
     * 
     * @return the point1
     */
    public Point getPoint1() {
        return this.point1;
    }

    /**
     * Sets the point1.
     * 
     * @param point1 the point1 to set
     */
    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    /**
     * Getter for point2.
     * 
     * @return the point2
     */
    public Point getPoint2() {
        return this.point2;
    }

    /**
     * Sets the point2.
     * 
     * @param point2 the point2 to set
     */
    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    /**
     * Getter for calculateBounds.
     * 
     * @return the calculateBounds
     */
    public Rectangle getBoundsOfCalculate() {
        return this.boundsOfCalculate;
    }

    /**
     * Sets the calculateBounds.
     * 
     * @param calculateBounds the calculateBounds to set
     */
    public void setBoundsOfCalculate(Rectangle calculateBounds) {
        this.boundsOfCalculate = calculateBounds;
    }

    /**
     * Getter for connectorWidth.
     * 
     * @return the connectorWidth
     */
    public Integer getConnectorWidth() {
        return this.connectorWidth;
    }

    /**
     * Sets the connectorWidth.
     * 
     * @param connectorWidth the connectorWidth to set
     */
    public void setConnectorWidth(Integer connectorWidth) {
        this.connectorWidth = connectorWidth;
    }

    /**
     * Getter for style.
     * 
     * @return the style
     */
    public IStyleLink getStyle() {
        return this.style;
    }

    /**
     * Sets the style.
     * 
     * @param style the style to set
     */
    public void setStyle(IStyleLink style) {
        this.style = style;
    }

}
