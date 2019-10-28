var pageNumber = 0;

//Rotina que trabalha toda vez que a pagina for aberta pela 1º vez
$(document).ready(function(){
	$("#loader-img").hide();
	$("#fim-btn").hide();
});


//Efeito Infinite Scroll
$(window).scroll(function(){
	
	var scrollTop = $(this).scrollTop();
	var conteudo = $(document).height() - $(window).height();
	
	
	//console.log('scrollTop: ', scrollTop, ' | ', ' conteudo: ', conteudo);
	
	if (scrollTop >= conteudo){
		pageNumber++;
		setTimeout(function(){
			loadByScrollBar(pageNumber);
		}, 200);
	}
	
});


function loadByScrollBar(pageNumber){
	
	$.ajax({
		method: "GET",
		url: "/promocao/list/ajax",
		data: {
			page: pageNumber
		},
		beforSend: function(){
			$("#loader-img").show();
		},
		success: function( response ){
			//console.log("resposta: ", response);
			console.log("lista: ", response.length);
			
			if(response.length > 150){
			
				$(".row").fadeIn(250, function(){
					$(this).append(response);
				});
				
			}else{
				$("#fim-btn").show();
				$("#loader-img").removeClass("loader");
			}
		},
		error: function(xhr){
			alert("Ocorreu um erro! " + xhr.status + " - " + xhr.statusText);
		},
		complete: function(){
			$("#loader-img").hide();
		}
	})
}

//autocomplete
$("#autocomplete-input").autocomplete({
	source: function(request, response ){
		$.ajax({
			method: "GET",
			url: "/promocao/site",
			data: {
				termo: request.term
			},
			success: function(result){
				response(result);
			}
		});
	}
});



// adicionar likes
$(document).on("click", "button[id*='likes-btn-']", function(){
	var id = $(this).attr("id").split("-")[2];
	console.log("id: ", id);
	
	$.ajax({
		method: "POST",
		url: "/promocao/like/" + id,
		success: function(response){
			$("#likes-count-" + id).text(response);
		},
		error: function(xhr){
			alert("Ocorreu um erro no likes: " + xhr.status + ", " + xhr.statusText);
		}
	});
	
});
