
/* eXist Native XML Database
 * Copyright (C) 2000-03,  Wolfgang M. Meier (wolfgang@exist-db.org)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Library General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 * $Id$
 */

package org.exist.xquery;

import org.exist.dom.DocumentSet;
import org.exist.xquery.parser.XQueryAST;
import org.exist.xquery.util.ExpressionDumper;
import org.exist.xquery.value.Item;
import org.exist.xquery.value.Sequence;

/**
 * Base interface implemented by all classes which are part
 * of an XQuery/XPath expression. The main method is 
 * {@link #eval(StaticContext, DocumentSet, Sequence, Item)}. Please
 * read the description there.
 */
public interface Expression {
	
    public final static int SINGLE_STEP_EXECUTION = 1;
    
    /**
     * Statically analyze the expression and its subexpressions.
     * 
     * During the static analysis phase, the query engine can detect
     * unknown variables or some type errors.
     * @param parent
     * @param flags
     * 
     * @throws XPathException
     */
    public void analyze(Expression parent, int flags) throws XPathException;
    
	/**
	 * Evaluate the expression represented by this object.
	 *
	 * Depending on the context in which this expression is executed,
	 * either the context sequence, the context item or both of them may 
	 * be set. An implementing class should know how to handle this.
	 * 
	 * The general contract is as follows: if the {@link Dependency#CONTEXT_ITEM}
	 * bit is set in the bit field returned by {@link #getDependencies()}, the eval method will
	 * be called once for every item in the context sequence. The <b>contextItem</b>
	 * parameter will be set to the current item. Otherwise, the eval method will only be called
	 * once for the whole context sequence and <b>contextItem</b> will be null.
	 * 
	 * eXist tries to process the entire context set in one, single step whenever
	 * possible. Thus, most classes only expect context to contain a list of 
	 * nodes which represents the current context of the expression. 
	 * 
	 * The position() function in XPath is an example for an expression,
	 * which requires both, context sequence and context item to be set.
	 *
	 * The context sequence might be a node set, a sequence of atomic values or a single
	 * node or atomic value. 
	 * 
	 * @param docs the set of documents all nodes belong to.
	 * @param contextSequence the current context sequence.
	 * @param contextItem a single item, taken from context. This defines the item,
	 * the expression should work on.
	 */
	public Sequence eval(Sequence contextSequence, Item contextItem) throws XPathException;

	/**
	 * Evaluate the expression represented by this object.
	 *
	 * An overloaded method which just passes the context sequence depending on the
	 * expression context.
	 *
	 * @param docs the set of documents all nodes belong to.
	 * @param contextSet the node-set which defines the current context node-set.
	 */
	public Sequence eval(Sequence contextSequence)
		throws XPathException;
	
	public void setPrimaryAxis(int axis);
	
	/**
	 * The static return type of the expression.
	 *
	 * This method should return one of the type constants defined in class 
	 * {@link org.exist.xquery.value.Type}. If the return type cannot be determined
	 * statically, return Type.ITEM.
	 */
	public int returnsType();
	
	/**
	 * The expected cardinality of the return value of the expression.
	 * 
	 * Should return a bit mask with bits set as defined in class {@link Cardinality}.
	 */
	public int getCardinality();
	
	/**
	 * Returns a set of bit-flags, indicating some of the parameters
	 * on which this expression depends. The flags are defined in
	 * {@link Dependency}.
	 * 
	 * @return
	 */
	public int getDependencies();

	/**
	 * Called to inform an expression that it should reset to its initial state. 
	 * 
	 * All cached data in the expression object should be dropped. For example,
	 * the document() function calls this method whenever the input document
	 * set has changed.
	 */	
	public void resetState();

	/**
	 * This method is called to inform the expression object that
	 * it is executed inside an XPath predicate (or in a where clause).
	 * 
	 * @param inPredicate
	 */
	public void setInPredicate(boolean inPredicate);
	
	/**
	 * Write a diagnostic dump of the expression to the passed
	 * {@link ExpressionDumper}.
	 *  
	 * @param dumper the expression dumper to write to
	 */
	public void dump(ExpressionDumper dumper);
	
	public void setContextDocSet(DocumentSet contextSet);
	
	/**
	 * Returns the {@link XQueryAST} node from which this expression
	 * has been constructed by the parser. This node contains location information
	 * (line number and column) important for error reports.
	 * 
	 * @return
	 */
	public XQueryAST getASTNode();
	
	public void setASTNode(XQueryAST ast);
}
