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

import com.softwarementors.kpointers.*
import java.nio.charset.StandardCharsets
import java.nio.charset.Charset

@kotlin.ExperimentalUnsignedTypes
inline class ShortString constructor(internal val address: Pointer) {
   /****** auto begin */
   internal val lengthPtr : UBytePointer get() =  UBytePointer(this.address + LENGTH_OFFSET)  
   internal val maxLenPtr : UBytePointer get() =  UBytePointer(this.address + MAXLEN_OFFSET)
   internal val bytesPtr : UBytePointer get() =  UBytePointer(this.address + STR_OFFSET)
   /* auto end ******/
   
   companion object {
      /****** auto begin */
      const val LENGTH_OFFSET = 0L
      const val MAXLEN_OFFSET = 1L
      const val STR_OFFSET = 2L
      /* auto end ******/
      
      const val NON_STR_BYTES = STR_OFFSET
      const val MAX_LENGTH = 255 /* =UByte.MAX_VALUE*/ - STR_OFFSET
      // @CHARSETS_ENCODING
      val DEFAULT_CHARSET = StandardCharsets.ISO_8859_1
      // val encoder = charset.newEncoder()
      
      fun isValidChar(v: Char) : Boolean {
         return v.toInt() <= UByte.MAX_VALUE.toInt()
      }
      
      fun isValidMaxLen(v: UByte): Boolean {
         return v <= MAX_LENGTH.toUByte()
      }
      
   }
   
   // ******************************************
   fun toPointer() : Pointer = address
   fun isNull() : Boolean = address == NULL
   operator fun not() : Boolean = address == NULL
   
   /****** auto begin */
   val length : UByte get() {
      assert( !isNull() )
      return lengthPtr.it 
   }
   
   val maxLen : UByte get() {
      assert( !isNull() )
      return maxLenPtr.it
   }
   
   val bytes : UBytePointer get() {
      assert( !isNull() )
      return bytesPtr
   }
   /* auto end ******/

   fun stringIsValid(v: String) : Boolean {
      if(v.length > maxLen.toInt()) {
         return false
      }
      for( i in 0 until v.length) {
         if( !isValidChar(v[i])) {
            return false
         }
      }
      return true
   }
     
   fun isValidPosition(i: UByte): Boolean {
      assert( !isNull() )
      return i <= length
   }
      
   operator fun get(i: UByte) : UByte {
      assert( !isNull() )
      assert( isValidPosition(i))
      
      val r = bytesPtr[i.toLong()]
      return r
   }

   operator fun set(i: UByte, v: UByte) {
      assert( !isNull() )
      assert( isValidPosition(i))
      
      bytesPtr[i.toLong()] = v
   }

   fun charAt(i: UByte) : Char {
      assert( !isNull() )
      assert( isValidPosition(i))
      
      return this[i].toInt().toChar()
   }

   fun toString(charset: Charset ) : String {
      assert( !isNull())
      
      val len : Int = length.toInt()
      val bytes : ByteArray = ByteArray(len)
      for( i in 0..len-1) {
         bytes[i] = this.bytes[i.toLong()].toByte()
      }
      val r = String(bytes, charset)
      return r      
   }
      
      
   override fun toString() : String {
      assert( !isNull() )

      return toString(DEFAULT_CHARSET)
   }
   
   fun set(s: String) {
      assert( !isNull() )
      assert( stringIsValid(s))
      
      lengthPtr.it = s.length.toUByte()
      val len = s.length
      for( i in 0 until len) {
         bytes[i.toLong()] = s[i].toByte().toUByte()
      }
   }   
}
