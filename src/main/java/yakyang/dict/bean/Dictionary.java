package yakyang.dict.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dictionary implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 子字典
	 */
	private List<SubDictionary> subDictionaries = new ArrayList<SubDictionary>();

	public List<SubDictionary> getSubDictionaries() {
		return subDictionaries;
	}

	public void setSubDictionaries(List<SubDictionary> subDictionaries) {
		this.subDictionaries = subDictionaries;
	}
}
