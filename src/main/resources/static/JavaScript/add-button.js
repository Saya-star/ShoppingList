//料理の材料と調味料の登録・編集時に入力欄を追加するためのボタン

$(document).ready(function() {

	var max_fields = 20;//材料は20個まで入力可
	var min_fields = 1;//最小値は1個

	var n = 1;
	//材料の入力欄を増やす
	$("#add_ingredient").click(function() {
		if (n < max_fields) {
			n++;
			var element = $(".ingredient_input_field").clone(true)[0];//材料の入力エリアのクローン作成
			//入力エリアの初期化
			var inputList = element.querySelectorAll('input[type="text"]');
			for (var i = 0; i < inputList.length; i++) {
				inputList[i].value = "";
			}

			// name属性の更新
			element.querySelectorAll('input,select').forEach(e => {
				e.name = `${e.dataset.modelName}[${n - 1}].${e.dataset.attrName}`
			});

			$("#ingredients_input_fields").append(element);
		}
	});

	//材料の入力欄を減らす
	$(".remove_ingredient").click(function() {
		if (n > min_fields) {
			$(this).closest(".ingredient_input_field").remove();
			n--;
		}
	});

	var m = 1;
	//調味料の入力欄を増やす
	$("#add_seasoning").click(function() {
		if (m < max_fields) {
			m++;
			var element = $(".seasoning_input_field").clone(true)[0];//入力エリアのクローン作成
			//入力エリアの初期化
			var inputList = element.querySelectorAll('input[type="text"]');
			for (var i = 0; i < inputList.length; i++) {
				inputList[i].value = "";
			}

			// name属性の更新
			element.querySelectorAll('input').forEach(e => {
				e.name = `${e.dataset.modelName}[${m - 1}].${e.dataset.attrName}`
			});

			$("#seasonings_input_fields").append(element);
		}
	});

	//調味料の入力欄を減らす
	$(".remove_seasoning").click(function() {
		if (m > min_fields) {
			$(this).closest(".seasoning_input_field").remove();
			m--;
		}
	});

});