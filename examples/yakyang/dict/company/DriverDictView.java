package yakyang.dict.company;

import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import yakyang.dict.bean.*;
import yakyang.dict.service.*;

@Service
public class DriverDictView implements DictViewInterceptor {

	public static final String DICT_CLASS_CODE = "company.driver";

	@Autowired
	private DriverDao driverDao;

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
		List<Driver> list = driverDao.findDriver();
		for (Driver customer : list) {
			map.put(customer.getId().toString(), customer.getName());
		}
		return map;
	}

	@Override
	public String getNameByCode(String classCode, String code, boolean includeHelpCode) {
		Driver driver = driverDao.findDriverById(Integer.valueOf(code));
		if (driver != null) {
			return driver.getName();
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
			List<Driver> list = driverDao.findDriver();
			Dict dict = new Dict();
			for (Driver driver : list) {
				dict.addCodeName(driver.getId().toString(), driver.getName());
			}
			dict.setClassCode(this.DICT_CLASS_CODE);
			dict.setName("司机列表");
			dict.optimize(true);
			dicts.add(dict);

		}
		return dicts;
	}

}
