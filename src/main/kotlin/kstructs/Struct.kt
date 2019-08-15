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

import com.softwarementors.kpointers.Size
import java.util.BitSet
import com.softwarementors.kstructs.debug.*
import com.softwarementors.kstructs.util.roundUp
import com.softwarementors.kstructs.util.isValidIdentifier
import kotlin.reflect.KClass

@kotlin.ExperimentalUnsignedTypes
class Struct(val name: String, val structs: Structs ) {
   companion object {
      internal val structs = Structs()
   }
   
   // Allocated memory: every byte is represented with a bit, 1 meaning that byte
   // already used by some field, 0 meaning it is unused
   private var memory = BitSet()  
   private var committed_ = false
   private var fields_ = mutableListOf<Field<out Any>>() // sortedMapOf<String, Field<out Any>>()
   private var usableBytes_ : Size = 0L
   
   val committed: Boolean get() = committed_
   
   val fields : List<Field<out Any>> get() {
      assert( committed )
      
      return fields_.toList()
   }
   
   val usableBytes : Size get() {
      assert( committed)
      
      return usableBytes_
   }
   
   val sizeBytes : Size get() {
      assert( committed )
      
      if( fields_.isEmpty()) {
         return 0
      }
      // @DEBUG
      // val mem = toDebugString(memory)
      
      val baseSize = memory.length().toLong()
      val r = roundUp(baseSize, alignment)
      return r
   }

   val alignment : Size get() {
      if( fields_.isEmpty()) {
         return 0
      }
      val r = fields_[0].alignment
      return r
   }
      
   fun commit() {
      assert( !committed)

      committed_ = true
   }
   
   private fun addField(f: Field<out Any>) {
      assert( !committed)
      assert( !hasField(f))
      
      fields_.add(f)
   }
   
   fun addShortString(name : String, maxLen : UByte) : ShortStringField {
      assert(!committed)
      assert(isValidIdentifier(name))
      assert(!hasField(name))

      val sizeBytes = ShortString.sizeBytes(maxLen)
      val offset = allocateField( sizeBytes, alignment=1)
      val field = ShortStringField(name, offset, maxLen)
      addField(field)
      return field
   }
   
   fun addByte(name : String) : ByteField {
      assert(!committed)
      assert(isValidIdentifier(name))
      assert(!hasField(name))

      val sizeBytes = Byte.SIZE_BYTES.toLong()
      val offset = allocateField( sizeBytes, sizeBytes)
      val field = ByteField(name, offset)
      addField(field)
      return field
   }
   
   fun addUByte(name : String) : UByteField {
      assert( !committed)
      assert(isValidIdentifier(name))
      assert(!hasField(name))

      val sizeBytes = UByte.SIZE_BYTES.toLong()
      val offset = allocateField( sizeBytes, sizeBytes)
      val field = UByteField(name, offset)
      addField(field)
      return field
   }
   
   fun addBoolean(name : String) : BooleanField {
      assert( !committed)
      assert(isValidIdentifier(name))
      assert(!hasField(name))

      val sizeBytes = 1L
      val offset = allocateField( sizeBytes, sizeBytes)
      val field = BooleanField(name, offset)
      addField(field)
      return field
   }
   
   fun addChar(name : String) : CharField {
      assert( !committed)
      assert(isValidIdentifier(name))
      assert(!hasField(name))

      val sizeBytes = Char.SIZE_BYTES.toLong()
      val offset = allocateField( sizeBytes, sizeBytes)
      val field = CharField(name, offset)
      addField(field)
      return field
   }
   
   fun addShort(name : String) : ShortField {
      assert( !committed)
      assert(isValidIdentifier(name))
      assert(!hasField(name))

      val sizeBytes = Short.SIZE_BYTES.toLong()
      val offset = allocateField( sizeBytes, sizeBytes)
      val field = ShortField(name, offset)
      addField(field)
      return field
   }
   
   fun addUShort(name : String) : UShortField {
      assert( !committed)
      assert(isValidIdentifier(name))
      assert(!hasField(name))

      val sizeBytes = Short.SIZE_BYTES.toLong()
      val offset = allocateField( sizeBytes, sizeBytes)
      val field = UShortField(name, offset)
      addField(field)
      return field
   }
   
   fun addInt(name : String) : IntField {
      assert( !committed)
      assert(isValidIdentifier(name))
      assert(!hasField(name))

      val sizeBytes = Int.SIZE_BYTES.toLong()
      val offset = allocateField( sizeBytes, sizeBytes)
      val field = IntField(name, offset)
      addField(field)
      return field
   }
   
   fun addUInt(name : String) : UIntField {
      assert( !committed)
      assert(isValidIdentifier(name))
      assert(!hasField(name))

      val sizeBytes = UInt.SIZE_BYTES.toLong()
      val offset = allocateField( sizeBytes, sizeBytes)
      val field = UIntField(name, offset)
      addField(field)
      return field
   }
   
   fun addLong(name : String) : LongField {
      assert( !committed)
      assert(isValidIdentifier(name))
      assert(!hasField(name))

      val sizeBytes = Long.SIZE_BYTES.toLong()
      val offset = allocateField( sizeBytes, sizeBytes)
      val field = LongField(name, offset)
      addField(field)
      return field
   }
   
   fun addULong(name : String) : ULongField {
      assert( !committed)
      assert(isValidIdentifier(name))
      assert(!hasField(name))

      val sizeBytes = ULong.SIZE_BYTES.toLong()
      val offset = allocateField( sizeBytes, sizeBytes)
      val field = ULongField(name, offset)
      addField(field)
      return field
   }
   
   fun addFloat(name : String) : FloatField {
      assert( !committed)
      assert(isValidIdentifier(name))
      assert(!hasField(name))

      val sizeBytes = 4L
      val offset = allocateField( sizeBytes, sizeBytes)
      val field = FloatField(name, offset)
      addField(field)
      return field
   }
   
   fun addDouble(name : String) : DoubleField {
      assert( !committed)
      assert(isValidIdentifier(name))
      assert(!hasField(name))

      val sizeBytes = 8L
      val offset = allocateField( sizeBytes, sizeBytes)
      val field = DoubleField(name, offset)
      addField(field)
      return field
   }
     
   public fun hasField( name : String ) : Boolean {
      assert(isValidIdentifier(name))
      
      val field = fields_.find { it.name == name }
      return field != null
   }

   public fun hasField( field : Field<out Any>) : Boolean {
      return fields_.indexOf(field) >= 0
   }

   public fun getBooleanField(name: String) : BooleanField? {
      assert( isValidIdentifier(name))
      val field = fields_.find { it.name == name && (it.dataType == Boolean::class) }
      if( field == null ) {
         return null
      }
      return field as BooleanField
   }

   private fun getFieldByNameAndType( name : String, type : KClass<Any>) : Field<out Any>? {
      return fields_.find { it.name == name && (it.dataType == type) }
   }
         
   public fun getShortStringField(name: String) : ShortStringField? {
      assert( isValidIdentifier(name))
      val field = fields_.find { it.name == name && it.dataType == Byte::class}
      if( field == null ) {
         return null
      }
      return field as ShortStringField
   }
   
   public fun getByteField(name: String) : ByteField? {
      assert( isValidIdentifier(name))
      val field = fields_.find { it.name == name && it.dataType == Byte::class}
      if( field == null ) {
         return null
      }
      return field as ByteField
   }
      
   public fun getUByteField(name: String) : UByteField? {
      assert( isValidIdentifier(name))
      val field = fields_.find { it.name == name && (it.dataType == UByte::class) }
      if( field == null ) {
         return null
      }
      return field as UByteField
   }
      
   public fun getCharField(name: String) : CharField? {
      assert( isValidIdentifier(name))
      val field = fields_.find { it.name == name && (it.dataType == Char::class) }
      if( field == null ) {
         return null
      }
      return field as CharField
   }
       
   public fun getShortField(name: String) : ShortField? {
      assert( isValidIdentifier(name))
      val field = fields_.find { it.name == name && (it.dataType == Short::class) }
      if( field == null ) {
         return null
      }
      return field as ShortField
   }
      
   public fun getUShortField(name: String) : UShortField? {
      assert( isValidIdentifier(name))
      val field = fields_.find { it.name == name && (it.dataType == UShort::class) }
      if( field == null ) {
         return null
      }
      return field as UShortField
   }
      
   public fun getIntField(name: String) : IntField? {
      assert( isValidIdentifier(name))
      val field = fields_.find { it.name == name && (it.dataType == Int::class) }
      if( field == null ) {
         return null
      }
      return field as IntField
   }
      
   public fun getUIntField(name: String) : UIntField? {
      assert( isValidIdentifier(name))
      val field = fields_.find { it.name == name && (it.dataType == UInt::class) }
      if( field == null ) {
         return null
      }
      return field as UIntField
   }
      
   public fun getLongField(name: String) : LongField? {
      assert( isValidIdentifier(name))
      val field = fields_.find { it.name == name && (it.dataType == Long::class) }
      if( field == null ) {
         return null
      }
      return field as LongField
   }
      
   public fun getULongField(name: String) : ULongField? {
      assert( isValidIdentifier(name))
      val field = fields_.find { it.name == name && (it.dataType == ULong::class) }
      if( field == null ) {
         return null
      }
      return field as ULongField
   }
      
   public fun getFloatField(name: String) : FloatField? {
      assert( isValidIdentifier(name))
      val field = fields_.find { it.name == name && (it.dataType == Float::class) }
      if( field == null ) {
         return null
      }
      return field as FloatField
   }
      
   public fun getDoubleField(name: String) : DoubleField? {
      assert( isValidIdentifier(name))
      val field = fields_.find { it.name == name && (it.dataType == Double::class) }
      if( field == null ) {
         return null
      }
      return field as DoubleField
   }
      
   private fun allocateField( sizeBytes : Size, alignment : Size ) : Long {
      // @DEBUG
      // var mem = toDebugString(memory)
      
      usableBytes_ += sizeBytes
      var firstCandidateByte = memory.nextClearBit(0).toLong()
      firstCandidateByte = roundUp(firstCandidateByte, alignment)
      var lastCandidateByte = firstCandidateByte + sizeBytes -1
      do {
         // Try to find a block of free memory (consecutive 0 bits) in existing
         // memory
         val nextByteAllocated = memory.nextSetBit(firstCandidateByte.toInt())
         val noMoreMemoryAvailable = nextByteAllocated < 0
         if( noMoreMemoryAvailable) {
            firstCandidateByte = roundUp(memory.length().toLong(), alignment)
            lastCandidateByte = firstCandidateByte + sizeBytes - 1
            break;
         }
         val foundEnoughFreeMemory = nextByteAllocated > lastCandidateByte.toInt()
         if( foundEnoughFreeMemory) {
            break;
         }

         firstCandidateByte = roundUp(memory.nextClearBit(firstCandidateByte.toInt()).toLong(),alignment)
         lastCandidateByte = firstCandidateByte + sizeBytes  - 1 
      } while( true )

      // This will work whether we have free memory or whether we need to 'grow' it
      memory.set( firstCandidateByte.toInt(), lastCandidateByte.toInt() + 1, true )

      // @DEBUG
      // mem = toDebugString(memory)
      
      return firstCandidateByte
   }
   
   internal fun initializeMemory( v: StructPointer ) {
      assert( v.struct == this)
      
      fields.forEach {
         // For ShortStrings, initilize them to valid empty string
         if( it.dataType == ShortString::class) {
            val f = it as ShortStringField
            val s = v[f]
            s.initializeMem(f.maxLen)
         }
      }
   }
}