/**
 *
 * $Id$
 */
package org.talend.designer.core.model.utils.emf.talendfile.validation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.talend.designer.core.model.utils.emf.talendfile.LogsType;
import org.talend.designer.core.model.utils.emf.talendfile.ParametersType;
import org.talend.designer.core.model.utils.emf.talendfile.RequiredType;

/**
 * A sample validator interface for {@link org.talend.designer.core.model.utils.emf.talendfile.ProcessType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ProcessTypeValidator {
    boolean validate();

    boolean validateDescription(String value);
    boolean validateRequired(RequiredType value);
    boolean validateContext(EList value);
    boolean validateParameters(ParametersType value);
    boolean validateNode(EList value);
    boolean validateConnection(EList value);
    boolean validateNote(EList value);
    boolean validateLogs(LogsType value);
    boolean validateAuthor(String value);
    boolean validateComment(String value);
    boolean validateDefaultContext(String value);
    boolean validateName(String value);
    boolean validatePurpose(String value);
    boolean validateRepositoryContextId(String value);
    boolean validateStatus(String value);
    boolean validateVersion(String value);
    boolean validateSubjob(EList value);
    boolean validateScreenshot(byte[] value);
    boolean validateScreenshots(EMap value);
    boolean validateRoutinesDependencies(EList value);
    boolean validateJobType(String value);
    boolean validateFramework(String value);
}
