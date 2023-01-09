package main.decoder;


/**
 * Milesight UC300 controller data decoder.
 * This class is in charge of decoding the data of the binary payload of the uc300 controllers.
 * For more information see: https://resource.milesight-iot.com/milesight/document/uc300-lora-communication-protocol-en.pdf - "UC300 IoT Controller Communication Protocol (for LoRaWAN® Version)"
 * 
 * @author Juanma Tola
 * @version: v0.1.0
 */
public abstract class UC300Decoder {

	// Digital
	//	03 Digital Input 1
	private static final byte DI1_CH_ID = 0x03;
	//	04 Digital Input 2
	private static final byte DI2_CH_ID = 0x04;
	//	05 Digital Input 3
	private static final byte DI3_CH_ID = 0x05;
	//	06 Digital Input 4
	private static final byte DI4_CH_ID = 0x06;
	// Type ids
	private static final byte DI_TYPE_ID = 0x00;
	private static final byte DO_TYPE_ID = 0x01;
	private static final byte COUNTER_TYPE_ID = (byte) 0xC8;

	//	07 Digital Output 1
	private static final byte DO1_CH_ID = 0x07;
	//	08 Digital Output 2
	private static final byte DO2_CH_ID = 0x08;

	// Analog
	//	09 PT100 Input 1
	private static final byte PT100_1_CH_ID = 0x09;
	//	0a PT100 Input 2
	private static final byte PT100_2_CH_ID = 0x0A;
	
	//	0b Analog Input (4-20mA) 1
	private static final byte AI1_C_CH_ID = 0x0B;
	//	0c Analog Input (4-20mA) 2
	private static final byte AI2_C_CH_ID = 0x0C;
	//	0d Analog Input (0-10V) 1
	private static final byte AI1_V_CH_ID = 0x0D;
	//	0f Analog Input (0-10V) 2
	private static final byte AI2_V_CH_ID = 0x0E;
	
	//	ff RS485 Modbus Input/System Info
	private static final byte SYSINFO_CH_ID = (byte) 0xFF;
	
	// High value
	private static final byte HIGH_VALUE = 0x01;

	public static UC300Data decode(byte[] bytes) {

		UC300Data data = new UC300Data();

		int i = 0;

		while (i < bytes.length) {

			byte channel_id = bytes[i++];
			byte channel_type = bytes[i++];

			// DI 1
			if (channel_id == DI1_CH_ID && channel_type == DI_TYPE_ID) {
				data.getDigitalInputs().put((int) channel_id, bytes[i] == HIGH_VALUE);
				i += 1;
			}
			// DI 2
			else if (channel_id == DI2_CH_ID && channel_type == DI_TYPE_ID) {
				data.getDigitalInputs().put((int) channel_id, bytes[i] == HIGH_VALUE);
				i += 1;
			}
			// DI 3
			else if (channel_id == DI3_CH_ID && channel_type == DI_TYPE_ID) {
				data.getDigitalInputs().put((int) channel_id, bytes[i] == HIGH_VALUE);
				i += 1;
			}
			// DI 4
			else if (channel_id == DI4_CH_ID && channel_type == DI_TYPE_ID) {
				data.getDigitalInputs().put((int) channel_id, bytes[i] == HIGH_VALUE);
				i += 1;
			}
			// PULSE COUNTER 1
			else if (channel_id == DI1_CH_ID && channel_type == (byte) COUNTER_TYPE_ID) {
				long value = littleEndian4BytesValue(bytes, i);
				data.getPulseCounters().put((int) channel_id, value);
				i += 4;
			}
			// PULSE COUNTER 2
			else if (channel_id == DI2_CH_ID && channel_type == (byte) COUNTER_TYPE_ID) {
				long value = littleEndian4BytesValue(bytes, i);
				data.getPulseCounters().put((int) channel_id, value);
				i += 4;
			} // PULSE COUNTER 3
			else if (channel_id == DI3_CH_ID && channel_type == (byte) COUNTER_TYPE_ID) {
				long value = littleEndian4BytesValue(bytes, i);
				data.getPulseCounters().put((int) channel_id, value);
				i += 4;
			} // PULSE COUNTER 4
			else if (channel_id == DI4_CH_ID && channel_type == (byte) COUNTER_TYPE_ID) {
				long value = littleEndian4BytesValue(bytes, i);
				data.getPulseCounters().put((int) channel_id, value);
				i += 4;
			}
			// DO 1
			else if (channel_id == DO1_CH_ID && channel_type == DO_TYPE_ID) {
				data.getDigitalOutputs().put((int) channel_id, bytes[i] == HIGH_VALUE);
				i += 1;
			}
			// DO 2
			else if (channel_id == DO2_CH_ID && channel_type == DO_TYPE_ID) {
				data.getDigitalOutputs().put((int) channel_id, bytes[i] == HIGH_VALUE);
				i += 1;
			}
			// AI 1
			else if (channel_id == AI1_C_CH_ID) {
				long value = littleEndian4BytesValue(bytes, i);
				double valorEscalado =  (double) value / 100;
				data.getAnalogInputs().put((int) channel_id, valorEscalado);
				i += 4;
			}
			// AI 2
			else if (channel_id == AI2_C_CH_ID) {
				long value = littleEndian4BytesValue(bytes, i);
				double valorEscalado =  (double) value / 100;
				data.getAnalogInputs().put((int) channel_id, valorEscalado);
				i += 4;
			}
			// AI 1 for Voltage
			else if (channel_id == AI1_V_CH_ID) {
				long value = littleEndian4BytesValue(bytes, i);
				double valorEscalado =  (double) value / 100;
				data.getAnalogInputs().put((int) channel_id, valorEscalado);
				i += 4;
			}
			// AI 2 for Voltage
			else if (channel_id == AI2_V_CH_ID) {
				long value = littleEndian4BytesValue(bytes, i);
				double valorEscalado = (double) value / 100;
				data.getAnalogInputs().put((int) channel_id, valorEscalado);
				i += 4;
			}

		}

		return data;
	}

	private static long littleEndian4BytesValue(byte[] bytes, int i) {
		long byte3 = unsignedByteToLong(bytes[i + 3]);
		long byte2 = unsignedByteToLong(bytes[i + 2]);
		long byte1 = unsignedByteToLong(bytes[i + 1]);
		long byte0 = unsignedByteToLong(bytes[i]);
		return (byte3 << 24) + (byte2 << 16) + (byte1 << 8) + byte0;
	}

	private static long unsignedByteToLong(byte b) {

		return ((long) b) & 0xFF;

	}

	private static int unsignedByteToInt(byte b) {

		return ((int) b) & 0xFF;

	}

}
