# Advanced Kotlin: structs -with KStructs

![KStructs structs for Kotlin](README/headerImage.png)

## What KStructs is

**KStructs** is a library for

If you are into hard low-level programming, you'll love **KStructs**.

## Introduction

Working with raw _off-heap_ memory in Java can be daunting. In fact, Java does not provide pointers nor direct memory access, being very careful in wrapping whatever might need such things under friendly abstractions that do not allow memory access to transpire.

However, if you work with low-level memory provided by an external DLL or application (shared memory, anyone?), you'll have to deal with raw but structured memory. Frankly, you don't want to work with the _Unsafe_ API the JDK provides to access data, as you'll drown in _long_ values that represent pointers, field offsets, field sizes, etc., making very easy to make mistakes. You'll want to use **KStructs**, which provides a much easier way to handle all those chores.

## An example

## Running the examples

## License

**KStructs** is provided under the [LPGL 3.0 license](https://opensource.org/licenses/LGPL-3.0)
