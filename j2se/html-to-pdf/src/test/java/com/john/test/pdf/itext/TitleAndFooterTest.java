package com.john.test.pdf.itext;

import java.io.FileOutputStream;

import org.junit.Test;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.john.utils.ItextUtils;

/**
 * PDF页头页脚相关信息，目前页脚没有相关的设置方式，只有PDF属性信息可以设置，这个信息不是十分重要
 * @author zhang.hc
 * @date 2016年10月24日 上午11:02:23
 */
public class TitleAndFooterTest {
	@Test
	public void test() {
		try {
			Rectangle rectPageSize = new Rectangle(PageSize.A4);//定义A4页面大小
			rectPageSize.setBackgroundColor(BaseColor.WHITE);//设置背景色,不设置默认白色
			Document document = new Document(rectPageSize, 50, 50, 50, 50);//设置4个方向的边距
			PdfWriter.getInstance(document, new FileOutputStream("E:/hc.pdf"));//关联好document和输出IO流之间的映射关系
			
			//这些属性可以在PDF里面的"文件->属性"里面看得到
			document.addTitle("ABC浩成");
			document.addSubject("subject(主体)");
			document.addAuthor("author(作者)");
			document.addCreator("creator(创建人)");
			document.addKeywords("keywords(关键词)");
			
			//footer在新的版本里面貌似没有了，也没那么重要，不管了。
			
			document.open();
			
			//一个pdf至少放一个页面，要不然就会报错
			Chunk chunk = new Chunk("This is title(这斯是标题)", ItextUtils.cnFont(12, Font.NORMAL));
			Chapter chapter = new Chapter(new Paragraph(chunk), 1);
			chapter.setNumberDepth(0);//不加这个的话有一个段落的数字符号
			document.add(chapter);
			
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
