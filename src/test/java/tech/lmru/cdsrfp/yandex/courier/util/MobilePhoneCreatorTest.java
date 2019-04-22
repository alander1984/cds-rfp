package tech.lmru.cdsrfp.yandex.courier.util;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.lmru.yandex.courier.util.MobilePhoneCreator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Ilya on 22.04.2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MobilePhoneCreatorTest {

    @Autowired
    MobilePhoneCreator mobilePhoneCreator;

    @Test
    @Ignore
    public void testCreateMobilePhone(){
        assertEquals("+79275667788",  mobilePhoneCreator.createMobilePhone("89275667788"));
        assertEquals("+79275667788",  mobilePhoneCreator.createMobilePhone("79275667788"));
        assertEquals("+79275667788",  mobilePhoneCreator.createMobilePhone("9275667788"));
        assertEquals("+79275667788",  mobilePhoneCreator.createMobilePhone("+79275667788"));
        assertNull(mobilePhoneCreator.createMobilePhone("275667788"));
    }
}
