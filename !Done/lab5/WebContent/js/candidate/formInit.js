var countries = [ "Любая", "Беларусь", "Россия" ];
var cities = [ [ "Любой" ], [ "Любой", "Минск", "Брест" ],
		[ "Любой", "Москва", "Санкт-Петербург" ] ];

function maleInit() {
	var male = $("#male").attr("value");
	$("input[type='radio'][name='male'][value='" + male + "']").attr("checked",
			"checked");
}

function countryInit() {
	var country = $("#country").attr("value");
	var city = $("#city").attr("value");
	var countryIndex = getCountryIndex(country);
	var cityIndex = -1;
	if (countryIndex != -1)
		cityIndex = getCityIndex(city, countryIndex);
	if (countryIndex != -1 && cityIndex != -1) {
		var $countrySelect = $("#countrySelect");
		$countrySelect.empty();
		addCountries($countrySelect);
		select($countrySelect, countryIndex);
		countrySelectChangeListener();
		select($("#citySelect"), cityIndex);
		$countrySelect.change(countrySelectChangeListener);
	}
}

function select($select, index) {
	$select.children().eq(index).attr("selected", "selected");
}

function countrySelectChangeListener() {
	var country = $("#countrySelect option:selected").attr("value");
	var countryIndex = getCountryIndex(country);
	var $citySelect = $("#citySelect");
	addCities($citySelect, countryIndex);
	select($citySelect, 0);
}

function addCountries($countrySelect) {
	for ( var i = 0; i < countries.length; i++) {
		$countrySelect.append("<option>" + countries[i] + "</option>").attr(
				"value", countries[i]);
	}
}

function addCities($citySelect, countryIndex) {
	$citySelect.empty();
	for ( var i = 0; i < cities[countryIndex].length; i++) {
		$citySelect.append("<option>" + cities[countryIndex][i] + "</option>")
				.attr("value", cities[countryIndex][i]);
	}
}

function getCountryIndex(country) {
	var j = -1;
	for ( var i = 0; i < countries.length; i++) {
		if (countries[i] == country) {
			j = i;
			break;
		}
	}
	return j;
}

function getCityIndex(city, countryIndex) {
	var j = -1;
	for ( var i = 0; i < cities[countryIndex].length; i++) {
		if (cities[countryIndex][i] == city) {
			j = i;
			break;
		}
	}
	return j;
}