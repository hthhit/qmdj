package com.qm.common.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class BaseUtils {

	public static final boolean listEmpty(List<?> l) {
		return l == null || l.isEmpty();
	}
	public static final boolean listNotEmpty(List<?> l) {
		return l != null && !l.isEmpty();
	}

	public static final boolean mapEmpty(Map<?, ?> m) {
		return m == null || m.isEmpty();
	}
	public static final boolean mapNotEmpty(Map<?, ?> m) {
		return m != null && !m.isEmpty();
	}

	public static final boolean setEmpty(Set<?> s) {
		return s == null || s.isEmpty();
	}
	public static final boolean setNotEmpty(Set<?> s) {
		return s != null && !s.isEmpty();
	}

	public static final boolean arrEmpty(Object[] arr) {
		return arr == null || arr.length < 1;
	}
	public static final boolean arrNotEmpty(Object[] arr) {
		return arr != null && arr.length > 0;
	}
	
	public static final boolean checkBoolean(Boolean b) {
		return b != null && b.booleanValue();
	}
	
	public static final boolean checkLong(Long l, long v) {
		return l != null && l.longValue() == v;
	}
	
	public static final boolean checkInteger(Integer i, int v) {
	    return i != null && i.intValue() == v;
	}
	
	public static final int value(Integer i) {
	    return i == null ? 0 : i.intValue();
	}

    public static final int value(Integer i, int def) {
        return i == null ? def : i.intValue();
    }

    public static final long value(Long l) {
        return l == null ? 0L : l.longValue();
    }
    
    public static final double value(Double d, double def) {
        return d == null ? def : d.doubleValue();
    }
    
    public static final boolean isAnyNull(Object ...objects) {
        if (arrEmpty(objects)) {
            return true;
        }
        for (Object obj : objects) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }
}
