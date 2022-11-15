package com.gfg.pdf.openpdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class ConversionOfHtml2PdfWithOpenHtml {

    private static final String htmlnputFile = "src/main/resources/htmlforopenpdf.html";
    private static final String pdfOutputFile = "src/main/resources/html2pdf.pdf";

    public static void main(String[] args) {
        try {
        	ConversionOfHtml2PdfWithOpenHtml htmlToPdf = new ConversionOfHtml2PdfWithOpenHtml();
            htmlToPdf.generateHtmlToPdf();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateHtmlToPdf() throws IOException {
        File inputHTML = new File(htmlnputFile);
        Document doc = createWellFormedHtml(inputHTML);
        xhtmlToPdf(doc, pdfOutputFile);
    }

    private Document createWellFormedHtml(File inputHTML) throws IOException {
        Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings()
            .syntax(Document.OutputSettings.Syntax.xml);
        return document;
    }

    private void xhtmlToPdf(Document doc, String outputPdf) throws IOException {
        try (OutputStream os = new FileOutputStream(outputPdf)) {
            String baseUri = FileSystems.getDefault()
                .getPath("src/main/resources/")
                .toUri()
                .toString();
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(outputPdf);
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(doc), baseUri);
            builder.run();
        }
    }
}
