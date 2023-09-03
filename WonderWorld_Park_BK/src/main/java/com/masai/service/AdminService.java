package com.masai.service;




import java.util.List;
import java.util.Optional;

import com.masai.Exception.AdminException;
import com.masai.model.Admin;

public interface AdminService {

	Admin insertAdmin(Admin admin)throws AdminException;
	
	Admin deleteAdmin(Integer adminId)throws AdminException;
	
	Optional<Admin> findByEmail(String Email)throws AdminException;

	Admin findByAdminId(Integer adminId)throws AdminException;
	
	List<Admin> viewAllAdmin(Integer pageNumber,Integer pageSize)throws AdminException;
	
	
}
