package com.medical.common.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.medical.common.commonconst.MedicalConst;
/**
 * 
 * @Package com.xdja.core
 * @ClassName: RichFreeMarkerView
 * @Description: 扩展spring的FreemarkerView，加上path属性
 * @author lcs
 * @date 2013-7-15 上午11:25:20
 */
public class RichFreeMarkerView extends FreeMarkerView {
	private static final Logger log = LoggerFactory
			.getLogger(RichFreeMarkerViewResolver.class);
	protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request)
			throws Exception {
		log.debug("##########RichFreeMarkerView<Start>##########");
		super.exposeHelpers(model, request);
		String languageTag = request.getLocale().toLanguageTag();
		model.put(MedicalConst.LANGUAGE_TAG, languageTag);
		model.put(MedicalConst.CONTEXT_PATH, request.getContextPath());
		log.debug("##########RichFreeMarkerView<End>##########");
	}
}