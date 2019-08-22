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
import com.softwarementors.kpointers.impl.unsafe
import com.softwarementors.kpointers.*
import com.softwarementors.kpointers.PrimitiveArraysAllocator


@kotlin.ExperimentalUnsignedTypes
class StructTest {
   
   @Test
   fun test_allocateSingle() {
      val struct : Struct = Structs().add("bs")
      val bf = struct.addByte("bf")
      struct.commit()
      
      assertEquals(0L, bf.offset)
      val sb = bf.sizeBytes
      assertEquals(1L, sb)
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
      assertTrue( struct.sizeBytes % MIN_MEM_ALIGNMENT == 0L)
   }

   @Test
   fun test_emptyStruct() {
      var struct : Struct = Structs().add("s")
      struct.commit()
      assertEquals( 0L, 0)
      assertEquals( 0L, struct.sizeBytes)
      assertEquals( 0L, struct.usableBytes)
      assertTrue( struct.sizeBytes % MIN_MEM_ALIGNMENT == 0L)
   }
   
   @Test
   fun test_allocateAlignment() {
      var struct : Struct = Structs().add("s")
      struct.addFloat("ff")
      struct.addByte("bf")
      struct.commit()
      assertEquals( 8L, struct.sizeBytes)
      assertEquals( 5L, struct.usableBytes)
      assertTrue( struct.sizeBytes % MIN_MEM_ALIGNMENT == 0L)
      
      struct = Structs().add("s")
      struct.addByte("bf")
      struct.addFloat("ff")
      struct.commit()
      assertEquals( 8L, struct.sizeBytes)
      assertEquals( 5L, struct.usableBytes)
      assertTrue( struct.sizeBytes % MIN_MEM_ALIGNMENT == 0L)

      struct = Structs().add("s")
      struct.addByte("bf")
      struct.addUShort("usf")
      struct.commit()
      assertEquals( 8L, struct.sizeBytes)
      assertEquals( 3L, struct.usableBytes)
      assertTrue( struct.sizeBytes % MIN_MEM_ALIGNMENT == 0L)

      struct = Structs().add("s")
      struct.addUShort("usf")
      struct.addByte("bf")
      struct.commit()
      assertEquals( 8L, struct.sizeBytes)
      assertEquals( 3L, struct.usableBytes)
      assertTrue( struct.sizeBytes % MIN_MEM_ALIGNMENT == 0L)
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
   
   @Test
   fun test_isMemoryAllocated() {
      val struct : Struct = Structs().add("s")
      assertFalse( struct.isMemoryAllocated(0,1))
      assertFalse( struct.isMemoryAllocated(1_000_000,1_000_000))
      struct.addByteAt( "b1", 3)

      var mem : String = struct.toBinaryDebugString()
      assert( "0001" == mem )
      assertTrue( struct.isMemoryAllocated(3,1))
      assertTrue( struct.isMemoryAllocated(2,2))
      assertFalse( struct.isMemoryAllocated(2,1))
      assertTrue( struct.isMemoryAllocated(3,2))
      assertTrue( struct.isMemoryAllocated(2,3))
      assertFalse( struct.isMemoryAllocated(1,2))
      assertFalse( struct.isMemoryAllocated(0,3))
      assertTrue( struct.isMemoryAllocated(0,4))
      assertTrue( struct.isMemoryAllocated(3,3))
      assertFalse( struct.isMemoryAllocated(4,1))
      
      struct.addFloatAt("f1", 8)
      mem = struct.toBinaryDebugString()
      assert( "000100001111" == mem )
      assertTrue( struct.isMemoryAllocated(8,1))
      assertFalse( struct.isMemoryAllocated(12,1))
      assertTrue( struct.isMemoryAllocated(9,1))
      assertTrue( struct.isMemoryAllocated(11,1))
      assertTrue( struct.isMemoryAllocated(8,4))
      assertFalse( struct.isMemoryAllocated(5,3))
      assertTrue( struct.isMemoryAllocated(5,4))
   }
   
   @Test
   fun test_addXxxAt() {
      val struct : Struct = Structs().add("s")
      val b1 = struct.addByteAt( "b1", 0)
      val l1 = struct.addLongAt( "l1", 8)
      val f1 = struct.addFloatAt( "f1", 16)
      val bool1 = struct.addBooleanAt( "bool1", 20)
      struct.commit()
      val sp : StructPointer = StructArrayAllocator.unsafeAllocator.allocateArray( struct, 2, true )
      var s1 = sp
      s1[b1] = 17.toByte()
      s1[l1] = 19898L
      s1[f1] = 3.23f
      s1[bool1] = true
      assertEquals( 17.toByte(), BytePointer(s1.address + 0).it )
      assertEquals( 19898L, LongPointer(s1.address +8).it )
      assertEquals( 3.23f, FloatPointer(s1.address + 16).it )
      val bool1Val = BooleanPointer(s1.address + 20).it
      assertEquals( true, bool1Val )
      
      var structSize = struct.sizeBytes
      assertEquals(24, structSize)
      s1[b1, 1] = 99.toByte()
      s1[l1, 1] = 987L
      s1[f1, 1] = 89.9f
      s1[bool1, 1] = true
      assertEquals( 99.toByte(), BytePointer(s1.address + structSize + 0).it )
      assertEquals( 987L, LongPointer(s1.address + structSize + 8).it )
      assertEquals( 89.9f, FloatPointer(s1.address + structSize + 16).it )
      assertEquals( true, BooleanPointer(s1.address + structSize + 20).it )
   }
}