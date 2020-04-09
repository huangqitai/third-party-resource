<script type="text/javascript"
	src="${resourceUri}/js/${entityPackage}/${entityPackage}${className}.js"></script>
<script>
	$(document).ready(function() {
		$('#form').ajaxpager({
			target : '#list'
		});
	})
</script>
<form id="form" action="${base}/${lowerName}/list" method="post">
	<div class="main_right_searchbar">
		<input name="" type="text" value="关键词" /> <a href="#"><img
			src="${resourceUri}/css/images/search_btn.png" /></a> <span><a
			href="#">高级搜索</a></span>
	</div>
	<div class="main_right_btn">
		<a style="margin-left: 0px;" class="main_right_btn2"
			href="javascript:${entityPackage}${lowerName}.Add();"> <span
			class="add_icon">新增</span>
		</a> <a class="main_right_btn2"
			href="javascript:${entityPackage}${lowerName}.edit();"> <span
			class="minus_icon">查看</span>
		</a> <a class="main_right_btn2"
			href="javascript:${entityPackage}${lowerName}.delete();"> <span
			class="minus_icon">删除</span>
		</a> <a class="main_right_btn2" href="#"> <span class="upload_icon">导入</span>
		</a> <a class="main_right_btn2" href="#"> <span class="download_icon">导出</span>
		</a>
	</div>
	<div id="list"><#include "${entityPackage}/${lowerName}_list.html" /></div>
</form>