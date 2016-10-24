package com.john.utils;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;

public class ItextUtils {
	/**
	 * 创建中文字体Font
	 * @param size:字体大小
	 * @param fontType:文字样式,枚举:如Font.BOLDITALIC
	 * @return
	 */
	public static Font cnFont(float size, int fontType) {
		try {
			BaseFont bfChinese =BaseFont.createFont("STSong-Light",  
					"UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
			Font fontChinese = new Font(bfChinese, size, fontType);
			return fontChinese;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Paragraph cnParagraph(String text, float size, int fontType) {
		return new Paragraph(new Chunk(text, cnFont(14, Font.BOLDITALIC)));
	}
}
