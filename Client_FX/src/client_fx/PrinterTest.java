package client_fx;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class PrinterTest {

    public PrinterTest() {

        PrinterJob pj = PrinterJob.getPrinterJob();
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
                try {
                    Graphics2D g2d = (Graphics2D) graphics;
                    System.out.println("[Print] " + dump(pageFormat));
                    double width = pageFormat.getImageableWidth();
                    double height = pageFormat.getImageableHeight();
                    g2d.translate((int) pageFormat.getImageableX(),
                            (int) pageFormat.getImageableY());
                    FontMetrics fm = g2d.getFontMetrics();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    final int COLUMN_A = 10;
                    final int COLUMN_B = 80;
                    int row = 10;BufferedImage img = null;
                    img = ImageIO.read(MainApp.class.getResource("assets/logo.jpg"));
                    
                    g2d.drawImage(img,null, COLUMN_A, row); row += 40;
                    g2d.drawString("ProjectHeist", COLUMN_A, row); row+=10;
                    g2d.setFont(new Font(g2d.getFont().getFontName(), Font.BOLD, 7));
                    try {
                        g2d.drawString("Transactienummer:", COLUMN_A, row);
                        g2d.drawString(String.format("%d", Transaction.getCurrentTransaction().getID()), COLUMN_B, row); row += 10;
                        g2d.drawString("Bedrag:" , COLUMN_A, row);
                        g2d.drawString(String.format("€%.2f", 1.0 * Transaction.getCurrentTransaction().getAmmount() / 100), COLUMN_B, row); row += 10;
                        g2d.drawString("Rekeningnummer:" , COLUMN_A, row);
                        g2d.drawString(String.format("*******%s", KeyPadListener.getListener().getAccountID().substring(11)), COLUMN_B, row); row += 10;
                        g2d.drawString("Pasnummer:", COLUMN_A, row);
                        g2d.drawString(KeyPadListener.getListener().getCardNumber(), COLUMN_B, row); row += 10;
                    } catch (Exception ex) {
                        Logger.getLogger(PrinterTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    g2d.drawString("Datum:", COLUMN_A, row);
                    g2d.drawString(dateFormat.format(date), COLUMN_B - 40, row); row += 10;
                    
                    
                            result = PAGE_EXISTS;
                } catch (IOException ex) {
                    Logger.getLogger(PrinterTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return result;
        }
    }
}
