/**
 * js
 *
 */
var materialequipment = {
	
	/**
	 * 新增
	 */
	Add: function(param) {
		$.ajax({
			type: "post", 
			url: baseURL + "/${lowerName}/add",
			data: param,
			dataType: "html",
			success: function(html) {
				$(".main_right").html("").html(html);
			}
		});
	},

	/**
	 * 保存详情
	 */
	bindSaveDetail: function() {
		$(".save_icon").click(function(){
			var options = {
				type: "post",
				url: baseURL + "/${lowerName}/save",
				success: function(result){
					alert(result);
				}
			}
			$("form").ajaxSubmit(options);
		});
	},
	
	/**
	 * 删除
	 */
	delete: function(id) {
		var Ids = "";
		var obj = document.getElementsByName("Id");
		for(var i=0;i<obj.length;i++){
			if(obj[i].checked){
				Ids += obj[i].value + ",";
			}
		}
		if(Ids !=""){
			$.ajax({
				type: "post",
				url: baseURL + "/${lowerName}/delete",
				data: {"ids": Ids},
				success: function(json) {
				}
			});
		}else{
			alert("请选择");
		}
	},
	
	edit: function(id) {
		var obj = document.getElementsByName("Id");
		var Ids = "";
		for(var i=0;i<obj.length;i++){
			if(obj[i].checked){
				Ids = obj[i].value;
			}
		}
		if(Ids !=""){
			$.ajax({
				type: "post",
				url: baseURL + "/${lowerName}/edit",
				data: {"Id": Ids},
				dataType: "html",
				success: function(html) {
					$(".main_right").html("").html(html);
				}
			});
		}else{
			alert("请选择");
		}
	},
};