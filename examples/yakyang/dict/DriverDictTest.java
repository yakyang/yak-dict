package yakyang.dict;

import java.util.*;

import org.junit.*;
import org.springframework.context.*;
import org.springframework.context.support.*;

import yakyang.dict.company.*;
import yakyang.dict.service.*;

public class DriverDictTest {

	DictViewService dictViewService;

	@Before
	public void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		dictViewService = (DictViewService) context.getBean("dictViewService");
	}

	@Test
	public void getCodeMap() {
		Map<String, String> map = dictViewService.getCodeMap(DriverDictView.DICT_CLASS_CODE);
		for (String key : map.keySet()) {
			System.out.println(key + "  <---> " + map.get(key));
		}
	}

}
