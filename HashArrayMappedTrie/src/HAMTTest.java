
public class HAMTTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashArrayMappedTrie h = new HashArrayMappedTrie();
		// Two lines below are for debug
		// System.out.println(to32(Integer.toBinaryString("hello".hashCode())));
		// System.out.println(to32(Integer.toBinaryString("java".hashCode())));
		h.add("hello");
		h.add("java");
		assert (h.contains("hello"));
		assert (h.contains("java"));
		assert (h.contains("world"));
	}
	
	// Just for debug
	public static String to32(String s) {
		while (s.length() != 32) {
			s = "0" + s;
		}
		return s;
	}
}