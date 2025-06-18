package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class ResumeParser {
    public static String parse(String filePath) {
        if (filePath.endsWith(".pdf")) return parsePDF(filePath);
        if (filePath.endsWith(".docx")) return parseDOCX(filePath);
        return "Unsupported file type.";
    }

    private static String parsePDF(String path) {
        try (PDDocument doc = Loader.loadPDF(new File(path))) {
            return new PDFTextStripper().getText(doc).trim();
        } catch (IOException e) {
            return "Error reading PDF: " + e.getMessage();
        }
    }

    private static String parseDOCX(String path) {
        try (FileInputStream fis = new FileInputStream(path);
             XWPFDocument doc = new XWPFDocument(fis);
             XWPFWordExtractor extractor = new XWPFWordExtractor(doc)) {
            return extractor.getText().trim();
        } catch (IOException e) {
            return "Error reading DOCX: " + e.getMessage();
        }
    }
}
