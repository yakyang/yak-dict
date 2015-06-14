package yakyang.dict.company;

import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import yakyang.dict.bean.*;
import yakyang.dict.service.*;

@Service
public class AccountantDictView implements DictViewInterceptor {

	public static final String DICT_CLASS_CODE = "company.accountant";

	@Autowired
	private AccountantDao accountantDao;

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
		List<Accountant> list = accountantDao.findAccountant();
		for (Accountant accountant : list) {
			map.put(accountant.getId().toString(), accountant.getName());
		}
		return map;
	}

	@Override
	public String getNameByCode(String classCode, String code, boolean includeHelpCode) {
		Accountant accountant = accountantDao.findAccountantById(Integer.valueOf(code));
		if (accountant != null) {
			return accountant.getName();
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
			List<Accountant> list = accountantDao.findAccountant();
			Dict dict = new Dict();
			for (Accountant driver : list) {
				dict.addCodeName(driver.getId().toString(), driver.getName());
			}
			dict.setClassCode(this.DICT_CLASS_CODE);
			dict.setName("会计员列表");
			dict.optimize(true);
			dicts.add(dict);

		}
		return dicts;
	}

}
