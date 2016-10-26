package com.john.test.task;

import java.io.FileOutputStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfWriter;
import com.john.utils.ItextUtils;
/**
 * html
 * 	第一个标题的class="preface"
 * 	其他的标题class="chapter"
 * @author zhang.hc
 * @date 2016年10月24日 下午4:00:12
 */
public class ESApiToPdf {
	String webUrl = "https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/index.html";
	String uri = "https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/";
	
	@Test
	public void exportPdf() {
		try {
			//----PDF准备(开始)
			Rectangle rectPageSize = new Rectangle(PageSize.A4);//定义A4页面大小
			rectPageSize.setBackgroundColor(BaseColor.WHITE);//设置背景色,不设置默认白色
			com.itextpdf.text.Document document = new com.itextpdf.text.Document(rectPageSize, 50, 50, 50, 50);//设置4个方向的边距
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("E:/elasticsearch_Java_guide_2.4.pdf"));//关联好document和输出IO流之间的映射关系
			
			writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);//这是打开文档的时候默认显示属性结构
			
			document.open();
			//----PDF准备(结束)
			
			Chapter chapter = new Chapter("elasticsearch Java guide 2.4", 1);
			chapter.setNumberDepth(0);//将根节点的前置数字去掉
			
			Element menuEl = getHtmlData(webUrl, "ul", "toc");
			Elements menuEls = menuEl.children();
			for(Element element : menuEls) {
				//System.out.println(element.html());
				if(element.getElementsByTag("span").hasClass("preface") || element.getElementsByTag("span").hasClass("chapter")) {
					String url = uri + element.getElementsByTag("span").get(0).getElementsByTag("a").attr("href");//目录的url
					Paragraph pageParaph = ItextUtils.enParagraph(element.getElementsByTag("span").get(0).text(), 18, Font.BOLD);
					pageParaph.setSpacingBefore(20.0f);
					pageParaph.setSpacingAfter(1.0f);
					Section titleSection = chapter.addSection(pageParaph);
					titleSection.setNumberDepth(0);
					titleSection.setBookmarkOpen(false);
					Element pageElement = getHtmlData(url, "div", element.getElementsByTag("span").attr("class"));
					//titleSection.add(ItextUtils.enParagraph(pageElement.getElementsByClass("title").get(0).text(), 18, Font.BOLDITALIC));//具体页面上面的title
					Elements pageElements = new Elements();
					for(Element pElementF : pageElement.getAllElements()) {
						if(pElementF.getElementsByTag("p").size() > 0) {//段落
							if(!pageElements.contains(pElementF.getElementsByTag("p").get(0))) {
								pageElements.add(pElementF.getElementsByTag("p").get(0));
							}
						}
						if(pElementF.getElementsByClass("pre_wrapper").size() > 0) {//代码
							if(!pageElements.contains(pElementF.getElementsByClass("pre_wrapper").get(0))) {
								pageElements.add(pElementF.getElementsByClass("pre_wrapper").get(0));
							}
						}
						if(pElementF.getElementsByTag("li").size() > 0) {//li
							if(!pageElements.contains(pElementF.getElementsByTag("li").get(0))) {
								pageElements.add(pElementF.getElementsByTag("li").get(0));
							}
						}
					}
					for(Element pElement : pageElements) {
						titleSection.add(ItextUtils.enParagraph(pElement.text(), 12, Font.NORMAL));//遍历页面上的段落
					}
					
					//遍历完一个标题，紧接着判断下面有没有子节点
					if(element.getAllElements().hasClass("section")) {
						//System.out.println(element.getElementsByClass("chapter").get(0).text() + " 有子节点");
						for(Element subElement : element.getElementsByClass("section")) {
							//添加子标题
							Paragraph subParagraph = ItextUtils.enParagraph(subElement.text(), 16, Font.BOLD);
							subParagraph.setSpacingBefore(6.0f);
							subParagraph.setSpacingAfter(1.0f);
							Section subSection = titleSection.addSection(subParagraph);
							
							//更上面一样，组装好需要添加的数据集合
							String subUrl = uri + subElement.getElementsByTag("a").attr("href");
							Element subPageElement = getHtmlData(subUrl, "div", "section");
							Elements subPageElements = new Elements();
							for(Element pElementS : subPageElement.getAllElements()) {
								if(pElementS.getElementsByTag("p").size() > 0) {//段落
									if(!subPageElements.contains(pElementS.getElementsByTag("p").get(0))) {
										subPageElements.add(pElementS.getElementsByTag("p").get(0));
									}
								}
								if(pElementS.getElementsByClass("pre_wrapper").size() > 0) {//代码
									if(!subPageElements.contains(pElementS.getElementsByClass("pre_wrapper").get(0))) {
										subPageElements.add(pElementS.getElementsByClass("pre_wrapper").get(0));
									}
								}
								if(pElementS.getElementsByTag("li").size() > 0) {//li
									if(!subPageElements.contains(pElementS.getElementsByTag("li").get(0))) {
										subPageElements.add(pElementS.getElementsByTag("li").get(0));
									}
								}
							}
							
							//开始往section里面添加
							for(Element spElement : subPageElements) {
								subSection.add(ItextUtils.enParagraph(spElement.text(), 12, Font.NORMAL));
							}
						}
					}
				}
				System.out.println("已生成一页.");
			}
			
			document.add(chapter);
			document.close();//关闭PDF.document，相当于flush出相应的流到本地生成PDF
		} catch (Exception e) {
			
		}
	}
	
	private Element getHtmlData(String reqUrl, String tagName, String className) {
		try {
			Document doc = Jsoup.connect(reqUrl).get();
			Elements elements = doc.getElementsByTag(tagName);
			if(CollectionUtils.isNotEmpty(elements)) {
				if(elements.size() == 1) {//如果根据标签只有一个元素，就将元素拿出来就可以了
					return elements.get(0);
				} else {
					for(Element element : elements) {
						if(StringUtils.isNotBlank(className) && element.hasClass(className)) {
							return element;
						}
					}
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
