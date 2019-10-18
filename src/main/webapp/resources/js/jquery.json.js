function serializeJson(formId) {
	 var $ = layui.$;//不能删除,layui占用了￥符号的重新声明
	var o = {};
	var a = $('#' + formId).serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return JSON.stringify(o);
}