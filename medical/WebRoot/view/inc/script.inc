<script type="text/javascript">
	var _path_ = "${path!}";
	var _language_ = "${languageTag!}";
</script>
<#if (languageTag=='zh-CN')>
<link href="${path!}/css/main.css" rel="stylesheet" type="text/css">
<#else>
<link href="${path!}/css/main_en.css" rel="stylesheet" type="text/css">
</#if>
<link href="${path!}/script/pagination/jquery.pagination.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${path!}/script/jquery.js"></script>
<script language="javascript" src="${path!}/script/jquery.form.js"></script>
<script language="javascript" src="${path!}/script/jquery.i18n.properties-min-1.0.9.js"></script>
<script language="javascript" src="${path!}/script/pagination/jquery.pagination.js"></script>
<script language="javascript" src="${path!}/script/pagination/pagination.js"></script>
<script language="javascript" src="${path!}/script/enumCompute.js"></script>