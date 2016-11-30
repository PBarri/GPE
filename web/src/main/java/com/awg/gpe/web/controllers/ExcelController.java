package com.awg.gpe.web.controllers;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Arrays;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;

/**
 * Clase base para todos los controladores que tengan descargas de listados en excel.
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 */
public abstract class ExcelController extends BaseController {

    private static final long serialVersionUID = 1L;

    public enum EnumCellStyle {
        HEADER, TITLE, CURRENCY
    }

    protected XSSFWorkbook wb;
    protected XSSFCellStyle headerStyle;
    protected XSSFCellStyle titleStyle;
    protected XSSFCellStyle currencyStyle;

    protected XSSFSheet currentSheet;
    protected XSSFRow currentRow;
    protected XSSFCell currentCell;

    protected int row;
    protected int cell;
    protected int titleColumnsUsed;
    protected int usedColumns;

    /**
     * Inicializa los datos necesarios para crear un archivo excel.
     * @version 1.0
     * @since 1.0
     */
    protected void initializeExcel() {
        this.wb = new XSSFWorkbook();
        this.row = 0;
        this.cell = 0;
        this.titleColumnsUsed = 0;
        this.usedColumns = 0;

        // Fonts
        XSSFFont titleFont = this.wb.createFont();
        titleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

        // DataFormats
        XSSFDataFormat df = this.wb.createDataFormat();

        // Colors
        XSSFColor black = new XSSFColor(Color.BLACK);
        XSSFColor white = new XSSFColor(Color.WHITE);
        XSSFColor lightBlue = new XSSFColor(new Color(51, 204, 204));

        // Estilos
        this.headerStyle = this.wb.createCellStyle();
        this.headerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        this.headerStyle.setFont(titleFont);
        this.headerStyle.setFillForegroundColor(lightBlue);
        this.headerStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        this.headerStyle.setWrapText(false);
        Arrays.asList(XSSFCellBorder.BorderSide.values()).forEach(side -> this.headerStyle.setBorderColor(side, black));

        this.titleStyle = this.wb.createCellStyle();
        this.titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        this.titleStyle.setFont(titleFont);
        this.titleStyle.setFillForegroundColor(white);
        this.titleStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        this.titleStyle.setWrapText(false);
        Arrays.asList(XSSFCellBorder.BorderSide.values()).forEach(side -> this.titleStyle.setBorderColor(side, black));

        this.currencyStyle = this.wb.createCellStyle();
        this.currencyStyle.setFont(titleFont);
        this.currencyStyle.setFillForegroundColor(white);
        this.currencyStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        this.currencyStyle.setWrapText(false);
        this.currencyStyle.setDataFormat(df.getFormat("#,##0.00"));
        Arrays.asList(XSSFCellBorder.BorderSide.values()).forEach(side -> this.currencyStyle.setBorderColor(side, black));
    }

    /**
     * Método que genera una nueva hoja en el archivo de excel
     * @param sheetName Nombre de la hoja
     * @version 1.0
     * @since 1.0
     */
    protected void sheet(String sheetName) {
        this.currentSheet = this.wb.createSheet(sheetName);
        this.row = 0;
        this.cell = 0;
        this.usedColumns = 0;
        this.titleColumnsUsed = 0;
    }

    /**
     * Método que crea una nueva fila en el archivo excel
     * @version 1.0
     * @since 1.0
     */
    protected void row() {
        this.currentRow = this.currentSheet.createRow(this.row++);
        this.cell = 0;
        this.usedColumns = 0;
    }

    /**
     * Método que crea una nueva celda, asignándole un nuevo valor
     * @param value Valor de la nueva celda
     * @version 1.0
     * @since 1.0
     */
    protected void cell(String value) {
        this.currentCell = this.currentRow.createCell(this.cell++);
        this.currentCell.setCellValue(value);
        this.currentCell.setCellStyle(this.titleStyle);
        this.titleColumnsUsed++;
    }

    /**
     * Método que genera una nueva celda, asignándole un valor y un nuevo estilo.
     * @param value Valor de la celda
     * @param style {@link ExcelController.EnumCellStyle} conteniendo los estilos posibles para la nueva celda
     * @version 1.0
     * @since 1.0
     */
    protected void cell(String value, ExcelController.EnumCellStyle style) {
        this.currentCell = this.currentRow.createCell(this.cell++);
        this.currentCell.setCellValue(value);
        setStyle(this.currentCell, style);
        this.titleColumnsUsed++;
    }

    /**
     * Método que crea una nueva celda numérica
     * @param value Valor de la nueva celda
     * @version 1.0
     * @since 1.0
     */
    protected void cell(BigDecimal value) {
        this.currentCell = this.currentRow.createCell(this.cell++);
        this.currentCell.setCellValue(value.toPlainString());
        this.currentCell.setCellStyle(this.currencyStyle);
        this.titleColumnsUsed++;
    }

    /**
     * Método encargado de alinear las columnas del archivo excel
     * @version 1.0
     * @since 1.0
     */
    protected void alignColumns() {
        // TODO:
    }

    private void setStyle(XSSFCell cell, ExcelController.EnumCellStyle style) {
        switch (style) {
            case HEADER:
                cell.setCellStyle(this.headerStyle);
                break;
            case TITLE:
                cell.setCellStyle(this.titleStyle);
                break;
            case CURRENCY:
                cell.setCellStyle(this.currencyStyle);
                break;
            default:
                break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.awg.gpe.web.controllers.BaseController#init()
     */
    @Override
    public abstract void init();

    /*
     * (non-Javadoc)
     * 
     * @see com.awg.gpe.web.controllers.BaseController#reloadPage()
     */
    @Override
    protected abstract void reloadPage();
}
