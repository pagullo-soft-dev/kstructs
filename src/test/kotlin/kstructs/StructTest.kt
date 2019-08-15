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
import com.softwarementors.kpointers.malloc.unsafeAllocator
import com.softwarementors.kpointers.impl.unsafe
import com.softwarementors.kpointers.*

@kotlin.ExperimentalUnsignedTypes
class StructTest {

   val outstandingAllocations = mutableListOf<BytePointer>()
   fun allocArray100() : BytePointer {
      val r = unsafeAllocator.allocateBytePointerArray(100)
      outstandingAllocations.add(r)
      return r
   }
      
   @AfterEach
   fun afterEach() {
      outstandingAllocations.forEach { unsafeAllocator.free(it)}
   }
   
   @Test
   fun test_allocateSingle() {
      val struct : Struct = Structs().add("bs")
      val bf = struct.addByte("bf")
      struct.commit()
      
      assertEquals(0L, bf.offset)
      assertEquals(1L, bf.sizeBytes)
   }
   
   @Test
   fun test_allocateThreeFields() {
      val struct : Struct = Structs().add("bs")
      val bf = struct.addByte("bf")
      val bf2 = struct.addByte("bf2")
      val bf3 = struct.addByte("bf3")
      struct.commit()
      
      assertEquals(0L, bf.offset)
      assertEquals(1L, bf2.offset)
      assertEquals(2L, bf3.offset)
   }

   @Test
   fun test_allocateFieldsOfAllTyes() {
      val struct : Struct = Structs().add("bs")
      val bf = struct.addByte("bf")
      val cf = struct.addChar("cf")
      val intf = struct.addInt("if")
      val boolf = struct.addBoolean("boolf")
      val sf = struct.addShort("sf")
      val lf = struct.addLong("lf")
      val b2 = struct.addByte("b2")
      val ff = struct.addFloat("ff")
      val s2 = struct.addShort("s2")
      val df = struct.addDouble("df")
      val i2 = struct.addInt("i2")
      struct.commit()
      
      assertEquals(0L, bf.offset)
      assertEquals(2L, cf.offset)
      assertEquals(4L, intf.offset)
      assertEquals(1L, boolf.offset)
      assertEquals(8L, sf.offset)
      assertEquals(16L, lf.offset)
      assertEquals(10L, b2.offset)      
      assertEquals(12L, ff.offset)      
      assertEquals(24L, s2.offset)
      assertEquals(32L, df.offset)
      assertEquals(28L, i2.offset)
      assertEquals( 40L, struct.sizeBytes)
      assertEquals( 37L, struct.usableBytes)
      assertEquals( 1L, struct.alignment)
   }

   @Test
   fun test_emptyStruct() {
      var struct : Struct = Structs().add("s")
      struct.commit()
      assertEquals( 0L, struct.alignment)
      assertEquals( 0L, struct.sizeBytes)
      assertEquals( 0L, struct.usableBytes)
   }
   
   @Test
   fun test_allocateAlignment() {
      var struct : Struct = Structs().add("s")
      struct.addFloat("ff")
      struct.addByte("bf")
      struct.commit()
      assertEquals( 4L, struct.alignment)
      assertEquals( 8L, struct.sizeBytes)
      assertEquals( 5L, struct.usableBytes)
      
      struct = Structs().add("s")
      struct.addByte("bf")
      struct.addFloat("ff")
      struct.commit()
      assertEquals( 1L, struct.alignment)
      assertEquals( 8L, struct.sizeBytes)
      assertEquals( 5L, struct.usableBytes)

      struct = Structs().add("s")
      struct.addByte("bf")
      struct.addUShort("usf")
      struct.commit()
      assertEquals( 1L, struct.alignment)
      assertEquals( 4L, struct.sizeBytes)
      assertEquals( 3L, struct.usableBytes)

      struct = Structs().add("s")
      struct.addUShort("usf")
      struct.addByte("bf")
      struct.commit()
      assertEquals( 2L, struct.alignment)
      assertEquals( 4L, struct.sizeBytes)
      assertEquals( 3L, struct.usableBytes)
   }
   
   @Test
   fun test_getXxxField() {
      var struct : Struct = Structs().add("s")
      struct.addFloat("ff")
      struct.addByte("bf")
      struct.commit()

      assertNull( struct.getBooleanField("xyz"))
      assertNull( struct.getBooleanField("ff"))
      assertNull( struct.getByteField("ff"))
      assertNotNull( struct.getByteField("bf"))
   }
   
   @Test
   fun test_shortStringSupport() {
      val strLen = 2.toUByte()
      val struct : Struct = Structs().add("s")
      val padBegin = struct.addByte("padBegin")
      val sf = struct.addShortString("str", strLen)
      val padEnd = struct.addByte("padEnd")
      struct.commit()
      val strBytesOffset = sf.offset + ShortString.NON_STR_BYTES
      
      assertEquals( 0L, padBegin.offset )
      assertEquals( Byte.SIZE_BYTES.toLong(), sf.offset )
      assertEquals( (sf.sizeBytes + padBegin.sizeBytes).toLong(), padEnd.offset )
      
      val struct1 = StructArrayAllocator.unsafeAllocator.allocateArray(struct, 1L)
      struct1.set(padBegin, 127.toByte())
      struct1.set(sf, "ab")
      struct1.set(padEnd, 129.toByte())
      val struct1Address = struct1.toPointer()
      assertEquals( 127.toByte(), BytePointer(struct1Address + padBegin.offset).it)
      assertEquals( 'a'.toByte(), BytePointer(struct1Address + strBytesOffset).it)
      assertEquals( 'b'.toByte(), BytePointer(struct1Address + strBytesOffset+1).it)
      assertEquals( 129.toByte(), BytePointer(struct1Address + padEnd.offset).it)
   }
}