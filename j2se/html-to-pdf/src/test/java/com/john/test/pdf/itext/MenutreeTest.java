package com.john.test.pdf.itext;

import java.io.FileOutputStream;

import org.junit.Test;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfWriter;
import com.john.utils.ItextUtils;

/**
 * 树形结构
 * @author zhang.hc
 * @date 2016年10月24日 下午3:12:18
 */
public class MenutreeTest {
	/**
	 * 这个demo跑下来树形是
	 * ROOT
	 * 	1.小标题1-1
	 * 		1.1 小标题1-2
	 * 	2.小标题2-1
	 * 		2.1小标题2-2
	 */
	@Test
	public void test() {
		try {
			Rectangle rectPageSize = new Rectangle(PageSize.A4);//定义A4页面大小
			rectPageSize.setBackgroundColor(BaseColor.WHITE);//设置背景色,不设置默认白色
			Document document = new Document(rectPageSize, 50, 50, 50, 50);//设置4个方向的边距
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("E:/hc.pdf"));//关联好document和输出IO流之间的映射关系
			
			writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);//这是打开文档的时候默认显示属性结构
			
			document.open();
			
			//一个pdf至少放一个页面，要不然就会报错
			Chapter chapter = new Chapter("ROOT", 1);
			chapter.setNumberDepth(0);//将根节点的前置数字去掉
			
			//一个节点
			Section section = chapter.addSection(ItextUtils.cnParagraph("小标题1-1", 14, Font.BOLDITALIC), 1);
			section.add(ItextUtils.cnParagraph("内容内容1-1", 12, Font.NORMAL));
			section.add(ItextUtils.cnParagraph("内容内容1-1ext", 12, Font.NORMAL));
			section.setBookmarkOpen(false);
			
			Section section2 = section.addSection(ItextUtils.cnParagraph("小标题1-2", 14, Font.BOLDITALIC), 2);//后面这个数字2说明要跟随前面的数字进行级联
			section2.add(ItextUtils.cnParagraph("内容1-2内容1-2", 12, Font.NORMAL));
			
			//另一个节点
			Section section3 = chapter.addSection(ItextUtils.cnParagraph("小标题2-1", 14, Font.BOLDITALIC), 1);
			section3.add(ItextUtils.cnParagraph("内容内容2-1", 12, Font.NORMAL));
			section3.setBookmarkOpen(false);
			
			Section section4 = section3.addSection(ItextUtils.cnParagraph("小标题2-2", 14, Font.BOLDITALIC), 2);//后面这个数字2说明要跟随前面的数字进行级联
			section4.add(ItextUtils.cnParagraph("内容2-2内容2-2", 12, Font.NORMAL));
			
			document.add(chapter);
			
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test//网上的例子
	public void testComplex() {
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);  
        try {  
            // step 2: we create a writer that listens to the document  
            PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream("E:/hc.pdf"));  
            // step 3: we open the document  
            writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);  
            document.open();  
            // step 4: we add content to the document  
            // we define some fonts  
            Font chapterFont = ItextUtils.cnFont(24, Font.NORMAL);  
            Font sectionFont = ItextUtils.cnFont(20, Font.NORMAL);  
            Font subsectionFont = ItextUtils.cnFont(18, Font.NORMAL);  
            // we create some paragraphs  
            Paragraph blahblah = new Paragraph("blah blah blah blah blah blah blaah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah");  
            Paragraph blahblahblah = new Paragraph("blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blaah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah");  
            // this loop will create 7 chapters  
            for (int i = 1; i < 8; i++) {  
                Paragraph cTitle = new Paragraph("This is chapter " + i, chapterFont);
                Chapter chapter = new Chapter(cTitle, i);
                // in chapter 4 we change the alignment to ALIGN_JUSTIFIED  
                if (i == 4) {  
                    blahblahblah.setAlignment(Element.ALIGN_JUSTIFIED);
                    blahblah.setAlignment(Element.ALIGN_JUSTIFIED);  
                    chapter.add(blahblah);  
                }  
                // in chapter 5, the alignment is changed again  
                if (i == 5) {  
                    blahblahblah.setAlignment(Element.ALIGN_CENTER);  
                    blahblah.setAlignment(Element.ALIGN_RIGHT);  
                    chapter.add(blahblah);  
                }  
                // the alignment is changed to ALIGN_JUSTIFIED again  
                if (i == 6) {  
                    blahblahblah.setAlignment(Element.ALIGN_JUSTIFIED);  
                    blahblah.setAlignment(Element.ALIGN_JUSTIFIED);  
                }  
                // in every chapter 3 sections will be added  
                for (int j = 1; j < 4; j++) {  
                    Paragraph sTitle = new Paragraph("This is section " + j + " in chapter " + i, sectionFont);  
                    Section section = chapter.addSection(sTitle, 1);  
                    // for chapters > 2, the outine isn't open by default  
                    if (i > 2) section.setBookmarkOpen(false);
                    // in all chapters except the 1st one, some extra text is added to section 3  
                    if (j == 3 && i > 1) {  
                        section.setIndentationLeft(72);  
                        section.add(blahblah);  
                        section.add(new Paragraph("test"));  
                    }  
                    // in every section 3 subsections are added  
                    for (int k = 1; k < 4; k++) {  
                        Paragraph subTitle = new Paragraph("This is subsection " + k + " of section " + j, subsectionFont);  
                        Section subsection = section.addSection(subTitle, 3);  
                        // in the first subsection of section 3, extra text is added  
                        if (k == 1 && j == 3) {  
                            subsection.add(blahblahblah);  
                        }  
                        subsection.add(blahblah);  
                    }  
                    // in the section section of every chapter > 2 extra text is added  
                    if (j == 2 && i > 2) {  
                        section.add(blahblahblah);  
                    }  
                    // a new page is added after the second section in Chapter 1  
                    if (j == 2 && i == 1) {  
                        section.add(Chunk.NEXTPAGE);  
                    }  
                }  
                document.add(chapter);  
            }  
        }  
        catch(Exception de) {  
            de.printStackTrace();  
        }  
        // step 5: we close the document  
        document.close();
	}
}
