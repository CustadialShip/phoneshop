package com.es.core.model.phone;

import com.es.core.model.color.Color;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/context/testContext-core.xml")
public class JdbcPhoneDaoTest {
    @Resource
    private PhoneDao phoneDao;

    public static final long NEW_PHONE_ID = 1009L;
    public static final String COLOR_CODE_BLACK = "Black";
    private final static String TEST_NAME = "Test";
    private final long EXISTING_PHONE_ID_0 = 1000L;
    private final long EXISTING_PHONE_ID_2 = 1002L;
    private final long EXISTING_PHONE_ID_3 = 1003L;

    @Test
    public void shouldPhoneDaoExist() {
        assertNotNull(phoneDao);
    }

    @Test
    public void shouldSavePhone() {
        Phone actualPhone = new Phone();
        actualPhone.setBrand(TEST_NAME);
        actualPhone.setModel(TEST_NAME);
        actualPhone.setDescription(TEST_NAME);
        actualPhone.setDeviceType(TEST_NAME);
        actualPhone.setWeightGr(1);
        actualPhone.setPixelDensity(1);
        actualPhone.setBatteryCapacityMah(1);
        phoneDao.save(actualPhone);

        assertTrue(actualPhone.getId() > 0);

        Phone expectedPhone = phoneDao.get(actualPhone.getId()).get();
        assertEquals(expectedPhone, actualPhone);
    }

    @Test
    public void shouldGetColorWhenGetMethod() {
        if (phoneDao.get(1000L).isPresent()) {
            Phone actualPhone = phoneDao.get(EXISTING_PHONE_ID_0).get();
            Set<Color> colorSet = new HashSet<>();
            Color color = new Color();
            color.setCode("Black");
            color.setId(1000L);
            colorSet.add(color);
            assertEquals(colorSet, actualPhone.getColors());
        } else {
            fail();
        }
    }

    @Test
    public void shouldFindAll() {
        List<Phone> phoneListExpected = phoneDao.findAll(1, 2);
        List<Phone> phoneListActual = new ArrayList<>();
        if (phoneDao.get(1000L).isPresent() && phoneDao.get(1001L).isPresent()) {
            phoneListActual.add(phoneDao.get(EXISTING_PHONE_ID_2).get());
            phoneListActual.add(phoneDao.get(EXISTING_PHONE_ID_3).get());
            assertEquals(phoneListExpected, phoneListActual);
        } else {
            fail();
        }
    }

    @Test
    public void shouldGetColorSetWhenGetMethod() {
        if (phoneDao.get(1000L).isPresent()) {
            Phone actualPhone = phoneDao.get(EXISTING_PHONE_ID_0).get();
            assertNotNull(actualPhone.getColors());
        } else {
            fail();
        }
    }

    @Test
    public void shouldSaveColorWhenSaveMethod() {
        if (phoneDao.get(1000L).isPresent()) {
            Phone actualPhone = new Phone();
            actualPhone.setBrand(TEST_NAME);
            actualPhone.setModel(TEST_NAME);
            actualPhone.setDescription(TEST_NAME);
            actualPhone.setDeviceType(TEST_NAME);
            actualPhone.setWeightGr(1);
            actualPhone.setPixelDensity(1);
            actualPhone.setBatteryCapacityMah(1);
            Set<Color> colorSet = new HashSet<>();
            Color color = new Color();
            color.setCode(COLOR_CODE_BLACK);
            color.setId(EXISTING_PHONE_ID_0);
            actualPhone.setColors(colorSet);
            colorSet.add(color);
            phoneDao.save(actualPhone);
            if (phoneDao.get(NEW_PHONE_ID).isPresent()) {
                Phone expectedPhone = phoneDao.get(1009L).get();
                assertEquals(actualPhone, expectedPhone);
            } else {
                fail();
            }
        } else {
            fail();
        }
    }

    @Test
    public void shouldGetColorByColorIdWhenGetColorMethod() {
        Color actualColor = new Color();
        actualColor.setId(1000L);
        actualColor.setCode("Black");
        Optional<Color> expectedColor = phoneDao.getColor(1000L);
        if (expectedColor.isPresent()) {
            assertEquals(expectedColor.get(), actualColor);
        } else {
            fail();
        }
    }

    @Test
    public void shouldGetColorsByPhoneId() {
        Color color = new Color();
        color.setId(1000L);
        color.setCode("Black");
        Set<Color> actualColors = new HashSet<>();
        actualColors.add(color);
        Set<Color> expectedColors = phoneDao.getColorsByPhoneId(1000L);
        assertEquals(expectedColors, actualColors);
    }

    @Test
    public void shouldGetStockByPhoneIdWhenGetMethod() {
        if (phoneDao.get(EXISTING_PHONE_ID_0).isPresent()) {
            Phone phone = phoneDao.get(EXISTING_PHONE_ID_0).get();
            Stock actualStock = new Stock();
            actualStock.setStock(11);
            actualStock.setReserved(0);
            actualStock.setPhone(phone);
            Optional<Stock> optionalStock = phoneDao.getStock(EXISTING_PHONE_ID_0);
            if (optionalStock.isPresent()) {
                Stock expectedStock = optionalStock.get();
                assertEquals(expectedStock, actualStock);
            } else fail();
        } else {
            fail();
        }
    }
}
