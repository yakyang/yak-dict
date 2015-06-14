package yakyang.dict;

import java.util.*;
import org.junit.*;
import org.springframework.context.*;
import org.springframework.context.support.*;
import yakyang.dict.bean.*;
import yakyang.dict.dao.*;

public class DictDaoTest {
	
	DictDao dictDao;
	
	@Before
	public void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		dictDao = (DictDao) context.getBean("dictDao");
	}
	
	@Test
	public void getDictByClassCode(){
		Dict dict = dictDao.getDictByClassCode("china.city");
		System.out.println("dict.getName() = "+dict.getName());
	}
	
	@Test
	public void getDictByClassCodePattern(){
		List<Dict> dicts = dictDao.getDictByClassCodePattern("payment.channel");
		for(Dict dict : dicts){
			System.out.println("[classCode="+dict.getClassCode()+"] name = "+dict.getName());
		}
	}

}
