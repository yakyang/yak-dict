package yakyang.dict.company;

import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import yakyang.dict.bean.*;
import yakyang.dict.service.*;

@Service
public class SalesmanDictView implements DictViewInterceptor {

	public static final String DICT_CLASS_CODE = "company.salesman";

	@Autowired
	private SalesmanDao salesmanDao;

	@Override
	public boolean isIntercepted(String classCode) {
		if (DICT_CLASS_CODE.indexOf(classCode)>-1) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, String> getCodeMap(String classCode, String scopeId, boolean includeHelpCode) {
		Map<String, String> map = new TreeMap<String, String>();
		List<Salesman> list = salesmanDao.findSalesman();
		for (Salesman salesman : list) {
			map.put(salesman.getId().toString(), salesman.getName());
		}
		return map;
	}

	@Override
	public String getNameByCode(String classCode, String code, boolean includeHelpCode) {
		Salesman salesman = salesmanDao.findSalesmanById(Integer.valueOf(code));
		if (salesman != null) {
			return salesman.getName();
		} else {
			return null;
		}
	}

	@Override
	public Set<String> getScopeIds(String classCode) {
		return new TreeSet<String>();
	}

	@Override
	public List<Dict> getDicts(String classCodePattern) {
		List<Dict> dicts = new ArrayList<Dict>();
		if (this.DICT_CLASS_CODE.startsWith(classCodePattern)) {
			List<Salesman> list = salesmanDao.findSalesman();
			Dict dict = new Dict();
			for (Salesman salesman : list) {
				dict.addCodeName(salesman.getId().toString(), salesman.getName());
			}
			dict.setClassCode(this.DICT_CLASS_CODE);
			dict.setName("销售员列表");
			dict.optimize(true);
			dicts.add(dict);

		}
		return dicts;
	}
	
}
