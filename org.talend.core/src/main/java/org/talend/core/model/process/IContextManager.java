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
package org.talend.core.model.process;

import java.util.List;

import org.talend.designer.core.model.utils.emf.talendfile.ProcessType;


/**
 * DOC nrousseau  class global comment. Detailled comment
 * <br/>
 *
 * $Id$
 *
 */
public interface IContextManager {

    public List<IContext> getListContext();
    
    public void setListContext(List<IContext> listContext);

    public IContext getDefaultContext();
    
    public void setDefaultContext(IContext context);

    public void addContextListener(IContextListener listener);
    
    public void removeContextListener(IContextListener listener);
    
    public void fireContextsChangedEvent();
    
    public boolean checkValidParameterName(String parameterName);
}
