package client_fx;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrinterTest {

    public PrinterTest() {

        PrinterJob pj = PrinterJob.getPrinterJob();
        if (pj.printDialog()) {
            PageFormat pf = pj.defaultPage();
            Paper paper = pf.getPaper();
            double width = fromCMToPPI(5);
            double height = fromCMToPPI(9.5);
            paper.setSize(width, height);
            paper.setImageableArea(
                    fromCMToPPI(0.5),
                    fromCMToPPI(0.1),
                    width - fromCMToPPI(0.5),
                    height - fromCMToPPI(1));
            System.out.println("Before- " + dump(paper));
            pf.setOrientation(PageFormat.PORTRAIT);
            pf.setPaper(paper);
            System.out.println("After- " + dump(paper));
            System.out.println("After- " + dump(pf));
            dump(pf);
            PageFormat validatePage = pj.validatePage(pf);
            System.out.println("Valid- " + dump(validatePage));
            //Book book = new Book();
            //book.append(new MyPrintable(), pf);
            //pj.setPageable(book);    
            pj.setPrintable(new MyPrintable(), pf);
            try {
                pj.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected static double fromCMToPPI(double cm) {
        return toPPI(cm * 0.393700787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }

    protected static String dump(Paper paper) {
        StringBuilder sb = new StringBuilder(64);
        sb.append(paper.getWidth()).append("x").append(paper.getHeight())
                .append("/").append(paper.getImageableX()).append("x").
                append(paper.getImageableY()).append(" - ").append(paper
                        .getImageableWidth()).append("x").append(paper.getImageableHeight());
        return sb.toString();
    }

    protected static String dump(PageFormat pf) {
        Paper paper = pf.getPaper();
        return dump(paper);
    }

    public static class MyPrintable implements Printable {

        @Override
        public int print(Graphics graphics, PageFormat pageFormat,
                int pageIndex) throws PrinterException {
            System.out.println(pageIndex);
            int result = NO_SUCH_PAGE;
            if (pageIndex < 1) {
                Graphics2D g2d = (Graphics2D) graphics;
                System.out.println("[Print] " + dump(pageFormat));
                double width = pageFormat.getImageableWidth();
                double height = pageFormat.getImageableHeight();
                g2d.translate((int) pageFormat.getImageableX(),
                        (int) pageFormat.getImageableY());
                FontMetrics fm = g2d.getFontMetrics();
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                g2d.drawString("ProjectHeist", 10, 10);
                g2d.setFont(new Font(g2d.getFont().getFontName(), Font.BOLD, 7));
                try {
                    g2d.drawString("Transactienummer"+Transaction.getCurrentTransaction().getID(), 10, 20);
                    g2d.drawString("Bedrag "+Transaction.getCurrentTransaction().getAmmount(), 10, 30);
                } catch (Exception ex) {
                    Logger.getLogger(PrinterTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                g2d.drawString("Datum "+dateFormat.format(date), 10, 40);
                result = PAGE_EXISTS;
            }
            return result;
        }
    }
}
