var chart;

$(document).ready(
		function() {
			chart = new Highcharts.Chart({
				chart : {
					renderTo : 'plot',
					type : getType(),
					marginRight : 130,
					marginBottom : 50
				},
				title : {
					text : urlDecode(getParameterByName('name')),
					x : -20
				// center
				},
				subtitle : {
					text : urlDecode(getParameterByName('description')),
					x : -20
				},
				xAxis : {
					title : {
						text : urlDecode(getParameterByName('signature_x'))
					},
					min : parseInt(urlDecode(getParameterByName('min_x'))),
					max : parseInt(urlDecode(getParameterByName('max_x')))
				},
				yAxis : {
					title : {
						text : urlDecode(getParameterByName('signature_y'))
					},
					min : parseInt(urlDecode(getParameterByName('min_y'))),
					max : parseInt(urlDecode(getParameterByName('max_y'))),
					plotLines : [ {
						value : 0,
						width : 1,
						color : '#808080'
					} ]
				},
				tooltip : {
					formatter : function() {
						return '<b>' + this.series.name + '</b><br/>'
								+ urlDecode(getParameterByName('signature_x'))+": "
								+ this.x + '<br/>'
								+ urlDecode(getParameterByName('signature_y'))+": "
								+ this.y;
					},
					enabled: getParameterByName("signature_points") == "false"? false : true
				},
				legend : {
					layout : 'vertical',
					align : 'right',
					verticalAlign : 'top',
					x : -10,
					y : 150,
					borderWidth : 0
				},
				series : getSeries()
			});
		});

function getType(){
	return urlDecode(getParameterByName("smoothing")) == "true" ? "spline" : "line";
}

function getSeries() {
	series = [];
	for ( var i = 1; i <= parseInt(urlDecode(getParameterByName("sequence_number"))); i++) {
		var serie = {};
		serie["name"] = urlDecode(getParameterByName("sequence_name_" + i));
		var values = [];
		var xs = urlDecode(getParameterByName("sequence_x_" + i)).split(/\s+/);
		var ys = urlDecode(getParameterByName("sequence_y_" + i)).split(/\s+/);
		$.each(xs, function(i, val) {
			values.push([ parseInt(xs[i]), parseInt(ys[i]) ]);
		});
		serie["data"] = values;
		series.push(serie);
	}
	return series;
}

function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
	var regexS = "[\\?&]" + name + "=([^&#]*)";
	var regex = new RegExp(regexS);
	var results = regex.exec(window.location.search);
	if (results == null)
		return "";
	else
		return decodeURIComponent(results[1].replace(/\+/g, " "));
}

function urlDecode(str) {
	return decodeURIComponent((str + '').replace(/\+/g, '%20'));
}
