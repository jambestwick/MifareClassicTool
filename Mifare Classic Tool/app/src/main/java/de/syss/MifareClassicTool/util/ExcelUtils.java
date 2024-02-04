package de.syss.MifareClassicTool.util;

import android.content.Context;
import android.os.Build;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * <p>文件描述：Excel工具类<p>
 * <p>作者：jambestwick<p>
 * <p>创建时间：2024/2/2<p>
 * <p>更新时间：2024/2/2<p>
 * <p>版本号：<p>
 * <p>邮箱：jambestwick@126.com<p>
 */
public class ExcelUtils {

    public static WritableFont arial14font = null;
    public static WritableCellFormat arial14format = null;
    public static WritableFont arial10font = null;
    public static WritableCellFormat arial10format = null;
    public static WritableFont arial12font = null;
    public static WritableCellFormat arial12format = null;
    public final static String UTF8_ENCODING = "UTF-8";
    public final static String GBK_ENCODING = "GBK";

    /**
     * 单元格的格式设置 字体大小 颜色 对齐方式、背景颜色等...
     */
    private static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(Alignment.CENTRE);
            arial14format.setBorder(Border.ALL, BorderLineStyle.THIN);
            arial14format.setBackground(Colour.VERY_LIGHT_YELLOW);
            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(Alignment.CENTRE);
            arial10format.setBorder(Border.ALL, BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);
            arial12font = new WritableFont(WritableFont.ARIAL, 10);
            arial12format = new WritableCellFormat(arial12font);
            arial10format.setAlignment(Alignment.CENTRE);//对齐格式
            arial12format.setBorder(Border.ALL, BorderLineStyle.THIN); //设置边框
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Excel * @param fileName * @param colName
     */
    public static void initExcel(String fileName, String[] colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("产品表", 0);
            //创建标题栏
            sheet.addCell(new Label(0, 0, fileName, arial14format));
            Arrays.sort(colName);
            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new Label(col, 0, colName[col], arial10format));
            }
            sheet.setRowView(0, 340);
            //设置行高
            workbook.write();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T> void writeObjListToExcel(List<T> objList, String fileName, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName), workbook);
                WritableSheet sheet = writebook.getSheet(0);
                for (int j = 0; j < objList.size(); j++) {
                    T objT = objList.get(j);
                    Map<String, Object> map = convert(objT);
                    int index = 0;
                    for (Map.Entry entry : map.entrySet()) {
                        sheet.addCell(new Label(index, j + 1, entry.getValue() + "", arial12format));
                        sheet.setColumnView(index, +8);//设置列宽
                        index++;
                    }
                    sheet.setRowView(j + 1, 350);//设置行高
                }
                writebook.write();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BiffException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Map<String, Object> convert(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass(); // 获取类信息
        Field[] fields = clazz.getDeclaredFields(); // 获取所有字段
        Map<String, Object> map = new LinkedHashMap<>();
        for (Field field : fields) {
            String fieldName = field.getName(); // 获取字段名称

            boolean accessible = field.isAccessible(); // 判断字段可访问性
            if (!accessible) {
                field.setAccessible(true); // 设置字段可访问
            }

            Object value = field.get(obj); // 获取字段值
            map.put(fieldName, value); // 添加到Map中

            if (!accessible) {
                field.setAccessible(false); // 还原字段不可访问状态
            }
        }

        return map;
    }
}
