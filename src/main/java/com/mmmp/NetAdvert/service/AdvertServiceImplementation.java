package com.mmmp.NetAdvert.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmmp.NetAdvert.DAO.TestDAO;
import com.mmmp.NetAdvert.model.Customer;

@Service
public class AdvertServiceImplementation implements AdverService {

	
	@Autowired
	private TestDAO td;
	
	@Override
	@Transactional
	public void insert(Customer customer) {
		this.td.insert(customer);
		
	}


}
