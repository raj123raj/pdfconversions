package com.gfg.pdf.openpdf;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ConversionOfHtml2PdfWithFlyingSaucer {

    private static final String htmlnputFile = "src/main/resources/htmlforopenpdf.html";
    private static final String pdfOutputFile = "src/main/resources/html2pdf.pdf";

    public static void main(String[] args) {
        try {
            ConversionOfHtml2PdfWithFlyingSaucer htmlToPdfConversion = new ConversionOfHtml2PdfWithFlyingSaucer();
            htmlToPdfConversion.generationOfHtmlToPdf();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generationOfHtmlToPdf() throws Exception {
        File inputHTMLFile = new File(htmlnputFile);
        Document inputHtmlFile = creationWellFormedHtml(inputHTMLFile);
        File outputPdfFile = new File(pdfOutputFile);
        conversionOfxhtmlToPdf(inputHtmlFile, outputPdfFile);
    }

    private Document creationWellFormedHtml(File inputHTML) throws IOException {
        Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings()
            .syntax(Document.OutputSettings.Syntax.xml);
        return document;
    }

    private void conversionOfxhtmlToPdf(Document xhtml, File outputPdf) throws Exception {
        try (OutputStream outputStream = new FileOutputStream(outputPdf)) {
            ITextRenderer iTextRenderer = new ITextRenderer();
            SharedContext sharedContext = iTextRenderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            sharedContext.setReplacedElementFactory(new CustomElementFactoryImpl());
            iTextRenderer.setDocumentFromString(xhtml.html());
            iTextRenderer.layout();
            iTextRenderer.createPDF(outputStream);
        }
    }
}
