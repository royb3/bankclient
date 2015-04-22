package client_fx;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.*;

public class PrinterTest {

    public void initialize(){

        PrinterJob pj = PrinterJob.getPrinterJob();
        if (pj.printDialog()) {
            PageFormat pf = pj.defaultPage();
            Paper paper = pf.getPaper();    
            double width = fromCMToPPI(5);
            double height = fromCMToPPI(10);    
            paper.setSize(width, height);
            paper.setImageableArea(
                            fromCMToPPI(0.5), 
                            fromCMToPPI(1), 
                            width - fromCMToPPI(0.35), 
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
                g2d.drawString("ProjectHeist", 5, 5);
                g2d.drawString("Naam", 0, fm.getAscent());
                result = PAGE_EXISTS;    
            }    
            return result;    
        }
    }
}