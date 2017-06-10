# Virtual Parser

Virtual panel parser for Cellbase annotated variants.

## Usage

Uses HTSJDK to parse VCF files and submit the genomic coordinates to the Cellbase RESTful service for annotation.

Outline plan:

1. Ability to import VCF (partially implemented)
2. Query Cellbase for annotation (partially implemented)
3. Deconstruct returned JSON using Odin
4. Filter by ruleset and provided gene symbol list
5. Score and order shortlist by impact, population frequency


## License

Copyright © 2017 Garan Jones

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
