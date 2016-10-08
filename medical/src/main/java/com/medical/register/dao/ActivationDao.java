package com.medical.register.dao;

import com.medical.register.entity.ActivationCode;

public interface ActivationDao {
	/**
	 * 获取激活码
	 * @param code
	 * @return
	 */
	ActivationCode getActivationCode(String code);
	/**
	 * 修改激活码
	 * @param activationCode
	 */
	void updateActivationCode(ActivationCode activationCode);

}
