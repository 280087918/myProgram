package com.john.test.pdf;

import org.junit.Test;

/**
 * @author zhang.hc
 * @date 2016年10月19日 上午11:23:40
 */
public class PdfBoxApiTest {
	
	@Test//apache pdfbox不支持中文，扔掉
	public void pdfBoxApiTest() {
		/*try {
			PDDocument document = new PDDocument();
			PDPage page = new PDPage();
			document.addPage(page);
			
			PDFont font = PDType1Font.HELVETICA_BOLD;
			
			PDPageContentStream pageContentStream = new PDPageContentStream(document, page);
			pageContentStream.beginText();
			pageContentStream.setFont(font, 14);
			pageContentStream.newLineAtOffset(100, 700);
			pageContentStream.showText("hello world");
			pageContentStream.endText();
			
			pageContentStream.close();
			
			document.save("E:/hc.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
