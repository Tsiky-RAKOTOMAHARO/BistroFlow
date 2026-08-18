package com.projet.ui.services;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.projet.common.dto.CommandeDTO;
import com.projet.common.dto.LigneCommandeDTO;
import com.projet.common.dto.MenuDTO;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class PdfReceiptService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void generateReceipt(CommandeDTO cmd, Map<String, MenuDTO> menuMap, File targetFile) throws Exception {
        // Format A6 idéal pour un reçu type ticket de caisse
        Document document = new Document(PageSize.A6, 15, 15, 15, 15);
        PdfWriter.getInstance(document, new FileOutputStream(targetFile));

        document.open();

        // Polices
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD, Color.DARK_GRAY);
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, Color.BLACK);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Color.BLACK);
        Font headerCellFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, Color.WHITE);

        // En-tête du Reçu
        Paragraph restaurantName = new Paragraph("E-letra restaurant", titleFont);
        restaurantName.setAlignment(Element.ALIGN_CENTER);
        document.add(restaurantName);

        Paragraph title = new Paragraph("REÇU", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph(" ", normalFont)); // Espace

        // Infos Commande & Client
        document.add(new Paragraph("N° Commande : " + (cmd.getIdcom() != null ? cmd.getIdcom() : "-"), boldFont));
        document.add(new Paragraph("Client : " + (cmd.getNomcli() != null && !cmd.getNomcli().isBlank() ? cmd.getNomcli() : "Anonyme"), normalFont));
        
        if (cmd.getDatecom() != null) {
            document.add(new Paragraph("Date : " + cmd.getDatecom().format(DATE_FORMAT), normalFont));
        }

        String typeService = (cmd.getTypecom() != null && !cmd.getTypecom().isBlank()) ? cmd.getTypecom() : "Non spécifié";
        document.add(new Paragraph("Service : " + typeService, normalFont));



        // Tableau des plats
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{2.5f, 1.0f, 1.5f});
        table.setSpacingBefore(5f);
        table.setSpacingAfter(5f);

        // En-têtes de colonnes
        addHeaderCell(table, "Plat", headerCellFont);
        addHeaderCell(table, "Qté", headerCellFont);
        addHeaderCell(table, "Total", headerCellFont);

        long totalGeneral = 0;

        if (cmd.getLignes() != null && !cmd.getLignes().isEmpty()) {
            for (LigneCommandeDTO ligne : cmd.getLignes()) {
                MenuDTO menu = menuMap.get(ligne.getIdplat());
                String nomPlat = (menu != null && menu.getNomplat() != null) ? menu.getNomplat() : ligne.getIdplat();
                int pu = (menu != null) ? menu.getPu() : 0;
                long totalLigne = (long) pu * ligne.getQuantite();
                totalGeneral += totalLigne;

                table.addCell(createCell(nomPlat, normalFont, Element.ALIGN_LEFT));
                table.addCell(createCell(String.valueOf(ligne.getQuantite()), normalFont, Element.ALIGN_CENTER));
                table.addCell(createCell(totalLigne + " Ar", normalFont, Element.ALIGN_RIGHT));
            }
        } else {
            PdfPCell emptyCell = new PdfPCell(new Phrase("Aucun article", normalFont));
            emptyCell.setColspan(3);
            emptyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(emptyCell);
        }

        document.add(table);


        // Total
        Paragraph totalPara = new Paragraph("TOTAL À PAYER : " + totalGeneral + " Ar", titleFont);
        totalPara.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalPara);

        // Pied de page
        document.add(new Paragraph(" ", normalFont));
        Paragraph footer = new Paragraph("Merci de votre visite et à bientôt !", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8, Color.GRAY));
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
    }

    private static void addHeaderCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(Color.GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(4f);
        table.addCell(cell);
    }

    private static PdfPCell createCell(String text, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(3f);
        return cell;
    }
}