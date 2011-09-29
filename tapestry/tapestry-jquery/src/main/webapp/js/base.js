T5.extendInitializer({
	"jquery.ui": function() {
		(function($) {
			// create buttons from every element of 'ui-button' class
			$(".ui-button").each(function() {
				// get element
				var element = $(this);
				// find element representing primary icon ang parse it's class attribute
				var primary = element.find(".ui-button-icon-primary")
						.removeClass("ui-button-icon-primary ui-button-icon-secondary ui-icon")
						.attr("class");
				// find element representing secondary icon ang parse it's class attribute
				var secondary = element.find(".ui-button-icon-secondary")
						.removeClass("ui-button-icon-primary ui-button-icon-secondary ui-icon")
						.attr("class");
				// find element representing button text and get it's content
				var label = element.find(".ui-button-text").html();
				// '_PLACEHOLDER_' means that button doesn't have any text
				var text = label && label != "_PLACEHOLDER_";
				// if element is label we should create button from input it references for
				if (element.is("label")) {
					element = $("#" + element.attr("for"));
				}
				// create button
				element.button({
					text: text,
					label: label,
					icons: {
						primary: primary,
						secondary: secondary
					}
				});
			});

			// for demo only - create usual buttons from every element of 'button' class
			$(".button").button();
		})(jQuery);
	}
});
