package net.archigny.net.utils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MacAddress {

    /**
     * Pattern matching with any separator.
     */
    public static final Pattern MAC        = Pattern
                                                   .compile(
                                                           "([0-9A-F]{2}).([0-9A-F]{2}).([0-9A-F]{2}).([0-9A-F]{2}).([0-9A-F]{2}).([0-9A-F]{2})",
                                                           Pattern.CASE_INSENSITIVE);

    public static final int     MAC_LENGTH = 17;

    /**
     * Components of Mac Address stored as uppercased strings
     */
    private String[]            macComponents;

    /**
     * Separator used to retrieve Mac Address
     */
    private char                separator  = ':';

    /**
     * Creates a new Mac Address from a string
     * 
     * @param address Mac Address as (XX?XX?XX?XX?XX?XX), separators can be anything as there's one.
     */
    public MacAddress(final String address) {

        if (address.length() != MAC_LENGTH) {
            throw new IllegalArgumentException("A MAC address is expected to be " + MAC_LENGTH + " chars long.");
        }

        Matcher m = MAC.matcher(address);

        if (m.matches()) {
            if (m.groupCount() != 6) {
                throw new IllegalArgumentException("A MAC Address is composed of 6 groups of 2 hex digits");
            }
            macComponents = new String[6];
            for (int i = 1; i <= 6; i++) {
                macComponents[i - 1] = m.group(i).toUpperCase(Locale.ENGLISH);
            }
        } else {
            throw new IllegalArgumentException("Illegal MAC Address found");
        }
    }

    private String macToString(final String mySeparator) {

        StringBuilder sb = new StringBuilder(MAC_LENGTH);
        for (int i = 0; i < 6; i++) {
            sb.append(macComponents[i]);
            if (i < 5 && mySeparator != null) {
                sb.append(mySeparator);
            }
        }
        return sb.toString();
    }

    /**
     * Returns MAC Address as used as attributes in radius request. See RFC 3580 # 3.21 about Calling-Station-Id format
     * 
     * @return Mac Address with "-" separator
     */
    public String toStationId() {

        return macToString("-");
    }
    
    /**
     * Returns MAC Address as used as username in radius request : digits with no separator.
     *  
     * @return Mac Address with null separator
     */
    public String toUserName() {
        return macToString(null);
    }

    @Override
    public String toString() {

        if (separator == 0x00) {
            return macToString(null);
        } else {
            return macToString(String.valueOf(separator));
        }
    }

    // Setters et getters

    public char getSeparator() {

        return separator;
    }

    public void setSeparator(final char separator) {

        this.separator = separator;
    }

}
