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

package com.softwarementors.kstructs

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import com.softwarementors.kstructs.*
import com.softwarementors.kpointers.BytePointer
import com.softwarementors.kpointers.Size



@kotlin.ExperimentalUnsignedTypes
class StructPointerTest {

   val struct1 : Struct
   val byteField : ByteField
   val struct1Size : Size
   
   init {
      struct1 = Structs().add("bs")
      byteField = struct1.addByte("byteField")
      struct1.commit()
      struct1Size = struct1.sizeBytes
   }
   
   @Test
   fun test_noFields() {
      val struct : Struct = Structs().add("S")
      struct.commit()
      assertTrue( struct.fields.isEmpty())
      assertEquals( 0L, struct.sizeBytes)
      assertEquals( 0L, struct.usableBytes)
   }
   
   @Test
   fun test_access() {
      var itemCount = 100L
      val array = StructsArrayAllocator.unsafeAllocator.allocateArray(struct1, itemCount)
      try {
         array[byteField] = 98

         var currentBp = BytePointer(array.toPointer())
         assertEquals( 98, currentBp[byteField.offset])
         
         assertEquals( 98, array[byteField])
      }
      finally {
         StructsArrayAllocator.unsafeAllocator.free(array)
      }
    }
   
   @Test
   fun test_indexedAccess() {
      var itemCount = 100L
      val array = StructsArrayAllocator.unsafeAllocator.allocateArray(struct1, itemCount)
      try {
         var current = array
         for( i in 0 until itemCount) {
            current[byteField, i.toLong()] = i.toByte()
         }
   
         var currentBp = BytePointer(array.toPointer())
         for( n in 0L until itemCount) {
            assertEquals( n.toByte(), currentBp[byteField.offset])
            assertEquals( n.toByte(), array[byteField, n])
            currentBp += struct1.sizeBytes
         }         
      }
      finally {
         StructsArrayAllocator.unsafeAllocator.free(array)
      }
   }
   
   @Test
   fun test_nestedStruct() {
      
   }
}