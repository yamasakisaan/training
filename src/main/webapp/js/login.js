/**
 *
 */

var rootUrl = "/java_s04/api/v1.1/users";

//$('#js-login-button').click(findByUserid($('#js-login-name').val(),$('#js-login-pass').val()));
//
//function findByUserid(userid,password){
//	console.log('findByUserid - Userid:'+userid)
//	$.ajax({
//		type:'POST',
//		url:rootUrl + '/' + userid + '/'+ password,
//		dataType:'json',
//		success:function(data){
//			console.log('findByuserid success');
//		}
//	})
//
//}

$(function(){

	$('#js-login-button').click(function(){

		var userid=$('#js-login-name').val();
		var password = $('#js-login-pass').val();
		console.log('findByUserid - Userid:'+userid)
		$.ajax({
			type:'POST',
			url:rootUrl + '/' + userid + '/'+ password,
			dataType:'json',
			success:function(data){
				console.log('findByuserid success');
				alert('ログイン成功しました');
				window.location.href = '/java_s04/MainMenu.html';
			}
		})

	})


})








/*
 *
function login(user){
	if(user.password)
}*/