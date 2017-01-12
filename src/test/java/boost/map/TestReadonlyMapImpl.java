package boost.map;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import java.util.*;
import java.util.Map.Entry;

public class TestReadonlyMapImpl extends TestCase {

    public TestReadonlyMapImpl(String name) {
        super(name);
    }

    public static TestSuite suite() {
        return new TestSuite(TestReadonlyMapImpl.class);
    }

    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public void test_emptyMap() throws Exception {
        IReadonlyMap<Object, Object> map = ReadonlyMapBuilder.empty();
        assert map.isEmpty();
        assert map.size() == 0;
        assert !map.containsKey(null);
        assert !map.containsValue(null);
        assert map.get(null) == null;
        assert map.getAnyValue() == null;
        assert map.keySet().isEmpty();
        assert map.values().isEmpty();
        assert map.entrySet().isEmpty();

        assert map.hashCode() == Collections.emptyMap().hashCode();
        assert map.equals(Collections.emptyMap());

        map.addAllTo(Collections.emptyMap());
        map.addAllValuesTo(Collections.emptyList());
        Iterator<Entry<Object, Object>> it = map.iterator();
        assert !it.hasNext();

        boolean hasException = false;
        try { map.put(null, null); } catch (UnsupportedOperationException ex) { hasException=true; }
        assert hasException;

        hasException = false;
        try { map.remove(null); } catch (UnsupportedOperationException ex) { hasException=true; }
        assert hasException;

        hasException = false;
        try { map.clear(); } catch (UnsupportedOperationException ex) { hasException=true; }
        assert hasException;

        hasException = false;
        try { map.putAll(new HashMap()); } catch (UnsupportedOperationException ex) { hasException=true; }
        assert hasException;
    }

    public void test_singletonMap() throws Exception {
        IReadonlyMap<String, String> map = ReadonlyMapBuilder.singleton("key", "value");

        assert !map.isEmpty();
        assert map.size() == 1;

        assert map.containsKey("key");
        assert !map.containsKey(null);
        assert !map.containsKey("key2");

        assert map.containsValue("value");
        assert !map.containsValue("key");
        assert !map.containsValue("value1");
        assert !map.containsValue(null);

        assert "value".equals(map.getAnyValue());

        assert map.get(null) == null;
        assert "value".equals(map.get("key"));
        assert map.get("key2") == null;

        HashMap<String, String> map1 = new HashMap<String, String>();
        map.addAllTo(map1);
        assert  map1.size()==1;
        assert "value".equals(map1.get("key"));
        assert map.equals(map1);
        assert map.keySet().size()==1;
        assert map.keySet().equals(map1.keySet());
        assert map.keySet().equals(Collections.singleton("key"));

        Map<String, String> singletonMap = Collections.singletonMap("key", "value");
        assert map.equals(singletonMap);
        assert map.hashCode() == singletonMap.hashCode();

        List<String> list = new ArrayList<String>();
        map.addAllValuesTo(list);

        assert map.values().size()==1;
        assert map.values().equals(list);
        assert map.values().equals(Collections.singletonList("value"));

        assert map.entrySet().size()==1;
        Iterator<Map.Entry<String, String>> it = map.iterator();
        assert it.hasNext();
        Map.Entry<String, String> entry = it.next();
        assert "key".equals(entry.getKey());
        assert "value".equals(entry.getValue());

        boolean hasException = false;
        try { map.put(null, null); } catch (UnsupportedOperationException ex) { hasException=true; }
        assert hasException;

        hasException = false;
        try { map.remove(null); } catch (UnsupportedOperationException ex) { hasException=true; }
        assert hasException;

        hasException = false;
        try { map.clear(); } catch (UnsupportedOperationException ex) { hasException=true; }
        assert hasException;

        hasException = false;
        try { map.putAll(new HashMap<String, String>()); } catch (UnsupportedOperationException ex) { hasException=true; }
        assert hasException;
    }

    public void test_twoValuesMap() throws Exception {
        IReadonlyMap<String, String> map = new TwoValuesMap<String, String>("key1", "value1", "key2", "value2");
        IReadonlyMap<String, String> map1 = new TwoValuesMap<String, String>("key2", "value2", "key1", "value1");

        assert !map.isEmpty();
        assert map.size() == 2;
        assert map1.size() == 2;

        assert map.containsKey("key1");
        assert map.containsKey("key2");
        assert !map.containsKey(null);
        assert !map.containsKey("key3");

        assert map.containsValue("value1");
        assert map.containsValue("value2");
        assert !map.containsValue("key1");
        assert !map.containsValue("key2");
        assert !map.containsValue("value3");
        assert !map.containsValue(null);

        assert "value1".equals(map.getAnyValue());
        assert "value2".equals(map1.getAnyValue());

        assert map.get(null) == null;
        assert "value1".equals(map.get("key1"));
        assert "value2".equals(map.get("key2"));
        assert map.get("key3") == null;

        HashMap<String, String> map2 = new HashMap<String, String>();
        map.addAllTo(map2);
        assert  map2.size()==2;
        assert "value1".equals(map2.get("key1"));
        assert "value2".equals(map2.get("key2"));

        assert map.hashCode() == map1.hashCode();
        assert map.hashCode() == map2.hashCode();

        assert map.equals(map1);
        assert map.equals(map2);
        assert map1.equals(map2);
        assert map.keySet().size()==2;
        assert map.keySet().equals(map1.keySet());
        assert map.keySet().equals(map2.keySet());
        assert map1.keySet().equals(map2.keySet());

        List<String> list = new ArrayList<String>();
        map.addAllValuesTo(list);
        assert list.size()==2;

        Set<String> set = new HashSet<String>();
        map.addAllValuesTo(set);
        assert set.size()==2;

        assert map.values().size()==2;
        assert map.values().equals(set);
        assert map1.values().equals(set);

        assert map.entrySet().size()==2;
        Iterator<Map.Entry<String, String>> it = map.iterator();
        assert it.hasNext();
        Map.Entry<String, String> entry = it.next();
        assert "key1".equals(entry.getKey());
        assert "value1".equals(entry.getValue());
        assert it.hasNext();
        entry = it.next();
        assert "key2".equals(entry.getKey());
        assert "value2".equals(entry.getValue());

        boolean hasException = false;
        try { map.put(null, null); } catch (UnsupportedOperationException ex) { hasException=true; }
        assert hasException;

        hasException = false;
        try { map.remove(null); } catch (UnsupportedOperationException ex) { hasException=true; }
        assert hasException;

        hasException = false;
        try { map.clear(); } catch (UnsupportedOperationException ex) { hasException=true; }
        assert hasException;

        hasException = false;
        try { map.putAll(new HashMap<String, String>()); } catch (UnsupportedOperationException ex) { hasException=true; }
        assert hasException;
    }

    public void test_readonlyMap() throws Exception {

    }
}
