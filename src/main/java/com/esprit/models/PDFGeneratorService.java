package com.esprit.models;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.Color;
import java.io.IOException;

public class PDFGeneratorService {
    public static void generatePDF(TableView<Produit> tableView) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Liste des Produits:");
                contentStream.newLine();

                ObservableList<Produit> produits = tableView.getItems();
                for (Produit produit : produits) {
                    contentStream.showText("Nom: " + produit.getDesc());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Prix: " + produit.getPrix());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Quantite: " + produit.getQuant());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Marque: " + produit.getMarque());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Reference: " + produit.getSku());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            document.save("Liste_des_Produits.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void generatePDFProduit(Produit produit) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.setNonStrokingColor(Color.BLUE);
                float pageWidth = page.getMediaBox().getWidth();
                // Obtenir la largeur du titre
                float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth("Liste des Produits:") / 1000 * 12; // Taille de police * échelle
                // Calculer la position horizontale du titre pour le centrer
                float titleX = (pageWidth - titleWidth) / 2;

                contentStream.newLineAtOffset(titleX, 700);
                  //  contentStream.newLineAtOffset(100, 700);
                    contentStream.showText("Liste des Produits:");
                    contentStream.newLine();
                contentStream.setNonStrokingColor(Color.BLACK);
            //    contentStream.newLineAtOffset(100, 700);
                contentStream.newLineAtOffset(0, -21);
                    contentStream.showText("Nom: " + produit.getDesc());
                    contentStream.newLineAtOffset(0, -21);
                    contentStream.showText("Prix: " + produit.getPrix());
                    contentStream.newLineAtOffset(0, -21);
                    contentStream.showText("Quantite: " + produit.getQuant());
                    contentStream.newLineAtOffset(0, -21);
                    contentStream.showText("Marque: " + produit.getMarque());
                    contentStream.newLineAtOffset(0, -21);
                    contentStream.showText("Reference: " + produit.getSku());
                    contentStream.newLineAtOffset(0, -21);
                    contentStream.newLine();

                contentStream.endText();
            }

            document.save("Liste_des_Produits.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
