package com.github.mohammad

import java.io.File

import scodec.bits.BitVector
import java.util.Random

import io.github.nremond.PBKDF2
import scorex.crypto.hash.Sha256

import scala.io.Source


package object Bip39 {

  def generateSeed(entropy: Entropy, language: Language, passphrase: String = ""): BitVector = {
    val mnemonic = generateMnemonic(entropy, language)
    generateSeedFromMnemonic(mnemonic, passphrase)
  }

  def generateMnemonic(entropy: Entropy, language: Language): String = {
    // Generate random ByteArray
    val bytes: Array[Byte] = BitVector.fill(entropy.bits)(high = false).toByteArray
    new Random().nextBytes(bytes)

    // Hash ByteArray and get checkSum
    val checkSum = BitVector(Sha256.hash(bytes)).slice(0, entropy.checkSumLength / 8)

    // Concat bytes with checkSum and process chunks of 11 bits into Ints
    val bitsWithCheckSum = BitVector(bytes) ++ checkSum
    val wordIndexes = bitsWithCheckSum.grouped(11).map(group => group.toInt(signed = false)).toArray

    // Get Words from index
    val lines = Source.fromResource(language.filename).getLines.toArray
    wordIndexes.map(index => lines(index)).mkString("")
  }


  def generateSeedFromMnemonic(mnemonic: String, passphrase: String = ""): BitVector = {
    val salt = ("mnemonic" + passphrase).getBytes()
    val bytes = PBKDF2(password = mnemonic.getBytes, salt, iterations = 2048, cryptoAlgo = "HmacSHA512")
    BitVector(bytes)
  }


  sealed abstract class Entropy(val bits: Int) {
    def checkSumLength: Int = bits / 32
  }

  case object Entropy128 extends Entropy(128)
  case object Entropy160 extends Entropy(160)
  case object Entropy192 extends Entropy(192)
  case object Entropy224 extends Entropy(224)
  case object Entropy256 extends Entropy(256)

  sealed abstract class Language(val filename: String)

  case object English extends Language("words/english.txt")


}

