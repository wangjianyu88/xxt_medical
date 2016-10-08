package com.medical.maintain.bean;
import com.medical.maintain.entity.Medical;
/**
 * 药物信息
 * @author 
 * @time 下午10:51:11
 */
public class MedicalInfo extends Medical{
	/*以英文逗号分隔的主键*/
	private String medicaTimeIds;
	/*以英文逗号分隔的哪次服药*/
	private String whichTimes;
	public String getMedicaTimeIds() {
		return medicaTimeIds;
	}
	public void setMedicaTimeIds(String medicaTimeIds) {
		this.medicaTimeIds = medicaTimeIds;
	}
	public String getWhichTimes() {
		return whichTimes;
	}
	public void setWhichTimes(String whichTimes) {
		this.whichTimes = whichTimes;
	}
}
