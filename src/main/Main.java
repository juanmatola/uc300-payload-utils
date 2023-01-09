package main;

import java.util.Base64;

import main.decoder.UC300Data;
import main.decoder.UC300Decoder;

public class Main {

	public static void main(String[] args) {
		
		//Example
		String payload = "AwAABwEACwK9BgAA";
		// hex => 0300000701000b02bd060000
		
		// 03 00 00 => di1 low
		// 07 01 00 => do1 low
		// 0b 02 bd 06 00 00 => 00 00 06 bd (1725) => / 100 => ai1 17,25

		// Get binary payload
		byte[] binaryPayload = Base64.getDecoder().decode(payload);

		UC300Data data = UC300Decoder.decode(binaryPayload);

		if(data.getDigitalInputs().get(3) != null) System.out.println("DI 03: " + data.getDigitalInputs().get(3));
		if(data.getPulseCounters().get(3) != null) System.out.println("PC 03: " + data.getPulseCounters().get(3));

		if(data.getDigitalInputs().get(4) != null) System.out.println("DI 04: " + data.getDigitalInputs().get(4));
		if(data.getPulseCounters().get(4) != null) System.out.println("PC 04: " + data.getPulseCounters().get(4));
		
		if(data.getDigitalInputs().get(5) != null) System.out.println("DI 03: " + data.getDigitalInputs().get(5));
		if(data.getPulseCounters().get(5) != null) System.out.println("PC 03: " + data.getPulseCounters().get(5));

		if(data.getDigitalInputs().get(6) != null) System.out.println("DI 04: " + data.getDigitalInputs().get(6));
		if(data.getPulseCounters().get(6) != null) System.out.println("PC 04: " + data.getPulseCounters().get(6));
		
		if(data.getDigitalOutputs().get(7) != null) System.out.println("DO 01: " + data.getDigitalOutputs().get(7));
		if(data.getDigitalOutputs().get(8) != null) System.out.println("DO 02: " + data.getDigitalOutputs().get(8));
		
		if(data.getAnalogInputs().get(11) != null) System.out.println("AI 01: " + data.getAnalogInputs().get(11));
		if(data.getAnalogInputs().get(12) != null) System.out.println("AI 02: " + data.getAnalogInputs().get(12));
		
		if(data.getAnalogInputs().get(13) != null) System.out.println("AI 01 V ch-13: " + data.getAnalogInputs().get(13));
		if(data.getAnalogInputs().get(14) != null) System.out.println("AI 02 V ch-14: " + data.getAnalogInputs().get(14));

	}

}
