package net.archigny.net.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class MacAddressTest {

    public static final String MAC1              = "00:11:22:33:44:55";

    public static final String MAC2              = "00:16:3e:6d:46:b8";

    public static final String EXPECTED_MAC2     = "00:16:3E:6D:46:B8";

    public static final String EXPECTED_STATION2 = "00-16-3E-6D-46-B8";

    public static final String EXPECTED_USER2    = "00163E6D46B8";

    public static final String MIXED_MAC         = "00-11:22f33z44x55";

    @Test
    public void mainTest() {

        MacAddress mac;

        try {
            mac = new MacAddress("123456");
            fail("Too short mac should have triggered an exception");
        } catch (IllegalArgumentException e) {
        }

        try {
            mac = new MacAddress("12-34-56-78-GF-AB");
            fail("Illegal Mac should have triggered an exception");
        } catch (IllegalArgumentException e) {
        }

        mac = new MacAddress(MAC1);
        assertEquals(MAC1, mac.toString());

        // Mac are stored as uppercased
        MacAddress mac2 = new MacAddress(MAC2);
        mac2.setSeparator(':');

        assertEquals(EXPECTED_MAC2, mac2.toString());

        assertEquals(EXPECTED_STATION2, mac2.toStationId());

        assertEquals(EXPECTED_USER2, mac2.toUserName());

        MacAddress mixed = new MacAddress(MIXED_MAC);

        assertEquals(MAC1, mixed.toString());

    }

}
