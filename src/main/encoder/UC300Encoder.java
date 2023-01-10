package main.encoder;

import java.util.Optional;

public abstract class UC300Encoder {

	// Control
	private static final byte DO1_CH = 0x07;
	private static final byte DO2_CH = 0x08;

	// Config
	private static final byte DEVICE_CONFIG_CH = (byte) 0xFF;
	private static final byte REPORTING_INVERVAL_ID = 0x03;
	private static final byte REBOOT_ID = 0x10;

	// Values
	private static final byte HIGH = 0x01;
	private static final byte LOW = 0x00;
	private static final byte BLANK = 0x00;
	private static final byte RESERVED_VALUE = (byte) 0xFF;

	// Static cmd
	private static final byte[] REBOOT_PAYLOAD = new byte[] { DEVICE_CONFIG_CH, REBOOT_ID, RESERVED_VALUE };

	// DO control payload
	public static byte[] encodeControlPayload(UC300DigitalOutputs outputs) {

		byte[] payloadDO1 = getByteControlPayloadFrom(outputs.getDo01Value(), DO1_CH);
		byte[] payloadDO2 = getByteControlPayloadFrom(outputs.getDo02Value(), DO2_CH);

		return concatPayloads(payloadDO1, payloadDO2);

	}

	// Device config payloads

	// Set reporting interval
	// Default: 1800s
	// Range: 10-64800s
	public static byte[] encodeReportingIntervalPayload(Integer seconds) {

		if (seconds > 64800)
			seconds = 64800;

		if (seconds < 10)
			seconds = 10;

		int value = seconds.intValue();

		byte byte0 = (byte) value;
		byte byte1 = (byte) (value >>> 8);

		byte[] payload = new byte[4];

		payload[0] = DEVICE_CONFIG_CH;
		payload[1] = REPORTING_INVERVAL_ID;
		payload[2] = byte0;
		payload[3] = byte1;

		return payload;

	}

	public static byte[] encodeRebootDevicePayload() {

		return REBOOT_PAYLOAD;

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
