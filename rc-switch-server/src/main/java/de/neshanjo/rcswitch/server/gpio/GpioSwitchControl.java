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
package de.neshanjo.rcswitch.server.gpio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.GpioUtil;

import de.pi3g.pi.rcswitch.RCSwitch;

/**
 *
 * @author Johannes
 */
public class GpioSwitchControl implements SwitchControl {
    
    private static final Logger LOG = LoggerFactory.getLogger(GpioSwitchControl.class);
    private final RCSwitch transmitter;
    
    public GpioSwitchControl() {
        GpioUtil.enableNonPrivilegedAccess();
        transmitter = new RCSwitch(RaspiPin.GPIO_00);
        LOG.info("Initialized switch control.");
    }
    
    @Override
    public synchronized void turnOn(String group, int switchId) {
        transmitter.switchOn(RCSwitch.getSwitchGroupAddress(group), switchId);
    }

    @Override
    public synchronized void turnOff(String group, int switchId) {
        transmitter.switchOff(RCSwitch.getSwitchGroupAddress(group), switchId);
    }
    
}
