package utils;

import java.util.UUID;

/**
 * 激活码实用类
 */
public class UUIDUtils {
    private static String randomUUID() {
        return String.valueOf(UUID.randomUUID());
    }

    private static String randomUUIDWithoutRod() {
        return randomUUID().replaceAll("-", "");
    }

    private static String upperCaseUUID() {
        return randomUUIDWithoutRod().toUpperCase();
    }
}
