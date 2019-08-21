# kstructs: TO-DO list

## Must have

- Publish blog about KStructs in personal blog

- ShortString:

  - Basic functionality to integrate with environment (collections!): compareTo + hash + equals

  - String basic funcionality: case-insensitive, startsWith, hashCi, etc.
  
    With regards to case-sensitiviry, beware: Char.toUpper() para chars 181 y 255 NO caben en un UByte
  
    With regards to comparions, beware: 'a' vs 'à' vs 'A' vs...

  - Many functions should exist in two versions: String + ShortString

## Important

- Add nested struct support

- Add nested "array" support

- Add "dump for debug" support for structs

- Publish to JCenter repository: see [https://reflectoring.io/bintray-jcenter-maven-central/](https://reflectoring.io/bintray-jcenter-maven-central/)

- Do FAQ: might be cut&paste of the one for KPointers

- Integrate it into a multi-project build with Gradle in which projects can exist independently of each other while begin well synchonized

- Try to publish about KStructs in DZone or similar

- Doc: document need to refresh Gradle config from Eclipse after getting project from repo (.classpath and .project must be regenerated)

- README, add:

  - Explain we do not provide 'by field name' access for performance reasons

  - Explain we do not provide iterator-like for performance reasons

## Nice to have

- Move utils.Math.kt to some general place, has tests

- Move isValidIdentifier to some general place, has tests and very exhaustive

- Examples, add:

  - Traverse all field for all structs and provide metadata

  - Access through field name
