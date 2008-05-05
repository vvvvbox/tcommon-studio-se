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
package org.talend.dq.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.talend.commons.emf.EMFUtil;
import org.talend.cwm.constants.DevelopmentStatus;
import org.talend.cwm.dependencies.DependenciesHandler;
import org.talend.cwm.helper.DataProviderHelper;
import org.talend.cwm.helper.TaggedValueHelper;
import org.talend.cwm.relational.TdColumn;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.analysis.AnalysisParameters;
import org.talend.dataquality.domain.Domain;
import org.talend.dataquality.domain.RangeRestriction;
import org.talend.dataquality.expressions.BooleanExpressionNode;
import org.talend.dataquality.helpers.BooleanExpressionHelper;
import org.talend.dataquality.helpers.DomainHelper;
import org.talend.dataquality.helpers.MetadataHelper;
import org.talend.dataquality.indicators.DataminingType;
import org.talend.dataquality.indicators.Indicator;
import org.talend.dq.indicators.definitions.DefinitionHandler;
import org.talend.utils.sugars.TypedReturnCode;
import orgomg.cwm.foundation.softwaredeployment.DataManager;
import orgomg.cwm.objectmodel.core.Dependency;
import orgomg.cwm.objectmodel.core.Expression;
import orgomg.cwm.objectmodel.core.ModelElement;

/**
 * @author scorreia
 * 
 * This class helps to handle a Column analysis.
 */
public class ColumnAnalysisHandler {

    private static Logger log = Logger.getLogger(ColumnAnalysisHandler.class);

    /**
     * The resources that are connected to this analysis and that are potentially modified.
     */
    private Collection<Resource> modifiedResources = new HashSet<Resource>();

    private Analysis analysis;

    /**
     * Method "setAnalysis".
     * 
     * @param columnAnalysis the analysis to set
     */
    public void setAnalysis(Analysis columnAnalysis) {
        this.analysis = columnAnalysis;
    }

    /**
     * Method "getAnalysis".
     * 
     * @return the analysis
     */
    public Analysis getAnalysis() {
        return this.analysis;
    }

    public String getName() {
        assert analysis != null;
        return this.analysis.getName();
    }

    public void setName(String name) {
        assert analysis != null;
        this.analysis.setName(name);
    }

    public String getPurpose() {
        assert analysis != null;
        return TaggedValueHelper.getPurpose(analysis);
    }

    public void setPurpose(String purpose) {
        assert analysis != null;
        TaggedValueHelper.setPurpose(purpose, analysis);
    }

    public String getDescription() {
        assert analysis != null;
        return TaggedValueHelper.getDescription(analysis);
    }

    public void setDescription(String description) {
        assert analysis != null;
        TaggedValueHelper.setDescription(description, analysis);
    }
    
    public String getAuthor() {
        
        assert analysis != null;
        return TaggedValueHelper.getAuthor(analysis);
    }
    
    public void setAuthor(String anthor) {
        
        assert analysis != null;
        TaggedValueHelper.setAuthor(analysis, anthor);
    }
    
    public String getStatus() {
        
        assert analysis != null;
        return TaggedValueHelper.getDevStatus(analysis).getLiteral();
    }

    public void setStatus(String status) {
        
        assert analysis != null;
        TaggedValueHelper.setDevStatus(analysis, DevelopmentStatus.get(status));
    }
    /**
     * Method "addColumnToAnalyze".
     * 
     * @param column
     * @return
     */
    public boolean addColumnToAnalyze(TdColumn column) {
        assert analysis != null;
        assert analysis.getContext() != null;
        return analysis.getContext().getAnalysedElements().add(column);
    }

    public boolean addColumnsToAnalyze(Collection<TdColumn> column) {
        assert analysis != null;
        assert analysis.getContext() != null;
        return analysis.getContext().getAnalysedElements().addAll(column);
    }

    public EList<ModelElement> getAnalyzedColumns() {
        return analysis.getContext().getAnalysedElements();
    }

    public boolean addIndicator(TdColumn column, Indicator... indicators) {
        if (!analysis.getContext().getAnalysedElements().contains(column)) {
            analysis.getContext().getAnalysedElements().add(column);
        }
        for (Indicator indicator : indicators) {
            indicator.setAnalyzedElement(column);
            // ADDED MODSCA 2008-04-24 set the default indicator definitions
            // // FIXME following code should be executed as soon as an indicator is created, not here.
            boolean definitionSet = DefinitionHandler.getInstance().setDefaultIndicatorDefinition(indicator);
            if (log.isDebugEnabled()) {
                log.debug("Definition set for " + indicator.getName() + ": " + definitionSet);
            }
            analysis.getResults().getIndicators().add(indicator);
        }
        DataManager connection = analysis.getContext().getConnection();
        if (connection == null) {
            // try to get one
            log.error("Connection has not been set in analysis Context");
            connection = DataProviderHelper.getTdDataProvider(column);
            analysis.getContext().setConnection(connection);
        }
        TypedReturnCode<Dependency> rc = DependenciesHandler.getInstance().setDependencyOn(analysis, connection);
        if (rc.isOk()) {
            // DependenciesHandler.getInstance().addDependency(rc.getObject());
            this.modifiedResources.add(DependenciesHandler.getInstance().getDependencyResource());
        }
        return true;
    }

    public void clearAnalysis() {
        assert analysis != null;
        assert analysis.getContext() != null;
        analysis.getContext().getAnalysedElements().clear();
        analysis.getResults().getIndicators().clear();
    }

    /**
     * Method "setDatamingType".
     * 
     * @param dataminingTypeLiteral the literal expression of the datamining type used for the analysis
     * @param column a column
     */
    public void setDatamingType(String dataminingTypeLiteral, TdColumn column) {
        DataminingType type = DataminingType.get(dataminingTypeLiteral);
        MetadataHelper.setDataminingType(type, column);
        Resource resource = column.eResource();
        if (resource != null) {
            resource.setModified(true); // tell that the resource has been modified.
            // it would be better to handle modifications with EMF Commands
            this.modifiedResources.add(resource);
        }
    }

    /**
     * Method "getDatamingType".
     * 
     * @param column
     * @return the datamining type literal if any or empty string
     */
    public String getDatamingType(TdColumn column) {
        DataminingType dmType = MetadataHelper.getDataminingType(column);
        if (dmType == null) {
            return "";
        }
        // else
        return dmType.getLiteral();
    }

    /**
     * Method "getIndicators".
     * 
     * @param column
     * @return the indicators attached to this column
     */
    public Collection<Indicator> getIndicators(TdColumn column) {
        Collection<Indicator> indics = new ArrayList<Indicator>();
        EList<Indicator> allIndics = analysis.getResults().getIndicators();
        for (Indicator indicator : allIndics) {
            if (indicator.getAnalyzedElement() != null && indicator.getAnalyzedElement().equals(column)) {
                indics.add(indicator);
            }
        }
        return indics;
    }

    /**
     * Method "setStringDataFilter".
     * 
     * @param datafilterString
     * @return true when a new data filter is created, false if it is only updated
     */
    public boolean setStringDataFilter(String datafilterString) {
        EList<Domain> dataFilters = analysis.getParameters().getDataFilter();
        // update existing filters
        if (!dataFilters.isEmpty()) {
            Domain domain = dataFilters.get(0);
            EList<RangeRestriction> ranges = domain.getRanges();
            RangeRestriction rangeRestriction = (ranges.isEmpty()) ? DomainHelper.addRangeRestriction(domain) : ranges.get(0);
            BooleanExpressionNode expressions = rangeRestriction.getExpressions();
            if (expressions == null) {
                expressions = BooleanExpressionHelper.createBooleanExpressionNode(datafilterString);
                rangeRestriction.setExpressions(expressions);
            } else {
                Expression expression = expressions.getExpression();
                if (expression == null) {
                    expression = BooleanExpressionHelper.createExpression("SQL", datafilterString);
                    expressions.setExpression(expression);
                } else {
                    expression.setBody(datafilterString);
                }
            }
            return false;
        }
        // else
        return dataFilters.add(createDomain(datafilterString));
    }

    public String getStringDataFilter() {
        AnalysisParameters parameters = analysis.getParameters();
        if (parameters == null) {
            return null;
        }
        EList<Domain> dataFilters = parameters.getDataFilter();
        // remove existing filters
        if (dataFilters.isEmpty()) {
            return null;
        }

        for (Domain domain : dataFilters) {
            if (domain == null) {
                continue;
            }
            EList<RangeRestriction> ranges = domain.getRanges();
            for (RangeRestriction rangeRestriction : ranges) {
                BooleanExpressionNode expressions = rangeRestriction.getExpressions();
                if (expressions == null) {
                    continue;
                }
                Expression expression = expressions.getExpression();
                if (expression == null) {
                    continue;
                }
                return expression.getBody();
            }
        }
        return null;
    }

    private Domain createDomain(String datafilterString) {
        // by default use same name as the analysis. This is ok as long as there is only one datafilter.
        Domain domain = DomainHelper.createDomain(getName());
        RangeRestriction rangeRestriction = DomainHelper.addRangeRestriction(domain);
        BooleanExpressionNode expressionNode = BooleanExpressionHelper.createBooleanExpressionNode(datafilterString);
        rangeRestriction.setExpressions(expressionNode);
        return domain;
    }

    public boolean saveModifiedResources() {
        EMFUtil util = new EMFUtil();
        util.getResourceSet().getResources().addAll(this.modifiedResources);
        return util.save();
    }
}
