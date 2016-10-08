<#assign spring=JspTaglibs["/WEB-INF/tld/spring.tld"]/>
<#-- 吃药单位计算 -->
<#macro takeUnit value>
	<#switch value> 
  		<#case 0> <@spring.message code="page.medical.unit.grain"/> <#break> 
  		<#case 1> <@spring.message code="page.medical.unit.slice"/>	<#break> 
  		<#case 2> <@spring.message code="page.medical.unit.bottle"/><#break>
  		<#case 3> <@spring.message code="page.medical.unit.bag"/>	<#break> 
  		<#case 4> <@spring.message code="page.medical.unit.box"/><#break> 
  		<#case 5> <@spring.message code="page.medical.unit.gram"/>	<#break>  
  		<#default> 
	</#switch>
</#macro>
<#-- 吃药时间点计算 -->
<#macro eatTime value>
	<#switch value> 
  		<#case 1><@spring.message code="page.medical.timing.before"/><#break> 
  		<#case 2><@spring.message code="page.medical.timing.after"/><#break> 
  		<#default> 
	</#switch>
</#macro>