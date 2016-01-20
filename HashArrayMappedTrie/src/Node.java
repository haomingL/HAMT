import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Node {
		// A mask to get the last bit of an Integer
		public static final int MASK = 0b00000000000000000000000000000001;
	
		public boolean isLeaf; // true if it's a leaf, false otherwise
		public List<Node> next; // stores all the children
		public int signature; // the signature to determine if part of the hash code exists
		public Set<Object> value; // stores all the values in the leaf
		
		public Node(boolean isLeaf) {
			this.isLeaf = isLeaf; 
			this.signature = 0;
			this.value = new HashSet<Object>();
			this.next = new LinkedList<Node>();
			assert isLeaf || Integer.bitCount(this.signature) == this.next.size();
		}
		
		public Node(Object val) {
			this(true);
			this.add(val);
		}
		
		/**
		 * This function adds the object value to the set.
		 * @param val the value to be added to the node.
		 */
		public void add(Object val) {
			if (this.isLeaf)
				this.value.add(val);
		}
		
		/**
		 * This function checks if the node contains the object
		 * @param val to be checked
		 * @return true if the node contains the object. False otherwise.
		 */
		public boolean contains(Object val) {
			if (!this.isLeaf)
				return false;
			return this.value.contains(val);
		}
		
		/**
		 * This function adds the signature to this node
		 * @param hashcode to be used to add the signature
		 * @param startIndex is the start index of the hash code
		 */
		public void addSignature(int hashcode, int startIndex) {
			if (this.isLeaf)
				return;
			if (this.match(hashcode, startIndex))
				return;
			// Get the needed five digits in the hash code
			int fiveBits = (hashcode << startIndex) >>> 
					(HashArrayMappedTrie.INTEGER_BIT_LENGTH - HashArrayMappedTrie.LENGTH); 
			this.signature = this.signature | (MASK << fiveBits);
		}
		
		/**
		 * The function checks if the given part of the hash code matches of one of 
		 * the element in the bucket.
		 * @param hashcode is the given hash code.
		 * @param startIndex is the start index of the hash code.
		 * @return true if there is a bucket that has the matching part of the hash code.
		 * 			False otherwise.
		 */
		public boolean match(int hashcode, int startIndex) {
			if (this.isLeaf)
				return false;
			// Get the needed five digits in the hash code
			int fiveBits = (hashcode << startIndex) >>> 
					(HashArrayMappedTrie.INTEGER_BIT_LENGTH - HashArrayMappedTrie.LENGTH);
			
			// Return if we have this bucket in the array
			return 1 == ((this.signature >>> fiveBits) & MASK);
		}
		
		/**
		 * The function returns a match child of this node. Null if match is not found.
		 * @param hashcode is the hash code of the object to find the match node.
		 * @param startIndex is the start index of the hash code.
		 * @return the node if one is found. Null otherwise.
		 */
		public Node getMatchNode(int hashcode, int startIndex) {
			if (this.isLeaf) 
				return null;
			if (!this.match(hashcode, startIndex))
				return null;
			// Get the needed five digits in the hash code
			int fiveBits = (hashcode << startIndex) >>> 
					(HashArrayMappedTrie.INTEGER_BIT_LENGTH - HashArrayMappedTrie.LENGTH);
			
			// Get the index of the match node in the list
			int index = Integer.bitCount(this.signature >>> (fiveBits + 1));
			return this.next.get(index);
		}
	}
