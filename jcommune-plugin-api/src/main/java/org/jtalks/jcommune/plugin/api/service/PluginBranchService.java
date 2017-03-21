/**
 * Copyright (C) 2011  JTalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.jtalks.jcommune.plugin.api.service;

import org.jtalks.jcommune.model.entity.Branch;
import org.jtalks.jcommune.plugin.api.exceptions.NotFoundException;

/**
 * @author Mikhail Stryzhonok
 */
public interface PluginBranchService {

    /**
     * Searches in database {@link org.jtalks.jcommune.model.entity.Branch} with specified id
     *
     * @param id id of interested branch
     * @return {@link org.jtalks.jcommune.model.entity.Branch} with specified id
     *
     * @throws NotFoundException if branch not found
     */
    Branch get(Long id) throws NotFoundException;
}
