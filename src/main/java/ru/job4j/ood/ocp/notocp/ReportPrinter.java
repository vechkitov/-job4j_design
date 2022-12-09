package ru.job4j.ood.ocp.notocp;

/**
 * Нарушение OCP: если появится еще один формат для отчета (например, json), придется
 * изменять уже существующий код: добавлять метод печати отчета в формате json.
 */
public class ReportPrinter {

    public void printXmlReport(Report report) {
        System.out.println("Печать отчета в xml");
    }

    public void printXlsReport(Report report) {
        System.out.println("Печать отчета в Excel");
    }

    public static class Report {
    }
}
