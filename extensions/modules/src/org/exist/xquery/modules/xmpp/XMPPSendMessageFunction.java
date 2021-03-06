/*
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software Foundation
 *  Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package org.exist.xquery.modules.xmpp;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.exist.dom.QName;
import org.exist.xquery.BasicFunction;
import org.exist.xquery.Cardinality;
import org.exist.xquery.FunctionSignature;
import org.exist.xquery.XPathException;
import org.exist.xquery.XQueryContext;
import org.exist.xquery.value.BooleanValue;
import org.exist.xquery.value.FunctionParameterSequenceType;
import org.exist.xquery.value.FunctionReturnSequenceType;
import org.exist.xquery.value.IntegerValue;
import org.exist.xquery.value.Sequence;
import org.exist.xquery.value.SequenceType;
import org.exist.xquery.value.Type;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

public class XMPPSendMessageFunction extends BasicFunction
{
	protected static final Logger logger = LogManager.getLogger(XMPPSendMessageFunction.class);

	public final static FunctionSignature signatures[] = {
		new FunctionSignature(
			new QName( "send-message", XMPPModule.NAMESPACE_URI, XMPPModule.PREFIX ),
				"Send XMPP chat message.",
				new SequenceType[]
  				{
					new FunctionParameterSequenceType( "chat", Type.LONG, Cardinality.EXACTLY_ONE, 
						"The chat handle for send a message."),
  					new FunctionParameterSequenceType( "message", Type.STRING, Cardinality.EXACTLY_ONE, 
						"The sending message.")
  				},
				new FunctionReturnSequenceType(Type.BOOLEAN, Cardinality.EXACTLY_ONE, 
					"true if the message was sended")
			)
		};

	public XMPPSendMessageFunction( XQueryContext context, FunctionSignature signature )
	{
		super( context, signature );
    }

	public Sequence eval( Sequence[] args, Sequence contextSequence ) throws XPathException
	{
		long chatHandle = ((IntegerValue) args[0].itemAt(0)).getLong();
		Chat chat = XMPPModule.retrieveChat(chatHandle);

		String message = args[1].itemAt(0).getStringValue();
		
		try {
			chat.sendMessage(message);
			return BooleanValue.TRUE;
		} catch (XMPPException e) {
			return BooleanValue.FALSE;
		}
		
	}
}
