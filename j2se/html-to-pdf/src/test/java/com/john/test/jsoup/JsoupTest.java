package com.john.test.jsoup;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

public class JsoupTest {
	String url = "https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started.html";
	
	@Test
	public void getHtmlTest() {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements elements = doc.getElementsByClass("part");
			if(CollectionUtils.isNotEmpty(elements)) {
				System.out.println(elements.get(0).html());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
