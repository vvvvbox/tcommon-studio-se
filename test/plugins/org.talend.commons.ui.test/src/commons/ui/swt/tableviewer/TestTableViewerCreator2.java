// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package commons.ui.swt.tableviewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.LAYOUT_MODE;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.SORT;
import org.talend.commons.ui.swt.tableviewer.behavior.CellEditorValueAdapter;
import org.talend.commons.ui.swt.tableviewer.tableeditor.CheckboxTableEditorContent;
import org.talend.commons.ui.swt.tableviewer.tableeditor.TableEditorContent;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;

import commons.utils.DataObject;

/**
 * 
 * Test class of <code>TableViewerCreator</code>. <br/>
 * 
 * $Id: TestTableViewerCreator2.java 7038 2007-11-15 14:05:48Z plegall $
 * 
 */
public final class TestTableViewerCreator2 {

    /**
     * Default Constructor. Must not be used.
     */
    private TestTableViewerCreator2() {
    }

    private static final int ZERO = 0;

    private static final int TEN = 10;

    private static final int TWENTY = 20;

    private static final int FIFTY = 50;

    private static final int ONE_HUNDRED = 100;

    private static final int TWO_HUNDRED = 200;

    private static final int FIVE_HUNDRED = 500;

    private static final int HEIGHT_HUNDRED = 800;

    private static final int ALL = 255;

    public static void main(String[] args) {
        Display display = new Display();
        final Shell shell1 = new Shell(display);
        shell1.setLayout(new FillLayout());

        TableViewerCreator tableViewerCreator = new TableViewerCreator(shell1);
        tableViewerCreator.setBorderVisible(true);
        tableViewerCreator.setColumnsMoveableByDefault(true);
        tableViewerCreator.setColumnsResizableByDefault(true);
        tableViewerCreator.setColumnsSortableByDefault(true);
        tableViewerCreator.setLayoutMode(LAYOUT_MODE.DEFAULT);
        // tableViewerCreator.setAdjustWidthValue(-50);

        Table table = tableViewerCreator.createTable();
        // When the table is in a Composite (and not direct in a Shell),
        // and when you would use the full space on the composite :
        // use this portion of code :
        // Composite composite = new Composite(compositeParent, SWT.NONE);
        // composite.setLayout(new GridLayout(1, false));
        // tableViewerCreator.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));

        // intValueAdapter
        CellEditorValueAdapter intValueAdapter = new CellEditorValueAdapter() {

            public Object getOriginalTypedValue(final CellEditor cellEditor, Object value) {
                try {
                    return new Integer(value.toString());
                } catch (Exception ex) {
                    return null;
                }
            }

            @Override
            public Object getCellEditorTypedValue(final CellEditor cellEditor, Object value) {
                try {
                    return String.valueOf(value);
                } catch (Exception ex) {
                    return null;
                }
            }
        };

        initColumns(tableViewerCreator, table, intValueAdapter);

        List<DataObject> list = new ArrayList<DataObject>();
        Random random = new Random();
        DataObject listObject2 = null;
        for (int i = 0; i < TEN; i++) {
            listObject2 = new DataObject();
            // listObject2.setPrimaryIntegerValue(random.nextBoolean() ? i : null);
            listObject2.setIntegerValue1(random.nextInt(ONE_HUNDRED));
            listObject2.setLibelle("libelle " + random.nextInt(ONE_HUNDRED)); //$NON-NLS-1$
            listObject2.setIntegerValue2(random.nextInt(ONE_HUNDRED));
            listObject2.setBool(random.nextBoolean());
            list.add(listObject2);
        }
        list.add(listObject2);
        tableViewerCreator.init(list);

        shell1.setSize(HEIGHT_HUNDRED, FIVE_HUNDRED);
        shell1.setSize(HEIGHT_HUNDRED + TEN, FIVE_HUNDRED);
        shell1.open();

        while (!shell1.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    /**
     * 
     * @param tableViewerCreator
     * @param table
     * @param intValueAdapter
     */
    private static void initColumns(TableViewerCreator tableViewerCreator, Table table, CellEditorValueAdapter intValueAdapter) {
        TableViewerCreatorColumn column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setModifiable(true);
        column.setWidth(0);
        column.setWeight(0);
        column.setId("uid1"); //$NON-NLS-1$
        column.setCellEditor(new TextCellEditor(table), intValueAdapter);
        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setModifiable(true);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<DataObject, Integer>() {

            public Integer get(DataObject bean) {
                return bean.getIntegerValue1();
            }

            public void set(DataObject bean, Integer value) {
                bean.setIntegerValue1(value);
            }
        });
        column.setWidth(TEN);
        column.setWeight(TWENTY);
        column.setDefaultDisplayedValue(String.valueOf(Integer.MAX_VALUE));
        column.setId("uid2"); //$NON-NLS-1$
        column.setCellEditor(new TextCellEditor(table), intValueAdapter);

        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle("check"); //$NON-NLS-1$
        column.setId("check"); //$NON-NLS-1$
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<DataObject, Boolean>() {

            public Boolean get(DataObject bean) {
                return bean.isBool();
            }

            public void set(DataObject bean, Boolean value) {
                bean.setBool(value);
            }
        });
        column.setModifiable(true);
        column.setWidth(56);
        column.setDisplayedValue(""); //$NON-NLS-1$
        CheckboxTableEditorContent nullableCheckbox = new CheckboxTableEditorContent();
        column.setTableEditorContent(nullableCheckbox);

        tableViewerCreator.setDefaultSort(column, SORT.DESC);
        // tableViewerCreator.setSortable(true);

        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setModifiable(true);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<DataObject, Integer>() {

            public Integer get(DataObject bean) {
                return bean.getIntegerValue2();
            }

            public void set(DataObject bean, Integer value) {
                bean.setIntegerValue2(value);
            }
        });
        column.setWidth(TEN);
        column.setWeight(TWENTY);
        column.setId("uid3"); //$NON-NLS-1$
        column.setCellEditor(new TextCellEditor(table), intValueAdapter);
        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setModifiable(true);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<DataObject, String>() {

            public String get(DataObject bean) {
                return bean.getLibelle();
            }

            public void set(DataObject bean, String value) {
                bean.setLibelle(value);
            }
        });
        column.setWidth(TEN);
        column.setWeight(TWENTY);
        column.setId("uid"); //$NON-NLS-1$
        column.setCellEditor(new TextCellEditor(table));

        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle("Integer Null Value"); //$NON-NLS-1$
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<DataObject, Integer>() {

            public Integer get(DataObject bean) {
                return bean.getIntegerValue2();
            }

            public void set(DataObject bean, Integer value) {
                bean.setIntegerValue2(value);
            }
        });
        column.setModifiable(true);
        column.setWidth(ONE_HUNDRED);
        final String[] valueSet = new String[] { "xxx", "yyy", "zzz" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        column.setCellEditor(new ComboBoxCellEditor(table, valueSet), new CellEditorValueAdapter() {

            public String getColumnText(CellEditor cellEditor, Object bean, Object cellEditorValue) {
                String[] items = ((ComboBoxCellEditor) cellEditor).getItems();
                int index = (Integer) cellEditorValue;
                if (index >= 0 && index < items.length) {
                    return items[index];
                } else {
                    return ""; //$NON-NLS-1$
                }
            }
        });
        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle("Id"); //$NON-NLS-1$
        column.setModifiable(true);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<DataObject, Integer>() {

            public Integer get(DataObject bean) {
                return bean.getId();
            }

            public void set(DataObject bean, Integer value) {
                bean.setId(value);
            }
        });
        column.setWeight(FIFTY);
        column.setTableEditorContent(new TableEditorContent() {

            public Control initialize(Table table, TableEditor tableEditor, TableViewerCreatorColumn currentColumn,
                    Object currentRowObject, Object currentCellValue) {
                Button button = new Button(table, SWT.PUSH);
                button.setText(String.valueOf(currentCellValue));
                button.computeSize(SWT.DEFAULT, table.getItemHeight());
                // Set attributes of the editor
                tableEditor.grabHorizontal = true;
                tableEditor.minimumHeight = button.getSize().y;
                tableEditor.minimumWidth = button.getSize().x;
                return button;
            }
        });
        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle("Id2"); //$NON-NLS-1$
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<DataObject, Integer>() {

            public Integer get(DataObject bean) {
                return bean.getIntegerValue2();
            }

            public void set(DataObject bean, Integer value) {
                bean.setIntegerValue2(value);
            }
        });
        column.setWidth(TWO_HUNDRED);
        column.setModifiable(true);
        column.setTableEditorContent(new TableEditorContent() {

            public Control initialize(Table table, TableEditor tableEditor, TableViewerCreatorColumn currentColumn,
                    Object currentRowObject, Object currentCellValue) {
                Composite composite = new Composite(table, SWT.PUSH);
                // Set attributes of the button
                composite.setBackground(new Color(null, ALL, ZERO, ZERO));
                composite.setSize(ONE_HUNDRED * ((Integer) currentCellValue).intValue() / ONE_HUNDRED, table.getItemHeight());
                // Set attributes of the editor
                // tableEditor.grabHorizontal = true;
                tableEditor.minimumHeight = composite.getSize().y;
                tableEditor.horizontalAlignment = SWT.LEFT;
                tableEditor.minimumWidth = composite.getSize().x;
                return composite;
            }

        });
        column.setCellEditor(new ComboBoxCellEditor(table, valueSet), new CellEditorValueAdapter() {

            public String getColumnText(CellEditor cellEditor, Object bean, Object cellEditorValue) {
                String[] items = ((ComboBoxCellEditor) cellEditor).getItems();
                int index = (Integer) cellEditorValue;
                if (index >= 0 && index < items.length) {
                    return items[index];
                } else {
                    return ""; //$NON-NLS-1$
                }
            }
        });
    }
}
