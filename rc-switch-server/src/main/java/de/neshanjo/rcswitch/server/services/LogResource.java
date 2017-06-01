/*
 * Copyright (C) 2016-2017 Johannes C. Schneider
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.neshanjo.rcswitch.server.services;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.neshanjo.rcswitch.server.data.LogEntry;

/**
 *
 * @author Johannes C. Schneider
 */
@Path("/log")
public class LogResource {
    
    @Inject
    private LogService logService;
    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<LogEntry> getLog() {
        return logService.getLog();
        
    }
}
