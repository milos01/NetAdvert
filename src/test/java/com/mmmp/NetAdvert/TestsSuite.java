package com.mmmp.NetAdvert;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.mmmp.NetAdvert.web.controller.AdvertControllerTest;
import com.mmmp.NetAdvert.web.controller.AdvertRatingControllerTest;
import com.mmmp.NetAdvert.web.controller.CommentControllerTest;
import com.mmmp.NetAdvert.web.controller.CompanyControllerTest;
import com.mmmp.NetAdvert.web.controller.HomeControllerTest;
import com.mmmp.NetAdvert.web.controller.ImageControllerTest;
import com.mmmp.NetAdvert.web.controller.LocationControllerTest;
import com.mmmp.NetAdvert.web.controller.ReportControllerTest;
import com.mmmp.NetAdvert.web.controller.SearchControllerTest;
import com.mmmp.NetAdvert.web.controller.UserControllerTest;
import com.mmmp.NetAdvert.web.controller.UserRatingControllerTest;

/**
 * Created by milosandric on 12/12/2016.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserControllerTest.class,
        AdvertControllerTest.class,
        CommentControllerTest.class,
        HomeControllerTest.class,
        ImageControllerTest.class,
        LocationControllerTest.class,
        ReportControllerTest.class,
        AdvertRatingControllerTest.class,
        UserRatingControllerTest.class,
        SearchControllerTest.class,
        CompanyControllerTest.class
})
public class TestsSuite {

}
