$(document).ready(function() {
	$(".datepicker").change(calculatePrice);
});

function calculatePrice() {
	$startDatepicker = $("#startDatepicker");
	$finishDatepicker = $("#finishDatepicker");
	startDate = new Date($startDatepicker.val());
	finishDate = new Date($finishDatepicker.val());
	if (startDate != NaN
			&& finishDate != NaN
			&& (startDate.getUTCFullYear() * 12 + startDate.getUTCMonth()) <= (finishDate
					.getUTCFullYear() * 12 + finishDate.getUTCMonth())) {
		var monthQuantity = finishDate.getUTCFullYear() * 12
				+ finishDate.getUTCMonth()
				- (startDate.getUTCFullYear() * 12 + startDate.getUTCMonth())
				+ 1;
		var monthPrice = $("#priceForMonth").html();
		$("#monthQuantity").html(monthQuantity);
		$("#totalPrice").html((monthQuantity * monthPrice).toFixed(1));
		$("#priceCalculcation").show("fast");
		$("#subscriptionSubmit").removeAttr("disabled");
		$("#priceField").val((monthQuantity * monthPrice).toFixed(1));
	} else {
		$("#priceCalculcation").css("display", "none");
		$("#subscriptionSubmit").attr("disabled", true);
		$("#priceField").val('');
	}
}