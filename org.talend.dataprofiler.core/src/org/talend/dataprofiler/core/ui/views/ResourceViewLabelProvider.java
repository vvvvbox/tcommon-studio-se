// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.views;

import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;
import org.talend.cwm.softwaredeployment.TdDataProvider;
import org.talend.dataprofiler.core.PluginConstant;
import org.talend.dataprofiler.core.helper.AnaResourceFileHelper;
import org.talend.dataprofiler.core.helper.PrvResourceFileHelper;
import org.talend.dataprofiler.core.helper.RepResourceFileHelper;
import org.talend.dataprofiler.core.utils.DateFormatUtils;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.reports.TdReport;
import org.talend.utils.sugars.TypedReturnCode;

/**
 * @author rli
 * 
 */
public class ResourceViewLabelProvider extends WorkbenchLabelProvider implements ICommonLabelProvider {

    private static Logger log = Logger.getLogger(ResourceViewLabelProvider.class);

    public void init(ICommonContentExtensionSite aConfig) {
    }

    public String getDescription(Object anElement) {

        if (anElement instanceof IResource) {
            return ((IResource) anElement).getFullPath().makeRelative().toString();
        }
        return null;
    }

    public void restoreState(IMemento aMemento) {

    }

    public void saveState(IMemento aMemento) {
    }

    protected String decorateText(String input, Object element) {
        if (input.endsWith(PluginConstant.PRV_SUFFIX)) {
            IFile file = (IFile) element;
            TypedReturnCode<TdDataProvider> rc = PrvResourceFileHelper.getInstance().getTdProvider(file);
            String decorateText = PluginConstant.EMPTY_STRING;
            if (rc.isOk()) {
                decorateText = rc.getObject().getName();
            } else {
                log.warn(rc.getMessage());
            }
            return decorateText;
        } else if (input.endsWith(PluginConstant.ANA_SUFFIX)) {
            log.info("Loading file " + ((IFile) element).getLocation());
            Analysis analysis = AnaResourceFileHelper.getInstance().findAnalysis((IFile) element);
            if (analysis != null) {
                Date executionDate = analysis.getResults().getResultMetadata().getExecutionDate();
                String executeInfo = executionDate == null ? "(Not executed yet)" : "("
                        + DateFormatUtils.getSimpleDateString(executionDate) + ")";
                return analysis.getName() + PluginConstant.SPACE_STRING + executeInfo;
            }
        } else if (input.endsWith(PluginConstant.REP_SUFFIX)) {
            TdReport findReport = RepResourceFileHelper.getInstance().findReport((IFile) element);
            return findReport.getName();
        }
        return input;
    }

}
