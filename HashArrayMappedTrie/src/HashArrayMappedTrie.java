/**
 * 
 * @author haomingl
 *
 */
public class HashArrayMappedTrie {
	public static final int INTEGER_BIT_LENGTH = 32;
	public static final int LENGTH = 4;
	
	private Node root;
	
	public HashArrayMappedTrie() {
		root = new Node(false);
	}
	
	/**
	 * This function adds the element to the HAMT
	 * @param element is the one to be added
	 */
	public void add(Object element) {
		int hashcode = element.hashCode();
		Node node = root;
		int i = 0;
		
		// Go through all the branches that match the hash code
		while (i <= INTEGER_BIT_LENGTH / LENGTH) {
			Node match = node.getMatchNode(hashcode, LENGTH * i);
			
			// If there's no matches, break the loop
			if (match == null) {
				break;
			}
			node = match;
			i++;
		}
		
		// Create all the needed branch
		while (i <= INTEGER_BIT_LENGTH / LENGTH) {
			Node newNode;
			
			// Determine if the new node is a leaf or not
			if (i != INTEGER_BIT_LENGTH / LENGTH)
				newNode = new Node(false);
			else 
				newNode = new Node(true);
			
			// Add the signature to the node
			node.addSignature(hashcode, i * LENGTH);
			
			// Add the new node to the children of this node
			node.next.add(newNode);
			node = newNode;
			i++;
		}
		
		// Add the element to the leaf
		node.add(element);
	}
	
	/**
	 * This function checks if this element is in HAMT
	 * @param element to be checked if it's in the HAMT
	 * @return true if the HAMT contains this element. False otherwise.
	 */
	public boolean contains(Object element) {
		int hashcode = element.hashCode();
		Node node = root;
		int i = 0;
		
		// Go thru all the branch to see if there's a match
		while (i <= INTEGER_BIT_LENGTH / LENGTH) {
			Node match = node.getMatchNode(hashcode, i * LENGTH);
			
			// If there's no match for this element, return false
			if (match == null)
				return false;
			
			// If there's a match, keep going until we reach the leaf
			node = match;
			i++;
		}
		return node.contains(element);
	}
}
