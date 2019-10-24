
//Efeito Infinite Scroll
$(window).scroll(function(){
	
	var scrollTop = $(this).scrollTop();
	var conteudo = $(document).height() - $(window).height();
	
	
	console.log('scrollTop: ', scrollTop, ' | ', ' conteudo: ', conteudo);
	
	if (scrollTop >= conteudo){
		console.log("***");
	}
	
});
