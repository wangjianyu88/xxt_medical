<div class="menu">
	<div class="logo"><a href="#"><img src="${path!}/img/<#if (languageTag=='en-US')>logo_en.gif<#else>logo.gif</#if>" alt="家庭用药助手"></a></div>
    <div class="nav" id="headDiv">
    	<a href="${path!}/index.do"><@spring.message code="page.head.homePage"/></a>
    	<a href="${path!}/about/aboutUs.do"><@spring.message code="page.head.aboutUs"/></a>
    	<a href="${path!}/about/productIntroduction.do"  ><@spring.message code="page.head.product"/></a>
    	<a href="${path!}/register/registerIndex.do"><@spring.message code="page.head.freeSignUp"/></a>
    	<a href="${path!}/login/loginIndex.do"><@spring.message code="page.head.memberLogin"/></a>
    </div>
</div>
<script language="javascript">
$(function(){
	$("#userInfoDiv").hide();
	$("#headDiv").find("a").each(function(){
		if(location.href.indexOf($(this).attr("href"))>=0){
			$(this).attr("class","on");
			return false;
		}
	})
})
</script>