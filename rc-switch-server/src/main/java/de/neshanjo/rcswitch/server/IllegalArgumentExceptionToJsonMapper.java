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
package de.neshanjo.rcswitch.server;

import de.neshanjo.rcswitch.server.data.ExceptionResponse;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Johannes C. Schneider
 */
@Provider
public class IllegalArgumentExceptionToJsonMapper implements ExceptionMapper<IllegalArgumentException> {

    private static final Logger LOG = LoggerFactory.getLogger(IllegalArgumentExceptionToJsonMapper.class);

    @Override
    public Response toResponse(IllegalArgumentException e) {
        LOG.error(e.getMessage(), e);
        return Response.status(Status.BAD_REQUEST)
                .entity(new ExceptionResponse(Status.BAD_REQUEST.getStatusCode(), e))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
