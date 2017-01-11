package boost.util;

import java.util.*;

public class Utils {
    public static @NotNull <T> List<T> newList(List<T> p_list) {
        if(p_list==null || p_list.isEmpty())
            return Collections.emptyList();
        if(p_list.size()==1) {
            T value = p_list.get(0);
            return Collections.singletonList(value);
        }
        return new ArrayList<T>(p_list);
    }

    public static @NotNull <T,K> Set<T> newSet(Set<T> p_set) {
        if(p_set==null || p_set.isEmpty())
            return Collections.emptySet();
        if(p_set.size()==1) {
            T value = p_set.iterator().next();
            return Collections.singleton(value);
        }
        return new HashSet<T>(p_set);
    }

    public static @NotNull <T,K> Map<T,K> newMap(Map<T,K> p_map) {
        if(p_map==null || p_map.isEmpty())
            return Collections.emptyMap();
        if(p_map.size()==1) {
            Map.Entry<T, K> entry = p_map.entrySet().iterator().next();
            return Collections.singletonMap(entry.getKey(), entry.getValue());
        }
        return new HashMap<T,K>(p_map);
    }

    public static @NotNull <T> List<T> newReadOnlyList(List<T> p_list) {
        if(p_list==null || p_list.isEmpty())
            return Collections.emptyList();
        if(p_list.size()==1) {
            T value = p_list.get(0);
            return Collections.singletonList(value);
        }
        return Collections.unmodifiableList(new ArrayList<T>(p_list));
    }

    public static @NotNull <T,K> Set<T> newReadOnlySet(Set<T> p_set) {
        if(p_set==null || p_set.isEmpty())
            return Collections.emptySet();
        if(p_set.size()==1) {
            T value = p_set.iterator().next();
            return Collections.singleton(value);
        }
        return Collections.unmodifiableSet(new HashSet<T>(p_set));
    }

    public static @NotNull <T,K> Map<T,K> newReadOnlyMap(Map<T,K> p_map) {
        if(p_map==null || p_map.isEmpty())
            return Collections.emptyMap();
        if(p_map.size()==1) {
            Map.Entry<T, K> entry = p_map.entrySet().iterator().next();
            return Collections.singletonMap(entry.getKey(), entry.getValue());
        }
        return Collections.unmodifiableMap(new HashMap<T, K>(p_map));
    }

    public static @NotNull <T> List<T> getList(List<T> p_list) {
        if(p_list==null || p_list.isEmpty())
            return Collections.emptyList();
        if(p_list.size()==1) {
            T value = p_list.get(0);
            return Collections.singletonList(value);
        }
        return p_list;
    }

    public static @NotNull <T,K> Set<T> getSet(Set<T> p_set) {
        if(p_set==null || p_set.isEmpty())
            return Collections.emptySet();
        if(p_set.size()==1) {
            T value = p_set.iterator().next();
            return Collections.singleton(value);
        }
        return p_set;
    }

    public static @NotNull <T,K> Map<T,K> getMap(Map<T,K> p_map) {
        if(p_map==null || p_map.isEmpty())
            return Collections.emptyMap();
        if(p_map.size()==1) {
            Map.Entry<T, K> entry = p_map.entrySet().iterator().next();
            return Collections.singletonMap(entry.getKey(), entry.getValue());
        }
        return p_map;
    }

    public static @NotNull <T> List<T> getReadOnlyList(List<T> p_list) {
        if(p_list==null || p_list.isEmpty())
            return Collections.emptyList();
        if(p_list.size()==1) {
            T value = p_list.get(0);
            return Collections.singletonList(value);
        }
        return Collections.unmodifiableList(p_list);
    }

    public static @NotNull <T,K> Set<T> getReadOnlySet(Set<T> p_set) {
        if(p_set==null || p_set.isEmpty())
            return Collections.emptySet();
        if(p_set.size()==1) {
            T value = p_set.iterator().next();
            return Collections.singleton(value);
        }
        return Collections.unmodifiableSet(p_set);
    }

    public static @NotNull <T,K> Map<T,K> getReadOnlyMap(Map<T,K> p_map) {
        if(p_map==null || p_map.isEmpty())
            return Collections.emptyMap();
        if(p_map.size()==1) {
            Map.Entry<T, K> entry = p_map.entrySet().iterator().next();
            return Collections.singletonMap(entry.getKey(), entry.getValue());
        }
        return Collections.unmodifiableMap(p_map);
    }
}
