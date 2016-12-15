package com.mmmp.NetAdvert;

import com.mmmp.NetAdvert.service.UserServiceTest;
import com.mmmp.NetAdvert.web.controller.UserControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by milosandric on 12/12/2016.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserServiceTest.class,
        UserControllerTest.class
})
public class UsersSuite {

}
