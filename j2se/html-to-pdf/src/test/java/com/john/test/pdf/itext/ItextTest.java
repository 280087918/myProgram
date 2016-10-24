package com.john.test.pdf.itext;

import java.io.FileOutputStream;

import org.junit.Test;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * ITEXT官网:http://itextpdf.com/
 * 这是一个简单的demo
 * @author zhang.hc
 * @date 2016年10月24日 上午9:30:00
 */
public class ItextTest {
	@Test//官方文档里面的简单demo，只是这里面加入了中文字体
	public void simpleTest() {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("E:/hc.pdf"));
			document.open();
//			Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
			Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
			
			BaseFont bfChinese =BaseFont.createFont("STSong-Light",  
                    "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
			Font fontChinese = new Font(bfChinese, 16, Font.BOLDITALIC);
			
			Chunk chunk = new Chunk("This is title(这斯是标题)", fontChinese);
			Chapter chapter = new Chapter(new Paragraph(chunk), 1);
			chapter.setNumberDepth(0);
			chapter.add(new Paragraph("This is the paragraph", paragraphFont));
			document.add(chapter);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
