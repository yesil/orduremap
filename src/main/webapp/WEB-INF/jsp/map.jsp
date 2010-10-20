<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>Google Maps JavaScript API v3 Example: Fusion Tables
Layer</title>
<link
	href="http://code.google.com/apis/maps/documentation/javascript/examples/default.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript">
	function initialize() {

		var mapHomePosition = new google.maps.LatLng(46.5, 6.8);

		map = new google.maps.Map(document.getElementById('map_canvas'), {
			center : mapHomePosition,
			zoom : 9,
			mapTypeId : 'roadmap'
		});

		layer = new google.maps.FusionTablesLayer(281211);
		layer.setMap(map);
	}
</script>
</head>
<body onload="initialize()">
<div id="map_canvas"></div>
</body>
</html>
