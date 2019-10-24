var pageNumber = 0;

//Efeito Infinite Scroll
$(window).scroll(function(){
	
	var scrollTop = $(this).scrollTop();
	var conteudo = $(document).height() - $(window).height();
	
	
	console.log('scrollTop: ', scrollTop, ' | ', ' conteudo: ', conteudo);
	
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
		success: function( response ){
			console.log("resposta: ", response)
		}
	})
}