package com.mmmp.netadvert.controller;

import com.mmmp.netadvert.model.Advert;
import com.mmmp.netadvert.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by milosandric on 02/01/2017.
 */
public interface AdvertRepository extends JpaRepository<Advert, Long> {

}
