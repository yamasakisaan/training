/**
 * 経費管理のためのjs
 */
'use strict';
var rootUrl = "/java_s04/api/v1.1/expenses";
findAll();

$('#saveExpense').click(function(){
	if($('#id').val() === ''){
		console.log('add new expense');
		addExpense();
	}else{
		console.log('activate method updateExpense');
		updateExpense($('#id').val());
	}
});

function addExpense(){
	console.log('addExpense started');

	var fd = new FormData($('#expenseForm').val());
	$.ajax({
		url:rootUrl,
		type : "POST",
		data : fd,
		contentType : false,
		processData : false,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			alert('経費データの追加に成功しました');
			findAll();
			renderDetails(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('経費データの追加に失敗しました');
		}
	})
}



function findAll(){
	console.log('findAll-expense start.')
	$.ajax({
		type:'GET',
		url:rootUrl,
		dataType:'json',
		success: renderTable
	});
}

function findById(id){
	console.log('findById-expense start. id:'+ id);
	$.ajax({
		type:'GET',
		url:rootUrl + '/' + id,
		dataType:'json',
		success: function(data) {
			console.log('findById success: ' + data.title);
			renderDetails(data)
		}
	});
}

function renderTable(data){
	var headerRow = '<tr><th>ID</th><th>申請日</th><th>更新日</th><th>申請者</th><th>タイトル</th><th>金額</th><th>ステータス</th></tr>';

	$('#expenses').children().remove();
	if(data.length === 0){
		$('#expenses').append('<p>現在データが存在していません。</p>')
	}else{
		var table = $('<table>').attr('border',1);
		table.append(headerRow);

		$.each(data, function(index, expense){
			var row = $('<tr>');
			row.append( $('<td>').text(expense.id));
			row.append( $('<td>').text(expense.applicationDate));
			row.append( $('<td>').text(expense.updateDate));
			row.append( $('<td>').text(expense.empId));
			row.append( $('<td>').text(expense.title));
			row.append( $('<td>').text(expense.amount));
			row.append( $('<td>').text(expense.status));
			row.append(
					$('<td>').append(
							$('<button>').text("編集").attr("type","button").attr("onclick","findById('"+expense.id+"')")));
			row.append(
					$('<td>').append(
							$('<button>').text("削除").attr("type","button").attr("onclick","deleteById('"+expense.id+"')")));

			table.append(row);
		});

		$('#expenses').append(table);
	}
}

//編集ボタン押す→findById→登録されている内容が表示される（そして変更可能になる）
function renderDetails(expense){
	$('.error').text('');
	$('#id').val(expense.id);
	$('#applicationDate').val(expense.applicationDate);
	$('#updateDate').val(expense.updateDate);
	$('#empId').val(expense.empId);
	$('[value="'+expense.title+'"]').prop('selected',true);
	$('#amount').val(expense.amount);
	$('#status').val(expense.status);
	$('#modifiedBy').val(expense.modifiedBy);
	$('#reasonOfReject').val(expense.reasonOfReject);
	$('#payee').val(expense.payee);

	console.log('findById success: ' + expense.id);
}

function updateExpense(id){
	console.log('update expense start id:'+id);
	//var fd = new FormData($('#expenseForm').get(0));
	var fd = new FormData(document.getElementById("expenseForm"));
	$.ajax({
		url:rootUrl + '/' + id,
		type:'PUT',
		dataType:'json',
		data:fd,
		contentType:false,
		processData:false,
		success: function(data, textStatus, jqXHR) {
			alert('経費データの更新に成功しました');
			findAll();
			renderDetails(data);

	},error : function(jqXHR, textStatus, errorThrown) {
		alert('経費データの更新に失敗しました');
	}
			})
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
