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

@kotlin.ExperimentalUnsignedTypes
class StructPointer(internal val address: Pointer, val struct: Struct) {
   val sizeBytes: Size get() = struct.sizeBytes

   fun toPointer(): Pointer = address

   fun isNull(): Boolean = address == NULL
   operator fun not() = address == NULL

   operator fun get(field: ShortStringField): ShortString {
      assert( struct.hasField(field))
      
      val r = ShortString(address + field.offset)
      return r
   }
      
   operator fun set(field: ShortStringField, v: String) {
      assert( struct.hasField(field))
      
      val r = ShortString(address + field.offset)
      r.set(v)
   }
      
   operator fun get(field: ByteField): Byte {
      assert( struct.hasField(field))
      
      val r = BytePointer(address + field.offset).it
      return r
   }
   
   operator fun set(field: ByteField, v: Byte) {
      assert( struct.hasField(field))
      
      BytePointer(address + field.offset).it = v
   }

   operator fun get(field: ByteField, offset: PointerOffset): Byte {
      assert( struct.hasField(field))
      
      val r = BytePointer(address + (sizeBytes * offset) + field.offset).it
      return r
   }
   
   operator fun set(field: ByteField, offset: PointerOffset, v: Byte) {
      assert( struct.hasField(field))
      
      BytePointer(address + (sizeBytes * offset) + field.offset).it = v
   }

   operator fun set(field: IntField, v: Int) {
      assert( struct.hasField(field))
      
      IntPointer(address + field.offset).it = v
   }

   operator fun get(field: IntField, offset: PointerOffset): Int {
      assert( struct.hasField(field))
      
      val r = IntPointer(address + (sizeBytes * offset) + field.offset).it
      return r
   }
   
   operator fun set(field: IntField, offset: PointerOffset, v: Int) {
      assert( struct.hasField(field))
      
      IntPointer(address + (sizeBytes * offset) + field.offset).it = v
   }

   operator fun set(field: UByteField, v: UByte) {
      assert( struct.hasField(field))
      
      UBytePointer(address + field.offset).it = v
   }

   operator fun get(field: UByteField, offset: PointerOffset): UByte {
      assert( struct.hasField(field))
      
      val r = UBytePointer(address + (sizeBytes * offset) + field.offset).it
      return r
   }
   
   operator fun set(field: UByteField, offset: PointerOffset, v: UByte) {
      assert( struct.hasField(field))
      
      UBytePointer(address + (sizeBytes * offset) + field.offset).it = v
   }

   operator fun set(field: ShortField, v: Short) {
      assert( struct.hasField(field))
      
      ShortPointer(address + field.offset).it = v
   }

   operator fun get(field: ShortField, offset: PointerOffset): Short {
      assert( struct.hasField(field))
      
      val r = ShortPointer(address + (sizeBytes * offset) + field.offset).it
      return r
   }
   
   operator fun set(field: ShortField, offset: PointerOffset, v: Short) {
      assert( struct.hasField(field))
      
      ShortPointer(address + (sizeBytes * offset) + field.offset).it = v
   }

   operator fun set(field: UShortField, v: UShort) {
      assert( struct.hasField(field))
      
      UShortPointer(address + field.offset).it = v
   }

   operator fun get(field: UShortField, offset: PointerOffset): UShort {
      assert( struct.hasField(field))
      
      val r = UShortPointer(address + (sizeBytes * offset) + field.offset).it
      return r
   }
   
   operator fun set(field: UShortField, offset: PointerOffset, v: UShort) {
      assert( struct.hasField(field))
      
      UShortPointer(address + (sizeBytes * offset) + field.offset).it = v
   }

   operator fun set(field: UIntField, v: UInt) {
      assert( struct.hasField(field))
      
      UIntPointer(address + field.offset).it = v
   }

   operator fun get(field: UIntField, offset: PointerOffset): UInt {
      assert( struct.hasField(field))
      
      val r = UIntPointer(address + (sizeBytes * offset) + field.offset).it
      return r
   }
   
   operator fun set(field: UIntField, offset: PointerOffset, v: UInt) {
      assert( struct.hasField(field))
      
      UIntPointer(address + (sizeBytes * offset) + field.offset).it = v
   }

   operator fun set(field: LongField, v: Long) {
      assert( struct.hasField(field))
      
      LongPointer(address + field.offset).it = v
   }

   operator fun get(field: LongField, offset: PointerOffset): Long {
      assert( struct.hasField(field))
      
      val r = LongPointer(address + (sizeBytes * offset) + field.offset).it
      return r
   }
   
   operator fun set(field: LongField, offset: PointerOffset, v: Long) {
      assert( struct.hasField(field))
      
      LongPointer(address + (sizeBytes * offset) + field.offset).it = v
   }

   operator fun set(field: ULongField, v: ULong) {
      assert( struct.hasField(field))
      
      ULongPointer(address + field.offset).it = v
   }

   operator fun get(field: ULongField, offset: PointerOffset): ULong {
      assert( struct.hasField(field))
      
      val r = ULongPointer(address + (sizeBytes * offset) + field.offset).it
      return r
   }
   
   operator fun set(field: ULongField, offset: PointerOffset, v: ULong) {
      assert( struct.hasField(field))
      
      ULongPointer(address + (sizeBytes * offset) + field.offset).it = v
   }

   operator fun set(field: FloatField, v: Float) {
      assert( struct.hasField(field))
      
      FloatPointer(address + field.offset).it = v
   }

   operator fun get(field: FloatField, offset: PointerOffset): Float {
      assert( struct.hasField(field))
      
      val r = FloatPointer(address + (sizeBytes * offset) + field.offset).it
      return r
   }
   
   operator fun set(field: FloatField, offset: PointerOffset, v: Float) {
      assert( struct.hasField(field))
      
      FloatPointer(address + (sizeBytes * offset) + field.offset).it = v
   }

   operator fun set(field: DoubleField, v: Double) {
      assert( struct.hasField(field))
      
      DoublePointer(address + field.offset).it = v
   }

   operator fun get(field: DoubleField, offset: PointerOffset): Double {
      assert( struct.hasField(field))
      
      val r = DoublePointer(address + (sizeBytes * offset) + field.offset).it
      return r
   }
   
   operator fun set(field: DoubleField, offset: PointerOffset, v: Double) {
      assert( struct.hasField(field))
      
      DoublePointer(address + (sizeBytes * offset) + field.offset).it = v
   }

   operator fun set(field: BooleanField, v: Boolean) {
      assert( struct.hasField(field))
      
      BooleanPointer(address + field.offset).it = v
   }

   operator fun get(field: BooleanField, offset: PointerOffset): Boolean {
      assert( struct.hasField(field))
      
      val r = BooleanPointer(address + (sizeBytes * offset) + field.offset).it
      return r
   }
   
   operator fun set(field: BooleanField, offset: PointerOffset, v: Boolean) {
      assert( struct.hasField(field))
      
      BooleanPointer(address + (sizeBytes * offset) + field.offset).it = v
   }
}

