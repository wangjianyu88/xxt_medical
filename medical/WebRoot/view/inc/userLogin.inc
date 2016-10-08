<div class="userinfo">
	<span class="fright"><a href="${path!}/login/loginIndex.do"><@spring.message code="page.common.back"/>&gt;&gt;</a></span>
	<@spring.message code="page.common.welcome"/><em>${familyUser.name!}</em>&nbsp;&nbsp;
	<a href="${path!}/login/logout.do" ><@spring.message code="page.login.cancel"/></a>&nbsp;&nbsp;
	<a href="${path!}/remind/updatePasswordIndex.do" ><@spring.message code="page.login.changePassword"/></a>
</div>