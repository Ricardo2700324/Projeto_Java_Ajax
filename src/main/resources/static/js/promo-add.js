//submit do formulário para o controller
$("#form-add-promo").submit(function(evt){
	//bloquear o comportamento padrão do submit
	evt.preventDefault();
	
	
	var promo = {};
	promo.linkPromocao = $("#linkPromocao").val();
	promo.descricao = $("#descricao").val();
	promo.preco = $("#preco").val();
	promo.titulo = $("#titulo").val();
	promo.categoria = $("#categoria").val();
	promo.linkImagem = $("#linkImagem").attr("src");
	promo.site = $("#site").text();
	
	
	console.log('promo >', promo);
	
	
	$.ajax({
		method: "POST",
		url: "/promocao/save",
		data: promo, 
		success: function(){
			$("alert").addClass("alert alert-success").text("OK! Promoção cadastrada com sucesso!")
		},
		error:function(xhr){
			console.log("> error", xhr.responseText);
			$("#alert").addClass("alert alert-danger").text("Não foi possivel salvar esta promoção!")
		}
	})
});



// função para capturar as meta tags
$('#linkPromocao').on('change', function(){
	
	var url = $(this).val();
	
	if(url.length > 7){
		
		$.ajax({
			method:"POST", 
			url:"/meta/info?url=" + url,
			cache: false,
			beforeSend: function(){
				$("#alert").removeClass("alert alert-danger").text('');
				$("#titulo").val("");
				$("#site").text("");
				$("#linkImagem").attr("src", "");
				$("#loader-img").addClass("loader");
			},
			success: function( data ){
				console.log(data);
				$("#titulo").val(data.title);
				$("#site").text(data.site.replace("@", ""));
				$("#linkImagem").attr("src", data.image);
			},
			statusCode: {
				404: function(){
					$("#alert").addClass("alert alert-danger").text("Nenhuma informação pode ser recuperada dessa URL!");
					$("#linkImagem").attr("src", "/images/promo-dark.png");
				}
			},
			error: function(){
				$("#alert").addClass("alert alert-danger").text("Url informada não existe!");
				$("#linkImagem").attr("src", "/images/promo-dark.png");
			},
			complete: function(){
				$("#loader-img").removeClass("loader");
			}
		})
	}
	
})