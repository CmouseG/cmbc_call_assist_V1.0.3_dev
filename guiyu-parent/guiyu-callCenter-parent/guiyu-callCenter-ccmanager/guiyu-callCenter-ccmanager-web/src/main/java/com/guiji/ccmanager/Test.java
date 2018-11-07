package com.guiji.ccmanager;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class Test {

    private final static String[] headers = {"序号", "列名", "类型", "允许空", "默认值","说明"};
    private final static int [] lengths = {10,20,20,10,20,60};


    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {

        String dburl = "jdbc:mysql://47.97.179.12:3306/guiyu_callcenter";
        String dbuser = "callcenter";
        String dbpwd = "callcenter@1234";
        Class.forName("com.mysql.jdbc.Driver");

        List<String> tableName = new ArrayList<String>();
        Map<String, List<Entry<String, String>>> tables = new HashMap<String, List<Entry<String, String>>>();
        Connection con = DriverManager.getConnection(dburl, dbuser, dbpwd);
        DatabaseMetaData metaDb = con.getMetaData();

        File file = new File("C:\\Users\\Administrator\\Desktop\\11.xls");
        file.createNewFile();
        WritableWorkbook wb = Workbook.createWorkbook(file);
        int s = 0;


        ResultSet rsTableName = metaDb.getTables(null, null, null, new String[] { "TABLE" });
        while (rsTableName.next()) {



            String tmpTableName = rsTableName.getString("table_name");
            tableName.add(tmpTableName);
            String sql = "select * from " + tmpTableName;
            Statement st = con.createStatement();
            ResultSet rsColumName = st.executeQuery(sql);
            ResultSetMetaData metaRs = rsColumName.getMetaData();

            if(tmpTableName.endsWith("1") || tmpTableName.endsWith("0") || tmpTableName.startsWith("call_in")){
                continue;
            }

            WritableSheet sheet = wb.createSheet(tmpTableName,s);
            s++;
            for (int i = 0; i < headers.length; i++) {
                Label label = new Label(i, 0 , headers[i]);
                sheet.addCell(label);
                sheet.setColumnView(i, lengths[i]);
            }

            for (int i = 1; i <= metaRs.getColumnCount(); i++) {
                sheet.addCell(new Label(0, i, String.valueOf(i)));
                sheet.addCell(new Label(1, i, metaRs.getColumnName(i)));

                // 某列类型的精确度(类型的长度)
                int precision = metaRs.getPrecision(i);

                String columType = "";
                switch (metaRs.getColumnType(i )) {
                    case Types.CHAR:
                        columType = "char";
                        break;
                    case Types.BIGINT:
                        columType = "bigint";
                        break;
                    case Types.DATE:
                        columType = "date";
                        break;
                    case Types.DECIMAL:
                        columType = "decimal";
                        break;
                    case Types.INTEGER:
                        columType = "int";
                        break;
                    case Types.NCHAR:
                        columType = "nchar";
                        break;
                    case Types.NUMERIC:
                        columType = "numeric";
                        break;
                    case Types.NVARCHAR:
                        columType = "nvarchar";
                        break;
                    case Types.SMALLINT:
                        columType = "smallint";
                        break;
                    case Types.TIME:
                        columType = "time";
                        break;
                    case Types.TINYINT:
                        columType = "tinyint";
                        break;
                    case Types.TIMESTAMP:
                        columType = "timestamp";
                        break;
                    case Types.VARCHAR:
                        columType = "varchar";
                        break;
                    default:
                        columType = "aa";
                }
                sheet.addCell(new Label(2, i, columType+"("+precision+")"));
                sheet.addCell(new Label(3, i, metaRs.isNullable(i)==0 ? "否":"是"));
                sheet.addCell(new Label(4, i, metaRs.getColumnLabel(i)));
                sheet.addCell(new Label(4, i, metaRs.getSchemaName(i)));

            }

        }


        wb.write();
        wb.close();

    }
}