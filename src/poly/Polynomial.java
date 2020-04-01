package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		//variables
		Node first = poly1;
		Node second = poly2;
		Node ptr = new Node(0, 0, null);
		Node res = ptr;
		
		//while both files are not empty
		while(first != null && second != null)
		{
			//if same exponent then add constants
			if(first.term.degree == second.term.degree)
			{
				ptr.next = new Node(first.term.coeff + second.term.coeff, first.term.degree, null);
				first = first.next;
				second = second.next;
			}	
			
			//if one exponent bigger then other, use the smaller one
			else if (first.term.degree < second.term.degree)
			{
				ptr.next = new Node(first.term.coeff, first.term.degree, null);
				first = first.next;
				
			}
			
			//if one exponent bigger then other, use the smaller one
			else if (first.term.degree > second.term.degree)
			{
				ptr.next = new Node(second.term.coeff, second.term.degree, null);
				second = second.next;
			}
			
			//remove any 0 coefficients
			ptr = ptr.next;	
		}	
		
		//check if one file shorter and ended and add the rest of the other file
		while(second != null && first == null)
		{
			ptr.next = new Node(second.term.coeff, second.term.degree, null);
			second = second.next;
			ptr = ptr.next;
		}
		
		while(first != null && second == null)
		{
			ptr.next = new Node(first.term.coeff, first.term.degree, null);
			first = first.next;
			ptr = ptr.next;
		}
		
		//move res one more because of the initial node 0
		res = res.next;
	
		
		ptr = res;
		Node prev = null;
		
		//loop for 0.0 coefficients
		while(ptr != null)
		{
			//if ptr is 0.0, remove
			if(ptr.term.coeff == 0.0)
			{
				if(prev == null)
				{
					res = res.next;
					ptr = res;
				}
				else {
					ptr = ptr.next;
					prev.next = ptr;
				}
			}
			//else move on
			else
			{
				prev = ptr;
				ptr = ptr.next;
			}

		}
		
		return res;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		//variables
		Node first = poly1;
		Node second = poly2;
		Node ptr = new Node(0, 0, null);
		Node res = ptr;
		
		//check for null file
		if(first == null || second == null)
			return null;
		
		
		//multiplication
		while(first != null)
		{	
			ptr.next = new Node(first.term.coeff * second.term.coeff, first.term.degree + second.term.degree, null);
			second = second.next;
			
			if(second == null)
			{
				first = first.next;
				second = poly2;
			}
			
			ptr = ptr.next;
		}
		
		
		//remove 0 coefficient from front
		if(res.term.coeff == 0.0)
			res = res.next;
		
		//adding up similar degrees
		Node prev = res;
		Node curr = res.next;
		Node temp = res;

		while(curr != null && temp != null)
		{
			if(temp.term.degree == curr.term.degree)
			{
				temp.term.coeff = temp.term.coeff + curr.term.coeff;
				curr = curr.next;
				prev.next = curr;
			}
			else
			{
				prev = curr;
				curr = curr.next;
			}
			
			if(curr == null)
			{
				temp = temp.next;
				prev = temp;
				curr = temp.next;
			}
			
		}
		
		
		temp = res;
		curr = temp.next;
		
		//sort in descending order
		while(curr != null)
		{
			if(temp.term.degree < curr.term.degree)
				curr = curr.next;
		
			else if (temp.term.degree > curr.term.degree)
			{
				
				Term hold = curr.term;
				curr.term = temp.term;
				temp.term = hold;
				curr = curr.next;
			}
			
			if(curr == null)
			{
				temp = temp.next;
				curr = temp.next;
			}
		}
		
		return res;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		float res = 0;
		
		for(Node ptr = poly; ptr != null; ptr = ptr.next)
			res += ptr.term.coeff*Math.pow(x, ptr.term.degree);
		
		return res;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
