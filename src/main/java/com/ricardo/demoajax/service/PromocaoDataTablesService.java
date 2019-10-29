package com.ricardo.demoajax.service;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.ricardo.demoajax.repository.PromocaoRepository;

public class PromocaoDataTablesService {

	private String[] cols = {
			"id", "titulo", "site", "linkPromocao", "descricao", "linkImagem",
			"preco", "likes", "dtCadastro", "categoria"
	};
	
	public Map<String, Object> execute(PromocaoRepository repository, HttpServletRequest request){
	
		
		int start = Integer.parseInt(request.getParameter("start"));
		int lenght = Integer.parseInt(request.getParameter("lenght"));
		int draw = Integer.parseInt(request.getParameter("draw"));
		
		int current = currentPage(start, lenght);
		
		String column = columnName(request);
		Sort.Direction direction = orderBy(request);
		
		Pageable pageable = PageRequest.of(current, lenght, direction, column); 
		
		Map<String, Object> json = new LinkedHashMap<>();
		json.put("draw", null);
		json.put("recordsTotal", 0);
		json.put("recordsFiltered", 0);
		json.put("data", null);
		
		return json;
	}

	private Direction orderBy(HttpServletRequest request) {
		String order = request.getParameter("order[0][dir]");
		Sort.Direction sort = Sort.Direction.ASC;
		if(order.equalsIgnoreCase("desc")) {
			sort = Sort.Direction.DESC;
		}
		return sort;
	}

	private String columnName(HttpServletRequest request) {
		
		int iCol = Integer.parseInt(request.getParameter("order[0][column]"));
		return cols[iCol];
		
	}

	private int currentPage(int start, int lenght) {
	// 0       1          2
	//0-9  | 10-19  | 20-29	
	
		
		return start/lenght;
	}
}
