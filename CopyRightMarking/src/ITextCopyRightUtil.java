import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

/**
 * Hello world!
 *
 */
public class ITextCopyRightUtil 
{
    //AGPL License URL : https://github.com/amritSrf/ITextCopyrightStamper
	// STAMPING USING ITEXT
	public static void stampPdf(String inputPdfPath, String outputPDfpath, String text) throws Exception {
			PdfReader reader = new PdfReader(inputPdfPath);
			PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(outputPDfpath));
			Document document = new Document(pdfDoc);

			reader.setUnethicalReading(true);
			Rectangle pageSize;
			Text blackText = new Text(text).setFontColor(ColorConstants.BLACK).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA, PdfEncodings.WINANSI, true));
			Paragraph footer = new Paragraph(blackText).setFontSize(7);
			for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
				PdfPage pdfPage = pdfDoc.getPage(i);
				pageSize = pdfPage.getPageSize();

				int xCordinate = (int) (pageSize.getLeft() + pageSize.getWidth() / 2);
				int yCordinate = 7;
				if (pageSize.getHeight() == 1224.0f) {
					yCordinate = 227;
				} else if (pageSize.getHeight() == 788.0f) {
					yCordinate = 13;
				}
				if (pageSize.getBottom() != 0) {
					yCordinate = (int) (yCordinate + pageSize.getBottom());
				}
				document.showTextAligned(footer, xCordinate, yCordinate, i, TextAlignment.CENTER, VerticalAlignment.BOTTOM, 0);
			}
			document.close();
			pdfDoc.close();
			System.out.println("PDF updated");
		}


	private static String readFromFile(File inputFile) throws IOException {
		StringBuilder resultStringBuilder = new StringBuilder();
		InputStream inputStream = new FileInputStream(inputFile);
		try (BufferedReader br
				= new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
			br.close();
			inputStream.close();
		}
		return resultStringBuilder.toString();
	}
	
	public static void main( String[] args )
    {
		String inputPdf = args[0];
		String outputPdf = args[1];
		String stampingTextFile = args[2];

		try{
						File txtFile =  new File(stampingTextFile);
			if(txtFile.exists()){
				String stampingText = readFromFile(txtFile);
				stampPdf(inputPdf,outputPdf,stampingText);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
    }
}
