/*
LGPL v3.0
Copyright (C) 2019 Pedro Agullo Soliveres
p.agullo.soliveres@gmail.com

KStructs is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3 of the License, or (at your option) any later version.

KStructs is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software Foundation,
Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.softwarementors.kstructs.examples

import com.softwarementors.kstructs.*
import com.softwarementors.kpointers.*

@kotlin.ExperimentalUnsignedTypes
fun main( args : Array<String> ) {
   println( title )
   println( "Example: simple structs example")
   println()
   
   println( "Starting example execution...")
   val iOffset = 0L
   val fOffset = 4L
   val bOffset = 8L
   val recordSize = 12L
   
   val myStruct = Structs().add("MyStruct")   
   val iField = myStruct.addIntAt("i", iOffset)
   val fField = myStruct.addFloatAt("f", fOffset)
   val bField = myStruct.addByteAt("b", bOffset)
   myStruct.commit(recordSize)
   
   val itemCount = 5L
   val data = StructArrayAllocator.unsafeAllocator.allocateArray(myStruct, itemCount)
   try {
      for( i in 0L until itemCount) {
         data[iField, i] = i.toInt() * 10
         data[fField, i] = i.toFloat() * 100.0f
         data[bField, i] = i.toByte()
      }      

      for( index in 0L until itemCount) {
         val i = data[iField, index]
         val f = data[fField, index]
         val b = data[bField, index]
         
         println( "Record #$index= {i:$i, f:$f, b:$b}")
      }      
   }
   finally {
      StructArrayAllocator.unsafeAllocator.free(data)
   }
   
}



