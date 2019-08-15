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
   val myStruct = Structs().add("MyStruct")
   val bf = myStruct.addByte("b")
   val df = myStruct.addDouble("d")
   val lf = myStruct.addLong("l")
   myStruct.commit()
   
   val itemCount = 5L
   val data = StructArrayAllocator.unsafeAllocator.allocateArray(myStruct, itemCount)
   try {
      for( i in 0L until itemCount) {
         data[bf, i] = i.toByte()
         data[df, i] = i.toDouble() * 10.0
         data[lf, i] = i.toLong() * 100L
      }      

      for( i in 0L until itemCount) {
         val vb = data[bf, i]
         val vd = data[df, i]
         val vl = data[lf, i]
         
         println( "Record #$i= {b:$vb, d:$vd, l:$vl}")
      }      
   }
   finally {
      StructArrayAllocator.unsafeAllocator.free(data)
   }
   
}



