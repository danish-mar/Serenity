package Assets.Utils;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TableUtils {
    public static void setColumnWidth(JTable table, String columnName, int width) {
        TableColumnModel columnModel = table.getColumnModel();
        int columnIndex = table.getColumnModel().getColumnIndex(columnName);
        TableColumn column = columnModel.getColumn(columnIndex);
        column.setPreferredWidth(width);
    }
}