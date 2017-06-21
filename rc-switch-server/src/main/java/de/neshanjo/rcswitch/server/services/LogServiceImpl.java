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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.neshanjo.rcswitch.server.data.LogEntry;

/**
 *
 * @author Johannes C. Schneider
 */
public class LogServiceImpl implements LogService {
    
    private static final Logger LOG = LoggerFactory.getLogger(LogServiceImpl.class);
    private static final int WEAK_MAX_LOG_SIZE = 100;
    
    private final ConcurrentLinkedQueue<LogEntry> logRepo = new ConcurrentLinkedQueue<>();
    
    @Override
    public void log(LogEntry entry) {
        LOG.debug(entry.getServerMessage());
        logRepo.offer(entry);
    }
    
    @Override
    public List<LogEntry> getLog() {
        final int currentSize = logRepo.size();
        for (int i = 0; i < currentSize - WEAK_MAX_LOG_SIZE; i++) {
            //if log size is more than WEAK_MAX_LOG_SIZE, remove entries
            logRepo.poll();
        }
        //Note that the resulting list could still have more than WEAK_MAX_LOG_SIZE entries, if entries are added after 
        //currentSize has been determined (that's why it is called WEAL_...)
        
        return new ArrayList<>(logRepo);
    } 
    
}
