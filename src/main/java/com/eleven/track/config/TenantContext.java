package com.eleven.track.config;

public class TenantContext {
    private static final ThreadLocal<Long> TENANT_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<String> USERNAME_LOCAL = new ThreadLocal<>();

    public static void set(Long userId) {
        TENANT_LOCAL.set(userId);
    }

    public static Long get() {
        return TENANT_LOCAL.get();
    }

    public static void set(String username) {
        USERNAME_LOCAL.set(username);
    }

    public static String getUserName() {
        return USERNAME_LOCAL.get();
    }

    public static void clear() {
        TENANT_LOCAL.remove();
    }
}

