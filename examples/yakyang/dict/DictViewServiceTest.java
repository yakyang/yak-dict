package yakyang.dict;

import java.util.*;

import org.junit.*;
import org.springframework.context.*;
import org.springframework.context.support.*;

import yakyang.dict.bean.*;
import yakyang.dict.bean.Dictionary;
import yakyang.dict.service.*;

public class DictViewServiceTest {

	DictViewService dictViewService;

	@Before
	public void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		dictViewService = (DictViewService) context.getBean("dictViewService");
	}
	
	@Test
	public void getCodeMap1() {
		Map<String, String> map = dictViewService.getCodeMap("payment.channel.online");
		for (String key : map.keySet()) {
			System.out.println(key + "  <---> " + map.get(key));
		}
	}
	
	@Test
	public void getCodeMap2() {
		Map<String, String> map = dictViewService.getCodeMap("payment.channel.online",true);
		for (String key : map.keySet()) {
			System.out.println(key + "  <---> " + map.get(key));
		}
	}

	@Test
	public void getCodeMap3() {
		Map<String, String> map = dictViewService.getCodeMap("payment.channel", "payment.channel.online");
		for (String key : map.keySet()) {
			System.out.println(key + "  <---> " + map.get(key));
		}
	}
	
	@Test
	public void getCodeMap4() {
		Map<String, String> map = dictViewService.getCodeMap("payment.channel", "payment.channel.online",true);
		for (String key : map.keySet()) {
			System.out.println(key + "  <---> " + map.get(key));
		}
	}
	
	@Test
	public void getDictsByExcludes(){
		Set<String> excludeClassCodes = new HashSet<String>();
		excludeClassCodes.add("china.county");
		List<Dict> dicts = dictViewService.getDictsByExcludes(excludeClassCodes);
		for(Dict dict : dicts){
			System.out.println(dict.getClassCode()+" --> "+dict.getName());
		}
	}
	
	@Test
	public void getDictsByClassCodePattern(){
		List<Dict> dicts = dictViewService.getDictsByClassCodePattern("company");
		for(Dict dict : dicts){
			System.out.println(dict.getClassCode()+" --> "+dict.getName());
		}
	}
	
	@Test
	public void getNameByCode1(){
		String name = dictViewService.getNameByCode("company.salesman", "2001");
		System.out.println("name = "+name);
	}
	
	
	@Test
	public void getNameByCode3(){
		Collection<String> codes = new HashSet<String>();
		codes.add("3001");
		codes.add("2001");
		String name = dictViewService.getNameByCodes("company", codes);
		System.out.println("name = "+name);
	}
	
	@Test
	public void getCodeMapHierarchy() {
		Map<String, Map<String, String>> map = dictViewService.getCodeMapHierarchy("payment.channel");
		for (String key1 : map.keySet()) {
			for (String key2 : map.get(key1).keySet()) {
				System.out.println(key1 + " --> " + key2 + " --> " + map.get(key1).get(key2));
			}
		}
	}

	@Test
	public void getDictionaryByClassCode() {
		Dictionary dictionary = dictViewService.getDictionaryByClassCode(null);
		for (SubDictionary dd : dictionary.getSubDictionaries()) {
			System.out.println("classCode : " + dd.getClassCode());
			for (CodeMapTable codeMapTable : dd.getCodeMapTables()) {
				System.out.println("------>" + codeMapTable.getScopeId());
				for (CodeMap codeMap : codeMapTable.getCodeMaps()) {
					System.out.println("             " + codeMap.getCode() + " ---- " + codeMap.getName());
				}
			}
		}
	}

	@Test
	public void getScopeIds() {
		Set<String> scopes = dictViewService.getScopeIds("china.city");
		for (String scope : scopes) {
			System.out.println("scopeId = " + scope);
		}
	}

}
