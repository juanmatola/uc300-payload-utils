package main.encoder;

import java.util.Base64;
import java.util.Optional;

@SuppressWarnings("unused")
public abstract class UC300Encoder {

	private static final byte DO1_CH = 0x07;
	private static final byte DO2_CH = 0x08;
	private static final byte DEVICE_CONFIG_CH = (byte) 0xFF;
	private static final byte REPORTING_INVERVAL_ID = 0x03;
	private static final byte REBOOT_ID = 0x10;

	private static final byte HIGH = 0x01;
	private static final byte LOW = 0x00;
	private static final byte BLANK = 0x00;
	private static final byte RESERVED_VALUE = (byte) 0xFF;

	public static byte[] encodeControlPayload(UC300DigitalOutputs outputs) {

		byte[] payloadDO1 = getByteControlPayloadFrom(outputs.getDo01Value(), DO1_CH);
		byte[] payloadDO2 = getByteControlPayloadFrom(outputs.getDo02Value(), DO2_CH);

		return concatPayloads(payloadDO1, payloadDO2);

	}
	
	// TODO: Config payload
	public static byte[] encodeConfigPayload(Object cfg) {

		return null;

	}

	private static byte[] getByteControlPayloadFrom(Optional<Boolean> doValue, byte channelID) {

		if (doValue.isEmpty())
			return new byte[0];

		byte[] payload = new byte[4];
		boolean value = doValue.get();

		payload[0] = channelID;
		payload[1] = (value) ? HIGH : LOW;
		payload[2] = BLANK;
		payload[3] = RESERVED_VALUE;

		return payload;
	}

	private static byte[] concatPayloads(byte[] payloadDO1, byte[] payloadDO2) {

		byte[] result = new byte[payloadDO1.length + payloadDO2.length];

		for (int i = 0; i < result.length; i++) {
			if (i < payloadDO1.length)
				result[i] = payloadDO1[i];
			else
				result[i] = payloadDO2[i - payloadDO1.length];
		}

		return result;
	}

}
