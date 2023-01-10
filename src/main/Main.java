package main;

import java.util.Base64;

import main.decoder.UC300Data;
import main.decoder.UC300Decoder;
import main.encoder.UC300DigitalOutputs;
import main.encoder.UC300Encoder;

public class Main {

	public static void main(String[] args) {

		// DECODER
		// Ex
		String payload = "AwAABwEACwK9BgAA";
		// hex => 0300000701000b02bd060000

		// 03 00 00 => di1 low
		// 07 01 00 => do1 low
		// 0b 02 bd 06 00 00 => 00 00 06 bd (1725) => / 100 => ai1 17,25

		// Get binary payload
		byte[] binaryPayload = Base64.getDecoder().decode(payload);

		UC300Data data = UC300Decoder.decode(binaryPayload);

		System.out.println("Decode payload\n----------------");

		System.out.println("DI1 (CH-03): " + data.getDigitalInputs().get(3));
		System.out.println("PC1 (CH-03): " + data.getPulseCounters().get(3));
		System.out.println("DI2 (CH-04): " + data.getDigitalInputs().get(4));
		System.out.println("PC2 (CH-04): " + data.getPulseCounters().get(4));
		System.out.println("DI3 (CH-05): " + data.getDigitalInputs().get(5));
		System.out.println("PC3 (CH-05): " + data.getPulseCounters().get(5));
		System.out.println("DI4 (CH-06): " + data.getDigitalInputs().get(6));
		System.out.println("PC4 (CH-06): " + data.getPulseCounters().get(6));
		System.out.println("DO1 (CH-07): " + data.getDigitalOutputs().get(7));
		System.out.println("DO2 (CH-08): " + data.getDigitalOutputs().get(8));
		System.out.println("AI1 mA (CH-11): " + data.getAnalogInputs().get(11));
		System.out.println("AI2 mA (CH-12): " + data.getAnalogInputs().get(12));
		System.out.println("AI1 V (CH-13): " + data.getAnalogInputs().get(13));
		System.out.println("AI2 V (CH-14): " + data.getAnalogInputs().get(14));

		
		// ENCODER
		
		// DO control
		// Ex:
		// do1 low, do2 high
		// hex: 070000ff080100ff
		// base64: BwAA/wgBAP8=

		System.out.println();
		System.out.println("Encode payloads\n----------------");
		System.out.println("-DO control payload");
		
		UC300DigitalOutputs outputsValues = new UC300DigitalOutputs();
		outputsValues.setDO01Value(false);
		outputsValues.setDO02Value(true);

		byte[] encodedControlPayload = UC300Encoder.encodeControlPayload(outputsValues);
		String base64ControlPayload = Base64.getEncoder().encodeToString(encodedControlPayload);

		System.out.println("base64: " + base64ControlPayload);
		

		// Set reporting interval
		System.out.println();
		System.out.println("-Set reporting interval payload");
		byte[] encodedReportingPayload = UC300Encoder.encodeReportingIntervalPayload(600);

		String base64ReportingPayload = Base64.getEncoder().encodeToString(encodedReportingPayload);
		System.out.println("base64: " + base64ReportingPayload);

		
		// Reboot device
		System.out.println();
		System.out.println("-Reboot device payload");
		byte[] encodedRebootPayload = UC300Encoder.encodeRebootDevicePayload();

		String base64RebootPayload = Base64.getEncoder().encodeToString(encodedRebootPayload);
		System.out.println("base64: " + base64RebootPayload);

	}

}
