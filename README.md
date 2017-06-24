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

#### Current criterion plan:

Based on Katherine Smith's presentation of Genomics England Rare Disease Filtering rules (ACGS Exome Workshop 2017)
Rules adapted were required:

* Criterion 1: Variant must contain PASS filter (this maybe flagged to allow criterion to be relaxed)
* Criterion 2: Allele frequency 0.01 Biallelic / 0.001 Monoallelic (can be set by args) 
* Criterion 3:
     - Following Biotypes allowed: protein_coding; nonsense_mediated_decay; non_stop_decay;
     IG_C_gene; IG_D_gene; IG_J_gene; IG_V_gene; TR_C_gene; TR_D_gene; TR_J_gene; TR_V_gene
     - High consequence: transcript_ablation; splice_acceptor_variant; splice_donor_variant; 
        stop_gained; frameshift_variant; stop_lost; initiator_codon_variant
     - Moderate consequence: transcript_amplification; inframe_insertion; inframe_deletion; missense_variant;
        splice_region_variant; incomplete_terminal_codon_variant
* Criterion 4: Segregation (maybe future work since it will require a pedigree)
* Criterion 5: Gene panels (checked against HGNC api)

## License

Copyright © 2017 Garan Jones

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
