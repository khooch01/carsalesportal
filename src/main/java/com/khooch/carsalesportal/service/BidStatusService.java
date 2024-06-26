package com.khooch.carsalesportal.service;

import com.khooch.carsalesportal.entity.BidStatus;

public interface BidStatusService {

    BidStatus findOrCreateByName(String name);

}
