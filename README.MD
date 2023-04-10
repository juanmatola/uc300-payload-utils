# UC300 Milesight Decoder and Encoder

This Java library provides classes for decoding and encoding data from the UC300 Milesight controllers. The project structure is as follows:

```
|-----decoder
|             |--------UC300Data
|             |--------UC300Decoder
|-----encoder
              |--------UC300DigitalOutputs
              |--------UC300Encoder
```

## Decoder

### UC300Data

This class contains the data fields that store the information received from the UC300 controller. The fields include:

* Digital Inputs
* Digital Outputs
* Pulse Counters
* Analog Inputs

### UC300Decoder

This abstract class provides a method `decode(byte[] bytes)` that decodes the raw data received from the UC300 controller into a `UC300Data` object.

## Encoder

### UC300DigitalOutputs

This class represents the digital outputs of the UC300 controller. It contains the following fields:

* DO1 Value
* DO2 Value

### UC300Encoder

This abstract class provides methods for encoding control and configuration payloads to be sent to the UC300 controller. The methods include:

* `encodeControlPayload(UC300DigitalOutputs outputs)`
* `encodeReportingIntervalPayload(Integer seconds)`
* `encodeRebootDevicePayload()`

## Example

```java


// Example byte array received from the UC300 controller
byte[] rawData = ...;

// Decoding the raw data
UC300Data data = UC300Decoder.decode(rawData);

// Accessing the decoded data
System.out.println("Digital Inputs: " + data.getDigitalInputs());
System.out.println("Digital Outputs: " + data.getDigitalOutputs());
System.out.println("Pulse Counters: " + data.getPulseCounters());
System.out.println("Analog Inputs: " + data.getAnalogInputs());

// Creating a UC300DigitalOutputs object to control the digital outputs
UC300DigitalOutputs outputs = new UC300DigitalOutputs();
outputs.setDO01Value(true); // Setting DO1 to HIGH
outputs.setDO02Value(false); // Setting DO2 to LOW

// Encoding the control payload
byte[] controlPayload = UC300Encoder.encodeControlPayload(outputs);

// Sending the control payload to the UC300 controller
// ...

```
