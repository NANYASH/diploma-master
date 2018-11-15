(function($){
	
	$.fn.slideBox = function(params){

		$(this).css("display", "");
		var content = $(this).html();
		var defaults = {
			width: "100%",
			height: "200px",
			position: "top"			// Possible values : "top", "bottom"
		}
		
		// extending the fuction
		if(params) $.extend(defaults, params);
		
		var divPanel = $("<div class='slide-panel'>");
		var divContent = $("<div class='content'>");

		$(divContent).html(content);
		$(divPanel).addClass(defaults.position);
		$(divPanel).css("width", defaults.width);
		
		// centering the slide panel
		$(divPanel).css("left", (100 - parseInt(defaults.width))/2 + "%");
	
		// if position is top we're adding 
		if(defaults.position == "top")
			$(divPanel).append($(divContent));
		
		// adding buttons
		
		if(defaults.position == "bottom")
			$(divPanel).append($(divContent));
		
		$(this).replaceWith($(divPanel));
		
		// Buttons action
		$(".slide-button").click(function(){
			if($(this).attr("id") == "close-button") {
				$(this).removeAttr('id');
				$(divContent).animate({height: "0px"}, 500);
			}
			else {
				$(this).attr("id","close-button");
				$(divContent).animate({height: defaults.height}, 500);
			}

		});
	};
	
})(jQuery);