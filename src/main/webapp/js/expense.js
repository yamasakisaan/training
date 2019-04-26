/**
 * 経費管理のためのjs
 */
'use strict';
var rootUrl = "/java_s04/api/v1.1/expenses";
findAll();

function findAll(){
	console.log('findAll-expense start.')
	$.ajax({
		type:'GET',
		url:rootUrl,
		dataType:'json',
		success: renderTable
	});
}

function renderTable(data){
	var headerRow = '<tr><th>社員ID</th><th>立替日</th><th>金額</th><th>カテゴリ</th><th>備考</th></tr>';

	$('#expenses').children().remove();
	if(data.length === 0){
		$('#expenses').append('<p>現在データが存在していません。</p>')
	}else{
		var table = $('<table>').attr('border',1);
		table.append(headerRow);

		$.each(data, function(index, expense){
			var row = $('<tr>');
			row.append( $('<td>').text(expense.empId));
			row.append( $('<td>').text(expense.advanceYmd));
			row.append( $('<td>').text(expense.amount));
			row.append( $('<td>').text(expense.category));
			row.append( $('<td>').text(expense.dscrpt));
			//row.append( $('<td>').append(expense.advanceYmd));

			table.append(row);
		});
		$('#expenses').append(table);
	}
}


/*
 *
function formToJSON(){
	var empId = $('#empIdParam').val();
	return JSON.stringfy({
		"empid" :  $('#empIdParam').val();,
		""
	})
} */
