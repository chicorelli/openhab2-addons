/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.homematic.converter.type;

import org.eclipse.smarthome.core.library.types.StringType;
import org.openhab.binding.homematic.converter.ConverterException;
import org.openhab.binding.homematic.internal.model.HmDatapoint;
import org.openhab.binding.homematic.internal.model.HmDatapointInfo;

/**
 * Converts between a Homematic datapoint value and a openHab StringType.
 *
 * @author Gerhard Riegler - Initial contribution
 */
public class StringTypeConverter extends AbstractTypeConverter<StringType> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean toBindingValidation(HmDatapoint dp) {
        return dp.isStringType() || dp.isEnumType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object toBinding(StringType type, HmDatapoint dp) throws ConverterException {
        if (dp.isStringType()) {
            return type.toString();
        } else {
            int idx = dp.getOptionIndex(type.toString());
            if (idx == -1) {
                throw new ConverterException(String.format("Option value '%s' not found in datapoint '%s'",
                        type.toString(), new HmDatapointInfo(dp)));
            }
            return idx;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean fromBindingValidation(HmDatapoint dp) {
        return (dp.isStringType() && dp.getValue() instanceof String)
                || (dp.isEnumType() && dp.getValue() instanceof Number);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected StringType fromBinding(HmDatapoint dp) throws ConverterException {
        if (dp.isStringType()) {
            return new StringType(dp.getValue().toString());
        } else {
            String value = dp.getOptionValue();
            if (value == null) {
                throw new ConverterException(String.format("Option for value '%s' not found in datapoint '%s'",
                        dp.getValue(), new HmDatapointInfo(dp)));
            }
            return new StringType(value);
        }
    }

}
