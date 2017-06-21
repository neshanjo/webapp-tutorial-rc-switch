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

import java.util.regex.Pattern;

import de.neshanjo.rcswitch.server.gpio.SwitchControl;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.neshanjo.rcswitch.server.data.LogEntry;

/**
 *
 * @author Johannes C. Schneider
 */
@Path("/switch")
public class SwitchResource {
    
    public enum Operation {
        on, off
    }

    private static final Pattern GROUP_PATTERN = Pattern.compile("[01]{5}");
    
    @Inject
    private SwitchControl switchControl;
    
    @Inject
    private LogService logService;
    
    /**
     * 
     * @param group switch group (as coded in the remote control)
     * @param code code of the socket, A=1, B=2, ...
     * @param operation
     * @param request
     * @return 
     */
    @Path("{group}/{code}/{operation}")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response turnSwitch(@PathParam("group") String group, 
            @PathParam("code") int code,
            @PathParam("operation") Operation operation,
            @Context HttpServletRequest request) {
        if (!GROUP_PATTERN.matcher(group).matches()) {
            throw new WebApplicationException("group must match pattern " + GROUP_PATTERN, Response.Status.BAD_REQUEST);
        }
        if (operation == Operation.on) {
            switchControl.turnOn(group, code);
            logService.log(new LogEntry(System.currentTimeMillis(), String.format("%s turned on switch %s:%d", 
                    request.getRemoteAddr(), group, code), request.getRemoteAddr(), group, code, true));
        } else {
            switchControl.turnOff(group, code);
            logService.log(new LogEntry(System.currentTimeMillis(), String.format("%s turned off switch %s:%d", 
                    request.getRemoteAddr(), group, code), request.getRemoteAddr(), group, code, false));
        }
        return Response.noContent().status(Response.Status.NO_CONTENT).build();
    }
}
