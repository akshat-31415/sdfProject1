# Arbitrary Precision Arithmetic Library in Java

This project implements an arbitrary precision arithmetic library in Java, designed to handle large integers and floating-point numbers beyond the native Java limitations. The library provides functionality for addition, subtraction, multiplication, and division for both integer and floating-point numbers with arbitrary precision.

## Features

- **Support for Arbitrarily Large Integers and Floats:** Handles large numbers using string representations, avoiding overflow issues.
- **Operator Overloading:** Supports basic arithmetic operations (`+`, `-`, `*`, `/`) for both integer and floating-point types.
- **Precision Handling:** Ensures operations on floating-point numbers preserve precision up to 30 decimal places, following the behavior of Java’s `BigDecimal`.
- **CLI and Library Mode:** Can be used as a command-line tool or imported as a Java library.

## Classes

### `AInteger` Class

The `AInteger` class supports arbitrary precision integers and provides the following functionality:
- Constructors:
  - `AInteger()`: Initializes the integer to 0.
  - `AInteger(String s)`: Initializes the integer with the value provided as a string.
  - Copy constructor: Creates a copy of an existing `AInteger` instance.
- Methods:
  - `static AInteger parse(String s)`: Parses a string into an `AInteger` instance.
  - Arithmetic operations: `add()`, `subtract()`, `multiply()`, `divide()` to perform basic arithmetic.

### `AFloat` Class

The `AFloat` class supports arbitrary precision floating-point numbers and provides the following functionality:
- Constructors:
  - `AFloat()`: Initializes the float to 0.0.
  - `AFloat(String s)`: Initializes the float with the value provided as a string.
  - Copy constructor: Creates a copy of an existing `AFloat` instance.
- Methods:
  - `static AFloat parse(String s)`: Parses a string into an `AFloat` instance.
  - Arithmetic operations: `add()`, `subtract()`, `multiply()`, `divide()` to perform basic arithmetic.

## Usage

### As a CLI Tool

To use the library via the command line, run the `MyInfArith` class with the required arguments:

```bash
java -cp .:(address of jar file) (address of MyInfArith) <int/float> <operation> <operand1> <operand2>

### As a python script

It can also be run as a python file:
./run.py <int/float> <operation> <operand1> <operand2>
