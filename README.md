# Advanced Kotlin: structs -with KStructs

![KStructs structs for Kotlin](README/headerImage.png)

## What KStructs is

**KStructs** is a library for dealing with low level off-heap memory access without the hassles of using _Unsafe_.

If you are into hard low-level programming, you'll love **KStructs**.

## Introduction

Working with raw _off-heap_ memory in Java can be daunting. In fact, Java does not provide pointers nor direct memory access, being very careful in wrapping whatever might need such things under friendly abstractions that do not allow memory access to transpire.

However, if you work with low-level memory provided by an external DLL or application (shared memory, anyone?), you might need to deal with raw _but_ structured memory. In that scenario, you don't want to work with the _Unsafe_ API to access fields in off-heap memory: if you do, you'll drown in _long_ values that represent pointers, field offsets, field sizes, etc., making very easy to make very dangerous mistakes.

In scenarios where you need to access raw memory, you'll want to use **KStructs**, which provides a much easier way to deal with all those offsets, addresses, etc.

## An example

Here is an example of a simple application that has to deal with off-heap memory. Just for the sake of simplicity, let's imagine that the memory is structured as follows:

>       Offset : Field -> Type
>       ------   -----    ----
>         0    : i     -> Int
>         1    : i
>         2    : i
>         3    : i
>         4    : f     -> Float
>         5    : f
>         6    : f
>         7    : f
>         8    : b     -> Byte

Here, we have a data structure in which we've got an ``Int`` named ``i`` which starts at offset 0, a ``Float`` named ``f`` that starts at offset 4, and a ``Byte`` field named ``b`` whose offset is 8. Now, we can write a program and fill it with offsets all around, enjoying a suffer fest, or we can use **KStructs** as follows. First of all we define the memory structure by creating an ``Struct`` definition for it:

  ``` Kotlin
  // dataAddress offsets
  val iOffset = 0L
  val fOffset = 4L
  val bOffset = 8L
  val recordSize = 12L

  val myStruct = Structs().add("MyStruct")
  val iField = myStruct.addIntAt("i", iOffset)
  val fField = myStruct.addFloatAt("f", fOffset)
  val bField = myStruct.addByteAt("b", bOffset)
  myStruct.commit(recordSize)
  ```

Of course, we are using constants for offsets, but in any real application you will probably ask the external application for the offsets and structure size with some kind of handshake mechansm. Now that we have a ``myStruct`` ``Struct``, we can use it and the defined fields to access raw memory safely, as follows:

``` Kotlin
   val itemCount = 5L
   // Get a StructPointer: this is just for illustrative purposes, you might
   // create one directly from external raw memory...
   val dataAddress = StructArrayAllocator.unsafeAllocator.allocateArray(myStruct, itemCount)

   try {
      // Fill array with dataAddress
      for( i in 0L until itemCount) {
         dataAddress[iField, i] = i.toInt() * 10
         dataAddress[fField, i] = i.toFloat() * 100.0f
         dataAddress[bField, i] = i.toByte()
      }

      // Traverse array and print each 'record'
      for( index in 0L until itemCount) {
         val i = dataAddress[iField, index]
         val f = dataAddress[fField, index]
         val b = dataAddress[bField, index]
         println( "Record #$index= {i:$i, f:$f, b:$b}")
      }
   }
   ```

The output for this code will be as follows:

>       Record #0= {i:0, f:0.0, b:0}
>       Record #1= {i:10, f:100.0, b:1}
>       Record #2= {i:20, f:200.0, b:2}
>       Record #3= {i:30, f:300.0, b:3}
>       Record #4= {i:40, f:400.0, b:4}

As you can see, dealing with raw memory this way is completely safe and very easy, once you have defined the memory field offsets, as seen above: no more pointer arithmetic, and much less pain.

## Running the examples

To see the list of existing examples you can run from the command line, execute ``gradlew tasks --group Application`` from the command line

To run example ``Xxx``, use the existing _runXxxExample_ task executing ``gradlew runXxxExample`` from the command line

All examples source code is located under _src/main/kstructs/examples_: check it.

## License

**KStructs** is provided under the [LPGL 3.0 license](https://opensource.org/licenses/LGPL-3.0)
