$(document).ready(function() {
	$("#remove_sequence").click(removeSequence);
	$("#add_sequence").click(addSequence);
});

function removeSequence() {
	var number = getSequnceNumber();
	if (number > 1) {
		console.log(number);
		$(".sequence:last-child").remove();
		changeSequenceNumber(-1);
	}
}

function addSequence() {
	var number = getSequnceNumber();
	if (number < 5) {
		$("#sequences").append(getSequenceTag(number + 1));
		changeSequenceNumber(1);
	}
}

function getSequenceTag(sequence_number) {
	tag = "<div id='sequence_"+sequence_number+"' class='sequence'>"
			+ "<label class='control-label' for='sequence_x_"+sequence_number+"'>Последовательность "
			+ "#"+sequence_number+" X и Y:</label>"
			+ "<div class='controls'>"
			+ "<input class='input-xlarge' type='text' name='sequence_name_"+sequence_number+"'"
			+ "value='Борисовдрев' placeholder='Название графика'"
			+ "pattern='[а-яА-Яa-zA-Z0-9 ]{1,15}' required> <input "
			+ "class='input-xlarge' type='text' name='sequence_x_"+sequence_number+"'"
			+ "value='20 30 40 50 60 70'"
			+ "placeholder='Введите последовательность X # "+sequence_number+"' pattern='[0-9 ]{1,100}'"
			+ "required> <input class='input-xlarge' type='text'"
			+ "name='sequence_y_"+sequence_number+"' value='100 200 250 200 150 100'"
			+ "placeholder='Введите последовательность Y # "+sequence_number+"' pattern='[0-9 ]{1,100}''"
			+ "required>" + "</div>" + "</div>";
	return tag;
}

function getSequnceNumber() {
	var number = parseInt($("#sequence_number").val());
	return number;
}

function changeSequenceNumber(n) {
	$sn = $("#sequence_number");
	$sn.val(parseInt($sn.val()) + n);
}