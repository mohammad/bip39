package com.github.mohammad.bip39

import org.scalatest.flatspec.AnyFlatSpec
import com.github.mohammad._


class Bip39Spec extends AnyFlatSpec {

  it should "generate a valid seed" in {
    val seed = Bip39.generateSeed(Bip39.Entropy128, Bip39.English, "bob")
    print(seed)
  }
}

