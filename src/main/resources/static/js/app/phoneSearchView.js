var app = app || {};

$(function() {

	app.PhoneSearchView = Backbone.View.extend({
		getTemplate: function (name) {
            if (typeof Handlebars.templates == 'undefined') {
                Handlebars.templates = {};
            }

            if (typeof Handlebars.templates[name] == 'undefined') {
                $.ajax({
                   url : "/templates/" + name + ".hbs",
                   success : function(data) {
                   		// console.log(data);
                       Handlebars.templates[name] = Handlebars.compile(data);
                   },
                  async : false
                });
            }

            return Handlebars.templates[name];
		},
		
		initialize: function() {
			console.log("initializing");
			//this.template = _.template($('#hello-template').html());
			this.listenTo(this.model, 'change', this.render);
			
			
			console.log("In searchResultView: " + $( '#merch-property-results' ).data('location'));

			this.model = new app.SearchResult();

			this.template = this.getTemplate('search-property-record');

			// console.dir(this.model.toJSON());
			// console.log(this.template);

			this.render();
		},

		render: function(){
			console.log("rendering");
			this.$el.html(this.template(this.model.attributes));
		},

});
	

/*
app.SearchResultsView = Backbone.View.extend ({
	el: $( '#merch-property-results' ),

	events: {
		'click .view-rate-button': 'viewMyRates',
		'dblclick .view-rate-button': 'closeMyRates'
	},

	getRateListDiv: function(clickEvent) {
		var parentContainer = $(clickEvent.currentTarget).parents('.merch-property-records').prop('id');
		// console.log(parentContainer);

		var rateListDiv = $( '#'+parentContainer+' .js-rate-list' );

		return rateListDiv;
	},

	closeMyRates: function (clickEvent) {
		var rateListDiv = this.getRateListDiv(clickEvent);

		rateListDiv.html('');	
	},

	viewMyRates: function (clickEvent) {
		console.log('The View Rates button was clicked');
		// console.dir(clickEvent);

		var rateListDiv = this.getRateListDiv(clickEvent);

		var rateListView = new app.RateListView();

		rateListDiv.html(rateListView.el);
	},

	getTemplate: function (name) {
            if (typeof Handlebars.templates == 'undefined') {
                Handlebars.templates = {};
            }

            if (typeof Handlebars.templates[name] == 'undefined') {
                $.ajax({
                   url : "/templates/" + name + ".hbs",
                   success : function(data) {
                   		// console.log(data);
                       Handlebars.templates[name] = Handlebars.compile(data);
                   },
                  async : false
                });
            }

            return Handlebars.templates[name];
    },

	initialize: function() {
		console.log("In searchResultView: " + $( '#merch-property-results' ).data('location'));

		this.model = new app.SearchResult();

		this.template = this.getTemplate('search-property-record');

		// console.dir(this.model.toJSON());
		// console.log(this.template);

		this.render();
	},


	// render properties by rendering each property in the list of properties
	render: function() {
		
		// $( '#merch-property-results' ).html( this.template ( this.model.toJSON() ) );
		this.$el.html( this.template ( this.model.toJSON() ) );

		console.log("After rendering Search Results. ");

		return this;
	}

});


*/

