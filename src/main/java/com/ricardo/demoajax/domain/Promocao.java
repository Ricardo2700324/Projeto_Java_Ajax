package com.ricardo.demoajax.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@SuppressWarnings("serial")
@Entity
@Table(name = "promocoes")
public class Promocao implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O campo título não foi preenchido!")
	@Column(name = "titulo", nullable = false)
	private String titulo;

	@NotBlank(message = "O link da promoção precisa ser preenchido!")
	@Column(name = "link_promocao", nullable = false)
	private String linkPromocao;

	
	@Column(name = "site_promocao", nullable = false)
	private String site;

	@Column(name = "descricao")
	private String descricao;

	
	@Column(name = "link_imagem", nullable = false)
	private String linkImagem;

	@NotNull(message = "Uma categoria é requerida!")
	@ManyToOne
	@JoinColumn(name = "categoria_fk")
	private Categoria categoria;

	@NotNull(message = "O preço precisa ser informado!")
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	@Column(name = "preco_promocao", nullable = false)
	private BigDecimal preco;

	@Column(name = "total_likes")
	private int likes;

	@Column(name = "data_cadastro", nullable = false)
	private LocalDateTime dtCadastro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getLinkPromocao() {
		return linkPromocao;
	}

	public void setLinkPromocao(String linkPromocao) {
		this.linkPromocao = linkPromocao;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLinkImagem() {
		return linkImagem;
	}

	public void setLinkImagem(String linkImagem) {
		this.linkImagem = linkImagem;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public LocalDateTime getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(LocalDateTime dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Promocao [id=");
		builder.append(id);
		builder.append(", titulo=");
		builder.append(titulo);
		builder.append(", linkPromocao=");
		builder.append(linkPromocao);
		builder.append(", site=");
		builder.append(site);
		builder.append(", descricao=");
		builder.append(descricao);
		builder.append(", linkImagem=");
		builder.append(linkImagem);
		builder.append(", categoria=");
		builder.append(categoria);
		builder.append(", preco=");
		builder.append(preco);
		builder.append(", likes=");
		builder.append(likes);
		builder.append(", dtCadastro=");
		builder.append(dtCadastro);
		builder.append("]");
		return builder.toString();
	}

}
