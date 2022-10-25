package app.client.utils;

import app.client.controller.dialogs.MessageAlert;
import app.client.wrraper.ProdusWrraper;
import app.server.model.Client;
import app.server.model.Comanda;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.awt.*;
import java.io.File;
import java.util.List;

public class GeneratorPDF {
    private static GeneratorPDF instance = new GeneratorPDF();

    private GeneratorPDF(){}

    public static GeneratorPDF getInstance() {
        return instance;
    }

    public void generarePdfRaportComanda(Comanda comanda, List<ProdusWrraper> produse){
        try {
            MyDateTimeFormatter dateTimeFormatter = MyDateTimeFormatter.getInstance();
            Client client = comanda.getClient();

            String fileName = "C:\\Users\\MSI\\Desktop\\Informatica\\Anul III\\ELL - Elaborarea Lucrarii de Licenta" +
                    "\\Aplicatie_Licenta\\Electronics_Store_Order_Manager_1\\Client\\src\\main\\java\\pdf_files\\" +
                    "\\Raport_comanda_"+comanda.getId()+"_"+client.getNume()+"_"+client.getPrenume()+
                    "_"+dateTimeFormatter.formatOnlyDate(comanda.getData())+".pdf";


            PdfWriter writer = new PdfWriter(fileName);

            PdfDocument pdfDoc = new PdfDocument(writer);

            pdfDoc.addNewPage();
            Document document = new Document(pdfDoc);
            document.add(new Paragraph("Raport efectuare comanda").setBold().setUnderline().setFontSize(15));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph("ID comanda: "+comanda.getId()));
            document.add(new Paragraph(""));
            document.add(new Paragraph("Informatii despre client ").setBold());
            document.add(new Paragraph("Nume: "+client.getNume()));
            document.add(new Paragraph("Prenume: "+client.getPrenume()));
            document.add(new Paragraph("Telefon: "+client.getTelefon()));
            document.add(new Paragraph("Email: "+client.getEmail()));
            document.add(new Paragraph(""));
            document.add(new Paragraph("Data efectuarii comenzii: "+dateTimeFormatter.formatDateClassic(comanda.getData())));
            document.add(new Paragraph("Operator: "+comanda.getOperator().getNume()+" "+comanda.getOperator().getPrenume()));
            document.add(new Paragraph(""));
            document.add(new Paragraph("Produsele incluse in comanda").setBold());
            document.add(new Paragraph(""));

            Table table = new Table(6);
            table.addCell(new Cell().add("ID"));
            table.addCell(new Cell().add("Nume"));
            table.addCell(new Cell().add("Producator"));
            table.addCell(new Cell().add("Tip"));
            table.addCell(new Cell().add("Pret"));
            table.addCell(new Cell().add("Nr. exemplare"));

            float val = 0;
            int nr = 0;

            for( ProdusWrraper x : produse){
                table.addCell(new Cell().add(String.valueOf(x.getId())));
                table.addCell(new Cell().add(x.getProdus().getNume()));
                table.addCell(new Cell().add(x.getProducator().getNume()));
                table.addCell(new Cell().add(x.getTip().getNume()));
                table.addCell(new Cell().add(String.valueOf(x.getPret())));
                table.addCell(new Cell().add(String.valueOf(x.getNrExemplare())));
                val += x.getPret()*x.getNrExemplare();
                nr += x.getNrExemplare();
            }
            document.add(table);

            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph("TOTAL ").setBold());
            document.add(new Paragraph("Nr. produse: "+nr));
            document.add(new Paragraph("Valoare: "+ val +" lei"));

            document.close();

            Desktop.getDesktop().open(new File(fileName));
        }
        catch(Exception e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
    }

}
