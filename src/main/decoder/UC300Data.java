package main.decoder;

import java.util.HashMap;

/**
 * Milesight UC300 controller data model
 * The keys of the internal hash maps are the channel identifiers.(3-14)
 * For more information see: https://resource.milesight-iot.com/milesight/document/uc300-lora-communication-protocol-en.pdf - "UC300 IoT Controller Communication Protocol (for LoRaWAN® Version)"
 * 
 * @author Juanma Tola
 * @version: v0.1.0
 */
public class UC300Data {

	// Digital inputs:
	private HashMap<Integer, Boolean> digitalInputs;
	// Digital outputs:
	private HashMap<Integer, Boolean> digitalOutputs;
	// Pulse Counters: 
	private HashMap<Integer, Long> pulseCounters;
	// Analog outputs:
	private HashMap<Integer, Double> analogInputs;

	public UC300Data() {
		this.digitalInputs = new HashMap<Integer, Boolean>();
		this.digitalOutputs = new HashMap<Integer, Boolean>();
		this.pulseCounters = new HashMap<Integer, Long>();
		this.analogInputs = new HashMap<Integer, Double>();
	}

	public HashMap<Integer, Boolean> getDigitalInputs() {
		return digitalInputs;
	}

	public void setDigitalInputs(HashMap<Integer, Boolean> digitalInputs) {
		this.digitalInputs = digitalInputs;
	}

	public HashMap<Integer, Boolean> getDigitalOutputs() {
		return digitalOutputs;
	}

	public void setDigitalOutputs(HashMap<Integer, Boolean> digitalOutputs) {
		this.digitalOutputs = digitalOutputs;
	}

	public HashMap<Integer, Long> getPulseCounters() {
		return pulseCounters;
	}

	public void setPulseCounters(HashMap<Integer, Long> pulseCounters) {
		this.pulseCounters = pulseCounters;
	}

	public HashMap<Integer, Double> getAnalogInputs() {
		return analogInputs;
	}

	public void setAnalogInputs(HashMap<Integer, Double> analogInputs) {
		this.analogInputs = analogInputs;
	}

}
