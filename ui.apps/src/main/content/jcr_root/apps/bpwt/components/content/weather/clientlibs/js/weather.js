$(function() {
	$("#fetch-weather-info").click(function(e) {
		var location = $(".location-name").val();
        $.ajax({        	
        	// Trigger call to Weather Tracker Servlet
        	type : "GET",
            url : '/bin/weatherinfo?loc='+location,
            success : function(data, textStatus, jqXHR) {            	
            	var weatherInfo = "<br/><br/>";
            	var tempByDate 	= "";

            	// Display Weather Information
				var weather = data.weather;

            	$.each(weather, function(arrayIndex, eachForcastEntry) {
            		tempByDate 	= tempByDate + " Date: " + eachForcastEntry.bp_wt_date+ "  --  "
    							+ "Temperature:  " + eachForcastEntry.bp_wt_temp_c 
    							+ " °C,  " + eachForcastEntry.bp_wt_temp_f + " °F <br/>";
            	});
            	
            	if (tempByDate == "") {
            		$(".weather-info").html("");
            		$(".status-div").html("Please provide proper location to view Weather information.");
            	} else {
            		$(".status-div").html("");
            		weatherInfo = weatherInfo + tempByDate;
            		$(".weather-info").html(weatherInfo);
            	}
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                $(".status-div").html("Invalid Location.");
                return;
            }
        });
	});
});