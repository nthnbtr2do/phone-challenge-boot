define(function(require) {
	var Backbone = require('Backbone');

	return Backbone.Model.extend({
		urlRoot: 'http://localhost:8080/translatePhoneNum',
		url: function() {
			return this.urlRoot + '?name=' + this.id;
		}
	});
});
