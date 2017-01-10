package com.mmmp.netadvert;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.mmmp.netadvert.web.controller.AdvertControllerTest;
import com.mmmp.netadvert.web.controller.CommentControllerTest;
import com.mmmp.netadvert.web.controller.HomeControllerTest;
import com.mmmp.netadvert.web.controller.ImageControllerTest;
import com.mmmp.netadvert.web.controller.LocationControllerTest;
import com.mmmp.netadvert.web.controller.ReportControllerTest;
import com.mmmp.netadvert.web.controller.UserControllerTest;

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
        ReportControllerTest.class
})
public class TestsSuite {

}
