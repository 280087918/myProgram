package com.john.test.task;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
/**
 * html
 * 	第一个标题的class="preface"
 * 	其他的标题class="chapter"
 * @author zhang.hc
 * @date 2016年10月24日 下午4:00:12
 */
public class ESApiToPdf {
	String webUrl = "https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/index.html";
	
	@Test
	public void exportPdf() {
		Element menuEl = getHtmlData(webUrl, "ul", "toc");
		Elements menuEls = menuEl.children();
		for(Element element : menuEls) {
			System.out.println(element.html());
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
