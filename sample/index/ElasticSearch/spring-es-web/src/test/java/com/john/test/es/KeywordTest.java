package com.john.test.es;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.dao.KeywordService;
import com.john.test.BaseTest;
import com.john.vo.Keyword;

/**
 * `ik_smart` , `ik_max_word`

	http://192.168.22.181:9200/_analyze?analyzer=ik_max_word&text=美的遥控式落地电风扇FS40-16ER

	http://192.168.22.181:9200/local/keyword/_search
	  {
		"query" : {
			"query_string" : { "query" : "name:电风扇" }
		}
	  }
 * @author zhang.hc
 * @date 2016年9月28日 下午3:39:39
 */
public class KeywordTest extends BaseTest {
	@Autowired
	private KeywordService keywordService;
	
	@Test
	public void saveDatas() {
		//放到下面是不想影响我的视线
		save();
	}
	
	@Test
	public void searchTest() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyword", "空调");
//		params.put("searchId", "pid002");//parentId
		//params.put("searchId", "k001");//id
		keywordService.searchKeywords(params);
	}
	
	@Test//删除索引,这个比较彻底，连mapping都给删了
	public void removeIndex() {
		keywordService.removeIndex();
	}
	
	@Test
	public void clearData() {
		keywordService.clearData();
	}
	
	private void save() {
		Keyword kw1 = new Keyword("k001", "海信空调KFR-35GW/A8S318N-A2(大1.5P)");
		kw1.addParentId("pid001");
		kw1.addParentId("pid002");
		kw1.addParentId("pid003");
		keywordService.persistObj(kw1);
		
		Keyword kw2 = new Keyword("k002", "海信空调KFR-50GW/A8D860N-N3 2P白色");
		kw2.addParentId("pid001");
		kw2.addParentId("pid002");
		kw2.addParentId("pid004");
		keywordService.persistObj(kw2);
		
		Keyword kw3 = new Keyword("k003", "2017年新版信封");
		kw3.addParentId("pid001");
		kw3.addParentId("pid002");
		kw3.addParentId("pid005");
		keywordService.persistObj(kw3);
		
		Keyword kw4 = new Keyword("k004", "调频FM收音机");
		kw4.addParentId("pid001");
		kw4.addParentId("pid002");
		kw4.addParentId("pid006");
		keywordService.persistObj(kw4);
		
		Keyword kw5 = new Keyword("k005", "海尔冰箱BCD-185TMPQ拉丝P219银色 双门");
		kw5.addParentId("pid001");
		kw5.addParentId("pid003");
		kw5.addParentId("pid007");
		keywordService.persistObj(kw5);
		
		Keyword kw6 = new Keyword("k006", "美乐爱家系列斩切刀K-04AK");
		kw6.addParentId("pid001");
		kw6.addParentId("pid004");
		kw6.addParentId("pid008");
		keywordService.persistObj(kw6);
		
		Keyword kw7 = new Keyword("k007", "海信空调KFR-72LW/A8T900Z-A2金色(3P)");
		kw7.addParentId("pid001");
		kw7.addParentId("pid005");
		kw7.addParentId("pid009");
		keywordService.persistObj(kw7);
		
		Keyword kw8 = new Keyword("k008", "空调被");
		kw8.addParentId("pid001");
		kw8.addParentId("pid005");
		kw8.addParentId("pid009");
		keywordService.persistObj(kw8);
	}
}
