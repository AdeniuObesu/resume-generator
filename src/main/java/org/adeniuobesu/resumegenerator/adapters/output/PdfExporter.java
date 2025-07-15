package org.adeniuobesu.resumegenerator.adapters.output;


import org.adeniuobesu.resumegenerator.application.dtos.*;
import org.adeniuobesu.resumegenerator.application.ports.OutputStrategy;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

public final class PdfExporter implements OutputStrategy<ResumeDto> {
    private static final float MARGIN = 50;
    private static final float LINE_HEIGHT = 20;
    private static final float SECTION_GAP = 10;
    
    private final PDFont normalFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
    private final PDFont boldFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private final PDFont italicFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE);
    
    private float currentY;
    private PDPageContentStream contentStream;

    @Override
    public void generate(ResumeDto resume, OutputStream outputStream) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            
            contentStream = new PDPageContentStream(document, page);
            currentY = page.getMediaBox().getHeight() - MARGIN;
            
            // Set up colors
            Color primaryColor = new Color(44, 62, 80);   // Dark blue
            Color secondaryColor = new Color(52, 152, 219); // Light blue
            
            // Header
            drawText(resume.fullName(), boldFont, 24, MARGIN, currentY, primaryColor);
            currentY -= LINE_HEIGHT * 1.5f;
            drawText(resume.professionalTitle(), normalFont, 16, MARGIN, currentY, secondaryColor);
            currentY -= LINE_HEIGHT * 1.5f;
            
            // Summary
            if (resume.professionalSummary() != null) {
                drawWrappedText(resume.professionalSummary(), normalFont, 12, MARGIN, currentY, 
                    page.getMediaBox().getWidth() - 2 * MARGIN, Color.BLACK);
                currentY -= LINE_HEIGHT * 1.5f;
            }
            
            drawHorizontalLine(page.getMediaBox().getWidth() - 2 * MARGIN);
            currentY -= SECTION_GAP * 2;
            
            // Contact Information
            drawSectionTitle("Contact Information", primaryColor);
            for (ContactMethodDto contact : resume.contactMethods()) {
                drawText(contact.type() + ": " + contact.value(), normalFont, 12, MARGIN + 10, currentY, Color.BLACK);
                currentY -= LINE_HEIGHT;
            }
            currentY -= SECTION_GAP;
            
            // Work Experience
            drawSectionTitle("Professional Experience", primaryColor);
            for (WorkExperienceDto exp : resume.workExperiences()) {
                drawText(exp.companyName(), boldFont, 14, MARGIN + 10, currentY, primaryColor);
                currentY -= LINE_HEIGHT;
                
                float jobTitleX = MARGIN + 10;
                float dateX = page.getMediaBox().getWidth() - MARGIN - 100;
                
                drawText(exp.jobTitle(), normalFont, 12, jobTitleX, currentY, Color.BLACK);
                drawText(exp.startDate() + " - " + exp.endDate(), normalFont, 12, dateX, currentY, Color.DARK_GRAY);
                currentY -= LINE_HEIGHT;
                
                for (String achievement : exp.keyAchievements()) {
                    drawText("• " + achievement, normalFont, 11, MARGIN + 20, currentY, Color.BLACK);
                    currentY -= LINE_HEIGHT;
                }
                currentY -= SECTION_GAP;
            }
            
            // Education
            drawSectionTitle("Education", primaryColor);
            for (EducationDto edu : resume.educationHistory()) {
                drawText(edu.institutionName(), boldFont, 14, MARGIN + 10, currentY, primaryColor);
                currentY -= LINE_HEIGHT;
                
                String degreeText = edu.degree();
                if (edu.fieldOfStudy() != null) {
                    degreeText += " in " + edu.fieldOfStudy();
                }
                
                drawText(degreeText, normalFont, 12, MARGIN + 10, currentY, Color.BLACK);
                currentY -= LINE_HEIGHT;
                
                drawText(edu.startDate() + " - " + edu.endDate(), italicFont, 11, MARGIN + 10, currentY, Color.DARK_GRAY);
                currentY -= LINE_HEIGHT * 1.5f;
            }
            
            // Skills
            drawSectionTitle("Technical Skills", primaryColor);
            for (SkillCategoryDto category : resume.skillCategories()) {
                drawText(category.categoryName() + ":", boldFont, 12, MARGIN + 10, currentY, primaryColor);
                currentY -= LINE_HEIGHT;
                
                String skills = String.join(", ", category.skills());
                drawWrappedText(skills, normalFont, 11, MARGIN + 20, currentY, 
                    page.getMediaBox().getWidth() - 2 * MARGIN - 10, Color.BLACK);
                currentY -= LINE_HEIGHT * 1.5f;
            }
            
            // Languages
            drawSectionTitle("Languages", primaryColor);
            for (LanguageDto lang : resume.languages()) {
                drawText(lang.language() + " (" + lang.proficiency() + ")", normalFont, 12, MARGIN + 10, currentY, Color.BLACK);
                currentY -= LINE_HEIGHT;
            }
            
            // Hobbies
            if (!resume.hobbies().isEmpty()) {
                drawSectionTitle("Interests", primaryColor);
                for (HobbyDto hobby : resume.hobbies()) {
                    String hobbyText = hobby.name();
                    if (hobby.description() != null) {
                        hobbyText += ": " + hobby.description();
                    }
                    drawText(hobbyText, normalFont, 11, MARGIN + 10, currentY, Color.BLACK);
                    currentY -= LINE_HEIGHT;
                }
            }
            
            contentStream.close();
            document.save(outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    private void drawText(String text, PDFont font, float fontSize, float x, float y, Color color) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(color);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }

    private void drawWrappedText(String text, PDFont font, float fontSize, float x, float y, float maxWidth, Color color) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(color);
        contentStream.newLineAtOffset(x, y);
        
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        
        for (String word : words) {
            if (line.length() > 0) {
                line.append(" ");
            }
            float lineWidth = getStringWidth(line + word, font, fontSize);
            if (lineWidth > maxWidth) {
                contentStream.showText(line.toString());
                contentStream.newLineAtOffset(0, -LINE_HEIGHT);
                currentY -= LINE_HEIGHT;
                line = new StringBuilder(word);
            } else {
                line.append(word);
            }
        }
        
        if (line.length() > 0) {
            contentStream.showText(line.toString());
        }
        
        contentStream.endText();
    }

    private float getStringWidth(String text, PDFont font, float fontSize) throws IOException {
        return font.getStringWidth(text) / 1000 * fontSize;
    }

    private void drawHorizontalLine(float width) throws IOException {
        contentStream.setLineWidth(0.5f);
        contentStream.setStrokingColor(new Color(200, 200, 200));
        contentStream.moveTo(MARGIN, currentY);
        contentStream.lineTo(MARGIN + width, currentY);
        contentStream.stroke();
    }

    private void drawSectionTitle(String title, Color color) throws IOException {
        drawText(title, boldFont, 16, MARGIN, currentY, color);
        currentY -= LINE_HEIGHT;
        drawHorizontalLine(100);
        currentY -= SECTION_GAP;
    }
}
