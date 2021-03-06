/*
 *  eXist Open Source Native XML Database
 *  Copyright (C) 2010-2011 The eXist Project
 *  http://exist-db.org
 *  
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
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *  
 *  $Id$
 */
package org.exist.security.management;

import org.exist.EXistException;
import org.exist.config.ConfigurationException;
import org.exist.security.Group;
import org.exist.security.PermissionDeniedException;

/**
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 *
 */
public interface GroupsManagement {

	public Group addGroup(Group group) throws PermissionDeniedException, EXistException, ConfigurationException;
	
	public Group getGroup(String name);

	public boolean hasGroup(Group group);
	public boolean hasGroup(String name);

	public boolean updateGroup(Group group) throws PermissionDeniedException, EXistException, ConfigurationException;
	
	public boolean deleteGroup(Group group) throws PermissionDeniedException, EXistException, ConfigurationException;

}