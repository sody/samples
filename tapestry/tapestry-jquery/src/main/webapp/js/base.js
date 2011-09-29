T5.extendInitializer({
	"jquery.ui": function() {
		(function($) {
			$(".ui-button").each(function() {
				var element = $(this);
				var primary = element.find(".ui-button-icon-primary")
						.removeClass("ui-button-icon-primary ui-button-icon-secondary ui-icon")
						.attr("class");
				var secondary = element.find(".ui-button-icon-secondary")
						.removeClass("ui-button-icon-primary ui-button-icon-secondary ui-icon")
						.attr("class");
				var label = element.find(".ui-button-text").html();
				var text = label && label != "_PLACEHOLDER_";
				if (element.is("label")) {
					element = $("#" + element.attr("for"));
				}
				element.button({
					text: text,
					label: label,
					icons: {
						primary: primary,
						secondary: secondary
					}
				});
			});

			$(".button").button();
		})(jQuery);
	}
});
