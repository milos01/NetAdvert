package com.mmmp.netadvert.service;

import com.mmmp.netadvert.controller.AdvertRepository;
import com.mmmp.netadvert.model.Advert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by milosandric on 02/01/2017.
 */
@Service
public class AdvertService {

    @Autowired
    AdvertRepository advertRepository;

    public Page<Advert> findAll(Pageable page) {
        return advertRepository.findAll(page);
    }
}
