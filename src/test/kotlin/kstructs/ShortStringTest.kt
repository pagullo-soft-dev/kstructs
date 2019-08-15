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
import com.softwarementors.kpointers.NULL
import com.softwarementors.kpointers.Pointer
import com.softwarementors.kstructs.ShortStringAllocator

@kotlin.ExperimentalUnsignedTypes
class ShortStringTest {

   val outstandingAllocations = mutableListOf<ShortString>()
   val allocator = ShortStringAllocator.unsafeAllocator
   
   fun allocString(maxLen: UByte) : ShortString {
      assert( ShortString.isValidMaxLen(maxLen))
      
      val r = allocator.allocate(maxLen)
      outstandingAllocations.add(r)
      return r
   }
      
   @AfterEach
   fun afterEach() {
      outstandingAllocations.forEach { allocator.free(it)}
   }
   
   @Test
   fun test_lowLevelAccess() {
      val s = "a".padEnd( (ShortString.MAX_LENGTH-1).toInt(), '.').plus('z')
      var ss = allocString(ShortString.MAX_LENGTH.toUByte())      
      ss.set(s)
      
      assertEquals( ShortString.MAX_LENGTH.toUByte(), ss.length)
      assertEquals( s.length, ss.length.toInt())
      assertEquals( ShortString.MAX_LENGTH.toUByte(), ss.maxLen)
      
      for( i in 0 until s.length) {
         val c = ss.charAt(i.toUByte())
         assertEquals( s[i], c)
      }
      // Make sure there is no some mistake in the 's' creation logic
      assertEquals( 'a', ss.charAt(0u))
      assertEquals( 'z', ss.charAt((ShortString.MAX_LENGTH-1).toUByte()))
   }

   @Test
   fun test_isNull() {
      assertTrue(ShortString(NULL).isNull())
      assertFalse(ShortString(Pointer(33)).isNull())
   }

   @Test
   fun test_get_indexed() {
      val s = allocString(100u)
      s.set( "Hello")
      assertEquals( 'H', s[0u].toInt().toChar())
      assertEquals( 'e', s[1u].toInt().toChar())
      assertEquals( 'o', s[4u].toInt().toChar())
   }
   
   @Test
   fun test_set_indexed() {
      val s = allocString(5u)
      s.set( "".padEnd( 5, ' '))
      s[0u] = 'a'.toInt().toUByte()      
      assertEquals( 'a', s.charAt(0u))
      
      s[4u] = 'b'.toInt().toUByte()
      assertEquals( 'b', s.charAt(4u))      
   }
   
   @Test
   fun test_not() {
      assertTrue(!ShortString(NULL))
      assertFalse(!ShortString(Pointer(33)))
   }

   @Test
   fun test_setString() {
      var ss = allocString(ShortString.MAX_LENGTH.toUByte())
      ss.set("")
      assertEquals( 0.toUByte(), ss.length)
      ss.set("hello")
      assertEquals( 5.toUByte(), ss.length)
      assertEquals( 'h', ss.charAt(0u))
      assertEquals( 'e', ss.charAt(1u))
      assertEquals( 'o', ss.charAt(4u))
   }
   
   @Test
   fun test_toString() {
      var ss = allocString(ShortString.MAX_LENGTH.toUByte())      
      ss.set("")
      assertEquals( "", ss.toString())

      ss.set("abC")
      assertEquals( "abC", ss.toString())
   }
   
   @Test
   fun test_isValidMaxLength() {
      assertTrue(ShortString.isValidMaxLen(0u))
      assertTrue(ShortString.isValidMaxLen(33u))
      assertTrue(ShortString.isValidMaxLen((ShortString.MAX_LENGTH-1).toUByte()))
      assertTrue(ShortString.isValidMaxLen(ShortString.MAX_LENGTH.toUByte()))
      assertFalse(ShortString.isValidMaxLen((ShortString.MAX_LENGTH+1).toUByte()))
   }

   @Test
   fun test_isValidChar() {
      for( i in 0..(UByte.MAX_VALUE.toInt()+33)) {
         val ok = i <= UByte.MAX_VALUE.toInt()
         assertEquals( ok, ShortString.isValidChar(i.toChar()) )
      }
   }   
   
   @Test
   fun test_stringIsValid_checkingLength() {
      var ss = allocString(1.toUByte())
      assertTrue( ss.stringIsValid(""))
      assertTrue( ss.stringIsValid("a"))
      assertFalse( ss.stringIsValid("ab"))
      assertFalse( ss.stringIsValid("abcd"))
   }
   
   @Test
   fun test_stringIsValid_checkingCharValidity() {
      var illegalChar : Char = (UByte.MAX_VALUE.toInt() + 1024).toChar()
      var ss = allocString(10.toUByte())
      assertTrue( ss.stringIsValid("abc"))
      assertFalse( ss.stringIsValid(illegalChar.toString().plus("abc")))
      assertFalse( ss.stringIsValid("ab".plus(illegalChar)))
      assertFalse( ss.stringIsValid("a".plus(illegalChar).plus("b")))
   }
   
}