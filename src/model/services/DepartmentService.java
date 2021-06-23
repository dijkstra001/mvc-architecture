package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Department;

public class DepartmentService {
	
	public List<Department> findAll(){
		List<Department> list = new ArrayList<>();
		list.add(new Department(1, "Livros"));
		list.add(new Department(2, "Jogos"));
		list.add(new Department(3, "Eletrônicos"));
		list.add(new Department(4, "Ferramentas"));
		
		return list;
	}
}
