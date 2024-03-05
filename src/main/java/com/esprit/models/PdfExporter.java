package com.esprit.models;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;

public class PdfExporter {
    public static void exportToPDF(ObservableList<Programme> tableView, File filename) {
        try {
            PdfWriter writer = new PdfWriter(filename.getAbsolutePath());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            Table table = new Table(8);

            // Ajouter les en-têtes de colonne
            table.addCell(new Cell().add(new Paragraph("Nom Programme")));
            table.addCell(new Cell().add(new Paragraph("Description")));
            table.addCell(new Cell().add(new Paragraph("Rate")));
            table.addCell(new Cell().add(new Paragraph("État Initial")));
            table.addCell(new Cell().add(new Paragraph("État Final")));
            table.addCell(new Cell().add(new Paragraph("Date Début")));
            table.addCell(new Cell().add(new Paragraph("Date Fin")));
            table.addCell(new Cell().add(new Paragraph("ID Utilisateur")));

            // Ajouter les données de chaque programme à la table
            for (Programme programme : tableView) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(programme.getNom_prog()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(programme.getDesc_prog()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(programme.getRate()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(programme.getEtat_initial()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(programme.getEtat_final()))));
                table.addCell(new Cell().add(new Paragraph(programme.getDate_debut().toString())));
                table.addCell(new Cell().add(new Paragraph(programme.getDate_fin().toString())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(programme.getID_user()))));

            }

            // Ajouter la table au document
            document.add(table);

            document.close();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
